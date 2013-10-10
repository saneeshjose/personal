package com.personal.policy.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.personal.policy.User;

public class DataStore {
	
	private final String SHARED_PREF_USER = "user.info";
	private final String STR_USER_ID = "user.info.id";
	private final String STR_USER_NAME = "user.info.name";
	private final String STR_USER_EMAIL = "user.info.email";
	private final String STR_USER_SUBS_ID = "user.info.subscriber";
	private final String STR_USER_SSN = "user.info.ssn";
	
	private Context context;
	
	public DataStore( Context context) {
		this.context = context;
	}
	
	public void saveUserId( String userId) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_ID, userId);
		e.commit();
	}
	
	public String getUserId () {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		return s.getString(STR_USER_ID, null);
	}
	
	public User getUserInfo() {
		
		User user = new User();
		
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		
		user.setEmail(s.getString(STR_USER_EMAIL, null));
		user.setName(s.getString(STR_USER_NAME, ""));
		user.setSsn(s.getString(STR_USER_SSN, ""));
		user.setSubscriberId(s.getString(STR_USER_SUBS_ID, ""));
		
		return user;
	}
	
	public void saveUserInfo( User user ) {
		SharedPreferences s = context.getSharedPreferences(SHARED_PREF_USER, 0);
		Editor e = s.edit();
		e.putString(STR_USER_EMAIL, user.getEmail());
		e.putString(STR_USER_NAME, user.getName());
		e.putString(STR_USER_SSN, user.getSsn());
		e.putString(STR_USER_SUBS_ID, user.getSubscriberId());
		e.commit();
	}
}
