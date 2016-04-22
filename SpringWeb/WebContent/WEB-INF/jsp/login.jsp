
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="css/style.css">

<div class="w-container">
	<div class="w-form">
		<form action="login" method="post">
			<div class="w-nav" data-collapse="medium" data-animation="default"
				data-duration="400" data-contain="1">
				<div class="w-container">
					<a class="w-nav-brand" href="#"></a>

					<div class="w-nav-button">
						<div class="w-icon-nav-menu"></div>
					</div>
				</div>
			</div>
			<h1>Login</h1>
			<label for="UserName">Username:</label> <input class="w-input"
				id="UserName" type="text" placeholder="Enter your username"
				name="UserName" required="required"> <br>
			<br> <label for="Password">Password:</label> <input
				class="w-input" id="Password" type="password"
				placeholder="Enter your password" name="Password"
				required="required"> <br>
			<br>
			<div class="w-row">
				<div class="w-col w-col-6"></div>
				<div class="w-col w-col-6">
					<input class="w-button" type="submit" value="Login"
						data-wait="Please wait...">
				</div>
			</div>
		</form>

		<c:if test="${mess != null}">
			<span style="color: red">${mess}</span>
		</c:if>

	</div>
</div>