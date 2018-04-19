package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.cnipr.pss.rs.dto.search.PatPrsPreservationInfo;

@XmlRootElement
public class PatPrsPreservationDTO extends AbstractSearchDTO implements
		Serializable {

	private static final long serialVersionUID = 6154553691318597946L;

	// 返回一页转移信息
	private List<PatPrsPreservationInfo> prsPreservationInfoList;
	// 总记录数
	private long totalCount;

	public List<PatPrsPreservationInfo> getPrsPreservationInfoList() {
		return prsPreservationInfoList;
	}

	public void setPrsPreservationInfoList(
			List<PatPrsPreservationInfo> prsPreservationInfoList) {
		this.prsPreservationInfoList = prsPreservationInfoList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
