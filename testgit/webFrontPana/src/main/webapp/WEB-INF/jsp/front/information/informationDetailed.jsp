<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><c:out value="${information.title}"/>｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>mypage/css/mypage.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.heightLine.js"></script>
<script type="text/javascript">
<!--
$(function(){
	$(".bnrBlock>.columnBlock>.blockInner>a>dl").heightLine({minWidth: 640});
});
-->
</script>
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner" class="mypageInfo">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="<c:out value='${commonParameters.resourceRootUrl}'/>">トップ</a></li>
					<li><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/">マイページ</a></li>
					<li class="current">お客様へのお知らせ</li>
				</ul>
			</div>
		</div>

		<div class="section01 clearfix">
			<c:import url="/WEB-INF/jsp/front/include/mypageTop/sideBlock.jsh" />

			<div class="mainBlock">
				<h1>お客様へのお知らせ</h1>

				<div class="noticeBlock">
					<dl>
						<dt>
							<p class="date">
								<dm3lookup:lookupForEach lookupName="information_type">
									<c:if test="${005 == information.informationType}">
										<c:if test="${key == information.informationType}">
										<span class="iconNotice02"><c:out value="${value}"/></span><fmt:formatDate value="${information.insDate}" pattern="yyyy.MM.dd"/>
										</c:if>
									</c:if>
									<c:if test="${005 != information.informationType}">
										<c:if test="${key == information.informationType}">
										<span class="iconNotice01"><c:out value="${value}"/></span><fmt:formatDate value="${information.insDate}" pattern="yyyy.MM.dd"/>
										</c:if>
									</c:if>
								</dm3lookup:lookupForEach>
							</p>
							<p class="subject"><c:out value="${information.title}"/></p>
						</dt>
						<dd>
							${dm3functions:crToHtmlTag(information.informationMsg)}
							<br>
							<a href="<c:out value="${information.url}"/>" target="_blank"><c:out value="${information.url}"/></a>
						</dd>
					</dl>
					<p class="btnBlack01"><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/">マイページトップへ戻る</a></p>
				</div>
			</div>
		</div>

		<div class="bnrBlock">
			<!--#include virtual="/common/ssi/column.html"-->
		</div>
	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>
