package com.cnipr.pss.search;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;

public class Multi extends AbstractSearcher implements Callable<List<String>> {
//	List<String> resultList = 
	private static final Logger logger = LoggerFactory
			.getLogger(Multi.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String strSortMethod;
	//
	// /** 统计结果表达式，多个表达式之间以逗号分隔，可为空 **/
	// private String strStat;
	//
	// /** 检索条件表达式中缺省字段的子检索表达式的目标字段名集列表，字段名之间以半角分号分隔 **/
	// private String strDefautCols;

	/**
	 * 检索选项掩码
	 **/
	private int iOption;
	/** 开始下标 **/
	private long startIndex;
	/** 结束下标 **/
	private long endIndex;
	/** 是否进行同义词扩展 **/
	private String strSynonymous;
	// //////////////////////////////////////////////
	/** 基于某单库进行检索 **/
	private String strSection;

	/** 概览要取的字段值 **/
	private String classCol;
	public Multi(String strSources, String strWhere,
			String strSortMethod, int iOption, String strSection,
			String strSynonymous, String classCol, long startIndex, long endIndex) {
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.strSection = strSection;
		this.strSynonymous = strSynonymous;
		this.classCol = classCol;
//		logger.info("[PatOverviewSearcher]strSources=" + strSources
//				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
//				+ ";iOption=" + iOption + ";strSection=" + strSection
//				+ ";strSynonymous=" + strSynonymous + ";classCol=" + classCol);
	}
	
	@Override
	public List<String> call() throws Exception {
		// TODO Auto-generated method stub
		return (List<String>) this.excute();
	}

	@Override
	public List<String> doSearch() {
//		long start = System.currentTimeMillis();
//		ClassSearchDTO dto = new ClassSearchDTO();

		// SemanticSearchOperation operation = new SemanticSearchOperation();
		ArrayList<String> l = new ArrayList<String>();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);

			// SemanticSearch semanticSearch = new SemanticSearch();
			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
					strWhere, 0, "", iOption, null, strSynonymous, null,
					TRSConstant.TCM_KAXST);

//			 if (strSortMethod == null || strSortMethod.equals("")) {
//			 strSortMethod = "+table_sn;+sysid";
//			 } else {
//			 strSortMethod = strSortMethod + ";+table_sn;+sysid";
//			 }

			trsresultset = operation.getSemanticResult(strSources,
					tempLastWhere, "", iOption, 115, false, strSynonymous, "",
					strWhere, semanticInstance);
			if (trsresultset == null) {
//				dto.setReturncode(2100);
//				dto.setMessage("检索结果集为空");
//				// dto.setSearchMessageBean(searchMessageBean);
//				return dto;
			}
			long recordCount = trsresultset.getRecordCount();
			if (endIndex > recordCount) {
				endIndex = recordCount;
			}
			logger.info(";startIndex=" + startIndex
					+ ";endIndex=" + endIndex);
			trsresultset.setReadOptions(0, classCol, null);
			trsresultset.setBufferSize(1000, 1000);
			trsresultset.moveTo(0, startIndex);
			// 取出本次需要的全部记录
			long i = startIndex;
			do {
				String value = trsresultset.getString(classCol);
				if (value != null && !value.trim().equals("")) {
//					if (trsresultset.getString("an").equals("CN201010263074.6")) {
//						System.out.print("1");
//					}
					if (value.contains("#####")) {
						value = value.split("#####")[0];
					}
					String[] array = value.split(";");
					
					for (String v : array) {
						if (!v.trim().equals("")) {
//							if (v.equals("实质审查的生效")) {
//								System.out.println(trsresultset.getString("an") + "\t" + v);
//							}
							String val = v;
							if (v.contains("#####")) {
								val = v.split("#####")[0];
							}
							l.add(val);
						}
					}
				}
				
			} while (trsresultset.moveNext()
					&& ++i < endIndex);
		}catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatOverviewSearcher", "doSearch", "trs");
//			dto.setReturncode(2002);
//			dto.setMessage(e.getMessage());
		}
//		long end = System.currentTimeMillis();
//		logger.info(""+l.size());	
		
		return l;
	}
}
