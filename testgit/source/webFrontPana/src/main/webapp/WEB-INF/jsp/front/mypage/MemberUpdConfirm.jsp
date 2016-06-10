<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>登録情報の変更　確認｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript">
	<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
	// -->
</script>
<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--#include virtual="/common/ssi/simple-header-D.html"-->

<form action="" method="post" name="inputForm">
<input type="hidden" name="projectFlg" value="front">
<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
<input type="hidden" name="lastLogin" value="<c:out value="${inputForm.lastLogin}"/>">
<input type="hidden" name="insDate" value="<c:out value="${inputForm.insDate}"/>">
<input type="hidden" name="updDate" value="<c:out value="${inputForm.updDate}"/>">
<input type="hidden" name="promoCd" value="<c:out value="${inputForm.promoCd}"/>">
<input type="hidden" name="refCd" value="<c:out value="${inputForm.refCd}"/>">
<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>">
<input type="hidden" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>">
<input type="hidden" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>">
<input type="hidden" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>">
<input type="hidden" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>">
<input type="hidden" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>">
<input type="hidden" name="zip" value="<c:out value="${inputForm.zip}"/>">
<input type="hidden" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
<input type="hidden" name="address" value="<c:out value="${inputForm.address}"/>">
<input type="hidden" name="addressOther" value="<c:out value="${inputForm.addressOther}"/>">
<input type="hidden" name="residentFlg" value="<c:out value="${inputForm.residentFlg}"/>">
<input type="hidden" name="hopePrefCd" value="<c:out value="${inputForm.hopePrefCd}"/>">
<input type="hidden" name="hopeAddress" value="<c:out value="${inputForm.hopeAddress}"/>">
<input type="hidden" name="tel" value="<c:out value="${inputForm.tel}"/>">
<input type="hidden" name="mailSendFlg" value="<c:out value="${inputForm.mailSendFlg}"/>">
<c:forEach items="${inputForm.questionId}" var="selectedQuestionId">
	<input type="hidden" name="questionId" value="<c:out value="${selectedQuestionId}"/>">
</c:forEach>

<div id="contents">
	<div id="contentsInner">
		<div class="section01">
			<div id="topicPath">
				<div id="topicInner">
					<ul class="clearfix">
							<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>">トップ</a></li>
							<li><a href="<c:out value="${pageContext.request.contextPath}/mypage/"/>">マイページ</a></li>
						<li class="current">登録情報の変更</li>
					</ul>
				</div>
			</div>
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">登録情報の変更</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step02">
				<ul>
					<li><span>入力</span></li>
					<li class="current"><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<div class="contentsInner01">
				<p class="f14">登録内容をご確認ください。</p>
			</div>
			<div class="itemBlockA01 spMb00">
				<div class="headingBlockD01 clearfix">
					<h2 class="ttl">ログイン情報</h2>
				</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
					<table class="tableBlockA01">
						<tr>
							<th>メールアドレス</th>
							<td style="word-break:break-all"><c:out value="${inputForm.email}"/>&nbsp;</td>
						</tr>
						<tr>
							<th>パスワード</th>
							<td>
								<c:if test="${empty inputForm.password }">
									&nbsp;
								</c:if>
								<c:if test="${!empty inputForm.password }">
									<c:out value="●●●●●●●●●●●●"/>
								</c:if>
							</td>
						</tr>
					</table>
				</div><!-- /.columnInner -->
			</div><!-- /.itemBlockA01 -->
				 <div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">お客様情報</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>お名前</th>
								<td><c:out value="${inputForm.memberLname}"/><c:out value="${inputForm.memberFname}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>お名前（フリガナ）</th>
								<td><c:out value="${inputForm.memberLnameKana}"/><c:out value="${inputForm.memberFnameKana}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>郵便番号</th>
								<td><c:if test="${!empty inputForm.zip}"><c:out value="${fn:substring(inputForm.zip,0,3)}-${fn:substring(inputForm.zip,3,7)}"/></c:if></td>
							</tr>
							<tr>
								<th>都道府県</th>
								<td><c:out value="${inputForm.prefName}"/></td>
							</tr>
							<tr>
								<th>市区町村番地</th>
								<td><c:out value="${inputForm.address}"/></td>
							</tr>
							<tr>
								<th>建物名</th>
								<td><c:out value="${inputForm.addressOther}"/></td>
							</tr>
							<tr>
								<th>電話番号</th>
								<td><c:out value="${inputForm.tel}"/></td>
							</tr>
							<tr>
								<th>居住状態</th>
								<td>
									<dm3lookup:lookupForEach lookupName="residentFlg">
					                    <c:if test="${inputForm.residentFlg == key}"><c:out value="${value}"/></c:if>
									</dm3lookup:lookupForEach>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件希望地域</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>都道府県</th>
								<td><c:out value="${inputForm.hopePrefName}"/></td>
							</tr>
							<tr>
								<th>市区町村</th>
								<td><c:out value="${inputForm.hopeAddress}"/></td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
			<div class="itemBlockA01 spMb00">
			<div class="headingBlockD01 clearfix">
				<h2 class="ttl">メルマガ配信設定</h2>
			</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
					<table class="tableBlockA01">
						<tr>
							<th>メルマガ配信</th>
							<td>
				                <dm3lookup:lookupForEach lookupName="mailSendFlg">
				                    <c:if test="${inputForm.mailSendFlg == key}"><c:out value="${value}"/></c:if>
								</dm3lookup:lookupForEach>
							</td>
						</tr>
					</table>
				</div><!-- /.columnInner -->
			</div><!-- /.itemBlockA01 -->
			<div class="contentsInner01">
				<ul class="btnList01 spPb10 spMt10">
					<li><button type="button" onClick="linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/mypage/member/change/input/', 'back');" class="backBtn">修正する</button></li>
					<li><button type="button" onClick="linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/mypage/member/change/result/', '');" class="primaryBtn01">この内容で<br class="SPdisplayBlock">登録する</button></li>

				</ul>
			</div>
		</div>
	</div>
</div>
<dm3token:oneTimeToken/>
</form>
<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>
</html>
