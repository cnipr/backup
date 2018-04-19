package com.cnipr.pss.search.impl;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.search.AbstractSearcher;


/**
 * 自动摘要检索人
 * @author lq
 *
 */
public class PatAutoAbsSearcher extends AbstractSearcher {

	private String appNo;
	private String tablename;
	private String stopWords;
	private String weiWords;
	private int absNum;
	private int absPercent;

	public PatAutoAbsSearcher(String appNo, String tablename, String stopWords,
			String weiWords, int absNum, int absPercent) {
		this.searchType = 2;
		this.appNo = appNo;
		this.tablename = tablename;
		this.stopWords = stopWords;
		this.weiWords = weiWords;
		this.absNum = absNum;
		this.absPercent = absPercent;
	}

	@Override
	public String doSearch() {
		appNo = "申请号=" + appNo;
		String abs = "";
		try {
			String[] fields = new String[] { "说明书" };
			abs = semanticInstance.GetPatentAbs(appNo, tablename, "", weiWords,
					stopWords, "", absNum, absPercent, fields);
		} catch (Exception e) {
			new PSSException("2002",  "appNo=" + appNo + ";tablename=" + tablename + ";message=" + e.getMessage(),
					"执行TRS检索出错", "PatAutoAbsSearcher", "doSearch", "trs");
			return abs;
		}
		return abs;
	}

}
