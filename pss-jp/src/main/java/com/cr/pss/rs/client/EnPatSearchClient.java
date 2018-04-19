package com.cnipr.pss.rs.client;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.PatNumNoSysidExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatNumbersExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.rs.dto.TranslateSearchDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class EnPatSearchClient {
	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}
	
	public OverviewSearchDTO excutePatOverviewSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String startIndex, String endIndex, String strSection,
			String strSynonymous, String displayCols) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);
		params.add("strSection", strSection);
		params.add("strSynonymous", strSynonymous);
		params.add("displayCols", displayCols);

		return client.path("/ensearch/enoverview")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<OverviewSearchDTO>() {
				}, params);
	}
	
	public DetailSearchDTO excutePatDetailSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String recordCursor, String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("recordCursor", recordCursor);
		params.add("strSynonymous", strSynonymous);
		return client.path("/ensearch/endetail")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<DetailSearchDTO>() {
				}, params);
	}
	
	public DetailSearchDTO excutePatSQDetailSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String recordCursor, String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("recordCursor", recordCursor);
		params.add("strSynonymous", strSynonymous);
		return client.path("/ensearch/ensqdetail")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<DetailSearchDTO>() {
				}, params);
	}
	
	public PatNumNoSysidExtractSearchDTO excuteNumbersExtractSearch(
			String extractType, String sysids, String strSources,
			String strWhere, String strSortMethod, String iOption,
			String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("extractType", extractType);
		params.add("sysids", sysids);
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("strSynonymous", strSynonymous);
		return client.path("/ensearch/ennumbersextract")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatNumNoSysidExtractSearchDTO>() {
				}, params);
	}
	
	public LegalStatusSearchDTO executePatLegalStatusSearch(String strWhere,
			String startIndex, String endIndex) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strWhere", strWhere);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);
		return client.path("/ensearch/enlegalstatus")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<LegalStatusSearchDTO>() {
				}, params);
	}
	
	public PatXmlDocSearchDTO excutePatXmlDocSearch(String strSources,
			String strWhere, String column) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("column", column);
		return client.path("/ensearch/enxmldoc")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatXmlDocSearchDTO>() {
				}, params);
	}
	
	public TranslateSearchDTO excutePatTranslateSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String recordCursor, String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("recordCursor", recordCursor);
		params.add("strSynonymous", strSynonymous);
		return client.path("/ensearch/translate")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<TranslateSearchDTO>() {
				}, params);
	}
}
