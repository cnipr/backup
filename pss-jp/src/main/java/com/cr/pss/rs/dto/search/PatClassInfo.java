package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class PatClassInfo implements Serializable {
	private static final long serialVersionUID = 3975017423001558635L;
	private String className;
	private int recordNum;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getRecordNum() {
		return recordNum;
	}

	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
}
