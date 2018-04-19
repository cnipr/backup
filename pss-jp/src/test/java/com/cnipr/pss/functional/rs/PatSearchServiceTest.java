package com.cnipr.pss.functional.rs;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnipr.pss.rs.client.PatSearchClient;
import com.cnipr.pss.rs.dto.ClassSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;

public class PatSearchServiceTest {
	private static PatSearchClient client;

	@BeforeClass
	public static void setUpClient() throws Exception {
		client = new PatSearchClient();
		client.setBaseUrl("http://127.0.0.1:8080/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://183.62.9.133:8080/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://192.168.3.150:7070/cnipr-pss" + "/rs");
	}
	
//	@Test
//	public void excutePatClassSearch() {
//		ClassSearchDTO dto = client.excutePatClassSearch("fmzl",
//				"an=%", "10000", "2", "", "", "公开（公告）日");
//		System.out.println(dto.getClassInfoList());
////		assertEquals(10, dto.getPatentInfoList().size());
////		assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
//	}
	
//	@Test
//	public void excuteNumbersExtractSearch() {
//		long start = System.currentTimeMillis();
//		PatNumbersExtractSearchDTO dto = client.excuteNumbersExtractSearch("1", "",
//				"syxx,wgzl", "名称=(电动车 or 汽车)", "", "1", "");
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
//		System.out.println("111111111111");
////		System.out.println(dto.getExtractInfoList());
////		assertEquals(1, dto.getReturncode());
//		// assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
//	}
	
//	@Test
//	public void excutePatHotWordsSearch() {
//		HotWordsDTO s = client.excutePatHotWordsSearch("CNVDB1", "专利类型='XX' and 名称='一种'",
//				"10");
//		System.out.println(s.getHotwords());
//	}
	
//	@Test
//	public void excutePatSimilaritySearch() {
//		PatSimilarityDTO s = client.excutePatSimilaritySearch("申请号=(CN98813644.9)", "申请号=（CN98810994.8,CN00118644.2,CN98813644.9,CN95109648.6）",
//				"主权项");
//		System.out.println(s.getSimilaritys().get(1).getSimilarity());
//	}
	
//	@Test
//	public void excutePatFamilySearch() {
//		PatFamilySearchDTO s = client.excutePatFamilySearch("CN201010113183%");
//		System.out.println(s.getFamilies());
//	}
	
//	@Test
//	public void excuteCnReferenceSearch() {
//		CnReferencesDTO dto = client.excuteCnReferenceSearch("CN00100197");
//		System.out.println(dto.getScyyzzlList());
//	}
	
	@Test
	public void excutePatOverviewSearch() {
		OverviewSearchDTO dto = client.excutePatOverviewSearch("fmzl",
				"申请日=(1992 to 2012)", "", "2", "0", "10", "", "", "");
		System.out.println(dto.getPatentInfoList().get(0).getAb());
//		assertEquals(10, dto.getPatentInfoList().size());
//		assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
	}
	
//	@Test
//	public void clusterSearch() {
//		PatClusterSearchDTO dto = client.clusterSearch("CNVDB1", "自动摘要='电气'",
//				"", "", "2", "", "名称	0.3;摘要	0.3;权利要求	0.3;说明书	0.1", "3", "3", "ap,ti");
//		System.out.println(dto.getXml());
//	} 
//	and an=('CN200680033732.4','CN200780022359.7','CN200780030644.3')
	
//	@Test
//	public void excuteSimilarSearch() {
//		OverviewSearchDTO dto = client.excuteSimilarSearch("CNVDB1", "申请号=('CN00200561.1')", "0", "10", "", "", "");
//		for (PatentInfo info : dto.getPatentInfoList()) {
//			System.out.println(info.getAn());
//		}
//		System.out.println(dto.getRecordCount());
//	}
	
//	@Test
//	public void excuteAutoKeyWordsSearch() {
//		String dto = client.excuteAutoKeyWordsSearch("申请（专利权）人='华光海外公司'", "FMZL", "", "");
//		System.out.println(dto);
//	}
	
	/*
	@Test
	public void excuteAutoAbsSearch() {
		String dto = client.excuteAutoAbsSearch("CN03200662.4", "syxx_ft,fmzl_ft,fmsq_ft", "", "", "5", "50");
		System.out.println(dto);
	}
	
	
	
	@Test
	public void excuteAutoTipSearch() {
		String dto = client.excuteAutoTipSearch("计算机");
		System.out.println(dto);
	}
	
	
	
	
	

	
	@Test
	public void excutePatDetailSearch() {
		DetailSearchDTO dto = client.excutePatDetailSearch("fmzl_ft",
				"pnm=CN85100042", "", "1", "0", "");
		assertEquals("CN85100042", dto.getPatentInfo().getPnm());
//		assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
	}
	
	@Test
	public void excutePatXmlDocSearch() {
		PatXmlDocSearchDTO dto = client.excutePatXmlDocSearch("fmsq_ft",
				"公开（公告）号='CN1853211B'", "权利要求书");
		assertEquals(1, dto.getReturncode());
	}
	
	@Test
	public void executePatLegalStatusSearch() {
		LegalStatusSearchDTO dto = client.executePatLegalStatusSearch("法律状态,法律状态信息+=公开",
				"0", "10");
		System.out.println(dto.getLegalStatusInfoList().get(0).getStrStatusInfo());
		assertEquals(1, dto.getReturncode());
	}*/
}
