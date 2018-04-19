package com.cnipr.pss.search.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.TranslateSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.rs.dto.search.TranslateInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.search.enimpl.EnPatTranslateSearcher;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.GetSearchFormat;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;

//import com.eprobiti.trs.TRSException;

/**
 * 概览检索人
 * 
 * @author lq
 */
public class PatBatchDownloadSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatBatchDownloadSearcher.class);

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

	public PatBatchDownloadSearcher(String strSources, String strWhere,
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

		logger.info("[PatBatchDownloadSearcher]strSources=" + strSources
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
			dto.setUnfilterTotalCount(recordCount);
			// 检索的专利信息列表
			List<PatentInfo> patentInfoList = null;
			// 数据库/视图信息列表
			// List<SectionInfo> sectionInfos = null;
			// 当有检索结果时
			if (recordCount > 0) {
				// 第一次检索，用户不知道总记录数，因此结束下标有可能大于总记录数
				if (endIndex > recordCount) {
					endIndex = recordCount;
				}

				// sectionInfos = (new PssUtil()).getSectionInfos(trsresultset);
				patentInfoList = new ArrayList<PatentInfo>();
				if (strSection != null && !strSection.equals("")) {
					// semanticInstance.cleanTrsResult(trsresultset);
					// semanticInstance = (new
					// TRSOperation()).getSemanticInstance();
					trsresultset = operation.getSemanticResult(strSection,
							tempLastWhere, strSortMethod, iOption, 115, false,
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
//				trsresultset
//						.setReadOptions(
//								0,
//								"名称,申请号,申请日,公开（公告）日,公开（公告）号,专利号,申请（专利权）人,发明（设计）人,国省代码,主分类号,分类号,优先权,摘要,主权项,专利代理机构,代理人,地址,专利类型",
//								null);
				trsresultset.setBufferSize(2000, 2000);
				// 相关度系数

				trsresultset.moveTo(0, startIndex);
				// 取出本次需要的全部记录
				long i = startIndex;
//				System.out.println("startIndex=" + startIndex);
				do {
					// 构造专利信息
					PatentInfo patentInfo = new PatentInfo();
					patentInfo.setIndex(Long.toString(i));
//					System.out.println("displayCols=" + displayCols);
					if (displayCols != null && !displayCols.equals("")) {
						
						String[] colArray = displayCols.split(";");
						for (String colname : colArray) {
							if (colname.equals("ENAB")) {
								AbstractSearcher searcher = new EnPatTranslateSearcher(
										"CNFMZL_EN,CNSYXX_EN,CNWGZL_EN",
										"ap="
												+ trsresultset
														.getString(Constants.SHEN_QING_HAO),
										"", 0, 0, null);
								TranslateSearchDTO endto = (TranslateSearchDTO) searcher
										.excute();
								if (endto.getReturncode() == 1
										&& endto.getTranslateInfo() != null) {
									TranslateInfo eninfo = endto
											.getTranslateInfo();
									patentInfo.setAbEN(eninfo.getAbEN());
									patentInfo.setClEN(eninfo.getClEN());
									patentInfo.setTiEN(eninfo.getTiEN());
									patentInfo.setInnEN(eninfo.getInnEN());
									patentInfo.setPaEN(eninfo.getPaEN());
								}
							} else if (colname.equals("权利要求书")) {
								byte[] xmlByte = null;
								InputStream xml_is = null;
								String xmlCL = "";
								xml_is = trsresultset.getBinaryStream("权利要求书", 0);
//								BufferedReader reader = null;
//								InputStream src_is = null;
								if (xml_is != null) {
									try {
										xmlByte = PssUtil.getBytes(xml_is);
										xmlCL=new String(xmlByte, "UTF-8");
										String[] xmlCnEn = xmlCL.split("<!-- SIPO document split -->");
										xmlCL = xmlCnEn[0].replaceAll("<!--.*?-->", "").replaceAll("<.*?>", "").replace("\r\n", "").replace(" ", "");
										xmlCL += "<!-- SIPO document split -->";
										if (xmlCnEn.length >= 2 && xmlCnEn[1] != null) {
											xmlCL += xmlCnEn[1].replaceAll("<!--.*?-->", "").replaceAll("<.*?>", "").replace("\r\n", "");
										}
//										System.out.println(xmlCL);
//										xmlCL=xmlCL.substring(0,xmlCL.indexOf("<!-- SIPO document split -->"));
//										src_is = new ByteArrayInputStream(xmlCL.getBytes());
//										reader = new BufferedReader(new InputStreamReader(src_is, "UTF-8"));
//										xmlCL = reader.readLine();
									} catch (Exception e) {
										xmlCL = "";
									}finally {
										try {
//											if (reader != null)
//												reader.close();
//											if (src_is != null)
//												src_is.close();
											if (xml_is != null)
												xml_is.close();
										} catch (Exception ex) {
//											ex.printStackTrace();
										}
									}
								} 
								patentInfo.setClm(xmlCL);
							} else if (colname.equals("名称")) {
								patentInfo.setInfoByName(colname,trsresultset.getString(colname));
								patentInfo.setTitleJp(trsresultset.getString("名称日"));
							} else if (colname.equals("摘要")) {
								patentInfo.setInfoByName(colname,trsresultset.getString(colname));
								patentInfo.setAbJp(trsresultset.getString("摘要日"));
							}else {
								patentInfo.setInfoByName(colname,
										trsresultset.getString(colname));
							}
						}
					} else {
						patentInfo.setTi(trsresultset
								.getString(Constants.MING_CHENG));
						patentInfo.setAn(trsresultset
								.getString(Constants.SHEN_QING_HAO));
						patentInfo.setAd(trsresultset
								.getString(Constants.SHENG_QING_RI));
						patentInfo.setPd(trsresultset
								.getString(Constants.GONG_KAI_RI));
						patentInfo.setPnm(trsresultset
								.getString(Constants.GONG_KAI_HAO));
						patentInfo.setPn(trsresultset
								.getString(Constants.ZHUAN_LI_HAO));
						patentInfo.setPa(trsresultset
								.getString(Constants.SHEN_QING_REN));
						patentInfo.setPin(trsresultset
								.getString(Constants.FA_MING_REN));
						patentInfo.setCo(trsresultset
								.getString(Constants.GUO_SHENG_DAI_M));
						patentInfo.setSic(trsresultset
								.getString(Constants.FEN_LEI_HAO));
						patentInfo.setPic(trsresultset
								.getString(Constants.ZHU_FEN_LEI_H));
						patentInfo.setPr(trsresultset
								.getString(Constants.YOU_XIAN_QUAN));
						patentInfo.setAb(trsresultset
								.getString(Constants.ZHAI_YAO));
						patentInfo.setCl(trsresultset
								.getString(Constants.ZHU_QUAN_XIANG));
						patentInfo.setFslx(trsresultset.getString(Constants.FSLX));
						patentInfo.setTitleJp(trsresultset.getString(Constants.MING_CHEN_RI));
						patentInfo.setAbJp(trsresultset.getString(Constants.ZHAI_YAO_RI));
						// switch (getInfoType) {
						// /** 国内细缆特有信息 */
						// case 1:
						patentInfo.setAr(trsresultset
								.getString(Constants.DI_ZHI));
						patentInfo.setAgc(trsresultset
								.getString(Constants.ZHUAN_LI_DAI_LJG));
						patentInfo.setAgt(trsresultset
								.getString(Constants.DAI_LI_REN));
					}
					 String distributePath = (trsresultset.getString(Constants.FA_BU_LU_J) == null) ? "": 
						 trsresultset.getString(Constants.FA_BU_LU_J).replaceAll("\\\\", "/");
					 patentInfo.setTifDistributePath(distributePath);
					 patentInfo.setPages(trsresultset.getString(Constants.YE_SHU));
					// 智能检索相关度
					// double relevance = trsresultset.getRelevance();
					// if (relevance >= 0.02) {
					// relevance = relevance - 0.02;
					// }
					// patentInfo.setRelevance(PssUtil.getPercent(relevance
					// * lastSimValue));
					patentInfo.setSectionName(trsresultset.getRecord().strName);
					patentInfoList.add(patentInfo);

				} while (trsresultset.moveNext() && ++i < endIndex);
				dto.setPatentInfoList(patentInfoList);
				// dto.setSectionInfos(sectionInfos);
			}

		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatBatchDownloadSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
		long end = System.currentTimeMillis();
		logger.info("PSS-概览检索：" + (end - start));
		return dto;
	}

}
