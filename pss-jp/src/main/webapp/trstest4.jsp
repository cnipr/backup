<%@ page language="java" import="com.trs.ckm.zl.SemanticSearch,com.eprobiti.trs.TRSResultSet,com.eprobiti.trs.TRSException,com.eprobiti.trs.TRSConstant,com.cnipr.pss.rs.dto.OverviewSearchDTO,com.cnipr.pss.rs.client.PatSearchClient" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
for (int j=0; j <1; j++) {
	SemanticSearch search = new SemanticSearch();
	TRSResultSet trsresultset =null;
	try {
		trsresultset = search.GetSimilarPatants("",
				"FMZL", "申请日=(1992 to 2012) and 公开（公告）日=(1992 to 2012.01.31) and 分类号=(b44c3\\/02% or b32b%) and (名称=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 摘要=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 权利要求书=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板))", "and",
				10,
				"RELEVANCE", TRSConstant.TCM_SORTALWAYS
						| TRSConstant.TCE_OFFSET, "(申请日=(1992 to 2012) and 公开（公告）日=(1992 to 2012.01.31) and 分类号=(b44c3\\/02% or b32b%) and (名称=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 摘要=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 权利要求书=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板)))", false,
				"", "", "", 0);
		for (int i = 0; i < 779; i++) {
			
			trsresultset.moveTo(0, i);
			%><%=trsresultset.getString("名称") %>###<%=trsresultset.getString("申请号") %><br/><%
		}

		
	} catch (TRSException e) {
		// TODO Auto-generated catch block
		%><%=e.getMessage()%><br/><%
		e.printStackTrace();
	} finally {
		search.cleanTrsResult(trsresultset);
	}
}
%>
---------------------------------------------------------------------<br/>

</body>
</html>