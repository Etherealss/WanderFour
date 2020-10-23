package service;

import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.po.Writing;

import java.util.List;

/**
 * @author 寒洲
 * @description 评论service
 * @date 2020/10/23
 */
public interface CommentService {

	/**
	 * 获取文章下方的评论
	 * @param currentPage
	 * @param commentRows
	 * @param replyRows
	 * @param parentId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<CommentDto> getHotCommentList(int currentPage, int commentRows, int replyRows,
	                                   Long parentId, Long userId) throws Exception;

	/**
	 * 按分页获取页面所有评论
	 * @param currentPage
	 * @param commentRows
	 * @param replyRows
	 * @param orderBy
	 * @param parentId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	PageBean<CommentDto> getCommentListByPage(int currentPage, int commentRows, int replyRows, String orderBy,
	                                          Long parentId, Long userId) throws Exception;

}
