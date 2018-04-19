package com.cnipr.pss.search.enimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.TranslateSearchDTO;
import com.cnipr.pss.rs.dto.search.PatentInfo;
import com.cnipr.pss.rs.dto.search.TranslateInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetEnSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;

/**
 * 细缆检索人 专利细缆检索，这里取出的为普通著录项信息，包括收起选项内容
 * 
 * @author lq
 * 
 */
public class EnPatTranslateSearcher extends AbstractSearcher {

	private static final Logger logger = LoggerFactory
	.getLogger(EnPatTranslateSearcher.class);
	
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String strSortMethod;
//	/** 是否相似性检索1：是； 其他：否 **/
//	private int isSimilarSearch = 0;
	/**
	 * 检索选项掩码
	 **/
	private int iOption;

	// /**
	// * 命中点类型
	// **/
	// private int iHitPointType;
	//
	// /** 是否二次检索(即是否在前次检索的结果上继续检索)，如果为true则为二次检索 **/
	// private boolean bContinue;

	/** 本次检索专利的下标 **/
	private long recordCursor;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	public EnPatTranslateSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long recordCursor,
			String strSynonymous) {
		/** 走语义检索接口 */
		this.searchType = 1;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		// this.iHitPointType = iHitPointType;
		// this.bContinue = bContinue;
		this.recordCursor = recordCursor;
		this.strSynonymous = strSynonymous;
		
		logger.info("[PatDetailSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";recordCursor=" + recordCursor 
				+ ";strSynonymous=" + strSynonymous);
	}

	/**
	 * 细缆检索
	 */
	@Override
	public TranslateSearchDTO doSearch() {
		TranslateSearchDTO dto = new TranslateSearchDTO();
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			strWhere = (new GetEnSearchFormat()).preprocess(strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, strSortMethod, "", "", iOption,
					TRSConstant.TCE_OFFSET, false, strSynonymous);
		} catch (TRSException e) {
			trsresultset = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnPatDetailSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}

		if (trsresultset != null) {
			// 检索的总记录数
			long recordCount = trsresultset.getRecordCount();
			// 检索的专利信息列表
			TranslateInfo info = null;
			// 当有检索结果时
			if (recordCount > 0) {
				info = new TranslateInfo();
				try {
					if (recordCursor >= 0) {
						// 移动到检索结果记录集的指定记录
						trsresultset.moveTo(0, recordCursor);
					} else {
						trsresultset.moveTo(0, 0);
					}

					info.setTiEN(trsresultset.getString("TI"));
					info.setTiCN(trsresultset.getString("CN_TI"));
					info.setAbCN(trsresultset.getString("CN_ABS"));
					info.setAbEN(trsresultset.getString("ABS"));
					info.setPaCN(trsresultset.getString("CN_PA"));
					info.setPaEN(trsresultset.getString("PA"));
					info.setAn(trsresultset.getString("AP"));
					info.setInnCN(trsresultset.getString("CN_INV"));
					info.setInnEN(trsresultset.getString("INV"));

				} catch (TRSException e) {
					new PSSException("2002", e.getErrorCode() + ":"
							+ e.getMessage(), e.getMessage(),
							"EnPatTranslateSearcher", "doSearch", "trs");
					dto.setReturncode(2002);
					dto.setMessage(e.getMessage());
				}
			}
			dto.setTranslateInfo(info);
//			detail.setRecordCount(recordCount);
		}

		return dto;
	}
}
