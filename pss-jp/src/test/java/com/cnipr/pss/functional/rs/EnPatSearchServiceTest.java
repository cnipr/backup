package com.cnipr.pss.functional.rs;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cnipr.pss.rs.client.EnPatSearchClient;
import com.cnipr.pss.rs.dto.DetailSearchDTO;
import com.cnipr.pss.rs.dto.LegalStatusSearchDTO;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.cnipr.pss.rs.dto.PatNumbersExtractSearchDTO;
import com.cnipr.pss.rs.dto.PatXmlDocSearchDTO;

public class EnPatSearchServiceTest {
	private static EnPatSearchClient client;

	@BeforeClass
	public static void setUpClient() throws Exception {
		client = new EnPatSearchClient();
		client.setBaseUrl("http://127.0.0.1:8080/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://59.151.93.249/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://192.168.3.180:8080/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://192.168.3.150:7072/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://192.168.3.233:7070/cnipr-pss" + "/rs");
	}
	
//	@Test
//	public void excutePatOverviewSearch() {
//		OverviewSearchDTO dto = client.excutePatOverviewSearch("CNFMZL_EN,USPATENT",
//				"TI=('Method','medical') and PNM=('CN102439026A','US2012069024(A1)') and AN=(CN200980117402.7,US88346410A) and AD=('2009.03.03','2010.09.16') and PD=('2012.05.02','2012.03.22') and SIC=('G06T11/20%','C07J41/00%') and PIC=('C07J41/00%','G06T11/20%') and PA=('A.R. shed Rall','PALO ALTO RES CT INC') and INN=('SAUND ERIC','A.R. shed Rall') and ABST=('serious','recognition')", "", "1", "0", "10", "", "", "");
//		assertEquals(2, dto.getPatentInfoList().size());
//	}
	
//	@Test
//	public void excuteNumbersExtractSearch() {
//		PatNumbersExtractSearchDTO dto = client.excuteNumbersExtractSearch("2", "D17692AECF304D464112EB1DF8646D01",
//				"fmzl_ft", "pnm=CN85100044", "", "1", "");
//		assertEquals(1, dto.getReturncode());
//		// assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
//	}
	
	@Test
	public void excutePatDetailSearch() {
		DetailSearchDTO dto = client.excutePatDetailSearch("USPATENT",//,USPATENT
				"TI=('Method','medical') and PNM=('CN102439026A','US2012069024(A1)') and AN=(CN200980117402.7,US88346410A) and AD=('2009.03.03','2010.09.16') and PD=('2012.05.02','2012.03.22') and SIC=('G06T11/20%','C07J41/00%') and PIC=('C07J41/00%','G06T11/20%') and PA=('A.R. shed Rall','PALO ALTO RES CT INC') and INN=('SAUND ERIC','A.R. shed Rall') and ABST=('serious','recognition')", "", "1", "0", "");
		assertEquals("CN102439026A", dto.getPatentInfo().getPnm());
//		assertEquals("计算机 计算机网 计算机系 计算机化 计算机房 计算机所 计算机业 非计算机 计算机界 计算机配 ",dto.getRelevance());
	}
//	
//	@Test
//	public void excutePatXmlDocSearch() {
//		PatXmlDocSearchDTO dto = client.excutePatXmlDocSearch("fmsq_ft",
//				"公开（公告）号='CN1853211B'", "权利要求书");
//		assertEquals(1, dto.getReturncode());
//	}
	
//	@Test
//	public void executePatLegalStatusSearch() {
//		LegalStatusSearchDTO dto = client.executePatLegalStatusSearch("Legal_Status,Legal_Status_Info+=transfer",
//				"0", "10");
//		System.out.println(dto.getLegalStatusInfoList().get(0).getStrStatusInfo());
//		assertEquals(1, dto.getReturncode());
//	}
}
