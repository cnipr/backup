/**
 * 
 */
package com.cnipr.pss.search.db;

import com.cnipr.pss.util.ParseSemanticExp;
import com.cnipr.pss.util.ReadresourceUtil;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.zl.SemanticSearch;

/**
 * @author 戴国栋 (lq修改)
 * 
 *         create 2010-1-12 company 知识产权出版社
 * @version 1.0
 */
public class SemanticSearchOperation {
	
	private String trsLastWhere;

	private String patentKeyWords;

	public TRSResultSet getSemanticResult(String strSources, String strWhere,
			String strSortMethod, int iOption, int iHitPointType,
			boolean bContinue, String strSynonymous, String similarWhere,
			String srcWhere, SemanticSearch semanticInstance)
			throws NumberFormatException, TRSException {
		// semanticInstance.cleanTrsResult(trsRs)
		// strWhere = "ss=计算机";

		TRSResultSet trsresultset = null;
		String[] semanticArr = ParseSemanticExp.getSemanticExp(srcWhere);
		// String relation = "";// 智能检索和普通检索的逻辑关系
		String keyWords = "";// 智能检索输入的内容
		// String commonCond = "";// 普通检索条件
		String strThesaurus = "";// 主题词典,做相似性检索时候要用到
		String strAntonym = "";// 同义词典
		String similarSource = "";// 相似性检索的目标库

		if (similarWhere != null && !similarWhere.equals("")) {
			strSortMethod = "RELEVANCE";
			strThesaurus = ReadresourceUtil.getDict("strThesaurus");
			strSynonymous = ReadresourceUtil.getDict("strSynonymous");
			strAntonym = ReadresourceUtil.getDict("strAntonym");
			if (strSources == null || strSources.equals("")) {
				similarSource = ReadresourceUtil.getDict("similarSource");
				strSources = similarSource;
			} else {
				similarSource = strSources;
			}
			String[] wheres = similarWhere.split("and");
			
			similarWhere = similarSource + ":(" + wheres[0] + ")";
			try {
				strWhere =wheres[1];
			} catch (Exception e) {
				strWhere = "";
			}
			
		}

		// try {
		if (semanticArr != null && semanticArr.length > 0) {
			// relation = semanticArr[0].trim();
			keyWords = semanticArr[1];
			// commonCond = semanticArr[2];
		}
		
		// 自动扩展检索选项掩码设置(同义词、反义词扩展)
		int iOptionMask = 0;
		// 同义词扩展
		if (strSynonymous != null && !strSynonymous.equals("")) {
			iOptionMask = TRSConstant.TCM_KAXST;
		}
		// 反义词扩展
		if (strAntonym != null && !strAntonym.equals("") && iOptionMask != 0) {
			iOptionMask = iOptionMask | TRSConstant.TCM_KAXAT;
		} else if (strAntonym != null && !strAntonym.equals("") && iOptionMask == 0) {
			iOptionMask = TRSConstant.TCM_KAXAT;
		}
		
//		if (strSortMethod == null || strSortMethod.equals("")) {
//			strSortMethod = "+table_sn";
//		} else {
//			strSortMethod = strSortMethod + ",+table_sn";
//		}
//		System.out.println("similarWhere=" + similarWhere
//				+ ";strSources=" + strSources + ";strWhere=" + strWhere
//				+ ";and=and;keywordnums=" + Integer.parseInt(ReadresourceUtil.getNum("keywordnums"))
//				+ ";strSortMethod=" + strSortMethod + ";ioption=" + (TRSConstant.TCM_SORTALWAYS
//				| TRSConstant.TCE_OFFSET)
//				+ ";srcWhere=" + srcWhere + ";bContinue="
//				+ bContinue + ";strThesaurus="
//				+ strThesaurus + ";strSynonym=;strAntonym="
//				+ strAntonym + ";iOptionMask="
//				+ iOptionMask);
		
		trsresultset = semanticInstance.GetSimilarPatants(similarWhere,
				strSources, strWhere, "and",
				Integer.parseInt(ReadresourceUtil.getNum("keywordnums")),
				strSortMethod, TRSConstant.TCM_SORTALWAYS
						| TRSConstant.TCE_OFFSET, srcWhere, bContinue,
				strThesaurus, "", strAntonym, iOptionMask);

		// 将上次的检索结果记录
		// System.out.println("semanticInstance.getLastWhere()="
		// + semanticInstance.getLastWhere());

		setTrsLastWhere(semanticInstance.getLastWhere());
		setPatentKeyWords(keyWords);
		// } catch (Exception wasexception) {
		// wasexception.printStackTrace();
		// trsresultset = null;
		// }
		return trsresultset;
	}

