package com.cnipr.pss.search.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.exception.PSSException;
import com.cnipr.pss.rs.dto.search.CnReferencesInfo;


@Component
public class CnReferenceDao extends HibernateDao<CnReferencesInfo, String> {
	public List<CnReferencesInfo> getScyyzzlList(String an) {
		List<CnReferencesInfo> list = null;

		try {
			Query query = createQuery(
					"from CnReferencesInfo where applicationNumber=? and citedType='patentCitation'",
					an);
			list = query.list();
		} catch (Exception e) {
			new PSSException("3014", e.getMessage(), "审查员引证专利检索出错",
					"CnReferenceDao", "getScyyzzlList", "rdb");
			list = null;
		}

		return list;
	}

	public List<CnReferencesInfo> getScyyzfzlList(String an) {
		List<CnReferencesInfo> list = null;

		try {
			Query query = createQuery(
					"from CnReferencesInfo where applicationNumber=? and citedType='literatureCitation'",
					an);
			list = query.list();
		} catch (Exception e) {
			new PSSException("3014", e.getMessage(), "审查员引证非专利检索出错",
					"CnReferenceDao", "getScyyzzlList", "rdb");
			list = null;
		}

		return list;
	}

	public List<CnReferencesInfo> getSqryzzlList(String an) {
		List<CnReferencesInfo> list = null;

		try {
			Query query = createQuery(
					"from CnReferencesInfo where applicationNumber=? and citedType='applicatCitation'",
					an);
			list = query.list();
		} catch (Exception e) {
			new PSSException("3014", e.getMessage(), "申请人引证专利检索出错",
					"CnReferenceDao", "getSqryzzlList", "rdb");
			list = null;
		}
		return list;
	}

	public List<CnReferencesInfo> getScybyzzlList(String an) {
		List<CnReferencesInfo> list = null;

		try {
			Query query = createQuery(
					"from CnReferencesInfo where applicationNumber=? and citedType='patentCitationed'",
					an);
			list = query.list();
		} catch (Exception e) {
			new PSSException("3014", e.getMessage(), "审查员被引证专利检索出错",
					"CnReferenceDao", "getScyyzzlList", "rdb");
			list = null;
		}

		return list;
	}

	public List<CnReferencesInfo> getSqrbyzzlList(String an) {
		List<CnReferencesInfo> list = null;

		try {
			Query query = createQuery(
					"from CnReferencesInfo where applicationNumber=? and citedType='applicatCitationed'",
					an);
			list = query.list();
		} catch (Exception e) {
			new PSSException("3014", e.getMessage(), "申请人被引证专利检索出错",
					"CnReferenceDao", "getScyyzzlList", "rdb");
			list = null;
		}

		return list;
	}
}
