package com.cnipr.pss.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.SemanticSearchOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSRecord;


/**
 * 根据查询条件进行检索，返回List<Map>
 * 
 * @author weiding
 * 
 */
public class TrsListMapSearcher2 extends AbstractSearcher {
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

	/** 是否进行同义词扩展 **/
	private String strSynonymous;

	/**
	 * 普通字段
	 */
	private String[] columns;
	/**
	 * 全文字段
	 */
	private String[] columns2;

	/** 开始下标 **/
	private long startIndex;

	/** 结束下标 **/
	private long endIndex;
	

	public TrsListMapSearcher2() {
	}

	public TrsListMapSearcher2(String strSources, String strWhere,
			String strSortMethod, int iOption, String strSynonymous,
			int isSimilarSearch, String[] columns, String[] columns2,
			long startIndex, long endIndex) {
		/** 走语义检索接口 */
		this.searchType = 2;
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.strSortMethod = strSortMethod;
		this.iOption = iOption;
		this.isSimilarSearch = isSimilarSearch;
		// this.iHitPointType = iHitPointType;
		// this.bContinue = bContinue;
		this.strSynonymous = strSynonymous;
		this.columns = columns;
		this.columns2 = columns2;
		this.startIndex = startIndex;
		this.endIndex = endIndex;

		logger.info("[TrsListMapSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";strSortMethod=" + strSortMethod
				+ ";iOption=" + iOption + ";isSimilarSearch=" + isSimilarSearch
				+ ";strSynonymous=" + strSynonymous);
	}

	/**
	 * 专利代码化全文(xml)检索
	 */
	@Override
	public TrsListMapSearchDTO doSearch() {
		TrsListMapSearchDTO dto = new TrsListMapSearchDTO();
		InputStream xml_is = null;
		InputStreamReader isr = null;
		try {
			trsresultset = (new SemanticSearchOperation()).getSemanticResult(
					strSources, strWhere, strSortMethod, iOption, 115,
					false, strSynonymous, "", "", semanticInstance);

			if (trsresultset == null) {
				dto.setReturncode(2100);
				dto.setMessage("检索结果集为空");
				return dto;
			}
			long totalCount = trsresultset.getRecordCount();
			dto.setTotalCount(totalCount);
			if (totalCount == 0) {
				return dto;
			} else {
				// 第一次检索，用户不知道总记录数，因此结束下标有可能大于总记录数
				if (endIndex > totalCount) {
					endIndex = totalCount;
				}
				
				trsresultset.moveTo(0, startIndex);
				
				// 取出本次需要的全部记录
				for (long i = startIndex; i < endIndex; i++) {
					trsresultset.moveTo(0, i);
					Map<String, String> map = new HashMap<String, String>();
					for (int j = 0; j < columns.length; j++) {
						map.put(columns[j], trsresultset.getString(columns[j]));
					}
//					System.out.println(map.get("PUB_ID"));
					for (int j = 0; j < columns2.length; j++) {
						if ("".equals(columns2[j].trim())) {
							continue;
						}
						xml_is = trsresultset.getBinaryStream(columns2[j], 0);
						if (xml_is != null) {
							StringBuffer sb = new StringBuffer();
							isr = new InputStreamReader(xml_is, "UTF-8");
							char buf[] = new char[1024];
							int nBufLen = isr.read(buf);
							while (nBufLen != -1) {
								sb.append(new String(buf, 0, nBufLen));
								nBufLen = isr.read(buf);
							}
							if (sb != null) {
								map.put(columns2[j], sb.toString());
							}
						}
					}
					//设置当前这条专利的数据库名称
					TRSRecord rec = trsresultset.getRecord();
					map.put("patent_db", rec.strName.toUpperCase());
					dto.getResultList().add(map);
				}
			}

		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "TrsListMapSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		} catch (Exception e) {
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"TrsListMapSearcher", "doSearch(strSources:" + strSources
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
							e.getMessage(), "TrsListMapSearcher", "doSearch",
							"java");
				}
			}
		}

		// dto.setXmlByte(xmlByte);
		return dto;
	}
	
}
