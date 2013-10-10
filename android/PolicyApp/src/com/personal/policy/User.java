package com.personal.policy;


public class User {
	
	private String name;
	private String email;
	private String password;
	private String subscriberId;
	private String ssn;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSsn() {
		return ssn;
	}
	
	public String getSubscriberId() {
		return subscriberId;
	}
	
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	
}
