package com.cnipr.pss.search;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.zl.SemanticSearch;

public class TrsSematicSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SemanticSearch semanticInstance = (new TRSOperation()).getSemanticInstance();
		
		SemanticSearchOperation operation = new SemanticSearchOperation();
		TRSResultSet trsresultset =null;
		try {
			String strWhere = (new GetSearchFormat()).preprocess("an=%");

			// SemanticSearch semanticSearch = new SemanticSearch();
			String tempLastWhere = semanticInstance.ExpressionParse("fmzl",
					strWhere, 0, "RELEVANCE", 0, null, "",
					null, 1);

			String	strSortMethod = "+table_sn;+sysid";

			trsresultset = operation.getSemanticResult("fmzl",
					tempLastWhere, strSortMethod, 0, 115, false,
					"", "", strWhere, semanticInstance);
			trsresultset.moveFirst();
			System.out.println(trsresultset.getString("名称"));
			if (trsresultset == null) {
//				dto.setReturncode(2100);
//				dto.setMessage("检索结果集为空");
//				// dto.setSearchMessageBean(searchMessageBean);
//				return dto;
			}
		} catch (TRSException e) {
			e.printStackTrace();
		} finally {
			if (semanticInstance != null) {
				semanticInstance.cleanTrsResult(trsresultset);
			}

			if (trsresultset != null) {
				try {
					trsresultset.close();
				} catch (Exception exception) {
				}
			}
		}
	}

}
