package com.personal.policy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.personal.policy.Provider;
import com.personal.policy.dao.ProviderDAO;

@SuppressWarnings("serial")
public class ProviderManagementServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "Add Provider".equalsIgnoreCase(action)) {
			System.out.println("Add Provider");
			String provider_name = req.getParameter("provider_name");
			String provider_address = req.getParameter("provider_address");
			String provider_phone = req.getParameter("provider_phone");
			
			Provider p = new Provider();
			p.setName(provider_name);
			p.setAddress(provider_address);
			p.setPhone(provider_phone);
			
			try {
				ProviderDAO.createProvider(p);
				resp.sendRedirect("/providers.jsp");
			}
			catch(Exception e) {
				resp.getWriter().print(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
