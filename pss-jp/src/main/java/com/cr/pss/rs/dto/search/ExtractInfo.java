package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "MET_TB_PATENT")
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ExtractInfo implements Serializable {

	private static final long serialVersionUID = -6337555659437614370L;

	private String sysId;

	private String appId;

	private String an;

	private String pn;
	
	private String tb;

	@Column(name = "TABLE_FLAG")
	public String getTb() {
		return tb;
	}

	public void setTb(String tb) {
		this.tb = tb;
	}

	@Id
	@Column(name = "SYSID")
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	@Column(name = "APP_ID")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Column(name = "APPLICATION_NUMBER")
	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	@Column(name = "PUBLICATION_NUMBER")
	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

}
