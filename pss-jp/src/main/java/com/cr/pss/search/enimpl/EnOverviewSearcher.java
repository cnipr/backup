package com.cnipr.pss.search.enimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.rs.dto.search.SectionInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.search.impl.PatOverviewSearcher;
import com.cnipr.pss.util.GetEnSearchFormat;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;

public class EnOverviewSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(EnOverviewSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	/** 结果记录的排序方式 **/
	private String strSortMethod;
	/** 检索选项掩码 */
	private int iOption;
	/** 是否进行同义词扩展 **/
	private String strSynonymous;
	/** 开始下标 **/
	private long startIndex;
	/** 结束下标 **/
	private long endIndex;
	/** 基于某单库进行检索 **/
	private String strSection;
	/** 概览要取的字段值 **/
	private String displayCols;

	public EnOverviewSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long startIndex, long endIndex,
			String strSection, String strSynonymous, String displayCols) {
		this.searchType = 1;
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
		OverviewSearchDTO dto = new OverviewSearchDTO();
		try {
			strWhere = (new GetEnSearchFormat()).preprocess(strWhere);
			if (strSection != null && !strSection.equals("")) {
				trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
						strSources, strWhere, "", "", "", iOption,
						TRSConstant.TCE_OFFSET, false, strSynonymous);
			} else {
				trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
						strSources, strWhere, strSortMethod, "", "", iOption,
						TRSConstant.TCE_OFFSET, false, strSynonymous);
			}
			
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
					trsresultset = (new TRSOperation()).executeTRSSearch(
							trsConnection, strSection, strWhere, strSortMethod,
							"", "", iOption, TRSConstant.TCE_OFFSET, false,
							strSynonymous);
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
						endIndex = 0;
					}

				}
				// 取出本次需要的全部记录
				trsresultset.moveTo(0, startIndex);
				// 取出本次需要的全部记录
				long i = startIndex;

				do {
					// // 移动到检索结果记录集的指定记录
					// trsresultset.moveTo(0, i);
					// 构造专利信息
					PatentInfo patentInfo = new PatentInfo();
					patentInfo.setIndex(Long.toString(i));
					// if (strSources.toUpperCase().startsWith("CNFMSQ_EN")) {
					// patentInfo.setPd(trsresultset.getString("GD"));
					// } else {
					if (displayCols != null && !displayCols.equals("")) {
					} else {
						/**
						 * 检索关键词反色显示
						 */
						TRSRecord rec = trsresultset.getRecord();
						String sectionName = rec.strName;
						patentInfo.setSectionName(sectionName);

						if (sectionName.equalsIgnoreCase("CNFMZL_EN")
								|| sectionName.equalsIgnoreCase("CNFMSQ_EN")
								|| sectionName.equalsIgnoreCase("CNSYXX_EN")
								|| sectionName.equalsIgnoreCase("CNWGZL_EN")) {
							patentInfo.setSysid(trsresultset.getString("SYSID"));
							// patentInfo.setAppid(trsresultset.getString("APP_ID"));
							patentInfo.setTi(trsresultset.getString("TI", "red"));
							patentInfo.setAn(trsresultset.getString("AP"));
							patentInfo.setPnm(trsresultset.getString("PN"));
							patentInfo.setPin(trsresultset.getString("INN"));
							patentInfo.setPd(trsresultset.getString("PD"));
							patentInfo.setAd(trsresultset.getString("AD"));
							patentInfo.setSic(trsresultset.getString("IPC"));
							patentInfo.setPr(trsresultset.getString("PRI"));
							patentInfo.setAb(trsresultset.getString("ABS", "red"));
							patentInfo.setPa(trsresultset.getString("PA"));
							String distributePath = (trsresultset
									.getString("PUB_Path") == null) ? ""
									: trsresultset.getString("PUB_Path")
											.replaceAll("\\\\", "/");
							patentInfo.setTifDistributePath(distributePath);
							patentInfo.setPages(trsresultset.getString("pages"));
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
							patentInfo.setTi(trsresultset.getString("TI", "red"));
							patentInfo.setAn(trsresultset.getString("AN"));
							patentInfo.setPnm(trsresultset.getString("PNM"));
							patentInfo.setPd(trsresultset.getString("PD"));
							patentInfo.setAd(trsresultset.getString("AD"));
							patentInfo.setSic(trsresultset.getString("SIC"));
							patentInfo.setPr(trsresultset.getString("PR"));
							patentInfo.setAb(trsresultset.getString("ABST", "red"));
							patentInfo.setPa(trsresultset.getString("PA"));
						}

						
					}
					// patentInfo.setXMLPATH((trsresultset
					// .getString("XMLPATH") == null) ? ""
					// : trsresultset.getString("XMLPATH")
					// .replaceAll("\\\\", "/"));
					// patentInfo.setXMLPATH("");
					// }
					patentInfoList.add(patentInfo);
				} while (trsresultset.moveNext() && ++i < endIndex);
				dto.setPatentInfoList(patentInfoList);
				dto.setSectionInfos(sectionInfos);
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnOverviewSearcher", "doSearch", "trs");
		} catch (Exception e) {
			new PSSException("3002", e.getMessage(), e.getMessage(),
					"EnOverviewSearcher", "doSearch", "trs");
		}

		return dto;
	}
}
