package com.cnipr.pss.search.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.entity.search.PatFamily;
import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.PatFamilySearchDTO;

@Component
public class FamilyDao extends HibernateDao<PatFamily, String> {
//	private static final String GET_ALL_FAMILYS = "";

	@SuppressWarnings("unchecked")
	public PatFamilySearchDTO getPatFamilys(String an) {
		PatFamilySearchDTO dto = new PatFamilySearchDTO();

		StringBuffer familys = new StringBuffer();

		try {
			Query query = createQuery("from PatFamily where familyNumber in (select t.familyNumber from PatFamily t where t.applicationNumber like '"+an+"%')");
			List<PatFamily> list = query.list();

			if (list != null && list.size() > 0) {
				for (PatFamily family : list) {
					String strAn = family.getApplicationNumber();
					if (family.getApplicationNumber().startsWith("CN")) {
						strAn = strAn.substring(0, strAn.length() - 1);
					}
					if (strAn.equals(an.replace("%", ""))) {
						continue;
					} else if (familys.indexOf(strAn) <= 0) {
						familys.append(strAn).append(",");
					}
				}
			}
		} catch (Exception e) {
			new PSSException("3013", e.getMessage(), "同族检索出错", "FamilyDao",
					"getPatFamilys", "rdb");
			dto.setReturncode(3013);
			dto.setMessage(e.getMessage());
		}

		if (familys.length() >= 1) {
			dto.setFamilies(familys.substring(0, familys.length() - 1));
		} else {
			dto.setFamilies(familys.toString());
		}

		return dto;

	}
}
