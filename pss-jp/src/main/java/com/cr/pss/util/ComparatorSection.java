package com.cnipr.pss.util;

import java.util.Comparator;

import com.cnipr.pss.rs.dto.search.SectionInfo;

public class ComparatorSection implements Comparator {

	public int compare(Object arg0, Object arg1) {
		SectionInfo user0 = (SectionInfo) arg0;
		SectionInfo user1 = (SectionInfo) arg1;

		int flag = 0;
		if (user0.getRecordNum() < user1.getRecordNum()) {
			flag = 1;
		}
		return flag;
	}
}