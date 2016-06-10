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

<title>新規会員登録　完了｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>mypage/css/mypage.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>

<!--#include virtual="/common/ssi/simple-header-D.html"-->

<div id="contents">
	<div id="contentsInner" class="mypageMember">
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">新規会員登録</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step03 mb30 spMb15">
				<ul>
					<li><span>入力</span></li>
					<li><span>確認</span></li>
					<li class="current"><span>完了</span></li>
				</ul>
			</nav>
			<div class="bgBlock01">
				<h2>新規会員登録が完了しました。</h2>
				<p class="lead">ご登録ありがとうございます。<br>ご登録いただいたメールアドレスにメールを送信しましたのでご確認ください。</p>
				
				<div class="requestBlock">
					<p class="text">併せて「物件リクエスト」はいかがですか？<br>
					ご希望の所在地、条件に沿った物件が出てきた際にいち早くお伝えいたします。会員登録と併せて是非ご登録ください。</p>
					<p class="btn"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/request/input/" class="secondaryBtn">物件リクエストへ</a></p>
				</div>
			</div>
			<div class="contentsInner01">
				<p class="center spPb10"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/" class="secondaryBtn">マイページへ</a></p>
			</div>
		</div>
	</div>
</div>
<!--#include virtual="/common/ssi/simple-footer-S.html"-->
<img src="<c:out value="${janetUrl}"/>" width="1" height="1">
<!-- Yahoo Code for your Conversion Page -->
<img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=PjVmCI-nzmMQze_kvQM&guid=ON&script=0"/>
</body>
</html>
