<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Content-Style-Type" content="text/css">
	<meta http-equiv="Content-Script-Type" content="text/javascript">
	<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
	<link rel="stylesheet" href="/cmn/css/import.css" type="text/css" media="screen,print" />

	<script type="text/javascript">
	$(document).ready(function(){
    	document.inputform.action += $("#urlPattern").val();
    	document.inputform.submit();
		});

    </script>

</head>
<body>

	<form method="post" name="inputform">

		<input type="hidden" name="command" value="redirect"/>
		<input type="hidden" id="urlPattern" name="urlPattern" value="<c:out value="${housingRequestForm.getUrlPattern()}"/>">
		<dm3token:oneTimeToken/>
	</form>

</body>
</html>
