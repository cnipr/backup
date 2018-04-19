package com.cnipr.pss.search.enimpl;

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
public class EnPatTifPathSearcher extends AbstractSearcher {

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 专利的申请号  */
	private String an;

	public EnPatTifPathSearcher() {
	}

	public EnPatTifPathSearcher(String strSources, String an) {
		/** 走TRS普通检索  */
		this.searchType = 1;
		this.strSources = strSources;
		this.an = an;
		
	}

	/** 专利tif路径及页数检索 */
	@Override
	public List<String> doSearch() {
		List<String> tifInfo = null;
		// 根据申请号拼出检索表达式
		String strWhere = "";
		String strANShort = an;
		if (strANShort.indexOf(".") > -1) {
			strANShort = strANShort.substring(0, strANShort.indexOf("."));
		}
		if (an.equals(strANShort)) {
			strWhere = "AP=" + an;
		} else {
			strWhere = "AP=(" + an + " or " + strANShort + ")";
		}

		try {
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection, strSources,
					strWhere, "", "", "", 0, 0, false, "all");
			if (trsresultset.getRecordCount() > 0) {
				trsresultset.moveTo(0, 0);
				tifInfo = new ArrayList<String>();
				tifInfo.add((trsresultset.getString("PUB_Path") == null) ? ""
						: trsresultset.getString("PUB_Path")
								.replaceAll("\\\\", "/"));
				tifInfo.add(trsresultset.getString("pages"));
			}
		} catch (TRSException e) {
			tifInfo = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnPatTifPathSearcher", "doSearch", "trs");
		}
		
		return tifInfo;
	}

}
