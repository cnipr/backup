package com.cnipr.pss.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;

/**
 * 根据查询条件进行检索，返回List<Map>
 * @author weiding
 *
 */
public class TrsListMapSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatDetailSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String strSortMethod;
	/** 是否相似性检索1：是； 其他：否 **/
	private int isSimilarSearch = 0;
	/**
	 * 检索选项掩码
	 **/
	private int iOption;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	private String[] columns;

	public TrsListMapSearcher() {
	}

	public TrsListMapSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, 
			String strSynonymous, int isSimilarSearch, String[] columns) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.isSimilarSearch = isSimilarSearch;
		// this.iHitPointType = iHitPointType;
		// this.bContinue = bContinue;
		this.strSynonymous = strSynonymous;
		this.columns = columns;

		logger.info("[TrsListMapSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";isSimilarSearch=" + isSimilarSearch
				+ ";strSynonymous="
				+ strSynonymous);
	}

	/**
	 * 专利代码化全文(xml)检索
	 */
	@Override
	public TrsListMapSearchDTO doSearch() {
		TrsListMapSearchDTO dto = new TrsListMapSearchDTO();
		InputStream xml_is = null;
		InputStreamReader isr = null;
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			// 相似性检索表达式
			String similarWhere = "";
			if (isSimilarSearch == 1) {
				similarWhere = strWhere;
			} else {
				strWhere = (new GetSearchFormat()).preprocess(strWhere);
			}

			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
					strWhere, 0, "RELEVANCE", iOption, null, strSynonymous,
					null, TRSConstant.TCM_KAXST);

//			if (strSortMethod == null || strSortMethod.equals("")) {
//				strSortMethod = "+table_sn;+sysid";
//			} else {
//				strSortMethod = strSortMethod + ";+table_sn;+sysid";
//			}

			trsresultset = (new SemanticSearchOperation()).getSemanticResult(
					strSources, tempLastWhere, strSortMethod, iOption, 115,
					false, strSynonymous, similarWhere, "", semanticInstance);
			
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				return dto;
			}
			long totalCount = trsresultset.getRecordCount();
			if (totalCount > 30000) {
				dto.setReturncode(2100);
				dto.setMessage("结果集超过30000");
				return dto;
			}
			for (int i = 0; i < columns.length; i++) {
				if ("公开（公告）号".equals(columns[i])) {
					/** 对公开（公告）号两字段设置缓存 */
					trsresultset.setReadOptions(0, "公开（公告）号", null);
					trsresultset.setBufferSize(10000, 10000);
					break;
				}
			}

			for (int i = 0; i < trsresultset.getRecordCount(); i++) {
				trsresultset.moveTo(0, i);
				Map<String, String> map = new HashMap<String, String>();
				for (int j = 0; j < columns.length; j++) {
					map.put(columns[j], trsresultset.getString(columns[j]));
				}
				//设置当前这条专利的数据库名称
				TRSRecord rec = trsresultset.getRecord();
				map.put("paramDb", rec.strName.toUpperCase());
				dto.getResultList().add(map);
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "TrsListMapSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		} catch (Exception e) {
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"TrsListMapSearcher", "doSearch(strSources:" + strSources
							+ ";strWhere:" + strWhere + ";columns:"
							+ columns.toString() + ")", "java");
			dto.setReturncode(1000);
			dto.setMessage(e.getMessage());
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (xml_is != null) {
				try {
					xml_is.close();
				} catch (IOException e) {
					new PSSException("3000", "3000:" + e.getMessage(),
							e.getMessage(), "TrsListMapSearcher", "doSearch",
							"java");
				}
			}
		}

		// dto.setXmlByte(xmlByte);
		return dto;
	}

}
