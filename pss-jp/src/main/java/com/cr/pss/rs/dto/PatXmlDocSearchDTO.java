package com.cnipr.pss.rs.dto;

import java.io.Serializable;
/**
 * 代码化全文检索结果
 * @author lq
 *
 */
public class PatXmlDocSearchDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = 3572802043729062508L;
	
//	private byte[] xmlByte;
	
	private String strXml;
	
//	public byte[] getXmlByte() {
//		return xmlByte;
//	}
//
//	public void setXmlByte(byte[] xmlByte) {
//		this.xmlByte = xmlByte;
//	}

	public String getStrXml() {
		return strXml;
	}

	public void setStrXml(String strXml) {
		this.strXml = strXml;
	}
	
	
}
