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
import com.eprobiti.trs.TRSException;
import com.trs.client.TRSConstant;

//import com.eprobiti.trs.TRSException;

/**
 * 同族分组检索人
 * 
 * @author lq
 */
public class PatFamilyGroupSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatFamilyGroupSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	
	/**
	 * 检索选项掩码
	 **/
	private int iOption;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;
	// //////////////////////////////////////////////
	/** 基于某单库进行检索 **/
	private String strSection;


	public PatFamilyGroupSearcher(String strSources, String strWhere,
			int iOption, String strSection, String strSynonymous) {
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.iOption = iOption;
		this.strSection = strSection;
		this.strSynonymous = strSynonymous;

		logger.info("[PatClassSearcher]strSources=" + strSources + ";strWhere="
				+ strWhere + ";iOption=" + iOption + ";strSection="
				+ strSection + ";strSynonymous=" + strSynonymous);
	}

	/**
	 * 概览检索
	 */
	@Override
	public ClassSearchDTO doSearch() {
		ClassSearchDTO dto = new ClassSearchDTO();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		if (strSection != null && !strSection.trim().equals("")) {
			strSources = strSection;
		}
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);
			trsresultset = operation.getSemanticResult(strSources, strWhere,
					"", iOption, 115, false, strSynonymous, "", strWhere,
					semanticInstance);
			int iClassNum = trsresultset.classResult("family", "", 10, "",
					false, TRSConstant.TCM_CLASSASCENT);
			if (iClassNum > 0) {
				List<PatClassInfo> patclassList = new ArrayList<PatClassInfo>();
				for (int i = 0; i < iClassNum; i++) {
					ClassInfo classInfo = trsresultset.getClassInfo(i);
					if (classInfo.strValue.equals("0")
							|| classInfo.strValue.equals("1")) {
						continue;
					}
					PatClassInfo info = new PatClassInfo();
					info.setClassName(classInfo.strValue);
					info.setRecordNum(classInfo.iRecordNum);
					patclassList.add(info);
				}
				dto.setClassInfoList(patclassList);
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatClassSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
		return dto;
	}

	public static void main(String[] args) {
		PatFamilyGroupSearcher s = new PatFamilyGroupSearcher("fmzl", "申请号=%",
				2, "", "");
		s.excute();
	}
}
