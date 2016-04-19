<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<title>Login page login</title>
</head>
<body>
	<div align = "center"> <h1> Login Accounting</h1></div>
	<div class="large-3 large-centered columns">
		<div class= "login-box">
			<div class="row">
				<div class="large-12 columns">
					<form:form action="checklogin" method="POST" modelAttribute="user">

						<div class="row">
							<div class="large-12 columns">
								<form:input path="username" placeholder="Username" />
							</div>
						</div>
						<div class="row">
							<div class="large-12 columns">
								<form:password path="password" placeholder="Password" />
							</div>
						</div>
						<div class="row">
							<div class="large-12 large-center columns">
								<input type="button" class="button expand" value="Log In"
									id="btnSubmit" name="btnSubmit" />
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">
	$("#btnSubmit").click(function() {
		$.ajax({
			dataType : false,
			url : 'login',
			type : 'POST',
			data : {
				username : $('#username').val(),
				password : $('#password').val()
			},
			success : function(logincheck) {
				if (logincheck == "loginOK") {
					window.location.href = 'homeEmployee';
				} else {
					alert("Uername or Password Invalid !!!!");
				}
			},
			error : function() {
				alert("Ajax login error");
			}
		});
	});

</script>
</html>