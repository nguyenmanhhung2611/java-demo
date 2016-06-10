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
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>お問い合わせ　確認｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>inquiry/css/inquiry.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, command) {
		document.inputForm.action = url;
		document.inputForm.command.value=command;
		document.inputForm.submit();
	}
// -->
</script>

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
			<nav class="stepChartNav step02">
				<ul>
					<li><span>入力</span></li>
					<li class="current"><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
				<div class="contentsInner01">
					<p class="f14">お問い合わせ内容をご確認ください。</p>
				</div>
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">お問い合わせ</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>お問い合わせ内容</th>
								<td>
					    			<dm3lookup:lookupForEach lookupName="inquiry_dtl_type">
					                   <c:if test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == key}"><c:out value="${value}"/> </c:if>
									</dm3lookup:lookupForEach>
								&nbsp;</td>
							</tr>
							<c:if test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == '004'}">
							<tr>
								<th>セミナー・イベント名</th>
								<td><c:out value="${inputForm.eventName}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>日時</th>
								<td><c:out value="${inputForm.eventDatetimeWithFormat}"/>&nbsp;</td>
							</tr>
							</c:if>
							<tr>
								<th>お問い合わせ内容詳細</th>
								<td><c:out value="${inputForm.inquiryText1}" escapeXml="false"/>&nbsp;</td>
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
								<td style="word-break:break-all"><c:out value="${inputForm.inquiryHeaderForm.lname}"/>　<c:out value="${inputForm.inquiryHeaderForm.fname}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>お名前（フリガナ）</th>
								<td style="word-break:break-all"><c:out value="${inputForm.inquiryHeaderForm.lnameKana}"/>　<c:out value="${inputForm.inquiryHeaderForm.fnameKana}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>メールアドレス</th>
								<td style="word-break:break-all"><c:out value="${inputForm.inquiryHeaderForm.email}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>電話番号</th>
								<td><c:out value="${inputForm.inquiryHeaderForm.tel}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>ご希望の連絡方法</th>
								<td>
					    			<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
					                   <c:if test="${inputForm.inquiryContactType == key}"><c:out value="${value}"/> </c:if>
									</dm3lookup:lookupForEach>
								&nbsp;</td>
							</tr>
							<tr>
								<th>連絡可能な時間帯</th>
								<td>
									<c:forEach items="${inputForm.inquiryContactTime}" var="selectedInquiryContactTime">
						    			<dm3lookup:lookupForEach lookupName="inquiry_contact_time">
						                   <c:if test="${selectedInquiryContactTime == key}"><c:out value="${value}"/>&nbsp;&nbsp; </c:if>
										</dm3lookup:lookupForEach>
									</c:forEach>
								&nbsp;</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb10">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">アンケート</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th><dm3lookup:lookup lookupName="question" lookupKey="001"/></th>
								<td class="mb20 spMb00">
									<c:forEach items="${inputForm.ansCd}" var="ansCd">
										<dm3lookup:lookup lookupName="ans_all" lookupKey="${ansCd}"/>
										<c:if test="${ansCd == '008' && inputForm.etcAnswer1 != null && inputForm.etcAnswer1.length() > 0}">
											／<c:out value="${inputForm.etcAnswer1}"/>
										</c:if>
										<c:if test="${ansCd == '009' && inputForm.etcAnswer2 != null && inputForm.etcAnswer2.length() > 0}">
											／<c:out value="${inputForm.etcAnswer2}"/>
										</c:if>
										<c:if test="${ansCd == '010' && inputForm.etcAnswer3 != null && inputForm.etcAnswer3.length() > 0}">
											／<c:out value="${inputForm.etcAnswer3}"/>
										</c:if>
										<br>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->

				<div class="contentsInner01 mt30 mb15 spMt10 spPb10">
					<ul class="btnList01">
						<li><button type="button" name="" class="backBtn" onclick="javascript:linkToUrl('../input/', 'back');">修正する</button></li>
						<li><button type="button" name="" class="primaryBtn01" onclick="javascript:linkToUrl('../result/', '');">この内容で<br class="SPdisplayBlock">送信する</button></li>
					</ul>
				</div>
			<form action="" method="post" name="inputForm">
				<input type="hidden" name="command" value="">
				<input type="hidden" name="inquiryDtlType" value="<c:out value="${inputForm.inquiryHeaderForm.inquiryDtlType[0]}"/>">
				<input type="hidden" name="eventName" value="<c:out value="${inputForm.eventName}"/>">
				<input type="hidden" name="eventDatetimeMonth" value="<c:out value="${inputForm.eventDatetimeMonth}"/>">
				<input type="hidden" name="eventDatetimeDay" value="<c:out value="${inputForm.eventDatetimeDay}"/>">
				<input type="hidden" name="eventDatetimeHour" value="<c:out value="${inputForm.eventDatetimeHour}"/>">
				<input type="hidden" name="eventDatetimeMinute" value="<c:out value="${inputForm.eventDatetimeMinute}"/>">
				<input type="hidden" name="eventDatetime" value="<c:out value="${inputForm.eventDatetime}"/>">
				<input type="hidden" name="eventDatetimeWithFormat" value="<c:out value="${inputForm.eventDatetimeWithFormat}"/>">
				<input type="hidden" name="inquiryText" value="<c:out value="${inputForm.inquiryHeaderForm.inquiryText}"/>">
				<input type="hidden" name="lname" value="<c:out value="${inputForm.inquiryHeaderForm.lname}"/>">
				<input type="hidden" name="fname" value="<c:out value="${inputForm.inquiryHeaderForm.fname}"/>">
				<input type="hidden" name="lnameKana" value="<c:out value="${inputForm.inquiryHeaderForm.lnameKana}"/>">
				<input type="hidden" name="fnameKana" value="<c:out value="${inputForm.inquiryHeaderForm.fnameKana}"/>">
				<input type="hidden" name="email" value="<c:out value="${inputForm.inquiryHeaderForm.email}"/>">
				<input type="hidden" name="tel" value="<c:out value="${inputForm.inquiryHeaderForm.tel}"/>">
				<input type="hidden" name="inquiryContactType" value="<c:out value="${inputForm.inquiryContactType}"/>">
				<c:forEach items="${inputForm.inquiryContactTime}" var="selectedInquiryContactTime">
					<input type="hidden" name="inquiryContactTime" value="<c:out value="${selectedInquiryContactTime}"/>">
				</c:forEach>
				<c:forEach items="${inputForm.ansCd}" var="selectedAnsCd">
					<input type="hidden" name="ansCd" value="<c:out value="${selectedAnsCd}"/>">
				</c:forEach>
				<input type="hidden" name="etcAnswer1" value="<c:out value="${inputForm.etcAnswer1}"/>">
				<input type="hidden" name="etcAnswer2" value="<c:out value="${inputForm.etcAnswer2}"/>">
				<input type="hidden" name="etcAnswer3" value="<c:out value="${inputForm.etcAnswer3}"/>">
				<input type="hidden" name="urlPattern" value="<c:out value="${inputForm.urlPattern}"/>">
				<dm3token:oneTimeToken/>
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->
</body>
</html>
