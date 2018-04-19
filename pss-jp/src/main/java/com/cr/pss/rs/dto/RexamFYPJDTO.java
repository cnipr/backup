package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

import com.cnipr.pss.rs.dto.search.RexamFYPJ;
/**
 * 法院判决DTO
 * @author chubanshe
 *
 */
public class RexamFYPJDTO extends AbstractSearchDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RexamFYPJ> rexamFypjList;
	
	private Long totalCount;

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

	/**
	 * @return the rexamFypjList
	 */
	public List<RexamFYPJ> getRexamFypjList() {
		return rexamFypjList;
	}

	/**
	 * @param rexamFypjList the rexamFypjList to set
	 */
	public void setRexamFypjList(List<RexamFYPJ> rexamFypjList) {
		this.rexamFypjList = rexamFypjList;
	}

}
