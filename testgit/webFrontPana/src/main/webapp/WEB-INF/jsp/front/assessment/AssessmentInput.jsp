<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]> <html class="ie7"> <![endif]-->
<!--[if IE 8 ]> <html class="ie8"> <![endif]-->
<!--[if IE 9 ]> <html class="ie9"> <![endif]-->
<!--[if IE 10 ]> <html class="ie10"> <![endif]-->
<!--[if (gt IE 10)|!(IE)]><!--> <html> <!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>査定のお申し込み｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" type="text/css" media="screen,print" />

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder_mod.js"></script>
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/efoBase.css" rel="stylesheet">
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/efoBase.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/efoAssessment.js"></script>
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type="text/javascript">

	$(function(){
		if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {
			return;
		} else {
			$("#etcAnswer1").css("width", "500px");
			$("#etcAnswer2").css("width", "500px");
			$("#etcAnswer3").css("width", "500px");
		}
	});

	function zipToAddress1() {
		if ($("#zip").css('color') ==  'silver' || $("#zip").val() == null || $("#zip").val().length == 0) {
			alert("郵便番号を入力してください。");
			$("#zip").focus();
			return;
		}
		$("#address").focus();
		$.getJSON("../zipToAddress/", {zip:$("#zip").val()}, function(dataObj){
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

	function zipToAddress2() {
		if ($("#userZip").css('color') ==  'silver' || $("#userZip").val() == null || $("#userZip").val().length == 0) {
			alert("郵便番号を入力してください。");
			$("#userZip").focus();
			return;
		}
		$("#userAddress").focus();
		$.getJSON("../zipToAddress/", {zip:$("#userZip").val()}, function(dataObj){
			if (dataObj.zipMst[0] != "0") {
				$("#userPrefCd").val("");
				$("#userAddress").val("");
				alert(dataObj.zipMst[1]);
				$("#userZip").focus();
				return;
			}
			$("#userPrefCd").val(dataObj.zipMst[1]);
			$("#userAddress").val(dataObj.zipMst[2]);
		});
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
			<form action="../confirm/" method="post" name="inputForm">
				<input type="hidden" name="command" value=""/>
				<div class="headingBlockA01 clearfix">
					<h1 class="ttl">査定のお申し込み</h1>
				</div><!-- /.headingBlockA01 -->
				<nav class="stepChartNav step01">
					<ul>
						<li class="current"><span>入力</span></li>
						<li><span>確認</span></li>
						<li><span>完了</span></li>
					</ul>
				</nav>
				<div class="contentsInner01">
					<p class="f14">査定のお申し込みをいただきましたら、査定担当者よりご連絡いたします。</p>
				</div>
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">ご売却希望条件について</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
							<table class="tableBlockA01">
								<tr>
									<th>ご売却物件の種別<span class="mustIcon">必須</span></th>
										<td>
											<ul class="checkList02" id="radioForSale">
												<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
													<c:param name="targetLabel" value="assessment.input.buyHousingType"/>
												</c:import>
												<li class="mansion">
													<dl>
														<dt><label onClick=""><input id="radMansion" type="radio" name="buyHousingType" value="01" checked><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="01"/></label></dt>
														<dd>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.layoutCd1"/>
														</c:import>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.personalArea"/>
														</c:import>
														<c:set var="personalAreahasError" value="${dm3hasError}"/>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.buildAge1"/>
														</c:import>
															<div class="cell"><span class="bold">間取り</span>
																<select id="layoutCd1" name="layoutCd1" class="selectType01">
																	<option value="">タイプ</option>
																	<dm3lookup:lookupForEach lookupName="layoutCd">
																		<option value="<c:out value="${key}"/>" <c:if test="${inputForm.layoutCd1 == key}">selected</c:if>><c:out value="${value}"/></option>
																	</dm3lookup:lookupForEach>
																</select>
															</div>
															<div class="cell"><span class="bold">専有面積</span>
																<span class="pl10">約</span>
																<input type="tel" id="personalArea" name="personalArea" class="inputType01 <c:if test="${personalAreahasError}">error</c:if>" value="<c:out value="${inputForm.personalArea}"/>" maxlength="8">m&sup2;<br>
																<span class="note01 pl20 spPl50">(半角数字)</span>
															</div>
															<div class="cell"><span class="bold">築年数</span>
																<input type="tel" id="buildAge1" name="buildAge1" class="inputType01 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.buildAge1}"/>" maxlength="3">年<br>
																<span class="note02">(半角数字)</span>
															</div>
														</dd>
													</dl>
												</li>
												<li class="house">
													<dl>
														<dt><label onClick=""><input id="radHouse" type="radio" name="buyHousingType" value="02" <c:if test="${inputForm.buyHousingType == '02'}">checked</c:if>><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="02"/></label></dt>
														<dd>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.layoutCd2"/>
														</c:import>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.buildAge2"/>
														</c:import>
															<div class="mb20 spMb00">
																<div class="cell"><span class="bold">間取り</span>
																	<select id="layoutCd2" name="layoutCd2" class="selectType01">
																		<option value="">タイプ</option>
																		<dm3lookup:lookupForEach lookupName="layoutCd">
																			<option value="<c:out value="${key}"/>" <c:if test="${inputForm.layoutCd2 == key}">selected</c:if>><c:out value="${value}"/></option>
																		</dm3lookup:lookupForEach>
																	</select>
																</div>
																<div class="cell"><span class="bold">築年数</span>
																	<input type="tel" id="buildAge2" name="buildAge2" class="inputType01 <c:if test="${dm3hasError}">error</c:if>" value="<c:out value="${inputForm.buildAge2}"/>" maxlength="3">年<br>
																	<span class="note01">(半角数字)</span>
																</div>
															</div>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.landArea2"/>
														</c:import>
														<c:set var="landArea2hasError" value="${dm3hasError}"/>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.landAreaCrs2"/>
														</c:import>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.buildingArea"/>
														</c:import>
														<c:set var="buildingAreahasError" value="${dm3hasError}"/>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.buildingAreaCrs"/>
														</c:import>
															<div>
																<div class="cell"><span class="bold">土地面積</span>
																	<span class="pl10">約</span>
																	<input type="tel" id="landArea2" name="landArea2" class="inputType01 <c:if test="${landArea2hasError}">error</c:if>" value="<c:out value="${inputForm.landArea2}"/>" maxlength="8">
																	<span><label onClick=""><input type="radio" name="landAreaCrs2" value="00" checked>坪</label>
																	<label onClick="" class="ml10"><input type="radio" name="landAreaCrs2" value="01" <c:if test="${inputForm.landAreaCrs2 == '01'}">checked</c:if>>m&sup2;</label></span><br>
																	<span class="note02">(半角数字)</span>
																</div>
																<div class="cell"><span class="bold">建物面積</span>
																	<span class="pl10">約</span>
																	<input type="tel" id="buildingArea" name="buildingArea" class="inputType01 <c:if test="${buildingAreahasError}">error</c:if>" value="<c:out value="${inputForm.buildingArea}"/>" maxlength="8">
																	<span><label onClick=""><input type="radio" name="buildingAreaCrs" value="00" checked>坪</label>
																	<label onClick="" class="ml10"><input type="radio" name="buildingAreaCrs" value="01" <c:if test="${inputForm.buildingAreaCrs == '01'}">checked</c:if>>m&sup2;</label></span><br>
																	<span class="note02">(半角数字)</span>
																</div>
															</div>
														</dd>
													</dl>
												</li>
												<li class="land">
													<dl>
														<dt><label onClick=""><input id="radLand" type="radio" name="buyHousingType" value="03" <c:if test="${inputForm.buyHousingType == '03'}">checked</c:if>><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="03"/></label></dt>
														<dd>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.landArea3"/>
														</c:import>
														<c:set var="landArea3hasError" value="${dm3hasError}"/>
														<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
															<c:param name="targetLabel" value="assessment.input.landAreaCrs3"/>
														</c:import>
															<div class="cell"><span class="bold">土地面積</span>
																<span class="pl10">約</span>
																<input type="tel" id="landArea3" name="landArea3" class="inputType01 <c:if test="${landArea3hasError}">error</c:if>" value="<c:out value="${inputForm.landArea3}"/>" maxlength="8">m&sup2;<br>
																<input type="hidden" name="landAreaCrs3" value="01">
																<span class="note02">(半角数字)</span>
															</div>
														</dd>
													</dl>
												</li>
											</ul>
									</td>
							</tr>
							<tr class="onlyTh">
								<th>ご売却物件所在地</th>
								<td></td>
							</tr>
							<tr>
								<th>郵便番号<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.zip"/>
										</c:import>
										<input type="tel" id="zip" name="zip" class="inputType02 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：1234567" value="<c:out value="${inputForm.zip}"/>" maxlength="7">
										<a href="javascript:zipToAddress1();" class="addressBtn">住所検索</a>
										<a href="http://www.post.japanpost.jp/zipcode/" target="_blank" class="link02">郵便番号がわからない方</a>
										<div class="mt10">※半角数字、ハイフンなしで入力してください。</div>
									</div>
								</td>
							</tr>
							<tr>
								<th>都道府県<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.prefCd"/>
										</c:import>
										<select id="prefCd" name="prefCd">
											<option value="">選択してください</option>
											<c:forEach items="${prefMst}" var="prefMst">
												<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${inputForm.prefCd == prefMst.prefCd}">selected</c:if>><c:out value="${prefMst.prefName}"/></option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>市区町村番地<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.address"/>
										</c:import>
										<input type="text" id="address" name="address" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：市区町村１－２３－４" value="<c:out value="${inputForm.address}"/>" maxlength="50">
									</div>
								</td>
							</tr>
							<tr>
								<th>建物名</th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.addressOther"/>
										</c:import>
										<input type="text" name="addressOther" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：○○マンション１０１号室" value="<c:out value="${inputForm.addressOther}"/>" maxlength="30">
									</div>
								</td>
							</tr>
							<tr>
								<th>現況</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.presentCd"/>
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_present_cd">
											<li><label onClick=""><input type="radio" name="presentCd" value="<c:out value="${key}"/>" <c:if test="${inputForm.presentCd == key}">checked</c:if>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th>ご売却予定の時期</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.buyTimeCd"/>
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="inquiry_buy_time_cd">
											<li><label onClick=""><input type="radio" name="buyTimeCd" value="<c:out value="${key}"/>" <c:if test="${inputForm.buyTimeCd == key}">checked</c:if>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th>買い替えの有無</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.replacementFlg"/>
									</c:import>
									<ul class="checkList01">
										<dm3lookup:lookupForEach lookupName="replacement_flg">
											<li><label onClick=""><input type="radio" name="replacementFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.replacementFlg == key}">checked</c:if>><c:out value="${value}"/></label></li>
										</dm3lookup:lookupForEach>
									</ul>
								</td>
							</tr>
							<tr>
								<th><label for="demand01">ご要望・ご質問など</label></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.requestText"/>
										</c:import>
										<textarea id="demand01" name="requestText" class="textareaType01 <c:if test="${dm3hasError}">error</c:if>" placeholder="ご要望・ご質問はこちらにご入力ください。"><c:out value="${inputForm.requestText}"/></textarea>
									</div>
								</td>
							</tr>
						</table>
				</div><!-- /.columnInner -->
			</div><!-- /.itemBlockA01 -->
			<!-- //ご売却物件の種別のjs -->
			<div class="itemBlockA01 spMb00">
				<div class="headingBlockD01 clearfix">
					<h2 class="ttl">お客様情報</h2>
				</div><!-- /.headingBlockD01 -->
				<div class="columnInner">
					<table class="tableBlockA01">
						<tr>
							<th><label for="name01">お名前<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.lname"/>
								</c:import>
								<c:set var="lnamehasError" value="${dm3hasError}"/>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.fname"/>
								</c:import>
								<div>
								姓 <input type="text" id="lname" name="lname" class="inputType05 <c:if test="${lnamehasError}">error</c:if>" placeholder="例：松下" value="<c:out value="${inputForm.getInquiryHeaderForm().lname}"/>" maxlength="30">
								名 <input type="text" id="fname" name="fname" class="inputType05 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：太郎" value="<c:out value="${inputForm.getInquiryHeaderForm().fname}"/>" maxlength="30"></div>
							</td>
						</tr>
						<tr>
							<th><label for="name02">お名前（フリガナ）<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.lnameKana"/>
								</c:import>
								<c:set var="lnameKanahasError" value="${dm3hasError}"/>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.fnameKana"/>
								</c:import>
								<div>
								姓 <input type="text" id="lnameKana" name="lnameKana" class="inputType05 <c:if test="${lnameKanahasError}">error</c:if>" placeholder="例：マツシタ" value="<c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>" maxlength="30">
								名 <input type="text" id="fnameKana" name="fnameKana" class="inputType05 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：タロウ" value="<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/>" maxlength="30"></div>
							</td>
						</tr>
						<tr>
							<th>メールアドレス<span class="mustIcon">必須</span></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.email"/>
								</c:import>
								<div><input type="email" id="email" name="email" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：panasonic＠panasonic.com" value="<c:out value="${inputForm.getInquiryHeaderForm().email}"/>" maxlength="255"></div>
							</td>
						</tr>
						<tr>
							<th><label for="tel01">電話番号<span class="mustIcon">必須</span></label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.tel"/>
								</c:import>
								<div><input type="tel" id="tel01" name="tel" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：1234567890" value="<c:out value="${inputForm.getInquiryHeaderForm().tel}"/>" maxlength="11"></div>
								<div class="mt05">※半角数字、ハイフンなしで市外局番から入力してください。<br>
									※携帯電話・PHSの番号でもご登録いただけます。</div>
							</td>
						</tr>
						<tr>
							<th><label for="fax01">FAX番号</label></th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.fax"/>
								</c:import>
								<div><input type="tel" id="fax01" name="fax" class="inputType04 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：1234567890" value="<c:out value="${inputForm.getInquiryHeaderForm().fax}"/>" maxlength="11"></div>
							</td>
						</tr>
						<tr>
							<th>ご希望の連絡方法</th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.contactType"/>
								</c:import>
								<ul class="checkList01">
									<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
										<li><label onClick=""><input type="radio" name="contactType" value="<c:out value="${key}"/>" <c:if test="${inputForm.contactType == key}">checked</c:if>><c:out value="${value}"/></label></li>
									</dm3lookup:lookupForEach>
								</ul>
							</td>
						</tr>
						<tr>
							<th>連絡可能な時間帯</th>
							<td>
								<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
									<c:param name="targetLabel" value="assessment.input.contactTime"/>
								</c:import>
								<ul class="checkList01">
									<dm3lookup:lookupForEach lookupName="inquiry_contact_time">
										<li><label onClick=""><input type="checkbox" name="contactTime" value="<c:out value="${key}"/>"
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
				<div class="itemBlockA01 spMb00" id="inputSwitchArea">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl"><label onClick=""><input type="checkbox" name="sameWithHousing" class="middle mr10" id="switchChk" value="1" <c:if test="${sameWithHousingFG || inputForm.sameWithHousing == '1'}">checked</c:if>>お客様のご住所と同一</label></h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>郵便番号<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.userZip"/>
										</c:import>
										<input type="tel" id="userZip" name="userZip" class="inputType02 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：1234567" value="<c:out value="${inputForm.userZip}"/>" maxlength="7">
										<a href="javascript:zipToAddress2();" class="addressBtn">住所検索</a>
										<a href="http://www.post.japanpost.jp/zipcode/" target="_blank" class="link02">郵便番号がわからない方</a>
										<br>
										※半角数字、ハイフンなしで入力してください。
									</div>
								</td>
							</tr>
							<tr>
								<th>都道府県<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
											<c:param name="targetLabel" value="assessment.input.userPrefCd"/>
										</c:import>
										<select id="userPrefCd" name="userPrefCd">
											<option value="">選択してください</option>
											<c:forEach items="${prefMst}" var="prefMst">
												<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${inputForm.userPrefCd == prefMst.prefCd}">selected</c:if>><c:out value="${prefMst.prefName}"/></option>
											</c:forEach>
										</select>
									</div>
								</td>
							</tr>
							<tr>
								<th>市区町村番地<span class="mustIcon">必須</span></th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.userAddress"/>
									</c:import>
									<div><input type="text" id="userAddress" name="userAddress" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：市区町村１－２３－４" value="<c:out value="${inputForm.userAddress}"/>" maxlength="50"></div>
								</td>
							</tr>
							<tr>
								<th>建物名</th>
								<td>
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.userAddressOther"/>
									</c:import>
									<div><input type="text" name="userAddressOther" class="inputType03 <c:if test="${dm3hasError}">error</c:if>" placeholder="例：○○マンション１０１号室" value="<c:out value="${inputForm.userAddressOther}"/>" maxlength="30"></div>
								</td>
							</tr>
						</table>
						<div class="mask"></div>
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
								<div class="mb20 spMb10">
									<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
										<c:param name="targetLabel" value="assessment.input.ansCd"/>
									</c:import>
									<ul class="checkList01 column02">
										<dm3lookup:lookupForEach lookupName="ans_etcNs">
											<li><label onClick=""><input type="checkbox" name="ansCd" value="<c:out value="${key}"/>"
													<c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == key}">checked</c:if></c:forEach>>
												<c:out value="${value}"/>
												</label>
											</li>
										</dm3lookup:lookupForEach>
									</ul>
									<ul class="checkList03 gearCheck2TextList">
										<li><div style="margin-bottom:5px !important;"><label onClick="" ><input type="checkbox" id="ansCd08" name="ansCd" value="008" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '008'}">checked</c:if></c:forEach>>
												<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="008"/>
											</label></div>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
												<c:param name="targetLabel" value="assessment.input.etcAnswer1"/>
											</c:import>
											<c:set var="etcAnswer1LengthErr" value="${dm3hasError}" scope="request"/>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
											<c:param name="targetLabel" value="アンケート内容1" />
											</c:import>
											<c:set var="etcAnswer1CheckErr" value="${dm3hasError}" scope="request"/>
											<input type="text" id="etcAnswer1" name="etcAnswer1" class="inputType04 <c:if test="${etcAnswer1LengthErr or etcAnswer1CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer1}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="etcAnswer" defaultValue="50"/>"></li>
										<li><div style="margin-bottom:5px !important;"><label onClick=""><input type="checkbox" id="ansCd09" name="ansCd" value="009" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '009'}">checked</c:if></c:forEach>>
												<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="009"/>
											</label></div>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
												<c:param name="targetLabel" value="assessment.input.etcAnswer2"/>
											</c:import>
											<c:set var="etcAnswer2LengthErr" value="${dm3hasError}" scope="request"/>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
											<c:param name="targetLabel" value="アンケート内容2" />
											</c:import>
											<c:set var="etcAnswer2CheckErr" value="${dm3hasError}" scope="request"/>
											<input type="text" id="etcAnswer2" name="etcAnswer2" class="inputType04 <c:if test="${etcAnswer2LengthErr or etcAnswer2CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer2}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="etcAnswer" defaultValue="50"/>"></li>
										<li><div style="margin-bottom:5px !important;"><label onClick=""><input type="checkbox" id="ansCd10" name="ansCd" value="010" <c:forEach items="${inputForm.ansCd}" var="selectedQuestion"><c:if test="${selectedQuestion == '010'}">checked</c:if></c:forEach>>
												<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="010"/>
											</label></div>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh">
												<c:param name="targetLabel" value="assessment.input.etcAnswer3"/>
											</c:import>
											<c:set var="etcAnswer3LengthErr" value="${dm3hasError}" scope="request"/>
											<c:import url="/WEB-INF/jsp/front/include/panaValidationFieldErrors.jsh" >
											<c:param name="targetLabel" value="アンケート内容3" />
											</c:import>
											<c:set var="etcAnswer3CheckErr" value="${dm3hasError}" scope="request"/>
											<input type="text" id="etcAnswer3" name="etcAnswer3" class="inputType04 <c:if test="${etcAnswer3LengthErr or etcAnswer3CheckErr}">error</c:if>" placeholder="入力してください" value="<c:out value="${inputForm.etcAnswer3}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="etcAnswer" defaultValue="50"/>"></li>
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
								<p class="link"><a href="<c:out value="${pageContext.request.contextPath}"/>/info/privacy.html" target="_blank">別ページで読む</a></p>
							</div>
							<a class="SPdisplayBlock" href="<c:out value='${commonParameters.resourceRootUrl}'/>info/privacy.html">
								<h2 class="ttl">個人情報の取り扱いについて<span class="SPdisplayBlock">【必ずお読みください】</span></h2>
							</a>
						</div><!-- /.headingBlockB02 -->
						<div class="columnInner">
							<p class="txt01 center spLeft">ご入力いただいた情報は、当社の個人情報保護方針に従い、適正に管理いたします。<br><span class="bold">下記の「個人情報の取り扱い」を必ずご一読いただき、同意のうえお申し込みください。</span></p>
							<div class="textAreaType02">
								<!--#include virtual="/common/ssi/privacy.html"-->
							</div>
							<p class="mb15 center spLeft"><label onClick=""><input type="checkbox" id="agreeChk"> 個人情報の取り扱いに同意する</label></p>
							<p class="center mb15"><button type="submit" name="confirmBtn" id="confirmBtn" class="primaryBtn01">入力内容を確認する</button></p>
						</div><!-- /.columnInner -->
				</div>
			</form>
		</div>
	</div>
</div>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->
<div class="naviWindow">
  <div>必須の入力項目が<br>あと<span></span>ヵ所あります</div>
</div>
</body>
</html>
