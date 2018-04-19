package postgres.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lq.spider.ShenChaExt6;

public class PostgresTest3 {
	private static final Logger logger = LoggerFactory.getLogger(PostgresTest3.class);
	private ShenChaExt6 shenCha = new ShenChaExt6();
	public static void main(String[] args) {
	}

	public void updateJson(long startIndex, long endIndex) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://localhost:5432/postgres";
			Connection con = DriverManager.getConnection(url, "postgres", "postgres");	
			long limit = endIndex - startIndex;
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select sysid,an from patent_after_2007 limit " + limit + " offset " + startIndex);
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
