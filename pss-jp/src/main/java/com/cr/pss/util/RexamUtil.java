package com.cnipr.pss.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.cnipr.pss.rs.dto.search.RexamFSJD;
import com.cnipr.pss.rs.dto.search.RexamFYPJ;

/**
 * 检索服务工具类
 * 
 * @author yzb
 * 
 */
public class RexamUtil {
	/**
	 * 服务地址
	 */
	public final static String REXAM_RES_ADDR = PssUtil
			.getProperty("rexamResource.addr");

	/**
	 * 
	 * @param path
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static RexamFYPJ parseFile2FYPJ(String path, String filename) {
		// 对path进行处理
		path = path.replaceAll("\\\\", "/");
		RexamFYPJ fypj = new RexamFYPJ();
		String filePath = REXAM_RES_ADDR + path + "/" + filename + ".xml";
		SAXReader reader = new SAXReader();
//		不使用DTD验证XML文件，因为在resource中DTD文件没有和XML文件在同一路径下
		reader.setValidation(false);
		reader.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(  
			             new ByteArrayInputStream(  
			                   "<?xml version='1.0' encoding='UTF-8'?>".getBytes() 
			                   ));
			}
		});
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(filePath);
			Document doc = reader.read(url);
			// // 获取DTD标签
			// Element root = doc.getRootElement();
			// String dtd = root.attribute("dtd-version").getStringValue();
			/** *************************************案件涉及人信息********************************************* */
			Node caseParties = (Node) doc
					.selectSingleNode("//cn-patent-verdict/cn-legal-case-parties");
			if (caseParties != null) {
				// *******************原告信息*******************
				// 【 原告】
				String yg = "";
				// 【 原告-代表人】
				String ygdb = "";
				// 【原告-法律代理人】
				String ygdl = "";
				List ygs = (List) caseParties
						.selectNodes("./cn-legal-plaintiffs/cn-legal-plaintiff");
				if (ygs != null && !ygs.isEmpty()) {
					for (Object object : ygs) {
						Element elePlaintiff = (Element) object;
						Node node1 = elePlaintiff
								.selectSingleNode("./cn-legal-litigant/addressbook/dtext");
						if (node1 != null) {
							yg += node1.getText() + "\r\n";
						}
						Node node2 = elePlaintiff
								.selectSingleNode("./cn-legal-litigant/cn-representative/addressbook/dtext");
						if (node2 != null) {
							ygdb += node2.getText() + "\r\n";
						}
						// 【原告-法律代理人】
						List legalAttorneys = (List) elePlaintiff
								.selectNodes("./cn-legal-attorneys/cn-legal-attorney");
						if (legalAttorneys != null) {
							for (Object legalAttorney : legalAttorneys) {
								Element eleLegalAttorney = (Element) legalAttorney;
								Node node3 = eleLegalAttorney
										.selectSingleNode("./addressbook/dtext");
								if (node3 != null) {
									ygdl += node3.getText() + "\r\n";
								}
							}
						}
					}
				}
				//System.out.println("【 原告】");
				//System.out.println(yg);
				//System.out.println("【 原告-代表人】");
				//System.out.println(ygdb);
				//System.out.println("【原告-法律代理人】");
				//System.out.println(ygdl);
				sb.append("【原告】").append("<br/>").append(yg).append("<br/>")
						.append("【原告-代表人】").append("<br/>").append(ygdb)
						.append("<br/>").append("【原告-法律代理人】").append("<br/>")
						.append(ygdl).append("<br/>");
				// *******************被告*******************
				// 【 被告】
				String bg = "";
				// 【 被告-代表人】
				String bgdb = "";
				// 【被告-法律代理人】
				String bgdl = "";
				List bgs = (List) caseParties
						.selectNodes("./cn-legal-defendants/cn-legal-defendant");
				if (bgs != null && !bgs.isEmpty()) {
					for (Object object : bgs) {
						Element eleDefendant = (Element) object;
						Node node1 = eleDefendant
								.selectSingleNode("./cn-legal-litigant/addressbook/dtext");
						if (node1 != null) {
							bg += node1.getText() + "\r\n";
						}
						Node node2 = eleDefendant
								.selectSingleNode("./cn-legal-litigant/cn-representative/addressbook/dtext");
						if (node2 != null) {
							bgdb += node2.getText() + "\r\n";
						}
						// 【被告-法律代理人】
						List legalAttorneys = (List) eleDefendant
								.selectNodes("./cn-legal-attorneys/cn-legal-attorney");
						if (legalAttorneys != null) {
							for (Object legalAttorney : legalAttorneys) {
								Element eleLegalAttorney = (Element) legalAttorney;
								Node node3 = eleLegalAttorney
										.selectSingleNode("./addressbook/dtext");
								if (node3 != null) {
									bgdl += node3.getText() + "\r\n";
								}
							}
						}
					}
				}
				//System.out.println("【 被告】");
				//System.out.println(bg);
				//System.out.println("【 被告-代表人】");
				//System.out.println(bgdb);
				//System.out.println("【被告-法律代理人】");
				//System.out.println(bgdl);
				sb.append("【被告】").append("<br/>").append(bg).append("<br/>")
						.append("【被告-代表人】").append("<br/>").append(bgdb)
						.append("<br/>").append("【被告-法律代理人】").append("<br/>")
						.append(bgdl).append("<br/>");
				// *******************第三人列表*******************
				// 【 第三人列表】
				String sf = "";
				// 【 第三人列表-代表人】
				String sfdb = "";
				// 【第三人列表-法律代理人】
				String sfdl = "";
				List sfs = (List) caseParties
						.selectNodes("./third-parties/cn-legal-third-party");
				if (sfs != null && !sfs.isEmpty()) {
					for (Object object : sfs) {
						Element eleThirdParty = (Element) object;
						Node node1 = eleThirdParty
								.selectSingleNode("./cn-legal-litigant/addressbook/dtext");
						if (node1 != null) {
							sf += node1.getText() + "\r\n";
						}
						Node node2 = eleThirdParty
								.selectSingleNode("./cn-legal-litigant/cn-representative/addressbook/dtext");
						if (node2 != null) {
							sfdb += node2.getText() + "\r\n";
						}
						List legalAttorneys = (List) eleThirdParty
								.selectNodes("./cn-legal-attorneys/cn-legal-attorney");
						if (legalAttorneys != null) {
							for (Object legalAttorney : legalAttorneys) {
								Element eleLegalAttorney = (Element) legalAttorney;
								Node node3 = eleLegalAttorney
										.selectSingleNode("./addressbook/dtext");
								if (node3 != null) {
									sfdl += node3.getText() + "\r\n";
								}
							}
						}
					}
				}
				//System.out.println("【 第三人列表】");
				//System.out.println(sf);
				//System.out.println("【 第三人列表-代表人】");
				//System.out.println(sfdb);
				//System.out.println("【第三人列表-法律代理人】");
				//System.out.println(sfdl);
				sb.append("【第三人】").append("<br/>").append(sf).append("<br/>")
						.append("【第三人-代表人】").append("<br/>").append(sfdb)
						.append("<br/>").append("【第三人-法律代理人】").append("<br/>")
						.append(sfdl).append("<br/>");
			}
			String caseReferPerson = sb.toString();
			fypj.setCaseReferPerson(caseReferPerson);

