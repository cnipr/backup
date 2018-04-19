package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.entity.search.Rexam;

public class RexamDTO extends AbstractSearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Rexam> rexamList;
	
	private Long totalCount;
	
	/**
	 * @return the rexamList
	 */
	public List<Rexam> getRexamList() {
		return rexamList;
	}
	/**
	 * @param rexamList the rexamList to set
	 */
	public void setRexamList(List<Rexam> rexamList) {
		this.rexamList = rexamList;
	}
	/**
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

}
