package com.cnipr.pss.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.rs.dto.IpcInfoDTO;
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
public class IpcManager {

	private IpcTreeDao ipcTreeDao;

	/**
	 * 获取默认.
	 */
	@Transactional(readOnly = true)
	public IpcInfoDTO searchDefault() {
		return ipcTreeDao.searchDefault();
	}

	@Transactional(readOnly = true)
	public IpcInfoDTO searchStr(int choice, String str, int from, int to) {
		return ipcTreeDao.searchStr(choice, str.toUpperCase(), from, to);
	}

	@Transactional(readOnly = true)
	public IpcInfoDTO searchPathAndIpcs(String ipc) {
		return ipcTreeDao.searchPathAndIpcs(ipc.toUpperCase());
	}

	@Transactional(readOnly = true)
	public IpcInfoDTO expandIpc(String ipc) {
		return ipcTreeDao.expandIpc(ipc.toUpperCase());
	}

	@Autowired
	public void setIpcTreeDao(IpcTreeDao ipcTreeDao) {
		this.ipcTreeDao = ipcTreeDao;
	}

}
