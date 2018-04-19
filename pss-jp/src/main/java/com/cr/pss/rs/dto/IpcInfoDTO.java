package com.cnipr.pss.rs.dto;

import java.io.Serializable;
import java.util.List;

public class IpcInfoDTO extends AbstractSearchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3926233164160048895L;

	private String ipc;

	private String parentIpc;

	private String note;
	
	private String notejp;
	
	private String noteen;

	private List<IpcInfoDTO> childIpcs;

	private boolean isHasChild;

	private long total;

	public String getIpc() {
		return ipc;
	}

	public void setIpc(String ipc) {
		this.ipc = ipc;
	}

	public String getParentIpc() {
		return parentIpc;
	}

	public void setParentIpc(String parentIpc) {
		this.parentIpc = parentIpc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<IpcInfoDTO> getChildIpcs() {
		return childIpcs;
	}

	public void setChildIpcs(List<IpcInfoDTO> childIpcs) {
		this.childIpcs = childIpcs;
	}

	public boolean isHasChild() {
		return isHasChild;
	}

	public void setHasChild(boolean isHasChild) {
		this.isHasChild = isHasChild;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
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
