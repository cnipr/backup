package com.cnipr.pss.rs.server;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.cnipr.pss.rs.dto.ClassSearchDTO;
import com.cnipr.pss.rs.dto.CnReferencesDTO;
import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.DownloadSearchDTO;
import com.cnipr.pss.rs.dto.HotWordsDTO;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.PatCantrastSearchDTO;
import com.cnipr.pss.rs.dto.PatClusterSearchDTO;
import com.cnipr.pss.rs.dto.PatHighLightSearchDTO;
import com.cnipr.pss.rs.dto.PatNumbersExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatPrsExploitationDTO;
import com.cnipr.pss.rs.dto.PatPrsPreservationDTO;
import com.cnipr.pss.rs.dto.PatPrsTransferDTO;
import com.cnipr.pss.rs.dto.PatSimilarityDTO;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.impl.PatAutoAbsSearcher;
import com.cnipr.pss.search.impl.PatAutoKeyWordsSearcher;
import com.cnipr.pss.search.impl.PatAutoTipSearcher;
import com.cnipr.pss.search.impl.PatBatchDownloadSearcher;
import com.cnipr.pss.search.impl.PatCantrastSearcher;
import com.cnipr.pss.search.impl.PatClassSearcher;
import com.cnipr.pss.search.impl.PatCnReferenceSearcher;
import com.cnipr.pss.search.impl.PatDetailSearcher;
import com.cnipr.pss.search.impl.PatDownloadSearcher;
import com.cnipr.pss.search.impl.PatFamilyGroupSearcher;
import com.cnipr.pss.search.impl.PatHighlightSearcher;
import com.cnipr.pss.search.impl.PatHotWordsSearcher;
import com.cnipr.pss.search.impl.PatLegalStatusSearcher;
import com.cnipr.pss.search.impl.PatNumbersExtractSearcher;
import com.cnipr.pss.search.impl.PatOverviewSearcher;
import com.cnipr.pss.search.impl.PatPrsExploitationSearcher;
import com.cnipr.pss.search.impl.PatPrsPreservationSearcher;
import com.cnipr.pss.search.impl.PatPrsTransferSearcher;
import com.cnipr.pss.search.impl.PatSimilarSearcher;
import com.cnipr.pss.search.impl.PatSimilaritySearcher;
import com.cnipr.pss.search.impl.PatXmlDocSearcher;
import com.cnipr.pss.search.impl.TrsCluSearcher;
import com.cnipr.pss.search.impl.TrsListMapSearcher;
import com.cnipr.pss.search.impl.TrsListMapSearcher2;

@Component
@Path("/search")
public class PatSearchService {
	private static final String CHARSET = ";charset=UTF-8";

//	@Autowired
//	private PatFamilyManager patFamilyManager;

//	@Autowired
//	private CnReferencesManager cnReferencesManager;

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
	@Path("/detail")
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

