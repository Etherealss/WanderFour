package common.strategy.remark;

import common.util.ObjectUtil;
import dao.UserDao;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.bean.CommentBean;
import pojo.dto.CommentDto;
import pojo.po.Comment;
import pojo.po.User;

import java.util.List;
import java.util.Objects;

/**
 * @author wtk
 * @description 评论和回复的获取策略，因为打算将评论回复分开，所以这作为抽象父类
 * @date 2021-07-23
 */
public abstract class RemarkQueryStrategy {
    protected Logger logger = LoggerFactory.getLogger("simpleAsyncLogger");

    protected UserDao userDao;
    protected Long userid;

    /**
     * 获取评论或回复的记录数据
     * 模板方法模式中的模板方法
     * @return
     * @param parentId
     */
    public abstract List<CommentDto> getRemarkData(long parentId);

    public RemarkQueryStrategy(UserDao userDao, Long userid) {
        this.userDao = userDao;
        this.userid = userid;
    }

    /**
     * 检查参数是否正确
     */
    protected void checkArguments() {
        Objects.requireNonNull(userDao, "UserDao对象缺失！");
        Objects.requireNonNull(userid, "userid对象缺失！");
    }

    /**
     * 包装评论者的用户昵称和头像（base64转码）
     * @param comment 当前评论的Comment对象
     * @param userid  当前用户
     * @return 包括评论信息、评论者昵称、评论者头像的CommentBean
     */
    public CommentBean wrapCommentBean(@NotNull Comment comment, Long userid) {
        CommentBean cb = new CommentBean();
        //判断是否为当前用户
        Long reviewerUserId = comment.getUserid();
        //userid可以为null
        cb.setCanDelete(reviewerUserId.equals(userid));


        //获取评论者的用户信息
		/*
		注意！函数参数的userid是当前用户的userid，是用来判断评论能否删除的
		而comment的userid是评论者的userid，此处要封装评论者的用户信息（reviewerInfo）
		需要用到的是reviewerUserId
		 */
        User reviewerInfo = userDao.getImgAndNicknameById(reviewerUserId);
        cb.setUserNickname(reviewerInfo.getNickname());
        // TODO 用户头像
        //使用base64转码
//        byte[] imgStream;
//        try {
//            imgStream = FileUtil.getFileStream(reviewerInfo.getAvatarPath());
//            String imgByBase64 = FileUtil.getImgByBase64(imgStream);
//            cb.setUserImg(imgByBase64);
//        } catch (IOException e) {
//            logger.error("评论图片转码异常", e);
//        }
        cb.setUserImg("用户头像");
        //封装评论信息
        cb.setComment(comment);
        return cb;
    }

}
