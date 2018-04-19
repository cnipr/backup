package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatNumbersExtractSearchDTO;
import com.cnipr.pss.rs.dto.search.ExtractInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.dao.METPatentDao;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.GetSearchFormat;
import com.cnipr.pss.util.TableflagMap;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;

/**
 * 号单抽取检索人 返回的号单格式为 ^库标识@申请号1@公开号1@申请号2@公开号2^库标识2@申请号@公开号
 * 其中库标识如为国内则截取TRS库名称的前4为，如为国外则取前两位
 * 
 * @author lq
 * 
 */
public class PatNumbersExtractSearcher extends AbstractSearcher {
	/** 增加抽取标识，1：表示通过表达式抽取数据；2：表示通过sysid抽取数据 */
	private String extractType;
	/** sysid串，多个以逗号分割 */
	private String sysids;
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	/** 结果记录的排序方式 **/
	private String strSortMethod;
	/** 检索选项掩码 */
	private int iOption;
	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	/** 构造函数 **/
	public PatNumbersExtractSearcher(String extractType, String sysids,
			String strSources, String strWhere, String strSortMethod,
			int iOption, String strSynonymous) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.extractType = extractType;
		this.sysids = sysids;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.strSynonymous = strSynonymous;
	}

	@Override
	/** 检索方法 */
	public PatNumbersExtractSearchDTO doSearch() {
		PatNumbersExtractSearchDTO dto = new PatNumbersExtractSearchDTO();
		List<ExtractInfo> extractInfoList = new ArrayList<ExtractInfo>();
		if (extractType.equals("1")) {
			SemanticSearchOperation operation = new SemanticSearchOperation();
			try {
				strWhere = (new GetSearchFormat()).preprocess(strWhere);
				
				String tempLastWhere = semanticInstance.ExpressionParse(strSources,
						strWhere, 0, "RELEVANCE", iOption, null, strSynonymous,
						null, TRSConstant.TCM_KAXST);
				
				trsresultset = operation.getSemanticResult(strSources, tempLastWhere,
						strSortMethod, iOption, 115, false, strSynonymous, "", "",
						semanticInstance);
				if (trsresultset == null) {
					dto.setReturncode(2100);
					dto.setMessage("检索结果集为空");
					return dto;
				}
				/** 对申请号,公开（公告）号两字段设置缓存 */
				trsresultset.setReadOptions(0, "申请号,SYSID,APP_ID,公开（公告）号,TABLE_SN", null);
				trsresultset.setBufferSize(10000, 10000);

				long totalCount = trsresultset.getRecordCount();
				dto.setTotalCount(totalCount);
				if (totalCount > 0) {
					extractInfoList = new LinkedList<ExtractInfo>();
					// 取出本次需要的全部记录
					trsresultset.moveFirst();
					do {
//					for (long i = 0; i < totalCount; i++) {
//						trsresultset.moveTo(0, i);
//						sb.append(trsresultset.getString(Constants.SHEN_QING_HAO)).append(trsresultset.getString("sysid"))
//						.append(trsresultset.getString("app_id")).append(trsresultset.getString("TABLE_SN"));
						ExtractInfo info = new ExtractInfo();
						info.setAn(trsresultset.getString(Constants.SHEN_QING_HAO));
						info.setSysId(trsresultset.getString("sysid"));
						info.setAppId(trsresultset.getString("app_id"));
						info.setPn(trsresultset.getString(Constants.GONG_KAI_HAO));
						info.setTb(TableflagMap.getInstance().tbMap.get(trsresultset.getString("TABLE_SN")));
						extractInfoList.add(info);
					} while (trsresultset.moveNext());
					dto.setExtractInfoList(extractInfoList);

				}

			} catch (TRSException e) {
				new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
						"执行TRS检索出错", "PatNumbersExtractSearcher", "doSearch", "trs");
				dto.setReturncode(2002);
				dto.setMessage(e.getMessage());
			}
		} else {
			if (sysids == null || sysids.equals("")) {
				dto.setReturncode(4000);
				dto.setMessage("参数不合法(提取的专利号单不能为空)");
				return dto;
			}

			String[] confFile = {"applicationContext.xml"};
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(confFile);
            METPatentDao dao = (METPatentDao)ctx.getBean("metPatentDao");
			extractInfoList = dao.extractPatBySysid(sysids);
			dto.setTotalCount(Long.valueOf(extractInfoList.size()));
			dto.setExtractInfoList(extractInfoList);
		}

		return dto;
	}

	// public PatNumbersExtractSearchDTO doSearch() {
	// PatNumbersExtractSearchDTO dto = new PatNumbersExtractSearchDTO(1, "");
	// // 返回的消息体
	// // SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
	// SemanticSearchOperation operation = new SemanticSearchOperation();
	//
	// try {
	// trsresultset = operation.getSemanticResult(strSources, strWhere,
	// strSortMethod, iOption, 115, false, strSynonymous, "", "",
	// semanticInstance);
	// if (trsresultset == null) {
	// dto.setReturncode(2100);
	// dto.setMessage("检索结果集为空");
	// return dto;
	// }
	// /** 对申请号,公开（公告）号两字段设置缓存 */
	// trsresultset.setReadOptions(0, "申请号,公开（公告）号", null);
	// trsresultset.setBufferSize(2000, 2000);
	//
	// long totalCount = trsresultset.getRecordCount();
	// dto.setTotalCount(totalCount);
	// StringBuffer patentInfoList = new StringBuffer();
	// if (totalCount > 0) {
	// Map<String, StringBuffer> patmap = new HashMap<String, StringBuffer>();
	// // 取出本次需要的全部记录
	// for (long i = 0; i < totalCount; i++) {
	// trsresultset.moveTo(0, i);
	// String dbname = trsresultset.getRecord().strName;
	// StringBuffer sb = null;
	// if (patmap.get(dbname) == null) {
	// sb = new StringBuffer();
	// } else {
	// sb = patmap.get(dbname);
	// }
	// sb.append(trsresultset.getString(Constants.SHEN_QING_HAO));
	// sb.append("@");
	// if (dbname.contains("_")) {
	// sb.append(trsresultset
	// .getString(Constants.GONG_KAI_HAO));
	// } else {
	// sb.append(trsresultset
	// .getString(Constants.ZHUAN_LI_HAO));
	// }
	// sb.append("@");
	//
	// patmap.put(dbname, sb);
	// }
	//
	// /** 拼检索号单 */
	// Iterator<?> it = patmap.entrySet().iterator();
	// while (it.hasNext()) {
	// Entry<?, ?> entry = (Entry<?, ?>) it.next();
	// StringBuffer value = (StringBuffer) entry.getValue();
	// String key = (String) entry.getKey();
	// if (key.contains("_")) {
	// patentInfoList.append("^").append(key.substring(0, 4))
	// .append("@")
	// .append(value.substring(0, value.length() - 1));
	// } else {
	// patentInfoList.append("^").append(key.substring(0, 2))
	// .append("@")
	// .append(value.substring(0, value.length() - 1));
	// }
	// }
	// dto.setNumberList(patentInfoList.toString());
	// }
	//
	// } catch (TRSException e) {
	// new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
	// "执行TRS检索出错", "PatOverviewSearcher", "doSearch", "trs");
	// dto.setReturncode(2002);
	// dto.setMessage(e.getMessage());
	// }
	//
	// return dto;
	// }

}
