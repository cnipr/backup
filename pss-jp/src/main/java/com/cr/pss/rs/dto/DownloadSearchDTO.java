package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 概览检索结果
 * 
 * @author dj
 * 
 */
@XmlRootElement
public class DownloadSearchDTO extends AbstractSearchDTO implements
		Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 过滤后记录数 */
	private long recordCount;

	/** 概览需要的记录列表 */
	private List<String[]> sysIdList = null;

	public List<String[]> getSysIdList() {
		return sysIdList;
	}

	public void setSysIdList(List<String[]> sysIdList) {
		this.sysIdList = sysIdList;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

}
