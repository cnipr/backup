package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatPrsTransferDTO;
import com.cnipr.pss.rs.dto.search.PatPrsTransferInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSException;

//import com.eprobiti.trs.TRSException;

/**
 * 申请专利权转移检索人
 * 
 * @author lq
 */
public class PatPrsTransferSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatPrsTransferSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String strSortMethod;

	/** 开始下标 **/
	private long startIndex;

	/** 结束下标 **/
	private long endIndex;

	public PatPrsTransferSearcher(String strSources, String strWhere,
			String strSortMethod, long startIndex, long endIndex) {
		this.searchType = 1;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		logger.info("[PatOverviewSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod);
	}

	/**
	 * 概览检索
	 */
	@Override
	public PatPrsTransferDTO doSearch() {
		PatPrsTransferDTO dto = new PatPrsTransferDTO();

		// SemanticSearchOperation operation = new SemanticSearchOperation();
		// SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			strWhere = (new GetSearchFormat()).preprocess(strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, strSortMethod, "", "", 0, 0,
					false, "");
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				// dto.setSearchMessageBean(searchMessageBean);
				return dto;
			}
			long recordCount = trsresultset.getRecordCount();
			dto.setTotalCount(recordCount);

			// 当有检索结果时
			if (recordCount > 0) {
				if (endIndex > recordCount) {
					endIndex = recordCount;
				}
				
				List<PatPrsTransferInfo> prsTransInfoList = new ArrayList<PatPrsTransferInfo>();

				trsresultset.setBufferSize(10, 10);

				trsresultset.moveTo(0, startIndex);
				// 取出本次需要的全部记录
				long i = startIndex;

				do {
					// 构造信息
					PatPrsTransferInfo info = new PatPrsTransferInfo();
					info.setAn(trsresultset.getString("申请号"));
					info.setStrLegalStatusDay(trsresultset.getString("法律状态公告日"));
					info.setTi(trsresultset.getString("名称"));
					info.setType(trsresultset.getString("转移类型"));
					info.setBeforeTransAp(trsresultset.getString("变更前权利人"));
					info.setAfterTransAp(trsresultset.getString("变更后权利人"));
					info.setCurrentAp(trsresultset.getString("当前权利人"));
					info.setBeforeTransAddr(trsresultset.getString("变更前地址"));
					info.setAfterTransAddr(trsresultset.getString("变更后地址"));
					info.setCurrentAddr(trsresultset.getString("当前地址"));
					info.setAreaCode(trsresultset.getString("区域代码"));
					info.setEffectiveDate(trsresultset.getString("生效日"));

					prsTransInfoList.add(info);

				} while (trsresultset.moveNext() && ++i < endIndex);
				dto.setPrsTransInfoList(prsTransInfoList);
			}

		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "PatPrsTransferSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
//		long end = System.currentTimeMillis();
//		logger.info("PSS-概览检索：" + (end - start));
		return dto;
	}


}
