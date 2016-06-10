<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
物件リクエスト　確認｜<c:out value="${commonParameters.panaReSmail}"/>
</title>


<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>


<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
<script type ="text/javascript">
function linkToUrl(url, command) {
	document.inputForm.action=url;
	document.inputForm.command.value=command;
	document.inputForm.submit();
}


function searchLinkToUrl(url, command, housingKindCd) {
	var prefCdUrl = "";
	var priceLowerUrl = "";
	var priceUpperUrl = "";
	var reformPriceCheckUrl = "";
	var personalAreaLowerUrl = "";
	var personalAreaUpperUrl = "";
	var buildingAreaLowerUrl = "";
	var buildingAreaUpperUrl = "";
	var landAreaLowerUrl = "";
	var landAreaUpperUrl = "";
	var layoutCdUrl = "";
	var compDateUrl = "";
	var iconUrl = "";
	var partUrl = "";
	var moneinTimingUrl = "";
	var housingCdUrl = "";
	var searchCount = 0;
	var partCount = 0;

	prefCdUrl = "prefCd=" + $("#prefCd").val();
	searchCount = searchCount + 1;

	if(housingKindCd == "01"){
		if($("#priceLowerMansion").val() != ""){
			if(searchCount != 0){
				priceLowerUrl = "&priceLower=" + $("#priceLowerMansion").val();
			}else{
				priceLowerUrl = "priceLower=" + $("#priceLowerMansion").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#priceUpperMansion").val() != ""){
			if(searchCount != 0){
				priceUpperUrl = "&priceUpper=" + $("#priceUpperMansion").val();
			}else{
				priceUpperUrl = "priceUpper=" + $("#priceUpperMansion").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#reformCheckMansion").val() == "1"){
			if(searchCount != 0){
				reformPriceCheckUrl = "&reformPriceCheck=" + $("#reformCheckMansion").val();
			}else{
				reformPriceCheckUrl = "reformPriceCheck=" + $("#reformCheckMansion").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#personalAreaLowerMansion").val() != ""){
			if(searchCount != 0){
				personalAreaLowerUrl = "&personalAreaLower=" + $("#personalAreaLowerMansion").val();
			}else{
				personalAreaLowerUrl = "personalAreaLower=" + $("#personalAreaLowerMansion").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#personalAreaUpperMansion").val() != ""){
			if(searchCount != 0){
				personalAreaUpperUrl = "&personalAreaUpper=" + $("#personalAreaUpperMansion").val();
			}else{
				personalAreaUpperUrl = "personalAreaUpper=" + $("#personalAreaUpperMansion").val();
			}
			searchCount = searchCount + 1;
		}

		var layoutValue = document.all["layoutCdMansion"];
		if (layoutValue != null && layoutValue.length>0) {
			var layoutArray = "";
			for (var i = 0;i<layoutValue.length;i++) {
				if(layoutValue[i].value != ""){
					if(layoutArray == ""){
						layoutArray = layoutValue[i].value;
					}else{
						layoutArray = layoutArray+ "," + layoutValue[i].value;
					}

				}
			}
			if(searchCount != 0){
				layoutCdUrl = "&layoutCd=" + layoutArray;
			}else{
				layoutCdUrl = "layoutCd=" + layoutArray;
			}
			searchCount = searchCount + 1;
		}

		if($("#compDateMansion").val() != ""){
			if(searchCount != 0){
				compDateUrl = "&compDate=" + $("#compDateMansion").val();
			}else{
				compDateUrl = "compDate=" + $("#compDateMansion").val();
			}
			searchCount = searchCount + 1;
		}

		var iconValue = document.all["iconCdMansion"];

		if (iconValue != null && iconValue.length>0) {
			var iconArray = "";
			for (var i = 0;i<iconArray.length;i++) {
				if(iconArray[i].value != ""){
					if(iconArray == ""){
						iconArray = iconValue[i].value;
					}else{
						iconArray = iconArray+ "," + iconValue[i].value;
					}

				}
			}
			if(searchCount != 0){
				iconUrl = "&iconCd=" + iconArray;
			}else{
				iconUrl = "iconCd=" + iconArray;
			}
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd == "02"){

		if($("#priceLowerHouse").val() != ""){
			if(searchCount != 0){
				priceLowerUrl = "&priceLower=" + $("#priceLowerHouse").val();
			}else{
				priceLowerUrl = "priceLower=" + $("#priceLowerHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#priceUpperHouse").val() != ""){
			if(searchCount != 0){
				priceUpperUrl = "&priceUpper=" + $("#priceUpperHouse").val();
			}else{
				priceUpperUrl = "priceUpper=" + $("#priceUpperHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#reformCheckHouse").val() != ""){
			if(searchCount != 0){
				reformPriceCheckUrl = "&reformPriceCheck=" + $("#reformCheckHouse").val();
			}else{
				reformPriceCheckUrl = "reformPriceCheck=" + $("#reformCheckHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#buildingAreaLowerHouse").val() != ""){
			if(searchCount != 0){
				buildingAreaLowerUrl = "&buildingAreaLower=" + $("#buildingAreaLowerHouse").val();
			}else{
				buildingAreaLowerUrl = "buildingAreaLower=" + $("#buildingAreaLowerHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#buildingAreaUpperHouse").val() != ""){
			if(searchCount != 0){
				buildingAreaUpperUrl = "&buildingAreaUpper=" + $("#buildingAreaUpperHouse").val();
			}else{
				buildingAreaUpperUrl = "buildingAreaUpper=" + $("#buildingAreaUpperHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#landAreaLowerHouse").val() != ""){
			if(searchCount != 0){
				landAreaLowerUrl = "&landAreaLower=" + $("#landAreaLowerHouse").val();
			}else{
				landAreaLowerUrl = "landAreaLower=" + $("#landAreaLowerHouse").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#landAreaUpperHouse").val() != ""){
			if(searchCount != 0){
				landAreaUpperUrl = "&landAreaUpper=" + $("#landAreaUpperHouse").val();
			}else{
				landAreaUpperUrl = "landAreaUpper=" + $("#landAreaUpperHouse").val();
			}
			searchCount = searchCount + 1;
		}

		var layoutValue = document.all["layoutCdHouse"];
		if (layoutValue != null && layoutValue.length>0) {
			var layoutArray = "";
			for (var i = 0;i<layoutValue.length;i++) {
				if(layoutValue[i].value != ""){
					if(layoutArray == ""){
						layoutArray = layoutValue[i].value;
					}else{
						layoutArray = layoutArray+ "," + layoutValue[i].value;
					}

				}
			}
			if(searchCount != 0){
				layoutCdUrl = "&layoutCd=" + layoutArray;
			}else{
				layoutCdUrl = "layoutCd=" + layoutArray;
			}
			searchCount = searchCount + 1;
		}

		if($("#compDateHouse").val() != ""){
			if(searchCount != 0){
				compDateUrl = "&compDate=" + $("#compDateHouse").val();
			}else{
				compDateUrl = "compDate=" + $("#compDateHouse").val();
			}
			searchCount = searchCount + 1;
		}

		var iconValue = document.all["iconCdHouse"];
		if (iconValue != null && iconValue.length>0) {
			var iconArray = "";
			for (var i = 0;i<iconArray.length;i++) {
				if(iconArray[i].value != ""){
					if(iconArray == ""){
						iconArray = iconValue[i].value;
					}else{
						iconArray = iconArray+ "," + iconValue[i].value;
					}

				}
			}
			if(searchCount != 0){
				iconUrl = "&iconCd=" + iconArray;
			}else{
				iconUrl = "iconCd=" + iconArray;
			}
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd == "03"){

		if($("#priceLowerLand").val() != ""){
			if(searchCount != 0){
				priceLowerUrl = "&priceLower=" + $("#priceLowerLand").val();
			}else{
				priceLowerUrl = "priceLower=" + $("#priceLowerLand").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#priceUpperLand").val() != ""){
			if(searchCount != 0){
				priceUpperUrl = "&priceUpper=" + $("#priceUpperLand").val();
			}else{
				priceUpperUrl = "priceUpper=" + $("#priceUpperLand").val();
			}
			searchCount = searchCount + 1;
		}


		if($("#buildingAreaLowerLand").val() != ""){
			if(searchCount != 0){
				buildingAreaLowerUrl = "&buildingAreaLower=" + $("#buildingAreaLowerLand").val();
			}else{
				buildingAreaLowerUrl = "buildingAreaLower=" + $("#buildingAreaLowerLand").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#buildingAreaUpperLand").val() != ""){
			if(searchCount != 0){
				buildingAreaUpperUrl = "&buildingAreaUpper=" + $("#buildingAreaUpperLand").val();
			}else{
				buildingAreaUpperUrl = "buildingAreaUpper=" + $("#buildingAreaUpperLand").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#landAreaLowerLand").val() != ""){
			if(searchCount != 0){
				landAreaLowerUrl = "&landAreaLower=" + $("#landAreaLowerLand").val();
			}else{
				landAreaLowerUrl = "landAreaLower=" + $("#landAreaLowerLand").val();
			}
			searchCount = searchCount + 1;
		}

		if($("#landAreaUpperLand").val() != ""){
			if(searchCount != 0){
				landAreaUpperUrl = "&landAreaUpper=" + $("#landAreaUpperLand").val();
			}else{
				landAreaUpperUrl = "landAreaUpper=" + $("#landAreaUpperLand").val();
			}
			searchCount = searchCount + 1;
		}

	}

	if(searchCount != 0){
		urlPattern = "?" + prefCdUrl + priceLowerUrl + priceUpperUrl + reformPriceCheckUrl + personalAreaLowerUrl + personalAreaUpperUrl
			+ buildingAreaLowerUrl + buildingAreaUpperUrl + landAreaLowerUrl + landAreaUpperUrl + layoutCdUrl + compDateUrl
			+ iconUrl;
	}

	$("#urlPattern").val(urlPattern);

	linkToUrl(url, command);
}
</script>

</head>
<body>
	
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<!--[if lte IE 9]><script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=REQ-01-06" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">物件リクエストconfirm</h1>
				${housingRequestForm.getStartDate()}
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step02">
				<ul>
					<li><span>入力</span></li>
					<li class="current"><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<form action="comfirm" method="post" name="inputForm">

				<input type="hidden" name="command" value=""/>
				<input type="hidden" id="urlPattern" name="urlPattern" value="">
				<input type="hidden" name="housingRequestId" value="<c:out value="${housingRequestForm.getHousingRequestId()}"/>">
				<input type="hidden" name="model" value="<c:out value="${housingRequestForm.getModel()}"/>"/>

				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件種別</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<p class="contentsInner01 mb00">
						<input type="hidden" name="housingKindCd" value="<c:out value="${housingRequestForm.getHousingKindCd()}"/>"/>
						<dm3lookup:lookupForEach lookupName="hosuing_kind">
							<c:if test="${housingRequestForm.getHousingKindCd() == key}">
								<c:out value="${value}"/>
							</c:if>
						</dm3lookup:lookupForEach>

						</p>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件所在地</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>都道府県</th>

								<td><c:out value="${prefName}"/>
									<input type="hidden" id="prefCd" name="prefCd" value="<c:out value="${housingRequestForm.getPrefCd()}"/>"/>
								</td>
							</tr>
							
							<tr>
								<th>路線: </th>
									<td>	<c:out value="${routeName}"/>
									<input type="hidden" name="routeCd" id="routeCd" value="<c:out value="${housingRequestForm.getRouteCd()}"/>"/></td>
							</tr>
							
							<tr>
								<th>駅: </th>
									<td><c:out value="${stationName}"/>
									<input type="hidden" name="stationCd" id="stationCd" value="<c:out value="${housingRequestForm.stationCd}"/>"/></td>
							</tr>
							
							<tr>
								<th>Hope_Request_Text: </th>									
									<td>	<c:out value="${housingRequestForm.getHopeRequestTest()}"/>
									<input type="hidden" name="hopeRequestTest" id="hopeRequestTest" value="<c:out value="${housingRequestForm.getHopeRequestTest()}"/>"/></td>
									
							</tr>
							
							<tr>
								<th>Start Date: </th>	
									<td>	<c:out value="${housingRequestForm.getStartDate()}"/>
									<input type="hidden" name="startDate" id="startDate" value="<c:out value="${housingRequestForm.getStartDate()}"/>"/></td>
							</tr>
							
							<tr>
								<th>End Date: </th>	
									<td>	<c:out value="${housingRequestForm.getEndDate()}"/>
									<input type="hidden" name="endDate" id="endDate" value="<c:out value="${housingRequestForm.getEndDate()}"/>"/></td>
							</tr>
							
							<tr style="display: none">
								<th class="normal">※「市区町村」または<br class="SPdisplayNone">「沿線」の、<br class="SPdisplayBlock">どちらかで<br class="SPdisplayNone">検索してください。</th>
								<td>
									<ul class="listTypeB01">
										<li>市区町村　｜　渋谷区</li>
										<li>沿線　｜　指定なし</li>
									</ul>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">基本条件 </h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							
							<tr>
								<th>予算</th>
								<td>
									<dm3lookup:lookupForEach lookupName="price">
										<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
											<input type="hidden" name="priceLowerMansion" id="priceLowerMansion" value="<c:out value="${housingRequestForm.getPriceLowerMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerMansion() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<input type="hidden" name="priceLowerHouse" id="priceLowerHouse" value="<c:out value="${housingRequestForm.getPriceLowerHouse()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerHouse() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<input type="hidden" name="priceLowerLand" id="priceLowerLand" value="<c:out value="${housingRequestForm.getPriceLowerLand()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerLand() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

									</dm3lookup:lookupForEach>

									<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
											<c:if test="${!empty housingRequestForm.getPriceLowerMansion() != '' || !empty housingRequestForm.getPriceUpperMansion() != ''}">
											　～　
										</c:if>
									</c:if>
									<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
										<c:if test="${!empty housingRequestForm.getPriceLowerHouse() != '' || !empty housingRequestForm.getPriceUpperHouse() != ''}">
											　～　
										</c:if>
									</c:if>
									<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
										<c:if test="${!empty housingRequestForm.getPriceLowerLand() != '' || !empty housingRequestForm.getPriceUpperLand() != ''}">
											　～　
										</c:if>
									</c:if>

									<dm3lookup:lookupForEach lookupName="price">
										<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
											<input type="hidden" name="priceUpperMansion" id="priceUpperMansion" value="<c:out value="${housingRequestForm.getPriceUpperMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperMansion() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>

											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<input type="hidden" name="priceUpperHouse" id="priceUpperHouse" value="<c:out value="${housingRequestForm.getPriceUpperHouse()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperHouse() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<input type="hidden" name="priceUpperLand" id="priceUpperLand" value="<c:out value="${housingRequestForm.getPriceUpperLand()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperLand() == key}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

									</dm3lookup:lookupForEach>

									<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
										<c:if test="${housingRequestForm.getReformCheckMansion() == '1'}">
											<input type="hidden" name="reformCheckMansion" id="reformCheckMansion" value="<c:out value="${housingRequestForm.getReformCheckMansion()}"/>"/>
											リフォーム価格込みで検索する
										</c:if>
									</c:if>

									<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
										<c:if test="${housingRequestForm.getReformCheckHouse() == '1'}">
											<input type="hidden" name="reformCheckHouse" id="reformCheckHouse" value="<c:out value="${housingRequestForm.getReformCheckHouse()}"/>"/>
											リフォーム価格込みで検索する
										</c:if>
									</c:if>
								</td>
							</tr>
							<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
								<tr>
									<th>専有面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<input type="hidden" name="personalAreaLowerMansion" id="personalAreaLowerMansion" value="<c:out value="${housingRequestForm.getPersonalAreaLowerMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPersonalAreaLowerMansion() == key}"><c:out value="${value}"/>m&sup2;</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${!empty housingRequestForm.getPersonalAreaLowerMansion() != '' || !empty housingRequestForm.getPersonalAreaUpperMansion() != ''}">
										　～　
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<input type="hidden" name="personalAreaUpperMansion" id="personalAreaUpperMansion" value="<c:out value="${housingRequestForm.getPersonalAreaUpperMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPersonalAreaUpperMansion() == key}"><c:out value="${value}"/>m&sup2;</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
							</c:if>

							<c:if test="${housingRequestForm.getHousingKindCd() != '01'}">
								<tr>
									<th>建物面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="buildingAreaLowerHouse" id="buildingAreaLowerHouse" value="<c:out value="${housingRequestForm.getBuildingAreaLowerHouse()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaLowerHouse() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="buildingAreaLowerLand" id="buildingAreaLowerLand" value="<c:out value="${housingRequestForm.getBuildingAreaLowerLand()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaLowerLand() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<c:if test="${!empty housingRequestForm.getBuildingAreaLowerHouse() != '' || !empty housingRequestForm.getBuildingAreaUpperHouse() != ''}">
												　～　
											</c:if>
										</c:if>
										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<c:if test="${!empty housingRequestForm.getBuildingAreaLowerLand() != '' || !empty housingRequestForm.getBuildingAreaUpperLand() != ''}">
												　～　
											</c:if>
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="buildingAreaUpperHouse" id="buildingAreaUpperHouse" value="<c:out value="${housingRequestForm.getBuildingAreaUpperHouse()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaUpperHouse() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="buildingAreaUpperLand" id="buildingAreaUpperLand" value="<c:out value="${housingRequestForm.getBuildingAreaUpperLand()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaUpperLand() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
								<tr>
									<th>土地面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="landAreaLowerHouse" id="landAreaLowerHouse" value="<c:out value="${housingRequestForm.getLandAreaLowerHouse()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaLowerHouse() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="landAreaLowerLand" id="landAreaLowerLand" value="<c:out value="${housingRequestForm.getLandAreaLowerLand()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaLowerLand() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<c:if test="${!empty housingRequestForm.getLandAreaLowerHouse() != '' || !empty housingRequestForm.getLandAreaUpperHouse() != ''}">
												　～　
											</c:if>
										</c:if>
										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<c:if test="${!empty housingRequestForm.getLandAreaLowerLand() != '' || !empty housingRequestForm.getLandAreaUpperLand() != ''}">
												　～　
											</c:if>
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="landAreaUpperHouse" id="landAreaUpperHouse" value="<c:out value="${housingRequestForm.getLandAreaUpperHouse()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaUpperHouse() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="landAreaUpperLand" id="landAreaUpperLand" value="<c:out value="${housingRequestForm.getLandAreaUpperLand()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaUpperLand() == key}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
							</c:if>
							<c:if test="${housingRequestForm.getHousingKindCd() != '03'}">
								<tr>
									<th>間取り</th>
									<td>
										<dm3lookup:lookupForEach lookupName="layoutCd">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<c:forEach items="${housingRequestForm.getLayoutCdMansion()}" varStatus="LayoutCd">
													<c:if test="${housingRequestForm.getLayoutCdMansion()[LayoutCd.index] == key}">
														<input type="hidden" name="layoutCdMansion" id="layoutCdMansion" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"></c:out>
													</c:if>

						                        </c:forEach>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<c:forEach items="${housingRequestForm.getLayoutCdHouse()}" varStatus="LayoutCd">

													<c:if test="${housingRequestForm.getLayoutCdHouse()[LayoutCd.index] == key}">
														<input type="hidden" name="layoutCdHouse" id="layoutCdHouse" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　">
													</c:out></c:if>
						                        </c:forEach>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
								<tr>
									<th>築年数</th>
									<td>
										<dm3lookup:lookupForEach lookupName="compDate">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<input type="hidden" name="compDateMansion" id="compDateMansion" value="<c:out value="${housingRequestForm.getCompDateMansion()}"/>"/>
												<c:if test="${housingRequestForm.getCompDateMansion() == key}"><c:out value="${value}"></c:out></c:if>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="compDateHouse" id="compDateHouse" value="<c:out value="${housingRequestForm.getCompDateHouse()}"/>"/>
												<c:if test="${housingRequestForm.getCompDateHouse() == key}"><c:out value="${value}"></c:out></c:if>
											</c:if>
										</dm3lookup:lookupForEach>
									</td>
								</tr>
								<tr>
									<th>おすすめのポイント</th>
									<td>
										<dm3lookup:lookupForEach lookupName="recommend_point_icon_list">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<c:forEach items="${housingRequestForm.getIconCdMansion()}" varStatus="iconCd">
													<c:if test="${housingRequestForm.getIconCdMansion()[iconCd.index] == key}">
														<input type="hidden" name="iconCdMansion" id="iconCdMansion" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"></c:out>
													</c:if>

						                        </c:forEach>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<c:forEach items="${housingRequestForm.getIconCdHouse()}" varStatus="iconCd">
													<c:if test="${housingRequestForm.getIconCdHouse()[iconCd.index] == key}">
														<input type="hidden" name="iconCdHouse" id="iconCdHouse" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"></c:out>
													</c:if>

						                        </c:forEach>
											</c:if>
										</dm3lookup:lookupForEach>
									</td>
								</tr>
							</c:if>
							
							
							
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="contentsInner01 mt30 mb15 spMt10 spPb10">
					<ul class="btnList01">
						<li><button type="button" name="" class="backBtn" onclick="linkToUrl('../input/', 'back');">修正する</button></li>
						<li><button type="button" name="" class="primaryBtn01" onclick="searchLinkToUrl('../result/', 'comp', '<c:out value="${housingRequestForm.getHousingKindCd()}"/>');">この内容で<br class="SPdisplayBlock">送信する</button></li>
					</ul>
				</div>
				<dm3token:oneTimeToken/>
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>