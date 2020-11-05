package pojo.dto;

import pojo.bean.CommentBean;
import pojo.bean.WritingBean;
import pojo.po.Writing;

import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/11/4
 */
public class WritingDto<T extends Writing> {
	private WritingBean<T> writingBean;

	private List<CommentDto> commentDtoList;

	public WritingDto(WritingBean<T> writingBean, List<CommentDto> commentDtoList) {
		this.writingBean = writingBean;
		this.commentDtoList = commentDtoList;
	}

	public WritingDto() {
	}

	@Override
	public String toString() {
		return "WritingDto{" +
				"writingBean=" + writingBean +
				", commentDtoList=" + commentDtoList +
				'}';
	}

	public WritingBean<T> getWritingBean() {
		return writingBean;
	}

	public void setWritingBean(WritingBean<T> writingBean) {
		this.writingBean = writingBean;
	}

	public List<CommentDto> getCommentDtoList() {
		return commentDtoList;
	}

	public void setCommentDtoList(List<CommentDto> commentDtoList) {
		this.commentDtoList = commentDtoList;
	}
}
