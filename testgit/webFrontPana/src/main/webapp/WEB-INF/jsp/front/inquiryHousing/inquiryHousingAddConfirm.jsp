<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
物件のお問い合わせ　確認｜<c:out value='${commonParameters.panaReSmail}'/>
</title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
<script type ="text/javascript">
function linkToUrl(url, command) {
	document.inputForm.action=url;
	document.inputForm.command.value=command;
	document.inputForm.submit();
}

$(function(){
	var $submitBtn = $("#confirmBtn");
	var $agreeCheck = $("#agreeChk");
	$submitBtn.attr('disabled', 'disabled').addClass('disabled');

	$agreeCheck.on("click", function() {
		if ($(this).prop('checked')) {
			$submitBtn.removeAttr('disabled').removeClass('disabled');
		} else {
			$submitBtn.attr('disabled', 'disabled').addClass('disabled');
		}
	});
});

</script>


</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<!--#include virtual="/common/ssi/simple-header-D.html"-->
<div id="ptop"></div>

	<div id="contents">
		<div id="contentsInner">
			<div class="section01">
				<div class="headingBlockA01 clearfix">
					<h1 class="ttl">物件のお問い合わせ</h1>
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
						<h2 class="ttl">お問い合わせ内容</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>お問い合わせ物件</th>
								<td>
									<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_front_icon">
										<c:if test="${buildingInfo.housingKindCd == key}"><span class="icoMansion01 mr10 spMb05 mt05n"><c:out value="${value}"/></span></c:if>
									</dm3lookup:lookupForEach>
									<c:out value="${housingInfo.displayHousingName}"/>
								</td>
							</tr>
							<tr>
								<th>お問い合わせ内容</th>
								<td>
									<dm3lookup:lookupForEach lookupName="inquiry_housing_dtl_type">
										<c:if test="${inputForm.getInquiryHeaderForm().inquiryDtlType[0] == key}"><c:out value="${value}"/></c:if>
									</dm3lookup:lookupForEach>
								</td>
							</tr>
							<tr>
								<th>お問い合わせ内容詳細</th>
								<td>
									<div><c:out value="${inputForm.showInquiryText}" escapeXml="false"/></div>
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
								<td><c:out value="${inputForm.getInquiryHeaderForm().lname}"/>&nbsp;&nbsp;<c:out value="${inputForm.getInquiryHeaderForm().fname}"/></td>
							</tr>
							<tr>
								<th>お名前（フリガナ）</th>
								<td><c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>&nbsp;&nbsp;<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/></td>
							</tr>
							<tr>
								<th>メールアドレス</th>
								<td style="word-break:break-all"><c:out value="${inputForm.getInquiryHeaderForm().email}"/></td>
							</tr>
							<tr>
								<th>電話番号</th>
								<td><c:out value="${inputForm.getInquiryHeaderForm().tel}"/></td>
							</tr>
							<tr>
								<th>FAX番号</th>
								<td><c:out value="${inputForm.getInquiryHeaderForm().fax}"/></td>
							</tr>
							<tr>
								<th>郵便番号</th>
								<td><c:out value="${fn:substring(inputForm.getInquiryHeaderForm().zip, 0, 3)}-${fn:substring(inputForm.getInquiryHeaderForm().zip, 3, 7)}"/></td>
							</tr>
							<tr>
								<th>都道府県</th>
								<td><c:out value="${inputForm.prefName}"/></td>
							</tr>
							<tr>
								<th>市区町村番地</th>
								<td><c:out value="${inputForm.getInquiryHeaderForm().address}"/></td>
							</tr>
							<tr>
								<th>建物名</th>
								<td><c:out value="${inputForm.getInquiryHeaderForm().addressOther}"/></td>
							</tr>
							<tr>
								<th>ご希望の連絡方法</th>
								<td>
									<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
										<c:if test="${inputForm.contactType == key}"><c:out value="${value}"/></c:if>
									</dm3lookup:lookupForEach>
								</td>
							</tr>
							<tr>
								<th>連絡可能な時間帯</th>
								<td>
									<c:forEach items="${inputForm.getContactTime()}" varStatus="contactTime">
										<dm3lookup:lookup lookupName="inquiry_contact_time" lookupKey="${inputForm.getContactTime()[contactTime.index]}"/>&nbsp;&nbsp;
									</c:forEach>
								</td>
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
						<li><button type="button" name="" class="backBtn" onclick="linkToUrl('../input/', 'back');">修正する</button></li>
						<li><button type="button" name="" class="primaryBtn01" onclick="linkToUrl('../result/', '');">この内容で<br class="SPdisplayBlock">送信する</button></li>
					</ul>
				</div>
				<form action="" method="post" name="inputForm">
					<input type="hidden" name="command" value="">
					<input type="hidden" id="sysHousingCd" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd[0]}"/>">
					<input type="hidden" name="inquiryDtlType" value="<c:out value="${inputForm.getInquiryHeaderForm().inquiryDtlType[0]}"/>">
					<input type="hidden" name="inquiryText" value="<c:out value="${inputForm.getInquiryHeaderForm().inquiryText}"/>">
					<input type="hidden" name="lname" value="<c:out value="${inputForm.getInquiryHeaderForm().lname}"/>">
					<input type="hidden" name="fname" value="<c:out value="${inputForm.getInquiryHeaderForm().fname}"/>">
					<input type="hidden" name="lnameKana" value="<c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>">
					<input type="hidden" name="fnameKana" value="<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/>">
					<input type="hidden" name="email" value="<c:out value="${inputForm.getInquiryHeaderForm().email}"/>">
					<input type="hidden" name="tel" value="<c:out value="${inputForm.getInquiryHeaderForm().tel}"/>">
					<input type="hidden" name="fax" value="<c:out value="${inputForm.getInquiryHeaderForm().fax}"/>">
					<input type="hidden" name="zip" value="<c:out value="${inputForm.getInquiryHeaderForm().zip}"/>">
					<input type="hidden" name="prefCd" value="<c:out value="${inputForm.getInquiryHeaderForm().prefCd}"/>">
					<input type="hidden" name="prefName" value="<c:out value="${inputForm.prefName}"/>">
					<input type="hidden" name="address" value="<c:out value="${inputForm.getInquiryHeaderForm().address}"/>">
					<input type="hidden" name="addressOther" value="<c:out value="${inputForm.getInquiryHeaderForm().addressOther}"/>">
					<input type="hidden" name="contactType" value="<c:out value="${inputForm.contactType}"/>">
					<c:forEach items="${inputForm.getContactTime()}" varStatus="contactTime">
						<input type="hidden" name="contactTime" value="<c:out value="${inputForm.getContactTime()[contactTime.index]}"/>"/>
					</c:forEach>
					<c:forEach items="${inputForm.getAnsCd()}" varStatus="ansCd">
						<input type="hidden" name="ansCd" value="<c:out value="${inputForm.getAnsCd()[ansCd.index]}"/>"/>
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