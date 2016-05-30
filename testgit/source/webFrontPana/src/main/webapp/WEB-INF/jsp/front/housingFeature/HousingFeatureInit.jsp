<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${featurePageInfo.featurePageName}"/>,パナソニック,<c:out value="${commonParameters.panasonicSiteEnglish}"/>,<c:out value="${commonParameters.panasonicSiteJapan}"/>,Re2,リー・スクエア">
<meta name="description" content="<c:out value="${description}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><c:out value="${featurePageInfo.featurePageName}"/>｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/css/building.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/css/special.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/jquery.fancybox.css" rel="stylesheet">

<c:set var="pagingForm" value="${housingFeatureForm}" scope="request" />
<c:set var="defaultUrl" value="${pageContext.request.contextPath}/buy/special/${param.featurePageId}/" scope="request" />
<c:import url="/WEB-INF/jsp/front/include/common/paginate.jsh" />

<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>buy/js/buy.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.tooltipster.min.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type ="text/javascript">

$(document).ready(function(){

	$(".favadd").fancybox({
		fitToView : false,
		width : '450px',
		closeClick : false,
		closeBtn : false,
		openEffect : 'none',
		closeEffect : 'none'
	});
	$(".favadd").on('click', function() {
		setTimeout(function() {
			$.fancybox.close();
			$("#personalInfo .favorite").text(getFavoriteCount());
		}, 1500)

	});

	$(".recicon").fancybox({
		fitToView	: false,
		width		: '400px',
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none',
		iframe		: {
			scrolling : 'no',
		}
	});

	 $("#sortOrder1").click(function(){
		 if($("#sortUpdDateValue").val() == '4'){
			 $("#sortUpdDateValue").attr("value","3");
		 }
		 else{
			 $("#sortUpdDateValue").attr("value","4");
		 }

	 });
	  $("#sortOrder2").click(function(){

			 if($("#sortPriceValue").val() == '2'){
				 $("#sortPriceValue").attr("value","1");
			 }
			 else{
				 $("#sortPriceValue").attr("value","2");
			 }
	 });
	  $("#sortOrder3").click(function(){

			 if($("#sortBuildDateValue").val() == '6'){
				 $("#sortBuildDateValue").attr("value","5");
			 }
			 else{
				 $("#sortBuildDateValue").attr("value","6");
			 }
	 });
	  $("#sortOrder4").click(function(){

			 if($("#sortWalkTimeValue").val() == '8'){
				 $("#sortWalkTimeValue").attr("value","7");
			 }
			 else{
				 $("#sortWalkTimeValue").attr("value","8");
			 }
	 });

});

function getFavoriteCount() {
	var arrStr = document.cookie.split(";");
	for (var i = 0; i < arrStr.length; i++) {
		var temp = arrStr[i].split("=");
		if ($.trim(temp[0]) == "favoriteCount") {
			return unescape(temp[1]);
		}
	}
}

function linkToUrl(url, sysHousingCd) {
	document.inputForm.action=url;
	document.inputForm.sysHousingCd.value=sysHousingCd;
	document.inputForm.submit();
}
function orderLinkToUrl(url, keyOrder) {
	var urlChange = "&sortPriceValue=" + $("#sortPriceValue").val() + "&sortUpdDateValue=" + $("#sortUpdDateValue").val() + "&keyOrderType=" + keyOrder + "&sortWalkTimeValue=" + $("#sortWalkTimeValue").val()
	+ "&sortBuildDateValue=" + $("#sortBuildDateValue").val();

	document.inputForm.action = encodeURI(url + urlChange);

	document.inputForm.keyOrderType.value = keyOrder;
	document.inputForm.submit();
}

function orderSelect(url) {
	document.inputForm.action = url;
	document.inputForm.keyOrderType.value = $("#selKeyOrderType").val();
	document.inputForm.submit();
}
</script>

