package service.impl;

import common.enums.*;
import common.strategy.choose.CommentChoose;
import common.strategy.choose.GetWritingListChoose;
import common.strategy.impl.GetArticleStrategyImpl;
import common.strategy.impl.comment.GetOnlyCommentByLike;
import common.util.FileUtil;
import dao.*;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.vo.CommentVo;
import pojo.bean.WritingBean;
import pojo.bo.EsBo;
import pojo.dto.CommentDto;
import pojo.dto.WritingDto;
import pojo.po.Article;
import pojo.po.LikeRecord;
import pojo.po.User;
import service.WritingService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/7
 */
public class ArticleServiceImpl implements WritingService<Article> {

    private Logger logger = Logger.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Long publishNewWriting(Article article) {
        logger.trace("发表新文章");
        logger.debug(article);

        //添加文章信息
        articleDao.createWritingInfo(article);

        // MyBatis将新插入的id封装在pojo对象的字段中
        Long maxId = article.getId();
        logger.debug("maxId = " + maxId);
        article.setId(maxId);
        //添加文章内容
        articleDao.createWritingContent(maxId, article.getContent());

        return maxId;
    }

    @Override
    public WritingBean<Article> getWritingBean(Long writingId, Long userid) {
        logger.trace("获取文章");
        Article article = articleDao.getWritingById(writingId);
        if (article == null) {
            return null;
        }
        //获取文章信息包
        logger.debug(article);
        WritingBean<Article> bean = this.getWrticleBeanWithAuthorInfo(article);

        //userid可能为null
        if (userid != null) {
            //判断是否为作者
            bean.setAuthor(article.getAuthorId().equals(userid));
            //判断是否已点赞
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setUserid(userid);
            likeRecord.setTargetType(TargetType.ARTICLE);
            likeRecord.setTargetId(writingId);


            int isLike = likeDao.countUserLikeRecord(
                    TargetType.getLikeTableNameByTargetType(likeRecord.getTargetType()),
                    likeRecord);

            //注意取反
            bean.setLiked(isLike == 1);

            //TODO 判断是否已收藏
        }

        return bean;
    }

    /**
     * @param article
     * @return
     * @throws SQLException
     */
    private WritingBean<Article> getWrticleBeanWithAuthorInfo(Article article) {
        //获取文章的内容
        String content = articleDao.getWritingContent(article.getId());
        article.setContent(content);

        //获取用户的信息
        User reviewerInfo = userDao.getImgAndNicknameById(article.getAuthorId());
        WritingBean<Article> bean = new WritingBean<>();
        //用户头像 使用base64转码
        byte[] imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
        String imgByBase64 = FileUtil.getImgByBase64(imgStream);

        //打包
        bean.setWriting(article);
        bean.setUserNickname(reviewerInfo.getNickname());
        //TODO 设置作品的作者头像数据
        bean.setUserImg(imgByBase64);
        return bean;
    }

    @Override
    public List<WritingDto<Article>> getWritingList(Long userid, int partition, String order) {
        GetWritingListChoose<Article> choose = new GetWritingListChoose<>(new GetArticleStrategyImpl(articleDao));
        //判断排序方式
        List<Article> articleList = null;
        if (DaoEnum.ORDER_BY_TIME.equals(order)) {
            articleList = choose.getByTime(partition);
        } else if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
            articleList = choose.getByLike(partition);
        }

        List<WritingDto<Article>> resList = new ArrayList<>();
        if (articleList == null || articleList.size() == 0) {
            return null;
        }
        for (Article article : articleList) {
            WritingBean<Article> bean = this.getWrticleBeanWithAuthorInfo(article);
            //获取评论
            CommentChoose commentChoose = new CommentChoose(new GetOnlyCommentByLike());
            CommentVo commentVo = new CommentVo();
            commentVo.setCommentDao(commentDao);
            commentVo.setUserDao(userDao);
            commentVo.setCommentTableName(Article.class);
            commentVo.setUserid(userid);
            //评论的文章的id
            commentVo.setParentId(article.getId());
            commentVo.setReplyRows(DaoEnum.ROWS_ZERO);
            //获取多条评论的Dto数据
            List<CommentDto> commentDtos = commentChoose.doGet(commentVo);

            //封装文章数据及其评论数据
            WritingDto<Article> writingDto = new WritingDto<>();
            writingDto.setCommentDtoList(commentDtos);
            writingDto.setWritingBean(bean);

            resList.add(writingDto);
        }
        return resList;
    }

    @Override
    public List<Article> getSimpleWritingList(int partition, String order) {
        GetWritingListChoose<Article> choose = new GetWritingListChoose<>(new GetArticleStrategyImpl(articleDao));

        List<Article> resList = null;
        if (DaoEnum.ORDER_BY_LIKE.equals(order)) {
            resList = choose.getSimpleByLike(partition);
        } else if (DaoEnum.ORDER_BY_TIME.equals(order)) {
            resList = choose.getSimpleByTime(partition);
        }
        return resList;
    }

    @Override
    public ResultType updateWriting(Article article) {
        logger.trace("修改文章");
        int i = articleDao.updateWritingInfo(article);
        if (i == 1) {
            articleDao.updateWritingContent(article.getId(), article.getContent());
            return ResultType.SUCCESS;
        } else {
            return ResultType.NOT_AUTHOR;
        }
    }

    @Override
    public ResultType deleteWriting(Long writingId, @NotNull Long deleterId) {
        logger.trace("删除文章");
        int i = articleDao.deleteWritingById(writingId, deleterId);
        // 实际上，NOT_AUTHOR可能是因为不是作者，也可能是writingId不对，总之都是非法操作导致的
        return i == 1 ? ResultType.SUCCESS : ResultType.NOT_AUTHOR;
    }

    @Override
    public List<Long> getAllWritingsId() {
        return articleDao.getAllWritingsId();
    }

    @Override
    public List<EsBo> getWritingListByIds(List<Long> ids) {
        List<EsBo> writings = null;
        if (ids.size() != 0) {
            writings = articleDao.getWritingsByIds(ids);
            for (EsBo esBo : writings) {
                esBo.setWritingType(WritingType.ARTICLE.val());
                esBo.setContent(articleDao.getWritingContent(esBo.getWritingId()));
            }
        }
        return writings;
    }

}
