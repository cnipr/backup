package com.cnipr.pss.rs.client;

import java.io.InputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Required;

import com.cnipr.pss.rs.dto.RexamDTO;
import com.cnipr.pss.rs.dto.RexamFSJDDTO;
import com.cnipr.pss.rs.dto.RexamFYPJDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RexamClient {
	private WebResource client;

	@Required
	public void setBaseUrl(String baseUrl) {
		Client jerseyClient = Client.create(new DefaultClientConfig());
		client = jerseyClient.resource(baseUrl);
	}

	public RexamDTO searchRexamsByAppNum(String appNum) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		return client.path("/rexam/paramAppNum")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.accept(MediaType.APPLICATION_JSON)
				.post(new GenericType<RexamDTO>() {
				}, params);
	}
	public RexamDTO queryRexamKindsByAppNum(String appNum) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		return client.path("/rexam/queryKindsByAppNum")
		.type(MediaType.APPLICATION_FORM_URLENCODED)
		.accept(MediaType.APPLICATION_JSON)
		.post(new GenericType<RexamDTO>() {
		}, params);
	}
	
	public RexamDTO queryRexamsByAppNumKind(String appNum,String kind) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		params.add("kind", kind);
		return client.path("/rexam/queryRexamsByAppNumKind")
		.type(MediaType.APPLICATION_FORM_URLENCODED)
		.accept(MediaType.APPLICATION_JSON)
		.post(new GenericType<RexamDTO>() {
		}, params);
	}
	
	/**
	 * 获取专利法院判决的详细信息
	 * @param appNum 申请号
	 * @param judgementalDate 审判日期
	 * @return
	 */
	public RexamFYPJDTO queryPatFYPJDetail(String appNum,String judgementalDate) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		params.add("judgementalDate", judgementalDate);
		return client.path("/rexam/queryPatFYPJDetail")
		.type(MediaType.APPLICATION_FORM_URLENCODED)
		.accept(MediaType.APPLICATION_JSON)
		.post(new GenericType<RexamFYPJDTO>() {
		}, params);
	}
	/**
	 * 获取复审决定的详细信息
	 * @param appNum 申请号
	 * @param decisionNumber 决定号
	 * @return
	 */
	
	public RexamFSJDDTO queryPatFSJDDetail(String appNum,String decisionNumber) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
		params.add("appNum", appNum);
		params.add("decisionNumber", decisionNumber);
		return client.path("/rexam/queryPatFSJDDetail")
		.type(MediaType.APPLICATION_FORM_URLENCODED)
		.accept(MediaType.APPLICATION_JSON)
		.post(new GenericType<RexamFSJDDTO>() {
		}, params);
	}
}
