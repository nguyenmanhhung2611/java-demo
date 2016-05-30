<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 会員情報編集　ＤＢ更新後の完了画面へのリダイレクト画面

 2015/04/29		zhang	新規作成
 2015/10/16     Thi Tran    Embed affiliate image
---------------------------------------------------------------- --%>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<link rel="stylesheet" href="/cmn/css/import.css" type="text/css" media="screen,print" />

	<script type="text/javascript">
	<!--
    function gotoNext() {
    	document.inputform.submit();
    }
    // -->
    </script>
	<title><c:out value="${commonParameters.adminPageTitle}"/></title>
</head>
<body onload="gotoNext()">

<form name="inputform" method="post" action="./../login/">
	<input type="hidden" name="command" value="redirect" />
	<input type="hidden" name="loginID" value="<c:out value="${inputForm.email}"/>"/>
	<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>"/>
	<dm3token:oneTimeToken/>
	<c:if test="${!empty redirectURL}">
	    <input type="hidden" name="redirectURL" value="<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/?redirectKey=<c:out value="${inputForm.redirectKey}"/>"/>
	</c:if>
	<c:if test="${!empty inputForm.redirectKey}">
	    <input type="hidden" name="redirectURL" value="<c:out value="${inputForm.redirectKey}"/>"/>
	</c:if>
	<c:if test="${empty redirectURL and empty inputForm.redirectKey}">
	    <input type="hidden" name="redirectURL" value="<c:out value="${pageContext.request.contextPath}"/>/account/member/new/comp/"/>
	</c:if>
</form>
</body>
</html>
