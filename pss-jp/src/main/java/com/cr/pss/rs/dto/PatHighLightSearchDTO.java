package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 高亮阅读页面的检索
 * @author weiding
 *
 */
public class PatHighLightSearchDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = 3572802043729062508L;
	
	/**
	 * map中的key为字段名，value为字段值
	 */
	private Map<String,String> resultMap = new HashMap<String,String>();

	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}
	
}
