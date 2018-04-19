package com.cnipr.pss.rs.dto.search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.entity.PssEntity;

@Entity
@Table(name = "MET_TB_REFE_PL_CN")
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EnReferencesInfo{
	// 申请号
	private String applicationNumber;
	// 公布号
	private String citedPubNumber;
	// 公布日期
	private String citedPubDate;
	// 文献标记
	private String citedType;
	//
	private String citedPatent;
	// 公开（公告）号
	private String publicationNumber;
	// 公开（公告）日
	private String publicationDate;
	protected String id;

	@Id
	@Column(name = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getCitedPubNumber() {
		return citedPubNumber;
	}

	public void setCitedPubNumber(String citedPubNumber) {
		this.citedPubNumber = citedPubNumber;
	}

	public String getCitedPubDate() {
		return citedPubDate;
	}

	public void setCitedPubDate(String citedPubDate) {
		this.citedPubDate = citedPubDate;
	}

	public String getCitedType() {
		return citedType;
	}

	public void setCitedType(String citedType) {
		this.citedType = citedType;
	}

	public String getCitedPatent() {
		return citedPatent;
	}

	public void setCitedPatent(String citedPatent) {
		this.citedPatent = citedPatent;
	}

	public String getPublicationNumber() {
		return publicationNumber;
	}

	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
}
