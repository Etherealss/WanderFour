package service.impl;

import common.factory.DaoFactory;
import common.strategy.choose.CommentDtoChoose;
import common.strategy.impl.GetDtoList.GetHeadCommentsByLike;
import common.strategy.impl.GetDtoList.GetCommentsByTime;
import common.util.JdbcUtil;
import dao.CommentDao;
import org.apache.log4j.Logger;
import pojo.CommentVo;
import pojo.bean.PageBean;
import pojo.dto.CommentDto;
import pojo.po.Writing;
import service.CommentService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

/**
 * @author 寒洲
 * @description
 * @date 2020/10/23
 */
public class CommentServiceImpl implements CommentService {

	private Logger logger = Logger.getLogger(CommentServiceImpl.class);

	private static final String ORDER_BY_LIKE = "like";
	private static final String ORDER_BY_TIME = "time";

	private CommentDao dao;

	/**
	 * @param clazz
	 */
	public CommentServiceImpl(Class<? extends Writing> clazz) {
		//根据泛型获取对应数据库表的DAO
		dao = DaoFactory.getCommentDao(clazz);
	}

	@Override
	public List<CommentDto> getHotCommentList(int currentPage, int commentRows, int replyRows, Long parentId, Long userId) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		//选择策略 按点赞数获取热门评论及其回复
		CommentDtoChoose choose;
		choose = new CommentDtoChoose(new GetHeadCommentsByLike());
		//获取评论DtoList
		CommentVo vo = new CommentVo();
		vo.setParentId(parentId);
		vo.setUserid(userId);
		vo.setConn(conn);
		vo.setDao(dao);
		//策略 获取所有评论数据
		List<CommentDto> list = choose.getCommentDtoList(vo);
		return list;
	}

	@Override
	public PageBean<CommentDto> getCommentListByPage(int currentPage, int commentRows, int replyRows, String orderBy,
	                                                 Long parentId, Long userId) throws Exception {
		Connection conn = JdbcUtil.getConnection();
		//存入当前页码和每页显示的记录数
		PageBean<CommentDto> pb = new PageBean<>(currentPage, commentRows);
		//获取并存入总记录数
		Long totalCount = dao.countCommentByParentId(conn, parentId);
		pb.setTotalCount(totalCount);
		//计算索引 注意在 -1 的时候加入long类型，使结果升格为Long
		Long start = (currentPage - 1L) * commentRows;
		//获取评论DtoList
		CommentDtoChoose choose = new CommentDtoChoose(new GetCommentsByTime());
		CommentVo vo = new CommentVo(conn, dao, orderBy, start, commentRows, replyRows, parentId, userId);
		List<CommentDto> list = choose.getCommentDtoList(vo);
		pb.setList(list);
		/*
		计算总页码数
		如果总记录数可以整除以每页显示的记录数，
		那么总页数就是它们的商否则
		说明有几条数据要另开一页显示，总页数+1
		 */
		int page = (int) (totalCount / commentRows);
		int totalPage = totalCount % commentRows == 0 ? page : page + 1;
		pb.setTotalPage(totalPage);
		return pb;
	}

}
