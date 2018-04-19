//package com.cnipr.pss.rs.server;
//
//import java.io.InputStream;
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
//import com.cnipr.pss.rs.dto.RexamDTO;
//import com.cnipr.pss.rs.dto.RexamFSJDDTO;
//import com.cnipr.pss.rs.dto.RexamFYPJDTO;
//import com.cnipr.pss.service.search.RexamManager;
//
//@Component
//@Path("/rexam")
//public class RexamService {
//	private static final String CHARSET = ";charset=UTF-8";
//	@Autowired
//	private RexamManager rexamManager;
//
//	@POST
//	@Path("/paramAppNum")
//	@Produces( { MediaType.APPLICATION_JSON,
//			MediaType.APPLICATION_XML + CHARSET })
//	public RexamDTO searchRexamsByAppNum(@FormParam("appNum")
//	String appNum) {
//		return rexamManager.searchRexamsByAppNum(appNum);
//	}
//
//	@POST
//	@Path("/queryKindsByAppNum")
//	@Produces( { MediaType.APPLICATION_JSON,
//			MediaType.APPLICATION_XML + CHARSET })
//	public RexamDTO queryRexamKindsByAppNum(@FormParam("appNum")
//	String appNum) {
//		return rexamManager.queryRexamKindsByAppNum(appNum);
//	}
//
//	@POST
//	@Path("/queryRexamsByAppNumKind")
//	@Produces( { MediaType.APPLICATION_JSON,
//			MediaType.APPLICATION_XML + CHARSET })
//	public RexamDTO queryRexamsByAppNumKind(@FormParam("appNum")
//	String appNum, @FormParam("kind")
//	String kind) {
//		return rexamManager.queryRexamsByAppNumKind(appNum, kind);
//	}
//	
//	/**
//	 * 获取专利法院判决的详细信息
//	 * @param appNum 申请号
//	 * @param judgementalDate 审判日期
//	 * @return
//	 */
//	
//	@POST
//	@Path("/queryPatFYPJDetail")
//	@Produces( { MediaType.APPLICATION_JSON,
//		MediaType.APPLICATION_XML + CHARSET })
//	public RexamFYPJDTO queryPatFYPJDetail(@FormParam("appNum")
//				String appNum, @FormParam("judgementalDate")
//				String judgementalDate) {
//		return rexamManager.queryPatFYPJDetail(appNum, judgementalDate);
//	}
//	
//	/**
//	 * 获取复审决定的详细信息
//	 * @param appNum 申请号
//	 * @param decisionNumber 决定号
//	 * @return
//	 */
//	
//	@POST
//	@Path("/queryPatFSJDDetail")
//	@Produces( { MediaType.APPLICATION_JSON,
//		MediaType.APPLICATION_XML + CHARSET })
//	public RexamFSJDDTO queryPatFSJDDetail(@FormParam("appNum")
//				String appNum, @FormParam("decisionNumber")
//				String decisionNumber) {
//		return rexamManager.queryPatFSJDDetail(appNum, decisionNumber);
//	}
//}
