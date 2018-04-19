package com.cnipr.pss.tools;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.cnipr.pss.rs.client.PatSearchClient;
import com.cnipr.pss.rs.dto.OverviewSearchDTO;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Main {
	private static PatSearchClient client;
	
//	public static void setUpClient() throws Exception {
//		client = new PatSearchClient();
////		client.setBaseUrl("http://192.168.3.144:8080/cnipr-pss" + "/rs");
//		client.setBaseUrl("http://192.168.201.79:8082/cnipr-pss" + "/rs");
////		client.setBaseUrl("http://192.168.3.150:7070/cnipr-pss" + "/rs");
//	}
	
    
    public static void main(String[] args) throws IOException {
    	client = new PatSearchClient();
//		client.setBaseUrl("http://192.168.3.144:8080/cnipr-pss" + "/rs");
		client.setBaseUrl("http://192.168.201.79:8082/cnipr-pss" + "/rs");
    	for(int i = 0; i < 100; i++) {
    		OverviewSearchDTO dto = client.excutePatOverviewSearch("FMZL,SYXX,WGZL,TWZL,HKPATENT,USPATENT,JPPATENT,GBPATENT,DEPATENT,FRPATENT,EPPATENT,WOPATENT,CHPATENT,KRPATENT,RUPATENT,ASPATENT,GCPATENT,APPATENT,ATPATENT,AUPATENT,CAPATENT,ESPATENT,ITPATENT,SEPATENT,OTHERPATENT",
    				"申请号=(%)", "", "2", "0", "10", "", "", "");
    		System.out.println(dto.getRecordCount());
    	}
    	
//        final String baseUri = "http://localhost:9998/";
//        final Map<String, String> initParams = new HashMap<String, String>();
//
//        initParams.put("com.sun.jersey.config.property.packages", 
//                "com.sun.jersey.samples.helloworld.resources");
//
//
//        System.out.println("Starting grizzly...");
//        SelectorThread threadSelector = GrizzlyWebContainerFactory.create(
//                baseUri, initParams);
//        System.out.println(String.format(
//                "Jersey app started with WADL available at %sapplication.wadl\n" + 
//                "Try out %shelloworld\nHit enter to stop it...", baseUri, baseUri));
//        System.in.read();
//        threadSelector.stopEndpoint();
//        System.exit(0);
    }    
}
