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

<title>お問い合わせ｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>


<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type ="text/JavaScript">
	$(document).ready(function() {
		if (!$("#ansCd08").prop('checked')) {
			$("#etcAnswer1").attr('disabled', 'disabled');
		}

		if (!$("#ansCd09").prop('checked')) {
			$("#etcAnswer2").attr('disabled', 'disabled');
		}

		if (!$("#ansCd10").prop('checked')) {
			$("#etcAnswer3").attr('disabled', 'disabled');
		}
		$("#ansCd08").on("change", function() {
			if ($("#ansCd08").prop('checked')) {
				$("#etcAnswer1").removeAttr('disabled');
			} else {
				$("#etcAnswer1").attr('disabled', 'disabled');
			}
		});
		$("#ansCd09").on("change", function() {
			if ($("#ansCd09").prop('checked')) {
				$("#etcAnswer2").removeAttr('disabled');
			} else {
				$("#etcAnswer2").attr('disabled', 'disabled');
			}
		});
		$("#ansCd10").on("change", function() {
			if ($("#ansCd10").prop('checked')) {
				$("#etcAnswer3").removeAttr('disabled');
			} else {
				$("#etcAnswer3").attr('disabled', 'disabled');
			}
		});
	});

	$(function(){
		var $seminarDetail = $("tr.seminarDetail");
		if (!$("#seminarBtn input").prop('checked')) {
			$seminarDetail.hide();
		}

		$("#inqList li input").on("change", function() {
			if ($("#seminarBtn input").prop('checked')) {
				$seminarDetail.show();
				$("#seminar01").focus();
			} else {
				$seminarDetail.hide();
				$("#seminar01").val("");
				$("input.inputType06").val("");
			}
		});
	});

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

	$(function(){
		if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {
			return;
		} else {
			$("#etcAnswer1").css("width", "500px");
			$("#etcAnswer2").css("width", "500px");
			$("#etcAnswer3").css("width", "500px");
		}
	});

	function setUrlPattern() {
		var urlPattern= "";
		$("input:checked").each(function(i){
			if (typeof($(this).attr("name")) != "undefined") {
				urlPattern += $(this).attr("name") + "=" + $(this).val() + "&";
			}
			});
		$("#urlPattern").val(urlPattern);
	}
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
				<h1 class="ttl">お問い合わせ</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step01">
				<ul>
					<li class="current"><span>入力</span></li>
					<li><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>
			<!-- /.generalBlockA01 -->
			<form action="../confirm/" name="inputForm" method="post">
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">お問い合わせ</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>お問い合わせ内容<span class="mustIcon">必須</span></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.inquiryDtlType" />
									</c:import>
									<ul class="checkList01" id="inqList">
										<dm3lookup:lookupForEach lookupName="inquiry_dtl_type">
											<li <c:if test="${key == '004'}">id="seminarBtn"</c:if> class="<c:if test="${dm3hasError}">error</c:if>"><label onClick=""><input type="radio" name="inquiryDtlType" value="<c:out value="${key}"/>" <c:if test="${inputForm.inquiryHeaderForm.inquiryDtlType[0] == key}">checked</c:if>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr class="seminarDetail">
								<th class="pb00 spPb10"><label for="seminar01">セミナー・イベント名<span class="mustIcon">必須</span></label></th>
								<td class="pb00">
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.eventName" />
									</c:import>
									<div><input type="text" name="eventName" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="seminar01" placeholder="入力してください" value="<c:out value="${inputForm.eventName}"/>" maxlength="50"></div>
								</td>
							</tr>
							<tr class="seminarDetail">
								<th class="SPdisplayNone"></th>
								<td class="pt05">※お申し込み対象のイベント・セミナー名を入力してください。</td>
							</tr>
							<tr class="seminarDetail">
									<th><label for="date01">日時<span class="mustIcon">必須</span></label></th>
									<td>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="inquiryGeneral.input.eventDateTimeMonth" />
										</c:import>
										<c:set var="monthHasError" value="${dm3hasError}" scope="request"/>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="inquiryGeneral.input.eventDateTimeDay" />
										</c:import>
										<c:set var="dyaHasError" value="${dm3hasError}" scope="request"/>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="inquiryGeneral.input.eventDateTimeHour" />
										</c:import>
										<c:set var="hourHasError" value="${dm3hasError}" scope="request"/>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="inquiryGeneral.input.eventDateTimeMinute" />
										</c:import>
										<c:set var="minuteHasError" value="${dm3hasError}" scope="request"/>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="日時" />
										</c:import>
										<c:set var="dateHasError" value="${dm3hasError}" scope="request"/>
										<div>
										<input type="text" name="eventDatetimeMonth" class="inputType06 <c:if test="${monthHasError or dateHasError}">error</c:if>" id="date01" value="<c:out value="${inputForm.eventDatetimeMonth}"/>" maxlength="2">月
										<input type="text" name="eventDatetimeDay" class="inputType06 <c:if test="${dyaHasError or dateHasError}">error</c:if>" value="<c:out value="${inputForm.eventDatetimeDay}"/>" maxlength="2">日
										<input type="text" name="eventDatetimeHour" class="inputType06 ml10 <c:if test="${hourHasError or dateHasError}">error</c:if>" value="<c:out value="${inputForm.eventDatetimeHour}"/>" maxlength="2">：
										<input type="text" name="eventDatetimeMinute" class="inputType06 ml05 <c:if test="${minuteHasError or dateHasError}">error</c:if>" value="<c:out value="${inputForm.eventDatetimeMinute}"/>" maxlength="2">
										</div>
									</td>
								</tr>
							<tr>
									<th><label for="detail01">お問い合わせ内容詳細<br>
									（最大全角1000文字）</label></th>
									<td>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
										<c:param name="targetLabel" value="inquiryGeneral.input.inquiryText" />
										</c:import>
										<div><textarea name="inquiryText" id="detail01" class="<c:if test="${dm3hasError}">error</c:if>" placeholder="お問い合わせ内容詳細をこちらにご入力ください。"><c:out value="${inputForm.inquiryHeaderForm.inquiryText}"/></textarea></div>
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
								<th><label for="name01">お名前<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.lname" />
									</c:import>
									<c:set var="lnameHasError" value="${dm3hasError}" scope="request"/>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.fname" />
									</c:import>
									<c:set var="fnameHasError" value="${dm3hasError}" scope="request"/>
									<div>姓 <input type="text" name="lname" class="inputType05 <c:if test="${lnameHasError}">error</c:if>" id="name01" placeholder="例：松下" value="<c:out value="${inputForm.inquiryHeaderForm.lname}"/>" maxlength="30">
										 名 <input type="text" name="fname" class="inputType05 <c:if test="${fnameHasError}">error</c:if>" placeholder="例：太郎" value="<c:out value="${inputForm.inquiryHeaderForm.fname}"/>" maxlength="30">
									</div>
								</td>
							</tr>
							<tr>
								<th><label for="name02">お名前（フリガナ）<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.lnameKana" />
									</c:import>
									<c:set var="lnameKanaHasError" value="${dm3hasError}" scope="request"/>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.fnameKana" />
									</c:import>
									<c:set var="fnameKanaHasError" value="${dm3hasError}" scope="request"/>
									<div>姓 <input type="text" name="lnameKana" class="inputType05 <c:if test="${lnameKanaHasError}">error</c:if>" id="name02" placeholder="例：マツシタ" value="<c:out value="${inputForm.inquiryHeaderForm.lnameKana}"/>" maxlength="30">
										 名 <input type="text" name="fnameKana" class="inputType05 <c:if test="${fnameKanaHasError}">error</c:if>" placeholder="例：タロウ" value="<c:out value="${inputForm.inquiryHeaderForm.fnameKana}"/>" maxlength="30">
									</div>
								</td>
							</tr>
							<tr>
								<th><label for="mail01">メールアドレス<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.email" />
									</c:import>
									<div><input type="text" name="email" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="mail01" placeholder="例：panasonic＠panasonic.com" value="<c:out value="${inputForm.inquiryHeaderForm.email}"/>" maxlength="255"></div>
								</td>
							</tr>
							<tr>
								<th><label for="tel01">電話番号<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.tel" />
									</c:import>
									<div><input type="text" name="tel" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="tel01" placeholder="例：1234567890" value="<c:out value="${inputForm.inquiryHeaderForm.tel}"/>" maxlength="11"></div>
									<div class="mt05">※半角数字、ハイフンなしで市外局番から入力してください。</div>
								</td>
							</tr>
							<tr>
								<th>ご希望の連絡方法</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.inquiryContactType" />
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
											<li class="<c:if test="${dm3hasError}">error</c:if>"><label onClick=""><input type="radio" name="inquiryContactType" value="<c:out value="${key}"/>" <c:if test="${inputForm.inquiryContactType == key}">checked</c:if>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th>連絡可能な時間帯</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.inquiryContactTime" />
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_contact_time">
											<li class="<c:if test="${dm3hasError}">error</c:if>"><label onClick=""><input type="checkbox" name="inquiryContactTime" value="<c:out value="${key}"/>" <c:forEach items="${inputForm.inquiryContactTime}" var="selectedInquiryContactTime"><c:if test="${selectedInquiryContactTime == key}">checked</c:if></c:forEach>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">アンケート</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th><dm3lookup:lookup lookupName="question" lookupKey="001"/></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryGeneral.input.ansCd" />
									</c:import>
									<div class="mb20 spMb10">
										<ul class="checkList01 column02">
											<dm3lookup:lookupForEach lookupName="ans_etcNs">
												<li><label onClick=""><input type="checkbox" name="ansCd" value="<c:out value="${key}"/>"
														<c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == key}">checked</c:if></c:forEach>>
													<c:out value="${value}"/>
													</label>
												</li>
											</dm3lookup:lookupForEach>
										</ul>
										<ul class="checkList03">
											<li><div style="margin-bottom:5px !important;"><label onClick="" ><input type="checkbox" id="ansCd08" name="ansCd" value="008" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '008'}">checked</c:if></c:forEach>>
													<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="008"/>
												</label></div>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="inquiryGeneral.input.etcAnswer1" />
												</c:import>
												<c:set var="etcAnswer1LengthErr" value="${dm3hasError}" scope="request"/>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="アンケート内容1" />
												</c:import>
												<c:set var="etcAnswer1CheckErr" value="${dm3hasError}" scope="request"/>
												<input type="text" id="etcAnswer1" name="etcAnswer1" class="inputType04 <c:if test="${etcAnswer1LengthErr or etcAnswer1CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer1}"/>" maxlength="50"></li>
											<li><div style="margin-bottom:5px !important;"><label onClick=""><input type="checkbox" id="ansCd09" name="ansCd" value="009" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '009'}">checked</c:if></c:forEach>>
													<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="009"/>
												</label></div>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="inquiryGeneral.input.etcAnswer2" />
												</c:import>
												<c:set var="etcAnswer2LengthErr" value="${dm3hasError}" scope="request"/>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="アンケート内容2" />
												</c:import>
												<c:set var="etcAnswer2CheckErr" value="${dm3hasError}" scope="request"/>
												<input type="text" id="etcAnswer2" name="etcAnswer2" class="inputType04 <c:if test="${etcAnswer2LengthErr or etcAnswer2CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer2}"/>" maxlength="50"></li>
											<li><div style="margin-bottom:5px !important;"><label onClick=""><input type="checkbox" id="ansCd10" name="ansCd" value="010" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '010'}">checked</c:if></c:forEach>>
													<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="010"/>
												</label></div>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="inquiryGeneral.input.etcAnswer3" />
												</c:import>
												<c:set var="etcAnswer3LengthErr" value="${dm3hasError}" scope="request"/>
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
												<c:param name="targetLabel" value="アンケート内容3" />
												</c:import>
												<c:set var="etcAnswer3CheckErr" value="${dm3hasError}" scope="request"/>
												<input type="text" id="etcAnswer3" name="etcAnswer3" class="inputType04 <c:if test="${etcAnswer3LengthErr or etcAnswer3CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer3}"/>" maxlength="50"></li>
										</ul>
									</div>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA03">
					<div class="headingBlockB02 clearfix">
						<div class="SPdisplayNone">
							<h2 class="ttl">個人情報の取り扱いについて【必ずお読みください】</h2>
							<p class="link"><a href="<c:out value="${commonParameters.resourceRootUrl}"/>info/privacy.html" target="_blank">別ページで読む</a></p>
						</div>
						<a class="SPdisplayBlock" href="#">
							<h2 class="ttl">個人情報の取り扱いについて<span class="SPdisplayBlock">【必ずお読みください】</span></h2>
						</a>
					</div><!-- /.headingBlockB02 -->
					<div class="columnInner">
						<p class="txt01 center spLeft">ご入力いただいた情報は、当社の個人情報保護方針に従い、適正に管理いたします。<br><span class="bold">下記の「個人情報の取り扱い」を必ずご一読いただき、同意のうえお申し込みください。</span></p>
						<div class="textAreaType02">
							<!--#include virtual="/common/ssi/privacy.html"-->
						</div>
						<p class="mb15 center spLeft"><label onClick=""><input type="checkbox" id="agreeChk"> 個人情報の取り扱いに同意する</label></p>
						<p class="center mb15"><button type="submit" name="confirmBtn" id="confirmBtn" class="primaryBtn01" onclick="javascript:setUrlPattern();" disabled>入力内容を確認する</button></p>
					</div><!-- /.columnInner -->
				</div>
				<input type="hidden" id="urlPattern" name="urlPattern" value="">
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->
</body>
</html>
