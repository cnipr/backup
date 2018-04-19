package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class SectionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1201098482130426508L;

	private String sectionName;
	
	private long recordNum;

	public long getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(long recordNum) {
		this.recordNum = recordNum;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
}
