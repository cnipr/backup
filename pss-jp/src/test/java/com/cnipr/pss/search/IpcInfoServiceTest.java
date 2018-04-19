package com.cnipr.pss.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cnipr.pss.rs.client.IpcInfoClient;
import com.cnipr.pss.rs.dto.IpcInfoDTO;

public class IpcInfoServiceTest {

	private static IpcInfoClient client;

	@Before
	public void setUp() {
		try {
			client = new IpcInfoClient();
			client.setBaseUrl("http://localhost/cnipr-pss" + "/rs");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {

	}

	@Test
	public void excuteIpcDefaultSearch() {
		IpcInfoDTO ipcInfo = client.excuteIpcDefaultSearch();
		System.out.println(ipcInfo.getChildIpcs().size());
	}

	@Test
	public void excuteIpcParamSearch() {
		IpcInfoDTO ipcInfo = client.excuteIpcParamSearch("1", "A", "0", "10");
		System.out.println(ipcInfo.getChildIpcs().size());
	}

	@Test
	public void excuteIpcPathSearch() {
		IpcInfoDTO ipcInfo = client.excuteIpcPathSearch("G02");
		System.out.println(ipcInfo.getNote());
	}

	@Test
	public void excuteIpcDetailSearch() {
		IpcInfoDTO ipcInfo = client.excuteIpcDetailSearch("G02");
		System.out.println(ipcInfo.getChildIpcs().size());
	}
}
