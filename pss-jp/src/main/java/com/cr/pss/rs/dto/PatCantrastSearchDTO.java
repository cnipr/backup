package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.PatentInfo;

public class PatCantrastSearchDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = 8676881424819804997L;
	
	/** 概览需要的记录列表 */
	private List<PatentInfo> patentInfoList = null;
	/** 对比记录数 */
	private long recordCount;

	public List<PatentInfo> getPatentInfoList() {
		return patentInfoList;
	}

	public void setPatentInfoList(List<PatentInfo> patentInfoList) {
		this.patentInfoList = patentInfoList;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	
	
}
