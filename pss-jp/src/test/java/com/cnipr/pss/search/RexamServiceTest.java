package com.cnipr.pss.search;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cnipr.pss.rs.client.RexamClient;
import com.cnipr.pss.rs.dto.RexamDTO;
import com.cnipr.pss.rs.dto.RexamFSJDDTO;
import com.cnipr.pss.rs.dto.RexamFYPJDTO;

public class RexamServiceTest {

	private static RexamClient client;

	@Before
	public void setUp() {
		try {
			client = new RexamClient();
			client.setBaseUrl("http://localhost/cnipr-pss" + "/rs");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		
	}

//	@Test
	public void searchRexamsByAppNum() {
		RexamDTO rexamDTO = client.searchRexamsByAppNum("00100881.1");
		System.out.println(rexamDTO.getTotalCount());
	}
	
//	@Test
	public void queryKindsByAppNum() {
		RexamDTO rexamDTO = client.queryRexamKindsByAppNum("00100881.1");
		System.out.println(rexamDTO.getTotalCount());
	}
	
//	@Test
	public void queryRexamsByAppNumKind() {
		RexamDTO rexamDTO = client.queryRexamsByAppNumKind("00100881.1","FYPJ");
		System.out.println(rexamDTO.getTotalCount());
	}
//	@Test
	public void queryPatFYPJDetail() {
		long start = System.currentTimeMillis();
		RexamFYPJDTO rexamDTO = client.queryPatFYPJDetail("00100881.1","20051201");
		System.out.println("耗时："+(System.nanoTime()-start));
		System.out.println(rexamDTO.getTotalCount());
	}
//	@Test
	public void queryPatFSJDDetail1() {
		long start = System.currentTimeMillis();
		RexamFSJDDTO rexamDTO = client.queryPatFSJDDetail("00100881.1","5680");
//		System.out.println("耗时："+(System.currentTimeMillis()-start));
		System.out.println(rexamDTO.getTotalCount());
	}
	
//	@Test
	public void queryPatFSJDDetail2() {
		long start = System.currentTimeMillis();
		RexamFSJDDTO rexamDTO = client.queryPatFSJDDetail("99124617.9","7534");
		System.out.println("耗时："+(System.currentTimeMillis()-start));
		System.out.println(rexamDTO.getTotalCount());
	}
}
