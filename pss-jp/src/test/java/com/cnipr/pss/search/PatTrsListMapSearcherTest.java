package com.cnipr.pss.search;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.impl.TrsListMapSearcher2;

import mysql.jdbc.MysqlTest;

//import postgres.jdbc.PostgresTest;

public class PatTrsListMapSearcherTest extends Thread{
	
	private String threadName;
	private Long from;
	private Long to;
	private int batchSize;
	private MysqlTest mysql = new MysqlTest();
	
	@Override
	public void run() {
//		System.out.println(new Date() + "["+ threadName +"]START:" + from);
		long batchNum = (to - from + batchSize - 1)/batchSize;
		for (int i = 0; i < batchNum; i++) {
			long startIndex = from + batchSize*i;
			long endIndex = startIndex + batchSize;
			if (endIndex > to) {
				endIndex = to;
			}
			long start = System.currentTimeMillis();
			
			
//			TrsListMapSearchDTO dto = getFmzlData(startIndex, endIndex);//获取FMZL数据
//			postgres.insertFmzl(dto); //插入FMZL数据
			
//			TrsListMapSearchDTO dto = getPatentCnData(startIndex, endIndex);//获取patent_cn数据
//			postgres.insertPatentCn2(dto); //插入patent_cn数据
			
//			TrsListMapSearchDTO dto = getPatentAfter2007(startIndex, endIndex);//获取patent_after_2007数据
			TrsListMapSearchDTO dto = getPatentBefore2007(startIndex, endIndex);//获取patent_before_2007数据
//			mysql.insertPatentAfter2007(dto); //插入patent_after_2007数据
			mysql.insertPatentBefore2007(dto); //插入patent_before_2007数据
//			TrsListMapSearchDTO dto = getDetail(startIndex, endIndex);//获取detail数据
//			postgres.updateDetail(dto); //更新全文数据
			
			//获取shoufee数据
//			TrsListMapSearchDTO dto = getShoufeeData(startIndex, endIndex);
			
			//打印数据
//			print(dto);
			
			//插入收费数据
//			postgres.insertShoufee(dto); 
			
//			TrsListMapSearchDTO dto = getFlztData(startIndex, endIndex);//获取flzt数据			
//			postgres.insertFlzt(dto); //插入flzt数据
			
//			TrsListMapSearchDTO dto = getTestarrayData(startIndex, endIndex);//获取testArray数据			
//			postgres.insertTestarray(dto); //插入testArray数据
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "["+ threadName + "]" + startIndex + "-" + endIndex + "耗时:" + consume); 
		}
//		System.out.println(new Date() + "["+ threadName +"]END" + to);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int threadNums = 10;
		int first = 0;
//		int last = 319,5760;322,1667;1698,3244;1701,2149;232,5195
		int last = 233 * 10000;	
//		last = 10;
		for (int i = 0; i < threadNums; i++) {
			String threadName = "Thread-" + (i+1);
			long from = first + (last - first)/threadNums * i;
			long to = first + (last - first)/threadNums * (i+1);
			System.out.println(threadName + "," + from + "," + to);
			PatTrsListMapSearcherTest thread = new PatTrsListMapSearcherTest(threadName,from,to,1000);
			thread.start();
		}
		
//		new PostgresTest().getAll();
	}
	
	private TrsListMapSearchDTO getFmzlData(long startIndex, long endIndex) {
		String[] columns = ("SYSID,APP_ID,PUB_ID,申请号,申请日,公开（公告）号,公开（公告）日,专利号,专利类型,REPRESENTATIVE,名称,摘要,"
				+ "说明书附图,分类号,主分类号,欧洲分类号,欧洲主分类号,本国分类号,本国主分类号,申请（专利权）人,发明（设计）人,专利代理机构,代理人,地址,"
				+ "申请国代码,国省代码,省,市,区,申请来源,国际申请,国际公布,进入国家日期,优先权,优先权号,优先权日,同族专利项,参考文献,分案原申请号,审查员,"
				+ "颁证日,页数,发布路径,缩略图发布路径,摘要附图存储路径,公报发布路径,公报所在页,公报翻页信息,关键词,自动摘要,公开年,TABLE_SN,VERSION,"
				+ "主权项,专利权状态,专利权状态代码,法律状态,范畴分类,族号,旧申请号,旧分类号,旧申请（专利权）人,旧发明（设计）人,引证文献,旧优先权,"
				+ "同日申请,PDF地址,复审类型,名称关键词,独权关键词,背景关键词").split(",");
		String[] columns2 = ("权利要求书,说明书").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL", "申请号=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getPatentCnData(long startIndex, long endIndex) {
		String[] columns = ("SYSID,APP_ID,PUB_ID,申请号,申请日,公开（公告）号,公开（公告）日,专利号,专利类型,REPRESENTATIVE,名称,摘要,"
				+ "说明书附图,分类号,主分类号,欧洲分类号,欧洲主分类号,本国分类号,本国主分类号,申请（专利权）人,发明（设计）人,专利代理机构,代理人,地址,"
				+ "申请国代码,国省代码,省,市,区,申请来源,国际申请,国际公布,进入国家日期,优先权,优先权号,优先权日,同族专利项,参考文献,分案原申请号,审查员,"
				+ "颁证日,页数,发布路径,缩略图发布路径,摘要附图存储路径,公报发布路径,公报所在页,公报翻页信息,关键词,自动摘要,公开年,TABLE_SN,VERSION,"
				+ "主权项,专利权状态,专利权状态代码,法律状态,范畴分类,族号,旧申请号,旧分类号,旧申请（专利权）人,旧发明（设计）人,引证文献,旧优先权,"
				+ "同日申请,PDF地址,复审类型,名称关键词,独权关键词,背景关键词").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL,FMSQ,SYXX,WGZL", "申请号=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getPatentAfter2007(long startIndex, long endIndex) {
		String[] columns = ("SYSID,申请号,申请日,公开（公告）号,公开（公告）日").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL,SYXX,WGZL", "公开（公告）日>='2007.01.01'",
				"+公开（公告）日", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getPatentBefore2007(long startIndex, long endIndex) {
		String[] columns = ("SYSID,申请号,申请日,公开（公告）号,公开（公告）日").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL,SYXX,WGZL", "公开（公告）日<'2007.01.01'",
				"+公开（公告）日", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getDetail(long startIndex, long endIndex) {
		String[] columns = {"SYSID"};
		String[] columns2 = ("权利要求书,说明书").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("FMZL,FMSQ,SYXX,WGZL", "申请号=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getShoufeeData(long startIndex, long endIndex) {
		String[] columns = ("Fee,FeeType,Receiption,RegisterCode,ApplyNum,HKInfo,State,HKDate,ReceiptionDate,ApplyNum_new,EN_FeeType,EN_State").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("shoufee", "ApplyNum=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getFlztData(long startIndex, long endIndex) {
		String[] columns = ("UUID,申请号,法律状态公告日,法律状态,APP_ID,VERSION,prs_year,法律状态信息").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("flzt", "申请号=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private TrsListMapSearchDTO getTestarrayData(long startIndex, long endIndex) {
		String[] columns = ("SYSID,申请号,名称,申请（专利权）人,发明（设计）人").split(",");
		String[] columns2 = ("").split(",");
		AbstractSearcher test2 = new TrsListMapSearcher2("fmzl", "申请号=%",
				"RELEVANCE", 2, "21", 0, columns , columns2,startIndex,endIndex);
		TrsListMapSearchDTO dto = (TrsListMapSearchDTO)test2.excute();
		return dto;
	}
	
	private void print(TrsListMapSearchDTO dto) {
		List<Map<String, String>> list = dto.getResultList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
//			System.out.println(map.get("PUB_ID"));
			System.out.println(map.get("Receiption"));
		}
	}

	public PatTrsListMapSearcherTest() {
		super();
	}

	public PatTrsListMapSearcherTest(String threadName, Long from, Long to, int batchSize) {
		super();
		this.threadName = threadName;
		this.from = from;
		this.to = to;
		this.batchSize = batchSize;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	
	
}
