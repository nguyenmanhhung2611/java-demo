<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>お問い合わせ　完了｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>inquiry/css/inquiry.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">


<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />

<div id="ptop"></div>
<!--#include virtual="/common/ssi/simple-header-D.html"-->

<div id="contents">
	<div id="contentsInner">
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">お問い合わせ</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step03">
				<ul>
					<li><span>入力</span></li>
					<li><span>確認</span></li>
					<li class="current"><span>完了</span></li>
				</ul>
			</nav>
			<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
				<p class="bold mb30 spMb10 f18 spF16 center spLeft">お問い合わせが完了しました。</p>
				<p class="f14 center spLeft">ご登録いただいたメールアドレスにメールを送信しましたのでご確認ください。<br>お問い合わせ内容を確認の上、担当者よりご連絡させていただきます。</p>
			</div>
			<div class="contentsInner01 spPb10">
				<p class="center"><a href="/" class="secondaryBtn">トップページへ</a></p>
			</div>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->
<!-- Yahoo Code for your Conversion Page -->
<c:choose>
     <c:when test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '001'}">
        <img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=No2YCMD6xWMQze_kvQM&guid=ON&script=0"/>
     </c:when>
     <c:when test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '002'}">
        <img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=u9PNCI_ivGMQze_kvQM&guid=ON&script=0"/>
     </c:when>
     <c:when test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '003'}">
        <img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=2nJcCO6mzmMQze_kvQM&guid=ON&script=0"/>
     </c:when>
     <c:when test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '004'}">
        <%-- 顧客要望によりお問い合わせフォームからのセミナー申し込みはカウントしない <img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=_DkDCN-mzmMQze_kvQM&guid=ON&script=0"/> --%>
     </c:when>
     <c:when test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '005'}">
        <img height="1" width="1" alt="" src="<c:out value='${commonParameters.yahooCodeSrc}'/>?value=0&label=OnhLCOumzmMQze_kvQM&guid=ON&script=0"/>
     </c:when>
</c:choose>

</body>
</html>
