package com.cnipr.pss.rs.dto;

import java.io.Serializable;

/**
 * 概览检索结果
 * @author lq
 *
 */
public class PatFamilySearchDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = -515389133257457656L;
	
	/** 同族专利申请号，逗号分隔 */
	private String families;

	public String getFamilies() {
		return families;
	}

	public void setFamilies(String families) {
		this.families = families;
	}

	
}
