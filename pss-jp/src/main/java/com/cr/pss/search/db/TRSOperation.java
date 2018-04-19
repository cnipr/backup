package com.cnipr.pss.search.db;

import java.io.InputStream;
import java.util.Properties;

import com.eprobiti.trs.TRSConnection;
import com.eprobiti.trs.TRSConstant;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;
import com.trs.ckm.zl.SemanticSearch;

/**
 * TRS数据库操作类，
 * 可以获取普通检索的连接，或是语义检索的实例
 * @author lq
 *
 */
public class TRSOperation {
	
	private static Properties _properties = null;

	/**
	 * 获取配置文件信息
	 * @param key：属性名
	 * @return 属性值
	 */
	public static String getProperty(String key) {
		if (_properties == null) {
			try {
				InputStream ins = TRSOperation.class
						.getResourceAsStream("/ckmconfig.properties");
				_properties = new Properties();
				_properties.load(ins);
			} catch (Exception ex) {
				_properties = null;
			}
		}

		return _properties.getProperty(key);
	}
	
	/**
	 * 获取trs普通检索的一个连接
	 * @return trs连接
	 * @throws TRSException
	 */
	public TRSConnection getConnect() {
		TRSConnection trsConnection = null;
		try {
			trsConnection = new TRSConnection();
			trsConnection.connect(getProperty("TRSHost"),
					getProperty("TRSPort"), getProperty("TRSUserName"),
					getProperty("TRSPasswd"));
		} catch (TRSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return trsConnection;
	}
	
	/**
	 * 执行TRS检索 并根据条件设置利用知识辅助词典自动进行知识扩展检索选项
	 * 
	 * @param strSources
	 * @param strWhere
	 * @param strSortMethod
	 * @param strStat
	 * @param strDefautCols
	 * @param iOption
	 * @param iHitPointType
	 * @param bContinue
	 * @param strSynonymous
	 * @return
	 * @throws TRSException
	 */
	public TRSResultSet executeTRSSearch(TRSConnection trsConnection, String strSources, String strWhere,
			String strSortMethod, String strStat, String strDefautCols,
			int iOption, int iHitPointType, boolean bContinue,
			String strSynonymous) throws TRSException {
		TRSResultSet resultSet = null;

		/** 根据条件设置利用知识辅助词典自动进行知识扩展检索选项(同义词扩展) */
		if (strSynonymous != null && !strSynonymous.equals("")) {
			trsConnection.setAutoExtend("", "", "", TRSConstant.TCM_KAXST);
		}
		
//		if (strSortMethod == null || strSortMethod.equals("")) {
//			strSortMethod = "+table_sn";
//		} else {
//			strSortMethod = strSortMethod + ",+table_sn";
//		}
		
		resultSet = trsConnection.executeSelect(strSources, strWhere,
				strSortMethod, strStat, strDefautCols, iOption, iHitPointType,
				bContinue);

		return resultSet;
	}
	
	/**
	 * 获取语义检索的一个实例
	 * @return
	 */
	public SemanticSearch getSemanticInstance() {
		return new SemanticSearch();
	}
}
