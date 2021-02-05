package service;

import common.enums.ResultType;
import pojo.vo.CommentVo;
import pojo.bo.PageBo;
import pojo.dto.CommentDto;
import pojo.po.Comment;

/**
 * @author 寒洲
 * @description 评论service
 * @date 2020/10/23
 */
public interface CommentService {


	/**
	 * 获取文章下方的评论
	 * @param parentId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	PageBo<CommentDto> getHotCommentList(Long parentId, Long userId) throws Exception;

	/**
	 * 按分页获取页面所有评论
	 *
	 * @param vo 参数包
	 *           需要提供 userid parentId commentRows replyRows orderBy
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	PageBo<CommentDto> getCommentListByPage(CommentVo vo, int currentPage) throws Exception;

	/**
	 * 发表新评论/回复
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	ResultType publishNewComment(Comment comment) throws Exception;

	/**
	 * 删除评论/回复
	 *
	 * @param commentId
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	ResultType deleteComment(Long commentId, Long userid) throws Exception;
}
