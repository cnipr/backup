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
		for (int j = 0; j < 1671; j++) {
			// 只看第21 和 1403页专利
			if (j != 20 && j != 1402) {
				continue;
			}
			TRSConnection trsConnection = null;
			try {
				trsConnection = new TRSConnection();
				trsConnection.connect("192.168.8.3", "8885", "system",
						"manager");
			} catch (TRSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TRSResultSet trsresultset = null;
			try {
				trsresultset = trsConnection
						.executeSelect("FMZL,SYXX,WGZL", "申请（专利权）人=(飞利浦)",
								"+table_sn;+sysid", "", "", 2,
								TRSConstant.TCM_SORTALWAYS
										| TRSConstant.TCE_OFFSET, false);
				trsresultset.setBufferSize(10, 10);
				trsresultset.setReadOptions(0, "申请号", null);
	%><%=j + 1%>------------
	<br />
	<%
		for (int i = j * 10; i < (j + 1) * 10; i++) {
					trsresultset.moveTo(0, i);
	%><%=trsresultset.getString("申请号")%><br />
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