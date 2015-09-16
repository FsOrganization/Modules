package com.fla.common.page;

import java.io.Serializable;

public final class Page implements Serializable {
	private static final long serialVersionUID = -7742988063989166270L;
	private int firstPage;
	private int lastPage;
	private int nextPage;
	private int prevPage;
	private int currentPage;
	private int totalPage;
	private int rowCount;
	private int pageSize;
	private boolean hasNext;
	private boolean hasPrev;
	private boolean hasFirst;
	private boolean hasLast;

	public Page() {
	}

	public Page(int _rowCount, int pageSize, int _currentPage) {
		this.rowCount = _rowCount;
		this.currentPage = _currentPage;
		this.pageSize = pageSize;
		this.totalPage = (this.rowCount % pageSize == 0 ? this.rowCount / pageSize : this.rowCount / pageSize + 1);
		if (this.totalPage > 0) 
		{
			this.hasFirst = true;
			this.firstPage = 1;
		}
		if (this.currentPage > 1) 
		{
			this.hasPrev = true;
			this.prevPage = (this.currentPage - 1);
		}
		if ((this.totalPage > 0) && (this.currentPage < this.totalPage)) 
		{
			this.hasNext = true;
			this.nextPage = (this.currentPage + 1);
		}
		if (this.totalPage > 0) 
		{
			this.hasLast = true;
			this.lastPage = this.totalPage;
		}
	}

	public int getFirstPage() {
		return this.firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return this.lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getNextPage() {
		return this.nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrevPage() {
		return this.prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isHasNext() {
		return this.hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrev() {
		return this.hasPrev;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public boolean isHasFirst() {
		return this.hasFirst;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public boolean isHasLast() {
		return this.hasLast;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}
}
