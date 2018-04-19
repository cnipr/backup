package com.cnipr.pss.rs.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.IpcInfoDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class IpcInfoClient {
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

	public IpcInfoDTO excuteIpcDefaultSearch() {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		return client.path("/ipc/default")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<IpcInfoDTO>() {
				}, params);
	}

	public IpcInfoDTO excuteIpcParamSearch(String searchChoice, String searchStr,
			String from, String to) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("searchChoice", searchChoice);
		params.add("searchStr", searchStr);
		params.add("from", from);
		params.add("to", to);
		return client.path("/ipc/param")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<IpcInfoDTO>() {
				}, params);
	}

	public IpcInfoDTO excuteIpcPathSearch(String ipc) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("ipc", ipc);
		return client.path("/ipc/path")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<IpcInfoDTO>() {
				}, params);
	}

	public IpcInfoDTO excuteIpcDetailSearch(String ipc) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("ipc", ipc);
		return client.path("/ipc/detail")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<IpcInfoDTO>() {
				}, params);
	}
}
