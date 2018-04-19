package com.cnipr.pss.search.enimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatNumNoSysidExtractSearchDTO;
import com.cnipr.pss.rs.dto.search.ExtractInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetEnSearchFormat;
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
public class EnPatNumbersExtractSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(EnPatNumbersExtractSearcher.class);
	
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
	public EnPatNumbersExtractSearcher(String extractType, String sysids,
			String strSources, String strWhere, String strSortMethod,
			int iOption, String strSynonymous) {
		/** 走语义检索接口 */
		this.searchType = 1;
		this.extractType = extractType;
		this.sysids = sysids;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.strSynonymous = strSynonymous;
		
		logger.info("[EnPatNumbersExtractSearcher]searchType=" + searchType
				+ ";extractType=" + extractType + ";sysids=" + sysids
				+ ";strSources=" + strSources + ";strWhere=" + strWhere
				+ ";strSortMethod=" + strSortMethod + ";iOption=" + iOption
				+ ";strSynonymous=" + strSynonymous);
	}

	@Override
	/** 检索方法 */
	public PatNumNoSysidExtractSearchDTO doSearch() {
		PatNumNoSysidExtractSearchDTO dto = new PatNumNoSysidExtractSearchDTO();
		List<Object[]> extractInfoList = new ArrayList<Object[]>();
		if (!extractType.equals("1")) {
			if (sysids == null || sysids.equals("")) {
				dto.setReturncode(4000);
				dto.setMessage("参数不合法(提取的专利号单不能为空)");
				return dto;
			}

			strWhere = "sysid=" + sysids;
			if (strSources == null || strSources.equals("")) {
				strSources = "CNFMZL_EN,CNSYXX_EN,CNWGZL_EN";
			}
			
		}

		try {
			strWhere = (new GetEnSearchFormat()).preprocess(strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, strSortMethod, "", "", iOption,
					TRSConstant.TCE_OFFSET, false, strSynonymous);
			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				return dto;
			}
			/** 对申请号,公开（公告）号两字段设置缓存 */
			if (strSources.toLowerCase().contains("patent")) {
				trsresultset.setReadOptions(0, "AN,PNM,PD,TABLE_SN", null);
			} else {
				trsresultset.setReadOptions(0, "AN,PNM,PD,PATENT_TYPE", null);
			}
			
			trsresultset.setBufferSize(2000, 2000);

			long totalCount = trsresultset.getRecordCount();
			dto.setTotalCount(totalCount);
			if (totalCount > 0) {
//				DATA_SRC,PATENT_TYPE,AN,PN,PD
				// 取出本次需要的全部记录
				trsresultset.moveFirst();
				do {
//				for (long i = 0; i < totalCount; i++) {
//					trsresultset.moveTo(0, i);
					Object[] extractInfo = new Object[5]; 
					extractInfo[0] = "2";
					if (strSources.toLowerCase().contains("patent")) {
						extractInfo[1] = TableflagMap.getInstance().tbMap.get(trsresultset.getString("TABLE_SN"));
					} else {
						extractInfo[1] = trsresultset.getString("PATENT_TYPE");
					}
					extractInfo[2] = trsresultset.getString("AN");
					extractInfo[3] = trsresultset.getString("PNM");
					extractInfo[4] = trsresultset.getString("PD").replace(".", "");
					extractInfoList.add(extractInfo);
				} while (trsresultset.moveNext());
				dto.setExtractInfoList(extractInfoList);

			}

		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					"执行TRS检索出错", "EnPatNumbersExtractSearcher", "doSearch",
					"trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		}
		// else {
		// if (sysids == null || sysids.equals("")) {
		// dto.setReturncode(4000);
		// dto.setMessage("参数不合法(提取的专利号单不能为空)");
		// return dto;
		// }
		// String[] sysArray = sysids.split(",", -1);
		// for (String sysid : sysArray) {
		// if (sysid != null && !sysid.equals("")) {
		// ExtractInfo info = new ExtractInfo();
		// info.setSysId(sysid);
		// extractInfoList.add(info);
		// }
		// }
		// // String[] confFile = {"applicationContext.xml"};
		// // ClassPathXmlApplicationContext ctx = new
		// ClassPathXmlApplicationContext(confFile);
		// // METPatentDao dao = (METPatentDao)ctx.getBean("metPatentDao");
		// // extractInfoList = dao.extractPatBySysid(sysids);
		// dto.setTotalCount(Long.valueOf(extractInfoList.size()));
		// dto.setExtractInfoList(extractInfoList);
		// }

		return dto;
	}

	// @Override
	// /** 检索方法 */
	// public PatNumbersExtractSearchDTO doSearch() {
	// PatNumbersExtractSearchDTO dto = new PatNumbersExtractSearchDTO();
	// List<ExtractInfo> extractInfoList = new ArrayList<ExtractInfo>();
	// if (extractType.equals("1")) {
	// try {
	// strWhere = GetEnSearchFormat.preprocess(strWhere);
	// trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
	// strSources,
	// strWhere, strSortMethod, "", "", iOption, TRSConstant.TCE_OFFSET, false,
	// strSynonymous);
	// if (trsresultset == null) {
	// dto.setReturncode(2100);
	// dto.setMessage("检索结果集为空");
	// return dto;
	// }
	// /** 对申请号,公开（公告）号两字段设置缓存 */
	// trsresultset.setReadOptions(0, "AP,SYSID,APP_ID,TABLE_SN", null);
	// trsresultset.setBufferSize(2000, 2000);
	//
	// long totalCount = trsresultset.getRecordCount();
	// dto.setTotalCount(totalCount);
	// if (totalCount > 0) {
	// extractInfoList = new ArrayList<ExtractInfo>();
	// /*
	// * String[] ans = {"TW07221292","TW07224932","TW07112101"};
	// * String[] pns = {"TW052870","TW055472","TW055472"}; String[]
	// * sysids = {"28AB453BF1FAD72CB32F971144E9A9F4",
	// * "1C511ED115748A1629FEE07B7527CE5A"
	// * ,"D36157093EDA9018B106CE3FD120B6D9"}; String[] appids =
	// * {"TW07221292","TW07224932","TW07224932"};
	// *
	// * for (int i = 0; i < 3; i++) { ExtractInfo info = new
	// * ExtractInfo(); info.setAn(ans[i]); info.setPn(pns[i]);
	// * info.setSysId(sysids[i]); info.setAppId(appids[i]);
	// * extractInfoList.add(info); }
	// */
	//
	// // 取出本次需要的全部记录
	// for (long i = 0; i < totalCount; i++) {
	// trsresultset.moveTo(0, i);
	// ExtractInfo info = new ExtractInfo();
	//
	// info.setAn(trsresultset.getString("AP"));
	// // info.setPn(trsresultset.getString("PN"));
	// /*
	// StringBuffer ly = new StringBuffer();
	// String sectionName = trsresultset
	// .getSectionInfo((int) trsresultset.getRecordSect()).strName;
	// if (sectionName.length() == 8
	// && sectionName.toUpperCase().endsWith("PATENT")) {
	// ly.append(sectionName.substring(0, 2).toUpperCase());
	// } else {
	// ly.append(sectionName.substring(0, 4).toUpperCase());
	// }
	//
	// String strANShort = trsresultset
	// .getString(Constants.SHEN_QING_HAO);
	// if (strANShort.indexOf(".") > -1) {
	// strANShort = strANShort.substring(0,
	// strANShort.indexOf(".") - 1);
	// }
	//
	// ly.append("@")
	// .append(strANShort)
	// .append("@")
	// .append(trsresultset
	// .getString(Constants.GONG_KAI_HAO));
	//
	// info.setSysId(new BigInteger(1, DigestUtils.md5Digest(ly
	// .toString().toUpperCase().getBytes())).toString(16)
	// .toUpperCase());
	// info.setAppId("");*/
	// info.setSysId(trsresultset.getString("SYSID"));
	// info.setAppId(trsresultset.getString("APP_ID"));
	// info.setTb(TableflagMap.getInstance().tbMap.get(trsresultset.getString("TABLE_SN")));
	// // info.setSysId(trsresultset.getString("sysid"));
	// // info.setAppId(trsresultset.getString("app_id"));
	// extractInfoList.add(info);
	//
	// }
	// dto.setExtractInfoList(extractInfoList);
	//
	// }
	//
	// } catch (TRSException e) {
	// new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
	// "执行TRS检索出错", "EnPatNumbersExtractSearcher", "doSearch", "trs");
	// dto.setReturncode(2002);
	// dto.setMessage(e.getMessage());
	// }
	// } else {
	// if (sysids == null || sysids.equals("")) {
	// dto.setReturncode(4000);
	// dto.setMessage("参数不合法(提取的专利号单不能为空)");
	// return dto;
	// }
	//
	// String[] confFile = {"applicationContext.xml"};
	// ClassPathXmlApplicationContext ctx = new
	// ClassPathXmlApplicationContext(confFile);
	// METPatentDao dao = (METPatentDao)ctx.getBean("metPatentDao");
	// extractInfoList = dao.extractPatBySysid(sysids);
	// dto.setTotalCount(Long.valueOf(extractInfoList.size()));
	// dto.setExtractInfoList(extractInfoList);
	// }
	//
	// return dto;
	// }

}
