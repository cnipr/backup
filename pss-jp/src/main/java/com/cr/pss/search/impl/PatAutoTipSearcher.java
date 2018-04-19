package com.cnipr.pss.search.impl;

import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.util.ReadresourceUtil;
import com.eprobiti.trs.TRSException;

/**
 * 自动提示
 * @author lq
 *
 */
public class PatAutoTipSearcher extends AbstractSearcher {

	private String strWhere;
	
	public PatAutoTipSearcher(String strWhere){
		this.searchType = 2;
		this.strWhere = strWhere;
	}
	
	@Override
	public String doSearch() {
		String r = "";
		String[] wordTips = null;
		int tipsNum = Integer.parseInt(ReadresourceUtil.getNum("wordtipsnum"));
		try {
			wordTips = semanticInstance.GetWordTips(strWhere, tipsNum, "");
			if (wordTips != null && wordTips.length > 0) {
				for (String s : wordTips) {
					r += s + "###";
				}
			}
		} catch (TRSException e) {
			e.printStackTrace();
			return null;
		}
		return r;
	}

}
