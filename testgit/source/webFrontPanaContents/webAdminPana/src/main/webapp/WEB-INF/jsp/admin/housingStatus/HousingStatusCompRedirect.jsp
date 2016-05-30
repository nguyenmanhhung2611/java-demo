<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： 物件ステータス変更　ＤＢ更新後の完了画面へのリダイレクト画面

 2015/03/18		Trans	新規作成
 2015/08/14		Duong.Nguyen	Add CSRF token to redirecting page
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
    	document.inputForm.submit();
    }
    // -->
    </script>
	<title><c:out value="${commonParameters.adminPageTitle}"/></title>
</head>
<body onload="gotoNext()">

<form name="inputForm" method="post">
	<input type="hidden" name="housingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<c:if test="${inputForm.command == 'insert'}">
		<input type="hidden" name="command" value="iRedirect" />
	</c:if>
	<c:if test="${inputForm.command == 'update'}">
		<input type="hidden" name="command" value="uRedirect" />
	</c:if>
	<dm3token:oneTimeToken/>
</form>

</body>
</html>
