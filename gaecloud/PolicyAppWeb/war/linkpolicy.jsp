<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Link Policy</title>
</head>
<body>

<form action="/policymanagement" method="post">
<table border="1">
	<tr>
		<td>Policy Number</td>
		<td><input type="text" name="policy_number" /></td>
	</tr>
	<tr>
		<td>Premium</td>
		<td><input type="text" name="premium" /></td>
	</tr>
	<tr>
		<td>Validity From ( MM/DD/YYYY )</td>
		<td><input type="text" name="validity_from" /></td>
	</tr>
	<tr>
		<td>Validity To ( MM/DD/YYYY )</td>
		<td><input type="text" name="validity_to" /></td>
	</tr>
	<tr>
		<td>Sum Insured</td>
		<td><input type="text" name="sum_insured" /></td>
	</tr>
	
	<tr>
		<td colspan="2">Dependents</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<input type="text" name="dependent" size="40" /><br>
			<input type="text" name="dependent" size="40" /><br>
			<input type="text" name="dependent" size="40" /><br>
		</td>
	</tr>
</table>
<input type="submit" name="action" value="Link Policy" />
<input type="hidden" name="userid" value="<%=request.getParameter("userid") %>" />
</form>
</body>
</html>