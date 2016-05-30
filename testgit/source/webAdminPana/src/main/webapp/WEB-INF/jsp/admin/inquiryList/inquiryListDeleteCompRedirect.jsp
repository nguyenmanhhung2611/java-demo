<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称：a page that redirect back to inquiry list screen

 2015/08/20     Vinh.Ly Add CSRF Token for Inquiry Delete
---------------------------------------------------------------- --%>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link rel="stylesheet" href="/cmn/css/import.css" type="text/css"
	media="screen,print" />

<script type="text/javascript">
<!--
	function gotoNext() {
		document.inputform.submit();
	}
// -->
</script>
<title><c:out value="${commonParameters.adminPageTitle}" /></title>
</head>
<body onload="gotoNext()">

	<form name="inputform" method="post" action="<c:out value="${pageContext.request.contextPath}/top/inquiry/list/"/>">
		<input type="hidden" name="command" value="list" />
		<input type="hidden" name="searchCommand" value="list" />
		<c:import
			url="/WEB-INF/jsp/admin/include/inquiryList/searchParams.jsh" />
		<input type="hidden" name="inquiryId"
			value="<c:out value="${inputForm.inquiryId}"/>">
		<dm3token:oneTimeToken/>
	</form>

</body>
</html>
