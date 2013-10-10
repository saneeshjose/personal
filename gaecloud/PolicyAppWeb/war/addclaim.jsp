<%@page import="com.personal.policy.User"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.personal.policy.dao.PolicyDAO"%>
<%@page import="com.personal.policy.Policy"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Claim</title>
</head>
<body>

<%
String userKey = request.getParameter("userid");
Policy p = PolicyDAO.getPolicy(userKey);
String username = (String) session.getAttribute("username");
%>

<form action="/policymanagement" method="post">
<table border="1">
	<tr>
		<td>Choose a member</td>
		<td>
			<select name="dependent">
				<option value="<%=username%>"><%=username%></option>
				<%
				for ( String dependent : p.getDependents()) {
				%>
					<option value="<%=dependent%>"><%=dependent%></option>
				<%
				}
				%>
				
			</select>
		</td>
	</tr>
	<tr>
		<td>Claim Amount</td>
		<td><input type="text" name="claim_amount" /></td>
	</tr>
	<tr>
		<td>Status</td>
		<td>
			<select name="claim_status" >
				<option value="Submitted">Submitted</option>
				<option value="Approved">Approved</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>Approved Amount</td>
		<td><input type="text" name="approved_amount" /></td>
	</tr>
</table>
<input type="hidden" name="policyid" value="<%=KeyFactory.keyToString(p.getKey())%>" />
<input type="submit"  name="action" value="Add Claim" />
</form>
</body>
</html>