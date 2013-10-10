package com.personal.policy.net;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.personal.policy.ErrorMessage;
import com.personal.policy.ResponseMessage;

public class PolicyJSONUtils {
	
	public static boolean isJSONError ( String json ) {
		
		Gson gson = new Gson();
		try {
			ErrorMessage e = gson.fromJson(json, ErrorMessage.class);
			if ( e== null || e.getError() == null ) return false;
			return true;
		}
		catch (JsonSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ErrorMessage parseError( String json ) {
		Gson gson = new Gson();
		ErrorMessage e = gson.fromJson(json, ErrorMessage.class);
		return e;
	}
	
	public static ResponseMessage parseResponse( String json ) {
		Gson gson = new Gson();
		ResponseMessage m = gson.fromJson(json, ResponseMessage.class);
		return m;
	}

}
