package pojo;

import dao.CommentDao;

import java.sql.Connection;

/**
 * @author 寒洲
 * @description 进行查询评论操作时的Vo，因为各层传递的参数太多了
 * @date 2020/10/23
 */
public class CommentVo {
	/** 数据库连接 */
	private Connection conn;
	/** 评论DAO */
	private CommentDao dao;
	/** 查询数据库的排序方式 */
	private String order;
	/** 记录起始值 */
	private Long start;
	/** 显示的评论行数 */
	private int commentRows;
	/** 显示的回复行数 */
	private int replyRows;
	/** 父容器Id */
	private Long parentId;
	/** 目标Id */
	private Long targetId;
	/** 当前用户Id */
	private Long userid;

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public CommentDao getDao() {
		return dao;
	}

	public void setDao(CommentDao dao) {
		this.dao = dao;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public int getCommentRows() {
		return commentRows;
	}

	public void setCommentRows(int commentRows) {
		this.commentRows = commentRows;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public int getReplyRows() {
		return replyRows;
	}

	public void setReplyRows(int replyRows) {
		this.replyRows = replyRows;
	}

	public CommentVo(Connection conn, CommentDao dao, Long start, int commentRows,
	                 int replyRows, Long parentId, Long targetId, Long userid) {
		this.conn = conn;
		this.dao = dao;
		this.start = start;
		this.commentRows = commentRows;
		this.replyRows = replyRows;
		this.parentId = parentId;
		this.targetId = targetId;
		this.userid = userid;
	}

	public CommentVo(Connection conn, CommentDao dao, Long start, int commentRows,
	                 int replyRows, Long parentId, Long userid) {
		this.conn = conn;
		this.dao = dao;
		this.start = start;
		this.commentRows = commentRows;
		this.replyRows = replyRows;
		this.parentId = parentId;
		this.userid = userid;
	}

	public CommentVo(Connection conn, CommentDao dao, String order, Long start,
	                 int commentRows, int replyRows, Long parentId, Long userid) {
		this.conn = conn;
		this.dao = dao;
		this.order = order;
		this.start = start;
		this.commentRows = commentRows;
		this.replyRows = replyRows;
		this.parentId = parentId;
		this.userid = userid;
	}

	public CommentVo(Connection conn, CommentDao dao, String order, Long start, int commentRows, int replyRows, Long parentId, Long targetId, Long userid) {
		this.conn = conn;
		this.dao = dao;
		this.order = order;
		this.start = start;
		this.commentRows = commentRows;
		this.replyRows = replyRows;
		this.parentId = parentId;
		this.targetId = targetId;
		this.userid = userid;
	}

	public CommentVo() {
	}
}
