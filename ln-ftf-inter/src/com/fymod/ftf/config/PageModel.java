package com.fymod.ftf.config;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class PageModel {

	/** 总记录数 **/
	private int total;

	/** 等同于pageSize 返回的最大记录数 **/
	private int maxItems;

	/** 数据列表 **/
	private List datas = new ArrayList();

	public PageModel() {
	}

	public PageModel(List datas, int total, int maxItems) {
		this.datas = datas;
		this.total = total;
		this.maxItems = maxItems;
	}

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPageNumber() {
		int quotient = total / maxItems;
		int mod = total % maxItems;
		if (mod == 0) {
			return quotient;
		}
		return quotient + 1;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

}
