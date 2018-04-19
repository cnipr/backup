package com.cnipr.pss.search.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatClusterSearchDTO;
import com.cnipr.pss.rs.dto.clu.Cluster;
import com.cnipr.pss.search.AbstractSearcher;
import com.cnipr.pss.util.clu.Processor;
import com.cnipr.pss.util.clu.TrsProcessor;

public class TrsCluSearcher extends AbstractSearcher {
	private static final Logger logger = LoggerFactory
			.getLogger(TrsCluSearcher.class);

	private String sources;
	private String where;
	private String pb;
	private String jq;
	private String option;
	private String synonym;
	private String docCol1;
	private String clsNum1;
	private String keywordNum1;
	private String display;

	private Processor processor = new TrsProcessor();

	public TrsCluSearcher(String sources, String where, String pb, String jq,
			String option, String synonym, String docCol1, String clsNum1,
			String keywordNum1, String display) {
		super();
		this.searchType = 3; // 聚类
		this.sources = sources;
		this.where = where;
		this.pb = pb;
		this.jq = jq;
		this.option = option;
		this.synonym = synonym;
		this.docCol1 = docCol1;
		this.clsNum1 = clsNum1;
		this.keywordNum1 = keywordNum1;
		this.display = display;

		logger.info("[TrsCluSearcher]sources=" + sources + ";where="
				+ where + ";pb=" + pb + ";jq=" + jq + ";option=" + option
				+ ";synonym=" + synonym + ";docCol1=" + docCol1 + ";clsNum1="
				+ clsNum1 + ";keywordNum1=" + keywordNum1 + ";display="
				+ display);
	}

	/**
	 * 聚类
	 * 
	 * @param flagCode
	 * @param sources
	 * @param where
	 * @param option
	 * @param synonym
	 * @param docCol1
	 * @param clsNum1
	 * @param keywordNum1
	 * @param display
	 *            应为ap,ti,similarity,ipc,pa,area,cc
	 * @throws DocumentException
	 * @throws IOException
	 * @throws Exception
	 */
	public PatClusterSearchDTO doSearch() {
		if (!StringUtils.isEmpty(sources)) {
			trsParam.settrsbasename(sources);
		}
		PatClusterSearchDTO model = new PatClusterSearchDTO();
		if (clsNum1 != null && !"".equals(clsNum1)) {
			trsParam.settrsmaxcls(Integer.parseInt(clsNum1)); // 最大分类数
			trsParam.setTrsmincls(Integer.parseInt(clsNum1)); // 最小分类数
		}
		if (keywordNum1 != null && !"".equals(keywordNum1)) {
			trsParam.setKeywordnum(Integer.parseInt(keywordNum1)); // 主题词个数
		}
		if (docCol1 != null && !"".equals(docCol1)) {
			trsParam.settrsdoccolumn(docCol1); // 检索字段
		}
		trsParam.setStopWord(pb);// 屏蔽词
		trsParam.setThemeWord(jq);// 加权词
		trsParam.settrswherexpr(where);
		if (synonym != null && !"".equals(synonym)) {
			trsParam.setStrSynonym(synonym);
		}
		if (option != null && !"".equals(option)) {
			trsParam.setImask(Integer.parseInt(option));
		}

		// 开始聚类
		try {
			cluResult = cluster.ClusterPatentTrsFile(trsParam, 0);
			for (int i = 0; (cluResult.getstatus() > 0 && i < 300); i++) {
				Thread.sleep(2000);
				cluResult = cluster.ClusterPatentTrsQuery(trsParam, 0);
			}
			// 超时停止聚类，有结果不能stop
			if (cluResult.getstatus() == 1) {
				new PSSException("3006", "聚类超时", "聚类超时", "TrsCluImp",
						"doSearch", "trs");
				model.setReturncode(3006);
				model.setMessage("聚类超时");
				return model;
			} else if (cluResult.getstatus() == -1) {
				new PSSException("3005", "聚类结果为空", "聚类结果为空", "TrsCluImp",
						"doSearch", "trs");
				model.setReturncode(3005);
				model.setMessage("聚类结果为空");
				return model;
			}
		} catch (Exception e) {
			new PSSException("3006", e.getMessage(), "聚类超时", "TrsCluImp",
					"doSearch", "trs");
			model.setReturncode(3006);
			model.setMessage("聚类超时");
			return model;
		}
		if (cluResult.getstatus() != 0) {
			model.setReturncode(3005);
			model.setMessage("聚类结果为空");
			return model;
		}
		String cluXml = cluResult.getxmlret();// 得到聚类的xml结果
		List<Cluster> lstCluster = null;
		try {
			lstCluster = processor.parse(cluXml);
		} catch (DocumentException e) {
			new PSSException("3007", e.getMessage(), "解析聚类xml结果时发生错误",
					"TrsCluImp", "doSearch", "java");
			model.setReturncode(3007);
			model.setMessage("解析聚类xml结果时发生错误");
			return model;
		}
		processor.setDisplay(display, lstCluster);
		String chartXml = "";
		try {
			chartXml = processor.createXmlString(lstCluster);
		} catch (IOException e) {
			new PSSException("3008", e.getMessage(), "生成聚类xml结果时发生错误",
					"TrsCluImp", "doSearch", "java");
			model.setReturncode(3008);
			model.setMessage("生成聚类xml结果时发生错误");
			return model;
		}
		model.setList(lstCluster);
		model.setXml(chartXml);
		return model;
	}
}
