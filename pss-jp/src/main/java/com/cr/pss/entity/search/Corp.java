package com.cnipr.pss.entity.search;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cnipr.pss.entity.PssEntity;

@Entity
@Table(name = "MET_TB_CORPCODE_DIC")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Corp extends PssEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926231164160042345L;

	private String corpname;

	private String corpcode;

	private String corpcode_topap;

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public String getCorpcode() {
		return corpcode;
	}

	public void setCorpcode(String corpcode) {
		this.corpcode = corpcode;
	}

	public String getCorpcode_topap() {
		return corpcode_topap;
	}

	public void setCorpcode_topap(String corpcode_topap) {
		this.corpcode_topap = corpcode_topap;
	}

}
