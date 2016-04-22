<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> --%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
<title>Spring Tiles Contact Form</title>
<style>
.error {
	color: #ff0000;
}

.errorblock {
	color: #000;
	background-color: #ffEEEE;
	border: 3px solid #ff0000;
	padding: 8px;
	margin: 16px;
}
</style>
</head>
<body>
	<h2>Contact Manager</h2>
		<spring:message code="NotEmpty.contact.firstname"/>
<%-- 	<form method="post" action="addContact"> --%>

<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<td><label>First Name</label></td> -->
<!-- 				<td><input name="firstname" /></td> -->
<!-- 				<td><p class="error" ></p></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>Last Name</label></td> -->
<!-- 				<td><input name="lastname" /></td> -->
<!-- 				<td><p class="error" ></p></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>Email</label></td> -->
<!-- 				<td><input name="email" /></td> -->
<!-- 				<td><p class="error" ></p></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td><label>Telephone</label></td> -->
<!-- 				<td><input name="telephone" /></td> -->
<!-- 				<td><p class="error" ></p></td> -->
<!-- 			</tr> -->
<!-- <!-- 			<tr> --> -->
<!-- <!-- 				<td><label>Birthday</label></td> --> -->
<!-- <!-- 				<td><input name="birthday" /></td> --> -->
<!-- <!-- 				<td><p class="error" ></p></td> --> -->
<!-- <!-- 			</tr> --> -->
<!-- 			<tr> -->
<!-- 				<td colspan="2"><input type="submit" value="Add Contact" /></td> -->
<!-- 			</tr> -->
<!-- 		</table> -->

<%-- 	</form> --%>


<!-- <form:form method="POST" commandName="contact" action="addContact"> -->
<!-- 		<form:errors path="*" cssClass="errorblock" element="div" /> -->
<!-- 		<table> -->
<!-- 			<tr> -->
<!-- 				<td>First Name :</td> -->
<!-- 				<td><form:input path="name" /></td> -->
<!-- 				<td><form:errors path="name" cssClass="error" /></td> -->
<!-- 			</tr> -->
			
<!-- 			<tr> -->
<!-- 				<td colspan="3"><input type="submit" /></td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
<!-- 	</form:form> -->


</body>
</html>
