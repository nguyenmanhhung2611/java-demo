<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>TTV - Login Form</title>
  <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />" />
  <c:set var="projectName" value="/hibernate" />

</head>
<body>
  <div class="w-container">
    <div class="w-form">
      <form id="wf-form-Login-Form" name="wf-form-Login-Form" data-name="Login Form" data-redirect="http://orderproduct.webflow.com/product-list" action="login" method="post">
        <div class="w-nav" data-collapse="medium" data-animation="default" data-duration="400" data-contain="1">
          <div class="w-container">
            <a class="w-nav-brand" href="#"></a>
            <nav class="w-nav-menu" role="navigation"><a class="w-nav-link" href="${projectName }">Home</a>
            </nav>
            <div class="w-nav-button">
              <div class="w-icon-nav-menu"></div>
            </div>
          </div>
        </div>
        <h1>Login</h1>
        <label for="UserName">Username:</label>
        <input class="w-input" id="UserName" type="text" placeholder="Enter your username" name="UserName" data-name="UserName" required="required">
        <label for="Password">Password:</label>
        <input class="w-input" id="Password" type="password" placeholder="Enter your password" name="Password" data-name="Password" required="required">
        <div class="w-row">
          <div class="w-col w-col-6"></div>
          <div class="w-col w-col-6">
            <input class="w-button" type="submit" value="Login" data-wait="Please wait...">
          </div>
        </div>
      </form>
      <div class="w-form-done">
        <p>Thank you! Your submission has been received!</p>
      </div>
      <div class="w-form-fail">
        <p>Oops! Something went wrong while submitting the form :(</p>
      </div>
    </div>
  </div>
  
</body>
</html>