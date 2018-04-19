package com.cnipr.pss.entity.search;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cnipr.pss.entity.PssEntity;

@Entity
@Table(name = "MET_TB_REXAM")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rexam extends PssEntity {

	private String appId;
	/**
	 *  申请号
	 */
	private String applicationNumber;
	/**
	 * 申请日
	 */
	private String applicationDate;
	/**
	 * 决定号
	 */
	private String decisionNumber;
	/**
	 * 决定日
	 */
	private String decisionDate;
	/**
	 * 判决号
	 */
	private String judgementNumber;
	/**
	 * 判决日
	 */
	private String judgementDate;
	private String inventionName;
	private String designName;
	/**
	 * 申请人
	 */
	private String applicant;
	/**
	 * 专利权人
	 */
	private String patentee;
	/**
	 * 授权公告日/审定公告日
	 */
	private String sqPd;
	private String examinationAnnouncement;
	private String internationalClassification;
	/**
	 * 外观设计分类号
	 */
	private String clcDesigns;
	/**
	 * 复审请求人
	 */
	private String reviewClaimant;
	/**
	 * 无效请求人
	 */
	private String invalidClaimant;
	/**
	 * 合议组组长
	 */
	private String collegiateTeamleader;
	/**
	 * 主审员
	 */
	private String trialMembers;
	/**
	 * 参审员
	 */
	private String participationAuditor;
	/**
	 * 法律依据
	 */
	private String legalBasis;
	/**
	 * 决定要点
	 */
	private String decision;
	/**
	 * 法院属性
	 */
	private String courtProperties;
	/**
	 * 审判厅属性
	 */
	private String trialChamberProperty;
	/**
	 * 审判长
	 */
	private String presidingJudge;
	/**
	 * 审判员
	 */
	private String judge;
	/**
	 * 书记员
	 */
	private String clerk;
	private String savepath;
	/**
	 * FYPJ
	 * FSJD
	 * WXXG
	 */
	private String kind;
	/**
	 * FYPJ-法院判决
	 * FSJD-复审决定
	 * WXXG-无效宣告
	 */
	private String kindDesp;
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * @return the applicationNumber
	 */
	public String getApplicationNumber() {
		return applicationNumber;
	}
	/**
	 * @param applicationNumber the applicationNumber to set
	 */
	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}
	/**
	 * @return the applicationDate
	 */
	public String getApplicationDate() {
		return applicationDate;
	}
	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	/**
	 * @return the decisionNumber
	 */
	public String getDecisionNumber() {
		return decisionNumber;
	}
	/**
	 * @param decisionNumber the decisionNumber to set
	 */
	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}
	/**
	 * @return the decision_date
	 */
	public String getDecisionDate() {
		return decisionDate;
	}
	/**
	 * @param decision_date the decision_date to set
	 */
	public void setDecisionDate(String decisionDate) {
		this.decisionDate = decisionDate;
	}
	/**
	 * @return the judgementNumber
	 */
	public String getJudgementNumber() {
		return judgementNumber;
	}
	/**
	 * @param judgementNumber the judgementNumber to set
	 */
	public void setJudgementNumber(String judgementNumber) {
		this.judgementNumber = judgementNumber;
	}
	/**
	 * @return the judgementDate
	 */
	public String getJudgementDate() {
		return judgementDate;
	}
	/**
	 * @param judgementDate the judgementDate to set
	 */
	public void setJudgementDate(String judgementDate) {
		this.judgementDate = judgementDate;
	}
	/**
	 * @return the inventionName
	 */
	public String getInventionName() {
		return inventionName;
	}
	/**
	 * @param inventionName the inventionName to set
	 */
	public void setInventionName(String inventionName) {
		this.inventionName = inventionName;
	}
	/**
	 * @return the designName
	 */
	public String getDesignName() {
		return designName;
	}
	/**
	 * @param designName the designName to set
	 */
	public void setDesignName(String designName) {
		this.designName = designName;
	}
	/**
	 * @return the applicant
	 */
	public String getApplicant() {
		return applicant;
	}
	/**
	 * @param applicant the applicant to set
	 */
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	/**
	 * @return the patentee
	 */
	public String getPatentee() {
		return patentee;
	}
	/**
	 * @param patentee the patentee to set
	 */
	public void setPatentee(String patentee) {
		this.patentee = patentee;
	}
	/**
	 * @return the sqPd
	 */
	public String getSqPd() {
		return sqPd;
	}
	/**
	 * @param sqPd the sqPd to set
	 */
	public void setSqPd(String sqPd) {
		this.sqPd = sqPd;
	}
	/**
	 * @return the examinationAnnouncement
	 */
	public String getExaminationAnnouncement() {
		return examinationAnnouncement;
	}
	/**
	 * @param examinationAnnouncement the examinationAnnouncement to set
	 */
	public void setExaminationAnnouncement(String examinationAnnouncement) {
		this.examinationAnnouncement = examinationAnnouncement;
	}
	/**
	 * @return the internationalClassification
	 */
	public String getInternationalClassification() {
		return internationalClassification;
	}
	/**
	 * @param internationalClassification the internationalClassification to set
	 */
	public void setInternationalClassification(String internationalClassification) {
		this.internationalClassification = internationalClassification;
	}
	/**
	 * @return the clcDesigns
	 */
	public String getClcDesigns() {
		return clcDesigns;
	}
	/**
	 * @param clcDesigns the clcDesigns to set
	 */
	public void setClcDesigns(String clcDesigns) {
		this.clcDesigns = clcDesigns;
	}
	/**
	 * @return the reviewClaimant
	 */
	public String getReviewClaimant() {
		return reviewClaimant;
	}
	/**
	 * @param reviewClaimant the reviewClaimant to set
	 */
	public void setReviewClaimant(String reviewClaimant) {
		this.reviewClaimant = reviewClaimant;
	}
	/**
	 * @return the invalidClaimant
	 */
	public String getInvalidClaimant() {
		return invalidClaimant;
	}
	/**
	 * @param invalidClaimant the invalidClaimant to set
	 */
	public void setInvalidClaimant(String invalidClaimant) {
		this.invalidClaimant = invalidClaimant;
	}
	/**
	 * @return the collegiateTeamleader
	 */
	public String getCollegiateTeamleader() {
		return collegiateTeamleader;
	}
	/**
	 * @param collegiateTeamleader the collegiateTeamleader to set
	 */
	public void setCollegiateTeamleader(String collegiateTeamleader) {
		this.collegiateTeamleader = collegiateTeamleader;
	}
	/**
	 * @return the trialMembers
	 */
	public String getTrialMembers() {
		return trialMembers;
	}
	/**
	 * @param trialMembers the trialMembers to set
	 */
	public void setTrialMembers(String trialMembers) {
		this.trialMembers = trialMembers;
	}
	/**
	 * @return the participationAuditor
	 */
	public String getParticipationAuditor() {
		return participationAuditor;
	}
	/**
	 * @param participationAuditor the participationAuditor to set
	 */
	public void setParticipationAuditor(String participationAuditor) {
		this.participationAuditor = participationAuditor;
	}
	/**
	 * @return the legalBasis
	 */
	public String getLegalBasis() {
		return legalBasis;
	}
	/**
	 * @param legalBasis the legalBasis to set
	 */
	public void setLegalBasis(String legalBasis) {
		this.legalBasis = legalBasis;
	}
	/**
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}
	/**
	 * @return the courtProperties
	 */
	public String getCourtProperties() {
		return courtProperties;
	}
	/**
	 * @param courtProperties the courtProperties to set
	 */
	public void setCourtProperties(String courtProperties) {
		this.courtProperties = courtProperties;
	}
	/**
	 * @return the trialChamberProperty
	 */
	public String getTrialChamberProperty() {
		return trialChamberProperty;
	}
	/**
	 * @param trialChamberProperty the trialChamberProperty to set
	 */
	public void setTrialChamberProperty(String trialChamberProperty) {
		this.trialChamberProperty = trialChamberProperty;
	}
	/**
	 * @return the presidingJudge
	 */
	public String getPresidingJudge() {
		return presidingJudge;
	}
	/**
	 * @param presidingJudge the presidingJudge to set
	 */
	public void setPresidingJudge(String presidingJudge) {
		this.presidingJudge = presidingJudge;
	}
	/**
	 * @return the judge
	 */
	public String getJudge() {
		return judge;
	}
	/**
	 * @param judge the judge to set
	 */
	public void setJudge(String judge) {
		this.judge = judge;
	}
	/**
	 * @return the clerk
	 */
	public String getClerk() {
		return clerk;
	}
	/**
	 * @param clerk the clerk to set
	 */
	public void setClerk(String clerk) {
		this.clerk = clerk;
	}
	/**
	 * @return the savepath
	 */
	public String getSavepath() {
		return savepath;
	}
	/**
	 * @param savepath the savepath to set
	 */
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}
	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}
	/**
	 * @return the kindDesp
	 */
	@Transient
	public String getKindDesp() {
		return kindDesp;
	}
	/**
	 * @param kindDesp the kindDesp to set
	 */
	public void setKindDesp(String kindDesp) {
		this.kindDesp = kindDesp;
	}

}
