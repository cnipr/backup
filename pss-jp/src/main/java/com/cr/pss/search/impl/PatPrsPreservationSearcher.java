package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatPrsPreservationDTO;
import com.cnipr.pss.rs.dto.search.PatPrsPreservationInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSException;

//import com.eprobiti.trs.TRSException;

/**
 * 质押保全信息检索人
 * 
 * @author lq
 */
public class PatPrsPreservationSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatPrsPreservationSearcher.class);

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

	public PatPrsPreservationSearcher(String strSources, String strWhere,
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
	public PatPrsPreservationDTO doSearch() {
		PatPrsPreservationDTO dto = new PatPrsPreservationDTO();

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
				
				List<PatPrsPreservationInfo> infoList = new ArrayList<PatPrsPreservationInfo>();

				trsresultset.setBufferSize(10, 10);

				trsresultset.moveTo(0, startIndex);
				// 取出本次需要的全部记录
				long i = startIndex;

				do {
					// 构造信息
					PatPrsPreservationInfo info = new PatPrsPreservationInfo();
					info.setAn(trsresultset.getString("申请号"));
					info.setStrLegalStatusDay(trsresultset.getString("法律状态公告日"));
					info.setTi(trsresultset.getString("名称"));
					info.setType(trsresultset.getString("类型"));
					info.setShengXiaoRi(trsresultset.getString("生效日"));
					info.setBianGengRi(trsresultset.getString("变更日"));
					info.setJieChuRi(trsresultset.getString("解除日"));
					info.setHeTongZT(trsresultset.getString("合同状态"));
					info.setHeTongDJH(trsresultset.getString("合同登记号"));
					info.setChuZhiRen(trsresultset.getString("出质人"));
					info.setZhiQuanRen(trsresultset.getString("质权人"));
					info.setDangQianZQR(trsresultset.getString("当前质权人"));
					
					infoList.add(info);

				} while (trsresultset.moveNext() && ++i < endIndex);
				dto.setPrsPreservationInfoList(infoList);
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
