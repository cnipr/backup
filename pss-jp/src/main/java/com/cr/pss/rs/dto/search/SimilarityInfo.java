package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class SimilarityInfo implements Serializable {

	private static final long serialVersionUID = 884176029594572675L;

	private String targetId;

	private String similarity;

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getSimilarity() {
		return similarity;
	}

	public void setSimilarity(String similarity) {
		this.similarity = similarity;
	}
}
