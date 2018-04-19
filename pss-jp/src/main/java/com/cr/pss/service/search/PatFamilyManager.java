package com.cnipr.pss.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.rs.dto.PatFamilySearchDTO;
import com.cnipr.pss.search.dao.FamilyDao;

@Component
@Transactional
public class PatFamilyManager {
	@Autowired
	private FamilyDao familyDao;
	
	public PatFamilySearchDTO getAllFamilys(String an) {
		return familyDao.getPatFamilys(an);
	}
}
