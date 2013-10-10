package com.personal.policy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Policy {
	
	private String holderName;
	private String policyNumber;
	private double premium;
	private Date validityStart;
	private Date validityEnd;
	private double sumInsured;
	
	private List<String> dependents;
	
	public Policy() {
		dependents = new ArrayList<String>();
	}
	
	public double getSumInsured() {
		return sumInsured;
	}
	
	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}
	
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public double getPremium() {
		return premium;
	}
	public void setPremium(double premium) {
		this.premium = premium;
	}
	public Date getValidityStart() {
		return validityStart;
	}
	public void setValidityStart(Date validityStart) {
		this.validityStart = validityStart;
	}
	public Date getValidityEnd() {
		return validityEnd;
	}
	public void setValidityEnd(Date validityEnd) {
		this.validityEnd = validityEnd;
	}
	
	public List<String> getDependents() {
		return dependents;
	}
	

}
