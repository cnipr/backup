package com.cnipr.pss.rs.client;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.CorpTreeInfoDTO;
import com.cnipr.pss.rs.dto.IpcInfoDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class CorpCodeClient {
	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	// public UserDTO getUser(Long id) {
	// return client.path("/users/" +
	// id).accept(MediaType.APPLICATION_JSON).get(UserDTO.class);
	// }

	public CorpTreeInfoDTO searchHasNoCode(String str, String from, String to) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("str", str);
		params.add("from", from);
		params.add("to", to);
		return client.path("/corpcode/hasnocode")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<CorpTreeInfoDTO>() {
				}, params);
	}

	public CorpTreeInfoDTO searchCorpByCode(String str, String from, String to) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("str", str);
		params.add("from", from);
		params.add("to", to);
		return client.path("/corpcode/corps")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<CorpTreeInfoDTO>() {
				}, params);
	}

	public CorpTreeInfoDTO searchTopCode(String str, String from, String to) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("str", str);
		params.add("from", from);
		params.add("to", to);
		return client.path("/corpcode/topcode")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<CorpTreeInfoDTO>() {
				}, params);
	}

	public List<CorpTreeInfoDTO> searchCorpCode(@FormParam("str") String str) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("str", str);
		return client.path("/corpcode/expand")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<List<CorpTreeInfoDTO>>() {
				}, params);
	}
}
