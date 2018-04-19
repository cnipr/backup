package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.RexamFSJD;

public class RexamFSJDDTO extends AbstractSearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RexamFSJD> rexamFsjdList;
	
	private Long totalCount;

	/**
	 * @return the rexamFsjdList
	 */
	public List<RexamFSJD> getRexamFsjdList() {
		return rexamFsjdList;
	}

	/**
	 * @param rexamFsjdList the rexamFsjdList to set
	 */
	public void setRexamFsjdList(List<RexamFSJD> rexamFsjdList) {
		this.rexamFsjdList = rexamFsjdList;
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
