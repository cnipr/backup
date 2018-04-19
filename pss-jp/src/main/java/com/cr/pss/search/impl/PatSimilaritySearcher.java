package com.cnipr.pss.search.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatSimilarityDTO;
import com.cnipr.pss.rs.dto.search.SimilarityInfo;
import com.cnipr.pss.search.AbstractSearcher;
import com.eprobiti.trs.TRSException;
import com.trs.ckm.zl.SimResult;

/**
 * 已知专利A、和专利集合B（B也可以是一件专利）。计算A的指定字段相对于B的对应字段的相似度
 * 
 * @author lq
 * 
 */
public class PatSimilaritySearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(PatSimilaritySearcher.class);

	/** 专利A的检索表达式（必须是完整的检索表达式） **/
	private String strWhereA;
	/** 参照系限定检索式（不能为空必须是完整的检索表达式） **/
	private String strWhereB;
	/** 专利文本A的指定字段名称（不能为空，多个字段之间用半角分号隔开） **/
	private String strField;

	public PatSimilaritySearcher(String strWhereA, String strWhereB,
			String strField) {
		this.searchType = 4;
		this.strWhereA = strWhereA;
		this.strWhereB = strWhereB;
		this.strField = strField;
		logger.info("[PatSimilaritySearcher]strWhereA=" + strWhereA
				+ ";strWhereB=" + strWhereB+ ";strField=" + strField);
	}

	@Override
	public PatSimilarityDTO doSearch() {
		PatSimilarityDTO dto = new PatSimilarityDTO();

		String table = "FMZL,SYXX,WGZL,FMSQ";
		try {
			SimResult[] results = patSimilarity.getPatentsSimilarity(strWhereA,
					table, strWhereB, table, strField);
			if (results == null) {
				return dto;
			} else {
				for(int i=0;i<results.length;i++)
	    		{
					BigDecimal bd = new BigDecimal(results[i].getSimv());
					SimilarityInfo info = new SimilarityInfo();
					info.setTargetId(results[i].getID());
					info.setSimilarity(bd.setScale(2, BigDecimal.ROUND_HALF_UP)
							.toString());
					dto.getSimilaritys().add(info);
	    		}
			}

		} catch (TRSException e) {
			new PSSException("2004", e.getErrorCode() + ":" + e.getMessage(),
					"执行相似度检索出错", "PatSimilaritySearcher", "doSearch", "trs");
			dto.setReturncode(2004);
			dto.setMessage(e.getMessage());
		}
		return dto;
	}

}
