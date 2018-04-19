package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.cnipr.pss.rs.dto.search.PatPrsTransferInfo;

@XmlRootElement
public class PatPrsTransferDTO extends AbstractSearchDTO implements
		Serializable {

	private static final long serialVersionUID = 6154553691318597946L;
	
	// 返回一页转移信息
	private List<PatPrsTransferInfo> prsTransInfoList;
	// 总记录数
	private long totalCount;

	public List<PatPrsTransferInfo> getPrsTransInfoList() {
		return prsTransInfoList;
	}

	public void setPrsTransInfoList(List<PatPrsTransferInfo> prsTransInfoList) {
		this.prsTransInfoList = prsTransInfoList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
