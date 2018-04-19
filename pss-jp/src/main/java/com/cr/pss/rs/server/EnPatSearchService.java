package com.cnipr.pss.rs.server;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.PatNumNoSysidExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.rs.dto.TranslateSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.enimpl.EnOverviewSearcher;
import com.cnipr.pss.search.enimpl.EnPatDetailSearcher;
import com.cnipr.pss.search.enimpl.EnPatLegalStatusSearcher;
import com.cnipr.pss.search.enimpl.EnPatNumbersExtractSearcher;
import com.cnipr.pss.search.enimpl.EnPatSQDetailSearcher;
import com.cnipr.pss.search.enimpl.EnPatTranslateSearcher;
import com.cnipr.pss.search.enimpl.EnPatXmlDocSearcher;

@Component
@Path("/ensearch")
public class EnPatSearchService {
	private static final String CHARSET = ";charset=UTF-8";
	
	/**
	 * 概览检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param startIndex
	 *            开始下标
	 * @param endIndex
	 *            结束下标
	 * @param strSection
	 *            单库过滤检索
	 * @param strSynonymous
	 *            是否同义词检索
	 * @param displayCols
	 *            按设置返回列值（暂时写空）
	 * @return
	 */
	@POST
	@Path("/enoverview")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public OverviewSearchDTO excutePatOverviewSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous,
			@FormParam("displayCols") String displayCols) {
		AbstractSearcher searcher = new EnOverviewSearcher(strSources,
				strWhere, strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(startIndex), Integer.parseInt(endIndex),
				strSection, strSynonymous, displayCols);
		return (OverviewSearchDTO) searcher.excute();
	}
	
	/**
	 * 细缆检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param recordCursor
	 *            取第几条检索到的专利
	 * @param strSynonymous
	 *            是否同义词检索
	 * @return
	 */
	@POST
	@Path("/endetail")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public DetailSearchDTO excutePatDetailSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			// @FormParam("iHitPointType") String iHitPointType,
			// @FormParam("bContinue") String bContinue,
			@FormParam("recordCursor") String recordCursor,
			@FormParam("strSynonymous") String strSynonymous) {

		AbstractSearcher searcher = new EnPatDetailSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(recordCursor), null, 0);

		return (DetailSearchDTO) searcher.excute();
	}
	
	/**
	 * 授权细缆检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param recordCursor
	 *            取第几条检索到的专利
	 * @param strSynonymous
	 *            是否同义词检索
	 * @return
	 */
	@POST
	@Path("/ensqdetail")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public DetailSearchDTO excutePatSQDetailSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			// @FormParam("iHitPointType") String iHitPointType,
			// @FormParam("bContinue") String bContinue,
			@FormParam("recordCursor") String recordCursor,
			@FormParam("strSynonymous") String strSynonymous) {

		AbstractSearcher searcher = new EnPatSQDetailSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(recordCursor), null, 0);

		return (DetailSearchDTO) searcher.excute();
	}
	
	/**
	 * 建库时专利号单抽取
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param strSynonymous
	 *            是否同义词检索
	 * @return
	 */
	@POST
	@Path("/ennumbersextract")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatNumNoSysidExtractSearchDTO excuteNumbersExtractSearch(
			@FormParam("extractType") String extractType,
			@FormParam("sysids") String sysids,
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			@FormParam("strSynonymous") String strSynonymous) {
		AbstractSearcher searcher = new EnPatNumbersExtractSearcher(extractType,
				sysids, strSources, strWhere, strSortMethod,
				Integer.parseInt(iOption), strSynonymous);
		return (PatNumNoSysidExtractSearchDTO) searcher.excute();
	}
	
	/**
	 * 法律状态检索
	 * 
	 * @param strWhere
	 *            表达式
	 * @param startIndex
	 *            开始下标
	 * @param endIndex
	 *            结束下标
	 * @return
	 */
	@POST
	@Path("/enlegalstatus")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public LegalStatusSearchDTO executePatLegalStatusSearch(
			@FormParam("strWhere") String strWhere,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex) {
		AbstractSearcher searcher = new EnPatLegalStatusSearcher(strWhere,
				Long.valueOf(startIndex), Long.valueOf(endIndex));
		return (LegalStatusSearchDTO) searcher.excute();
	}
	
	/**
	 * 代码化检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param column
	 *            要取的内容（为trs中的字段名称，分别为权利要求书、说明书、说明书附图）
	 * @return
	 */
	@POST
	@Path("/enxmldoc")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatXmlDocSearchDTO excutePatXmlDocSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("column") String column) {

		AbstractSearcher searcher = new EnPatXmlDocSearcher(strSources, strWhere,
				column);
		return (PatXmlDocSearchDTO) searcher.excute();
	}
	
	/**
	 * 翻译检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param strSortMethod
	 *            排序方式
	 * @param iOption
	 *            按词按字
	 * @param recordCursor
	 *            取第几条检索到的专利
	 * @param strSynonymous
	 *            是否同义词检索
	 * @return
	 */
	@POST
	@Path("/translate")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public TranslateSearchDTO excutePatTranslateSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			// @FormParam("iHitPointType") String iHitPointType,
			// @FormParam("bContinue") String bContinue,
			@FormParam("recordCursor") String recordCursor,
			@FormParam("strSynonymous") String strSynonymous) {

		AbstractSearcher searcher = new EnPatTranslateSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(recordCursor), null);

		return (TranslateSearchDTO) searcher.excute();
	}
}
