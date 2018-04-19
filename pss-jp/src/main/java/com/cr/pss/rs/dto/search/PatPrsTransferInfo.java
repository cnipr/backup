package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

/**
 * 申请专利权转移信息
 * 
 * @author liuqi
 * 
 */
public class PatPrsTransferInfo implements Serializable {

	private static final long serialVersionUID = 7614174322394851882L;
	// 申请号
	private String an;
	// 申请信息
	private String applicantInfo;
	// 法律状态信息
	private String strStatusInfo;
	// 法律状态
	private String strLegalStatus;
	// 法律状态公告日
	private String strLegalStatusDay;
	// 事务代码
	private String eventCode;
	// 名称
	private String ti;
	// 摘要
	private String ab;
	// 主权项
	private String cl;
	// 分类号
	private String ipc;
	// 转移类型
	private String type;
	// 变更前权利人
	private String beforeTransAp;
	// 变更后权利人
	private String afterTransAp;
	// 当前权利人
	private String currentAp;
	// 变更前地址
	private String beforeTransAddr;
	// 变更后地址
	private String afterTransAddr;
	// 当前地址
	private String currentAddr;
	// 区域代码
	private String areaCode;
	// 生效日
	private String effectiveDate;

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getApplicantInfo() {
		return applicantInfo;
	}

	public void setApplicantInfo(String applicantInfo) {
		this.applicantInfo = applicantInfo;
	}

	public String getStrStatusInfo() {
		return strStatusInfo;
	}

	public void setStrStatusInfo(String strStatusInfo) {
		this.strStatusInfo = strStatusInfo;
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

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getIpc() {
		return ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBeforeTransAp() {
		return beforeTransAp;
	}

	public void setBeforeTransAp(String beforeTransAp) {
		this.beforeTransAp = beforeTransAp;
	}

	public String getAfterTransAp() {
		return afterTransAp;
	}

	public void setAfterTransAp(String afterTransAp) {
		this.afterTransAp = afterTransAp;
	}

	public String getCurrentAp() {
		return currentAp;
	}

	public void setCurrentAp(String currentAp) {
		this.currentAp = currentAp;
	}

	public String getBeforeTransAddr() {
		return beforeTransAddr;
	}

	public void setBeforeTransAddr(String beforeTransAddr) {
		this.beforeTransAddr = beforeTransAddr;
	}

	public String getAfterTransAddr() {
		return afterTransAddr;
	}

	public void setAfterTransAddr(String afterTransAddr) {
		this.afterTransAddr = afterTransAddr;
	}

	public String getCurrentAddr() {
		return currentAddr;
	}

	public void setCurrentAddr(String currentAddr) {
		this.currentAddr = currentAddr;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

}
