package org.basilisk.dto.response;

import java.util.List;

public class PagedResponseDTO<T> {
	List<T> items;
	private int pageIndex;
	private int pageSize;
	private long totalCount;
	private int totalPages;
	
	public PagedResponseDTO() {}
	
	public PagedResponseDTO(List<T> items, int pageIndex, int pageSize, long totalCount, int totalPages) {
		this.items = items;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	
	
}
