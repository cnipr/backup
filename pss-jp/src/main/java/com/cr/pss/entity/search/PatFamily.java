package com.cnipr.pss.entity.search;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cnipr.pss.entity.PssEntity;

@Entity
@Table(name = "MET_TB_FAMILY_COMPLEX")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PatFamily extends PssEntity {
	private String sysid;
	private String familyNumber;
	private String applicationNumber;

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getFamilyNumber() {
		return familyNumber;
	}

	public void setFamilyNumber(String familyNumber) {
		this.familyNumber = familyNumber;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
}
