package com.cnipr.pss.rs.client;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.LawStatusDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class LawStatusClient {
	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}
	
	public List<LawStatusDTO> searchLawStatus(String app_id) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("app_id", app_id);
		return client.path("/lawstatus/searchbyappid")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<List<LawStatusDTO>>() {
				}, params);
	}

	
}
