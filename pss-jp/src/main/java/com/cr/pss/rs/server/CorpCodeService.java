package com.cnipr.pss.rs.server;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cnipr.pss.rs.dto.CorpTreeInfoDTO;
import com.cnipr.pss.service.search.CorpCodeManager;

@Component
@Path("/corpcode")
public class CorpCodeService {
	private static final String CHARSET = ";charset=UTF-8";
	@Autowired
	private CorpCodeManager corpCodeManager;

	@POST
	@Path("/hasnocode")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public CorpTreeInfoDTO searchHasNoCode(@FormParam("str") String str,
			@FormParam("from") String from, @FormParam("to") String to) {
		return corpCodeManager.searchHasNoCode(str, Integer.parseInt(from),
				Integer.parseInt(to));
	}

	@POST
	@Path("/corps")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public CorpTreeInfoDTO searchCorpByCode(@FormParam("str") String str,
			@FormParam("from") String from, @FormParam("to") String to) {
		return corpCodeManager.searchCorpByCode(str, Integer.parseInt(from),
				Integer.parseInt(to));
	}

	@POST
	@Path("/topcode")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public CorpTreeInfoDTO searchTopCode(@FormParam("str") String str,
			@FormParam("from") String from, @FormParam("to") String to) {
		return corpCodeManager.searchTopCode(str, Integer.parseInt(from),
				Integer.parseInt(to));
	}

	@POST
	@Path("/expand")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public List<CorpTreeInfoDTO> searchCorpCode(@FormParam("str") String str) {
		return corpCodeManager.searchCorpCode(str);
	}
}
