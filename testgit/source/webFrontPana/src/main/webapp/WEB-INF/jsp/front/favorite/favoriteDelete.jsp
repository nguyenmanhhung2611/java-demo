<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>お気に入り削除｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main-S.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript">

$(document).ready( function() {
	$("#lnkDelete").one("click", function(){
		$.ajax({
			cache:	true,
			type:	"POST",
			url:	"<c:out value="${pageContext.request.contextPath}"/>/modal/delete/",
			data:	$("#inputForm").serialize(),
			async:	false,
			error:	 function (XMLHttpRequest, textStatus, errorThrown) {
				parent.document.inputForm.error.value = "RuntimeError";
				parent.document.inputForm.action = "";
				parent.document.inputForm.target = "_self";
				parent.document.inputForm.submit();
			},
			success: function (data) {
				parent.document.inputForm.action = "";
				parent.document.inputForm.target = "_self";
				parent.document.inputForm.submit();
			}
		});
	});
});

</script>
</head>

<body id="modal">

<div class="modalBlock">
	<p class="name"><c:out value="${param.displayHousingName}"/></p>
	<p class="title center">削除しますか？</p>
	<div class="btnBlock">
		<p class="btnBlack01"><a id="lnkDelete" href="javascript:void(0);">はい</a></p>
		<p class="btnGray01"><a href="javascript:parent.jQuery.fancybox.close();">いいえ</a></p>
	</div>
</div>
<form id="inputForm" name="inputForm" method="post">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${param.sysHousingCd}"/>">
	<dm3token:oneTimeToken/>
</form>

</body>
</html>
