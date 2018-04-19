package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.LegalStatusInfo;
/**
 * 法律状态信息DTO
 * @author lq
 *
 */
public class LegalStatusSearchDTO extends AbstractSearchDTO implements Serializable{

	private static final long serialVersionUID = 1735575279693108706L;
	
	private List<LegalStatusInfo> legalStatusInfoList;
	
	/** 检索的总记录数 */
	private long recordCount;

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public List<LegalStatusInfo> getLegalStatusInfoList() {
		return legalStatusInfoList;
	}

	public void setLegalStatusInfoList(List<LegalStatusInfo> legalStatusInfoList) {
		this.legalStatusInfoList = legalStatusInfoList;
	}

}
