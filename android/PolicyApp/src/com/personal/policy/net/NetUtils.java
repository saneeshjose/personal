package com.personal.policy.net;

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
import com.google.gson.reflect.TypeToken;
import com.personal.policy.Claim;
import com.personal.policy.Policy;
import com.personal.policy.Provider;
import com.personal.policy.User;

public class NetUtils {
	
	public User getUserInfo( String userId ) throws Exception {
		Map<String,String> parameters = new HashMap<String, String>();
		
		parameters.put("userid", userId);
		parameters.put("action", "getuser");
		String json = postAuthRequest(parameters);
		
		Gson gson = new Gson();
		return gson.fromJson(json, User.class);
	}
	
	public List<Policy> getPolicies( String userId ) throws Exception {
		
		Map<String,String> parameters = new HashMap<String, String>();
		
		parameters.put("userid", userId);
		parameters.put("action", "getpolicies");
		String json = postPolicyRequest(parameters);
		
		Gson gson = new Gson();
		List<Policy> policies = gson.fromJson(json, new TypeToken<List<Policy>>(){}.getType());
		
		return policies;
	}

	public List<Claim> getClaims( String userid , String dependent ) throws Exception {
		
		Map<String,String> parameters = new HashMap<String, String>();
		
		parameters.put("userid", userid);
		parameters.put("dependent", dependent);
		parameters.put("action", "getclaims");
		String json = postPolicyRequest(parameters);
		
		Gson gson = new Gson();
		List<Claim> claims = gson.fromJson(json, new TypeToken<List<Claim>>(){}.getType());
		
		return claims;
		
	}
	
	public List<Provider> getProviders( String name, String location ) throws Exception {
		
		Map<String,String> parameters = new HashMap<String, String>();
		
		parameters.put("provider_location", location);
		parameters.put("provider_name", name);
		parameters.put("action", "findprovider");
		String json = postProviderRequest(parameters);
		
		Gson gson = new Gson();
		List<Provider> providers = gson.fromJson(json, new TypeToken<List<Provider>>(){}.getType());
		
		return providers;
	}
	
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
	
	public String postPolicyRequest( Map<String, String> parameters ) throws Exception {
		return postRequest(getServerUrl()+"/services/policy", parameters);
	}
	
	public String postProviderRequest( Map<String, String> parameters ) throws Exception {
		return postRequest(getServerUrl()+"/services/provider", parameters);
	}
	
	public Object getJSONObject( String json ) {
		
		System.out.println(json);
		Gson gson = new Gson();
		Provider p = gson.fromJson(json, Provider.class);
		System.out.println(p.getName());
		System.out.println(p.getAddress());
		System.out.println(p.getPhone());
		
		return p;
	}
	
	private String getServerUrl() {
//		return "http://10.0.2.2:8888";
		return "http://policyapp-msproject.appspot.com";
	}
}
