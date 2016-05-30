<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<!doctype html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>パスワードのお問い合わせ｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->


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
		<c:import url="../include/common/topicPath.jsh?pattern=MYP-03-00" />

		<div class="headingAreaInner">
			<p>
				<c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" />
			</p>
		</div>

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">パスワードのお問い合わせ</h1>
			</div><!-- /.headingBlockA01 -->
				<div class="contentsInner01">
					<p class="f14">ご登録いただいているメールアドレスを入力してください。<br>
					パスワード再設定用URLを送信いたします。<br>
					メールに記載のURLにアクセスの上、パスワードの再設定をお願いいたします。</p>
				</div>
				<form action="../result/" method="post" name="inputForm">

					<div class="layoutBox01">
						<div class="boxInner01">
							<table class="inputTable02">
								<tr>
									<th><label for="mailAddress01">メールアドレス</label></th>
									<td><input type="text" class="inputType01" id="mailAddress" name="mailAddress" placeholder="入力してください" value="<c:out value="${pwRemindForm.mailAddress}"/>"></td>
								</tr>
							</table>
						<p class="center spPb10"><button type="submit" name="" class="primaryBtn01">送信する</button></p>
						</div>
				</div><!-- /.layoutBox01 -->
				<dm3token:oneTimeToken/>
			</form>
		</div>
	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>