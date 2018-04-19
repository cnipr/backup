package com.cnipr.pss.entity.search;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cnipr.pss.entity.PssEntity;
import com.cnipr.pss.entity.PssNoIdEntity;

@Entity
@Table(name = "MET_TB_CORPCODE_TREE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CorpCode extends PssNoIdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926231164160042345L;

	private String corpcode_pap;
	@Id
	private String corpcode;

	private String corpcode_topap;

	private Long corpcode_children;

	public String getCorpcode_pap() {
		return corpcode_pap;
	}

	public void setCorpcode_pap(String corpcode_pap) {
		this.corpcode_pap = corpcode_pap;
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

	public Long getCorpcode_children() {
		return corpcode_children;
	}

	public void setCorpcode_children(Long corpcode_children) {
		this.corpcode_children = corpcode_children;
	}

}
