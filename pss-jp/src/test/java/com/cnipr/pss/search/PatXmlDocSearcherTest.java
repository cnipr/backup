package com.cnipr.pss.search;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.search.impl.PatHighlightSearcher;
import com.cnipr.pss.search.impl.PatDetailSearcher;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSResultSet;

public class PatXmlDocSearcherTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		PatXmlDocSearchDTO dto = doSearch();
//		System.out.println(dto.getStrXml()); 
//		AbstractSearcher test1 = new PatDetailSearcher("fmzl,syxx", "名称,摘要,权利要求书+=(太？光？池)",
//				"RELEVANCE", Integer.parseInt("2"),
//				Integer.parseInt("2"), "", 0);
//		test1.excute();
		
		String[] columns = {"权利要求书","说明书"};
		AbstractSearcher test = new PatHighlightSearcher("fmzl,syxx", "%",
				"RELEVANCE", 2, 2l, "", 0, columns);
		test.excute();
	}

	public static PatXmlDocSearchDTO doSearch() {
		PatXmlDocSearchDTO dto = new PatXmlDocSearchDTO();
		InputStream xml_is = null;
		InputStreamReader isr = null;
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			String strWhere = "名称,摘要,权利要求书+=(太？光？池)";
			String	strSortMethod = "RELEVANCE;+table_sn;+sysid";
			// 对表达式中所包含的特殊字符进行转义
			strWhere = (new GetSearchFormat()).preprocess(strWhere);
			
			TRSResultSet trsresultset = (new TRSOperation()).executeTRSSearch(
					new TRSOperation().getConnect(), "FMZL,SYXX", strWhere, strSortMethod,
					"", "", 2, 115, false, "");
			if (trsresultset.getRecordCount() > 0) {

				trsresultset.moveTo(0, 2);
				xml_is = trsresultset.getBinaryStream("AN", 0);

				StringBuffer sb = new StringBuffer();
				isr = new InputStreamReader(xml_is, "UTF-8");
				char buf[] = new char[1024];
				int nBufLen = isr.read(buf);
				while (nBufLen != -1) {
					sb.append(new String(buf, 0, nBufLen));
					nBufLen = isr.read(buf);
				}

				if (sb != null) {
					dto.setStrXml(sb.toString());
					// xmlByte = sb.toString().getBytes();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (xml_is != null) {
				try {
					xml_is.close();
				} catch (IOException e) {
					new PSSException("3000", "3000:" + e.getMessage(),
							e.getMessage(), "PatXmlDocSearcher", "doSearch",
							"java");
				}
			}
		}
		// dto.setXmlByte(xmlByte);
		return dto;
	}

}
