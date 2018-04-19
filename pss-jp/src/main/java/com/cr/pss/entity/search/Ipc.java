package com.cnipr.pss.entity.search;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cnipr.pss.entity.PssEntity;

@Entity
@Table(name = "MET_TB_IPC_DIC")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ipc extends PssEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926233164160042345L;

	private String ipc;

	private String parentipc;

	private String note;
	
	private String notejp;
	
	private String noteen;

	private Long ishaschild;

	public String getIpc() {
		return ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getParentipc() {
		return parentipc;
	}

	public void setParentipc(String parentipc) {
		this.parentipc = parentipc;
	}

	public Long getIshaschild() {
		return ishaschild;
	}

	public void setIshaschild(Long ishaschild) {
		this.ishaschild = ishaschild;
	}

	public String getNotejp() {
		return notejp;
	}

	public void setNotejp(String notejp) {
		this.notejp = notejp;
	}

	public String getNoteen() {
		return noteen;
	}

	public void setNoteen(String noteen) {
		this.noteen = noteen;
	}

}
