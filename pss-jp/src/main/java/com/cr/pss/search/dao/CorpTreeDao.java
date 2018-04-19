package com.cnipr.pss.search.dao;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.entity.search.Corp;
import com.cnipr.pss.entity.search.CorpCode;
import com.cnipr.pss.rs.dto.CorpDTO;
import com.cnipr.pss.rs.dto.CorpTreeInfoDTO;

/**
 * CorpCode操作的DAO.
 * 
 * @author dj
 */
@Component
public class CorpTreeDao extends HibernateDao<CorpTreeInfoDTO, Long> {
	// -- 统一定义所有以用户为主体的HQL --//
	private DozerBeanMapper dozer = new DozerBeanMapper();
	private static final String HAS_NO_CODE = "from Corp "
			+ "where isvalid = 1 and corpname like ? and corpcode is null order by corpname";
	private static final String CORP_BY_CODE = "from Corp "
			+ "where isvalid = 1 and corpcode = ?  order by corpname";
	private static final String SEARCH_TOP_CODE = "from Corp "
			+ "where isvalid = 1 and corpname like ? and corpcode_topap is not null";
	private static final String GET_CORP_CODE_BY_PARENT = "from CorpCode where corpcode_pap = ? order by corpcode";
	private static final String GET_CORP_CODE = "from CorpCode where corpcode = ? order by corpcode";

	/**
	 * 搜索没有corpcode的公司记录.
	 */
	@SuppressWarnings("unchecked")
	public CorpTreeInfoDTO searchHasNoCode(String str, int from, int to) {
		Query query = createQuery(HAS_NO_CODE, "%" + str + "%");
		query.setFirstResult(from);
		query.setMaxResults(to - from);
		List<Corp> list = query.list();
		List<CorpDTO> dtoList = new ArrayList();
		for(Corp corp:list){
			dtoList.add(dozer.map(corp, CorpDTO.class));
		}
		long total = (Long) findUnique("select count(corpname) " + HAS_NO_CODE,
				"%" + str + "%");
		if (list != null && list.size() > 0) {
			CorpTreeInfoDTO root = new CorpTreeInfoDTO();
			root.setCorpList(dtoList);
			root.setCorpTotal(total);
			return root;
		} else {
			return new CorpTreeInfoDTO();
		}
	}

	/**
	 * 搜索没有corpcode的公司记录.
	 */
	@SuppressWarnings("unchecked")
	public CorpTreeInfoDTO searchCorpByCode(String str, int from, int to) {
		Query query = createQuery(CORP_BY_CODE, str);
		query.setFirstResult(from);
		query.setMaxResults(to - from);
		List<Corp> list = query.list();
		List<CorpDTO> dtoList = new ArrayList();
		for(Corp corp:list){
			dtoList.add(dozer.map(corp, CorpDTO.class));
		}
		long total = (Long) findUnique(
				"select count(corpname) " + CORP_BY_CODE, str);
		if (list != null && list.size() > 0) {
			CorpTreeInfoDTO root = new CorpTreeInfoDTO();
			root.setCorpList(dtoList);
			root.setCorpTotal(total);
			return root;
		} else {
			return new CorpTreeInfoDTO();
		}
	}

	/**
	 * 搜索没有corpcode的公司记录.
	 */
	@SuppressWarnings("unchecked")
	public CorpTreeInfoDTO searchTopCode(String str, int from, int to) {
		Query query = createQuery("select distinct corpcode_topap "
				+ SEARCH_TOP_CODE, "%" + str + "%");
		query.setFirstResult(from);
		query.setMaxResults(to - from);
		List<String> list = query.list();
		long total = (Long) findUnique("select count(distinct corpcode_topap) "
				+ SEARCH_TOP_CODE, "%" + str + "%");
		if (list != null && list.size() > 0) {
			List<CorpTreeInfoDTO> corpTreeInfos = new ArrayList<CorpTreeInfoDTO>();
			for (String code : list) {
				CorpCode corpCode = findUnique(GET_CORP_CODE, code);
				CorpTreeInfoDTO temp = new CorpTreeInfoDTO();
				temp.setCorpCode(corpCode.getCorpcode());
				temp.setHasChild(corpCode.getCorpcode_children() > 0 ? true
						: false);
				corpTreeInfos.add(temp);
			}
			CorpTreeInfoDTO root = new CorpTreeInfoDTO();
			root.setCorpCodeList(corpTreeInfos);
			root.setCorpCodeTotal(total);
			return root;
		} else {
			return new CorpTreeInfoDTO();
		}
	}

	/**
	 * 搜索没有corpcode的公司记录.
	 */
	@SuppressWarnings("unchecked")
	public List<CorpTreeInfoDTO> searchCorpCode(String str) {
		List<CorpTreeInfoDTO> returnList = null;
		List<CorpCode> list = find(GET_CORP_CODE_BY_PARENT, str);
		if (list != null && list.size() > 0) {
			returnList = new ArrayList<CorpTreeInfoDTO>();
			for (CorpCode o : list) {
				CorpTreeInfoDTO node = new CorpTreeInfoDTO();
				node.setCorpCode(o.getCorpcode());
				node.setHasChild(o.getCorpcode_children() > 0 ? true : false);
				returnList.add(node);
			}
			return returnList;
		} else {
			return new ArrayList<CorpTreeInfoDTO>();
		}
	}

}
