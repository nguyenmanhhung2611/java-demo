<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
物件のお問い合わせ｜<c:out value='${commonParameters.panaReSmail}'/>
</title>


<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" type="text/css" media="screen,print" />

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type="text/javascript">

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

	$(function() {
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
		urlPattern += $("#prefCd").attr("name") + "=" + $("#prefCd").val();
		$("#urlPattern").val(urlPattern);
	}

	function zipToAddressHousing() {
		if ($("#zip").css('color') ==  'silver' || $("#zip").val() == null || $("#zip").val().length == 0) {
			alert("郵便番号を入力してください。");
			$("#zip").focus();
			return;
		}
		$("#address").focus();
		$.getJSON("../zipToAddressHousing/", {zip:$("#zip").val()}, function(dataObj){
			if (dataObj.zipMst[0] != "0") {
				$("#prefCd").val("");
				$("#address").val("");
				alert(dataObj.zipMst[1]);
				$("#zip").focus();
				return;
			}
			$("#prefCd").val(dataObj.zipMst[1]);
			$("#address").val(dataObj.zipMst[2]);
		});
	}

</script>
<style type="text/css">
.p1{
display: block;
overflow: hidden;
word-break: break-all;
white-space: nowrap;
text-overflow: ellipsis;
}
</style>

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />

<!--#include virtual="/common/ssi/simple-header-D.html"-->
<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<div class="section01">
			<form action="../confirm/" method="post" name="inputForm">
				<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
				<input type="hidden" id="sysHousingCd" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd[0]}"/>">
				<input type="hidden" id="urlPattern" name="urlPattern" value="">
				<div class="headingBlockA01 clearfix">
					<h1 class="ttl">物件のお問い合わせ</h1>
				</div><!-- /.headingBlockA01 -->
				<nav class="stepChartNav step01">
					<ul>
						<li class="current"><span>入力</span></li>
						<li><span>確認</span></li>
						<li><span>完了</span></li>
					</ul>
				</nav>
				<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>
				<div class="itemBlockA02 spMb00">
					<table class="tableBlockB01">
						<tr>
							<th class="head"><h2>お問い合わせ物件</h2></th>
							<td class="body">
								<div class="estateBlockB01 clearfix">
									<div class="buildingName">
										<ul class="buildingType clearfix">
											<c:if test="${dateFlg == '1'}">
												<li class="icoNew01">新着</li>
											</c:if>
											<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_front_icon">
												<c:if test="${buildingInfo.housingKindCd == key}"><li class="icoMansion01"><c:out value="${value}"/></li></c:if>
											</dm3lookup:lookupForEach>
										</ul>
										<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_En">
											<c:if test="${buildingInfo.housingKindCd == key}">
												<c:set var="housingKindName" value="${value}" />
											</c:if>
										</dm3lookup:lookupForEach>
										<h2>
											<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${inputForm.backPrefCd}"/>/detail/<c:out value="${inputForm.sysHousingCd[0]}"/>/">
												<c:out value="${housingInfo.displayHousingName}"/>
											</a>
										</h2>
									</div><!-- /.buildingName -->

									<div class="buildingPhoto">
										<p><a><img src="<c:if test="${!empty pathName}"><c:out value="${pathName}"/></c:if><c:if test="${empty pathName}"><c:out value="${commonParameters.resourceRootUrl}${commonParameters.noPhoto200}"/></c:if>" alt=""></a></p>
									</div>

									<div class="buildingSummary">
										<table class="tableType1" style="width:100%">
											<tbody>
												<tr>
													<th>物件価格</th>
													<td>
														<p class="buildingPrice">
															<c:if test="${!empty housingInfo.price}">
																<c:set var="price" value="${housingInfo.price / 10000}"/>
																<fmt:formatNumber value="${price + (1 - (price % 1)) % 1}" pattern="###,###" />万円
															</c:if>
														</p>
													</td>
												</tr>
												<tr>
													<th>所在地</th>
													<td><c:out value="${address}"/></td>
												</tr>
												<tr>
													<th>アクセス</th>
													<td>
														<c:forEach  items="${nearStationList}" var="akusesuItem">
															<c:out value="${akusesuItem}"/><br>
														</c:forEach>
													</td>
												</tr>
												<c:if test="${buildingInfo.housingKindCd == '01'}">
													<tr>
														<th nowrap="nowrap"><span>間取り</span><span class="SPdisplayNone"> &frasl; </span><span>専有面積</span></th>
														<td>
															<dm3lookup:lookupForEach lookupName="layoutCd">
																<c:if test="${housingInfo.layoutCd == key}"><c:out value="${value}"/></c:if>
															</dm3lookup:lookupForEach>
															<c:if test="${!empty housingInfo.layoutCd or !empty personalArea}">
																<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
															</c:if>
															<c:if test="${!empty personalArea}">
																<c:out value="${personalArea}"/>m&sup2;<c:out value="${personalAreaSquare}"/>
															</c:if>
														</td>
													</tr>
												</c:if>
												<c:if test="${buildingInfo.housingKindCd != '01'}">
													<tr>
														<th nowrap="nowrap"><span>間取り</span><span class="SPdisplayNone"> &frasl; </span><span>建物面積</span><span class="SPdisplayNone"> &frasl; </span>土地面積</th>
														<td>
															<dm3lookup:lookupForEach lookupName="layoutCd">
																<c:if test="${housingInfo.layoutCd == key}"><c:out value="${value}"/></c:if>
															</dm3lookup:lookupForEach>
															<c:if test="${!empty housingInfo.layoutCd or !empty buildingArea or !empty landArea}">
																<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
															</c:if>
															<c:if test="${!empty buildingArea}">
																<c:out value="${buildingArea}"/>m&sup2;<c:out value="${buildingAreaSquare}"/>
															</c:if>
															<c:if test="${!empty housingInfo.layoutCd or !empty buildingArea or !empty landArea}">
																<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
															</c:if>
															<c:if test="${!empty landArea}">
																<c:out value="${landArea}"/>m&sup2;<c:out value="${landAreaSquare}"/>
															</c:if>
														</td>
													</tr>
												</c:if>
												<tr>
													<th>築年月</th>
													<td><c:out value="${compDate}"/></td>
												</tr>
												<c:if test="${buildingInfo.housingKindCd == '01'}">
													<tr>
														<th>階建<span class="SPdisplayNone"> &frasl; </span><br class="SPdisplayBlock">所在階</th>
														<td>
															<c:out value="${totalFloor}"/>
															<c:if test="${!empty totalFloor or !empty floorNo}">
																<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
																<c:out value="${floorNo}"/>
															</c:if>
														</td>
													</tr>
												</c:if>
											</tbody>
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table><!-- /.tableBlockB01 -->
				</div><!-- /.itemBlockA02 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">お問い合わせ内容</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>お問い合わせ内容<span class="mustIcon">必須</span></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valInquiryDtlType" />
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_housing_dtl_type">
											<li class="<c:if test="${dm3hasError}">error</c:if>"><label onClick="">
												<input type="radio" name="inquiryDtlType" value="<c:out value="${key}"/>"
													<c:if test="${inputForm.getInquiryHeaderForm().inquiryDtlType[0] == key}">checked</c:if>>
												<c:out value="${value}"/>
											</label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th><label for="detail01">お問い合わせ内容詳細<br>（最大全角300文字）</label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valInquiryText" />
									</c:import>
									<div><textarea name="inquiryText" class="<c:if test="${dm3hasError}">error</c:if>" id="detail01" placeholder="お問い合わせ内容詳細をこちらにご入力ください。"><c:out value="${inputForm.getInquiryHeaderForm().inquiryText}"/></textarea></div>
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
									<c:param name="targetLabel" value="inquiryHousing.input.valLname" />
									</c:import>
									<c:set var="lnameHasError" value="${dm3hasError}" scope="request"/>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valFname" />
									</c:import>
									<c:set var="fnameHasError" value="${dm3hasError}" scope="request"/>
									<div>姓 <input type="text" name="lname" class="inputType05 <c:if test="${lnameHasError}">error</c:if>" id="name01" placeholder="例：松下" value="<c:out value="${inputForm.getInquiryHeaderForm().lname}"/>" maxlength="30">
										 名 <input type="text" name="fname" class="inputType05 <c:if test="${fnameHasError}">error</c:if>" placeholder="例：太郎" value="<c:out value="${inputForm.getInquiryHeaderForm().fname}"/>" maxlength="30">
									</div>
								</td>
							</tr>
							<tr>
								<th><label for="name02">お名前（フリガナ）<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valLnameKana" />
									</c:import>
									<c:set var="lnameKanaHasError" value="${dm3hasError}" scope="request"/>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valFnameKana" />
									</c:import>
									<c:set var="fnameKanaHasError" value="${dm3hasError}" scope="request"/>
									<div>姓 <input type="text" name="lnameKana" class="inputType05 <c:if test="${lnameKanaHasError}">error</c:if>" id="name02" placeholder="例：マツシタ" value="<c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>" maxlength="30">
										 名 <input type="text" name="fnameKana" class="inputType05 <c:if test="${fnameKanaHasError}">error</c:if>" placeholder="例：タロウ" value="<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/>" maxlength="30">
									</div>
								</td>
							</tr>
							<tr>
								<th><label for="mail01">メールアドレス<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valEmail" />
									</c:import>
									<div><input type="text" name="email" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="mail01" placeholder="例：panasonic＠panasonic.com" value="<c:out value="${inputForm.getInquiryHeaderForm().email}"/>" maxlength="255"></div>
								</td>
							</tr>
							<tr>
								<th><label for="tel01">電話番号<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valTel" />
									</c:import>
									<div><input type="text" name="tel" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="tel01" placeholder="例：1234567890" value="<c:out value="${inputForm.getInquiryHeaderForm().tel}"/>" maxlength="11"></div>
									<div class="mt05">※半角数字、ハイフンなしで市外局番から入力してください。</div>
								</td>
							</tr>
							<tr>
								<th><label for="fax01">FAX番号</label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valFax" />
									</c:import>
									<div><input type="text" name="fax" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" id="fax01" placeholder="例：1234567890" value="<c:out value="${inputForm.getInquiryHeaderForm().fax}"/>" maxlength="11"></div>
								</td>
							</tr>
							<tr>
								<th><label for="zip">郵便番号<span class="mustIcon">必須</span></label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valZip" />
									</c:import>
									<div>
										<input type="text" name="zip" class="inputType02 <c:if test="${dm3hasError}">error</c:if>" id="zip" placeholder="例：1234567" value="<c:out value="${inputForm.getInquiryHeaderForm().zip}"/>" maxlength="7">
										<a href="javascript:zipToAddressHousing();" class="addressBtn">住所検索</a>
										<a href="http://www.post.japanpost.jp/zipcode/" target="_blank" class="link02">郵便番号がわからない方</a>
										<div class="mt10">※半角数字、ハイフンなしで入力してください。</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>都道府県<span class="mustIcon">必須</span></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valPrefName" />
									</c:import>
									<div>
										<select name="prefCd" id="prefCd" class="<c:if test="${dm3hasError}">error</c:if>">
											<option value="">選択してください</option>
											<c:forEach items="${prefMstList}" var="prefMstInfo">
												<option value="<c:out value="${prefMstInfo.prefCd}"/>" <c:if test="${inputForm.getInquiryHeaderForm().prefCd == prefMstInfo.prefCd}">selected</c:if>><c:out value="${prefMstInfo.prefName}"/></option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>市区町村番地<span class="mustIcon">必須</span></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valAddress" />
									</c:import>
									<div><input type="text" name="address" id="address" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：市区町村１－２３－４" value="<c:out value="${inputForm.getInquiryHeaderForm().address}"/>" maxlength="50"></div>
								</td>
							</tr>
							<tr>
								<th><label for="building01">建物名</label></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valAddressOther" />
									</c:import>
									<div><input type="text" name="addressOther" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" id="building01" placeholder="例：○○マンション１０１号室" value="<c:out value="${inputForm.getInquiryHeaderForm().addressOther}"/>" maxlength="30"></div>
								</td>
							</tr>
							<tr>
								<th>ご希望の連絡方法</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valContactType" />
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
										 <li class="<c:if test="${dm3hasError}">error</c:if>"><label>
											<input type="radio" id="contactType" name="contactType" value="<c:out value="${key}"/>"
												<c:if test="${inputForm.contactType == key}">checked</c:if>
											/><c:out value="${value}"/>
										</label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th>連絡可能な時間帯</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
									<c:param name="targetLabel" value="inquiryHousing.input.valContactTime" />
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_contact_time">
											<li class="<c:if test="${dm3hasError}">error</c:if>"><label onClick=""><input type="checkbox" name="contactTime" value="<c:out value="${key}"/>"
													<c:forEach items="${inputForm.getContactTime()}" varStatus="contactTime">
														<c:if test="${inputForm.getContactTime()[contactTime.index] == key}">
															checked
														</c:if>
													</c:forEach>>
												<c:out value="${value}"/>
												</label>
											</li>
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
								<c:param name="targetLabel" value="inquiryHousing.input.ansCd" />
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
											<c:param name="targetLabel" value="inquiryHousing.input.etcAnswer1" />
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
											<c:param name="targetLabel" value="inquiryHousing.input.etcAnswer2" />
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
											<c:param name="targetLabel" value="inquiryHousing.input.etcAnswer3" />
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
						<a class="SPdisplayBlock" href="<c:out value="${pageContext.request.contextPath}"/>/info/privacy.html">
							<h2 class="ttl">個人情報の取り扱いについて<span class="SPdisplayBlock">【必ずお読みください】</span></h2>
						</a>
					</div><!-- /.headingBlockB02 -->
					<div class="columnInner">
						<p class="txt01 center spLeft">ご入力いただいた情報は、当社の個人情報保護方針に従い、適正に管理いたします。<br><span class="bold">下記の「個人情報の取り扱い」を必ずご一読いただき、同意のうえお申し込みください。</span></p>
						<div class="textAreaType02">
							<!--#include virtual="/common/ssi/privacy.html"-->
						</div>
						<p class="mb15 center spLeft"><label onClick="clickAgreeChk(this);"><input type="checkbox" id="agreeChk"> 個人情報の取り扱いに同意する</label></p>
						<p class="center mb15"><button type="submit" name="confirmBtn" id="confirmBtn" class="primaryBtn01" onclick="javascript:setUrlPattern();" disabled>入力内容を確認する</button></p>
					</div><!-- /.columnInner -->
				</div>
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->
</body>