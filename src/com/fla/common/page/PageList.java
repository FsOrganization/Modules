package com.fla.common.page;

import java.io.Serializable;
import java.util.List;

public final class PageList<T> implements Serializable
{
	private static final long serialVersionUID = 7636400405542683379L;
	private List<T> datas;
	private Page page;

	public List<T> getDatas() {
		return this.datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public Page getPage() {
		return this.page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
