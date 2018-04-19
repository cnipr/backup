package postgres.jdbc;

import java.sql.Array;
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
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cnipr.pss.rs.dto.TrsListMapSearchDTO;
import com.cnipr.pss.search.impl.PatOverviewSearcher;

public class PostgresTest {
	private static final Logger logger = LoggerFactory.getLogger(PostgresTest.class);
//	private static Connection con;
	public static void main(String[] args) {
		new PostgresTest().getAll();
	}

	public void getAll() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");
			Statement st = con.createStatement();
			String sql = " select * from user11 ";
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
	
	public void insertShoufee(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into shoufee(sysid,Fee,FeeType,Receiption,RegisterCode,ApplyNum,HKInfo,State,HKDate,ReceiptionDate,ApplyNum_new,EN_FeeType,EN_State) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 10000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1, UUID.randomUUID().toString());
				ps.setInt(2, Integer.parseInt(map.get("Fee")));
				ps.setString(3, map.get("FeeType"));
				ps.setString(4, map.get("Receiption"));
				ps.setString(5, map.get("RegisterCode"));
				ps.setString(6, map.get("ApplyNum"));
				ps.setString(7, map.get("HKInfo"));
				ps.setString(8, map.get("State"));
				ps.setString(9, map.get("HKDate"));
				ps.setString(10, map.get("ReceiptionDate"));
				ps.setString(11, map.get("ApplyNum_new"));
				ps.setString(12, map.get("EN_FeeType"));
				ps.setString(13, map.get("EN_State"));
				ps.addBatch();
				if(++count % batchSize == 0) {
			        ps.executeBatch();
			    }
			}
			ps.executeBatch();	// insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}
	
	public void updateDetail(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "update patent_cn set claim = ?,description = ? where sysid = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1, map.get("权利要求书"));
				ps.setString(2, map.get("说明书"));
				ps.setString(3, map.get("SYSID"));
				try {
					ps.execute();
				} catch (Exception e) {
					logger.error(map.get("SYSID") + e.getMessage());
				}
			}
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[update]" + list.size() + "耗时:" + consume); 
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}

	
	public void insertFlzt(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into flzt(uuid,apply_num,pubd_ate,flzt,app_id,flzt_version,prs_year,flzt_info) "
					+ "values (?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 10000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1, map.get("UUID"));
				ps.setString(2, map.get("申请号"));
				ps.setString(3, map.get("法律状态公告日"));
				ps.setString(4, map.get("法律状态"));
				ps.setString(5, map.get("APP_ID"));
				ps.setString(6, map.get("VERSION"));
				ps.setString(7, map.get("prs_year"));
				ps.setString(8, map.get("法律状态信息"));
				
				ps.addBatch();
				if(++count % batchSize == 0) {
			        ps.executeBatch();
			    }
			}
			ps.executeBatch();	// insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}
	
	
	public void insertTestarray(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into corpration_code(id,title,shen_qing_ren,fa_ming_ren,apply_num) values (?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 10000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1, map.get("SYSID"));
				ps.setString(2, map.get("名称"));
				ps.setArray(3, con.createArrayOf("varchar", map.get("申请（专利权）人").split(";")));
				ps.setArray(4, con.createArrayOf("varchar", map.get("发明（设计）人").split(";")));
				ps.setString(5, map.get("申请号"));
				ps.addBatch();
				if(++count % batchSize == 0) {
			        ps.executeBatch();
			    }
			}
			ps.executeBatch();	// insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}
	
	public void insertFmzl(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into fmzl(SYSID,APP_ID,PUB_ID,apply_num,apply_date,pub_num,pub_date,patent_num,patent_type,REPRESENTATIVE,title,abstract,claim,description,desc_pic,sic,main_sic,eu_sic,eu_main_sic,local_sic,local_main_sic,shen_qing_ren,fa_ming_ren,patent_agent,patent_agent_man,address,apply_country_code,code_guo,code_shen,code_shi,code_qu,apply_source,apply_i18n,pub_i18n,entry_country_date,priority,priority_num,priority_date,tong_zu,can_kao,old_apply_num,censor_man,certification_date,page_num,pub_dir,small_pic_dir,abstract_pic_dir,report_dir,report_page,report_page_info,keyword,auto_abstract,pub_year,table_sn,version,main_claim,patent_status_code,patent_status,flzt,fanchou_fenlei,zuhao,old_an,old_sic,old_pa,old_inn,yin_zheng,old_priority,same_day_apply,pdf_dir,fslx,title_keyword,claim_kyeword,background_keyword) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 100;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1 , map.get("SYSID"));
				ps.setString(2 , map.get("APP_ID"));
				ps.setString(3 , map.get("PUB_ID"));
				ps.setString(4 , map.get("申请号"));
				ps.setString(5 , map.get("申请日"));
				ps.setString(6 , map.get("公开（公告）号"));
				ps.setString(7 , map.get("公开（公告）日"));
				ps.setString(8 , map.get("专利号"));
				ps.setString(9 , map.get("专利类型"));
				ps.setString(10, map.get("REPRESENTATIVE"));
				ps.setString(11, map.get("名称"));
				ps.setString(12, map.get("摘要"));
				ps.setString(13, map.get("权利要求书"));
				ps.setString(14, map.get("说明书"));
				ps.setString(15, map.get("说明书附图"));
				ps.setArray(16, con.createArrayOf("varchar", map.get("分类号").split(";")));
				ps.setString(17, map.get("主分类号"));
				ps.setString(18, map.get("欧洲分类号"));
				ps.setString(19, map.get("欧洲主分类号"));
				ps.setString(20, map.get("本国分类号"));
				ps.setString(21, map.get("本国主分类号"));
				ps.setArray(22, con.createArrayOf("varchar", map.get("申请（专利权）人").split(";")));
				ps.setArray(23, con.createArrayOf("varchar", map.get("发明（设计）人").split(";")));
				ps.setString(24, map.get("专利代理机构"));
				ps.setArray(25, con.createArrayOf("varchar", map.get("代理人").split(";")));
				ps.setString(26, map.get("地址"));
				ps.setString(27, map.get("申请国代码"));
				ps.setString(28, map.get("国省代码"));
				ps.setString(29, map.get("省"));
				ps.setString(30, map.get("市"));
				ps.setString(31, map.get("区"));
				ps.setString(32, map.get("申请来源"));
				ps.setString(33, map.get("国际申请"));
				ps.setString(34, map.get("国际公布"));
				ps.setString(35, map.get("进入国家日期"));
				ps.setString(36, map.get("优先权"));
				ps.setString(37, map.get("优先权号"));
				ps.setString(38, map.get("优先权日"));
				ps.setArray(39, con.createArrayOf("varchar", map.get("同族专利项").split(";")));
				ps.setString(40, map.get("参考文献"));
				ps.setString(41, map.get("分案原申请号"));
				ps.setString(42, map.get("审查员"));
				ps.setString(43, map.get("颁证日"));
				ps.setString(44, map.get("页数"));
				ps.setString(45, map.get("发布路径"));
				ps.setString(46, map.get("缩略图发布路径"));
				ps.setString(47, map.get("摘要附图存储路径"));
				ps.setString(48, map.get("公报发布路径"));
				ps.setString(49, map.get("公报所在页"));
				ps.setString(50, map.get("公报翻页信息"));
				ps.setString(51, map.get("关键词"));
				ps.setString(52, map.get("自动摘要"));
				ps.setString(53, map.get("公开年"));
				ps.setString(54, map.get("TABLE_SN"));
				ps.setString(55, map.get("VERSION"));
				ps.setString(56, map.get("主权项"));
				ps.setString(57, map.get("专利权状态"));
				ps.setString(58, map.get("专利权状态代码"));
				ps.setArray(59, con.createArrayOf("varchar", map.get("法律状态").split(";")));
				ps.setString(60, map.get("范畴分类"));
				ps.setString(61, map.get("族号"));
				ps.setString(62, map.get("旧申请号"));
				ps.setString(63, map.get("旧分类号"));
				ps.setString(64, map.get("旧申请（专利权）人"));
				ps.setString(65, map.get("旧发明（设计）人"));
				ps.setString(66, map.get("引证文献"));
				ps.setString(67, map.get("旧优先权"));
				ps.setString(68, map.get("同日申请"));
				ps.setString(69, map.get("PDF地址"));
				ps.setString(70, map.get("复审类型"));
				ps.setArray(71, con.createArrayOf("varchar", map.get("名称关键词").split(";")));
				ps.setArray(72, con.createArrayOf("varchar", map.get("独权关键词").split(";")));
				ps.setArray(73, con.createArrayOf("varchar", map.get("背景关键词").split(";")));
				ps.addBatch();
				if(++count % batchSize == 0) {
			        ps.executeBatch();
			    }
			}
			ps.executeBatch();	// insert remaining records
			
			long consume = System.currentTimeMillis() - start;
			System.out.println(new Date() + "[insert]" + list.size() + "耗时:" + consume); 
			
			ps.close();
			con.close();
		} catch (Exception e) {
			System.out.print("exception:" + e.getMessage());
		}
	}
	
	
	public void insertPatentCn(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into patent_cn(SYSID,APP_ID,PUB_ID,apply_num,apply_date,pub_num,pub_date,patent_num,patent_type,REPRESENTATIVE,title,abstract,claim,description,desc_pic,sic,main_sic,eu_sic,eu_main_sic,local_sic,local_main_sic,shen_qing_ren,fa_ming_ren,patent_agent,patent_agent_man,address,apply_country_code,code_guo,code_shen,code_shi,code_qu,apply_source,apply_i18n,pub_i18n,entry_country_date,priority,priority_num,priority_date,tong_zu,can_kao,old_apply_num,censor_man,certification_date,page_num,pub_dir,small_pic_dir,abstract_pic_dir,report_dir,report_page,report_page_info,keyword,auto_abstract,pub_year,table_sn,version,main_claim,patent_status_code,patent_status,flzt,fanchou_fenlei,zuhao,old_an,old_sic,old_pa,old_inn,yin_zheng,old_priority,same_day_apply,pdf_dir,fslx,title_keyword,claim_kyeword,background_keyword,patent_db) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 100;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1 , map.get("SYSID"));
				ps.setString(2 , map.get("APP_ID"));
				ps.setString(3 , map.get("PUB_ID"));
				ps.setString(4 , map.get("申请号"));
				ps.setString(5 , map.get("申请日"));
				ps.setString(6 , map.get("公开（公告）号"));
				ps.setString(7 , map.get("公开（公告）日"));
				ps.setString(8 , map.get("专利号"));
				ps.setString(9 , map.get("专利类型"));
				ps.setString(10, map.get("REPRESENTATIVE"));
				ps.setString(11, map.get("名称"));
				ps.setString(12, map.get("摘要"));
				ps.setString(13, map.get("权利要求书").replaceAll("", ""));
				ps.setString(14, map.get("说明书").replaceAll("", ""));
				ps.setString(15, map.get("说明书附图"));
				ps.setArray(16, con.createArrayOf("varchar", map.get("分类号").split(";")));
				ps.setString(17, map.get("主分类号"));
				ps.setString(18, map.get("欧洲分类号"));
				ps.setString(19, map.get("欧洲主分类号"));
				ps.setString(20, map.get("本国分类号"));
				ps.setString(21, map.get("本国主分类号"));
				ps.setArray(22, con.createArrayOf("varchar", map.get("申请（专利权）人").split(";")));
				ps.setArray(23, con.createArrayOf("varchar", map.get("发明（设计）人").split(";")));
				ps.setString(24, map.get("专利代理机构"));
				ps.setArray(25, con.createArrayOf("varchar", map.get("代理人").split(";")));
				ps.setString(26, map.get("地址"));
				ps.setString(27, map.get("申请国代码"));
				ps.setString(28, map.get("国省代码"));
				ps.setString(29, map.get("省"));
				ps.setString(30, map.get("市"));
				ps.setString(31, map.get("区"));
				ps.setString(32, map.get("申请来源"));
				ps.setString(33, map.get("国际申请"));
				ps.setString(34, map.get("国际公布"));
				ps.setString(35, map.get("进入国家日期"));
				ps.setString(36, map.get("优先权"));
				ps.setString(37, map.get("优先权号"));
				ps.setString(38, map.get("优先权日"));
				ps.setArray(39, con.createArrayOf("varchar", map.get("同族专利项").split(";")));
				ps.setString(40, map.get("参考文献"));
				ps.setString(41, map.get("分案原申请号"));
				ps.setString(42, map.get("审查员"));
				ps.setString(43, map.get("颁证日"));
				ps.setString(44, map.get("页数"));
				ps.setString(45, map.get("发布路径"));
				ps.setString(46, map.get("缩略图发布路径"));
				ps.setString(47, map.get("摘要附图存储路径"));
				ps.setString(48, map.get("公报发布路径"));
				ps.setString(49, map.get("公报所在页"));
				ps.setString(50, map.get("公报翻页信息"));
				ps.setString(51, map.get("关键词"));
				ps.setString(52, map.get("自动摘要"));
				ps.setString(53, map.get("公开年"));
				ps.setString(54, map.get("TABLE_SN"));
				ps.setString(55, map.get("VERSION"));
				ps.setString(56, map.get("主权项"));
				ps.setString(57, map.get("专利权状态"));
				ps.setString(58, map.get("专利权状态代码"));
				ps.setArray(59, con.createArrayOf("varchar", map.get("法律状态").split(";")));
				ps.setString(60, map.get("范畴分类"));
				ps.setString(61, map.get("族号"));
				ps.setString(62, map.get("旧申请号"));
				ps.setString(63, map.get("旧分类号"));
				ps.setString(64, map.get("旧申请（专利权）人"));
				ps.setString(65, map.get("旧发明（设计）人"));
				ps.setString(66, map.get("引证文献"));
				ps.setString(67, map.get("旧优先权"));
				ps.setString(68, map.get("同日申请"));
				ps.setString(69, map.get("PDF地址"));
				ps.setString(70, map.get("复审类型"));
				ps.setArray(71, con.createArrayOf("varchar", map.get("名称关键词").split(";")));
				ps.setArray(72, con.createArrayOf("varchar", map.get("独权关键词").split(";")));
				ps.setArray(73, con.createArrayOf("varchar", map.get("背景关键词").split(";")));
				ps.setString(74, map.get("patent_db"));
				
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
	
	public void insertPatentCn2(TrsListMapSearchDTO dto) {
		long start = System.currentTimeMillis();
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");	
			
			String sql = "insert into patent_cn(SYSID,APP_ID,PUB_ID,apply_num,apply_date,pub_num,pub_date,patent_num,patent_type,REPRESENTATIVE,title,abstract,claim,description,desc_pic,sic,main_sic,eu_sic,eu_main_sic,local_sic,local_main_sic,shen_qing_ren,fa_ming_ren,patent_agent,patent_agent_man,address,apply_country_code,code_guo,code_shen,code_shi,code_qu,apply_source,apply_i18n,pub_i18n,entry_country_date,priority,priority_num,priority_date,tong_zu,can_kao,old_apply_num,censor_man,certification_date,page_num,pub_dir,small_pic_dir,abstract_pic_dir,report_dir,report_page,report_page_info,keyword,auto_abstract,pub_year,table_sn,version,main_claim,patent_status_code,patent_status,flzt,fanchou_fenlei,zuhao,old_an,old_sic,old_pa,old_inn,yin_zheng,old_priority,same_day_apply,pdf_dir,fslx,title_keyword,claim_kyeword,background_keyword,patent_db) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement ps = con.prepareStatement(sql);
			final int batchSize = 1000;
			int count = 0;
			
			List<Map<String, String>> list = dto.getResultList();
			for (Iterator<Map<String, String>> iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> map = (Map<String, String>) iterator.next();
				ps.setString(1 , map.get("SYSID"));
				ps.setString(2 , map.get("APP_ID"));
				ps.setString(3 , map.get("PUB_ID"));
				ps.setString(4 , map.get("申请号"));
				ps.setString(5 , map.get("申请日"));
				ps.setString(6 , map.get("公开（公告）号"));
				ps.setString(7 , map.get("公开（公告）日"));
				ps.setString(8 , map.get("专利号"));
				ps.setString(9 , map.get("专利类型"));
				ps.setString(10, map.get("REPRESENTATIVE"));
				ps.setString(11, map.get("名称"));
				ps.setString(12, map.get("摘要"));
//				ps.setString(13, map.get("权利要求书"));
//				ps.setString(14, map.get("说明书"));
				ps.setString(13, "");
				ps.setString(14, "");
				ps.setString(15, map.get("说明书附图"));
				ps.setArray(16, con.createArrayOf("varchar", map.get("分类号").split(";")));
				ps.setString(17, map.get("主分类号"));
				ps.setString(18, map.get("欧洲分类号"));
				ps.setString(19, map.get("欧洲主分类号"));
				ps.setString(20, map.get("本国分类号"));
				ps.setString(21, map.get("本国主分类号"));
				ps.setArray(22, con.createArrayOf("varchar", map.get("申请（专利权）人").split(";")));
				ps.setArray(23, con.createArrayOf("varchar", map.get("发明（设计）人").split(";")));
				ps.setString(24, map.get("专利代理机构"));
				ps.setArray(25, con.createArrayOf("varchar", map.get("代理人").split(";")));
				ps.setString(26, map.get("地址"));
				ps.setString(27, map.get("申请国代码"));
				ps.setString(28, map.get("国省代码"));
				ps.setString(29, map.get("省"));
				ps.setString(30, map.get("市"));
				ps.setString(31, map.get("区"));
				ps.setString(32, map.get("申请来源"));
				ps.setString(33, map.get("国际申请"));
				ps.setString(34, map.get("国际公布"));
				ps.setString(35, map.get("进入国家日期"));
				ps.setString(36, map.get("优先权"));
				ps.setString(37, map.get("优先权号"));
				ps.setString(38, map.get("优先权日"));
				ps.setArray(39, con.createArrayOf("varchar", map.get("同族专利项").split(";")));
				ps.setString(40, map.get("参考文献"));
				ps.setString(41, map.get("分案原申请号"));
				ps.setString(42, map.get("审查员"));
				ps.setString(43, map.get("颁证日"));
				ps.setString(44, map.get("页数"));
				ps.setString(45, map.get("发布路径"));
				ps.setString(46, map.get("缩略图发布路径"));
				ps.setString(47, map.get("摘要附图存储路径"));
				ps.setString(48, map.get("公报发布路径"));
				ps.setString(49, map.get("公报所在页"));
				ps.setString(50, map.get("公报翻页信息"));
				ps.setString(51, map.get("关键词"));
				ps.setString(52, map.get("自动摘要"));
				ps.setString(53, map.get("公开年"));
				ps.setString(54, map.get("TABLE_SN"));
				ps.setString(55, map.get("VERSION"));
				ps.setString(56, map.get("主权项"));
				ps.setString(57, map.get("专利权状态"));
				ps.setString(58, map.get("专利权状态代码"));
				ps.setArray(59, con.createArrayOf("varchar", map.get("法律状态").split(";")));
				ps.setString(60, map.get("范畴分类"));
				ps.setString(61, map.get("族号"));
				ps.setString(62, map.get("旧申请号"));
				ps.setString(63, map.get("旧分类号"));
				ps.setString(64, map.get("旧申请（专利权）人"));
				ps.setString(65, map.get("旧发明（设计）人"));
				ps.setString(66, map.get("引证文献"));
				ps.setString(67, map.get("旧优先权"));
				ps.setString(68, map.get("同日申请"));
				ps.setString(69, map.get("PDF地址"));
				ps.setString(70, map.get("复审类型"));
				ps.setArray(71, con.createArrayOf("varchar", map.get("名称关键词").split(";")));
				ps.setArray(72, con.createArrayOf("varchar", map.get("独权关键词").split(";")));
				ps.setArray(73, con.createArrayOf("varchar", map.get("背景关键词").split(";")));
				ps.setString(74, map.get("patent_db"));
				
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
	
	
//	public Connection getConnection()
//			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//		if (con == null || con.isClosed()) {
//			Class.forName("org.postgresql.Driver").newInstance();
//			String url = "jdbc:postgresql://192.168.201.165:5432/testDB";
//			Connection con = DriverManager.getConnection(url, "gpadmin", "gpadmin");
//		}		
//		return con;
//	}
	
}
