package com.sj.cloudtodo.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.sj.cloudtodo.model.User;

public class NetUtils {
	
	public String postRequest( String endpoint,  Map<String, String> parameters ) throws Exception{
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost( endpoint );
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		for ( String p : parameters.keySet() ) {
			params.add( new BasicNameValuePair(p, parameters.get(p)));
		}
		post.setEntity(new UrlEncodedFormEntity(params));
		
		HttpResponse res = client.execute(post);
		
		BufferedReader reader = new BufferedReader( new InputStreamReader(res.getEntity().getContent()));
		String line = null;
		String response="";
		
		while ( (line = reader.readLine())!=null) {
			response+=line;
		}
		
		reader.close();
		
		client.getConnectionManager().shutdown();
		
		return response;
	}
	
	public String postAuthRequest( Map<String, String> parameters ) throws Exception {
		return postRequest(getServerUrl()+"/services/auth", parameters);
	}
	
	private String getServerUrl() {
		return "http://10.0.2.2:8888";
//		return "http://policyapp-msproject.appspot.com";
	}
	
	public User getUserInfo( String userId ) throws Exception {
		Map<String,String> parameters = new HashMap<String, String>();
		
		parameters.put("userid", userId);
		parameters.put("action", "getuser");
		String json = postAuthRequest(parameters);
		
		Gson gson = new Gson();
		return gson.fromJson(json, User.class);
	}

}
