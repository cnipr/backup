package com.cnipr.pss.search.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.rs.dto.search.ExtractInfo;

@Component("metPatentDao")
public class METPatentDao extends HibernateDao<ExtractInfo, String> {

	@SuppressWarnings("unchecked")
	public List<ExtractInfo> extractPatBySysid(String sysids) {

		Session session = getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String hql = "select t from ExtractInfo t where t.sysId in ('"
				+ sysids.replace(",", "','") + "')";
		List<ExtractInfo> list = session.createQuery(hql).list();
		tx.commit();
		session.close();
		return list;
	}
}
