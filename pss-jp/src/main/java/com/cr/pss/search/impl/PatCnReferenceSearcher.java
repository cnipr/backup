package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.CnReferencesDTO;
import com.cnipr.pss.rs.dto.search.CnReferencesInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.eprobiti.trs.TRSException;

public class PatCnReferenceSearcher extends AbstractSearcher {
	private String pn;
	
	public PatCnReferenceSearcher() {
	}
	
	public PatCnReferenceSearcher(String pn) {
		this.pn = pn;
		this.searchType = 1;
	}

	@Override
	public CnReferencesDTO doSearch() {
		CnReferencesDTO dto = new CnReferencesDTO();
		
		if (pn.indexOf(".") > -1) {
			pn = pn.substring(0, pn.indexOf("."));
		}
		// 审查员引证专利
		String strYZSearchWord1 = "公开（公告）号=" + pn
				+ "% and clito=patentCitation";
		// 审查员引证非专利
		String strYZSearchWord2 = "公开（公告）号=" + pn
				+ "% and clito=literatureCitation";
		// 申请人引证专利
		String strYZSearchWord3 = "公开（公告）号=" + pn
				+ "% and clito=applicatCitation";
//		pnm = pnm.replace("CN", "");
//		// 审查员被引证
//		String strYZSearchWord4 = "公开（公告）号=CN" + pnm
//				+ "% and 文献标记=patentCitationed";
//		// 申请人被引证
//		String strYZSearchWord5 = "公开（公告）号=CN" + pnm
//				+ "% and 文献标记=applicatCitationed";

		// System.out.println(strYZSearchWord4);
		// System.out.println(strYZSearchWord5);

		try {
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					"yzwx_new", strYZSearchWord1, "", "", "", 0, 0, false, "");
			if (trsresultset != null && trsresultset.getRecordCount() > 0) {
				List<CnReferencesInfo> yzzlList = new ArrayList<CnReferencesInfo>();
				for (long i = 0; i < trsresultset.getRecordCount(); i++) {
					trsresultset.moveTo(0, i);
					CnReferencesInfo yzInfo = new CnReferencesInfo();
					yzInfo.setApplicationNumber(trsresultset.getString("申请号"));
//					yzInfo.setCitedPubNumber(trsresultset.getString("被引证文献公布号"));
					yzInfo.setCitedPubDate(trsresultset.getString("被引证文献公布日"));
					yzInfo.setPublicationNumber(trsresultset.getString("公开（公告）号"));
					yzInfo.setPublicationDate(trsresultset.getString("公开（公告）日"));
					yzInfo.setCitedPatent(trsresultset.getString("被引证文献"));
					
					yzInfo.setCitedNumType(trsresultset.getString("被引证文献号码类型"));
					yzInfo.setCitedIpc(trsresultset.getString("被引证专利文献分类号"));
					yzInfo.setCitedTi(trsresultset.getString("被引证专利文献名称"));
					yzInfo.setCitedPa(trsresultset.getString("被引证专利文献申请人"));
					yzInfo.setCitedCo(trsresultset.getString("被引证文献国别"));
					yzInfo.setCitedN(trsresultset.getString("被引证文献流水号"));
					yzInfo.setCitedNk(trsresultset.getString("被引证文献类型"));
					yzInfo.setCitedSn(trsresultset.getString("标准被引证文献流水号"));
					yzzlList.add(yzInfo);
				}
				dto.setScyyzzlList(yzzlList);
			}
			
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					"yzwx_new", strYZSearchWord3, "", "", "", 0, 0, false, "");
			if (trsresultset != null && trsresultset.getRecordCount() > 0) {
				List<CnReferencesInfo> yzzlList = new ArrayList<CnReferencesInfo>();
				for (long i = 0; i < trsresultset.getRecordCount(); i++) {
					trsresultset.moveTo(0, i);
					CnReferencesInfo yzInfo = new CnReferencesInfo();
					yzInfo.setApplicationNumber(trsresultset.getString("申请号"));
//					yzInfo.setCitedPubNumber(trsresultset.getString("被引证文献公布号"));
					yzInfo.setCitedPubDate(trsresultset.getString("被引证文献公布日"));
					yzInfo.setPublicationNumber(trsresultset.getString("公开（公告）号"));
					yzInfo.setPublicationDate(trsresultset.getString("公开（公告）日"));
					yzInfo.setCitedPatent(trsresultset.getString("被引证文献"));
					
					yzInfo.setCitedNumType(trsresultset.getString("被引证文献号码类型"));
					yzInfo.setCitedIpc(trsresultset.getString("被引证专利文献分类号"));
					yzInfo.setCitedTi(trsresultset.getString("被引证专利文献名称"));
					yzInfo.setCitedPa(trsresultset.getString("被引证专利文献申请人"));
					yzInfo.setCitedCo(trsresultset.getString("被引证文献国别"));
					yzInfo.setCitedN(trsresultset.getString("被引证文献流水号"));
					yzInfo.setCitedNk(trsresultset.getString("被引证文献类型"));
					yzInfo.setCitedSn(trsresultset.getString("标准被引证文献流水号"));
					yzzlList.add(yzInfo);
				}
				dto.setSqryzzlList(yzzlList);
			}
			
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					"yzwx_new", strYZSearchWord2, "", "", "", 0, 0, false, "");
			if (trsresultset != null && trsresultset.getRecordCount() > 0) {
				List<CnReferencesInfo> yzzlList = new ArrayList<CnReferencesInfo>();
				for (long i = 0; i < trsresultset.getRecordCount(); i++) {
					trsresultset.moveTo(0, i);
					CnReferencesInfo yzInfo = new CnReferencesInfo();
					yzInfo.setApplicationNumber(trsresultset.getString("申请号"));
//					yzInfo.setCitedPubNumber(trsresultset.getString("被引证文献公布号"));
					yzInfo.setCitedPubDate(trsresultset.getString("被引证文献公布日"));
					yzInfo.setPublicationNumber(trsresultset.getString("公开（公告）号"));
					yzInfo.setPublicationDate(trsresultset.getString("公开（公告）日"));
					yzInfo.setCitedPatent(trsresultset.getString("被引证文献"));
					
					yzInfo.setCitedNumType(trsresultset.getString("被引证文献号码类型"));
					yzInfo.setCitedIpc(trsresultset.getString("被引证专利文献分类号"));
					yzInfo.setCitedTi(trsresultset.getString("被引证专利文献名称"));
					yzInfo.setCitedPa(trsresultset.getString("被引证专利文献申请人"));
					yzInfo.setCitedCo(trsresultset.getString("被引证文献国别"));
					yzInfo.setCitedN(trsresultset.getString("被引证文献流水号"));
					yzInfo.setCitedNk(trsresultset.getString("被引证文献类型"));
					yzInfo.setCitedSn(trsresultset.getString("标准被引证文献流水号"));
					yzzlList.add(yzInfo);
				}
				dto.setScyyzfzlList(yzzlList);
			}
			
			
		} catch (TRSException e) {
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"PatCnReferenceSearcher", "doSearch(strSources:yzwx;an:" + pn,
					"java");
			dto.setReturncode(1000);
			dto.setMessage(e.getMessage());
		}
		// TODO Auto-generated method stub
		return dto;
	}
}
