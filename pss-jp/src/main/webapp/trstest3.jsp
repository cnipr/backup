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
						"fmzl,syxx,wgzl", "������=(20080101 to 20130313) and ��ַ=(����)", "and",
						5,
						"+table_sn;+sysid", 627, "������=(20080101 to 20130313) and ��ַ=(����)", false,
						"", "", "", 0);
				rs
				.setReadOptions(
						0,
						"����,�����,������,���������棩��,���������棩��,ר����,���루ר��Ȩ����,��������ƣ���,��ʡ����,�������,�����,����Ȩ,ժҪ,��Ȩ��,ר���������,������,��ַ,ר������",
						null);
				rs.setBufferSize(2000, 2000);
				
				rs.moveTo(0, 2000);
				int i = 2000;
				do {
					%><%=rs.getString("�����")%><br/><%
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