			/** *********************************案由************************************************* */
			sb = null;
			sb = new StringBuffer();
			Node caseIssue = (Node) doc
					.selectSingleNode("//cn-patent-verdict/cn-verdict-detail/cn-case-issue");
			if (caseIssue != null) {
				// 原告意见
				Element caseElement = (Element) caseIssue;
				for (Iterator i = caseElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append("<br/>").append(transXmlTag(element.getName()))
							.append("<br/>").append(element.getStringValue());
				}
			}
			String caseOrigin = sb.toString();
			fypj.setCaseOrigin(caseOrigin);
			/** *********************************事实认定************************************************* */
			sb = null;
			sb = new StringBuffer();
			Node fact = (Node) doc
					.selectSingleNode("//cn-patent-verdict/cn-verdict-detail/cn-cognizance-of-fact");
			if (fact != null) {
				Element factElement = (Element) fact;
				for (Iterator i = factElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append("").append(transXmlTag(element.getName()))
							.append("<br/>").append(element.getStringValue());
				}
			}
			String truthCognizance = sb.toString();
			fypj.setTruthCognizance(truthCognizance);
			/** *********************************判决意见************************************************* */
			sb = null;
			sb = new StringBuffer();
			Node verdict = (Node) doc
					.selectSingleNode("//cn-patent-verdict/cn-verdict-detail/cn-verdict-opinion");
			if (verdict != null) {
				Element verdictElement = (Element) verdict;
				for (Iterator i = verdictElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append("<br/>").append(transXmlTag(element.getName()))
							.append("<br/>").append(element.getStringValue());
				}
			}
			String judgeSuggest = sb.toString();
			fypj.setJudgeSuggest(judgeSuggest);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fypj.setWordOriginaltext(path + "/" + filename
				+ ".doc");
		return fypj;
	}

	@SuppressWarnings("unchecked")
	public static RexamFSJD parseFile2FSJD(String path, String filename) {
		// 对path进行处理
		path = path.replaceAll("\\\\", "/");
		RexamFSJD fsjd = new RexamFSJD();
		String filePath = REXAM_RES_ADDR + path + "/" + filename + ".xml";
		SAXReader reader = new SAXReader();
//		不使用DTD验证XML文件，因为在resource中DTD文件没有和XML文件在同一路径下
		reader.setValidation(false);
		reader.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId)
					throws SAXException, IOException {
				return new InputSource(  
			             new ByteArrayInputStream(  
			                   "<?xml version='1.0' encoding='UTF-8'?>".getBytes() 
			                   ));
			}
		});
		StringBuffer sb = null;
		try {
			URL url = new URL(filePath);
			Document doc = reader.read(url);

			/** *********************************案由************************************************* */
			sb = new StringBuffer();
			Node caseIssue = (Node) doc
					.selectSingleNode("//cn-appeal-decision/cn-decision-detail/cn-brief-history");
			if (caseIssue != null) {
				// 原告意见
				Element caseElement = (Element) caseIssue;
				for (Iterator i = caseElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append(transXmlTag(element.getName())).append("<br/>")
							.append(element.getStringValue());
				}
			}
			String caseOrigin = sb.toString();
			fsjd.setCaseOrigin(caseOrigin);
			/** *********************************决定理由************************************************* */
			sb = null;
			sb = new StringBuffer();
			Node fact = (Node) doc
					.selectSingleNode("//cn-appeal-decision/cn-decision-detail/cn-reasoning");
			if (fact != null) {
				Element factElement = (Element) fact;
				for (Iterator i = factElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append(transXmlTag(element.getName())).append("<br/>")
							.append(element.getStringValue());
				}
			}
			String decideReason = sb.toString();
			fsjd.setDecideReason(decideReason);
			/** *********************************决定************************************************* */
			sb = null;
			sb = new StringBuffer();
			Node verdict = (Node) doc
					.selectSingleNode("//cn-appeal-decision/cn-decision-detail/cn-holding");
			if (verdict != null) {
				Element verdictElement = (Element) verdict;
				for (Iterator i = verdictElement.elementIterator(); i.hasNext();) {
					Element element = (Element) i.next();
					//System.out.println(transXmlTag(element.getName()));
					// //System.out.println(element.getNodeType());
					//System.out.println(element.getStringValue());
					sb.append(transXmlTag(element.getName())).append("<br/>")
							.append(element.getStringValue());
				}
			}
			String finalDecision = sb.toString();
			fsjd.setFinalDecision(finalDecision);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fsjd.setWordOriginaltext(path + "/" + filename
				+ ".doc");
		return fsjd;
	}

	/**
	 * 对XML标签进行翻译
	 * 
	 * @param xmlTag
	 * @return
	 */
	private static String transXmlTag(String xmlTag) {
		if (xmlTag == null || xmlTag.isEmpty()) {
			return "";
		}
		if (xmlTag.toLowerCase().equals("cn-complainant-opinion")) {
			return "【原告意见】";
		}
		if (xmlTag.toLowerCase().equals("cn-prb-opinion")) {
			return "【复审意见】";
		}
		if (xmlTag.toLowerCase().equals("cn-appellant-opinion")) {
			return "【上诉人意见】";
		}
		if (xmlTag.toLowerCase().equals("cn-pre-verdict")) {
			return "【一审判决】";
		}
		if (xmlTag.toLowerCase().equals("cn-appellee-opinion")) {
			return "【被上诉人意见】";
		}
		if (xmlTag.toLowerCase().equals("cn-third-party-opinion")) {
			return "【第三人意见】";
		}
		if (xmlTag.toLowerCase().equals("cn-verdict-reasoning")) {
			return "【判决理由】";
		}
		if (xmlTag.toLowerCase().equals("cn-verdict-holding")) {
			return "【判决内容】";
		}
		return "";
	}

	public static void main(String[] args) {
		// RexamUtil.parseFile2FYPJ("FYPJ/5680/", "（2006）高行终字第104号");
//		RexamUtil.parseFile2FSJD("\\FSJD\\20050224\\5680", "5680");
		System.out.println("192.1213.ret".replace(".", ""));
	}
}
