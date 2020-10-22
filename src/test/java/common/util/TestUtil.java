package common.util;

import common.enums.Partition;
import pojo.bean.CommentBean;
import pojo.dto.CommentDto;
import pojo.dto.ReplyDto;
import pojo.po.Article;
import pojo.po.Comment;
import pojo.po.Posts;

import java.util.Date;
import java.util.Random;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/15
 */
public class TestUtil {

	public static Article getDefaultArticlePo(){
		Article writing = new Article();
		writing.setPartition("learning");
		writing.setAuthorId(1L);
		writing.setCategory(1);
		writing.setTitle("TestUtil测试");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		return writing;
	}

	public static Posts getDefaultPostsPo(){
		Posts writing = new Posts();
		writing.setPartition("learning");
		writing.setAuthorId(1L);
		writing.setCategory(1);
		writing.setTitle("TestUtil测试");
		writing.setContent("主要内容");
		writing.setLabel1("标签1");
		writing.setLabel2("标签2");
		return writing;
	}

	private  static Random random = new Random();
	private static Long getRandomLong(){
		long min = 1;
		long max = 10;
		long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
		return rangeLong;
	}

	/**
	 * 评论PO
	 * @return
	 */
	public static Comment getDefaultCommentPo(){

		return new Comment(getRandomLong(), 3L, 1L, null, "评论内容", new Date(), true, 23);
	}

	/**
	 * 回复PO
	 * @return
	 */
	public static Comment getDefaultReply(){
		return new Comment(1L, 3L, 1L, 1L, "回复内容", new Date(), true, 0);
	}

	/**
	 * 评论Bean
	 * @return
	 */
	public static CommentBean getDefaultCommentBean(){
		CommentBean commentBean = new CommentBean();
		commentBean.setComment(getDefaultCommentPo());
		commentBean.setUserImg("用户头像Base64转码");
		commentBean.setUserNickname("张三");
		commentBean.setCanDelete(false);
		return commentBean;
	}

	/**
	 * 回复Bean
	 * @return
	 */
	public static CommentBean getDefaultReplyBean(){
		CommentBean commentBean = new CommentBean();
		commentBean.setComment(getDefaultReply());
		commentBean.setUserImg("用户头像Base64转码");
		commentBean.setUserNickname("张三");
		commentBean.setCanDelete(false);
		return commentBean;
	}

	/**
	 * 回复Bean
	 * @param c
	 * @return
	 */
	public static CommentBean getDefaultReplyBean(Comment c){
		CommentBean commentBean = new CommentBean();
		commentBean.setComment(c);
		commentBean.setUserImg("用户头像Base64转码");
		commentBean.setUserNickname("张三");
		commentBean.setCanDelete(false);
		Comment reply = new Comment(getRandomLong(), 3L, 1L, null, "评论内容", new Date(), true, 23);
		return commentBean;
	}

	/**
	 * 评论Dto
	 * @return
	 */
	public static CommentDto getDeultCommentDto(){
		Comment comment = TestUtil.getDefaultCommentPo();

		CommentBean commentBean = TestUtil.getDefaultCommentBean();

		CommentDto dto = new CommentDto();
		dto.setParentComment(commentBean);

		CommentBean reply1 = TestUtil.getDefaultReplyBean();
		CommentBean reply2 = TestUtil.getDefaultReplyBean();
		CommentBean reply3 = TestUtil.getDefaultReplyBean();
		dto.addReplys(reply1);
		dto.addReplys(reply2);
		dto.addReplys(reply3);
		return dto;
	}

	/**
	 * 回复Dto
	 * @return
	 */
	public static ReplyDto getDeultReplyDto(){
		ReplyDto dto = new ReplyDto();
		//一下是评论1的回复
		Comment comment1 = new Comment(2L, 2L, 1L, 1L,
				"我是回复2，我回复一条评论，评论id是1", new Date(), true, 0);
		Comment comment2 = new Comment(3L, 3L, 1L, 2L,
				"B在评论id为1的评论下，回复另一条回复，那条回复的id是2", new Date(), true, 0);
		Comment comment3 = new Comment(4L, 4L, 1L, 2L,
				"C在评论id为1的评论下，回复另一条回复，那条回复的id是2", new Date(), true, 0);
		CommentBean reply1 = TestUtil.getDefaultReplyBean(comment1);

		CommentBean reply2 = TestUtil.getDefaultReplyBean(comment2);
		reply2.setRelayComment(comment1);
		CommentBean reply3 = TestUtil.getDefaultReplyBean(comment3);
		reply2.setRelayComment(comment1);
		dto.addReplys(reply1);
		dto.addReplys(reply2);
		dto.addReplys(reply3);
		return dto;
	}
}
