package com.shuntao.model;
import java.util.Date;

public class TctBaseBuyer {

	private int guid;
	private String corpName;

	private String legalPerson;
	private String address  ;
	private String shorpCardPath;
	private String linkman      ;
	private String tel         ;   
	private String checkPhone    ;     
	private String operatorGuid    ;   
	private Date opTime ;
	public int getGuid() {
		return guid;
	}
	public void setGuid(int guid) {
		this.guid = guid;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getShorpCardPath() {
		return shorpCardPath;
	}
	public void setShorpCardPath(String shorpCardPath) {
		this.shorpCardPath = shorpCardPath;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getCheckPhone() {
		return checkPhone;
	}
	public void setCheckPhone(String checkPhone) {
		this.checkPhone = checkPhone;
	}
	public String getOperatorGuid() {
		return operatorGuid;
	}
	public void setOperatorGuid(String operatorGuid) {
		this.operatorGuid = operatorGuid;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}   
	
	
}