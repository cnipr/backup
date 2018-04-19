<%@ page language="java" import="com.cnipr.pss.util.Constants,com.cnipr.pss.rs.dto.search.PatentInfo,com.eprobiti.trs.TRSConnection,com.trs.ckm.zl.SemanticSearch,com.eprobiti.trs.TRSResultSet,com.eprobiti.trs.TRSException,com.eprobiti.trs.TRSConstant,com.cnipr.pss.rs.dto.OverviewSearchDTO,com.cnipr.pss.rs.client.PatSearchClient" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	TRSConnection trsConnection = null;
	try {
		trsConnection = new TRSConnection();
		trsConnection.connect("192.168.8.111",
				"8885", "system",
				"manager");
	} catch (TRSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
	TRSResultSet trsresultset =null;
	try {
		Long s1=System.currentTimeMillis();
		trsresultset = trsConnection.executeSelect("FMZL,SYXX,WGZL,TWZL,HKPATENT,USPATENT,JPPATENT,GBPATENT,DEPATENT,FRPATENT,EPPATENT,WOPATENT,CHPATENT,KRPATENT,RUPATENT,ASPATENT,GCPATENT,APPATENT,ATPATENT,AUPATENT,CAPATENT,ESPATENT,ITPATENT,SEPATENT,OTHERPATENT", "申请号=(%)",
				"", "", "", 2, 115,
				false);
		PatentInfo patentInfo = new PatentInfo();
		trsresultset.moveTo(0, 0);
			patentInfo.setSysid(trsresultset.getString("sysid"));
			String patentName = trsresultset.getString(
					Constants.MING_CHENG, "red");
			patentName = patentName.replace("\"", "");
			patentInfo.setTi(patentName);
			patentInfo.setAn(trsresultset
					.getString(Constants.SHEN_QING_HAO));
			patentInfo.setAd(trsresultset
					.getString(Constants.SHENG_QING_RI, "red"));
			patentInfo.setPd(trsresultset
					.getString(Constants.GONG_KAI_RI, "red"));
			patentInfo.setPnm(trsresultset
					.getString(Constants.GONG_KAI_HAO));
			patentInfo.setPn(trsresultset
					.getString(Constants.ZHUAN_LI_HAO));
			patentInfo.setPa(trsresultset
					.getString(Constants.SHEN_QING_REN, "red"));
			patentInfo.setPin(trsresultset
					.getString(Constants.FA_MING_REN, "red"));
//			patentInfo.setCo(trsresultset
//					.getString(Constants.GUO_SHENG_DAI_M));
			patentInfo.setSic(trsresultset
					.getString(Constants.FEN_LEI_HAO));
			patentInfo.setPic(trsresultset
					.getString(Constants.ZHU_FEN_LEI_H));
			patentInfo.setPr(trsresultset
					.getString(Constants.YOU_XIAN_QUAN));
			patentInfo.setAb(trsresultset.getString(Constants.ZHAI_YAO,
					"red"));
			Long s2=System.currentTimeMillis();
			%>耗时：<%=(s1-s2) %><br/><%
	} catch (TRSException e) {
		// TODO Auto-generated catch block
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
%>
</body>
</html>