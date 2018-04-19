package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

public class PatNumNoSysidExtractSearchDTO extends AbstractSearchDTO implements
		Serializable {
	private static final long serialVersionUID = -6618129562027859968L;

	private List<Object[]> extractInfoList;
	private Long totalCount;

	public List<Object[]> getExtractInfoList() {
		return extractInfoList;
	}

	public void setExtractInfoList(List<Object[]> extractInfoList) {
		this.extractInfoList = extractInfoList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
}
