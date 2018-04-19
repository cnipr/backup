package com.cnipr.pss.search;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.search.db.TRSOperation;
import com.cnipr.pss.util.PssUtil;
import com.eprobiti.trs.TRSConnection;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.clu.TrsPatentCluParam;
import com.trs.ckm.soap.CluTrsResult;
import com.trs.ckm.zl.PatentCluster;
import com.trs.ckm.zl.PatentsSimilarity;
import com.trs.ckm.zl.SemanticSearch;

/**
 * 检索人抽象类，所有检索人继承该类
 * 
 * @author aa
 * 
 */
public abstract class AbstractSearcher {

	/** 语义检索实例 */
	public SemanticSearch semanticInstance;
	/** trs普通检索连接 */
	public TRSConnection trsConnection;
	/** 执行TRS检索 */
	public TRSResultSet trsresultset;
	/** ckm聚类 */
	public PatentCluster cluster;
	/** ckm聚类结果集 */
	public CluTrsResult cluResult;
	/** ckm聚类设置参数 */
	public TrsPatentCluParam trsParam;
	/** 相似度检索 */
	public PatentsSimilarity patSimilarity;

	/**
	 * 检索人类型， 构造函数会根据该类型实例化trsConnection或semanticInstance， 类型包括：1：普通检索;2、语义检索
	 * {@link com.cnipr.pss.util.Constants}
	 */
	public int searchType;

	/**
	 * 获取某类型的返回信息，如：细缆取国内信息时该值为1，取国外 时为2，如无区分的默认为1即可
	 */
	public int getInfoType = 1;

	/** 执行检索任务：包括获取连接、检索、释放连接 */
	public Object excute() {
		preSearch();
		Object o = null;
		try {
			o = doSearch();
		} catch (Exception e) {
			e.printStackTrace();
			new PSSException("1001", "1001:" + e.getMessage(),
					"执行检索出错,非trs错误", "AbstractSearcher", "excute", "java");
		} finally {
			postSearch();
		}
		
		return o;
	}

	/** 检索前要做的一些处理，根据检索人类型获取trs连接或是语义的实例 */
	public void preSearch() {
		// 普通检索
		if (searchType == 1) {
			trsConnection = (new TRSOperation()).getConnect();
		} else if (searchType == 2) {
//			trsConnection = (new TRSOperation()).getConnect();
			semanticInstance = (new TRSOperation()).getSemanticInstance();
		} else if (searchType == 3) {
			cluster = new PatentCluster();
			cluster.SetCKMServer(PssUtil.getProperty("CKMHost"),
					PssUtil.getProperty("CKMUserName"),
					PssUtil.getProperty("CKMPasswd"));
			trsParam = new TrsPatentCluParam();
			trsParam.settrsbasename("FMZL,SYXX,FMSQ,WGZL");
			trsParam.setcid(new StringBuffer()
					.append(Thread.currentThread().getId()).append("-")
					.append(System.currentTimeMillis()).toString());
			trsParam.settrsserverhost(PssUtil.getProperty("TRSHost"));
			trsParam.settrsserverport(PssUtil.getProperty("TRSPort"));
			trsParam.settrsusername(PssUtil.getProperty("TRSUserName"));
			trsParam.settrspassword(PssUtil.getProperty("TRSPasswd"));
			trsParam.settrsmaxcount(Integer.parseInt(PssUtil
					.getProperty("clu.doc.max"))); // 最大检索结果数
			trsParam.settrsmaxcls(Integer.parseInt(PssUtil
					.getProperty("clu.chart.max"))); // 最大分类数
			trsParam.setTrsmincls(Integer.parseInt(PssUtil
					.getProperty("clu.chart.max"))); // 最小分类数
			trsParam.setKeywordnum(Integer.parseInt(PssUtil
					.getProperty("clu.keyword.num"))); // 主题词个数
			trsParam.settrsrowidcolumn(PssUtil.getProperty("clu.trs.id")); // 主键字段
			trsParam.settrsdoccolumn(PssUtil.getProperty("clu.trs.col")); // 检索字段
			trsParam.settrstitlecolumn("名称;申请（专利权）人;分类号;国省代码"); // 返回内容
		} else if (searchType == 4) {
			patSimilarity = new PatentsSimilarity();
		}
	}

	/** 检索 */
	public abstract Object doSearch();

	/** 检索后要做的一些处理，释放trs连接或是语义的实例 */
	public void postSearch() {
		if (semanticInstance != null) {
			semanticInstance.cleanTrsResult(trsresultset);
		}

		if (trsresultset != null) {
			try {
				trsresultset.close();
			} catch (Exception exception) {
			}
		}

		if (trsConnection != null) {
			try {
				trsConnection.close();
			} catch (Exception exception) {
			}
		}

		if (cluResult != null) {
			try {
				if (cluResult.getstatus() == 1) {
					cluster.ClusterPatentTrsStop(trsParam, 0);
					cluster.ClusterPatentTrsDel(trsParam, 0);
				} else if (cluResult.getstatus() == -1) {
					cluster.ClusterPatentTrsDel(trsParam, 0);
				} else {
					cluster.ClusterPatentTrsDel(trsParam, 0);
				}
			} catch (TRSException e) {
				e.printStackTrace();
			}
		}

	}
}
