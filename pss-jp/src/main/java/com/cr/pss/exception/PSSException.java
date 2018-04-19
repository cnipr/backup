package com.cnipr.pss.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 检索发生的异常
 * 
 * @author lq
 */
public class PSSException extends Exception {

	private static final Logger logger = LoggerFactory
			.getLogger(PSSException.class);

	private static final long serialVersionUID = -786933694497734758L;

	/** 异常代码 */
	private String errorcode;

	/** 异常信息 */
	private String message;

	/** 显示的信息 */
	private String displaymessage;

	/** 异常来自于哪个类 */
	private String fromclassname;

	/** 异常来自于哪个方法 */
	private String frommethod;

	/** 错误类型 “trs：trs错误”、“rdb：关系型数据库错误”、“other：未知错误” */
	private String errortype;

	/**
	 * @param errorcode
	 *            ：异常代码
	 * @param message
	 *            ：异常信息
	 * @param displaymessage
	 *            ：显示的信息
	 * @param fromclassname
	 *            ：异常来自于哪个类
	 * @param frommethod
	 *            ：异常来自于哪个方法
	 * @param errortype
	 *            ：错误类型 “trs：trs错误”、“rdb：关系型数据库错误”、“other：未知错误”
	 */
	public PSSException(String errorcode, String message,
			String displaymessage, String fromclassname, String frommethod,
			String errortype) {
		super(message);
		this.errorcode = errorcode;
		this.message = message;
		this.displaymessage = displaymessage;
		this.fromclassname = fromclassname;
		this.frommethod = frommethod;
		this.errortype = errortype;
		logger.error("fromclassname=" + fromclassname + ";frommethod="
				+ frommethod + ";errorcode=" + errorcode + ";displaymessage="
				+ displaymessage + ";errortype=" + errortype + ";message="
				+ message);
	}

	public String getDisplaymessage() {
		return displaymessage;
	}

	public void setDisplaymessage(String displaymessage) {
		this.displaymessage = displaymessage;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromclassname() {
		return fromclassname;
	}

	public void setFromclassname(String fromclassname) {
		this.fromclassname = fromclassname;
	}

	public String getFrommethod() {
		return frommethod;
	}

	public void setFrommethod(String frommethod) {
		this.frommethod = frommethod;
	}

	public String getErrortype() {
		return errortype;
	}

	public void setErrortype(String errortype) {
		this.errortype = errortype;
	}

}
