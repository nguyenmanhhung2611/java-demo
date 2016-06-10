<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>物件のお問い合わせ｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>buy/css/building.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.heightLine.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript">
$(function(){
	$(".column2Wrap01>div").heightLine({minWidth: 640});
});

//function linkToUrl(url, sysHousingCd) {
//	document.inputForm.action = url;
//	document.inputForm.sysHousingCd.value = sysHousingCd;
//	document.inputForm.submit();

</script>

</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="contents">
	<div id="contentsInner">
					<c:import url="/WEB-INF/jsp/front/include/common/topicPath.jsh?pattern=INQ-00-00" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">物件お問い合わせ</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01">
				<p class="f14">既に会員登録がお済みの方は、ログインした上でお問い合わせください。</p>
			</div>
			<c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" />

			<div class="column2Wrap01 loginBox clearfix">
				<div class="columnBlock01">
					<div class="itemBlockA01 firstChild spMb00">
						<form action="<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/check/" method="post" name="inputForm">

<!--
							<input type="hidden" name="sysHousingCd" value="<c:out value="${sysHousingCd}"/>" />
-->
							<c:if test="${!empty redirectURL}">
							    <input type="hidden" name="redirectURL" value="<c:out value="${redirectURL}"/>"/>
							</c:if>
							<c:if test="${empty redirectURL}">
							    <input type="hidden" name="redirectURL" value="<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input"/>
							</c:if>

							<div class="headingBlockC01">
								<h2>会員登録がお済みの方はこちら</h2>
							</div><!-- /.headingBlockC01 -->
							<div class="columnInner">
								<p class="txt">登録いただいているメールアドレスとパスワードを入力してログインしてください。</p>
								<p class="note cautionColor01">※5回間違えますと24時間ログインできなくなりますのでご注意ください。</p>
								<table class="inputTable01">
									<tr>
										<th><label for="mail01">メールアドレス</label></th>
										<td><input type="text" class="inputType01" id="loginID" name="loginID" placeholder="入力してください" value="<c:out value="${loginForm.loginID}"/>"></td>
									</tr>
									<tr>
										<th><label for="password01">パスワード</label></th>
										<td><input type="password" class="inputType01" id="password" name="password" placeholder="入力してください" value=""></td>
									</tr>
								</table>
								<p class="mb15 center spLeft"><label onClick=""><input type="checkbox" name="autoLogin" id="chkLogin"  value="1"<c:if test="${loginForm.autoLogin == '1'}"> checked</c:if>> ログインしたままにする</label></p>
<!--
								<p class="center mb15"><button type="button" class="primaryBtn01" onclick="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/check/', '<c:out value="${sysHousingCd}"/>');">ログイン</button></p>
-->
								<p class="center mb15"><button type="submit" class="primaryBtn01">ログイン</button></p>
								<p class="center spLeft linkType01"><a href="<c:out value="${pageContext.request.contextPath}"/>/account/pw/forgot/input/">パスワードをお忘れの方はこちら</a></p>
							</div><!-- /.columnInner -->
						</form>
					</div><!-- /.itemBlockA01 -->
				</div>
				<div class="columnBlock02">
					<div class="itemBlockA01 mb20 spMb00">
						<div class="headingBlockC01 clearfix">
							<h2 class="ttl">会員登録をしないお問い合わせ</h2>
						</div><!-- /.headingBlockD01 -->
						<div class="columnInner">
							<p class="txt">会員登録をせずにお問い合わせする方は、こちらからお問い合わせください。</p>
<!--
							<p class="center"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${sysHousingCd}"/>')" class="primaryBtn02">お問い合わせ</a></p>
-->
							<p class="center"><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/?redirectKey=<c:out value="${fn:substring(redirectURL,fn:indexOf(redirectURL,'redirectKey=')+12,fn:length(redirectURL))}"/>" class="primaryBtn02">お問い合わせ</a></p>
						</div><!-- /.columnInner -->
					</div><!-- /.itemBlockA01 -->
					<div class="itemBlockA01 mb00">
						<div class="headingBlockC01 clearfix">
							<h2 class="ttl">会員登録</h2>
						</div><!-- /.headingBlockD01 -->
						<div class="columnInner">
							<p class="txt">会員登録していただくとより詳しい物件情報をご覧いただけます。</p>
							<p class="center mb15"><a href="<c:out value="${pageContext.request.contextPath}"/>/account/member/new/input/?redirectKey=<c:out value="${redirectURL}"/>" class="primaryBtn03">新規会員登録（無料）</a></p>
							<p class="center spLeft linkType01"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage_service/">会員さまだけの4つのメリット</a></p>
						</div><!-- /.columnInner -->
					</div><!-- /.itemBlockA01 -->
				</div>
			</div><!-- /.column2Wrap01 -->
		<!-- / .section01 -->
		</div>
	<!-- / #contentsInner -->
	</div>
<!-- / #contents -->
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>