package pojo.dto;

import pojo.bean.CommentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 寒洲
 * @description 回复的Dto
 * 在用户点击“查看（评论的）回复”后页面将请求多条回复，包装在这个类中返回
 * @date 2020/10/22
 */
public class ReplyDto {

	/**
	 * 评论的回复
	 */
	private CommentBean replys;

	public ReplyDto() {
	}

	public ReplyDto(CommentBean replys) {
		this.replys = replys;
	}

	public CommentBean getReplys() {
		return replys;
	}

	public void setReplys(CommentBean replys) {
		this.replys = replys;
	}
}
