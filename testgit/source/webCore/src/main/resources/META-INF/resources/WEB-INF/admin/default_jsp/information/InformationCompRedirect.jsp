<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： お知らせメンテナンス　ＤＢ更新後の完了画面へのリダイレクト画面

 2015/02/10		I.Shu	新規作成
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

<form name="inputform" method="post">
	<input type="hidden" name="command" value="redirect" />
	<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />
	<input type="hidden" name="informationNo" value="<c:out value="${inputForm.informationNo}"/>">
	<input type="hidden" name="title" value="<c:out value="${inputForm.title}"/>">
	<dm3token:oneTimeToken/>
</form>
<form name="goNext" method="post">
</form>

</body>
</html>
