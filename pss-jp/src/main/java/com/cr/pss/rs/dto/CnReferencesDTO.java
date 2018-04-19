package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.CnReferencesInfo;

public class CnReferencesDTO extends AbstractSearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<CnReferencesInfo> scyyzzlList;
	private List<CnReferencesInfo> scyyzfzlList;
	private List<CnReferencesInfo> sqryzzlList;
	private List<CnReferencesInfo> scybyzzlList;
	private List<CnReferencesInfo> sqrbyzzlList;

	public List<CnReferencesInfo> getScyyzzlList() {
		return scyyzzlList;
	}

	public void setScyyzzlList(List<CnReferencesInfo> scyyzzlList) {
		this.scyyzzlList = scyyzzlList;
	}

	public List<CnReferencesInfo> getScyyzfzlList() {
		return scyyzfzlList;
	}

	public void setScyyzfzlList(List<CnReferencesInfo> scyyzfzlList) {
		this.scyyzfzlList = scyyzfzlList;
	}

	public List<CnReferencesInfo> getSqryzzlList() {
		return sqryzzlList;
	}

	public void setSqryzzlList(List<CnReferencesInfo> sqryzzlList) {
		this.sqryzzlList = sqryzzlList;
	}

	public List<CnReferencesInfo> getScybyzzlList() {
		return scybyzzlList;
	}

	public void setScybyzzlList(List<CnReferencesInfo> scybyzzlList) {
		this.scybyzzlList = scybyzzlList;
	}

	public List<CnReferencesInfo> getSqrbyzzlList() {
		return sqrbyzzlList;
	}

	public void setSqrbyzzlList(List<CnReferencesInfo> sqrbyzzlList) {
		this.sqrbyzzlList = sqrbyzzlList;
	}
}
