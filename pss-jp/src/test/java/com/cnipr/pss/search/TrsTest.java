package com.cnipr.pss.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.rs.dto.ClassSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.search.PatClassInfo;
import com.cnipr.pss.search.impl.PatClassSearcher;
import com.cnipr.pss.search.impl.PatOverviewSearcher;
import com.trs.ckm.zl.SemanticSearch;
import com.trs.client.TRSConnection;
import com.trs.client.TRSConstant;
import com.trs.client.TRSException;
import com.trs.client.TRSHitPoint;
import com.trs.client.TRSResultSet;

public class TrsTest {

	private static final Logger logger = LoggerFactory.getLogger(TrsTest.class);

	public static void main2(String[] args) {
		AbstractSearcher searcher = new PatClassSearcher(args[0], args[1], "",
				2, "", "", args[2]);
		ClassSearchDTO dto = (ClassSearchDTO) searcher.excute();
		List<PatClassInfo> classInfoList = dto.getClassInfoList();

		for (int i = 0; i < 3; i++) {
			System.out.println(classInfoList.get(i).getClassName() + ":"
					+ classInfoList.get(i).getRecordNum());
		}
	}

	public static void main(String[] args) {
		TRSConnection conn = null;
		TRSResultSet rs = null;
		try {
			conn = new TRSConnection();
			conn.connect("192.168.100.12", "8888", "system", "manager");
			rs = conn.executeSelect("fmzl", "权利要求书=计算机", "", "", "", 0,
					TRSConstant.TCE_XML | TRSConstant.TCE_OFFSET, false);
			rs.moveFirst();
			System.out.println(rs.getString("名称"));
			// 获取正文+命中点
//			TRSHitPoint[] aHitPoints = rs.getHitPoints("权利要求书");
//			StringBuffer strHitValues = new StringBuffer(rs.getSummaryStringWithCutsize("权利要求书", 20, "red"));
//			
//			System.out.println(strHitValues.toString());
//
//			// 遍历命中点, 添加标记
//			if (aHitPoints != null) {
//				int iBegin, iEnd, iAddLen, k1, k2;
//				iAddLen = 0;
//
//				// 前后标记
//				String openStr = "<font color=\"#FF0000\">";
//				String closeStr = "</font>";
//				String hitWord = null;
//
//				// 遍历命中位置, 添加反显标记
//				for (int j = 0; j < aHitPoints.length; j++) {
//					// 获取偏移量
//					iBegin = aHitPoints[j].iCStart + iAddLen;
//					iEnd = iBegin + aHitPoints[j].iCLength;
//
//					// 获取命中词
//					 hitWord = strHitValues.substring(iBegin, iEnd);
//					 System.out.println(hitWord);
//
//					// 添加color标记
//					k1 = strHitValues.length();
//					strHitValues.insert(iEnd, closeStr);
//					strHitValues.insert(iBegin, openStr);
//					k2 = strHitValues.length();
//
//					// 增加长度
//					iAddLen += (k2 - k1);
//				}
//
//				// 输出
//				System.out.println(strHitValues.toString());
//			}
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
		} finally {
			if (rs != null)
				rs.close();
			rs = null;
			if (conn != null)
				conn.close();
			conn = null;
		}
	}

	public static void main4(String[] args) {
		AbstractSearcher searcher = new PatOverviewSearcher("FMSX",
				"ss=(本发明提供了一种实现随身电脑与计算机)", "", 2, 0L, 10L, "", "", "");
		OverviewSearchDTO dto = (OverviewSearchDTO) searcher.excute();
		logger.error(dto.getUnfilterTotalCount() + "");
	}

	public static void main3(String[] args) {
		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newCachedThreadPool();
		List<String> l = new ArrayList<String>();
		ArrayList<Future<List<String>>> res = new ArrayList<Future<List<String>>>();
		for (int i = 0; i < Integer.parseInt(args[3]); i++) {// 生成5个线程，每个线程的id分别是1，2，3，4，5
			res.add(exec.submit(new Multi(args[0], args[1], "", 2, "", "",
					args[2], i * 10000, (i + 1) * 10000)));
		}
		int sum = 0;
		for (Future<List<String>> f : res) {
			try {
				List<String> ll = f.get();
				l.addAll(ll);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(l.size());
		Map<Object, Integer> map = new HashMap<Object, Integer>();
		for (Object o : l) {
			map.put(o, map.get(o) == null ? 1 : map.get(o) + 1);
		}
		long end = System.currentTimeMillis();
		System.out.println("PSS-分类统计检索：" + (end - start));
		exec.shutdown();// 关闭线程池
	}

	// public static void main(String[] args) {
	// AbstractSearcher searcher = new PatClassSearcher("flzt", "UUID='%'", "",
	// 2, "", "", "法律状态");
	// ClassSearchDTO dto = (ClassSearchDTO) searcher.excute();
	// List<PatClassInfo> classInfoList = dto.getClassInfoList();
	//
	// // for (PatClassInfo info : classInfoList) {
	// // System.out.println(info.getClassName() + ":" + info.getRecordNum());
	// // }
	// }

	// public static void main1(String[] args) {
	//
	// // int pages = Integer.parseInt(args[0]);
	// for (int j = 0; j < 100; j++) {
	// SemanticSearch search = new SemanticSearch();
	// TRSResultSet trsresultset = null;
	// try {
	// trsresultset = search.GetSimilarPatants("", "FMZL", "申请号=(%)",
	// "", 0, "", TRSConstant.TCM_SORTALWAYS
	// | TRSConstant.TCE_OFFSET, "", false, "", "",
	// "", TRSConstant.TCM_KAXST);
	// logger.error(trsresultset.getRecordCount() + "");
	// // long ii = j*10 + 10;
	// // if (ii > trsresultset.getRecordCount()) {
	// // ii = trsresultset.getRecordCount();
	// // }
	// // for (long i = j*10; i < (j*10 + 10); i++) {
	// // trsresultset.moveTo(0, i);
	// // logger.error(trsresultset.getString("an"));
	// // }
	// } catch (TRSException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// search.cleanTrsResult(trsresultset);
	// }
	// }
	// for (int i = 0; i < 100; i++) {
	// AbstractSearcher searcher = new PatOverviewSearcher(
	// "FMZL,SYXX,WGZL,TWZL,HKPATENT,USPATENT,JPPATENT,GBPATENT,DEPATENT,FRPATENT,EPPATENT,WOPATENT,CHPATENT,KRPATENT,RUPATENT,ASPATENT,GCPATENT,APPATENT,ATPATENT,AUPATENT,CAPATENT,ESPATENT,ITPATENT,SEPATENT,OTHERPATENT",
	// "申请号=(%)", "", 2, 0L, 10L, "", "", "");
	// OverviewSearchDTO dto = (OverviewSearchDTO) searcher.excute();
	// logger.error(dto.getUnfilterTotalCount() + "");
	// }
	// }
}
