package com.cnipr.pss.rs.server;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnipr.pss.rs.dto.IpcInfoDTO;
import com.cnipr.pss.service.search.IpcManager;

@Component
@Path("/ipc")
public class IpcInfoService {
	private static final String CHARSET = ";charset=UTF-8";
	@Autowired
	private IpcManager ipcManager;

	@POST
	@Path("/default")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public IpcInfoDTO excuteIpcDefaultSearch() {
		return ipcManager.searchDefault();
	}

	@POST
	@Path("/param")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public IpcInfoDTO excuteIpcParamSearch(
			@FormParam("searchChoice") String searchChoice,
			@FormParam("searchStr") String searchStr,
			@FormParam("from") String from, @FormParam("to") String to) {

		return ipcManager.searchStr(Integer.parseInt(searchChoice), searchStr,
				Integer.parseInt(from), Integer.parseInt(to));
	}

	@POST
	@Path("/path")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public IpcInfoDTO excuteIpcPathSearch(@FormParam("ipc") String ipc) {
		return ipcManager.searchPathAndIpcs(ipc);
	}

	@POST
	@Path("/detail")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public IpcInfoDTO excuteIpcDetailSearch(@FormParam("ipc") String ipc) {
		return ipcManager.expandIpc(ipc);
	}
}
