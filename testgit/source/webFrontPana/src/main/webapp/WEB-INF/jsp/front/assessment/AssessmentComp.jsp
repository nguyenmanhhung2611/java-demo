<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>査定のお申し込み　完了｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" type="text/css" media="screen,print" />

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>

<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />

<!--#include virtual="/common/ssi/simple-header-D.html"-->
<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">査定のお申し込み</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step03">
				<ul>
					<li><span>入力</span></li>
					<li><span>確認</span></li>
					<li class="current"><span>完了</span></li>
				</ul>
			</nav>
			<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
				<p class="bold mb30 spMb10 f18 spF16 center spLeft">査定のお申し込みが完了しました。</p>
				<p class="f14 center spLeft">ご登録いただいたメールアドレスにメールを送信しましたのでご確認ください。<br>お申し込み内容を確認の上、不動産会社担当者よりご連絡させていただきます。</p>
			</div>
			<div class="contentsInner01 spPb10">
				<p class="center"><a href="<c:out value='${commonParameters.resourceRootUrl}'/>sell/" class="secondaryBtn">トップページへ</a></p>
			</div>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>
</html>