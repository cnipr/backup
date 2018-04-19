package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.PatClassInfo;

public class ClassSearchDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = 6587791169154404624L;
	
	private List<PatClassInfo> classInfoList;

	public List<PatClassInfo> getClassInfoList() {
		return classInfoList;
	}

	public void setClassInfoList(List<PatClassInfo> classInfoList) {
		this.classInfoList = classInfoList;
	}

	
}