</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<form action="init" method="post" name="inputForm">
	<input type="hidden" name="selectedPage" value="<c:out value="${housingFeatureForm.getSelectedPage()}"/>">
	<input type="hidden" name="keyOrderType"  value="<c:out value="${housingFeatureForm.getKeyOrderType()}"/>">
	<input type="hidden" name="sysHousingCd"  value="">

	<div id="contents">
		<div id="contentsInner" class="listPage">

			<c:import url="../include/common/topicPath.jsh?pattern=SPE-01-01&featureName=${featurePageInfo.featurePageName}" />

			<c:if test="${buildingList.size() == '0'}">
				<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
					<p class="bold mb30 spMb10 f18 spF16 center spLeft">該当する物件はございませんでした。</p>
				</div>

			</c:if>


			<div class="section01">
				<c:if test="${buildingList.size() != '0'}">
					<div class="specialTitle02">
						<p class="img"><img src="<c:out value="${featurePageInfo.pathName}"/>"></p>
						<div class="textBlock">
							<h1><c:out value="${featurePageInfo.featurePageName}"/></h1>
							<p class="lead"><c:out value="${featurePageInfo.featureComment}"/></p>
						</div>

					</div>
				</c:if>

				<div class="refineBlock">
					<c:if test="${buildingList.size() != '0'}">
						<div class="pagination clearfix">

							<c:import url="/WEB-INF/jsp/front/include/common/housingPagingjs.jsh?pageFlg=01" />
						</div>
					</c:if>
				</div>

				<c:if test="${buildingList.size() != '0'}">
					<c:set var="sortOrder" value="${pageContext.request.contextPath}/buy/special/${housingFeatureForm.getFeaturePageId()}/?selectedPage=1" />
					<div class="sortingArea">
						<ul>
							<li>並べ替え</li>
							<li>
							<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
								<c:if test="${housingFeatureForm.getSortUpdDateValue() == key}">
									<input type="hidden" id ="sortUpdDateValue"  name="sortUpdDateValue"  value="<c:out value="${key}"/>">
									<a id="sortOrder1" href="javascript:orderLinkToUrl('<c:out value="${sortOrder}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
								</c:if>
							</dm3lookup:lookupForEach>
							</li>

							<li>
							<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
								<c:if test="${housingFeatureForm.getSortPriceValue() == key}">
									<input type="hidden" id ="sortPriceValue"  name="sortPriceValue"  value="<c:out value="${key}"/>">
									<a id="sortOrder2" href="javascript:orderLinkToUrl('<c:out value="${sortOrder}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
								</c:if>
							</dm3lookup:lookupForEach>
							</li>

							<li>
							<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
								<c:if test="${housingFeatureForm.getSortBuildDateValue() == key}">
									<input type="hidden" id ="sortBuildDateValue"  name="sortBuildDateValue"  value="<c:out value="${key}"/>">
									<a id="sortOrder3" href="javascript:orderLinkToUrl('<c:out value="${sortOrder}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
								</c:if>
							</dm3lookup:lookupForEach>
							</li>

							<li>
							<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
								<c:if test="${housingFeatureForm.getSortWalkTimeValue() == key}">
									<input type="hidden" id ="sortWalkTimeValue"  name="sortWalkTimeValue"  value="<c:out value="${key}"/>">
									<a id="sortOrder4" href="javascript:orderLinkToUrl('<c:out value="${sortOrder}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
								</c:if>
							</dm3lookup:lookupForEach>
							</li>
						</ul>
						<p class="SPdisplayBlock"><span>並べ替え</span>
							<select name="selKeyOrderType" id="selKeyOrderType" onchange="javascript:orderSelect('<c:out value="${sortOrder}"/>');">
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '4'}">selected="selected"</c:if> value="4" label="物件登録日（新着順） ▼">物件登録日（新着順） ▼</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '3'}">selected="selected"</c:if> value="3" label="物件登録日（新着順） ▲">物件登録日（新着順） ▲</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '1'}">selected="selected"</c:if> value="1" label="物件価格 ▲">物件価格 ▲</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '2'}">selected="selected"</c:if> value="2" label="物件価格 ▼">物件価格 ▼</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '5'}">selected="selected"</c:if> value="5" label="築年数 ▲">築年数 ▲</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '6'}">selected="selected"</c:if> value="6" label="築年数 ▼">築年数 ▼</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '7'}">selected="selected"</c:if> value="7" label="駅からの距離 ▲">駅からの距離 ▲</option>
								<option <c:if test="${housingFeatureForm.getKeyOrderType() == '8'}">selected="selected"</c:if> value="8" label="駅からの距離 ▼">駅からの距離 ▼</option>
							</select>
						</p>

					</div>
				</c:if>
				<div class="resultArea">
