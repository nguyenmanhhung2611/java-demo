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

<title>パスワードの再設定　完了｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.ah-placeholder.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->


</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--[if lte IE 9]><script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->
<!--#include virtual="/common/ssi/simple-header-D.html"-->

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=MYP-03-02" />

		<div class="section01">
			<div class="headingBlockA01 mb40 spMb15 clearfix">
				<h1 class="ttl">パスワードの再設定</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
				<p class="bold mb30 spMb10 f18 spF16 center spLeft">新しいパスワードを設定しました。</p>
				<p class="f14 center spLeft">ご登録いただいたメールアドレスにメールを送信しましたのでご確認ください。</p>
			</div>
			<div class="contentsInner01">
				<p class="center spPb10"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/" class="secondaryBtn">ログイン画面へ</a></p>
			</div>
		</div>
	</div>
</div>



<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>