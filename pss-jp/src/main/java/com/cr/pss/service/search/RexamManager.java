package com.cnipr.pss.service.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cnipr.pss.entity.search.Rexam;
import com.cnipr.pss.rs.dto.RexamDTO;
import com.cnipr.pss.rs.dto.RexamFSJDDTO;
import com.cnipr.pss.rs.dto.RexamFYPJDTO;
import com.cnipr.pss.rs.dto.search.RexamFSJD;
import com.cnipr.pss.rs.dto.search.RexamFYPJ;
import com.cnipr.pss.search.dao.RexamDao;
import com.cnipr.pss.util.RexamUtil;

@Component
@Transactional
public class RexamManager {

	
	@Autowired
	private RexamDao rexamDao;

	/**
	 * 获取默认.
	 */
	@Transactional(readOnly = true)
	public RexamDTO searchRexamsByAppNum(String appNum) {
		RexamDTO rexamDTO = new RexamDTO();
		List<Rexam> rexamsList =  rexamDao.searchRexamsByAppNum(appNum);
		rexamDTO.setRexamList(rexamsList);
		rexamDTO.setTotalCount(rexamsList==null?0:new Long(rexamsList.size()));
		return rexamDTO;
	}
	
	/**
	 * 根据申请号和类型查询Rexam
	 * @param appNum
	 * @param kind
	 * @return
	 */
	@Transactional(readOnly = true)
	public RexamDTO queryRexamsByAppNumKind(String appNum,String kind) {
		RexamDTO rexamDTO = new RexamDTO();
		List<Rexam> rexamsList =  rexamDao.queryRexamsByAppNumKind(appNum,kind);
		rexamDTO.setRexamList(rexamsList);
		rexamDTO.setTotalCount(rexamsList==null?0:new Long(rexamsList.size()));
		return rexamDTO;
	}
	
	/**
	 * 根据申请号获取某专利明细是否需要显示的法院判决、复审决定、无效宣告
	 * @param appNum
	 * @return
	 */
	@Transactional(readOnly = true)
	public RexamDTO queryRexamKindsByAppNum(String appNum) {
		RexamDTO rexamDTO = new RexamDTO();
		List<Rexam> rexamsList = new ArrayList<Rexam>();
		List<Object> kindsList =  rexamDao.queryRexamKindsByAppNum(appNum);
		if(kindsList!=null){
			for (Object object : kindsList) {
				Rexam rexam = new Rexam();
				rexam.setApplicationNumber(appNum);
				rexam.setKind((String)((Object[])object)[0]);
				rexam.setKindDesp(transKind2Desp((String)((Object[])object)[0]));
				rexamsList.add(rexam);
			}
			rexamDTO.setTotalCount(new Long(kindsList.size()));
		}
		else{
			rexamDTO.setTotalCount(0L);
		}
		rexamDTO.setRexamList(rexamsList);
		return rexamDTO;
	}


	/**
	 * 获取专利法院判决的详细信息
	 * @param appNum 申请号
	 * @param judgementalDate 审判日期
	 * @return
	 */
	@Transactional(readOnly = true)
	public RexamFYPJDTO queryPatFYPJDetail(String appNum,String judgementalDate) {
		RexamFYPJDTO fypjDTO = new RexamFYPJDTO();
//		基本验证
		if(appNum==null || appNum.isEmpty()){
			fypjDTO.setReturncode(2);
			fypjDTO.setMessage("申请号不能为空！");
			fypjDTO.setTotalCount(0L);
			fypjDTO.setRexamFypjList(null);
			return fypjDTO;
		}
		if(judgementalDate==null || judgementalDate.isEmpty()){
			fypjDTO.setReturncode(2);
			fypjDTO.setMessage("审判日期不能为空！");
			fypjDTO.setTotalCount(0L);
			fypjDTO.setRexamFypjList(null);
			return fypjDTO;
		}
//		获取Rexam信息
		Rexam rexam = rexamDao.queryFypjRexamByAppNumJudDate(appNum, judgementalDate);
		if(rexam==null){
			fypjDTO.setReturncode(2);
			fypjDTO.setMessage("未找到记录！");
			fypjDTO.setTotalCount(0L);
			fypjDTO.setRexamFypjList(null);
			return fypjDTO;
		}
//		法院判决文件由PATH+判决号+后缀名，目前暂时为XML和DOC两种格式
//		得到path
		String path = rexam.getSavepath();
		if(path==null || path.isEmpty()){
			fypjDTO.setReturncode(2);
			fypjDTO.setMessage("未找到法院判决文件路径！");
			fypjDTO.setTotalCount(0L);
			fypjDTO.setRexamFypjList(null);
			return fypjDTO;
		}
//		得到判决号
		String judgementNumber = rexam.getJudgementNumber();
		if(judgementNumber==null || judgementNumber.isEmpty()){
			fypjDTO.setReturncode(2);
			fypjDTO.setMessage("未找到法院判决的判决号！");
			fypjDTO.setTotalCount(0L);
			fypjDTO.setRexamFypjList(null);
			return fypjDTO;
		}
//		获取法院判决xml文件
		RexamFYPJ fypj = RexamUtil.parseFile2FYPJ(path, judgementNumber);
		if(fypj!=null){
			fypj.setInventionName(rexam.getInventionName());
			fypj.setDesignName(rexam.getDesignName());
			fypj.setInternationalClassification(rexam.getInternationalClassification());
			fypj.setClcDesigns(rexam.getClcDesigns());
			fypj.setDecisionNumber(rexam.getDecisionNumber());
			fypj.setApplicationNumber(rexam.getApplicationNumber());
			fypj.setApplicationDate(rexam.getApplicationDate());
			fypj.setSqPd(rexam.getSqPd());
			fypj.setApplicant(rexam.getApplicant());
			fypj.setPatentee(rexam.getPatentee());
			fypj.setCourtProperties(rexam.getCourtProperties());
			fypj.setTrialChamberProperty(rexam.getTrialChamberProperty());
			fypj.setJudgementNumber(rexam.getJudgementNumber());
			fypj.setJudgementDate(rexam.getJudgementDate());
			fypj.setPresidingJudge(rexam.getPresidingJudge());
			fypj.setJudge(rexam.getJudge());
			fypj.setClerk(rexam.getClerk());
		}
		List<RexamFYPJ> rexamFypjList = new ArrayList<RexamFYPJ>();
		rexamFypjList.add(fypj);
		fypjDTO.setTotalCount(1L);
		fypjDTO.setRexamFypjList(rexamFypjList);
		fypjDTO.setReturncode(1);
		return fypjDTO;
	}
	
