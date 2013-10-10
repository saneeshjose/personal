package com.personal.policy.web.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.personal.policy.ErrorMessage;
import com.personal.policy.Provider;
import com.personal.policy.dao.ProviderDAO;

@SuppressWarnings("serial")
public class ProviderService extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "findprovider".equalsIgnoreCase(action)) {
			System.out.println("findprovider");
			String provider_name = req.getParameter("provider_name");
			String provider_location = req.getParameter("provider_location");
			List<Provider> providers = ProviderDAO.getProviders(provider_name, provider_location);
			
			Gson gson = new Gson();
			String json = gson.toJson(providers);
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
