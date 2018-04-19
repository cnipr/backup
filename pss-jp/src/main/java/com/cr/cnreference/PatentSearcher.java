package com.cnipr.cnreference;

import java.util.LinkedList;
import java.util.List;

import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.Constants;
import com.eprobiti.trs.TRSException;

public class PatentSearcher extends AbstractSearcher {
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	public PatentSearcher(String strSources, String strWhere) {
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
	}

	@Override
	public List<String> doSearch() {
		List<String> anList = new LinkedList<String>();

		SemanticSearchOperation operation = new SemanticSearchOperation();
		try {
			trsresultset = operation.getSemanticResult(strSources, strWhere,
					"", 2, 115, false, "", "", strWhere, semanticInstance);
			
			if (trsresultset == null) {
				return anList;
			}
			trsresultset.setReadOptions(0, "申请号", null);
			trsresultset.setBufferSize(10000, 10000);

			long recordCount = trsresultset.getRecordCount();

			System.out.println(recordCount);
			if (recordCount > 0) {
				for (long i = 0; i < recordCount; i++) {
					// 移动到检索结果记录集的指定记录
					trsresultset.moveTo(0, i);
					anList.add(trsresultset.getString(Constants.SHEN_QING_HAO) + "," + trsresultset.getString(Constants.SHENG_QING_RI));
				}
			}
		} catch (TRSException e) {
			e.printStackTrace();
		}
		return anList;
	}

	public static void main(String[] args) {
		AbstractSearcher searcher = new PatentSearcher("fmzl", "申请日 =2009");
		List<String> anList = (List<String>)searcher.excute();
		System.out.println(anList.size());
	}
}
