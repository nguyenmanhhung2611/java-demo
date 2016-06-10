<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム詳細メンテナンス　ＤＢ更新後の完了画面へのリダイレクト画面

 2015/04/19		fan		新規作成
 2015/08/14		Duong.Nguyen		Add CRSF token
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
    <input type="hidden" name="sysHousingCd" value="<c:out value="${reformDtlForm.sysHousingCd}"/>">
    <input type="hidden" name="sysReformCd" value="<c:out value="${reformDtlForm.sysReformCd}"/>">
    <input type="hidden" name="housingCd" value="<c:out value="${reformDtlForm.housingCd}"/>">
    <input type="hidden" name="housingKindCd" value="<c:out value="${reformDtlForm.housingKindCd}"/>">
    <c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
    <dm3token:oneTimeToken/>
</form>
<form name="goNext" method="post">
</form>

</body>
</html>
