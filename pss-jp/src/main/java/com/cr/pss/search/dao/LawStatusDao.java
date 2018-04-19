package com.cnipr.pss.search.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.entity.search.LawStatus;
import com.cnipr.pss.rs.dto.LawStatusDTO;

/**
 * LawStatus操作的DAO.
 * 
 * @author dj
 */
@Component
public class LawStatusDao extends HibernateDao<LawStatusDTO, Long> {
	// -- 统一定义所有以用户为主体的HQL --//
	private DozerBeanMapper dozer = new DozerBeanMapper();
	private static final String GET_LAW_STATUS = "from LawStatus "
			+ "where app_id in (:appids) ";

	/**
	 * 搜索此app_id的最新法律状态记录.
	 */
	@SuppressWarnings("unchecked")
	public List<LawStatusDTO> searchLawStatus(String str) {
		List<String> strs = null;
		if (str == null || str.equals("")) {
			return new ArrayList<LawStatusDTO>();
		} else {
			strs = Arrays.asList(str.split(","));
		}
		Query query = createQuery(GET_LAW_STATUS);
		query.setParameterList("appids", strs);
		List<LawStatus> list = query.list();
		List<LawStatusDTO> dtoList = new ArrayList<LawStatusDTO>();
		for (LawStatus lawStatus : list) {
			String flag = lawStatus.getTable_flag();
			if (flag != null && !flag.equals("")) {
				String prsDate = lawStatus.getPrs_date();
				Date curDate = new Date();
				int year = curDate.getYear();
				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				String date = "";
				if (flag.indexOf("FM") != -1) {
					curDate.setYear(year - 20);
					date = sDateFormat.format(curDate);
					
				} else {
					curDate.setYear(year - 10);
					date = sDateFormat.format(curDate);
				}
				int result = date.compareTo(prsDate);
				if(result>0){
					lawStatus.setPrs_code_level_1("失效");
				}
			}
			dtoList.add(dozer.map(lawStatus, LawStatusDTO.class));
		}
		return dtoList;
	}
}
