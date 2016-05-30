<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <body>
    <h1>テスト用ログイン画面</h1>
    <hr width="80%" size="1"/>
    <c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" />
    <p>Please enter your login ID and password below to continue.</p>
    <form action="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/check" method="post">
    <input type="hidden" name="redirectURL" value="<c:out value="${redirectURL}"/>"/>
    <table border="0">
      <tr>
        <td>Login ID:</td>
        <td><input type="text" name="loginID" value="<c:out value="${loginForm.loginID}"/>"/></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type="password" name="password" value="<c:out value="${loginForm.password}"/>"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="checkbox" name="autoLogin" value="1"<c:if test="${loginForm.autoLogin == '1'}"> checked</c:if>"/>
          Automatically login with this user name and password next time
        </td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="Login"/>
        </td>
      </tr>
    </table>
    </form>
    <hr width="80%" size="1"/>
    &copy; Transcosmos 2006
  </body>
</html>
