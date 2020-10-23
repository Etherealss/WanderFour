package pojo.dto;

import pojo.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 评论Dto
 * 当用户浏览文章（帖子）的评论时会获取评论以及评论的三条回复，包装在这个类中返回
 * @date 2020/10/22
 */
public class CommentDto {

	/**
	 * 顶层评论
	 */
	private CommentBean parentComment;

	/**
	 * 评论的回复
	 */
	private List<CommentBean> replys = new ArrayList<>();

	public CommentBean getParentComment() {
		return parentComment;
	}

	public void setParentComment(CommentBean parentComment) {
		this.parentComment = parentComment;
	}

	public List<CommentBean> getReplys() {
		return replys;
	}

	public void setReplys(List<CommentBean> replys) {
		this.replys = replys;
	}

	/**
	 * 添加回复实例
	 * @param reply
	 */
	public void addReplys(CommentBean reply){
		this.replys.add(reply);
	}

	public CommentDto(CommentBean parentComment, List<CommentBean> replys) {
		this.parentComment = parentComment;
		this.replys = replys;
	}

	public CommentDto() {
	}

	@Override
	public String toString() {
		return "CommentDto{" +
				"\n\tparentComment=" + parentComment +
				", \n\treplys=" + replys +
				"\n}";
	}
}
