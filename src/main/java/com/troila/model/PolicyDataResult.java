package com.troila.model;

import java.util.List;
/**
 * 主要用于接收从外网获取数据时的格式转换
 * @author xuanguojing
 *
 */
public class PolicyDataResult {
	private Integer total;
	
	private List<PolicyExternal> rows;
	
	private String status;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<PolicyExternal> getRows() {
		return rows;
	}

	public void setRows(List<PolicyExternal> rows) {
		this.rows = rows;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PolicyDataResult [total=" + total + ", rows=" + rows + ", status=" + status + "]";
	}
}
