package com.cnipr.pss.entity.search;

import java.io.Serializable;

public class LegalStatusInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8599276230745317355L;

	private long index;
	
	private String strAn;

	private String strStatusInfo;

	private String strLegalStatus;

	private String strLegalStatusDay;

	private String strAccredit;

	public String getStrAccredit() {
		return strAccredit;
	}

	public void setStrAccredit(String strAccredit) {
		this.strAccredit = strAccredit;
	}

	public String getStrLegalStatus() {
		return strLegalStatus;
	}

	public void setStrLegalStatus(String strLegalStatus) {
		this.strLegalStatus = strLegalStatus;
	}

	public String getStrLegalStatusDay() {
		return strLegalStatusDay;
	}

	public void setStrLegalStatusDay(String strLegalStatusDay) {
		this.strLegalStatusDay = strLegalStatusDay;
	}

	public String getStrStatusInfo() {
		return strStatusInfo;
	}

	public void setStrStatusInfo(String strStatusInfo) {
		this.strStatusInfo = strStatusInfo;
	}

	public String getStrAn() {
		return strAn;
	}

	public void setStrAn(String strAn) {
		this.strAn = strAn;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(long index) {
		this.index = index;
	}
}
