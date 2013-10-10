package com.personal.policy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

@Entity
@Table ( name="Policy")
public class Policy {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private Key key;
	
	private String userId;
	
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
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
