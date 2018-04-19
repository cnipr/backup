package com.cnipr.pss.search;

import java.math.BigInteger;

import org.springframework.util.DigestUtils;

import com.cnipr.pss.util.Constants;

public class MD5Test {
	public static void main(String[] args) {
		StringBuffer ly = new StringBuffer();
//		String sectionName = trsresultset
//				.getSectionInfo((int) trsresultset.getRecordSect()).strName;
//		if (sectionName.length() == 8
//				&& sectionName.toUpperCase().endsWith("PATENT")) {
//			ly.append(sectionName.substring(0, 2).toUpperCase());
//		} else {
//			ly.append(sectionName.substring(0, 4).toUpperCase());
//		}
//
//		String strANShort = trsresultset
//				.getString(Constants.SHEN_QING_HAO);
//		if (strANShort.indexOf(".") > -1) {
//			strANShort = strANShort.substring(0,
//					strANShort.indexOf("."));
//		}
//
//		ly.append("@")
//				.append(strANShort)
//				.append("@")
//				.append(trsresultset
//						.getString(Constants.GONG_KAI_HAO));
		ly.append("FMZL@CN9411093@CN1099528");
		System.out.println(new BigInteger(1, DigestUtils.md5Digest(ly
				.toString().toUpperCase().getBytes())).toString(16)
				.toUpperCase());
	}
}
