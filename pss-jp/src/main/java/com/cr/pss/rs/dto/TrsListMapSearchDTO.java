package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrsListMapSearchDTO extends AbstractSearchDTO implements
		Serializable {

	private static final long serialVersionUID = -3133964062964112352L;
	private List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
	
	// 总记录数
	private long totalCount;

	public List<Map<String, String>> getResultList() {
		return resultList;
	}

	public void setResultList(List<Map<String, String>> resultList) {
		this.resultList = resultList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
