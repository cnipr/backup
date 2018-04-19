package postgres.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBatch {
	private static final Logger logger = LoggerFactory.getLogger(TestBatch.class);
	
	public static void main(String[] args) {
		TestBatch test = new TestBatch();
		test.testBatch();
	}
	
	public void testBatch() {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into test_batch(uuid,id) values (?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 3;
			int count = 0;
			
			String[] ids = {"01","02","03","004","05","006","07","08","09"};
			for (int i = 0; i < ids.length; i++) {
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, ids[i]);
				ps.addBatch();
				if(++count % batchSize == 0) {
			        try {
						ps.executeBatch();
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			}
//			ps.executeBatch();	// insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + ids.length + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}

}
