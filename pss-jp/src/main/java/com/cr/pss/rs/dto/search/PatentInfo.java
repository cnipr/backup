package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class PatentInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926233164160048895L;

	private String index;

	private String sysid;

	private String appid;

	private String ti;

	private String an;

	private String ad;

	private String pnm;

	private String pd;

	private String pn;

	private String pin;

	private String pic;

	private String sic;

	private String pa;

	private String ar;

	private String ab;

	private String pr;

	private String agc;

	private String agt;

	private String cl;

	private String ian;

	private String ipn;

	private String ipd;

	private String dan;

	private String co;

	private String clm;

	private String ft;

	private String fcic;

	private String clmPage;
	// 公开说明书（tif）的路径
	private String tifDistributePath;
	// 公开说明书（tif）的页数
	private String pages;

	private String sectionName;

	private String countryCode;

	private String family;

	private String pec;

	private String sec;
	// 说明书页数
	private String desPage;
	// 说明书附图页数
	private String draPage;
	// 授权说明书（tif）的路径
	private String sqTifPath;
	// 授权说明书（tif）的页数
	private String sqTifPages;
	/**
	 * 授权说明书页数
	 */
	private String sqXmlPages;
	/**
	 * 授权说明书附图页数
	 */
	private String sqXmlImgPages;

	private String den;

	// 颁证日
	private String bzr;

	/** 审查员 */
	private String examinant;
	/** 公报路径 */
	private String gbPath;
	/** 公报所在页 */
	private String gbIndex;
	/** 公报翻页信息 */
	private String gbPages;
	/** 权利要求书机器翻译拆分路径 */
	private String CLMSplitPagePath;
	/** 机器翻译权利要求书页数 */
	private String CLMSplitPageCount;
	/** 机器翻译拆分路径 */
	private String DESSplitPagePath;
	/** 机器翻译说明书页数 */
	private String DESSplitPageCount;
	/** XML路径 */
	private String XMLPATH;
	/** 申请人翻译类型 */
	private String paTranslateType;
	/** 发明人翻译类型 */
	private String invTranslateType;
	/** 名称翻译类型 */
	private String tiTranslateType;
	/** 摘要翻译类型 */
	private String absTranslateType;
	// 授权说明书（tif）的页数
	private String patType;
	private String tiEN;
	private String abEN;
	private String paEN;
	private String clEN;
	private String innEN;
	private String abs;
	private String pdfUrl;
	/** 专利权状态 */
	private String patStatus;
	/** 专利权状态代码 */
	private String patStatusCode;
	/** 法律状态 */
	private String lawstatus;
	//同日申请
	private String trsq;
	//复审类型
	private String fslx;
	
	private String titleJp;
	private String titleEn;
	private String abJp;
	private String abEn;
	private Float score;
	private String legalJp;
	private String legalEn;
	private String legalInfoJp;
	private String legalInfoEn;
	

	public String getPatStatus() {
		return patStatus;
	}

	public void setPatStatus(String patStatus) {
		this.patStatus = patStatus;
	}

	public String getPatStatusCode() {
		return patStatusCode;
	}

	public void setPatStatusCode(String patStatusCode) {
		this.patStatusCode = patStatusCode;
		String status = "未知";
		if (patStatusCode != null
				&& (patStatusCode.startsWith("10")
						|| patStatusCode.startsWith("21") || patStatusCode.startsWith("22") || patStatusCode
							.startsWith("30"))) {
			status = "有权";
		} else if (patStatusCode != null && patStatusCode.startsWith("20")) {
			status = "无权";
		} 
		setPatStatus(status);
	}

	public String getLawstatus() {
		return lawstatus;
	}

	public void setLawstatus(String lawstatus) {
		this.lawstatus = lawstatus;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public String getPatType() {
		return patType;
	}

	public void setPatType(String patType) {
		this.patType = patType;
	}

	// 最新法律状态
	private String lastLegalStatus;

	public String getLastLegalStatus() {
		return lastLegalStatus;
	}

	public void setLastLegalStatus(String lastLegalStatus) {
		this.lastLegalStatus = lastLegalStatus;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getTiEN() {
		return tiEN;
	}

	public void setTiEN(String tiEN) {
		this.tiEN = tiEN;
	}

	public String getAbEN() {
		return abEN;
	}

	public void setAbEN(String abEN) {
		this.abEN = abEN;
	}

	public String getPaEN() {
		return paEN;
	}

	public void setPaEN(String paEN) {
		this.paEN = paEN;
	}

	public String getClEN() {
		return clEN;
	}

	public void setClEN(String clEN) {
		this.clEN = clEN;
	}

	public String getInnEN() {
		return innEN;
	}

	public void setInnEN(String innEN) {
		this.innEN = innEN;
	}

	public String getPaTranslateType() {
		return paTranslateType;
	}

	public void setPaTranslateType(String paTranslateType) {
		this.paTranslateType = paTranslateType;
	}

	public String getInvTranslateType() {
		return invTranslateType;
	}

	public void setInvTranslateType(String invTranslateType) {
		this.invTranslateType = invTranslateType;
	}

	public String getTiTranslateType() {
		return tiTranslateType;
	}

	public void setTiTranslateType(String tiTranslateType) {
		this.tiTranslateType = tiTranslateType;
	}

	public String getAbsTranslateType() {
		return absTranslateType;
	}

	public void setAbsTranslateType(String absTranslateType) {
		this.absTranslateType = absTranslateType;
	}

	public String getXMLPATH() {
		return XMLPATH;
	}

	public void setXMLPATH(String xMLPATH) {
		XMLPATH = xMLPATH;
	}

	public String getGbPath() {
		return gbPath;
	}

	public void setGbPath(String gbPath) {
		this.gbPath = gbPath;
	}

	public String getGbIndex() {
		return gbIndex;
	}

	public void setGbIndex(String gbIndex) {
		this.gbIndex = gbIndex;
	}

	public String getGbPages() {
		return gbPages;
	}

	public void setGbPages(String gbPages) {
		this.gbPages = gbPages;
	}

	public String getCLMSplitPagePath() {
		return CLMSplitPagePath;
	}

	public void setCLMSplitPagePath(String cLMSplitPagePath) {
		CLMSplitPagePath = cLMSplitPagePath;
	}

	public String getCLMSplitPageCount() {
		return CLMSplitPageCount;
	}

	public void setCLMSplitPageCount(String cLMSplitPageCount) {
		CLMSplitPageCount = cLMSplitPageCount;
	}

	public String getDESSplitPagePath() {
		return DESSplitPagePath;
	}

	public void setDESSplitPagePath(String dESSplitPagePath) {
		DESSplitPagePath = dESSplitPagePath;
	}

	public String getDESSplitPageCount() {
		return DESSplitPageCount;
	}

	public void setDESSplitPageCount(String dESSplitPageCount) {
		DESSplitPageCount = dESSplitPageCount;
	}

	public String getExaminant() {
		return examinant;
	}

	public void setExaminant(String examinant) {
		this.examinant = examinant;
	}

	public String getBzr() {
		return bzr;
	}

	public void setBzr(String bzr) {
		this.bzr = bzr;
	}

	public String getDen() {
		return den;
	}

	public void setDen(String den) {
		this.den = den;
	}

	/** 摘要附图存储路径 **/
	private String abPicPath;

	public String getAbPicPath() {
		return abPicPath;
	}

	public void setAbPicPath(String abPicPath) {
		this.abPicPath = abPicPath;
	}

	/**
	 * 相关度
	 */
	private String relevance;

	private String trskeyword;

	public String getTrskeyword() {
		return trskeyword;
	}

	public void setTrskeyword(String trskeyword) {
		this.trskeyword = trskeyword;
	}

	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		if (ad != null && !ad.equals("") && ad.length() > 10) {
			ad = ad.substring(0, 10);
		}
		this.ad = ad;
	}

	public String getAgc() {
		return agc;
	}

	public void setAgc(String agc) {
		this.agc = agc;
	}

	public String getAgt() {
		return agt;
	}

	public void setAgt(String agt) {
		this.agt = agt;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getAr() {
		return ar;
	}

	public void setAr(String ar) {
		this.ar = ar;
	}

	public String getCl() {
		return cl;
	}

	public void setCl(String cl) {
		this.cl = cl;
	}

	public String getClm() {
		return clm;
	}

	public void setClm(String clm) {
		this.clm = clm;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getDan() {
		return dan;
	}

	public void setDan(String dan) {
		this.dan = dan;
	}

	public String getFt() {
		return ft;
	}

	public void setFt(String ft) {
		this.ft = ft;
	}

	public String getIan() {
		return ian;
	}

	public void setIan(String ian) {
		this.ian = ian;
	}

	public String getIpd() {
		return ipd;
	}

	public void setIpd(String ipd) {
		this.ipd = ipd;
	}

	public String getIpn() {
		return ipn;
	}

	public void setIpn(String ipn) {
		this.ipn = ipn;
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		if (pd != null && !pd.equals("") && pd.length() > 10) {
			pd = pd.substring(0, 10);
		}

		this.pd = pd;
	}

	public String getPnm() {
		return pnm;
	}

	public void setPnm(String pnm) {
		this.pnm = pnm;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getTi() {
		return ti;
	}

	public void setTi(String ti) {
		this.ti = ti;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSic() {
		return sic;
	}

	public void setSic(String sic) {
		this.sic = sic;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getFcic() {
		return fcic;
	}

	public void setFcic(String fcic) {
		this.fcic = fcic;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getClmPage() {
		return clmPage;
	}

	public void setClmPage(String clmPage) {
		this.clmPage = clmPage;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getTifDistributePath() {
		return tifDistributePath;
	}

	public void setTifDistributePath(String tifDistributePath) {
		this.tifDistributePath = tifDistributePath;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setInfoByName(String columnName, String value) {
		if (columnName.equals("申请（专利）号") || columnName.equals("申请号")) {
			setAn(value);
		} else if (columnName.equals("专利号")) {
			setPn(value);
		} else if (columnName.equals("主分类号")) {
			setPic(value);
		} else if (columnName.equals("名称")) {
			setTi(value);
		} else if (columnName.equals("分类号")) {
			setSic(value);
		} else if (columnName.equals("申请（专利权）人")) {
			setPa(value);
		} else if (columnName.equals("发明（设计）人")) {
			setPin(value);
		} else if (columnName.equals("公开（公告）日")) {
			setPd(value);
		} else if (columnName.equals("公开（公告）号")) {
			setPnm(value);
		} else if (columnName.equals("申请日")) {
			setAd(value);
		} else if (columnName.equals("范畴分类")) {
			setFcic(value);
		} else if (columnName.equals("专利代理机构")) {
			setAgc(value);
		} else if (columnName.equals("代理人")) {
			setAgt(value);
		} else if (columnName.equals("地址")) {
			setAr(value);
		} else if (columnName.equals("国省代码")) {
			setCo(value);
		} else if (columnName.equals("优先权")) {
			setPr(value);
		} else if (columnName.equals("欧洲主分类号")) {
			setPec(value);
		} else if (columnName.equals("欧洲分类号")) {
			setSec(value);
		} else if (columnName.equals("摘要")) {
			setAb(value);
		} else if (columnName.equals("同族专利项")) {
			setFamily(value);
		} else if (columnName.equals("主权项")) {
			setCl(value);
		} else if (columnName.equals("国际申请")) {
			setIan(value);
		} else if (columnName.equals("国际公布")) {
			setIpn(value);
		} else if (columnName.equals("进入国家日期")) {
			setDen(value);
		} else if (columnName.equals("分案原申请号")) {
			setDan(value);
		} else if (columnName.equals("专利权状态代码")) {
			setPatStatusCode(value);
		} else if (columnName.equals("法律状态")) {
			setLawstatus(value);
		} else if (columnName.equals("复审类型")) {
			setFslx(value);
		}
		
		/**
		 * 增加相关度
		 */
		else if (columnName.equals("相关度")) {
			setRelevance(value);
		}
	}

	public String getInfoByName(String columnName) {
		if (columnName.equals("申请（专利）号") || columnName.equals("申请号")) {
			return getAn();
		} else if (columnName.equals("专利号")) {
			return getPn();
		} else if (columnName.equals("主分类号")) {
			return getPic();
		} else if (columnName.equals("名称")) {
			return getTi();
		} else if (columnName.equals("分类号")) {
			return getSic();
		} else if (columnName.equals("申请（专利权）人")) {
			return getPa();
		} else if (columnName.equals("发明（设计）人")) {
			return getPin();
		} else if (columnName.equals("公开（公告）日")) {
			return getPd();
		} else if (columnName.equals("公开（公告）号")) {
			return getPnm();
		} else if (columnName.equals("申请日")) {
			return getAd();
		} else if (columnName.equals("范畴分类")) {
			return getFcic();
		} else if (columnName.equals("专利代理机构")) {
			return getAgc();
		} else if (columnName.equals("代理人")) {
			return getAgt();
		} else if (columnName.equals("地址")) {
			return getAr();
		} else if (columnName.equals("国省代码")) {
			return getCo();
		} else if (columnName.equals("优先权")) {
			return getPr();
		} else if (columnName.equals("欧洲主分类号")) {
			return getPec();
		} else if (columnName.equals("欧洲分类号")) {
			return getSec();
		} else if (columnName.equals("摘要")) {
			return getAb();
		} else if (columnName.equals("同族专利项")) {
			return getFamily();
		} else if (columnName.equals("主权项")) {
			return getCl();
		} else if (columnName.equalsIgnoreCase("Title")) {
			return getTiEN();
		} else if (columnName.equalsIgnoreCase("Abstract")) {
			return getAbEN();
		} else if (columnName.equalsIgnoreCase("Applicant")) {
			return getPaEN();
		} else if (columnName.equalsIgnoreCase("Inventor")) {
			return getInnEN();
		} else if (columnName.equals("最新法律状态")) {
			return getLastLegalStatus();
		} else if (columnName.equals("国际申请")) {
			return getIan();
		} else if (columnName.equals("国际公布")) {
			return getIpn();
		} else if (columnName.equals("进入国家日期")) {
			return getDen();
		} else if (columnName.equals("分案原申请号")) {
			return getDan();
		} else if (columnName.equals("专利权状态")) {
			return getPatStatus();
		} else if (columnName.equals("专利权状态代码")) {
			return getPatStatusCode();
		} else if (columnName.equals("法律状态")) {
			return getLawstatus();
		}
		/**
		 * 增加相关度
		 */
		else if (columnName.equals("相关度")) {
			return getRelevance();
		} else {
			return null;
		}
	}

	public String getPec() {
		return pec;
	}

	public void setPec(String pec) {
		this.pec = pec;
	}

	public String getSec() {
		return sec;
	}

	public void setSec(String sec) {
		this.sec = sec;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getDesPage() {
		return desPage;
	}

	public void setDesPage(String desPage) {
		this.desPage = desPage;
	}

	public String getDraPage() {
		return draPage;
	}

	public void setDraPage(String draPage) {
		this.draPage = draPage;
	}

	public String getSqTifPages() {
		return sqTifPages;
	}

	public void setSqTifPages(String sqTifPages) {
		this.sqTifPages = sqTifPages;
	}

	public String getSqTifPath() {
		return sqTifPath;
	}

	public void setSqTifPath(String sqTifPath) {
		this.sqTifPath = sqTifPath;
	}

	public String getRelevance() {
		return relevance;
	}

	public void setRelevance(String relevance) {
		this.relevance = relevance;
	}

	public String getSqXmlPages() {
		return sqXmlPages;
	}

	public void setSqXmlPages(String sqXmlPages) {
		this.sqXmlPages = sqXmlPages;
	}

	public String getSqXmlImgPages() {
		return sqXmlImgPages;
	}

	public void setSqXmlImgPages(String sqXmlImgPages) {
		this.sqXmlImgPages = sqXmlImgPages;
	}

	public String getTrsq() {
		return trsq;
	}

	public void setTrsq(String trsq) {
		this.trsq = trsq;
	}

	public String getFslx() {
		return fslx;
	}

	public void setFslx(String fslx) {
		this.fslx = fslx;
	}

	public String getTitleJp() {
		return titleJp;
	}

	public void setTitleJp(String titleJp) {
		this.titleJp = titleJp;
	}

	public String getTitleEn() {
		return titleEn;
	}

	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}

	public String getAbJp() {
		return abJp;
	}

	public void setAbJp(String abJp) {
		this.abJp = abJp;
	}

	public String getAbEn() {
		return abEn;
	}

	public void setAbEn(String abEn) {
		this.abEn = abEn;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getLegalJp() {
		return legalJp;
	}

	public void setLegalJp(String legalJp) {
		this.legalJp = legalJp;
	}

	public String getLegalEn() {
		return legalEn;
	}

	public void setLegalEn(String legalEn) {
		this.legalEn = legalEn;
	}

	public String getLegalInfoJp() {
		return legalInfoJp;
	}

	public void setLegalInfoJp(String legalInfoJp) {
		this.legalInfoJp = legalInfoJp;
	}

	public String getLegalInfoEn() {
		return legalInfoEn;
	}

	public void setLegalInfoEn(String legalInfoEn) {
		this.legalInfoEn = legalInfoEn;
	}

}
