<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>パスワードの再設定｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.ah-placeholder.js"></script>

<script type ="text/javascript">
	function linkToUrl(url) {
		document.inputForm.action=url;
		document.inputForm.submit();
	}
</script>


</head>
<body>

<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
	
<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
<div id="ptop"></div>
<!--#include virtual="/common/ssi/simple-header-D.html"-->
<div id="contents">
	<div id="contentsInner">
		<c:import url="/WEB-INF/jsp/front/include/common/topicPath.jsh?pattern=MYP-02-01" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">パスワードの再設定</h1>
			</div><!-- /.headingBlockA01 -->

			<div class="contentsInner01">
				<p class="f14">有効期限が切れています。</p>
			</div>
			<form action="comfirm" method="post" name="inputForm">
				<p class="center spPb10"><button type="button" class="primaryBtn01" onclick="linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/account/pw/forgot/input/');">パスワードリマインダーへ</button></p>
			</form>
		</div>
	</div>
</div>
<!--#include virtual="/common/ssi/simple-footer-S.html"-->


</body>