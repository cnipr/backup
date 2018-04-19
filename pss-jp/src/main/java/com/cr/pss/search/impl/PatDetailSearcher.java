package com.cnipr.pss.search.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.GetSearchFormat;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;

/**
 * 细缆检索人 专利细缆检索，这里取出的为普通著录项信息，包括收起选项内容
 * 
 * @author lq
 * 
 */
public class PatDetailSearcher extends AbstractSearcher {
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

	// /**
	// * 命中点类型
	// **/
	// private int iHitPointType;
	//
	// /** 是否二次检索(即是否在前次检索的结果上继续检索)，如果为true则为二次检索 **/
	// private boolean bContinue;

	/** 本次检索专利的下标 **/
	private long recordCursor;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	public PatDetailSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long recordCursor,
			String strSynonymous, int isSimilarSearch) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.isSimilarSearch = isSimilarSearch;
		// this.iHitPointType = iHitPointType;
		// this.bContinue = bContinue;
		this.recordCursor = recordCursor;
		this.strSynonymous = strSynonymous;

		logger.info("[PatDetailSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";isSimilarSearch=" + isSimilarSearch
				+ ";recordCursor=" + recordCursor + ";strSynonymous="
				+ strSynonymous);
	}

	/**
	 * 细缆检索
	 */
	@Override
	public DetailSearchDTO doSearch() {
		DetailSearchDTO detail = new DetailSearchDTO();
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
//			tempLastWhere = "(申请号='cn01808621.7')";
			trsresultset = (new SemanticSearchOperation()).getSemanticResult(
					strSources, tempLastWhere, strSortMethod, iOption, 115,
					false, strSynonymous, similarWhere, "", semanticInstance);
		} catch (TRSException e) {
			trsresultset = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "DetailSearcher", "doSearch", "trs");
			detail.setReturncode(2002);
			detail.setMessage(e.getMessage());
		}

		if (trsresultset != null) {
			// 检索的总记录数
			long recordCount = trsresultset.getRecordCount();
			// 检索的专利信息列表
			PatentInfo patentInfo = null;
			// 数据库/视图信息列表
			String sectionName = "";
			// 当有检索结果时
			if (recordCount > 0) {
				patentInfo = new PatentInfo();
				try {
					if (recordCursor >= 0) {
						// 移动到检索结果记录集的指定记录
						trsresultset.moveTo(0, recordCursor);
					} else {
						trsresultset.moveTo(0, 0);
					}

					// sectionName = trsresultset
					// .getSectionInfo((int)
					// trsresultset.getRecordSect()).strName;
					// patentInfo.setSectionName(sectionName);
					patentInfo.setSysid(trsresultset.getString("sysid"));
					patentInfo.setAppid(trsresultset.getString("app_id"));
					patentInfo.setIndex(Long.toString(recordCursor));
					String patentName = trsresultset.getString(
							Constants.MING_CHENG, "red");
					patentName = patentName.replace("\"", "");
					patentInfo.setTi(patentName);
					patentInfo.setAn(trsresultset
							.getString(Constants.SHEN_QING_HAO));
					patentInfo.setAd(trsresultset.getString(
							Constants.SHENG_QING_RI, "red"));
					patentInfo.setPd(trsresultset.getString(
							Constants.GONG_KAI_RI, "red"));
					patentInfo.setPnm(trsresultset
							.getString(Constants.GONG_KAI_HAO));
					patentInfo.setPn(trsresultset
							.getString(Constants.ZHUAN_LI_HAO));
					patentInfo.setPa(trsresultset.getString(
							Constants.SHEN_QING_REN, "red"));
					patentInfo.setPin(trsresultset.getString(
							Constants.FA_MING_REN, "red"));
					patentInfo.setCo(trsresultset.getString(
							Constants.GUO_SHENG_DAI_M, "red"));
					patentInfo.setSic(trsresultset.getString(
							Constants.FEN_LEI_HAO, "red"));
					patentInfo.setPic(trsresultset.getString(
							Constants.ZHU_FEN_LEI_H, "red"));
					patentInfo.setPr(trsresultset
							.getString(Constants.YOU_XIAN_QUAN));
					patentInfo.setAb(trsresultset.getString(Constants.ZHAI_YAO,
							"red"));
					patentInfo.setCl(trsresultset.getString(
							Constants.ZHU_QUAN_XIANG, "red"));
					// switch (getInfoType) {
					// /** 国内细缆特有信息 */
					// case 1:
					patentInfo.setAr(trsresultset.getString(Constants.DI_ZHI,
							"red"));
					patentInfo.setIan(trsresultset
							.getString(Constants.GUO_JI_SHEN_Q));
					patentInfo.setIpn(trsresultset
							.getString(Constants.GUO_JI_GONG_B));
					patentInfo.setDen(trsresultset
							.getString(Constants.GUO_JIA_RI_Q));
					patentInfo.setAgc(trsresultset.getString(
							Constants.ZHUAN_LI_DAI_LJG, "red"));
					patentInfo.setAgt(trsresultset.getString(
							Constants.DAI_LI_REN, "red"));
					patentInfo.setBzr(trsresultset
							.getString(Constants.BAN_ZHENG_RI));
					patentInfo.setDan(trsresultset
							.getString(Constants.FEN_AN_YUAN_SQH));
					patentInfo.setExaminant(trsresultset
							.getString(Constants.SHEN_CHA_YUAN));
					patentInfo.setFcic(trsresultset
							.getString(Constants.FAN_CHOU_FEN_L));
					patentInfo.setPatType(trsresultset
							.getString(Constants.ZHUAN_LI_LEI_X));
					patentInfo.setTrsq(trsresultset.getString(Constants.TRSQ));
					patentInfo.setFslx(trsresultset.getString(Constants.FSLX));
					String tifpath = (trsresultset
							.getString(Constants.FA_BU_LU_J) == null) ? ""
							: trsresultset.getString(Constants.FA_BU_LU_J)
									.replaceAll("\\\\", "/");
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
					sectionName = rec.strName;
					
					patentInfo.setPdfUrl(PssUtil.getPdfUrl(patentInfo.getAn(),
							patentInfo.getPnm(), patentInfo.getAd(),
							patentInfo.getPd(), patentInfo.getPatType(),
							sectionName));
					patentInfo.setSectionName(sectionName);
					/** 获取发明专利或发明授权的说明书（tif）的路径及页数 */
					if (!sectionName.startsWith("FMSQ")) {
						if (tifpath != null) {
							String[] temp = tifpath.split("/");
							if (temp.length >= 4 && temp[3].contains(".")) {
								temp[3] = temp[3].replace(".", "");
							}
							tifpath = "";
							for (String s : temp) {
								tifpath += s + "/";
								;
							}

						}

						patentInfo.setTifDistributePath(tifpath);
						patentInfo.setPages(trsresultset
								.getString(Constants.YE_SHU));
						/** 发明授权的说明书（tif）的路径及页数需要再进行一次Trs检索 */
						@SuppressWarnings("unchecked")
						List<String> tifInfo = (List<String>) (new PatTifPathSearcher(
								"FMSQ", trsresultset.getString("app_id")))
								.excute();

						if (tifInfo != null) {
							patentInfo.setSqTifPath(tifInfo.get(0));
							patentInfo.setSqTifPages(tifInfo.get(1));
						}
					} else {
						if (tifpath != null) {
							String[] temp = tifpath.split("/");
							if (temp.length >= 4 && temp[3].contains(".")) {
								temp[3] = temp[3].replace(".", "");
							}
							tifpath = "";
							for (String s : temp) {
								tifpath += s + "/";
								;
							}

						}

						patentInfo.setSqTifPath(tifpath);
						patentInfo.setSqTifPages(trsresultset
								.getString(Constants.YE_SHU));
						@SuppressWarnings("unchecked")
						/** 发明专利的说明书（tif）的路径及页数需要再进行一次Trs检索 */
						List<String> tifInfo = (List<String>) (new PatTifPathSearcher(
								"FMZL", trsresultset.getString("app_id")))
								.excute();
						if (tifInfo != null) {
							patentInfo.setTifDistributePath(tifInfo.get(0));
							patentInfo.setPages(tifInfo.get(1));
						}
					}
					patentInfo.setGbPath(trsresultset.getString("公报发布路径"));
					patentInfo.setGbIndex(trsresultset.getString("公报所在页"));
					patentInfo.setGbPages(trsresultset.getString("公报翻页信息"));
					// case 2:
					// /** 国外细缆特有信息 */
					patentInfo.setPec(trsresultset
							.getString(Constants.OU_ZHOU_ZHU_FLH));
					patentInfo.setSec(trsresultset
							.getString(Constants.OU_ZHOU_FEN_LH));
					patentInfo.setTrskeyword(trsresultset
							.getString("trskeyword"));
					try {
						patentInfo.setLastLegalStatus(trsresultset
								.getString(Constants.ZUI_XIN_FA_LZT));
					} catch (TRSException e) {
					}
					try {
						patentInfo.setFamily(trsresultset
								.getString("同族专利项"));
						patentInfo.setTiTranslateType(trsresultset
								.getString("MT"));
					} catch (TRSException e) {
					}
					// }
				} catch (TRSException e) {
					new PSSException("2002", e.getErrorCode() + ":"
							+ e.getMessage(), e.getMessage(), "DetailSearcher",
							"doSearch", "trs");
					detail.setReturncode(2002);
					detail.setMessage(e.getMessage());
				}
			}
			detail.setPatentInfo(patentInfo);
			detail.setRecordCount(recordCount);
		}

		return detail;
	}
}
