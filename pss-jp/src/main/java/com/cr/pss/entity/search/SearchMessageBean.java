package com.cnipr.pss.entity.search;

import java.io.Serializable;

public class SearchMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1583123898021227939L;

	/** 
	 * 操作结果代码 
	 * 1:表示成功，其它均为失败
	 */
	private int returncode;
	
	/** 
	 * 操作错误详细信息，成功时message为空串
	 */
	private String message;
	
	public SearchMessageBean() {
		
	}

	public SearchMessageBean(int returncode, String message) {
		super();
		this.returncode = returncode;
		this.message = message;
	}

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
