package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cnipr.pss.rs.dto.search.SimilarityInfo;

public class PatSimilarityDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = -1088180158683152502L;
	
	private List<SimilarityInfo> similaritys;
	
	public PatSimilarityDTO() {
		super();
		similaritys = new ArrayList<SimilarityInfo>();
	}

	public List<SimilarityInfo> getSimilaritys() {
		return similaritys;
	}

	public void setSimilaritys(List<SimilarityInfo> similaritys) {
		this.similaritys = similaritys;
	}

}
