package com.cnipr.pss.rs.dto;

import java.io.Serializable;

import com.cnipr.pss.rs.dto.search.TranslateInfo;

/**
 * 细缆DTO
 */
public class TranslateSearchDTO extends AbstractSearchDTO implements Serializable{

	private static final long serialVersionUID = 6803468439256092390L;

	/**  检索的专利信息列表 */
	private TranslateInfo translateInfo = null;

	public TranslateInfo getTranslateInfo() {
		return translateInfo;
	}

	public void setTranslateInfo(TranslateInfo translateInfo) {
		this.translateInfo = translateInfo;
	}

}
