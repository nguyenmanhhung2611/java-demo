<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： a page that redirect back to housing browse screen

 2015/08/18     Duong.Nguyen		Create new file
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

<form name="inputform" method="post" action="<c:out value="${pageContext.request.contextPath}/top/housing/detail/${inputForm.sysHousingCd}"/>">
	<input type="hidden" name="command" value="redirect" />
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>
</body>
</html>
