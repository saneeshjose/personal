package com.personal.policy.web.services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.personal.policy.ErrorMessage;
import com.personal.policy.ResponseMessage;
import com.personal.policy.User;
import com.personal.policy.dao.UserDAO;

@SuppressWarnings("serial")
public class AuthService extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "signup".equalsIgnoreCase(action)) {
			System.out.println("Sign up");
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String subscriberId = req.getParameter("subscriberId");
			String ssn = req.getParameter("ssn");
			
			try {
				String userKey = signUp(fullName, email, password, subscriberId, ssn);
				System.out.println("created user " + userKey);
				
				ResponseMessage m = new ResponseMessage();
				m.setResponse(userKey);
				
				Gson gson = new Gson();
				String json = gson.toJson(m);
				resp.getWriter().print(json);
				System.out.println("Response :" + json);
			}
			catch( Exception e) {
				ErrorMessage err = new ErrorMessage();
				err.setError(e.getMessage());
				Gson gson = new Gson();
				String json = gson.toJson(err);
				resp.getWriter().print(json);
				System.out.println(err.getError());
			}
			
		}
		else if ( "signin".equalsIgnoreCase(action)) {
			System.out.println("Sign In");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			try{
				String userKey = signIn(email, password);
				ResponseMessage m = new ResponseMessage();
				m.setResponse(userKey);
				Gson gson = new Gson();
				String json = gson.toJson(m);
				resp.getWriter().print(json);
			}
			catch( Exception e) {
				ErrorMessage err = new ErrorMessage();
				err.setError(e.getMessage());
				Gson gson = new Gson();
				String json = gson.toJson(err);
				resp.getWriter().print(json);
				System.out.println(err.getError());
			}
		}
		else if ( "getuser".equalsIgnoreCase(action)) {
			System.out.println("getuser");
			String userid = req.getParameter("userid");
			User user = UserDAO.getUser(userid);
			
			Gson gson = new Gson();
			String json = gson.toJson(user);
			resp.getWriter().print(json);
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
	
	
	private String signUp( String fullName, String email, String password, String subscriberId, String ssn ) throws Exception {
		
		User user = UserDAO.getUserByEmail(email);
		if ( user == null ) throw new Exception ("User does not exists for the given email");
		
		if ( user.getPassword() !=null ) throw new Exception ("User has already registered");
		
		
		if ( fullName.equalsIgnoreCase(user.getName()) && 
				subscriberId.equalsIgnoreCase(user.getSubscriberId()) &&
				ssn.equalsIgnoreCase(user.getSsn())
				) {
			
			user.setPassword(password);
			String userKey = UserDAO.updateUser(user);
			return userKey;
		}
		else
			throw new Exception("User details are mismatching");
	}
	
	private String signIn ( String email, String password ) throws Exception {
		User user = UserDAO.getUserByEmail(email);
		
		if ( user == null ) {
			throw new Exception("Sign in failed");
		}
		
		if ( user.getPassword() == null )
			throw new Exception ("User is inactive");
		
		if ( user.getEmail().equalsIgnoreCase(email) &&  user.getPassword().equals(password)) 
			return KeyFactory.keyToString(user.getKey());
		else
			throw new Exception("Sign in failed");
	}
	
}
