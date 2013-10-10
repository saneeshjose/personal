package com.personal.policy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.personal.policy.Provider;

@SuppressWarnings("serial")
public class WellPointWebServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		Provider p = new Provider();
		p.setName("Saneesh Joseph");
		p.setAddress("#113, Trinity Meadows, Sarjapur Road, Bangalore - 113");
		p.setPhone("+919972183811");
		
		Gson gson = new Gson();
		String json = gson.toJson(p);
		
		
		resp.getWriter().println(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
