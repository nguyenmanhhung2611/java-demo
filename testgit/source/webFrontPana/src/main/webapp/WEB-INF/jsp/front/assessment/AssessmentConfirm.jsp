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

<title>査定のお申し込み　確認｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" type="text/css" media="screen,print" />

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type="text/javascript">
	function linkToUrl(url, cmd) {
		if (cmd != "back") {
			url = url + urlCreater();
		}
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function urlCreater() {
		var tempUrl = [];
		tempUrl.push('?buyHousingType=');
		tempUrl.push(document.inputForm.buyHousingType.value);
		if (document.inputForm.buyHousingType.value == "01") {
			if (document.inputForm.layoutCd1.value != null && document.inputForm.layoutCd1.value.length > 0) {
				tempUrl.push('&layoutCd1=');
				tempUrl.push(document.inputForm.layoutCd1.value);
			}
		}
		if (document.inputForm.buyHousingType.value == "02") {
			if (document.inputForm.layoutCd2.value != null && document.inputForm.layoutCd2.value.length > 0) {
				tempUrl.push('&layoutCd2=');
				tempUrl.push(document.inputForm.layoutCd2.value);
			}
			if (document.inputForm.landAreaCrs2.value != null && document.inputForm.landAreaCrs2.value.length > 0) {
				tempUrl.push('&landAreaCrs2=');
				tempUrl.push(document.inputForm.landAreaCrs2.value);
			}
			if (document.inputForm.buildingAreaCrs.value != null && document.inputForm.buildingAreaCrs.value.length > 0) {
				tempUrl.push('&buildingAreaCrs=');
				tempUrl.push(document.inputForm.buildingAreaCrs.value);
			}
		}
		if (document.inputForm.prefCd.value != null && document.inputForm.prefCd.value.length > 0) {
			tempUrl.push('&prefCd=');
			tempUrl.push(document.inputForm.prefCd.value);
		}
		if (document.inputForm.presentCd.value != null && document.inputForm.presentCd.value.length > 0) {
			tempUrl.push('&presentCd=');
			tempUrl.push(document.inputForm.presentCd.value);
		}
		if (document.inputForm.buyTimeCd.value != null && document.inputForm.buyTimeCd.value.length > 0) {
			tempUrl.push('&buyTimeCd=');
			tempUrl.push(document.inputForm.buyTimeCd.value);
		}
		if (document.inputForm.replacementFlg.value != null && document.inputForm.replacementFlg.value.length > 0) {
			tempUrl.push('&replacementFlg=');
			tempUrl.push(document.inputForm.replacementFlg.value);
		}
		if (document.inputForm.contactType.value != null && document.inputForm.contactType.value.length > 0) {
			tempUrl.push('&contactType=');
			tempUrl.push(document.inputForm.contactType.value);
		}
		if (document.inputForm.contactTime != null && document.inputForm.contactTime.length > 0) {
			tempUrl.push('&contactTime=');
			for (var i = 0, max = document.inputForm.contactTime.length; i < max; i++) {
				if (i > 0) tempUrl.push(',');
				tempUrl.push(document.inputForm.contactTime[i].value);
			}
		} else if (document.inputForm.contactTime != null && document.inputForm.contactTime.value.length > 0) {
			tempUrl.push('&contactTime=');
			tempUrl.push(document.inputForm.contactTime.value);
		}
		if (document.inputForm.sameWithHousing.value != null && document.inputForm.sameWithHousing.value.length > 0) {
			tempUrl.push('&sameWithHousing=');
			tempUrl.push(document.inputForm.sameWithHousing.value);
		} else {
			if (document.inputForm.userPrefCd.value != null && document.inputForm.userPrefCd.value.length > 0) {
				tempUrl.push('&userPrefCd=');
				tempUrl.push(document.inputForm.userPrefCd.value);
			}
		}
		if (document.inputForm.ansCd != null && document.inputForm.ansCd.length > 0) {
			tempUrl.push('&ansCd=');
			for (var i = 0, max = document.inputForm.ansCd.length; i < max; i++) {
				if (i > 0) tempUrl.push(',');
				tempUrl.push(document.inputForm.ansCd[i].value);
			}
		} else if (document.inputForm.ansCd != null && document.inputForm.ansCd.value.length > 0) {
			tempUrl.push('&ansCd=');
			tempUrl.push(document.inputForm.ansCd.value);
		}

		return tempUrl.join('');
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
				<h1 class="ttl">査定のお申し込み</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step02">
				<ul>
					<li><span>入力</span></li>
					<li class="current"><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<form action="" method="post">
				<div class="contentsInner01">
					<p class="f14">お申し込み内容をご確認ください。</p>
				</div>
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">ご売却希望物件について</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
									<th>ご売却物件の種別</th>
									<td class="spPb00">
										<ul class="checkList02">
											<li class="mansion mb00 spMb20">
												<dl>
													<c:if test="${inputForm.buyHousingType == '01'}">
														<dt><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="01"/></dt>
														<dd>
															<div class="cell">
																<span class="bold">間取り</span>
																<p class="mt05 pl10 spPl00">部屋数：<dm3lookup:lookup lookupName="layoutCd" lookupKey="${inputForm.layoutCd1}"/></p>
															</div>
															<div class="cell">
																<span class="bold">専有面積</span>
																<p class="mt05 pl10 spPl00">約　<c:out value="${inputForm.personalArea}"/>　m&sup2;</p>
															</div>
															<div class="cell">
																<span class="bold">築年数</span>
																<p class="mt05 pl10 spPl00"><c:out value="${inputForm.buildAge1}"/>　年</p>
															</div>
														</dd>
													</c:if>
													<c:if test="${inputForm.buyHousingType == '02'}">
														<dt><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="02"/></dt>
														<dd>
															<div class="cell">
																<span class="bold">間取り</span>
																<p class="mt05 pl10 spPl00">部屋数：<dm3lookup:lookup lookupName="layoutCd" lookupKey="${inputForm.layoutCd2}"/></p>
															</div>
															<div class="cell">
																<span class="bold">築年数</span>
																<p class="mt05 pl10 spPl00"><c:out value="${inputForm.buildAge2}"/>　年</p>
															</div>
															<div class="cell">
																<span class="bold">土地面積</span>
																<p class="mt05 pl10 spPl00">約　<c:out value="${inputForm.landArea2}"/>　<c:if test="${inputForm.landAreaCrs2 == '00'}">坪</c:if><c:if test="${inputForm.landAreaCrs2 == '01'}">m&sup2;</c:if></p>
															</div>
															<div class="cell">
																<span class="bold">建物面積</span>
																<p class="mt05 pl10 spPl00">約　<c:out value="${inputForm.buildingArea}"/>　<c:if test="${inputForm.buildingAreaCrs == '00'}">坪</c:if><c:if test="${inputForm.buildingAreaCrs == '01'}">m&sup2;</c:if></p>
															</div>
														</dd>
													</c:if>
													<c:if test="${inputForm.buyHousingType == '03'}">
														<dt><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="03"/></dt>
														<dd>
															<div class="cell">
																<span class="bold">土地面積</span>
																<p class="mt05 pl10 spPl00">約　<c:out value="${inputForm.landArea3}"/>　m&sup2;</p>
															</div>
														</dd>
													</c:if>
												</dl>
											</li>
										</ul>
									</td>
							</tr>
							<tr>
								<th class="pb10">ご売却物件所在地</th>
								<td class="pb10 SPdisplayNone">&nbsp;</td>
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
								<th>現況</th>
								<td><dm3lookup:lookup lookupName="inquiry_present_cd" lookupKey="${inputForm.presentCd}"/></td>
							</tr>
							<tr>
								<th>ご売却予定の時期</th>
								<td><dm3lookup:lookup lookupName="inquiry_buy_time_cd" lookupKey="${inputForm.buyTimeCd}"/></td>
							</tr>
							<tr>
								<th>買い替えの有無</th>
								<td><dm3lookup:lookup lookupName="replacement_flg" lookupKey="${inputForm.replacementFlg}"/></td>
							</tr>
							<tr>
								<th>ご要望・ご質問など</th>
								<td><c:out value="${inputForm.requestTextHyoji}" escapeXml="false"/></td>
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
								<td style="word-break:break-all"><c:out value="${inputForm.getInquiryHeaderForm().lname}"/>　<c:out value="${inputForm.getInquiryHeaderForm().fname}"/></td>
							</tr>
							<tr>
								<th>お名前（フリガナ）</th>
								<td style="word-break:break-all"><c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>　<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/></td>
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
								<th>ご希望の連絡方法</th>
								<td><dm3lookup:lookup lookupName="inquiry_contact_type" lookupKey="${inputForm.contactType}"/></td>
							</tr>
							<tr>
								<th>連絡可能な時間帯</th>
								<td>
									<c:forEach items="${inputForm.getContactTime()}" varStatus="contactTime">
										<dm3lookup:lookup lookupName="inquiry_contact_time" lookupKey="${inputForm.getContactTime()[contactTime.index]}"/>&nbsp;&nbsp;
									</c:forEach>
								</td>
							</tr>
							<c:if test="${inputForm.sameWithHousing == '1'}">
							<tr>
								<th>売却物件と同じ</th>
							</tr>
							</c:if>
							<c:if test="${inputForm.sameWithHousing == null || inputForm.sameWithHousing != '1'}">
							<tr>
								<th>郵便番号</th>
								<td><c:if test="${!empty inputForm.userZip}"><c:out value="${fn:substring(inputForm.userZip,0,3)}-${fn:substring(inputForm.userZip,3,7)}"/></c:if></td>
							</tr>
							<tr>
								<th>都道府県</th>
								<td><c:out value="${inputForm.userPrefName}"/></td>
							</tr>
							<tr>
								<th>市区町村番地</th>
								<td><c:out value="${inputForm.userAddress}"/></td>
							</tr>
							<tr>
								<th>建物名</th>
								<td><c:out value="${inputForm.userAddressOther}"/></td>
							</tr>
							</c:if>
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
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<input type="hidden" name="buyHousingType" value="<c:out value="${inputForm.buyHousingType}"/>">
	<input type="hidden" name="layoutCd1" value="<c:out value="${inputForm.layoutCd1}"/>">
	<input type="hidden" name="personalArea" value="<c:out value="${inputForm.personalArea}"/>">
	<input type="hidden" name="buildAge1" value="<c:out value="${inputForm.buildAge1}"/>">
	<input type="hidden" name="layoutCd2" value="<c:out value="${inputForm.layoutCd2}"/>">
	<input type="hidden" name="buildAge2" value="<c:out value="${inputForm.buildAge2}"/>">
	<input type="hidden" name="landArea2" value="<c:out value="${inputForm.landArea2}"/>">
	<input type="hidden" name="landAreaCrs2" value="<c:out value="${inputForm.landAreaCrs2}"/>">
	<input type="hidden" name="buildingArea" value="<c:out value="${inputForm.buildingArea}"/>">
	<input type="hidden" name="buildingAreaCrs" value="<c:out value="${inputForm.buildingAreaCrs}"/>">
	<input type="hidden" name="landArea3" value="<c:out value="${inputForm.landArea3}"/>">
	<input type="hidden" name="landAreaCrs3" value="<c:out value="${inputForm.landAreaCrs3}"/>">
	<input type="hidden" name="zip" value="<c:out value="${inputForm.zip}"/>">
	<input type="hidden" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
	<input type="hidden" name="prefName" value="<c:out value="${inputForm.prefName}"/>">
	<input type="hidden" name="address" value="<c:out value="${inputForm.address}"/>">
	<input type="hidden" name="addressOther" value="<c:out value="${inputForm.addressOther}"/>">
	<input type="hidden" name="presentCd" value="<c:out value="${inputForm.presentCd}"/>">
	<input type="hidden" name="buyTimeCd" value="<c:out value="${inputForm.buyTimeCd}"/>">
	<input type="hidden" name="replacementFlg" value="<c:out value="${inputForm.replacementFlg}"/>">
	<input type="hidden" name="requestText" value="<c:out value="${inputForm.requestText}"/>">
	<input type="hidden" name="requestTextHyoji" value="<c:out value="${inputForm.requestTextHyoji}"/>">
	<input type="hidden" name="lname" value="<c:out value="${inputForm.getInquiryHeaderForm().lname}"/>">
	<input type="hidden" name="fname" value="<c:out value="${inputForm.getInquiryHeaderForm().fname}"/>">
	<input type="hidden" name="lnameKana" value="<c:out value="${inputForm.getInquiryHeaderForm().lnameKana}"/>">
	<input type="hidden" name="fnameKana" value="<c:out value="${inputForm.getInquiryHeaderForm().fnameKana}"/>">
	<input type="hidden" name="email" value="<c:out value="${inputForm.getInquiryHeaderForm().email}"/>">
	<input type="hidden" name="tel" value="<c:out value="${inputForm.getInquiryHeaderForm().tel}"/>">
	<input type="hidden" name="fax" value="<c:out value="${inputForm.getInquiryHeaderForm().fax}"/>">
	<input type="hidden" name="contactType" value="<c:out value="${inputForm.contactType}"/>">
	<c:forEach items="${inputForm.getContactTime()}" varStatus="contactTime">
		<input type="hidden" name="contactTime" value="<c:out value="${inputForm.getContactTime()[contactTime.index]}"/>"/>
	</c:forEach>
	<input type="hidden" name="sameWithHousing" value="<c:out value="${inputForm.sameWithHousing}"/>">
	<input type="hidden" name="userZip" value="<c:out value="${inputForm.userZip}"/>">
	<input type="hidden" name="userPrefCd" value="<c:out value="${inputForm.userPrefCd}"/>">
	<input type="hidden" name="userPrefName" value="<c:out value="${inputForm.userPrefName}"/>">
	<input type="hidden" name="userAddress" value="<c:out value="${inputForm.userAddress}"/>">
	<input type="hidden" name="userAddressOther" value="<c:out value="${inputForm.userAddressOther}"/>">
	<c:forEach items="${inputForm.getAnsCd()}" varStatus="ansCd">
		<input type="hidden" name="ansCd" value="<c:out value="${inputForm.getAnsCd()[ansCd.index]}"/>"/>
	</c:forEach>
	<input type="hidden" name="etcAnswer1" value="<c:out value="${inputForm.etcAnswer1}"/>">
	<input type="hidden" name="etcAnswer2" value="<c:out value="${inputForm.etcAnswer2}"/>">
	<input type="hidden" name="etcAnswer3" value="<c:out value="${inputForm.etcAnswer3}"/>">
	<dm3token:oneTimeToken/>
</form>

<!--#include virtual="/common/ssi/simple-footer-S.html"-->

</body>
</html>
