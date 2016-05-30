<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
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

</head>
<body onload="gotoNext()">

	<form method="post" name="inputform">

		<input type="hidden" name="command" value="redirect" />
		<dm3token:oneTimeToken/>
	</form>

</body>
</html>
