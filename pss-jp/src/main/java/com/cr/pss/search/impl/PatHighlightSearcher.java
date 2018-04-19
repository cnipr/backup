package com.cnipr.pss.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatHighLightSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;

/**
 * 专利代码化全文(xml)检索人
 * 
 * @author lq
 * 
 */
public class PatHighlightSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatDetailSearcher.class);

	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;

	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;

	/** 结果记录的排序方式 **/
	private String strSortMethod;
	/** 是否相似性检索1：是； 其他：否 **/
	private int isSimilarSearch = 0;
	/**
	 * 检索选项掩码
	 **/
	private int iOption;

	/** 本次检索专利的下标 **/
	private long recordCursor;

	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	private String[] columns;

	public PatHighlightSearcher() {
	}

	public PatHighlightSearcher(String strSources, String strWhere,
			String strSortMethod, int iOption, long recordCursor,
			String strSynonymous, int isSimilarSearch, String[] columns) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.isSimilarSearch = isSimilarSearch;
		// this.iHitPointType = iHitPointType;
		// this.bContinue = bContinue;
		this.recordCursor = recordCursor;
		this.strSynonymous = strSynonymous;
		this.columns = columns;

		logger.info("[PatHighlightSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";isSimilarSearch=" + isSimilarSearch
				+ ";recordCursor=" + recordCursor + ";strSynonymous="
				+ strSynonymous);
	}

	/**
	 * 专利代码化全文(xml)检索
	 */
	@Override
	public PatHighLightSearchDTO doSearch() {
		PatHighLightSearchDTO dto = new PatHighLightSearchDTO();
		InputStream xml_is = null;
		InputStreamReader isr = null;
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			// 相似性检索表达式
			String similarWhere = "";
			if (isSimilarSearch == 1) {
				similarWhere = strWhere;
			} else {
				strWhere = (new GetSearchFormat()).preprocess(strWhere);
			}

			String tempLastWhere = semanticInstance.ExpressionParse(strSources,
					strWhere, 0, "RELEVANCE", iOption, null, strSynonymous,
					null, TRSConstant.TCM_KAXST);

//			if (strSortMethod == null || strSortMethod.equals("")) {
//				strSortMethod = "+table_sn;+sysid";
//			} else {
//				strSortMethod = strSortMethod + ";+table_sn;+sysid";
//			}

			trsresultset = (new SemanticSearchOperation()).getSemanticResult(
					strSources, tempLastWhere, strSortMethod, iOption, 115,
					false, strSynonymous, similarWhere, "", semanticInstance);

			// trsresultset = (new
			// TRSOperation()).executeTRSSearch(trsConnection,
			// strSources, strWhere, "", "", "", 0, 0, false, "");
			if (trsresultset.getRecordCount() > 0) {
				trsresultset.moveTo(0, recordCursor);
				System.out.println(trsresultset.getColumnType("权利要求书"));
//				 String an = trsresultset.getString("an");
//				 System.out.println(an);
				for (int i = 0; i < columns.length; i++) {
					xml_is = trsresultset.getBinaryStream(columns[i], 0);
					if (xml_is == null) {
						continue;
					}
					StringBuffer sb = new StringBuffer();
					isr = new InputStreamReader(xml_is, "UTF-8");
					char buf[] = new char[1024];
					int nBufLen = isr.read(buf);
					while (nBufLen != -1) {
						sb.append(new String(buf, 0, nBufLen));
						nBufLen = isr.read(buf);
					}

					if (sb != null) {
						dto.getResultMap().put(columns[i], sb.toString());
						// xmlByte = sb.toString().getBytes();
					}
				}
				//设置当前这条专利的数据库名称
				TRSRecord rec = trsresultset.getRecord();
				dto.getResultMap().put("paramDb", rec.strName.toUpperCase());
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "PatHighlightSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		} catch (Exception e) {
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"PatHighlightSearcher", "doSearch(strSources:" + strSources
							+ ";strWhere:" + strWhere + ";columns:"
							+ columns.toString() + ")", "java");
			dto.setReturncode(1000);
			dto.setMessage(e.getMessage());
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
							e.getMessage(), "PatHighlightSearcher", "doSearch",
							"java");
				}
			}
		}

		// dto.setXmlByte(xmlByte);
		return dto;
	}

}
