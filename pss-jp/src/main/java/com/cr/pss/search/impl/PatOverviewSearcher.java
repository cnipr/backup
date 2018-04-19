package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.rs.dto.search.SectionInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.GetSearchFormat;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;
import com.trs.ckm.zl.SemanticSearch;

//import com.eprobiti.trs.TRSException;

/**
 * 概览检索人
 * 
 * @author lq
 */
public class PatOverviewSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatOverviewSearcher.class);

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

	public PatOverviewSearcher(String strSources, String strWhere,
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

		logger.info("[PatOverviewSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";startIndex=" + startIndex
				+ ";endIndex=" + endIndex + ";strSection=" + strSection
				+ ";strSynonymous=" + strSynonymous + ";displayCols="
				+ displayCols);
	}

	/**
	 * 概览检索
	 */
	@Override
	public OverviewSearchDTO doSearch() {
		long start = System.currentTimeMillis();
		OverviewSearchDTO dto = new OverviewSearchDTO();

		// SemanticSearchOperation operation = new SemanticSearchOperation();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);

//			 SemanticSearch semanticSearch = new SemanticSearch();
//			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
//					strWhere, 0, "RELEVANCE", iOption, null, strSynonymous,
//					null, 1);

//			if (strSortMethod == null || strSortMethod.equals("")) {
//				strSortMethod = "+table_sn;+sysid";
//			} else {
//				strSortMethod = strSortMethod + ";+table_sn;+sysid";
//			}

//			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
//					strSources, strWhere, strSortMethod, "", "", iOption, 2,
//					false, "");
			
			trsresultset = operation.getSemanticResult(strSources,
					strWhere, strSortMethod, iOption, 115, false,
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
			dto.setUnfilterTotalCount(recordCount);
			// 检索的专利信息列表
			List<PatentInfo> patentInfoList = null;
			// 数据库/视图信息列表
			List<SectionInfo> sectionInfos = null;
			// 当有检索结果时
			if (recordCount > 0) {
				// 第一次检索，用户不知道总记录数，因此结束下标有可能大于总记录数
				if (endIndex > recordCount) {
					endIndex = recordCount;
				}

				sectionInfos = (new PssUtil()).getSectionInfos(trsresultset);
				patentInfoList = new ArrayList<PatentInfo>();
				if (strSection != null && !strSection.equals("")) {
					// semanticInstance.cleanTrsResult(trsresultset);
					// semanticInstance = (new
					// TRSOperation()).getSemanticInstance();
//					trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
//							strSection, strWhere, strSortMethod, "", "", iOption, 2,
//							false, "");
					trsresultset = operation.getSemanticResult(strSection,
							strWhere, strSortMethod, iOption, 115, false,
							strSynonymous, "", "", semanticInstance);
					if (trsresultset != null
							&& trsresultset.getRecordCount() > 0) {
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
					} else {
						dto.setRecordCount(0);
						endIndex = 0;
					}

				}
				trsresultset.setBufferSize(10, 10);
				// 相关度系数
				double lastSimValue = 0;
				// 两个表达式不等时认为是语义检索，需要取相关度系数、关键词、相关概念
//				if (!strWhere.equals(tempLastWhere)) {
//					lastSimValue = semanticInstance.getLastSimValue();
//					if (lastSimValue == 0) { // trs暂时有bug，如果为0则先赋值0.98
//						lastSimValue = 0.98;
//					}
//					/**
//					 * 返回智能检索的提取关键词
//					 */
//					dto.setPatentWords(operation.getPatentWords(tempLastWhere,
//							strSources, operation.getPatentKeyWords(), ""));
//					/**
//					 * 返回智能检索的相关概念
//					 */
//					dto.setRelevance(operation.getRelevance(strSources,
//							dto.getPatentWords()));
//				}

				trsresultset.moveTo(0, startIndex);
				// 取出本次需要的全部记录
				long i = startIndex;

				do {
					// 构造专利信息
					PatentInfo patentInfo = new PatentInfo();
					patentInfo.setIndex(Long.toString(i));
					if (displayCols != null && !displayCols.equals("")) {
					} else {
						/**
						 * 检索关键词反色显示
						 */

						patentInfo.setSysid(trsresultset.getString("sysid"));
						patentInfo.setAppid(trsresultset.getString("app_id"));
						patentInfo.setTi(trsresultset.getString(
								Constants.MING_CHENG, "red"));
						patentInfo.setAn(trsresultset
								.getString(Constants.SHEN_QING_HAO));
						patentInfo.setPn(trsresultset
								.getString(Constants.ZHUAN_LI_HAO));
						patentInfo.setPnm(trsresultset
								.getString(Constants.GONG_KAI_HAO));
						patentInfo.setPd(trsresultset.getString(
								Constants.GONG_KAI_RI, "red"));
						patentInfo.setAd(trsresultset.getString(
								Constants.SHENG_QING_RI, "red"));
						patentInfo.setPic(trsresultset
								.getString(Constants.ZHU_FEN_LEI_H));
						patentInfo.setSic(trsresultset
								.getString(Constants.FEN_LEI_HAO));
						patentInfo.setPr(trsresultset
								.getString(Constants.YOU_XIAN_QUAN));
						patentInfo.setAb(trsresultset.getString(
								Constants.ZHAI_YAO, "red"));
						patentInfo.setPa(trsresultset.getString(
								Constants.SHEN_QING_REN, "red"));
						patentInfo.setPatStatusCode(trsresultset.getString(Constants.PAT_STATUS_CODE));
						patentInfo.setTrsq(trsresultset.getString(Constants.TRSQ));
						patentInfo.setFslx(trsresultset.getString(Constants.FSLX));
						patentInfo.setDan(trsresultset.getString(Constants.FEN_AN_YUAN_SQH));
						patentInfo.setTitleJp(trsresultset.getString(Constants.MING_CHEN_RI));
						patentInfo.setAbJp(trsresultset.getString(Constants.ZHAI_YAO_RI));
						try{
							patentInfo.setPatType(trsresultset.getString(
								Constants.ZHUAN_LI_LEI_X));
						} catch (Exception e) {
							
						}
						try{
							patentInfo.setLastLegalStatus(trsresultset
									.getString(Constants.ZUI_XIN_FA_LZT));
						} catch (Exception e) {
							
						}
					}
					String tifpath = (trsresultset
							.getString(Constants.FA_BU_LU_J) == null) ? ""
							: trsresultset.getString(Constants.FA_BU_LU_J)
									.replaceAll("\\\\", "/");
					if (tifpath != null) {
						String[] temp = tifpath.split("/");
						if (temp.length >= 4 && temp[3].contains(".")) {
							temp[3] = temp[3].replace(".", "");
						}
						tifpath = "";
						for (String s : temp) {
							tifpath += s + "/";
						}
						
					}
					
//					String distributePath = (tifpath);
					patentInfo.setTifDistributePath(tifpath);
					
//					String sectionName = "";
//					if (tifpath.contains("FM")) {
//						sectionName = "FMZL";
//					} else if (tifpath.contains("XX")) {
//						sectionName = "SYXX";
//					} else if (tifpath.contains("WG")) {
//						sectionName = "WGZL";
//					} else {
//						sectionName = "FMSQ";
//					}
					TRSRecord rec = trsresultset.getRecord();
					patentInfo.setSectionName(rec.strName);
					patentInfo.setPdfUrl(PssUtil.getPdfUrl(patentInfo.getAn(),
							patentInfo.getPnm(), patentInfo.getAd(),
							patentInfo.getPd(), patentInfo.getPatType(),
							rec.strName));
//					TRSRecord rec = trsresultset.getRecord();
//					patentInfo.setSectionName(rec.strName);
					patentInfo.setPages(trsresultset
							.getString(Constants.YE_SHU));
					// 智能检索相关度
					double relevance = trsresultset.getRelevance();
					// if (relevance >= 0.02) {
					// relevance = relevance - 0.02;
					// }
					patentInfo.setRelevance(PssUtil.getPercent(relevance
							* lastSimValue));
					patentInfoList.add(patentInfo);

				} while (trsresultset.moveNext() && ++i < endIndex);
				dto.setPatentInfoList(patentInfoList);
				dto.setSectionInfos(sectionInfos);
			}

		} catch (TRSException e) {
			e.printStackTrace();
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatOverviewSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
		long end = System.currentTimeMillis();
		logger.info("PSS-概览检索：" + (end - start));
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
