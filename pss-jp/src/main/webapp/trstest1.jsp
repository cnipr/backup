<%@ page language="java" import="com.cnipr.pss.rs.dto.search.PatentInfo,java.util.List,com.cnipr.pss.search.impl.PatBatchDownloadSearcher,com.cnipr.pss.search.AbstractSearcher,com.cnipr.pss.rs.dto.OverviewSearchDTO,com.cnipr.pss.rs.client.PatSearchClient" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%

	for(int i = 0; i < 1; i++) {
		AbstractSearcher searcher = new PatBatchDownloadSearcher("fmzl,syxx,wgzl",
				"申请日=(20080101 to 20130313) and 地址=(津南)", "", 2, 2000L, 4000L, "", "", "");
		OverviewSearchDTO dto =  (OverviewSearchDTO) searcher.excute();
		List<PatentInfo> list = dto.getPatentInfoList();
		for (PatentInfo pat : list) {
			
		
		%><%=pat.getAn()%><br/><%
		}
}
%>
</body>
</html>