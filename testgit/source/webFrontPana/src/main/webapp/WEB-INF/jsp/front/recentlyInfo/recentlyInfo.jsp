<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value='${commonParameters.defaultKeyword}'/>">
<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
最近見た物件一覧｜<c:out value='${commonParameters.panaReSmail}'/>
</title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>buy/css/building.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type="text/javascript">
$(document).ready(function() {
	//SPのみ対応
	//--------------------------------
	if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {
		$(".favadd").fancybox({
			fitToView	: false,
			width		: '250px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
    }
	else {
		$(".favadd").fancybox({
			fitToView	: false,
			width		: '450px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
	}

	$(".favadd").on('click',function(){
		setTimeout(function(){
		$.fancybox.close();
		$("#personalInfo .favorite").text(getFavoriteCount());
		}, 1500)
	});

	$("#sortOrder1").click(function(){
		if($("#sortOrder1").html() == "最近見た日付 ▲"){
			$("#keyOrderType").val("2")
			$("#sortOrder1Label").val("最近見た日付 ▼")
		} else {
			$("#keyOrderType").val("1")
			$("#sortOrder1Label").val("最近見た日付 ▲")
		}

	});

	$("#sortOrder2").click(function(){
		if($("#sortOrder2").html() == "物件価格 ▲"){
			$("#keyOrderType").val("4")
			$("#sortOrder2Label").val("物件価格 ▼")
		} else {
			$("#keyOrderType").val("3")
			$("#sortOrder2Label").val("物件価格 ▲")
		}

	});

	$("#sortOrder3").click(function(){
		if($("#sortOrder3").html() == "築年数 ▲"){
			$("#keyOrderType").val("6")
			$("#sortOrder3Label").val("築年数 ▼")
		} else {
			$("#keyOrderType").val("5")
			$("#sortOrder3Label").val("築年数 ▲")
		}

	});

	$("#sortOrder4").click(function(){
		if($("#sortOrder4").html() == "駅からの距離 ▲"){
			$("#keyOrderType").val("8")
			$("#sortOrder4Label").val("駅からの距離 ▼")
		} else {
			$("#keyOrderType").val("7")
			$("#sortOrder4Label").val("駅からの距離 ▲")
		}

	});


	$("#spSort").change(function(){
		$("#keyOrderType").val($(this).val());
		orderLinkToUrl('');
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

function orderLinkToUrl(url) {
	document.inputForm.action = url;
	document.inputForm.submit();
}

function linkToUrl(url, sysHousingCd) {
	document.inputForm.action = url;
	document.inputForm.sysHousingCd.value = sysHousingCd;
	document.inputForm.submit();
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
<div id="ptop"></div>
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />

<div id="contents">
	<div id="contentsInner">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="<c:out value="${commonParameters.resourceRootUrl}"/>">トップ</a></li>
					<c:if test="${loginFlg == '0'}">
					<li><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/">マイページ</a></li>
					</c:if>
					<c:if test="${loginFlg != '0'}">
					<li><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage_service/">マイページ</a></li>
					</c:if>
					<li class="current">最近見た物件一覧</li>
				</ul>
			</div>
		</div>
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">最近見た物件一覧</h1>
			</div><!-- /.headingBlockA01 -->

			<c:if test="${recentlyInfoListSize == '0'}">
				<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
					<p class="bold mb30 spMb10 f18 spF16 center spLeft">最近見た物件はありません。</p>
				</div>
				<div class="contentsInner01">
					<p class="center spPb10"><a href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/#search" class="secondaryBtn">買いたいTOPへ</a></p>
				</div>
			</c:if>

			<c:if test="${recentlyInfoListSize != '0'}">
				<div class="listPage">
					<div class="sortingArea">
						<ul>
							<li>並べ替え</li>
							<li><a id="sortOrder1" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder1Label}"/></a></li>
							<li><a id="sortOrder2" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder2Label}"/></a></li>
							<li><a id="sortOrder3" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder3Label}"/></a></li>
							<li><a id="sortOrder4" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder4Label}"/></a></li>
						</ul>
						<form action="" method="post" name="inputForm">
							<p class="SPdisplayBlock"><span>並べ替え</span>
								<select id="spSort">
									<option value="1" label="最近見た日付 ▲" <c:if test="${keyOrderType == '1'}">selected="selected"</c:if>>最近見た日付 ▲</option>
									<option value="2" label="最近見た日付 ▼" <c:if test="${empty keyOrderType or keyOrderType == '2'}">selected="selected"</c:if>>最近見た日付 ▼</option>
									<option value="3" label="物件価格 ▲" <c:if test="${keyOrderType == '3'}">selected="selected"</c:if>>物件価格 ▲</option>
									<option value="4" label="物件価格 ▼" <c:if test="${keyOrderType == '4'}">selected="selected"</c:if>>物件価格 ▼</option>
									<option value="5" label="築年数 ▲" <c:if test="${keyOrderType == '5'}">selected="selected"</c:if>>築年数 ▲</option>
									<option value="6" label="築年数 ▼" <c:if test="${keyOrderType == '6'}">selected="selected"</c:if>>築年数 ▼</option>
									<option value="7" label="駅からの距離 ▲" <c:if test="${keyOrderType == '7'}">selected="selected"</c:if>>駅からの距離 ▲</option>
									<option value="8" label="駅からの距離 ▼" <c:if test="${keyOrderType == '8'}">selected="selected"</c:if>>駅からの距離 ▼</option>
								</select>
							</p>
							<input type="hidden" id="sysHousingCd" name="sysHousingCd" value="">
							<input type="hidden" id="keyOrderType" name="keyOrderType" value="">
							<input type="hidden" id="sortOrder1Label" name="sortOrder1Label" value="<c:out value="${sortOrder1Label}"/>">
							<input type="hidden" id="sortOrder2Label" name="sortOrder2Label" value="<c:out value="${sortOrder2Label}"/>">
							<input type="hidden" id="sortOrder3Label" name="sortOrder3Label" value="<c:out value="${sortOrder3Label}"/>">
							<input type="hidden" id="sortOrder4Label" name="sortOrder4Label" value="<c:out value="${sortOrder4Label}"/>">
						</form>
						<p class="right spLeft mt15">※募集が終了した物件は、一覧から削除されます。予めご了承ください。</p>
					</div>
				</div>

				<c:forEach  items="${housingList}" varStatus="EditItem">
				<div class="estateBlockA01 clearfix" data-number="<c:out value="${housingList.get(EditItem.index).housingCd}"/>">
					<div class="buildingName">
						<ul class="buildingType clearfix">
							<c:if test="${dateList.get(EditItem.index) == '1'}">
								<li class="icoNew01">新着</li>
							</c:if>
							<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_front_icon">
								<c:if test="${buildingList.get(EditItem.index).housingKindCd == key}"><li class="icoMansion01"><c:out value="${value}"/></li></c:if>
							</dm3lookup:lookupForEach>
						</ul>
						<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_En">
							<c:if test="${buildingList.get(EditItem.index).housingKindCd == key}">
								<c:set var="housingKindName" value="${value}" />
							</c:if>
						</dm3lookup:lookupForEach>
						<div class="SPdisplayNone">
						<h2>
							<p class="p1" style="width: 500px;" title="<c:out value="${housingList.get(EditItem.index).getDisplayHousingName()}"/>">
								<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/">
									<c:out value="${housingList.get(EditItem.index).getDisplayHousingName()}"/>
								</a>
							</p>
						</h2>
						</div>
						<div class="SPdisplayBlock">
						<h2>
							<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/">
								<c:out value="${housingList.get(EditItem.index).getDisplayHousingName()}"/>
							</a>
						</h2>
						</div>
					</div>
					<div class="buildingPhoto">
						<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/">
						<img src="<c:if test="${!empty searchHousingImgList.get(EditItem.index)}"><c:out value="${searchHousingImgList.get(EditItem.index)}"/></c:if><c:if test="${empty searchHousingImgList.get(EditItem.index)}"><c:out value="${commonParameters.resourceRootUrl}${commonParameters.noPhoto200}"/></c:if>" alt=""></a></p>
					</div>
					<div class="contactBlock">
						<ul>
							<c:if test="${loginFlg != '0'}">
							<li><p class="btnOrange01"><a href="javascript:linkToUrl('../buy/inquiry/division/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">お問い合わせ</a></p></li>
							</c:if>
							<c:if test="${loginFlg == '0'}">
							<li><p class="btnOrange01"><a href="javascript:linkToUrl('../buy/inquiry/input/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">お問い合わせ</a></p></li>
							<li><p class="btnOrange01"><a href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd">お気に入り登録</a></p></li>
							</c:if>
						</ul>
					</div>
					<div class="buildingSummary">
						<table class="buildingTableType1" style="width:100%">
							<tbody>
								<tr>
									<th style="width:30%">物件価格</th>
									<td>
										<p class="buildingPrice">
											<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
												<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
												<fmt:formatNumber value="${price + (1 - (price % 1)) % 1}" pattern="###,###" />万円
											</c:if>&nbsp;</p>
									</td>
								</tr>
								<tr>
									<th>所在地</th>
									<td><c:out value="${addressList.get(EditItem.index)}"/></td>
								</tr>
								<tr>
									<th>アクセス</th>
									<td>
										<c:forEach  items="${nearStationLists.get(EditItem.index)}" var="nearStation">
											<c:out value="${nearStation}"/><br>
										</c:forEach>
									</td>
								</tr>
								<tr>
									<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
										<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span>専有面積</th>
										<td>
											<dm3lookup:lookupForEach lookupName="layoutCd">
												<c:if test="${housingList.get(EditItem.index).getLayoutCd() == key}"><c:out value="${value}"/></c:if>
											</dm3lookup:lookupForEach>
											<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd() or !empty personalAreaList.get(EditItem.index)}">
												<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
											</c:if>
											<c:if test="${!empty personalAreaList.get(EditItem.index)}">
												<c:out value="${personalAreaList.get(EditItem.index)}"/>m&sup2;<c:out value="${personalAreaSquareList.get(EditItem.index)}"/>
											</c:if>
										</td>
									</c:if>
									<c:if test="${buildingList.get(EditItem.index).housingKindCd != '01'}">
										<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span><span>建物面積</span><span class="SPdisplayNone"> &frasl; </span>土地面積</th>
										<td>
											<dm3lookup:lookupForEach lookupName="layoutCd">
												<c:if test="${housingList.get(EditItem.index).getLayoutCd() == key}"><c:out value="${value}"/></c:if>
											</dm3lookup:lookupForEach>
											<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd() or !empty buildingAreaList.get(EditItem.index) or !empty landAreaList.get(EditItem.index)}">
												<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
											</c:if>
											<c:if test="${!empty buildingAreaList.get(EditItem.index)}">
												<c:out value="${buildingAreaList.get(EditItem.index)}"/>m&sup2;<c:out value="${buildingAreaSquareList.get(EditItem.index)}"/>
											</c:if>
											<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd() or !empty buildingAreaList.get(EditItem.index) or !empty landAreaList.get(EditItem.index)}">
												<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
											</c:if>
											<c:if test="${!empty landAreaList.get(EditItem.index)}">
												<c:out value="${landAreaList.get(EditItem.index)}"/>m&sup2;<c:out value="${landAreaSquareList.get(EditItem.index)}"/>
											</c:if>
										</td>
									</c:if>
								</tr>
								<tr>
									<th>築年月</th>
									<td><c:out value="${compDateList.get(EditItem.index)}"/></td>
								</tr>
								<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
								<tr>
									<th>階建<span class="SPdisplayNone"> &frasl; </span><br class="SPdisplayBlock">所在階</th>
									<td>
										<c:out value="${totalFloorList.get(EditItem.index)}"/>
										<c:if test="${!empty totalFloorList.get(EditItem.index) or !empty floorNoList.get(EditItem.index)}">
											<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
											<c:out value="${floorNoList.get(EditItem.index)}"/>
										</c:if>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div><!-- /.estateBlockA01 -->
				</c:forEach>
				<c:if test="${loginFlg == '0'}">
				<p class="center mt30"><a href="javascript:linkToUrl('../mypage/', '');" class="secondaryBtn">マイページトップへ戻る</a></p>
				</c:if>
				<c:if test="${loginFlg != '0'}">
				<p class="center mt30"><a href="javascript:linkToUrl('../mypage_service/', '');" class="secondaryBtn">マイページトップへ戻る</a></p>
				</c:if>
			</c:if>
		</div><!-- /.section01 -->
	</div><!-- /.contentsInner -->
</div><!-- /.contents -->
<!--#include virtual="/common/ssi/footer-S.html"-->

</body>
