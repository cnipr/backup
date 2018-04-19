package com.cnipr.pss.search.enimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.search.LegalStatusInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.eprobiti.trs.TRSException;

/**
 * 法律状态检索人
 * @author lq
 */
public class EnPatLegalStatusSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(EnPatLegalStatusSearcher.class);
	
	/** 检索条件表达式 **/
	private String strWhere;
	/** 开始下标 **/
	private long startIndex;
	/** 结束下标 **/
	private long endIndex;

	public EnPatLegalStatusSearcher(String strWhere, long startIndex,
			long endIndex) {
		this.searchType = 1;
		this.strWhere = strWhere;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		
		logger.info("[EnPatLegalStatusSearcher]strWhere=" + strWhere + ";startIndex="
				+ startIndex + ";endIndex=" + endIndex);
	}

	/**
	 * 法律状态检索
	 */
	@Override
	public LegalStatusSearchDTO doSearch() {
		List<LegalStatusInfo> legalStatusInfoList = new ArrayList<LegalStatusInfo>();
		LegalStatusSearchDTO dto = new LegalStatusSearchDTO();
		try {
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					"CNPRS_EN", strWhere, "-Legal_Status_PD", "", "Legal_Status,Legal_Status_Info", 0, 0,
					false, "");
			long recordCount = trsresultset.getRecordCount();
			dto.setRecordCount(recordCount);
			if (trsresultset != null && trsresultset.getRecordCount() > 0) {

				trsresultset.moveFirst();

				if (endIndex > recordCount) {
					endIndex = recordCount;
				}

				for (long i = startIndex; i < endIndex; i++) {
					trsresultset.moveTo(0, i);

					LegalStatusInfo legalStatusInfo = new LegalStatusInfo();
					legalStatusInfo.setIndex(trsresultset.getPosition() + 1);
					legalStatusInfo.setStrAn(trsresultset.getString("AP"));
					legalStatusInfo.setStrLegalStatus(trsresultset
							.getString("Legal_Status"));
					legalStatusInfo.setStrStatusInfo(trsresultset
							.getString("Legal_Status_Info"));
					legalStatusInfo.setStrLegalStatusDay(trsresultset
							.getString("Legal_Status_PD"));
//					legalStatusInfo.setStrAccredit(trsresultset
//							.getString("授权公告号"));
					legalStatusInfoList.add(legalStatusInfo);
				}

			}
			
			dto.setLegalStatusInfoList(legalStatusInfoList);
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnPatLegalStatusSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}

		return dto;
	}

}
