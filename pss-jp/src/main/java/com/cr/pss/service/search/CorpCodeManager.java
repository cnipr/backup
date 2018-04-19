package com.cnipr.pss.service.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.rs.dto.CorpTreeInfoDTO;
import com.cnipr.pss.rs.dto.IpcInfoDTO;
import com.cnipr.pss.search.dao.CorpTreeDao;
import com.cnipr.pss.search.dao.IpcTreeDao;

/**
 * ipc管理类.
 * 
 * 实现领域对象用户及其相关实体的所有业务管理函数. 使用Spring annotation定义事务管理.
 * 
 * @author dj
 */
// Spring Service Bean的标识.
@Component
// 默认将类中的所有函数纳入事务管理.
@Transactional
public class CorpCodeManager {
	@Autowired
	private CorpTreeDao corpTreeDao;

	/**
	 * 获取默认.
	 */
	@Transactional(readOnly = true)
	public CorpTreeInfoDTO searchHasNoCode(String str, int from, int to) {
		return corpTreeDao.searchHasNoCode(str, from, to);
	}

	@Transactional(readOnly = true)
	public CorpTreeInfoDTO searchCorpByCode(String str, int from, int to) {
		return corpTreeDao.searchCorpByCode(str, from, to);
	}

	@Transactional(readOnly = true)
	public CorpTreeInfoDTO searchTopCode(String str, int from, int to) {
		return corpTreeDao.searchTopCode(str, from, to);
	}

	@Transactional(readOnly = true)
	public List<CorpTreeInfoDTO> searchCorpCode(String str) {
		return corpTreeDao.searchCorpCode(str);
	}

	
	public void setCorpTreeDao(CorpTreeDao corpTreeDao) {
		this.corpTreeDao = corpTreeDao;
	}

}
