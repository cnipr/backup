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
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;

//import com.eprobiti.trs.TRSException;

/**
 * 概览检索人
 * @author lq
 */
public class PatSimilarSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatSimilarSearcher.class);
	
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String similarWhere;
//	/**
//	 * 检索选项掩码
//	 **/
//	private int iOption;

//	/**
//	 * 命中点类型，为零表示不修改
//	 **/
//	private int iHitPointType;
//
//	/** 是否二次检索(即是否在前次检索的结果上继续检索)，如果为true则为二次检索 **/
//	private boolean bContinue;
//
//	/** 是否进行同义词扩展 **/
//	private String strSynonymous;
	// //////////////////////////////////////////////
	/** 开始下标 **/
	private long startIndex;

	/** 结束下标 **/
	private long endIndex;

	/** 基于某单库进行检索 **/
	private String strSection;

	/** 概览要取的字段值 **/
	private String displayCols;

	/** 最终表达式 **/
	private String lastWhere;

	public PatSimilarSearcher(String strSources, String similarWhere,
			long startIndex,
			long endIndex, String strSection, String displayCols, String lastWhere) {
		this.searchType = 2;
		this.strSources = strSources;
		this.similarWhere = similarWhere;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.strSection = strSection;
		this.displayCols = displayCols;
		this.lastWhere = lastWhere;
		logger.info("[PatSimilarSearcher]strSources=" + strSources
				+ ";similarWhere=" + similarWhere);
	}


	/**
	 * 概览检索
	 */
	@Override
	public OverviewSearchDTO doSearch() {
		OverviewSearchDTO dto = new OverviewSearchDTO();

//		SemanticSearchOperation operation = new SemanticSearchOperation();
		SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			trsresultset = operation.getSemanticResult(strSources, "",
					"RELEVANCE", TRSConstant.TCM_SORTALWAYS
					| TRSConstant.TCE_OFFSET, 115, false,
					"", similarWhere, "", semanticInstance);
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
//				dto.setSearchMessageBean(searchMessageBean);
				return dto;
			}
			dto.setTrsLastWhere(operation.getTrsLastWhere());
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

				sectionInfos = getSectionInfos(trsresultset);
				patentInfoList = new ArrayList<PatentInfo>();
				if (strSection != null && !strSection.equals("")) {
					trsresultset = operation.getSemanticResult(strSection,
							"", "RELEVANCE", TRSConstant.TCM_SORTALWAYS
							| TRSConstant.TCE_OFFSET, 115,
							false, "", similarWhere, "",
							semanticInstance);
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
					PatentInfo patentInfo = new PatentInfo();
					patentInfo.setIndex(Long.toString(i));
					
					if (displayCols != null && !displayCols.equals("")) {
					} else {
						/**
						 * 检索关键词反色显示
						 */
						patentInfo.setTi(trsresultset.getString(
								Constants.MING_CHENG, "red"));
						patentInfo.setAn(trsresultset
								.getString(Constants.SHEN_QING_HAO));
						patentInfo.setPn(trsresultset
								.getString(Constants.ZHUAN_LI_HAO));
						patentInfo.setPnm(trsresultset
								.getString(Constants.GONG_KAI_HAO));
						patentInfo.setPd(trsresultset
								.getString(Constants.GONG_KAI_RI));
						patentInfo.setAd(trsresultset
								.getString(Constants.SHENG_QING_RI));
						patentInfo.setPic(trsresultset
								.getString(Constants.ZHU_FEN_LEI_H));
						patentInfo.setPr(trsresultset
								.getString(Constants.YOU_XIAN_QUAN));
						patentInfo.setAb(trsresultset.getString(Constants.ZHAI_YAO,
						"red"));
						patentInfo.setPa(trsresultset
								.getString(Constants.SHEN_QING_REN));
					}
//					String distributePath = (trsresultset
//							.getString(Constants.FA_BU_LU_J) == null) ? ""
//							: trsresultset.getString(Constants.FA_BU_LU_J)
//									.replaceAll("\\\\", "/");
//					patentInfo.setTifDistributePath(distributePath);
//					TRSRecord rec = trsresultset.getRecord();
//					patentInfo.setSectionName(rec.strName);
//					patentInfo.setPages(trsresultset
//							.getString(Constants.YE_SHU));
					// 智能检索相关度
					double relevance = trsresultset.getRelevance();
					if (relevance >= 0.02) {
						relevance = relevance - 0.02;
					}
					patentInfo.setRelevance(PssUtil.getPercent(relevance));
					patentInfoList.add(patentInfo);
				}
				dto.setPatentInfoList(patentInfoList);
			}
			dto.setSectionInfos(sectionInfos);
//			/**
//			 * 返回智能检索的提取关键词
//			 */
//			dto.setPatentWords(operation
//					.getPatentWords(similarWhere, strSources, operation.getPatentKeyWords(), ""));
//			/**
//			 * 返回智能检索的相关概念
//			 */
//			dto.setRelevance(operation.getRelevance(strSources, operation.getPatentKeyWords()));
			
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(), "执行TRS检索出错",
					"PatOverviewSearcher", "doSearch", "trs");
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
	
//	private void setCustomValue(PatentInfo patentInfo, String colName, TRSResultSet trsresultset) {
//		try {
//			trsresultset.getString(colName);
//		} catch (TRSException e) {
//			e.printStackTrace();
//		}
//	}

}
