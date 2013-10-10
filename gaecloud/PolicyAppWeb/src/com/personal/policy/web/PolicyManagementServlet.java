package com.personal.policy.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.personal.policy.Claim;
import com.personal.policy.Policy;
import com.personal.policy.dao.PolicyDAO;

@SuppressWarnings("serial")
public class PolicyManagementServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String action = req.getParameter("action");
		System.out.println("Action : " + action);
		
		resp.setContentType("text/plain");
		
		if ( "Link Policy".equalsIgnoreCase(action)) {
			System.out.println("Link Policy");
			String policyNumber = req.getParameter("policy_number");
			String premium = req.getParameter("premium");
			String validity_from = req.getParameter("validity_from");
			String validity_to = req.getParameter("validity_to");
			String sum_insured = req.getParameter("sum_insured");
			String userId = req.getParameter("userid");
			
			String [] dependents = req.getParameterValues("dependent");
			
			Policy p = new Policy();
			p.setPolicyNumber(policyNumber);
			p.setPremium(Double.parseDouble(premium));
			p.setSumInsured(Double.parseDouble(sum_insured));
			p.setUserId(userId);
			try {
				p.setValidityStart(new SimpleDateFormat("MM/dd/yyyy").parse(validity_from));
				p.setValidityEnd( new SimpleDateFormat("MM/dd/yyyy").parse(validity_to));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			for ( String dependent : dependents ) {
				
				if ( dependent==null || dependent.trim().equals("")) continue;
				
				p.getDependents().add(dependent);
			}
			
			try {
				String policyKey = PolicyDAO.createPolicy(p);
				System.out.println("Policy " +policyKey+" created");
				resp.sendRedirect("/users.jsp");
			}
			catch(Exception e) {
				resp.getWriter().print(e.getMessage());
				e.printStackTrace();
			}
		}
		else if ( "Add Claim".equalsIgnoreCase(action)) {
			String dependent = req.getParameter("dependent");
			String claim_amount= req.getParameter("claim_amount");
			String claim_status = req.getParameter("claim_status");
			String approved_amount = req.getParameter("approved_amount");
			String policyid = req.getParameter("policyid");
			
			Claim c = new Claim();
			c.setDependantName(dependent);
			c.setAmount(Double.parseDouble( claim_amount) );
			c.setClaimStatus(claim_status);
			c.setApprovedAmount(Double.parseDouble(approved_amount));
			c.setClaimDate( new Date());
			c.setPolicyId(policyid);
			try {
				PolicyDAO.createClaim(c);
				resp.sendRedirect("/users.jsp");
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
