package com.cnipr.pss.search.enimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.search.impl.PatTifPathSearcher;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.GetEnSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;

/**
 * 细缆检索人 专利细缆检索，这里取出的为普通著录项信息，包括收起选项内容
 * 
 * @author lq
 * 
 */
public class EnPatDetailSearcher extends AbstractSearcher {

	private static final Logger logger = LoggerFactory
			.getLogger(EnPatDetailSearcher.class);

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

	public EnPatDetailSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long recordCursor,
			String strSynonymous, int isSimilarSearch) {
		/** 走语义检索接口 */
		this.searchType = 1;
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
			strWhere = (new GetEnSearchFormat()).preprocess(strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, strSortMethod, "", "", iOption,
					TRSConstant.TCE_OFFSET, false, strSynonymous);
		} catch (TRSException e) {
			trsresultset = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnPatDetailSearcher", "doSearch", "trs");
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

					sectionName = trsresultset
							.getSectionInfo((int) trsresultset.getRecordSect()).strName;
					patentInfo.setSectionName(sectionName);
					if (sectionName.equalsIgnoreCase("CNFMZL_EN")
							|| sectionName.equalsIgnoreCase("CNFMSQ_EN")
							|| sectionName.equalsIgnoreCase("CNSYXX_EN")
							|| sectionName.equalsIgnoreCase("CNWGZL_EN")) {
						patentInfo.setSysid(trsresultset.getString("SYSID"));
						// patentInfo.setAppid(trsresultset.getString("APP_ID"));
						patentInfo.setIndex(Long.toString(recordCursor));
						String patentName = trsresultset.getString("TI", "red");
						patentName = patentName.replace("\"", "");
						patentInfo.setTi(patentName);
						patentInfo.setAn(trsresultset.getString("AP"));
						patentInfo.setAd(trsresultset.getString("AD"));
						patentInfo.setPd(trsresultset.getString("PD"));
						patentInfo.setPnm(trsresultset.getString("PN"));
						patentInfo.setPa(trsresultset.getString("PA"));
						patentInfo.setPin(trsresultset.getString("INV"));
						patentInfo.setCo(trsresultset.getString("CC"));
						patentInfo.setSic(trsresultset.getString("IPC"));
						patentInfo.setPic(trsresultset.getString("MAIN_IPC"));
						patentInfo.setPr(trsresultset.getString("PRI"));
						patentInfo.setAb(trsresultset.getString("ABS", "red"));
						/** 获取发明专利或发明授权的说明书（tif）的路径及页数 */
						if (!sectionName.startsWith("CNFMSQ")) {
							patentInfo.setTifDistributePath((trsresultset
									.getString("PUB_Path") == null) ? ""
									: trsresultset.getString("PUB_Path")
											.replaceAll("\\\\", "/"));
							patentInfo
									.setPages(trsresultset.getString("pages"));
							// /** 发明授权的说明书（tif）的路径及页数需要再进行一次Trs检索 */
							@SuppressWarnings("unchecked")
							List<String> tifInfo = (List<String>) (new EnPatTifPathSearcher(
									"CNFMSQ_EN", trsresultset.getString("AP")))
									.excute();
							if (tifInfo != null) {
								patentInfo.setSqTifPath(tifInfo.get(0));
								patentInfo.setSqTifPages(tifInfo.get(1));
							}
						} else {
							patentInfo.setSqTifPath((trsresultset
									.getString("PUB_Path") == null) ? ""
									: trsresultset.getString("PUB_Path")
											.replaceAll("\\\\", "/"));
							patentInfo.setSqTifPages(trsresultset
									.getString("pages"));
							@SuppressWarnings("unchecked")
							/** 发明专利的说明书（tif）的路径及页数需要再进行一次Trs检索 */
							List<String> tifInfo = (List<String>) (new EnPatTifPathSearcher(
									"CNFMZL_EN", trsresultset.getString("AP")))
									.excute();
							if (tifInfo != null) {
								patentInfo.setTifDistributePath(tifInfo.get(0));
								patentInfo.setPages(tifInfo.get(1));
							}
						}
						patentInfo.setIpn(trsresultset.getString("IPN"));
						patentInfo.setAr(trsresultset.getString("AR", "red"));
						patentInfo.setAgc(trsresultset.getString("AGENCY", "red"));
						patentInfo.setAgt(trsresultset.getString("AGENT", "red"));
						patentInfo.setPaTranslateType(trsresultset
								.getString("Translat_Type"));
						String ap = trsresultset.getString("AP").toUpperCase()
								.replaceAll("CN", "");
						String strANShort = ap;
						if (ap.indexOf(".") > -1) {
							strANShort = ap.substring(0, ap.indexOf("."));
						}
						patentInfo.setXMLPATH(trsresultset
								.getString("PATENT_TYPE")
								+ "/"
								+ trsresultset.getString("PD").replace(".", "")
								+ "/" + ap);
					} else {
						patentInfo.setSysid(trsresultset.getString("SYSID"));
						// patentInfo.setAppid(trsresultset.getString("APP_ID"));
						patentInfo.setIndex(Long.toString(recordCursor));
						String patentName = trsresultset.getString("TI", "red");
						patentName = patentName.replace("\"", "");
						patentInfo.setTi(patentName);
						patentInfo.setAn(trsresultset.getString("AN"));
						patentInfo.setAd(trsresultset.getString("AD"));
						patentInfo.setPd(trsresultset.getString("PD"));
						patentInfo.setPnm(trsresultset.getString("PNM"));
						patentInfo.setPa(trsresultset.getString("PA"));
						patentInfo.setPin(trsresultset.getString("INN"));
						patentInfo.setCo("");
						patentInfo.setSic(trsresultset.getString("SIC"));
						patentInfo.setPic(trsresultset.getString("PIC"));
						patentInfo.setPr(trsresultset.getString("PR"));
						patentInfo.setAb(trsresultset.getString("ABST", "red"));
						patentInfo.setAr(trsresultset.getString("AR", "red"));
					}
					// patentInfo.setSysid(trsresultset.getString("sysid"));
					// patentInfo.setAppid(trsresultset.getString("app_id"));

					// 取机器翻译的全文路径（相对）
					// patentInfo.setCLMSplitPagePath((trsresultset
					// .getString("CLM_SplitPage_Path") == null) ? ""
					// : trsresultset.getString("CLM_SplitPage_Path")
					// .replaceAll("\\\\", "/"));
					// patentInfo.setCLMSplitPageCount(trsresultset
					// .getString("CLM_SplitPage_Count"));
					// patentInfo.setDESSplitPagePath((trsresultset
					// .getString("DES_SplitPage_Path") == null) ? ""
					// : trsresultset.getString("DES_SplitPage_Path")
					// .replaceAll("\\\\", "/"));
					// patentInfo.setDESSplitPageCount(trsresultset
					// .getString("DES_SplitPage_Count"));
					// patentInfo.setXMLPATH((trsresultset
					// .getString("XMLPATH") == null) ? ""
					// : trsresultset.getString("XMLPATH")
					// .replaceAll("\\\\", "/"));
					// patentInfo.setInvTranslateType(trsresultset
					// .getString("Inv_Translate_Type"));
					// patentInfo.setTiTranslateType(trsresultset
					// .getString("Ti_Translate_Type"));
					// patentInfo.setAbsTranslateType(trsresultset
					// .getString("Abs_Translate_Type"));
				} catch (TRSException e) {
					new PSSException("2002", e.getErrorCode() + ":"
							+ e.getMessage(), e.getMessage(),
							"EnPatDetailSearcher", "doSearch", "trs");
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
