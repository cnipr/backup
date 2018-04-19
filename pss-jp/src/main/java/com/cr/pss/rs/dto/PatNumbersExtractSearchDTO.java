package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.ExtractInfo;

/**
 * 号单检索结果
 * 
 * @author lq
 * 
 */
public class PatNumbersExtractSearchDTO extends AbstractSearchDTO implements
		Serializable {
	private static final long serialVersionUID = -6618129562027859967L;

	// private String numberList;
	private List<ExtractInfo> extractInfoList;
	private Long totalCount;

	// public String getNumberList() {
	// return numberList;
	// }
	//
	// public void setNumberList(String numberList) {
	// this.numberList = numberList;
	// }
	public List<ExtractInfo> getExtractInfoList() {
		return extractInfoList;
	}

	public void setExtractInfoList(List<ExtractInfo> extractInfoList) {
		this.extractInfoList = extractInfoList;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
