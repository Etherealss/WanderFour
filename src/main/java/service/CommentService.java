package service;

import pojo.CommentVo;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;

import java.util.List;

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
	List<CommentDto> getHotCommentList(Long parentId, Long userId) throws Exception;

	/**
	 * 按分页获取页面所有评论
	 *
	 * @param vo 参数包
	 *           需要提供 userid parentId commentRows replyRows orderBy
	 * @param currentPage
	 * @return
	 * @throws Exception
	 */
	PageBean<CommentDto> getCommentListByPage(CommentVo vo, int currentPage) throws Exception;

}
