package mysql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.lq.spider.ShenChaExt6;

public class MysqlTest {
	private static final Logger logger = LoggerFactory.getLogger(MysqlTest.class);
	private ShenChaExt6 shenCha = new ShenChaExt6();
	public static void main(String[] args) {
		new MysqlTest().getAll();
	}
	
	public void getAll() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1/data?useUnicode=true&characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(url, "root", "root");
			Statement st = con.createStatement();
			String sql = " select * from db1 ";
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
			}
			rs.close();
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}
	
	public void insertPatentAfter2007(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1/data?useUnicode=true&characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(url, "root", "root");	
			
			String sql = "insert into patent_after_2007(sysid,an,ad,pn,pd,db) values (?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 1000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1 , map.get("SYSID"));
				ps.setString(2 , map.get("申请号"));
				ps.setString(3 , map.get("申请日"));
				ps.setString(4 , map.get("公开（公告）号"));
				ps.setString(5 , map.get("公开（公告）日"));
				ps.setString(6, map.get("patent_db"));
				
				ps.addBatch();
				if(++count % batchSize == 0) {
					ps.executeBatch();
			    }
			}
			ps.executeBatch();  // insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
			logger.error("exception:" + e.getMessage());
		}
	}
	
	public void insertPatentBefore2007(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1/data?useUnicode=true&characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(url, "root", "root");	
			
			String sql = "insert into patent_before_2007(sysid,an,ad,pn,pd,db) values (?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 1000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1 , map.get("SYSID"));
				ps.setString(2 , map.get("申请号"));
				ps.setString(3 , map.get("申请日"));
				ps.setString(4 , map.get("公开（公告）号"));
				ps.setString(5 , map.get("公开（公告）日"));
				ps.setString(6, map.get("patent_db"));
				
				ps.addBatch();
				if(++count % batchSize == 0) {
					ps.executeBatch();
			    }
			}
			ps.executeBatch();  // insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
			logger.error("exception:" + e.getMessage());
		}
	}

	public void updateJson(long startIndex, long endIndex) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://127.0.0.1/data?useUnicode=true&characterEncoding=utf-8";
			Connection con = DriverManager.getConnection(url, "root", "root");	
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select sysid,an from patent_after_2007 limit " + startIndex + "," + endIndex);
			PreparedStatement ps = con.prepareStatement("update patent_after_2007 set shencha = ? where sysid = ?");
			final int batchSize = 1000;
			int count = 0;
			while (rs.next()) {
				String sysid = rs.getString(1);
				String an = rs.getString(2);
				ps.setString(1 , an + shenCha.extract(an));
				ps.setString(2 , sysid);
				ps.addBatch();
				if(++count % batchSize == 0) {
					ps.executeBatch();
			    }
			}
			ps.executeBatch();  // insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[update]" + count + "耗时:" + consume); 
			rs.close();
			st.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
			logger.error("exception:" + e.getMessage());
		}
	}
}
