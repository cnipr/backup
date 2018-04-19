<%@ page language="java"
	import="com.eprobiti.trs.TRSConnection,com.trs.ckm.zl.SemanticSearch,com.eprobiti.trs.TRSResultSet,com.eprobiti.trs.TRSException,com.eprobiti.trs.TRSConstant,com.cnipr.pss.rs.dto.OverviewSearchDTO,com.cnipr.pss.rs.client.PatSearchClient"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<%
		for (int j = 0; j < 78; j++) {
			TRSConnection trsConnection = null;
			try {
				trsConnection = new TRSConnection();
				trsConnection.connect("192.168.8.111", "8885", "system",
						"manager");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TRSResultSet trsresultset = null;
			try {//RELEVANCE
				trsresultset = trsConnection
						.executeSelect(
								"FMZL",
								"申请日=(1992 to 2012) and 公开（公告）日=(1992 to 2012.01.31) and 分类号=(b44c3\\/02% or b32b%) and (名称=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 摘要=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板) or 权利要求书=(单板 or 贴面 or 木皮 or 饰面 or 胶合板 or 贴面板 or 饰面板))",
								"RELEVANCE", "", "", 2, TRSConstant.TCM_SORTALWAYS
										| TRSConstant.TCE_OFFSET, false);
				trsresultset.setBufferSize(10, 10);
	%><%=j + 1%>------------
	<br />
	<%
		for (int i = j * 10; i < (j + 1) * 10; i++) {
					if (i > trsresultset.getRecordCount()) {
						break;
					}

					trsresultset.moveTo(0, i);
	%><%=i%>###<%=trsresultset.getString("名称")%>###<%=trsresultset.getString("申请号")%><br />
	<%
		}

			} catch (TRSException e) {
				// TODO Auto-generated catch block
	%><%=e.getMessage()%><br />
	<%
		e.printStackTrace();
			} finally {
				if (trsresultset != null) {
					try {
						trsresultset.close();
					} catch (Exception exception) {
					}
				}

				if (trsConnection != null) {
					try {
						trsConnection.close();
					} catch (Exception exception) {
					}
				}
			}
		}
	%>
	---------------------------------------------------------------------
	<br />

</body>
</html>