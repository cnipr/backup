package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.DownloadSearchDTO;
import com.cnipr.pss.rs.dto.search.SectionInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;

//import com.eprobiti.trs.TRSException;

/**
 * 概览检索人
 * 
 * @author dj
 */
public class PatDownloadSearcher extends AbstractSearcher {
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
	/** 开始下标 **/
	private long startIndex;

	/** 结束下标 **/
	private long endIndex;

	/** 基于某单库进行检索 **/
	private String strSection;

	/** 概览要取的字段值 **/
	private String displayCols;

	// /** 最终表达式 **/
	// private String lastWhere;

	public PatDownloadSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long startIndex, long endIndex,
			String strSection, String strSynonymous, String displayCols) {
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.strSection = strSection;
		this.strSynonymous = strSynonymous;
		this.displayCols = displayCols;
	}

	/**
	 * 概览检索
	 */
	@SuppressWarnings("unused")
	@Override
	public DownloadSearchDTO doSearch() {
		DownloadSearchDTO dto = new DownloadSearchDTO();

		// SemanticSearchOperation operation = new SemanticSearchOperation();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);

			// SemanticSearch semanticSearch = new SemanticSearch();
			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
					strWhere, 0, "RELEVANCE", iOption, null, strSynonymous,
					null, TRSConstant.TCM_KAXST);
			
//			if (strSortMethod == null || strSortMethod.equals("")) {
//				strSortMethod = "+table_sn;+sysid";
//			} else {
//				strSortMethod = strSortMethod + ";+table_sn;+sysid";
//			}
			trsresultset = operation.getSemanticResult(strSources,
					tempLastWhere, strSortMethod, iOption, 115, false,
					strSynonymous, "", strWhere, semanticInstance);
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				// dto.setSearchMessageBean(searchMessageBean);
				return dto;
			}
			// String tempLastWhere = operation.getTrsLastWhere();
			// 检索的总记录数
			long recordCount = trsresultset.getRecordCount();
			dto.setRecordCount(recordCount);
			// 检索的专利信息列表
			List<String[]> sysIdList = null;
			// 数据库/视图信息列表
			List<SectionInfo> sectionInfos = null;
			// 当有检索结果时
			if (recordCount > 0) {
				// 第一次检索，用户不知道总记录数，因此结束下标有可能大于总记录数
				if (endIndex > recordCount) {
					endIndex = recordCount;
				}

				sectionInfos = getSectionInfos(trsresultset);
				sysIdList = new ArrayList<String[]>();
				if (strSection != null && !strSection.equals("")) {
					// semanticInstance.cleanTrsResult(trsresultset);
					// semanticInstance = (new
					// TRSOperation()).getSemanticInstance();
					trsresultset = operation.getSemanticResult(strSection,
							tempLastWhere, strSortMethod, iOption, 115, false,
							strSynonymous, "", "", semanticInstance);
					/**
					 * 测试第二次检索的方法
					 */
					getSectionInfos(trsresultset);
					/**
					 * 测试第二次检索的方法
					 */
					recordCount = trsresultset.getRecordCount();
					dto.setRecordCount(recordCount);
					if (recordCount > 0) {
						// 第一次检索，用户不知道总记录数，因此结束下标有可能大于总记录数
						if (endIndex > recordCount) {
							endIndex = recordCount;
						}
					}
				}
				// 取出本次需要的全部记录
				for (long i = startIndex; i < endIndex; i++) {
					// 移动到检索结果记录集的指定记录
					trsresultset.moveTo(0, i);
					// 构造专利信息
					String sysId[] = new String[2];
					if (displayCols != null && !displayCols.equals("")) {
					} else {
						/**
						 * 检索关键词反色显示
						 */
						sysId[0] = trsresultset.getString("SYSID");
						sysId[1] = trsresultset.getString("APP_ID");
					}

					sysIdList.add(sysId);
				}
				dto.setSysIdList(sysIdList);
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatOverviewSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}

		return dto;
	}

	private List<SectionInfo> getSectionInfos(TRSResultSet trsresultset) {
		int sectionCount = trsresultset.getSectionCount();
		List<SectionInfo> sectionInfoList = new ArrayList<SectionInfo>();

		for (int i = 0; i < sectionCount; i++) {
			try {
				SectionInfo info = new SectionInfo();
				info.setSectionName(trsresultset.getSectionInfo(i).strName);

				info.setRecordNum(trsresultset.getSectionInfo(i).iRecordNum);
				sectionInfoList.add(info);
			} catch (TRSException e) {
				// CoreBizLogger.LogError("获取数据库/视图信息列表出错：" +
				// e.getErrorString());
				e.printStackTrace();
			}
		}
		return sectionInfoList;
	}
}
