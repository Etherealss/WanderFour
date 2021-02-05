package pojo.bo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author 寒洲
 * @description 分页类
 * @date 2020/10/22
 */
public class PageBo<T> {
	/** 总记录数 */
	@JSONField(ordinal = 0)
	private Long totalCount;

	/** 总页码 */
	@JSONField(ordinal = 0)
	private int totalPage;

	/** 当前页码 */
	@JSONField(ordinal = 0)
	private int currentPage;

	/** 每一页显示的记录数 */
	@JSONField(ordinal = 0)
	private int rows;

	/** 每页的数据 */
	@JSONField(ordinal = 1)
	private List<T> list;

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

	public PageBo() {
	}

	/**
	 * @param currentPage 当前页码
	 * @param rows        每一页显示的记录数
	 */
	public PageBo(int currentPage, int rows) {
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
	public PageBo(Long totalCount, int totalPage, List<T> list, int currentPage, int rows) {
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
