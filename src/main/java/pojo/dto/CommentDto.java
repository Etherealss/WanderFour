package pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import pojo.bean.CommentBean;
import pojo.po.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 评论Dto
 * 当用户浏览文章（帖子）的评论时会获取评论以及评论的三条回复，包装在这个类中返回
 * 本来还要ReplyDto，因为发现有些累赘所以删掉了
 * ReplyDto就相当于没有replys的CommentDto，也相当于一个普通的CommentBean
 * @date 2020/10/22
 */
public class CommentDto {

	/**
	 * 顶层评论 或者是 一条回复记录
	 */
	@JSONField(ordinal = 0)
	private CommentBean parentComment;

	/**
	 * 回复的引用
	 */
	@JSONField(ordinal = 2)
	private CommentBean beRepliedComment;

	/**
	 * 评论的回复数
	 */
	@JSONField(ordinal = 1)
	private int replysCount;

	/**
	 * 评论的回复列表
	 */
	@JSONField(ordinal = 2)
	private List<CommentBean> replys = new ArrayList<>();


	public CommentBean getParentComment() {
		return parentComment;
	}

	public void setParentComment(CommentBean parentComment) {
		this.parentComment = parentComment;
	}

	public CommentBean getBeRepliedComment() {
		return beRepliedComment;
	}

	public void setBeRepliedComment(CommentBean beRepliedComment) {
		this.beRepliedComment = beRepliedComment;
	}

	public List<CommentBean> getReplys() {
		return replys;
	}

	public void setReplys(List<CommentBean> replys) {
		this.replys = replys;
	}

	public int getReplysCount() {
		return replysCount;
	}

	public void setReplysCount(int replysCount) {
		this.replysCount = replysCount;
	}

	/**
	 * 添加回复实例
	 * @param reply
	 */
	public void addReplys(CommentBean reply) {
		this.replys.add(reply);
	}

	/**
	 * 回复Dto 的构造函数
	 * @param parentComment    回复的数据
	 * @param beRepliedComment 回复对象的数据引用对象beRepliedComment
	 */
	public CommentDto(CommentBean parentComment, CommentBean beRepliedComment) {
		this.parentComment = parentComment;
		this.beRepliedComment = beRepliedComment;
	}

	/**
	 * 评论Dto的构造函数
	 * @param parentComment 评论的数据
	 * @param replys        评论下的多条回复
	 * @param replysCount   该评论下的总回复数量
	 */
	public CommentDto(CommentBean parentComment, List<CommentBean> replys, int replysCount) {
		this.parentComment = parentComment;
		this.replys = replys;
		this.replysCount = replysCount;
	}

	public CommentDto() {
	}

	@Override
	public String toString() {
		return "CommentDto{" +
				"parentComment=" + parentComment +
				", beRepliedComment=" + beRepliedComment +
				", replys=" + replys +
				", replysCount=" + replysCount +
				'}';
	}
}
