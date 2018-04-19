package com.cnipr.pss.rs.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.cnipr.pss.rs.dto.search.PatentInfo;

/**
 * 细缆DTO
 */
public class DetailSearchDTO extends AbstractSearchDTO implements Serializable{

	private static final long serialVersionUID = 6803468439256092390L;

	/** 检索的总记录数 */
	private long recordCount;

	/**  检索的专利信息列表 */
	private PatentInfo patentInfo = null;

	/**  数据库/视图信息列表 */
	private String sectionName = null;
	
//	public DetailSearchDTO() {
//	}
	
//	//审查员引证专利文献
//	private List<YzwsInfo> scyyzzlList = null;
//	
//	//审查员引证非专利文献
//	private List<YzwsInfo> scyyzfzlList = null;
//	
//	//申请人引证专利文献
//	private List<YzwsInfo> sqryzzlList = null;
//	
//	//审查员被引证专利文献
//	private List<YzwsInfo> scybyzzlList = null;
//	
//	//申请人被引证专利文献
//	private List<YzwsInfo> sqrbyzzlList = null;
//	
//	public List<YzwsInfo> getScyyzzlList() {
//		return scyyzzlList;
//	}
//
//	public void setScyyzzlList(List<YzwsInfo> scyyzzlList) {
//		this.scyyzzlList = scyyzzlList;
//	}
//
//	public List<YzwsInfo> getScyyzfzlList() {
//		return scyyzfzlList;
//	}
//
//	public void setScyyzfzlList(List<YzwsInfo> scyyzfzlList) {
//		this.scyyzfzlList = scyyzfzlList;
//	}
//
//	public List<YzwsInfo> getSqryzzlList() {
//		return sqryzzlList;
//	}
//
//	public void setSqryzzlList(List<YzwsInfo> sqryzzlList) {
//		this.sqryzzlList = sqryzzlList;
//	}
//
//	public List<YzwsInfo> getScybyzzlList() {
//		return scybyzzlList;
//	}
//
//	public void setScybyzzlList(List<YzwsInfo> scybyzzlList) {
//		this.scybyzzlList = scybyzzlList;
//	}
//
//	public List<YzwsInfo> getSqrbyzzlList() {
//		return sqrbyzzlList;
//	}
//
//	public void setSqrbyzzlList(List<YzwsInfo> sqrbyzzlList) {
//		this.sqrbyzzlList = sqrbyzzlList;
//	}
//
//	// 外观公报
//	private WGGBInfo wggbInfo = null;
//
//	public WGGBInfo getWggbInfo() {
//		return wggbInfo;
//	}
//
//	public void setWggbInfo(WGGBInfo wggbInfo) {
//		this.wggbInfo = wggbInfo;
//	}

	public PatentInfo getPatentInfo() {
		return patentInfo;
	}

	public void setPatentInfo(PatentInfo patentInfo) {
		this.patentInfo = patentInfo;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
}
