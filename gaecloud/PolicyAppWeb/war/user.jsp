<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.personal.policy.Policy"%>
<%@page import="com.personal.policy.dao.PolicyDAO"%>
<%@page import="com.personal.policy.User"%>
<%@page import="com.personal.policy.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Details</title>
</head>
<body>

<%
	String userKey = request.getParameter("userid");
	User user = UserDAO.getUser(userKey);
	session.setAttribute("username", user.getName());
%>

	<table border="1">
		<tr>
			<td>Full Name</td>
			<td><%=user.getName() %></td>
		</tr>
		<tr>
			<td>E-Mail</td>
			<td><%=user.getEmail() %></td>
		</tr>
		<tr>
			<td>Subscriber ID</td>
			<td><%=user.getSubscriberId() %></td>
		</tr>
		<tr>
			<td>SSN</td>
			<td><%=user.getSsn() %></td>
		</tr>
	</table>
	
	<a href="/linkpolicy.jsp?userid=<%=userKey%>">Link Policy</a>
	
<%
	Policy p = PolicyDAO.getPolicy(userKey);
	if ( p !=null )  {
%>

	<table border="1">
		<tr>
			<td>Policy Number</td>
			<td><%=p.getPolicyNumber() %></td>
		</tr>
		<tr>
			<td>Premium</td>
			<td><%=p.getPremium() %></td>
		</tr>
		<tr>
			<td>Sum Insured</td>
			<td><%=p.getSumInsured() %></td>
		</tr>
		<tr>
			<td>Validity</td>
			<td><%=p.getValidityStart() %> to <%=p.getValidityEnd() %></td>
		</tr>
		<tr>
			<td>Dependents</td>
			<td>
			<% for ( String d : p.getDependents()) { %>
				<%=d%><br>
			<%}	%>
			</td>
	</table>
	<a href="/addclaim.jsp?userid=<%=userKey%>&policyid=<%=KeyFactory.keyToString(p.getKey())%>">Add Claim</a>
<%} %>
</body>
</html>