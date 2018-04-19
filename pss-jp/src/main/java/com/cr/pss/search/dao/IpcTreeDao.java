package com.cnipr.pss.search.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.cnipr.pss.entity.search.Ipc;
import com.cnipr.pss.rs.dto.IpcInfoDTO;

/**
 * Ipc操作的DAO.
 * 
 * @author dj
 */
@Component
public class IpcTreeDao extends HibernateDao<IpcInfoDTO, Long> {
	// -- 统一定义所有以用户为主体的HQL --//
	private static final String DEFAULT = "from Ipc "
			+ "where isvalid = 1 and parentipc is null order by ipc";
	private static final String RESULT_BY_SEARCH_STR = "from Ipc "
			+ "where isvalid = 1 and NOTE like ? order by ipc";
	private static final String RESULT_BY_SEARCH_IPC = "from Ipc "
			+ "where isvalid = 1 and IPC like ? order by ipc";
	private static final String GET_PARENTIPC = "from Ipc where isvalid = 1 and IPC = ? order by ipc";
	private static final String GET_IPCS = "from Ipc where isvalid = 1 and PARENTIPC = ? order by ipc";

	/**
	 * 默认的IPC搜索.
	 */
	public IpcInfoDTO searchDefault() {
		// List<Object[]> list = getSession().createSQLQuery(DEFAULT).list();
		List<Ipc> list = find(DEFAULT);
		if (list != null && list.size() > 0) {
			List<IpcInfoDTO> ipcList = new ArrayList<IpcInfoDTO>();
			for (Ipc ipc : list) {
				IpcInfoDTO childIpc = new IpcInfoDTO();
				childIpc.setIpc(ipc.getIpc());
				childIpc.setNote(ipc.getNote());
				childIpc.setNotejp(ipc.getNotejp());
				childIpc.setNoteen(ipc.getNoteen());
				childIpc.setHasChild(ipc.getIshaschild() == 1 ? true : false);
				ipcList.add(childIpc);
			}
			IpcInfoDTO root = new IpcInfoDTO();
			root.setChildIpcs(ipcList);
			return root;
		} else {
			return new IpcInfoDTO();
		}
	}

	/**
	 * 带参数的IPC搜索.
	 */
	@SuppressWarnings("unchecked")
	public IpcInfoDTO searchStr(int choice, String str, int from, int to) {
		List<Ipc> list = null;
		long total = 0;
		if (choice == 1) {
			Query query = createQuery(RESULT_BY_SEARCH_IPC, "%" + str + "%");
			query.setFirstResult(from);
			query.setMaxResults(to - from);
			list = query.list();
			total = (Long) findUnique("select count(ipc) "
					+ RESULT_BY_SEARCH_IPC, "%" + str + "%");
		} else {
			Query query = createQuery(RESULT_BY_SEARCH_STR, "%" + str + "%");
			query.setFirstResult(from);
			query.setMaxResults(to - from);
			list = query.list();
			total = (Long) findUnique("select count(ipc) "
					+ RESULT_BY_SEARCH_STR, "%" + str + "%");
		}
		if (list != null && list.size() > 0) {
			List<IpcInfoDTO> ipcList = new ArrayList<IpcInfoDTO>();
			for (Ipc o : list) {
				IpcInfoDTO childIpc = new IpcInfoDTO();
				childIpc.setIpc(o.getIpc());
				childIpc.setParentIpc(o.getParentipc());
				childIpc.setNote(o.getNote());
				childIpc.setNotejp(o.getNotejp());
				childIpc.setNoteen(o.getNoteen());
				childIpc.setHasChild(o.getIshaschild() == 1 ? true : false);
				ipcList.add(childIpc);
			}
			IpcInfoDTO root = new IpcInfoDTO();
			root.setChildIpcs(ipcList);
			root.setTotal(total);
			return root;
		} else {
			return new IpcInfoDTO();
		}
	}

	/**
	 * 带路径的IPC搜索.
	 */
	public IpcInfoDTO searchPathAndIpcs(String ipc) {
		IpcInfoDTO root = null;
		IpcInfoDTO currentIpc = null;
		boolean flag = true;
		while (flag) {
			Ipc ipcBean = findUnique(GET_PARENTIPC, ipc);
			IpcInfoDTO ipcInfo = new IpcInfoDTO();
			ipcInfo.setIpc(ipc);
			ipcInfo.setNote(ipcBean.getNote());
			ipcInfo.setNotejp(ipcBean.getNotejp());
			ipcInfo.setNoteen(ipcBean.getNoteen());
			ipcInfo.setHasChild(ipcBean.getIshaschild() == 1 ? true : false);
			if (ipcBean.getParentipc() == null
					|| ipcBean.getParentipc().equals("")) {
				if (currentIpc != null) {
					List<IpcInfoDTO> tempList = new ArrayList<IpcInfoDTO>();
					tempList.add(currentIpc);
					ipcInfo.setChildIpcs(tempList);
				}
				root = ipcInfo;
				flag = false;
			} else {
				ipcInfo.setParentIpc(ipcBean.getParentipc());
				if (currentIpc != null) {
					List<IpcInfoDTO> tempList = new ArrayList<IpcInfoDTO>();
					tempList.add(currentIpc);
					ipcInfo.setChildIpcs(tempList);
				}
				currentIpc = ipcInfo;
				ipc = ipcBean.getParentipc();
			}
		}
		return root;
	}

	/**
	 * 展开IPC.
	 */
	public IpcInfoDTO expandIpc(String ipc) {
		List<Ipc> list = find(GET_IPCS, ipc);
		if (list != null && list.size() > 0) {
			List<IpcInfoDTO> ipcList = new ArrayList<IpcInfoDTO>();
			for (Ipc o : list) {
				IpcInfoDTO childIpc = new IpcInfoDTO();
				childIpc.setIpc(o.getIpc());
				childIpc.setParentIpc(o.getParentipc());
				childIpc.setNote(o.getNote());
				childIpc.setNotejp(o.getNotejp());
				childIpc.setNoteen(o.getNoteen());
				childIpc.setHasChild(o.getIshaschild() == 1 ? true : false);
				ipcList.add(childIpc);
			}
			IpcInfoDTO root = new IpcInfoDTO();
			root.setIpc(ipc);
			root.setChildIpcs(ipcList);
			return root;
		} else {
			return new IpcInfoDTO();
		}
	}
}
