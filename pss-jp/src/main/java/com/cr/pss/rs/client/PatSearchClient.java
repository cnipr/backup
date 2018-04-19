package com.cnipr.pss.rs.client;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.ClassSearchDTO;
import com.cnipr.pss.rs.dto.CnReferencesDTO;
import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.DownloadSearchDTO;
import com.cnipr.pss.rs.dto.HotWordsDTO;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.PatCantrastSearchDTO;
import com.cnipr.pss.rs.dto.PatClusterSearchDTO;
import com.cnipr.pss.rs.dto.PatFamilySearchDTO;
import com.cnipr.pss.rs.dto.PatHighLightSearchDTO;
import com.cnipr.pss.rs.dto.PatNumbersExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatPrsExploitationDTO;
import com.cnipr.pss.rs.dto.PatPrsPreservationDTO;
import com.cnipr.pss.rs.dto.PatPrsTransferDTO;
import com.cnipr.pss.rs.dto.PatSimilarityDTO;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class PatSearchClient {
	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
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
		return client.path("/search/detail")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<DetailSearchDTO>() {
				}, params);
	}

	public DetailSearchDTO excutePatDetailSimilarSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String recordCursor, String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		params.add("recordCursor", recordCursor);
		params.add("strSynonymous", strSynonymous);
		return client.path("/search/similardetail")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<DetailSearchDTO>() {
				}, params);
	}

	public PatNumbersExtractSearchDTO excuteNumbersExtractSearch(
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
		return client.path("/search/numbersextract")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatNumbersExtractSearchDTO>() {
				}, params);
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

		return client.path("/search/overview")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<OverviewSearchDTO>() {
				}, params);
	}

	public OverviewSearchDTO excutePatBatchDownloadSearch(String strSources,
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

		return client.path("/search/batchdownload")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<OverviewSearchDTO>() {
				}, params);
	}

	public PatXmlDocSearchDTO excutePatXmlDocSearch(String strSources,
			String strWhere, String column) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("column", column);
		return client.path("/search/xmldoc")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatXmlDocSearchDTO>() {
				}, params);
	}
	
	public PatHighLightSearchDTO excutePatXmlDocSearch(String strSources,
			String strWhere, String columns, String cursor,String strSortMethod, String iOption) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("columns", columns);
		params.add("cursor", cursor);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		return client.path("/search/xmldocCursor")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatHighLightSearchDTO>() {
				}, params);
	}
	
	public TrsListMapSearchDTO excuteListMapSearch(String strSources,
			String strWhere, String columns,String strSortMethod, String iOption) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("columns", columns);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		return client.path("/search/listMap")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<TrsListMapSearchDTO>() {
				}, params);
	}
	
	public TrsListMapSearchDTO excuteListMapSearch(String strSources,
			String strWhere, String columns, String columns2,String startIndex, String endIndex, String strSortMethod, String iOption) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("columns", columns);
		params.add("columns2", columns2);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);
		params.add("strSortMethod", strSortMethod);
		params.add("iOption", iOption);
		return client.path("/search/listMap2")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<TrsListMapSearchDTO>() {
				}, params);
	}

	public LegalStatusSearchDTO executePatLegalStatusSearch(String strWhere,
			String startIndex, String endIndex) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strWhere", strWhere);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);
		return client.path("/search/legalstatus")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<LegalStatusSearchDTO>() {
				}, params);
	}

	public PatClusterSearchDTO clusterSearch(String sources, String where,
			String pb, String jq, String option, String synonym,
			String docCol1, String clsNum1, String keywordNum1, String display) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("sources", sources);
		params.add("where", where);
		params.add("pb", pb);
		params.add("jq", jq);
		params.add("option", option);
		params.add("synonym", synonym);
		params.add("docCol1", docCol1);
		params.add("clsNum1", clsNum1);
		params.add("keywordNum1", keywordNum1);
		params.add("display", display);
		return client.path("/search/cluster")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatClusterSearchDTO>() {
				}, params);
	}

	public OverviewSearchDTO excuteSimilarSearch(String strSources,
			String similarWhere, String startIndex, String endIndex,
			String strSection, String displayCols, String lastWhere) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("similarWhere", similarWhere);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);
		params.add("strSection", strSection);
		params.add("displayCols", displayCols);
		params.add("lastWhere", lastWhere);
		return client.path("/search/similar")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<OverviewSearchDTO>() {
				}, params);
	}

	public String excuteAutoAbsSearch(String appNo, String tablename,
			String stopWords, String weiWords, String absNum, String absPercent) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNo", appNo);
		params.add("tablename", tablename);
		params.add("stopWords", stopWords);
		params.add("weiWords", weiWords);
		params.add("absNum", absNum);
		params.add("absPercent", absPercent);
		return client.path("/search/autoabs")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<String>() {
				}, params);
	}

	public String excuteAutoKeyWordsSearch(String appNum, String tableName,
			String inputQuerys, String searchCond) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		params.add("tableName", tableName);
		params.add("inputQuerys", inputQuerys);
		params.add("searchCond", searchCond);
		return client.path("/search/autokeywords")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<String>() {
				}, params);
	}

	public String excuteAutoTipSearch(String strWhere) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strWhere", strWhere);
		return client.path("/search/autotip")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<String>() {
				}, params);

	}

	public HotWordsDTO excutePatHotWordsSearch(String strSources,
			String strWhere, String extractNum) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("extractNum", extractNum);

		return client.path("/search/hotwords")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<HotWordsDTO>() {
				}, params);
	}

	public PatSimilarityDTO excutePatSimilaritySearch(String strWhereA,
			String strWhereB, String strField) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strWhereA", strWhereA);
		params.add("strWhereB", strWhereB);
		params.add("strField", strField);

		return client.path("/search/similarity")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatSimilarityDTO>() {
				}, params);
	}

	public DownloadSearchDTO excutePatDownloadSearch(String strSources,
			String strWhere, String strSortMethod, String iOption,
			String startIndex, String endIndex, String strSection,
			String strSynonymous, String displayCols, String downloadType) {
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
		params.add("downloadType", downloadType);

		return client.path("/search/download")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<DownloadSearchDTO>() {
				}, params);
	}

	public PatFamilySearchDTO excutePatFamilySearch(String an) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("an", an);

		return client.path("/search/family")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatFamilySearchDTO>() {
				}, params);
	}

	public CnReferencesDTO excuteCnReferenceSearch(String an) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("an", an);

		return client.path("/search/cnreference")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<CnReferencesDTO>() {
				}, params);
	}

	public PatPrsExploitationDTO excutePrsExploitationSearch(String strSources,
			String strWhere, String strSortMethod, String startIndex,
			String endIndex) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);

		return client.path("/search/prsexploitation")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatPrsExploitationDTO>() {
				}, params);
	}

	public PatPrsPreservationDTO excutePrsPreservationSearch(String strSources,
			String strWhere, String strSortMethod, String startIndex,
			String endIndex) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);

		return client.path("/search/prspreservation")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatPrsPreservationDTO>() {
				}, params);
	}

	public PatPrsTransferDTO excutePrsTransferSearch(String strSources,
			String strWhere, String strSortMethod, String startIndex,
			String endIndex) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("strSortMethod", strSortMethod);
		params.add("startIndex", startIndex);
		params.add("endIndex", endIndex);

		return client.path("/search/prstransfer")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatPrsTransferDTO>() {
				}, params);
	}

	/**
	 * 分类统计检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param strSection
	 *            单库过滤检索
	 * @param strSynonymous
	 *            是否同义词检索
	 * @param classCol
	 *            分类字段
	 * @return
	 */
	public ClassSearchDTO excutePatClassSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("buffersize") String buffersize,
			@FormParam("iOption") String iOption,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous,
			@FormParam("classCol") String classCol) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("buffersize", buffersize);
		params.add("iOption", iOption);
		params.add("strSection", strSection);
		params.add("strSynonymous", strSynonymous);
		params.add("classCol", classCol);

		return client.path("/search/classpat")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<ClassSearchDTO>() {
				}, params);
	}

	/**
	 * 对比检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @return
	 */
	public PatCantrastSearchDTO excutePatContrastSearch(
			@FormParam("strContrastList") String strContrastList) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strContrastList", strContrastList);

		return client.path("/search/contrastpat")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<PatCantrastSearchDTO>() {
				}, params);
	}
	
	/**
	 * 同族分组检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param strSection
	 *            单库过滤检索
	 * @param strSynonymous
	 *            是否同义词检索
	 * @return
	 */
	public ClassSearchDTO excutePatFamilyGroupSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("iOption") String iOption,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("strSources", strSources);
		params.add("strWhere", strWhere);
		params.add("iOption", iOption);
		params.add("strSection", strSection);
		params.add("strSynonymous", strSynonymous);

		return client.path("/search/familygroup")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<ClassSearchDTO>() {
				}, params);
	}
}
