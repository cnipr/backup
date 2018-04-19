package com.cnipr.pss.search;

import com.trs.client.TRSConnection;
import com.trs.client.TRSException;
import com.trs.client.TRSResultSet;

public class TrsCommonSearchTest {
	public static void main(String[] args) {
		TRSConnection conn = null;
		TRSResultSet rs = null;
		try {
			conn = new TRSConnection();
			conn.connect("192.168.8.2", "8885", "system", "manager");
			rs = conn.executeSelect("fmzl,syxx,wgzl", "申请日=(20080101 to 20130313) and 地址=(津南)",
					"", "", "", 2, 115,
					false);
			rs.moveTo(0, 1815);
			System.out.println(rs.getString("名称") + "," + rs.getString("申请号"));
			rs.moveTo(0, 2000);
			System.out.println(rs.getString("名称") + "," + rs.getString("申请号"));
		} catch (TRSException e) {
			System.out.println("ErrorCode: " + e.getErrorCode());
			System.out.println("ErrorString: " + e.getErrorString());
		} finally {
			if (rs != null)
				rs.close();
			rs = null;
			if (conn != null)
				conn.close();
			conn = null;
		}
		
		
		for (int i=0; i < 2; i++) {
			try {
				conn = new TRSConnection();
				conn.connect("192.168.8.2", "8885", "system", "manager");
				rs = conn.executeSelect("fmzl,syxx,wgzl", "申请日=(20080101 to 20130313) and 地址=(津南)",
						"", "", "", 2, 115,
						false);
				if (i == 0) {
					rs.moveTo(0, 1815);
				} else {
					rs.moveTo(0, 2000);
				}
				System.out.println(rs.getString("名称") + "," + rs.getString("申请号"));
			} catch (TRSException e) {
				System.out.println("ErrorCode: " + e.getErrorCode());
				System.out.println("ErrorString: " + e.getErrorString());
			} finally {
				if (rs != null)
					rs.close();
				rs = null;
				if (conn != null)
					conn.close();
				conn = null;
			}
		}
		
	}
}
