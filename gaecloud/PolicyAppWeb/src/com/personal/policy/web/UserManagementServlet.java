package com.personal.policy.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.personal.policy.User;
import com.personal.policy.dao.UserDAO;

@SuppressWarnings("serial")
public class UserManagementServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "Add Subscriber".equalsIgnoreCase(action)) {
			System.out.println("Add Subscriber");
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String subscriberId = req.getParameter("subscriberid");
			String ssn = req.getParameter("ssn");
			
			try {
				String userKey = createUser( fullName, email, subscriberId, ssn);
				System.out.println("created user " + userKey);
				resp.sendRedirect("/users.jsp");
			}
			catch( Exception e) {
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
	
	
	private String createUser( String fullName, String email, String subscriberId, String ssn ) throws Exception {
		
		User user = new User();
		user.setName(fullName);
		user.setEmail(email);
		user.setSubscriberId(subscriberId);
		user.setPassword(null);
		user.setSsn(ssn);
		
		String userKey = UserDAO.createUser(user);
		return userKey;
	}
}
