package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.entity.search.Corp;

public class CorpTreeInfoDTO extends AbstractSearchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926233164160048895L;

	private List<CorpTreeInfoDTO> corpCodeList;

	private long corpCodeTotal;

	private List<CorpDTO> corpList;

	private long corpTotal;

	private boolean isHasChild;

	private String corpCode;

	public List<CorpTreeInfoDTO> getCorpCodeList() {
		return corpCodeList;
	}

	public void setCorpCodeList(List<CorpTreeInfoDTO> corpCodeList) {
		this.corpCodeList = corpCodeList;
	}

	public List<CorpDTO> getCorpList() {
		return corpList;
	}

	public void setCorpList(List<CorpDTO> corpList) {
		this.corpList = corpList;
	}

	public long getCorpCodeTotal() {
		return corpCodeTotal;
	}

	public void setCorpCodeTotal(long corpCodeTotal) {
		this.corpCodeTotal = corpCodeTotal;
	}

	public long getCorpTotal() {
		return corpTotal;
	}

	public void setCorpTotal(long corpTotal) {
		this.corpTotal = corpTotal;
	}

	public boolean isHasChild() {
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild) {
		this.isHasChild = isHasChild;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

}
