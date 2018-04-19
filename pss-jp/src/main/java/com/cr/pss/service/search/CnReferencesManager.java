package com.cnipr.pss.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.rs.dto.CnReferencesDTO;
import com.cnipr.pss.search.dao.CnReferenceDao;

@Component
@Transactional
public class CnReferencesManager {
	@Autowired
	private CnReferenceDao cnReferenceDao;
	
	public CnReferencesDTO getReferenceList(String an) {
		CnReferencesDTO dto = new CnReferencesDTO();
		dto.setScybyzzlList(cnReferenceDao.getScybyzzlList(an));
		dto.setScyyzfzlList(cnReferenceDao.getScyyzfzlList(an));
		dto.setScyyzzlList(cnReferenceDao.getScyyzzlList(an));
		dto.setSqrbyzzlList(cnReferenceDao.getSqrbyzzlList(an));
		dto.setSqryzzlList(cnReferenceDao.getSqryzzlList(an));
		return dto;
	}
}
