package com.cnipr.pss.rs.dto;

import java.io.Serializable;

/**
 * 检索结果（抽象类）
 * @author lq
 *
 */
public class AbstractSearchDTO implements Serializable{
	
	private static final long serialVersionUID = -8905196784034649800L;
//	/** 检索信息Bean */
//	private SearchMessageBean searchMessageBean;
//
//	public SearchMessageBean getSearchMessageBean() {
//		return searchMessageBean;
//	} 
//
//	public void setSearchMessageBean(SearchMessageBean searchMessageBean) {
//		this.searchMessageBean = searchMessageBean;
//	}
	
	/** 
	 * 操作结果代码 
	 * 1:表示成功，其它均为失败
	 */
	private int returncode = 1;
	
	/** 
	 * 操作错误详细信息，成功时message为空串
	 */
	private String message = "";
	
	public int getReturncode() {
		return returncode;
	}

	public void setReturncode(int returncode) {
		this.returncode = returncode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
