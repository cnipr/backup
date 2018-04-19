package com.cnipr.pss.service.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.rs.dto.LawStatusDTO;
import com.cnipr.pss.search.dao.LawStatusDao;

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
public class LawStatusManager {

	private LawStatusDao lawStatusDao;

	/**
	 * 获取最新法律状态.
	 */
	@Transactional(readOnly = true)
	public List<LawStatusDTO> searchDefault(String app_id) {
		return lawStatusDao.searchLawStatus(app_id);
	}

	@Autowired
	public void setLawStatusDao(LawStatusDao lawStatusDao) {
		this.lawStatusDao = lawStatusDao;
	}

}
