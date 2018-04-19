package com.cnipr.cnreference;

import java.util.List;

import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.Constants;
import com.cnipr.pss.util.ReadresourceUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.zl.SemanticSearch;

public class AllPatentExtracter {

	private SemanticSearch semanticInstance;
//	private String strWhere;
//	private String similarSource;

	public void doExtracter(String source, String where) {
		AbstractSearcher searcher = new PatentSearcher(source, where);
		List<String> anList = (List<String>) searcher.excute();
		System.out.println(anList.size());

		semanticInstance = (new TRSOperation()).getSemanticInstance();
		String strThesaurus = ReadresourceUtil.getDict("strThesaurus");
		String strAntonym = ReadresourceUtil.getDict("strAntonym");

		StringBuffer sb = new StringBuffer();

		for (String an : anList) {
			String similarWhere = "fmzl,syxx:(an=" + an.split(",", -1)[0] + ")";

			try {
				TRSResultSet trsresultset = semanticInstance.GetSimilarPatants(
						similarWhere, "fmzl,syxx", "申请日<"
								+ an.split(",", -1)[1], "and", 0, "RELEVANCE",
								TRSConstant.TCM_SORTALWAYS
								| TRSConstant.TCE_OFFSET,
						"", false, strThesaurus, "", strAntonym, TRSConstant.TCM_KAXST);
				if (trsresultset == null || trsresultset.getRecordCount() == 0) {
					continue;
				}

				for (long i = 0; i < 10; i++) {
					trsresultset.moveTo(0, i);
					sb.append(an.split(",", -1)[0])
							.append("@fldrlm@")
							.append(trsresultset
									.getString("SYSID"))
							.append("@fldrlm@")
							.append(trsresultset
									.getString(Constants.SHEN_QING_HAO))
							.append("@fldrlm@")
							.append(trsresultset
									.getString(Constants.GONG_KAI_HAO))
							.append("@fldrlm@")
							.append(trsresultset.getRelevance())
							.append("@fldrlm@#recrlm#\r\n");
				}

			} catch (TRSException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {
		AllPatentExtracter a = new AllPatentExtracter();
		a.doExtracter("fmzl", "申请号=(CN01810855.5)");
	}
}