	/**
	 * 获取检索条件的关键词
	 * 
	 * @return
	 */
	public String getPatentWords(String appNum, String tableName,
			String inputQuerys, String searchCond) {
		SemanticSearch semanticInstance = new SemanticSearch();
		int wordsNum = Integer.parseInt(ReadresourceUtil
				.getNum("patentkeywordnums"));
		String patKeyWords = "";
		String keyWords = "";
		try {
			String[] fields = new String[] { "名称", "摘要", "权利要求书", "说明书" };
			patKeyWords = semanticInstance.GetPatentKeyWords(appNum, tableName,
					inputQuerys, "", ReadresourceUtil.getDict("FilterWords"),
					searchCond, wordsNum, fields);
			String[] firstArray = patKeyWords.split(";");
			for (String tempStr : firstArray) {
				String[] lastArray = tempStr.split("\t");
				keyWords += lastArray[0] + " ";
			}
		} catch (Exception e) {
			patKeyWords = "";
		}
		return patKeyWords;
	}

	/**
	 * 获取检索条件的相关概念
	 * 
	 * @return
	 */
	public String getRelevance(String tableName, String abs) {
		SemanticSearch semanticInstance = new SemanticSearch();
		String[] revWordArray = null;
		String keyWords = "";
		String dictName = ReadresourceUtil.getDict("revelancedict").equals("") ? "demo"
				: ReadresourceUtil.getDict("revelancedict");
		try {
			revWordArray = semanticInstance.GetRelevantQuerys(tableName,
					dictName, abs, 0);
			if (revWordArray != null) {
				int wordLimit = 0;
				for (String tempStr : revWordArray) {
					if (wordLimit > 5) {
						break;
					}
					keyWords += tempStr.split("\t")[0] + " ";
					wordLimit++;
				}
			}
		} catch (Exception e) {
			keyWords = "";
		}
		return keyWords;
	}

	/**
	 * 获取智能提示词
	 * 
	 * @return
	 */
	public String[] getWordTips(String inputParam) {
		SemanticSearch semanticInstance = new SemanticSearch();
		String[] wordTips = null;
		int tipsNum = Integer.parseInt(ReadresourceUtil.getNum("wordtipsnum"));
		try {
			wordTips = semanticInstance.GetWordTips(inputParam, tipsNum, "");
		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			wordTips = null;
		}
		return wordTips;
	}

	/**
	 * 获取智能检索实时提取ABS
	 * 
	 * @param trsResult
	 */
	public String getAbs(String appNo, String _tablename, String stopWords,
			String weiWords, int absNum, int absPercent) {
		SemanticSearch semanticInstance = new SemanticSearch();
		String _pn = "申请号=" + appNo;
		String abs = "";
		try {
			String[] fields = new String[] { "说明书" };
			abs = semanticInstance.GetPatentAbs(_pn, _tablename, "", weiWords,
					stopWords, "", absNum, absPercent, fields);
		} catch (Exception e) {
			abs = "";
		}
		return abs;
	}

	public String getTrsLastWhere() {
		return trsLastWhere;
	}

	public void setTrsLastWhere(String trsLastWhere) {
		this.trsLastWhere = trsLastWhere;
	}

	public String getPatentKeyWords() {
		return patentKeyWords;
	}

	public void setPatentKeyWords(String patentKeyWords) {
		this.patentKeyWords = patentKeyWords;
	}

}
