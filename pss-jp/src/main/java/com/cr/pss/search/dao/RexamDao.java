package com.cnipr.pss.search.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.entity.search.Rexam;

@Component
public class RexamDao extends HibernateDao<Rexam, Long> {
	
	public List<Rexam> searchRexamsByAppNum(String appNum){
		return findBy("applicationNumber", appNum);
	}
	
	/**
	 * 根据申请号和类型查询Rexams
	 * @param appNum
	 * @param kind
	 * @return
	 */
	public List<Rexam> queryRexamsByAppNumKind(String appNum,String kind){
		String hql="from Rexam where applicationNumber=? and kind=? order by judgementDate asc ";
		return find(hql,  new Object[]{appNum,kind});
	}
	
	/**
	 * 根据申请号查询某专利的法院判决、复审决定、无效宣告
	 * @param appNum
	 * @return
	 */
	public List<Object> queryRexamKindsByAppNum(String appNum){
		String hql="select kind,count(*) as KINDCOUNT from Rexam where applicationNumber=? group by kind";
		List<Object> kinds = find(hql, new Object[]{appNum});
		return kinds;
	}
	
	/**
	 * 
	 * @param appNum 申请号
	 * @param judgementalDate 法院判决日
	 * @return
	 */
	public Rexam queryFypjRexamByAppNumJudDate(String appNum,String judgementalDate){
		String hql="from Rexam where applicationNumber=? and judgementDate=? and kind='FYPJ' ";
		return (Rexam)findUnique(hql,  new Object[]{appNum,judgementalDate});
	}
	
	/**
	 * 
	 * @param appNum 申请号
	 * @param decisionNumber 决定日
	 * @return
	 */
	public Rexam queryFsjdRexamByAppNumDecisionnumber(String appNum,String decisionNumber){
		String hql="from Rexam where applicationNumber=? and decisionNumber=? and kind='FSJD' ";
		return (Rexam)findUnique(hql,  new Object[]{appNum,decisionNumber});
	}
}