	/**
	 * 获取复审决定的详细信息
	 * @param appNum 申请号
	 * @param decisionNumber 决定号
	 * @return
	 */
	@Transactional(readOnly = true)
	public RexamFSJDDTO queryPatFSJDDetail(String appNum,String decisionNumber) {
		RexamFSJDDTO fsjdDTO = new RexamFSJDDTO();
//		基本验证
		if(appNum==null || appNum.isEmpty()){
			fsjdDTO.setReturncode(2);
			fsjdDTO.setMessage("申请号不能为空！");
			fsjdDTO.setTotalCount(0L);
			fsjdDTO.setRexamFsjdList(null);
			return fsjdDTO;
		}
		if(decisionNumber==null || decisionNumber.isEmpty()){
			fsjdDTO.setReturncode(2);
			fsjdDTO.setMessage("决定号不能为空！");
			fsjdDTO.setTotalCount(0L);
			fsjdDTO.setRexamFsjdList(null);
			return fsjdDTO;
		}
//		获取Rexam信息
		Rexam rexam = rexamDao.queryFsjdRexamByAppNumDecisionnumber(appNum, decisionNumber);
		if(rexam==null){
			fsjdDTO.setReturncode(2);
			fsjdDTO.setMessage("未找到记录！");
			fsjdDTO.setTotalCount(0L);
			fsjdDTO.setRexamFsjdList(null);
			return fsjdDTO;
		}
//		法院判决文件由PATH+判决号+后缀名，目前暂时为XML和DOC两种格式
//		得到path
		String path = rexam.getSavepath();
		if(path==null || path.isEmpty()){
			fsjdDTO.setReturncode(2);
			fsjdDTO.setMessage("未找到复审决定文件路径！");
			fsjdDTO.setTotalCount(0L);
			fsjdDTO.setRexamFsjdList(null);
			return fsjdDTO;
		}
		
//		获取法院判决xml文件
		RexamFSJD fsjd = RexamUtil.parseFile2FSJD(path, decisionNumber);
		if(fsjd!=null){
			fsjd.setInventionName(rexam.getInventionName());
			fsjd.setDesignName(rexam.getDesignName());
			fsjd.setInternationalClassification(rexam.getInternationalClassification());
			fsjd.setClcDesigns(rexam.getClcDesigns());
			fsjd.setDecisionNumber(rexam.getDecisionNumber());
			fsjd.setApplicationNumber(rexam.getApplicationNumber());
			fsjd.setApplicationDate(rexam.getApplicationDate());
			fsjd.setSqPd(rexam.getSqPd());
			fsjd.setReviewClaimant(rexam.getReviewClaimant());
			fsjd.setInvalidClaimant(rexam.getInvalidClaimant());
			fsjd.setPatentee(rexam.getPatentee());
			fsjd.setCollegiateTeamleader(rexam.getCollegiateTeamleader());
			fsjd.setTrialMembers(rexam.getTrialMembers());
			fsjd.setParticipationAuditor(rexam.getParticipationAuditor());
			fsjd.setLegalBasis(rexam.getLegalBasis());
			fsjd.setDecisionPoint(rexam.getDecision());
		}
		List<RexamFSJD> rexamFsjdList = new ArrayList<RexamFSJD>();
		rexamFsjdList.add(fsjd);
		fsjdDTO.setTotalCount(1L);
		fsjdDTO.setRexamFsjdList(rexamFsjdList);
		fsjdDTO.setReturncode(1);
		return fsjdDTO;
	}
	
	/**
	 * 把字段kind翻译成描述
	 * @param kind
	 * @return
	 */
	public String transKind2Desp(String kind){
		if(kind==null || kind.isEmpty()){
			return "";
		}
		if(kind.toLowerCase().equals("fypj")){
			return "法院判决";
		}
		if(kind.toLowerCase().equals("fsjd")){
			return "复审决定";
		}
		if(kind.toLowerCase().equals("wxxg")){
			return "无效宣告";
		}
		return "";
	}
}
