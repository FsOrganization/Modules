package com.fla.common.page;

import java.io.Serializable;

public final class PageAttribute implements Serializable {
	private static final long serialVersionUID = -5623534037111390821L;
	@SuppressWarnings("unused")
	private static final int DEFAULT_PAGE_NO = 1;
	private int DEFAULT_PAGE_SIZE;
	private int pageNo;
	private int pageSize;
	private int startIndex;

	public PageAttribute() {
	}

	public PageAttribute(int pn, int psize, int definePageSize) {
		this.DEFAULT_PAGE_SIZE = this.pageSize;
		this.pageNo = getPageNo(pn);
		this.pageSize = getPageSize(psize);
		this.startIndex = ((this.pageNo - 1) * this.pageSize);
	}

	protected int getPageNo(int pn) {
		return pn > 0 ? pn : 1;
	}

	protected int getPageSize(int psize) {
		return psize > 0 ? psize : this.DEFAULT_PAGE_SIZE;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartIndex() {
		return this.startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
}
