package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.ClassSearchDTO;
import com.cnipr.pss.rs.dto.search.PatClassInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.Multi;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.ClassInfo;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;

//import com.eprobiti.trs.TRSException;

/**
 * 概览检索人
 * 
 * @author lq
 */
public class PatClassSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatClassSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String buffersize;
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

	// /**
	// * 命中点类型，为零表示不修改
	// **/
	// private int iHitPointType;
	//
	// /** 是否二次检索(即是否在前次检索的结果上继续检索)，如果为true则为二次检索 **/
	// private boolean bContinue;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;
	// //////////////////////////////////////////////
	/** 基于某单库进行检索 **/
	private String strSection;

	/** 概览要取的字段值 **/
	private String classCol;

	// /** 最终表达式 **/
	// private String lastWhere;

	public PatClassSearcher(String strSources, String strWhere,
			String buffersize, int iOption, String strSection,
			String strSynonymous, String classCol) {
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.buffersize = buffersize;
		this.iOption = iOption;
		this.strSection = strSection;
		this.strSynonymous = strSynonymous;
		this.classCol = classCol;

		logger.info("[PatClassSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";buffersize=" + buffersize
				+ ";iOption=" + iOption + ";strSection=" + strSection
				+ ";strSynonymous=" + strSynonymous + ";classCol=" + classCol);
	}

	/**
	 * 概览检索
	 */
	@Override
	public ClassSearchDTO doSearch() {
		long start = System.currentTimeMillis();
		ClassSearchDTO dto = new ClassSearchDTO();

		// SemanticSearchOperation operation = new SemanticSearchOperation();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		Map<Object, Integer> map = new HashMap<Object, Integer>();
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);

			// SemanticSearch semanticSearch = new SemanticSearch();
			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
					strWhere, 0, "", iOption, null, strSynonymous, null,
					TRSConstant.TCM_KAXST);

			// if (strSortMethod == null || strSortMethod.equals("")) {
			// strSortMethod = "+table_sn;+sysid";
			// } else {
			// strSortMethod = strSortMethod + ";+table_sn;+sysid";
			// }
			if (strSection != null && !strSection.equals("")) {
				strSources = strSection;
			}

			trsresultset = operation.getSemanticResult(strSources,
					tempLastWhere, "", iOption, 115, false, strSynonymous, "",
					strWhere, semanticInstance);
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				// dto.setSearchMessageBean(searchMessageBean);
				return dto;
			}

			trsresultset.setReadOptions(0, classCol, null);
			trsresultset.setBufferSize(Integer.parseInt(buffersize),
					Integer.parseInt(buffersize));
			trsresultset.moveTo(0, 0);
			// 取出本次需要的全部记录
			// long i = 0;
			// ArrayList<String> l = new ArrayList<String>();
			// do {
			// String value = trsresultset.getString(classCol);
			// l.add(value);
			// } while (trsresultset.moveNext()
			// && ++i < trsresultset.getRecordCount());
			//
			// for (Object o : l) {
			// map.put(o, map.get(o) == null ? 1 : map.get(o) + 1);
			// }

			int iClassNum = trsresultset.classResult(classCol, "", 0, "",
					false, false);
			if (trsresultset.getClassCount() > 0) {
				List<PatClassInfo> classInfoList = new ArrayList<PatClassInfo>();

				for (int i = 0; i < iClassNum; i++) {
					PatClassInfo info = new PatClassInfo();
					ClassInfo classInfo = trsresultset.getClassInfo(i);
					info.setClassName(classInfo.strValue);
					info.setRecordNum(classInfo.iRecordNum);
					classInfoList.add(info);
				}

				dto.setClassInfoList(classInfoList);
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatOverviewSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
		long end = System.currentTimeMillis();
		logger.info("PSS-分类统计检索：" + (end - start));
		return dto;
	}
	// private void setCustomValue(PatentInfo patentInfo, String colName,
	// TRSResultSet trsresultset) {
	// try {
	// trsresultset.getString(colName);
	// } catch (TRSException e) {
	// e.printStackTrace();
	// }
	// }

}
