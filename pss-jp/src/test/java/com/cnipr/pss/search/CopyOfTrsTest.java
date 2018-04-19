package com.cnipr.pss.search;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.zl.SemanticSearch;

public class CopyOfTrsTest {
	
	private static final Logger logger = LoggerFactory
	.getLogger(CopyOfTrsTest.class);
	
	public static void main(String[] args) {
		if (args[0].equals("0")) {
			for (int j=0; j < 34; j++) {
				SemanticSearch search = new SemanticSearch();
				TRSResultSet trsresultset =null;
				try {
					trsresultset = search.GetSimilarPatants("",
							"FMZL", "申请日=(19911201 to 20111130) and 公开（公告）日=(19911201 to 20111130) and 分类号=(c21b% or c21c% or c22c29\\/% or c22c38\\/% or b32b15\\/18%) and 名称,摘要,主权项,权利要求书+=(%磁钢% or %磁性钢% or %电磁钢% or %磁性%钢% or %电磁%钢% or %电工%钢% or %电炉钢%)", "", 0, "",
							TRSConstant.TCM_SORTALWAYS | TRSConstant.TCE_OFFSET, "",
							false, "", "", "", TRSConstant.TCM_KAXST);
					long ii = j*10 + 10;
					if (ii > trsresultset.getRecordCount()) {
						ii = trsresultset.getRecordCount();
					}
					for (long i = j*10; i < (j*10 + 10); i++) {
						trsresultset.moveTo(0, i);
						logger.error(trsresultset.getString("an"));
					}
				} catch (TRSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					search.cleanTrsResult(trsresultset);
				}
			}
		} else {
			SemanticSearch search = new SemanticSearch();
			TRSResultSet trsresultset =null;
			try {
				trsresultset = search.GetSimilarPatants("",
						"FMZL", "申请日=(19911201 to 20111130) and 公开（公告）日=(19911201 to 20111130) and 分类号=(c21b% or c21c% or c22c29\\/% or c22c38\\/% or b32b15\\/18%) and 名称,摘要,主权项,权利要求书+=(%磁钢% or %磁性钢% or %电磁钢% or %磁性%钢% or %电磁%钢% or %电工%钢% or %电炉钢%)", "", 0, "",
						TRSConstant.TCM_SORTALWAYS | TRSConstant.TCE_OFFSET, "",
						false, "", "", "", TRSConstant.TCM_KAXST);
				for (long i = 0; i < 333; i++) {
					// 移动到检索结果记录集的指定记录
					trsresultset.moveTo(0, i);
					logger.error(trsresultset.getString("an"));
				}
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				search.cleanTrsResult(trsresultset);
			}
		}
		

	}
}
