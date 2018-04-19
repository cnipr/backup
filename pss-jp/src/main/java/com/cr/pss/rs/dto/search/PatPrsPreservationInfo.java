package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

/**
 * 质押保全信息
 * 
 * @author liuqi
 * 
 */
public class PatPrsPreservationInfo implements Serializable {
	private static final long serialVersionUID = -1398309819832796549L;
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
	// 质押保全类型
	private String type;
	// 出质人
	private String chuZhiRen;
	// 质权人
	private String zhiQuanRen;
	// 当前质权人
	private String dangQianZQR;
	// 合同状态
	private String heTongZT;
	// 合同登记号
	private String heTongDJH;
	// 生效日
	private String shengXiaoRi;
	// 变更日
	private String bianGengRi;
	// 解除日
	private String jieChuRi;

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

	public String getChuZhiRen() {
		return chuZhiRen;
	}

	public void setChuZhiRen(String chuZhiRen) {
		this.chuZhiRen = chuZhiRen;
	}

	public String getZhiQuanRen() {
		return zhiQuanRen;
	}

	public void setZhiQuanRen(String zhiQuanRen) {
		this.zhiQuanRen = zhiQuanRen;
	}

	public String getDangQianZQR() {
		return dangQianZQR;
	}

	public void setDangQianZQR(String dangQianZQR) {
		this.dangQianZQR = dangQianZQR;
	}

	public String getHeTongZT() {
		return heTongZT;
	}

	public void setHeTongZT(String heTongZT) {
		this.heTongZT = heTongZT;
	}

	public String getHeTongDJH() {
		return heTongDJH;
	}

	public void setHeTongDJH(String heTongDJH) {
		this.heTongDJH = heTongDJH;
	}

	public String getShengXiaoRi() {
		return shengXiaoRi;
	}

	public void setShengXiaoRi(String shengXiaoRi) {
		this.shengXiaoRi = shengXiaoRi;
	}

	public String getBianGengRi() {
		return bianGengRi;
	}

	public void setBianGengRi(String bianGengRi) {
		this.bianGengRi = bianGengRi;
	}

	public String getJieChuRi() {
		return jieChuRi;
	}

	public void setJieChuRi(String jieChuRi) {
		this.jieChuRi = jieChuRi;
	}

}
