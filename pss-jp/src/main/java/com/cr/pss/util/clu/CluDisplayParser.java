package com.cnipr.pss.util.clu;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.cnipr.pss.exception.CluException;
import com.cnipr.pss.rs.dto.clu.Patent;

public class CluDisplayParser {

	/**
	 * 取得显示字段
	 * 
	 * @param display
	 * @param patent
	 * @return String
	 * @throws ClusteringException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String getDisplay(String display, Patent patent) {
		if (display == null || "".equals(display)) {
			throw new CluException("显示参数不正确");
		}
		String[] arr = display.split(",");
		if (arr == null || arr.length != 2) {
			throw new CluException("显示参数不正确");
		}
		String dis1 = null;
		String dis2 = null;
		try {
			dis1 = BeanUtils.getProperty(patent, arr[0]);
			dis2 = BeanUtils.getProperty(patent, arr[1]);
		} catch (IllegalAccessException e) {
			throw new CluException("显示参数不正确");
		} catch (InvocationTargetException e) {
			throw new CluException("显示参数不正确");
		} catch (NoSuchMethodException e) {
			throw new CluException("显示参数不正确");
		}
		return dis1 + " : " + dis2;
	}
}
