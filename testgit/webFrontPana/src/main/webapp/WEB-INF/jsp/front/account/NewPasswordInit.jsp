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

<title>パスワードの再設定｜<c:out value="${commonParameters.panaReSmail}"/></title>

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
<!--#include virtual="/common/ssi/simple-header-D.html"-->

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=MYP-03-03" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">パスワードの再設定</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01">
				<p class="f14">新しく設定するパスワードを入力してください。<br>
					※「パスワードを再設定する」ボタンを押すと、これまでご使用いただいていたパスワードがリセットされます。</p>
			</div>

			<!-- エラーメッセージ -->
			<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>

			<form action="<c:out value="${pageContext.request.contextPath}"/>/account/pw/new/result/<c:out value="${remindId}"/>/" name="inputForm" method="post">

				<div class="layoutBox01">
					<div class="boxInner01">
						<table class="inputTable02">
							<tr>
								<th><label for="newPassword01">新しいパスワード</label></th>
								<td><input type="password" class="inputType01" id="newPassword" name="newPassword" value="<c:out value="${pwdChangeForm.newPassword}"/>" placeholder="Panaso29">
								<p>※8文字以上16文字以下で入力してください。<br>
								※英大文字と英小文字と数字を必ず混在させてください。</p></td>
							</tr>
							<tr>
								<th><label for="newPassword02">新しいパスワードの確認</label></th>
								<td><input type="password" class="inputType01" id="newPasswordChk" name="newPasswordChk" value="<c:out value="${pwdChangeForm.newPasswordChk}"/>" placeholder="">
								<p>※確認のため、もう一度パスワードを入力してください。</p></td>
							</tr>
						</table>
						<p class="center spPb10"><button type="submit" class="primaryBtn01">パスワードを再設定する</button></p>
					</div>
				</div><!-- /.layoutBox01 -->
				<dm3token:oneTimeToken/>
			</form>
		</div>
	</div>
</div>


<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>