<c:forEach  var="buildingResults" items="${buildingList}" varStatus="EditItem">


					<div class="searchResult">
						<div class="buildingName">
							<ul class="buildingType clearfix">
								<c:if test="${dateList.get(EditItem.index) == '1'}">
									<li class="icoNew01">新着</li>
								</c:if>
								<dm3lookup:lookupForEach lookupName="hosuing_kind">
									<c:if test="${buildingList.get(EditItem.index).housingKindCd == key}">
										<li class="icoMansion01"><c:out value="${value}"/></li>
									</c:if>
								</dm3lookup:lookupForEach>
							</ul>
							<div class="buildingFeatures clearfix">
								<p class="caption"><c:out value="${housingDtlList.get(EditItem.index).getDtlComment()}" escapeXml="false"/></p>
								<p class="number">物件番号:<c:out value="${housingList.get(EditItem.index).getHousingCd()}"/></p>
							</div>

							<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
								<c:set var="housingKindName" value="mansion" />
							</c:if>
							<c:if test="${buildingList.get(EditItem.index).housingKindCd == '02'}">
								<c:set var="housingKindName" value="house" />
							</c:if>
							<c:if test="${buildingList.get(EditItem.index).housingKindCd == '03'}">
								<c:set var="housingKindName" value="ground" />
							</c:if>

							<h2><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><c:out value="${housingList.get(EditItem.index).getDisplayHousingName()}"/></a></h2>
						</div>

						<div class="utilityIcon">
							<ul class="clearfix">

								<c:forEach varStatus="Status" items="${iconInfoList.get(EditItem.index)}">
									<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>modal/<dm3lookup:lookup lookupName="recommend_point_html" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>" data-fancybox-type="iframe" class="recicon"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/<dm3lookup:lookup lookupName="recommend_point_icon_img_SPdisplayNone" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>" alt="<dm3lookup:lookup lookupName="recommend_point_icon" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>"></a></li>
								</c:forEach>

							</ul>
						</div>

						<div class="buildingPhoto">
							<c:if test="${searchHousingImgList.get(EditItem.index).get(0) != null}">
								<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><img src="<c:out value="${searchHousingImgList.get(EditItem.index).get(0)}"/>" alt=""></a></p>
							</c:if>

							<c:if test="${searchHousingImgList.get(EditItem.index).get(0) == null}">
								<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>${commonParameters.getNoPhoto200()}" alt=""></a></p>
							</c:if>
						</div>

						<div class="buildingSummary">
							<table class="tableType1">
								<tbody>
									<tr>
										<th>物件価格</th>
										<td>
											<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
												<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
												<p class="buildingPrice"><fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円</p>
											</c:if>
										</td>
									</tr>
									<tr>
										<th>所在地</th>
										<td><c:out value="${prefNameList.get(EditItem.index)}"/>&nbsp;
										    <c:out value="${buildingList.get(EditItem.index).getAddressName()}"/>
										    &nbsp;<c:out value="${buildingList.get(EditItem.index).getAddressOther1()}"/>
										    &nbsp;<c:out value="${buildingList.get(EditItem.index).getAddressOther2()}"/></td>
									</tr>
									<tr>
										<th>アクセス</th>
										<td>
											<c:forEach  var="buildingStationInfo" items="${buildingStationInfoList.get(EditItem.index)}" varStatus="buildingStationInfoItem">

												<c:if test="${rrMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRrName() != null}">
													<c:out value="${rrMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRrName()}"/>
												</c:if>

												<c:if test="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName() == null}">
													<c:out value="${buildingStationInfo.getDefaultRouteName()}"/>
												</c:if>
												<c:if test="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName() != null}">
													<c:out value="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName()}"/>
												</c:if>


												<c:if test="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName() == null}">
													<c:out value="${buildingStationInfo.getStationName()}"/>駅
												</c:if>
												<c:if test="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName() != null}">
													<c:out value="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName()}"/>駅
												</c:if>

												<c:if test="${!empty buildingStationInfo.getBusCompany()}">
													<c:out value="${buildingStationInfo.getBusCompany()}"/>
												</c:if>

												<c:if test="${!empty buildingStationInfo.getTimeFromBusStop()}">
												 	徒歩<c:out value="${buildingStationInfo.getTimeFromBusStop()}"/>分
												 </c:if>
												 <br>
											</c:forEach>
										</td>
									</tr>
									<tr>
												<c:if
													test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
													<th><span>間取り</span><span class="SPdisplayNone">
															&frasl; </span>専有面積</th>
													<td><c:if
															test="${!empty housingList.get(EditItem.index).getLayoutCd() || !empty housingList.get(EditItem.index).getPersonalArea() || !empty housingList.get(EditItem.index).getPersonalAreaMemo()}">
															<c:if
																test="${!empty housingList.get(EditItem.index).getLayoutCd()}">
																<span> <dm3lookup:lookupForEach
																		lookupName="layoutCd">
																		<c:if
																			test="${housingList.get(EditItem.index).getLayoutCd() == key}">
																			<c:out value="${value}" />
																		</c:if>
																	</dm3lookup:lookupForEach>
																</span>
															</c:if>
															<c:if
																test="${empty housingList.get(EditItem.index).getLayoutCd()}">
																<span>
																	&nbsp;
																</span>
															</c:if>
															<span class="SPdisplayNone"> &frasl; </span>
															<c:if
																test="${!empty housingList.get(EditItem.index).getPersonalArea()}">
																<c:out
																	value="${housingList.get(EditItem.index).getPersonalArea()}" />m&sup2;（約<c:out
																	value="${personalTubosuuList.get(EditItem.index)}" />坪）</c:if>
															<c:if
																test="${empty housingList.get(EditItem.index).getPersonalArea()}">
																&nbsp;</c:if>
															<c:out
																value="${housingList.get(EditItem.index).getPersonalAreaMemo()}" />
														</c:if></td>
												</c:if>
												<c:if
													test="${buildingList.get(EditItem.index).housingKindCd != '01'}">
													<th><span>間取り</span><span class="SPdisplayNone">
															&frasl; </span>建物面積<span class="SPdisplayNone"> &frasl; </span>土地面積</th>
													<td><c:if
															test="${!empty housingList.get(EditItem.index).getLayoutCd() || !empty buildingDtlList.get(EditItem.index).buildingArea || !empty housingList.get(EditItem.index).landArea || !empty buildingDtlList.get(EditItem.index).getBuildingAreaMemo() || !empty housingList.get(EditItem.index).getLandAreaMemo()}">
															<c:if
																test="${!empty housingList.get(EditItem.index).getLayoutCd()}">
																<span> <dm3lookup:lookupForEach
																		lookupName="layoutCd">
																		<c:if
																			test="${housingList.get(EditItem.index).getLayoutCd() == key}">
																			<c:out value="${value}" />
																		</c:if>
																	</dm3lookup:lookupForEach>
																</span>
															</c:if>
															<c:if
																test="${empty housingList.get(EditItem.index).getLayoutCd()}">
																<span> &nbsp;
																</span>
															</c:if>
															<span class="SPdisplayNone"> &frasl; </span>
															<c:if
																test="${empty buildingDtlList.get(EditItem.index).buildingArea}">
																&nbsp;
															</c:if>
															<c:out
																value="${buildingDtlList.get(EditItem.index).getBuildingAreaMemo()}" />

															<span class="SPdisplayNone"> &frasl; </span>
															<c:if
																test="${!empty housingList.get(EditItem.index).landArea}">
																<c:out
																	value="${housingList.get(EditItem.index).landArea}" />m&sup2;（約<c:out
																	value="${landAreaList.get(EditItem.index)}" />坪）
															</c:if>
															<c:if
																test="${empty housingList.get(EditItem.index).landArea}">
																&nbsp;
															</c:if>
															<c:out
																value="${housingList.get(EditItem.index).getLandAreaMemo()}" />

														</c:if></td>
												</c:if>
											</tr>
									<tr>
										<th>築年月</th>
										<td>
											<c:if test="${!empty compDateList.get(EditItem.index)}">
												<c:out value="${compDateList.get(EditItem.index).substring(0, 4)}"/>年<c:out value="${compDateList.get(EditItem.index).substring(4, 6)}"/>月築
											</c:if>
										</td>
									</tr>

									<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
										<tr>
											<th><span>階建</span><span class="SPdisplayNone"> &frasl; </span>所在階</th>
											<td>
												<c:if test="${!empty buildingList.get(EditItem.index).getTotalFloors() || !empty housingList.get(EditItem.index).getFloorNo()}">
													<c:if test="${!empty buildingList.get(EditItem.index).getTotalFloors()}">
														<span><c:out value="${buildingList.get(EditItem.index).getTotalFloors()}" />階建</span>
													</c:if>
													<c:if test="${empty buildingList.get(EditItem.index).getTotalFloors()}">
														<span>&nbsp;</span>
													</c:if>
													<span class="SPdisplayNone"> &frasl; </span>
													<c:if test="${!empty housingList.get(EditItem.index).getFloorNo()}">
														<c:out value="${housingList.get(EditItem.index).getFloorNo()}" />階
													</c:if>
													<c:if test="${empty housingList.get(EditItem.index).getFloorNo()}">
														&nbsp;
													</c:if>
												</c:if>
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<c:if test="${!empty housingList.get(EditItem.index).getBasicComment()}">
						<p class="comment">＜担当者からのおすすめ＞<c:out value="${housingList.get(EditItem.index).getBasicComment()}" escapeXml="false"/></p>
						</c:if>
						<div class="contactBlock">


							<c:if test="${buildingList.get(EditItem.index).getHousingKindCd() != '03'}">
								<c:if test="${loginFlg != '0'}">
									<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この物件に関するお問い合わせ</a></p>
								</c:if>
								<c:if test="${loginFlg == '0'}">
									<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この物件に関するお問い合わせ</a></p>
								</c:if>
							</c:if>

							<c:if test="${buildingList.get(EditItem.index).getHousingKindCd() == '03'}">
								<c:if test="${loginFlg != '0'}">
									<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この土地に関するお問い合わせ</a></p>
								</c:if>
								<c:if test="${loginFlg == '0'}">
									<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この土地に関するお問い合わせ</a></p>
								</c:if>
							</c:if>
							<c:if test="${loginFlg == '0'}">
								<p class="btnOrange02"><a href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd">お気に入り登録</a></p>
							</c:if>

						</div>
						<c:if test="${housingKindCd != '03'}">
							<div class="reformPlan">

								<c:if test="${reformPlanList.get(EditItem.index) == null}">
									<c:if test="${!empty housingList.get(EditItem.index).getReformComment()}">
										<h3 class="titleReform">この物件のリフォームプラン例</h3>
										<p class="note"><c:out value="${housingList.get(EditItem.index).getReformComment()}"  escapeXml="false"/></p>
									</c:if>
								</c:if>
								<c:if test="${reformPlanList.get(EditItem.index) != null}">
									<h3 class="titleReform">この物件のリフォームプラン例</h3>
									<table class="tableType2 SPdisplayNone">
										<thead>
											<tr>
												<th>リフォームプラン</th>
												<th>総額<span>※下段は個別価格</span></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach  var="reformPlanList" items="${reformPlanList.get(EditItem.index)}" varStatus="reformPlanListItem">
												<tr>

													<th>
													<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/<c:out value="${reformPlanList.getSysReformCd()}"/>/#anc01"><c:out value="${reformPlanList.getPlanName()}"/></a></th>
													<td>

														<c:if test="${!empty housingList.get(EditItem.index).getPrice() || !empty reformPlanList.getPlanPrice()}">
															<span class="buildingPrice">
																<c:set var="totalPrice" value="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice()}"/>
																<c:set var="price" value="${totalPrice / 10000}"/>
																約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
															</span><br>
														</c:if>


														<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
														物件価格：
															<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
															<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
														</c:if>

														<c:if test="${!empty housingList.get(EditItem.index).getPrice() && !empty reformPlanList.getPlanPrice()}">
														＋
														</c:if>
														<c:if test="${!empty reformPlanList.getPlanPrice()}">
														リフォーム価格：
															<c:set var="price" value="${reformPlanList.getPlanPrice() / 10000}"/>
															約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
														</c:if>

													</td>
												</tr>

											</c:forEach>

										</tbody>
									</table>

						<ul class="SPdisplayBlock">
							<c:forEach  var="reformPlanList" items="${reformPlanList.get(EditItem.index)}" varStatus="reformPlanListItem">
								<li><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/<c:out value="${reformPlanList.getSysReformCd()}"/>/#anc01"><span><c:out value="${reformPlanList.getPlanName()}"/><br>
								<c:set var="totalPrice" value="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice()}"/>
								<c:set var="price" value="${totalPrice / 10000}"/>
								総額：約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円</span><br>
								<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
								物件：
									<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
									<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
								</c:if>

								<c:if test="${!empty housingList.get(EditItem.index).getPrice() && !empty reformPlanList.getPlanPrice()}">
								＋
								</c:if>
								<c:if test="${!empty reformPlanList.getPlanPrice()}">
								リフォーム価格：
									<c:set var="price" value="${reformPlanList.getPlanPrice() / 10000}"/>
									約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
								</c:if>
								</a></li>
							</c:forEach>
						</ul>

									<p class="comment">※リフォームプランは一例です。その他にも多彩なプランをご提案いたします。<br>
														※リフォーム価格は概算です。</p>
								</c:if>
							</div>
						</c:if>
					</div>

</c:forEach>
</div>
				<c:if test="${buildingList.size() != '0'}">
					<c:if test="${pagingForm.maxPages > 1}">
						<div class="pagination">
							<c:import url="/WEB-INF/jsp/front/include/common/housingPagingjs.jsh" />
						</div>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
</form>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>