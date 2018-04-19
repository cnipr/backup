package com.cnipr.pss.search.enimpl;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.search.impl.PatDetailSearcher;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSException;

/**
 * 专利代码化全文(xml)检索人
 * 
 * @author lq
 * 
 */
public class EnPatXmlDocSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
	.getLogger(PatDetailSearcher.class);
	
	/** 检索的目标数据库/视图列表，多个对象名间以半角分号、逗号或空格分隔，不能为空 **/
	private String strSources;
	/** 检索条件表达式，为空表示无条件检索 **/
	private String strWhere;
	/** 要取的内容（为trs中的字段名称，分别为权利要求书、说明书、说明书附图） **/
	private String column;

	public EnPatXmlDocSearcher() {
	}

	public EnPatXmlDocSearcher(String strSources, String strWhere, String column) {
		this.strSources = strSources;
		this.strWhere = strWhere;
		this.column = column;
		this.searchType = 1;
	}

	/**
	 * 专利代码化全文(xml)检索
	 */
	@Override
	public PatXmlDocSearchDTO doSearch() {
		PatXmlDocSearchDTO dto = new PatXmlDocSearchDTO();
		byte[] xmlByte = null;
//		SearchMessageBean searchMessageBean = new SearchMessageBean(1, "");
		try {
//			strSources = strSources.toUpperCase().replaceAll("CNFMZL_EN", "CNFMZL_EN_Liuc").replaceAll("CNSYXX_EN", "CNSYXX_EN_liuc").replaceAll("CNWGZL_EN", "CNWGZL_EN_liuc").replaceAll("CNFMSQ_EN", "CNFMSQ_EN_liuc");
			logger.debug("EnPatXmlDocSearcher strSources:" + strSources + ";strWhere:" + strWhere);
			trsresultset = (new TRSOperation()).executeTRSSearch(trsConnection,
					strSources, strWhere, "", "", "", 0, 0, false, "");
			if (trsresultset.getRecordCount() > 0) {

				trsresultset.moveTo(0, 0);
				InputStream xml_is = trsresultset.getBinaryStream(column, 0);
				xmlByte = PssUtil.getBytes(xml_is);

			}
		} catch (TRSException e) {
			xmlByte = null;
			new PSSException("2002", e.getErrorCode() + ":" + e.getMessage(),
					e.getMessage(), "EnPatXmlDocSearcher", "doSearch", "trs");
			dto.setReturncode(2002);
			dto.setMessage(e.getMessage());
		} catch (Exception e) {
			xmlByte = null;
			new PSSException("1000", "1000:" + e.getMessage(), e.getMessage(),
					"EnPatXmlDocSearcher", "doSearch", "java");
			dto.setReturncode(1000);
			dto.setMessage(e.getMessage());
		}
//		dto.setXmlByte(xmlByte);
		return dto;
	}
}
