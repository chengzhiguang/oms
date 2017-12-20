package com.chengzg.oms.controller.support;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class PageResponse {
	private int total = 0;
    private Integer page = 1;

    private List rows;
    
    
    public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public List getRows() {
		return rows;
	}


	public void setRows(List rows) {
		this.rows = rows;
	}


	@Override
    public String toString() {
    	JSONObject result = new JSONObject();
    	result.put("total", total);
    	result.put("page", page);
    	result.put("rows", rows);
    	return result.toJSONString();
    }
}
