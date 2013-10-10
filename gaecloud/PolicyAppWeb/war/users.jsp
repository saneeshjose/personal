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
<title>Users</title>
</head>
<body>

<form action="/usermanagement" method="post">
	<table>
		<tr>
			<td>Full Name</td>
			<td><input type="text" name="fullname" /> </td>
		</tr>
		<tr>
			<td>E-Mail</td>
			<td><input type="text" name="email" /> </td>
		</tr>
		<tr>
			<td>Subscriber ID</td>
			<td><input type="text" name="subscriberid" /> </td>
		</tr>
		<tr>
			<td>SSN</td>
			<td><input type="text" name="ssn" /> </td>
		</tr>
	</table>
	<input type="submit" name="action" value="Add Subscriber" />
</form> 

<table border="1">

	<tr>
		<th>Full Name</th>
		<th>E-Mail</th>
	</tr>

<%
	List<User> users = UserDAO.getUsers();

	for ( User user : users ) {
%>
	<tr>
		<td><a href="/user.jsp?userid=<%=KeyFactory.keyToString(user.getKey())%>" ><%=user.getName() %></td>
		<td><%=user.getEmail() %></td>
	</tr>
<%
	}
%>
</table>

</body>
</html>