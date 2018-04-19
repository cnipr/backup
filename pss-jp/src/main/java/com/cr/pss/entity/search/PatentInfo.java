package com.cnipr.pss.entity.search;

import java.io.Serializable;

import com.cnipr.pss.util.Constants;

public class PatentInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926233164160048895L;

	private String index;

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
	// pdf url
	private String pdfUrl;
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

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
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
	
	// 授权说明书（tif）的页数
	private String lastLegalStatus;

	public String getLastLegalStatus() {
		return lastLegalStatus;
	}

	public void setLastLegalStatus(String lastLegalStatus) {
		this.lastLegalStatus = lastLegalStatus;
	}

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
		} else if (columnName.equals("最新法律状态")) {
			return getLastLegalStatus();
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

}
