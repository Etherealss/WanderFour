package pojo.bean;

import java.util.List;

/**
 * @author 寒洲
 * @description 分页类
 * @date 2020/10/22
 */
public class PageBean<T> {
	/** 总记录数 */
	private Long totalCount;
	/** 总页码 */
	private int totalPage;
	/** 每页的数据 */
	private List<T> list;
	/** 当前页码 */
	private int currentPage;
	/** 每一页显示的记录数 */
	private int rows;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public PageBean() {
	}

	/**
	 * @param currentPage 当前页码
	 * @param rows        每一页显示的记录数
	 */
	public PageBean(int currentPage, int rows) {
		this.currentPage = currentPage;
		this.rows = rows;
	}

	/**
	 * @param totalCount  总记录数
	 * @param totalPage   总页码
	 * @param list        每一页的数据
	 * @param currentPage 当前页码
	 * @param rows        每一页显示的记录数
	 */
	public PageBean(Long totalCount, int totalPage, List<T> list, int currentPage, int rows) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.list = list;
		this.currentPage = currentPage;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageBean{" +
				"totalCount=" + totalCount +
				", totalPage=" + totalPage +
				", currentPage=" + currentPage +
				", rows=" + rows +
				", list=" + list +
				'}';
	}
}