		AbstractSearcher searcher = new PatDetailSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(recordCursor), strSynonymous, 0);

		return (DetailSearchDTO) searcher.excute();
	}

	/**
	 * 相似性细缆检索
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
	@Path("/similardetail")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public DetailSearchDTO excutePatDetailSimilarSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			// @FormParam("iHitPointType") String iHitPointType,
			// @FormParam("bContinue") String bContinue,
			@FormParam("recordCursor") String recordCursor,
			@FormParam("strSynonymous") String strSynonymous) {

		AbstractSearcher searcher = new PatDetailSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(recordCursor), null, 1);

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
	@Path("/numbersextract")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatNumbersExtractSearchDTO excuteNumbersExtractSearch(
			@FormParam("extractType") String extractType,
			@FormParam("sysids") String sysids,
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			@FormParam("strSynonymous") String strSynonymous) {
		AbstractSearcher searcher = new PatNumbersExtractSearcher(extractType,
				sysids, strSources, strWhere, strSortMethod,
				Integer.parseInt(iOption), strSynonymous);
		return (PatNumbersExtractSearchDTO) searcher.excute();
	}

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
	@Path("/overview")
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
		AbstractSearcher searcher = new PatOverviewSearcher(strSources,
				strWhere, strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(startIndex), Integer.parseInt(endIndex),
				strSection, strSynonymous, displayCols);
		return (OverviewSearchDTO) searcher.excute();
	}

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
	@Path("/batchdownload")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public OverviewSearchDTO excutePatBatchDownloadSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous,
			@FormParam("displayCols") String displayCols) {
		AbstractSearcher searcher = new PatBatchDownloadSearcher(strSources,
				strWhere, strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(startIndex), Integer.parseInt(endIndex),
				strSection, strSynonymous, displayCols);
		return (OverviewSearchDTO) searcher.excute();
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
	@Path("/xmldoc")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatXmlDocSearchDTO excutePatXmlDocSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("column") String column) {

		AbstractSearcher searcher = new PatXmlDocSearcher(strSources, strWhere,
				column);
		return (PatXmlDocSearchDTO) searcher.excute();
	}
	
	/**
	 * 
	 * @param strSources
	 * @param strWhere
	 * @param columns  以逗号分隔的字段名称
	 * @param cursor
	 * @return
	 */
	@POST
	@Path("/xmldocCursor")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatHighLightSearchDTO excutePatXmlDocSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("columns") String columns,
			@FormParam("cursor") String cursor,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption) {
		AbstractSearcher searcher = new PatHighlightSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption), Long.parseLong(cursor), "", 0, columns.split(","));
		return (PatHighLightSearchDTO) searcher.excute();
	}
	
	@POST
	@Path("/listMap")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public TrsListMapSearchDTO excuteListMapSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("columns") String columns,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption) {
		AbstractSearcher searcher = new TrsListMapSearcher(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),  "", 0, columns.split(","));
		return (TrsListMapSearchDTO) searcher.excute();
	}
	
	@POST
	@Path("/listMap2")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public TrsListMapSearchDTO excuteListMapSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("columns") String columns,
			@FormParam("columns2") String columns2,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption) {
		AbstractSearcher searcher = new TrsListMapSearcher2(strSources, strWhere,
				strSortMethod, Integer.parseInt(iOption),  "", 0, columns.split(","), columns2.split(","), Long.parseLong(startIndex),Long.parseLong(endIndex));
		return (TrsListMapSearchDTO) searcher.excute();
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
	@Path("/legalstatus")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public LegalStatusSearchDTO executePatLegalStatusSearch(
			@FormParam("strWhere") String strWhere,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex) {
		AbstractSearcher searcher = new PatLegalStatusSearcher(strWhere,
				Long.valueOf(startIndex), Long.valueOf(endIndex));
		return (LegalStatusSearchDTO) searcher.excute();
	}

	/**
	 * 聚类
	 * 
	 * @param sources
	 *            检索目标库
	 * @param where
	 *            表达式
	 * @param pb
	 *            屏蔽词
	 * @param jq
	 *            加权词
	 * @param option
	 *            0 按词 2按字
	 * @param synonym
	 *            同义词
	 * @param docCol1
	 *            检索字段
	 * @param clsNum1
	 *            最大（小）分类数
	 * @param keywordNum1
	 *            主题词个数
	 * @param display
	 *            应为ap,ti,similarity,ipc,pa,area,cc
	 * @return
	 */
	@POST
	@Path("/cluster")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatClusterSearchDTO clusterSearch(
			@FormParam("sources") String sources,
			@FormParam("where") String where, @FormParam("pb") String pb,
			@FormParam("jq") String jq, @FormParam("option") String option,
			@FormParam("synonym") String synonym,
			@FormParam("docCol1") String docCol1,
			@FormParam("clsNum1") String clsNum1,
			@FormParam("keywordNum1") String keywordNum1,
			@FormParam("display") String display) {
		AbstractSearcher searcher = new TrsCluSearcher(sources, where, pb, jq,
				option, synonym, docCol1, clsNum1, keywordNum1, display);
		return (PatClusterSearchDTO) searcher.excute();
	}

	@POST
	@Path("/similar")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public OverviewSearchDTO excuteSimilarSearch(
			@FormParam("strSources") String strSources,
			@FormParam("similarWhere") String similarWhere,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex,
			@FormParam("strSection") String strSection,
			@FormParam("displayCols") String displayCols,
			@FormParam("lastWhere") String lastWhere) {
		AbstractSearcher searcher = new PatSimilarSearcher(strSources,
				similarWhere, Integer.parseInt(startIndex),
				Integer.parseInt(endIndex), strSection, displayCols, lastWhere);
		return (OverviewSearchDTO) searcher.excute();
	}

	@POST
	@Path("/autoabs")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public String excuteAutoAbsSearch(@FormParam("appNo") String appNo,
			@FormParam("tablename") String tablename,
			@FormParam("stopWords") String stopWords,
			@FormParam("weiWords") String weiWords,
			@FormParam("absNum") String absNum,
			@FormParam("absPercent") String absPercent) {
		AbstractSearcher searcher = new PatAutoAbsSearcher(appNo, tablename,
				stopWords, weiWords, Integer.parseInt(absNum),
				Integer.parseInt(absPercent));
		return (String) searcher.excute();
	}

	@POST
	@Path("/autokeywords")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public String excuteAutoKeyWordsSearch(@FormParam("appNum") String appNum,
			@FormParam("tableName") String tableName,
			@FormParam("inputQuerys") String inputQuerys,
			@FormParam("searchCond") String searchCond) {
		AbstractSearcher searcher = new PatAutoKeyWordsSearcher(appNum,
				tableName, inputQuerys, searchCond);
		return (String) searcher.excute();
	}

	@POST
	@Path("/autotip")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public String excuteAutoTipSearch(@FormParam("strWhere") String strWhere) {
		AbstractSearcher searcher = new PatAutoTipSearcher(strWhere);
		return (String) searcher.excute();
	}

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
	@Path("/download")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public DownloadSearchDTO excutePatDownloadSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("iOption") String iOption,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous,
			@FormParam("displayCols") String displayCols,
			@FormParam("downloadType") String downloadType) {
		AbstractSearcher searcher = new PatDownloadSearcher(strSources,
				strWhere, strSortMethod, Integer.parseInt(iOption),
				Integer.parseInt(startIndex), Integer.parseInt(endIndex),
				strSection, strSynonymous, displayCols);
		DownloadSearchDTO dto = (DownloadSearchDTO) searcher.excute();
		return dto;
	}

	/**
	 * 热点词检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @param strWhere
	 *            表达式
	 * @param extractNum
	 *            抽取热点词个数
	 * @return
	 */
	@POST
	@Path("/hotwords")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public HotWordsDTO excutePatHotWordsSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("extractNum") String extractNum) {
		AbstractSearcher searcher = new PatHotWordsSearcher(strSources,
				strWhere, Integer.parseInt(extractNum));
		return (HotWordsDTO) searcher.excute();
	}

	/**
	 * 相似度检索
	 * 
	 * @param strWhereA
	 *            专利A的检索表达式（必须是完整的检索表达式）
	 * @param strWhereB
	 *            参照系限定检索式（不能为空必须是完整的检索表达式）
	 * @param strField
	 *            专利文本A的指定字段名称
	 * @return
	 */
	@POST
	@Path("/similarity")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatSimilarityDTO excutePatSimilaritySearch(
			@FormParam("strWhereA") String strWhereA,
			@FormParam("strWhereB") String strWhereB,
			@FormParam("strField") String strField) {
		AbstractSearcher searcher = new PatSimilaritySearcher(strWhereA,
				strWhereB, strField);
		return (PatSimilarityDTO) searcher.excute();
	}

