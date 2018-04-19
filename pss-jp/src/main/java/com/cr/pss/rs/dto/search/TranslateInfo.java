package com.cnipr.pss.rs.dto.search;

import java.io.Serializable;

public class TranslateInfo implements Serializable {

	private static final long serialVersionUID = 3443491897015044252L;

	private String an;
	private String tiEN;
	private String tiCN;
	private String abEN;
	private String abCN;
	private String paEN;
	private String paCN;
	private String clEN;
	private String clCN;
	private String innEN;
	private String innCN;

	public String getInnEN() {
		return innEN;
	}

	public void setInnEN(String innEN) {
		this.innEN = innEN;
	}

	public String getInnCN() {
		return innCN;
	}

	public void setInnCN(String innCN) {
		this.innCN = innCN;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getClEN() {
		return clEN;
	}

	public void setClEN(String clEN) {
		this.clEN = clEN;
	}

	public String getClCN() {
		return clCN;
	}

	public void setClCN(String clCN) {
		this.clCN = clCN;
	}

	public String getTiEN() {
		return tiEN;
	}

	public void setTiEN(String tiEN) {
		this.tiEN = tiEN;
	}

	public String getTiCN() {
		return tiCN;
	}

	public void setTiCN(String tiCN) {
		this.tiCN = tiCN;
	}

	public String getAbEN() {
		return abEN;
	}

	public void setAbEN(String abEN) {
		this.abEN = abEN;
	}

	public String getAbCN() {
		return abCN;
	}

	public void setAbCN(String abCN) {
		this.abCN = abCN;
	}

	public String getPaEN() {
		return paEN;
	}

	public void setPaEN(String paEN) {
		this.paEN = paEN;
	}

	public String getPaCN() {
		return paCN;
	}

	public void setPaCN(String paCN) {
		this.paCN = paCN;
	}

}
