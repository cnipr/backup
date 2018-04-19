package com.cnipr.pss.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.GetSearchFormat;
import com.eprobiti.trs.TRSException;

/**
 * 专利代码化全文(xml)检索人
 * 
 * @author lq
 * 
 */
public class PatXmlDocSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatDetailSearcher.class);
	
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	/** 要取的内容（为trs中的字段名称，分别为权利要求书、说明书、说明书附图） **/
	private String column;
	/**
	 * 游标,结果中有多条记录时用来获取某一条。
	 */
	private String recordCursor = "0";

	public PatXmlDocSearcher() {
	}

	public PatXmlDocSearcher(String strSources, String strWhere, String column, String... recordCursor) {
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.column = column;
		this.searchType = 1;
		if (recordCursor.length > 0) {
			this.recordCursor = recordCursor[0];
		}
		logger.info("[PatXmlDocSearcher]strSources=" + strSources
				+ ";strWhere=" + strWhere + ";column=" + column);
	}

	/**
	 * 专利代码化全文(xml)检索
	 */
	@Override
	public PatXmlDocSearchDTO doSearch() {
		PatXmlDocSearchDTO dto = new PatXmlDocSearchDTO();
		InputStream xml_is = null;
		InputStreamReader isr = null;
		// SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
			//对表达式中所包含的特殊字符进行转义
			strWhere = (new GetSearchFormat()).preprocess(strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, "", "", "", 0, 0, false, "");
			if (trsresultset.getRecordCount() > 0) {

				trsresultset.moveTo(0, Integer.parseInt(recordCursor));
				xml_is = trsresultset.getBinaryStream(column, 0);
				
				
				StringBuffer sb = new StringBuffer();    
				isr=new InputStreamReader(xml_is, "UTF-8");    
				char buf[] = new char[1024];    
				int nBufLen = isr.read(buf);    
				while(nBufLen!=-1){    
				    sb.append(new String(buf, 0, nBufLen));    
				    nBufLen = isr.read(buf);    
				} 
				
				if (sb != null) {
					dto.setStrXml(sb.toString());
//					xmlByte = sb.toString().getBytes();
				}
			}
		} catch (TRSException e) {
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "PatXmlDocSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		} catch (Exception e) {
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"PatXmlDocSearcher", "doSearch(strSources:" + strSources
							+ ";strWhere:" + strWhere + ";column:" + column + ")",
					"java");
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
							e.getMessage(), "PatXmlDocSearcher", "doSearch",
							"java");
				}
			}
		}

//		dto.setXmlByte(xmlByte);
		return dto;
	}
}