//	/**
//	 * 同族专利检索
//	 * 
//	 * @param sysid
//	 * @return 同族专利申请号，逗号分隔
//	 */
//	@POST
//	@Path("/family")
//	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
//	public PatFamilySearchDTO excutePatFamilySearch(@FormParam("an") String an) {
//		return patFamilyManager.getAllFamilys(an);
//	}

	/**
	 * 中国引证专利检索
	 * 
	 * @param 申请号
	 * @return
	 */
	@POST
	@Path("/cnreference")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public CnReferencesDTO excuteCnReferenceSearch(@FormParam("an") String an) {
		AbstractSearcher searcher = new PatCnReferenceSearcher(an);
		return (CnReferencesDTO) searcher.excute();
	}

	/**
	 * 实施许可合同备案信息检索
	 * 
	 * @return
	 */
	@POST
	@Path("/prsexploitation")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatPrsExploitationDTO excutePrsExploitationSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex) {
		PatPrsExploitationSearcher searcher = new PatPrsExploitationSearcher(
				strSources, strWhere, strSortMethod,
				Integer.parseInt(startIndex), Integer.parseInt(endIndex));
		return (PatPrsExploitationDTO) searcher.excute();
	}

	/**
	 * 质押保全信息检索
	 * 
	 * @return
	 */
	@POST
	@Path("/prspreservation")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatPrsPreservationDTO excutePrsPreservationSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex) {
		PatPrsPreservationSearcher searcher = new PatPrsPreservationSearcher(
				strSources, strWhere, strSortMethod,
				Integer.parseInt(startIndex), Integer.parseInt(endIndex));
		return (PatPrsPreservationDTO) searcher.excute();
	}

	/**
	 * 申请专利权转移检索
	 * 
	 * @return
	 */
	@POST
	@Path("/prstransfer")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatPrsTransferDTO excutePrsTransferSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("strSortMethod") String strSortMethod,
			@FormParam("startIndex") String startIndex,
			@FormParam("endIndex") String endIndex) {
		PatPrsTransferSearcher searcher = new PatPrsTransferSearcher(
				strSources, strWhere, strSortMethod,
				Integer.parseInt(startIndex), Integer.parseInt(endIndex));
		return (PatPrsTransferDTO) searcher.excute();
	}

	// /**
	// * 中国引证专利检索
	// * @param 申请号
	// * @return
	// */
	// @POST
	// @Path("/cnreference")
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML +
	// CHARSET })
	// public CnReferencesDTO excuteCnReferenceSearch(@FormParam("an") String
	// an) {
	// return cnReferencesManager.getReferenceList(an);
	// }

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
	@POST
	@Path("/classpat")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public ClassSearchDTO excutePatClassSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("buffersize") String buffersize,
			@FormParam("iOption") String iOption,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous,
			@FormParam("classCol") String classCol) {
		AbstractSearcher searcher = new PatClassSearcher(strSources, strWhere,
				buffersize, Integer.parseInt(iOption), strSection,
				strSynonymous, classCol);
		return (ClassSearchDTO) searcher.excute();
	}

	/**
	 * 分类统计检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @return
	 */
	@POST
	@Path("/contrastpat")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public PatCantrastSearchDTO excutePatContrastSearch(
			@FormParam("strContrastList") String strContrastList) {
		AbstractSearcher searcher = new PatCantrastSearcher(strContrastList);
		return (PatCantrastSearchDTO) searcher.excute();
	}

	/**
	 * 同族分组检索
	 * 
	 * @param strSources
	 *            检索目标库
	 * @return
	 */
	@POST
	@Path("/familygroup")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML + CHARSET })
	public ClassSearchDTO excutePatFamilyGroupSearch(
			@FormParam("strSources") String strSources,
			@FormParam("strWhere") String strWhere,
			@FormParam("iOption") String iOption,
			@FormParam("strSection") String strSection,
			@FormParam("strSynonymous") String strSynonymous) {
		AbstractSearcher searcher = new PatFamilyGroupSearcher(strSources,
				strWhere, Integer.parseInt(iOption), strSection, strSynonymous);
		return (ClassSearchDTO) searcher.excute();
	}
}
