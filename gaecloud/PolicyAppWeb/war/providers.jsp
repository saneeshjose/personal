<%@page import="com.personal.policy.dao.ProviderDAO"%>
<%@page import="com.personal.policy.Provider"%>
<%@page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@page import="com.personal.policy.User"%>
<%@page import="java.util.List"%>
<%@page import="com.personal.policy.dao.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Providers</title>
</head>
<body>

<form action="/providermanagement" method="post">
	<table>
		<tr>
			<td>Provider Name</td>
			<td><input type="text" name="provider_name" /> </td>
		</tr>
		<tr>
			<td>Address</td>
			<td><input type="text" name="provider_address" /> </td>
		</tr>
		<tr>
			<td>Phone</td>
			<td><input type="text" name="provider_phone" /> </td>
		</tr>
	</table>
	<input type="submit" name="action" value="Add Provider" />
</form> 

<table border="1">

	<tr>
		<th>Provider Name</th>
		<th>Address</th>
		<th>Phone</th>
	</tr>

<%
	List<Provider> providers = ProviderDAO.getProviders();

	for ( Provider p : providers ) {
%>
	<tr>
		<td><%=p.getName() %></td>
		<td><%=p.getAddress() %></td>
		<td><%=p.getPhone() %></td>
	</tr>
<%
	}
%>
</table>

</body>
</html>