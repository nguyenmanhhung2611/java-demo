<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： NotFound共通画面

 2015/05/07		Trans	新規作成
 ---------------------------------------------------------------- --%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>ページ遷移エラー<｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>


<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
</head>

<body>

<div id="ptop"></div>

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="contents">
	<div id="contentsInner">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="${pageContext.request.contextPath}/index.html">トップ</a></li>
					<li class="current">ページ遷移エラー</li>
				</ul>
			</div>
		</div>

		<div class="section01 center">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl fNone">ページの遷移が正しくありません</h1>
			</div>
			<div class="contentsInner02">
				<p class="mb40">
				ページの遷移が正しくありませんでした。<br>
				ページ操作の途中でのブラウザの「戻る」ボタンのご使用や、<br>
				ブックマークなどからページへアクセスされた場合が考えられます。<br><br>
				お手数ですが、再度Topページからアクセスをお願いいたします。
				</p>
				<p class="center"><a class="secondaryBtn" href="${pageContext.request.contextPath}/index.html">TOPページヘ</a></p>
			</div>
		</div>
	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>
</html>
