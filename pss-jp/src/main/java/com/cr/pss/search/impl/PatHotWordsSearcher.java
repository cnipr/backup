package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.HotWordsDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.util.ParseSemanticExp;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSException;

/**
 * 专利热点词提取
 * 
 * @author lq
 * 
 */
public class PatHotWordsSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatHotWordsSearcher.class);
	
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	/** 抽取热点词的个数 */
	private int extractNum;

	/** 构造函数 **/
	public PatHotWordsSearcher(String strSources, String strWhere,
			int extractNum) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.extractNum = extractNum;
		logger.info("[PatSimilaritySearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere+ ";extractNum=" + extractNum);
	}

	@Override
	/** 检索方法 */
	public HotWordsDTO doSearch() {
		HotWordsDTO dto = new HotWordsDTO();

		String[] semanticArr = ParseSemanticExp.getSemanticExp(strWhere);
		String result;
		try {
			result = semanticInstance.GetBatchPatantKeyWords(strSources,
					semanticArr[1], semanticArr[2], semanticArr[0], 1, "", 2,
					null, false, null, null, null, 0, "",
					PssUtil.getProperty("FilterWords"));
			if (result != null && "".equals(result) == false) {
				List<String> list = new ArrayList<String>();
				String[] r = result.split(";");
				extractNum = (r.length > extractNum ? extractNum : r.length);
				for (int i = 0; i < extractNum; i++) {
					String[] keyvalues = r[i].split("\t");
					list.add(keyvalues[0]);
				}
				dto.setHotwords(list);
			}
		} catch (TRSException e) {
			new PSSException("2003", e.getErrorCode() + ":" + e.getMessage(),
					"执行热点词检索出错", "PatHotWordsSearcher", "doSearch", "trs");
			dto.setReturncode(2003);
			dto.setMessage(e.getMessage());
		}

		return dto;
	}
}
