package com.cnipr.pss.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.util.DocUtil;

import com.cnipr.pss.rs.dto.search.SectionInfo;
import com.eprobiti.trs.TRSException;
import com.eprobiti.trs.TRSResultSet;

/**
 * 检索服务工具类
 * 
 * @author lq
 * 
 */
public class PssUtil {

	private static Properties _properties = null;

	/**
	 * 获取配置文件信息
	 * 
	 * @param key
	 *            ：属性名
	 * @return 属性值
	 */
	public static String getProperty(String key) {
		if (_properties == null) {
			try {
				InputStream ins = PssUtil.class
						.getResourceAsStream("/ckmconfig.properties");
				_properties = new Properties();
				_properties.load(ins);
			} catch (Exception ex) {
				_properties = null;
			}
		}

		return _properties.getProperty(key);
	}

	/** InputStream转成byte[] **/
	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;

		Collection<byte[]> chunks = new ArrayList<byte[]>();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}

		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}

	/** double转成百分比 **/
	public static String getPercent(double source) {
		source = source * 100;
		String formatPercent = "";
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("###.#");
		formatPercent = df.format(source);
		return formatPercent + "%";
	}
	
	public static String getPdfUrl(String an, String pnm, String ad, String pd, String pattype, String sectionName) {
		String pdfurl = "";
		int patType = 0;
		try {
			patType = Integer.parseInt(pattype);
			pdfurl = DocUtil.getDocId(an,
					pnm, ad.replace(".", ""), pd.replace(".", ""), patType,  getPatentType(sectionName).toUpperCase());
//			System.out.println(pdfurl);
		} catch (Exception e) {
			e.printStackTrace();
			pdfurl = "";
		} finally {
//			System.out.println("111: " + an + "###" +
//					pnm + "###" +ad.replace(".", "") + "###" + pd.replace(".", "") + "###" + patType + "###" + getPatentType(sectionName).toUpperCase());
		}
		
		return pdfurl;
	}
	
	public static String getPatentType(String channelName) {
		channelName = channelName.toLowerCase();
		String patentType = "";
		try {
			if (channelName.indexOf("fmzl") > -1) {
				patentType = "fm";
			}
			if (channelName.indexOf("syxx") > -1) {
				patentType = "xx";
			}
			if (channelName.indexOf("wgzl") > -1) {
				patentType = "wg";
			}
			if (channelName.indexOf("fmsq") > -1) {
				patentType = "sq";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return patentType;
	}

	public List<SectionInfo> getSectionInfos(TRSResultSet trsresultset) {
		int sectionCount = trsresultset.getSectionCount();
		List<SectionInfo> sectionInfoList = new ArrayList<SectionInfo>();

		for (int i = 0; i < sectionCount; i++) {
			try {
				SectionInfo info = new SectionInfo();
				info.setSectionName(trsresultset.getSectionInfo(i).strName);
				info.setRecordNum(trsresultset.getSectionInfo(i).iRecordNum);
				sectionInfoList.add(info);
			} catch (TRSException e) {
				e.printStackTrace();
			}
		}

		return sectionInfoList;
	}
	
	/**
	  * 功能：去掉所有的<*>标记,去除html标签
	  * 
	  * @param content
	  * @return
	  */
	 public  String removeTagFromText(String content) {
	  Pattern p = null;
	  Matcher m = null;
	  String value = null;

	  // 去掉<>标签
	  p = Pattern.compile("(<[^>]*>)");
	  m = p.matcher(content);
	  String temp = content;
	  while (m.find()) {
	   value = m.group(0);
	   temp = temp.replace(value, "");
	  }
	  
	  p=Pattern.compile("(\r\n|\r|\n|\n\r)");
	  m = p.matcher(temp);
	  temp=m.replaceAll("");

//	  // 去掉换行或回车符号
//	  p = Pattern.compile("(/r+|/n+)");
//	  m = p.matcher(temp);
//	  while (m.find()) {
//	   value = m.group(0);
//	   temp = temp.replace(value, "");
//	   // System.out.println("....." + value);
//	  }
	  return temp;
	 }


	
	public static void main(String[] args){
		System.out.println(PssUtil.getPdfUrl("CN85100048","CN85100048", "19850712","19850910","1","FMSQ"));
//		List<SectionInfo> sectionInfoList = new ArrayList<SectionInfo>();
//		
//		SectionInfo info1 = new SectionInfo();
//		info1.setSectionName("111");
//		info1.setRecordNum(1);
//		sectionInfoList.add(info1);
//		SectionInfo info2 = new SectionInfo();
//		info2.setSectionName("222");
//		info2.setRecordNum(6);
//		sectionInfoList.add(info2);
//		SectionInfo info3 = new SectionInfo();
//		info3.setSectionName("333");
//		info3.setRecordNum(3);
//		sectionInfoList.add(info3);
//		SectionInfo info4 = new SectionInfo();
//		info4.setSectionName("333");
//		info4.setRecordNum(8);
//		sectionInfoList.add(info4);
//		
//		ComparatorSection comparator = new ComparatorSection();
//		Collections.sort(sectionInfoList, comparator);
//		
//		
//		for (int i = 0; i < sectionInfoList.size(); i++) {
//			SectionInfo user_temp = (SectionInfo) sectionInfoList.get(i);
//			System.out.println(user_temp.getRecordNum() + "," + user_temp.getSectionName());
//		}
	}

}
