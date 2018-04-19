package com.cnipr.pss.search;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cnipr.pss.rs.client.CorpCodeClient;
import com.cnipr.pss.rs.dto.CorpTreeInfoDTO;

public class CorpCodeServiceTest {

	private static CorpCodeClient client;

	@Before
	public void setUp() {
		try {
			client = new CorpCodeClient();
			client.setBaseUrl("http://localhost:2000/cnipr-pss" + "/rs");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {

	}

	@Test
	public void searchHasNoCode() {
		CorpTreeInfoDTO corpTreeInfo = client.searchHasNoCode("联想", "0", "10");
		System.out.println(corpTreeInfo.getCorpTotal());
	}

	@Test
	public void searchCorpByCode() {
		CorpTreeInfoDTO corpTreeInfo = client.searchCorpByCode("联想", "0", "10");
		System.out.println(corpTreeInfo.getCorpTotal());
	}

	@Test
	public void searchTopCode() {
		CorpTreeInfoDTO corpTreeInfo = client.searchTopCode("联想", "0", "10");
		System.out.println(corpTreeInfo.getCorpCodeTotal());
	}

	@Test
	public void searchCorpCode() {
		List<CorpTreeInfoDTO> corpTreeInfos = client.searchCorpCode("冠群");
		System.out.println(corpTreeInfos.size());
	}
}
