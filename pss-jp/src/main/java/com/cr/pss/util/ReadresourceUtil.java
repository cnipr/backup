/**
 * 
 */
package com.cnipr.pss.util;

import java.util.ResourceBundle;

/**
 * @author 戴国栋 获取资源文件属性的方法 create 2010-1-11 company 知识产权出版社
 * @version 1.0
 */
public class ReadresourceUtil {

	private static final String RESOURCENAME = "ckmconfig";

	public static String getNum(String keyName) {
		String resourceValue = "";
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(RESOURCENAME);
			resourceValue = rb.getString(keyName);
		} catch (Exception e) {
			return "10";
		}
		if (resourceValue == null || resourceValue.trim().equals("")) {
			resourceValue = "10";
		}
		return resourceValue;
	}

	public static String getDict(String keyName) {
		String resourceValue = "";
		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(RESOURCENAME);
			resourceValue = rb.getString(keyName);
		} catch (Exception e) {
			return "";
		}
		if (resourceValue == null || resourceValue.trim().equals("")) {
			resourceValue = "";
		}
		return resourceValue;
	}

	public static void main(String[] args) {
		System.out.println(getDict("TRSHost"));
	}
}
