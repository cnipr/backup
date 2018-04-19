package com.cnipr.pss.exception;

/**
 * 聚类异常
 * @author jefy
 *
 */
public class CluException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public CluException(){
		super("数据操作异常,请检查你的分析系列号是否正确");
	}
	public CluException(String message){
		super(message);
	}
	
}
