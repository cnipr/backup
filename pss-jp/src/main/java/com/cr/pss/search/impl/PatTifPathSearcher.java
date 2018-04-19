package com.cnipr.pss.search.impl;

import java.util.ArrayList;
import java.util.List;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.Constants;
import com.eprobiti.trs.TRSException;


/**
 * 专利tif路径及页数检索人（暂时只有细缆检索时用到，没有发布对外的服务）
 * @author lq
 *
 */
public class PatTifPathSearcher extends AbstractSearcher {

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 专利的申请号  */
	private String appID;

	public PatTifPathSearcher() {
	}

	public PatTifPathSearcher(String strSources, String appID) {
		/** 走TRS普通检索  */
		this.searchType = 1;
		this.strSources = strSources;
		this.appID = appID;
		
	}

	/** 专利tif路径及页数检索 */
	@Override
	public List<String> doSearch() {
		List<String> tifInfo = null;
		// 根据申请号拼出检索表达式
		String strWhere = "app_id=" + appID;
//		String strANShort = an;
//		if (strANShort.indexOf(".") > -1) {
//			strANShort = strANShort.substring(0, strANShort.indexOf("."));
//		}
//		if (an.equals(strANShort)) {
//			strWhere = "申请号=" + an;
//		} else {
//			strWhere = "申请号=(" + an + " or " + strANShort + "%)";
//		}

		try {
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection, strSources,
					strWhere, "", "", "", 0, 0, false, "");
			if (trsresultset.getRecordCount() > 0) {
				trsresultset.moveTo(0, 0);
				tifInfo = new ArrayList<String>();
				
				String tifpath = (trsresultset
						.getString(Constants.FA_BU_LU_J) == null) ? ""
						: trsresultset.getString(Constants.FA_BU_LU_J)
								.replaceAll("\\\\", "/");
				if (tifpath != null) {
					String[] temp = tifpath.split("/");
					if (temp.length >= 4 && temp[3].contains(".")) {
						tifpath = "";
						temp[3] = temp[3].replace(".", "");
					}
					tifpath = "";
					for (String s : temp) {
						tifpath += s + "/";;
					}
					
				}
				
				tifInfo.add(tifpath);
				tifInfo.add(trsresultset.getString(Constants.YE_SHU));
//				/**
//				 * 授权说明书页数
//				 */
//				tifInfo.add(trsresultset.getString(Constants.SHUO_MING_SHU_YS));
//				tifInfo.add(trsresultset
//						.getString(Constants.SHUO_MING_SHU_FTYS));

			}
		} catch (TRSException e) {
			tifInfo = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "PatTifPathSearcher", "doSearch", "trs");
		}
		
		return tifInfo;
	}

}
