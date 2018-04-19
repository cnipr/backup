package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.rs.dto.search.SectionInfo;

/**
 * 概览检索结果
 * @author lq
 *
 */
@XmlRootElement
public class OverviewSearchDTO extends AbstractSearchDTO implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1L;

	/** 过滤后记录数 */
	private long recordCount;

	/** 概览需要的记录列表 */
	private List<PatentInfo> patentInfoList = null;

	/** 命中记录分布的数据库信息，包括数据库名和该数据库命中的专利数量 */
	private List<SectionInfo> sectionInfos = null;
	
	/** 过滤前记录数（如国外，既要显示所有频道的记录数，
	 * 又要显示某一个频道的记录数，这个属性指的是前面情况的记录数）*/
	private long unfilterTotalCount;
	
	public long getUnfilterTotalCount() {
		return unfilterTotalCount;
	}

	public void setUnfilterTotalCount(long unfilterTotalCount) {
		this.unfilterTotalCount = unfilterTotalCount;
	}
	
	/**
	 * 智能提示词
	 * @return
	 */
	private String[] wordTips;
	/**
	 * 智能检索上一次的检索条件
	 */
	private String trsLastWhere;
	/**
	 * 智能检索相关概念
	 * @return
	 */
	private String relevance;
	/**
	 * 检索条件的关键词
	 * @return
	 */
	private String patentWords;
	
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

	public List<SectionInfo> getSectionInfos() {
		return sectionInfos;
	}

	public void setSectionInfos(List<SectionInfo> sectionInfos) {
		this.sectionInfos = sectionInfos;
	}

	public String[] getWordTips() {
		return wordTips;
	}

	public void setWordTips(String[] wordTips) {
		this.wordTips = wordTips;
	}

	public String getTrsLastWhere() {
		return trsLastWhere;
	}

	public void setTrsLastWhere(String trsLastWhere) {
		this.trsLastWhere = trsLastWhere;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public String getPatentWords() {
		return patentWords;
	}

	public void setPatentWords(String patentWords) {
		this.patentWords = patentWords;
	}
}
