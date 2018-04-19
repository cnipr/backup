//package com.cnipr.pss.rs.server;
//
//import java.util.List;
//
//import javax.ws.rs.FormParam;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.cnipr.pss.rs.dto.LawStatusDTO;
//import com.cnipr.pss.service.search.LawStatusManager;
//
//@Component
//@Path("/lawstatus")
//public class LawStatusService {
//	private static final String CHARSET = ";charset=UTF-8";
//	@Autowired
//	private LawStatusManager lawStatusManager;
//
//	@POST
//	@Path("/searchbyappid")
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
//	public List<LawStatusDTO> excuteIpcDefaultSearch(@FormParam("app_id") String app_id) {
//		return lawStatusManager.searchDefault(app_id);
//	}
//	
//}
