package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.DefaultValue;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "MET_TB_REFE_PL_CN")
@Transactional
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CnReferencesInfo implements Serializable {
	// 申请号
	private String applicationNumber;
	// 公布号
	private String citedPubNumber = "";
	// 公布日期
	private String citedPubDate = "";
	// 文献标记
	private String citedType = "";
	//
	private String citedPatent = "";
	// 公开（公告）号
	private String publicationNumber;
	// 公开（公告）日
	private String publicationDate;
	// 被引证文献号码类型
	private String citedNumType = "";
	// 被引证专利文献名称
	private String citedTi = "";
	// 被引证专利文献分类号
	private String citedIpc = "";	
	// 被引证专利文献申请人
	private String citedPa = "";
	// 被引证文献国别
	private String citedCo = "";	
	// 被引证文献流水号
	private String citedN = "";	
	// 标准被引证文献流水号
	private String citedSn = "";	
	// 被引证文献类型
	private String citedNk = "";	

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

	@DefaultValue("")
	public String getCitedPubNumber() {
		return citedPubNumber;
	}

	public void setCitedPubNumber(String citedPubNumber) {
		this.citedPubNumber = citedPubNumber;
	}

	@DefaultValue("")
	public String getCitedPubDate() {
		return citedPubDate;
	}

	public void setCitedPubDate(String citedPubDate) {
		this.citedPubDate = citedPubDate;
	}

	@DefaultValue("")
	public String getCitedType() {
		return citedType;
	}

	public void setCitedType(String citedType) {
		this.citedType = citedType;
	}

	@DefaultValue("")
	public String getCitedPatent() {
		return citedPatent;
	}

	public void setCitedPatent(String citedPatent) {
		this.citedPatent = citedPatent;
	}

	@DefaultValue("")
	public String getPublicationNumber() {
		return publicationNumber;
	}

	public void setPublicationNumber(String publicationNumber) {
		this.publicationNumber = publicationNumber;
	}

	@DefaultValue("")
	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getCitedNumType() {
		return citedNumType;
	}

	public void setCitedNumType(String citedNumType) {
		this.citedNumType = citedNumType;
	}

	public String getCitedTi() {
		return citedTi;
	}

	public void setCitedTi(String citedTi) {
		this.citedTi = citedTi;
	}

	public String getCitedIpc() {
		return citedIpc;
	}

	public void setCitedIpc(String citedIpc) {
		this.citedIpc = citedIpc;
	}

	public String getCitedPa() {
		return citedPa;
	}

	public void setCitedPa(String citedPa) {
		this.citedPa = citedPa;
	}

	public String getCitedCo() {
		return citedCo;
	}

	public void setCitedCo(String citedCo) {
		this.citedCo = citedCo;
	}

	public String getCitedN() {
		return citedN;
	}

	public void setCitedN(String citedN) {
		this.citedN = citedN;
	}

	public String getCitedSn() {
		return citedSn;
	}

	public void setCitedSn(String citedSn) {
		this.citedSn = citedSn;
	}

	public String getCitedNk() {
		return citedNk;
	}

	public void setCitedNk(String citedNk) {
		this.citedNk = citedNk;
	}
}
