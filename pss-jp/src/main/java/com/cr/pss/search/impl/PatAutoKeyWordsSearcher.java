package com.cnipr.pss.search.impl;

import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.util.PssUtil;
import com.cnipr.pss.util.ReadresourceUtil;

/**
 * 自动关键词检索
 * 
 * @author lq
 * 
 */
public class PatAutoKeyWordsSearcher extends AbstractSearcher {

	private String appNum;
	private String tableName;
	private String inputQuerys;
	private String searchCond;

	public PatAutoKeyWordsSearcher(String appNum, String tableName,
			String inputQuerys, String searchCond) {
		this.searchType = 2;
		this.appNum = appNum;
		this.tableName = tableName;
		this.inputQuerys = inputQuerys;
		this.searchCond = searchCond;
	}

	@Override
	public String doSearch() {
		int wordsNum = Integer.parseInt(ReadresourceUtil
				.getNum("patentkeywordnums"));
		String patKeyWords = "";
		String keyWords = "";
		try {
			String[] fields = new String[] { "名称", "摘要", "权利要求书", "说明书" };
			patKeyWords = semanticInstance.GetPatentKeyWords(appNum, tableName,
					inputQuerys, "", PssUtil.getProperty("FilterWords"),
					searchCond, wordsNum, fields);
			String[] firstArray = patKeyWords.split(";");
			for (String tempStr : firstArray) {
				String[] lastArray = tempStr.split("\t");
				keyWords += lastArray[0] + " ";
			}
		} catch (Exception e) {
			return "";
		}
		return patKeyWords;
	}

}
