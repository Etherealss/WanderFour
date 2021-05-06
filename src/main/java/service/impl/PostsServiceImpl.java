package service.impl;

import common.enums.DaoEnum;
import common.enums.ResultType;
import common.enums.WritingType;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetPostsStrategyImpl;
import common.strategy.impl.comment.GetOnlyCommentByLike;
import common.util.FileUtil;
import dao.CommentDao;
import dao.PostsDao;
import dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.vo.CommentVo;
import pojo.bean.WritingBean;
import pojo.bo.EsBo;
import pojo.dto.CommentDto;
import pojo.dto.WritingDto;
import pojo.po.Posts;
import pojo.po.User;
import service.WritingService;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/20
 */
public class PostsServiceImpl implements WritingService<Posts> {

    private Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");
    @Autowired
    private PostsDao postsDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public Long publishNewWriting(Posts posts) {
        logger.trace("发表新问贴");
        logger.debug(posts.toString());

        //添加问贴
        postsDao.createWritingInfo(posts);
        Long maxId = posts.getId();
        logger.debug("maxId = " + maxId);
        return maxId;
    }

    @Override
    public WritingBean<Posts> getWritingBean(Long writingId, Long userid) {
        logger.trace("获取问贴");
        Posts posts = postsDao.getWritingById(writingId);

        User userInfo = userDao.getImgAndNicknameById(posts.getAuthorId());
        byte[] imgStream = new byte[0];
        try {
            imgStream = FileUtil.getFileStream(userInfo.getAvatarPath());
        } catch (IOException e) {
            logger.error("获取问贴时用户头像转码异常",e);
            return null;
        }
        String imgByBase64 = FileUtil.getImgByBase64(imgStream);

        WritingBean<Posts> bean = new WritingBean<>();
        bean.setUserImg(imgByBase64);
        bean.setWriting(posts);
        bean.setUserNickname(userInfo.getNickname());

        if (userid != null) {

        }

        return bean;
    }

    @Override
    public List<WritingDto<Posts>> getWritingList(Long userid, int partition, String order) {
        GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl(postsDao));
        //判断排序方式
        List<Posts> postsList = null;
        try {
            if (DaoEnum.ORDER_BY_TIME.equals(order)) {
                postsList = choose.getByTime(partition);
            } else if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
                postsList = choose.getByLike(partition);
            }
            if (postsList == null) {
                throw new Exception("获取列表结果为null");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        List<WritingDto<Posts>> wdList = new ArrayList<>();
        for (Posts posts : postsList) {

            WritingBean<Posts> bean = this.getWritingBeanWithAuthorInfo(userDao, posts);

            //获取评论
            CommentChoose commentChoose = new CommentChoose(new GetOnlyCommentByLike());
            CommentVo commentVo = new CommentVo();
            commentVo.setCommentDao(commentDao);
            commentVo.setUserDao(userDao);
            commentVo.setCommentTableName(Posts.class);
            commentVo.setUserid(userid);
            //评论的问贴的id
            commentVo.setParentId(posts.getId());
            //获取多条评论的Dto数据
            List<CommentDto> commentDtos = null;
            commentDtos = commentChoose.doGet(commentVo);

            //封装问贴数据及其评论数据
            WritingDto<Posts> writingDto = new WritingDto<>();
            writingDto.setCommentDtoList(commentDtos);
            writingDto.setWritingBean(bean);

            wdList.add(writingDto);
        }
        return wdList;
    }

    private WritingBean<Posts> getWritingBeanWithAuthorInfo(UserDao userDao, Posts posts) {
        User reviewerInfo = userDao.getImgAndNicknameById(posts.getAuthorId());
        WritingBean<Posts> bean = new WritingBean<>();
        //用户头像 使用base64转码
        byte[] imgStream = new byte[0];
        try {
            imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
        } catch (IOException e) {
            logger.error("获取问贴列表时用户头像转码异常",e);
            return null;
        }
        String imgByBase64 = FileUtil.getImgByBase64(imgStream);

        bean.setWriting(posts);
        bean.setUserNickname(reviewerInfo.getNickname());
        bean.setUserImg(imgByBase64);
        return bean;
    }

    @Override
    public List<Posts> getSimpleWritingList(int partition, String order) {
        GetWritingListChoose<Posts> choose = new GetWritingListChoose<>(new GetPostsStrategyImpl(postsDao));

        List<Posts> resList = null;
        if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
            resList = choose.getSimpleByLike(partition);
        } else if (DaoEnum.ORDER_BY_TIME.equals(order)) {
            resList = choose.getSimpleByTime(partition);
        }
        return resList;
    }

    @Override
    public ResultType updateWriting(Posts posts) {
        logger.trace("修改问贴");
        int i = postsDao.updateWritingInfo(posts);
        logger.debug(String.valueOf(i));
        return i == 1 ? ResultType.SUCCESS : ResultType.NOT_AUTHOR;
    }

    @Override
    public ResultType deleteWriting(Long writingId, Long deleterId) {
        logger.trace("删除问贴");
        int i = postsDao.deleteWritingById(writingId, deleterId);
        logger.debug(String.valueOf(i));
        return i == 1 ? ResultType.SUCCESS : ResultType.NOT_AUTHOR;
    }

    @Override
    public List<Long> getAllWritingsId() {
        return postsDao.getAllWritingsId();
    }

    @Override
    public List<EsBo> getWritingListByIds(List<Long> ids) {
        List<EsBo> writings = null;
        if (ids.size() != 0) {
            writings = postsDao.getWritingsByIds(ids);

            for (EsBo esBo : writings) {
                esBo.setWritingType(WritingType.POSTS.val());
            }
        }
        return writings;
    }
}
