<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Test Show List</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>UserId</th>
			<th>PreLogin</th>
			<th>AfterLogin</th>
			<th>option</th>
		</tr>
		<c:forEach var="index" items="${listUser}" >
			<tr>
				<td>${index.userId}</td>
				<td>${index.preLogin}</td>
				<td>${index.lastLogin}</td>
				<td>${index.insDate}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>