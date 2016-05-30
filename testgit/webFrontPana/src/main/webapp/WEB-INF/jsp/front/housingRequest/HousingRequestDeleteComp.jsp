<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>物件リクエスト削除　完了｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=REQ-01-10" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">物件リクエスト</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
				<p class="bold mb30 spMb10 f18 spF16 center spLeft">物件リクエストの削除が完了しました。</p>
				<p class="f14 center spLeft">ご登録いただいたメールアドレスにメールを送信しましたのでご確認ください。<br>削除した内容が反映されるまでにお時間を要します。<br>リクエストした内容に基づく情報を担当者からご連絡する場合がございますが、ご容赦ください。</p>
			</div>
			<div class="contentsInner01">
				<p class="center spPb10"><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/" class="secondaryBtn">マイページトップへ</a></p>
			</div>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
