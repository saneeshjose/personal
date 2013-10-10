package com.personal.policy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.appengine.api.datastore.Key;

@Entity
@Table ( name="User")
public class User {
	
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	private Key key;
	
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
		this.email = email.toLowerCase();
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Key getKey() {
		return key;
	}
	
	public void setKey(Key key) {
		this.key = key;
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
