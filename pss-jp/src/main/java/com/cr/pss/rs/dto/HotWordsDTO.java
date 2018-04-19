package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

public class HotWordsDTO extends AbstractSearchDTO implements Serializable {

	private static final long serialVersionUID = -4132852382334891713L;

	private List<String> hotwords;

	public List<String> getHotwords() {
		return hotwords;
	}

	public void setHotwords(List<String> hotwords) {
		this.hotwords = hotwords;
	}
}
