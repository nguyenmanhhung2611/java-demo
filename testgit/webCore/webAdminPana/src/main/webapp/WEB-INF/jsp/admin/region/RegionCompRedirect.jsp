<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： 地域情報編集　ＤＢ更新後の完了画面へのリダイレクト画面

 2015/03/05		荊学ギョク	新規作成
 2015/08/14		Duong.Nguyen	Add CRSF token
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
	<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>

</body>
</html>
