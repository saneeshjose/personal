package com.personal.policy.web.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.personal.policy.Claim;
import com.personal.policy.ErrorMessage;
import com.personal.policy.Policy;
import com.personal.policy.dao.PolicyDAO;

@SuppressWarnings("serial")
public class PolicyService extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "getpolicies".equalsIgnoreCase(action)) {
			System.out.println("getpolicies");
			String userid = req.getParameter("userid");
			Policy p = PolicyDAO.getPolicy(userid);
			
			List<Policy> policies = new ArrayList<Policy>();
			policies.add(p);
			
			Gson gson = new Gson();
			String json = gson.toJson(policies);
			resp.getWriter().print(json);
			System.out.println("Response :" + json);
		}
		else if ( "getclaims".equalsIgnoreCase(action)) {
			System.out.println("getclaims");
			String userid = req.getParameter("userid");
			String dependent = req.getParameter("dependent");
			Policy p = PolicyDAO.getPolicy(userid);
			String policyKey = KeyFactory.keyToString(p.getKey());
			
			List<Claim> claims = PolicyDAO.getClaimsForDependant(policyKey,dependent);
			
			Gson gson = new Gson();
			String json = gson.toJson(claims);
			resp.getWriter().print(json);
			System.out.println("Response :" + json);
		}
		else {
			ErrorMessage e = new ErrorMessage();
			e.setError("required parameter 'action'");
			Gson gson = new Gson();
			String json = gson.toJson(e);
			resp.getWriter().print(json);
			System.out.println(e.getError());
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
