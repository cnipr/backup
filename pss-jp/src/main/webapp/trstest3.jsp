<%@ page language="java" import="com.trs.ckm.zl.SemanticSearch,com.eprobiti.trs.TRSResultSet,com.eprobiti.trs.TRSException,com.eprobiti.trs.TRSConstant,com.cnipr.pss.rs.dto.OverviewSearchDTO,com.cnipr.pss.rs.client.PatSearchClient" contentType="text/html; charset=utf-8"
    pageEncoding="gbk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>Insert title here</title>
</head>
<body>
<%
    SemanticSearch search = null;
		TRSResultSet rs =null;
			try {
      search = new SemanticSearch();
				rs = search.GetSimilarPatants("",
						"fmzl,syxx,wgzl", "申请日=(20080101 to 20130313) and 地址=(津南)", "and",
						5,
						"+table_sn;+sysid", 627, "申请日=(20080101 to 20130313) and 地址=(津南)", false,
						"", "", "", 0);
				rs
				.setReadOptions(
						0,
						"名称,申请号,申请日,公开（公告）日,公开（公告）号,专利号,申请（专利权）人,发明（设计）人,国省代码,主分类号,分类号,优先权,摘要,主权项,专利代理机构,代理人,地址,专利类型",
						null);
				rs.setBufferSize(2000, 2000);
				
				rs.moveTo(0, 2000);
				int i = 2000;
				do {
					%><%=rs.getString("申请号")%><br/><%
				}while (rs.moveNext() && ++i < 4000);
				
			} catch (TRSException e) {
      	%><%=e.getMessage()%>   <%
				e.printStackTrace();
			} finally {
				search.cleanTrsResult(rs);
			}

%>
</body>
</html>