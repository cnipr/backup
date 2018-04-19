package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatCantrastSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.Constants;
import com.eprobiti.trs.TRSException;

/**
 * 细缆检索人 专利细缆检索，这里取出的为普通著录项信息，包括收起选项内容
 * 
 * @author lq
 * 
 */
public class PatCantrastSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatCantrastSearcher.class);

	/** 对比专利列表，格式：an1:db1;an2:db2;...... **/
	private String strContrastList;

	public PatCantrastSearcher(String strContrastList) {
		/** 走语义检索接口 */
		this.searchType = 1;
		this.strContrastList = strContrastList;

		logger.info("[PatCantrastSearcher]strContrastList=" + strContrastList);
	}

	/**
	 * 细缆检索
	 */
	@Override
	public PatCantrastSearchDTO doSearch() {
		PatCantrastSearchDTO dto = new PatCantrastSearchDTO();
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			String[] contrastList = strContrastList.split(";");
			List<PatentInfo> patentInfoList = new ArrayList<PatentInfo>();

			for (String pat : contrastList) {
				String[] info = pat.split(":");

				trsresultset = (new TRSOperation()).executeTRSSearch(
						trsConnection, info[1], "an=" + info[0], "", "", "", 0,
						0, false, "");
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
							trsresultset.moveTo(0, 0);

							// sectionName = trsresultset
							// .getSectionInfo((int)
							// trsresultset.getRecordSect()).strName;
							patentInfo.setSectionName(info[1]);
							patentInfo
									.setSysid(trsresultset.getString("sysid"));
							patentInfo.setAppid(trsresultset
									.getString("app_id"));
							// patentInfo.setIndex(Long.toString(recordCursor));
							String patentName = trsresultset.getString(
									Constants.MING_CHENG);
							patentName = patentName.replace("\"", "");
							patentInfo.setTi(patentName);
							patentInfo.setAn(trsresultset
									.getString(Constants.SHEN_QING_HAO));
							patentInfo.setAd(trsresultset.getString(
									Constants.SHENG_QING_RI));
							patentInfo.setPd(trsresultset.getString(
									Constants.GONG_KAI_RI));
							patentInfo.setPnm(trsresultset
									.getString(Constants.GONG_KAI_HAO));
							patentInfo.setPn(trsresultset
									.getString(Constants.ZHUAN_LI_HAO));
							patentInfo.setPa(trsresultset.getString(
									Constants.SHEN_QING_REN));
							patentInfo.setPin(trsresultset.getString(
									Constants.FA_MING_REN));
							patentInfo.setCo(trsresultset.getString(
									Constants.GUO_SHENG_DAI_M));
							patentInfo.setSic(trsresultset.getString(
									Constants.FEN_LEI_HAO));
							patentInfo.setPic(trsresultset.getString(
									Constants.ZHU_FEN_LEI_H));
							patentInfo.setPr(trsresultset
									.getString(Constants.YOU_XIAN_QUAN));
							patentInfo.setAb(trsresultset.getString(
									Constants.ZHAI_YAO));
							patentInfo.setCl(trsresultset.getString(
									Constants.ZHU_QUAN_XIANG));
							// switch (getInfoType) {
							// /** 国内细缆特有信息 */
							// case 1:
							patentInfo.setAr(trsresultset.getString(
									Constants.DI_ZHI));
							patentInfo.setIan(trsresultset
									.getString(Constants.GUO_JI_SHEN_Q));
							patentInfo.setIpn(trsresultset
									.getString(Constants.GUO_JI_GONG_B));
							patentInfo.setDen(trsresultset
									.getString(Constants.GUO_JIA_RI_Q));
							patentInfo.setAgc(trsresultset.getString(
									Constants.ZHUAN_LI_DAI_LJG));
							patentInfo.setAgt(trsresultset.getString(
									Constants.DAI_LI_REN));
							patentInfo.setBzr(trsresultset
									.getString(Constants.BAN_ZHENG_RI));
							patentInfo.setDan(trsresultset
									.getString(Constants.FEN_AN_YUAN_SQH));
							patentInfo.setExaminant(trsresultset
									.getString(Constants.SHEN_CHA_YUAN));
							patentInfo.setFcic(trsresultset
									.getString(Constants.FAN_CHOU_FEN_L));
							patentInfo
									.setTifDistributePath((trsresultset
											.getString(Constants.FA_BU_LU_J) == null) ? ""
											: trsresultset.getString(
													Constants.FA_BU_LU_J)
													.replaceAll("\\\\", "/"));
							patentInfo.setPages(trsresultset
									.getString(Constants.YE_SHU));
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
							// }
						} catch (TRSException e) {
							new PSSException("2002", e.getErrorCode() + ":"
									+ e.getMessage(), e.getMessage(),
									"PatCantrastSearcher", "doSearch", "trs");
							dto.setReturncode(2002);
							dto.setMessage(e.getMessage());
						}
					}
					patentInfoList.add(patentInfo);
				}

				dto.setPatentInfoList(patentInfoList);
				dto.setRecordCount(patentInfoList.size());

			}
		} catch (TRSException e) {
			trsresultset = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "PatCantrastSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}

		return dto;
	}

	public String getStrContrastList() {
		return strContrastList;
	}

	public void setStrContrastList(String strContrastList) {
		this.strContrastList = strContrastList;
	}

}
