package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class YzwsInfo implements Serializable {
	private static final long serialVersionUID = -4122850405827184270L;
	// 申请号
	private String strAn;
	// 公布号
	private String strGBH;
	// 分类号
	private String strFLH;
	// 公布日期
	private String strGBRQ;
	// 文献标记
	private String strWXBJ;
	
	//公开（公告）号
	private String strPn;
	//公开（公告）日
	private String strPD;
	//被引证文献原始数据
	private String strBaseData;
	

	public String getStrAn() {
		return strAn;
	}

	public void setStrAn(String strAn) {
		this.strAn = strAn;
	}

	public String getStrGBH() {
		return strGBH;
	}

	public void setStrGBH(String strGBH) {
		this.strGBH = strGBH;
	}

	public String getStrFLH() {
		return strFLH;
	}

	public void setStrFLH(String strFLH) {
		this.strFLH = strFLH;
	}

	public String getStrGBRQ() {
		return strGBRQ;
	}

	public void setStrGBRQ(String strGBRQ) {
		this.strGBRQ = strGBRQ;
	}

	public String getStrWXBJ() {
		return strWXBJ;
	}

	public void setStrWXBJ(String strWXBJ) {
		this.strWXBJ = strWXBJ;
	}

	public String getStrPn() {
		return strPn;
	}

	public void setStrPn(String strPn) {
		this.strPn = strPn;
	}

	public String getStrPD() {
		return strPD;
	}

	public void setStrPD(String strPD) {
		this.strPD = strPD;
	}

	public String getStrBaseData() {
		return strBaseData;
	}

	public void setStrBaseData(String strBaseData) {
		this.strBaseData = strBaseData;
	}

}
