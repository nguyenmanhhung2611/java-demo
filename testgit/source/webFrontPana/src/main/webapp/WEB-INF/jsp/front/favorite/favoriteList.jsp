<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>お気に入り物件一覧｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/jquery.fancybox.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>buy/css/building.css" rel="stylesheet">


<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.ah-placeholder.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	//SPのみ対応
	//--------------------------------
	if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {
		$(".favdelete").fancybox({
			fitToView	: false,
			width		: '250px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
    }
	else {
		$(".favdelete").fancybox({
			fitToView	: false,
			width		: '400px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
	}


	$("#sortOrder1").click(function(){
		if($("#sortOrder1").html() == "お気に入り登録日 ▲"){
			$("#keyOrderType").val("2")
			$("#sortOrder1Label").val("お気に入り登録日 ▼")
		} else {
			$("#keyOrderType").val("1")
			$("#sortOrder1Label").val("お気に入り登録日 ▲")
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


function orderLinkToUrl(url) {
	document.inputForm.action = url;
	document.inputForm.target = "_self";
	document.inputForm.submit();
}

function linkToUrl(url, sysHousingCd) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.sysHousingCd.value = sysHousingCd;
		document.inputForm.submit();
	}

function KeyForm(page) {
	document.inputForm.action = '';
	document.inputForm.target = "_self";
	document.inputForm.selectedPage.value = page;
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
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />
<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="<c:out value="${commonParameters.resourceRootUrl}"/>">トップ</a></li>
					<li><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/">マイページ</a></li>
					<li class="current">お気に入り物件一覧</li>
				</ul>
			</div>
		</div>
		<c:if test="${publicFavoriteHousingListForm.visibleRows.size() > 0}">
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">お気に入り物件一覧</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="listPage">
				<div class="refineBlock">
					<div class="pagination type02 clearfix">
						<c:set var="strBefore" value="javascript:KeyForm('" scope="request" />
						<c:set var="strAfter" value="')" scope="request" />
						<c:set var="pagingForm" value="${publicFavoriteHousingListForm}" scope="request" />
						<c:import url="/WEB-INF/jsp/front/include/common/pagingjs.jsh?pageFlg=02" />
					</div>
				</div>
				<div class="sortingArea">
					<ul>
						<li>並べ替え</li>
						<li><a id="sortOrder1" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder1Label}"/></a></li>
						<li><a id="sortOrder2" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder2Label}"/></a></li>
						<li><a id="sortOrder3" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder3Label}"/></a></li>
						<li><a id="sortOrder4" href="javascript:orderLinkToUrl('');"><c:out value="${sortOrder4Label}"/></a></li>
					</ul>
					<form name="inputForm" method="post">
						<p class="SPdisplayBlock"><span>並べ替え</span>
							<select id="spSort">
								<option value="1" label="お気に入り登録日 ▲" <c:if test="${keyOrderType == '1'}">selected="selected"</c:if>>お気に入り登録日 ▲</option>
								<option value="2" label="お気に入り登録日 ▼" <c:if test="${empty keyOrderType or keyOrderType == '2'}">selected="selected"</c:if>>お気に入り登録日 ▼</option>
								<option value="3" label="物件価格 ▲" <c:if test="${keyOrderType == '3'}">selected="selected"</c:if>>物件価格 ▲</option>
								<option value="4" label="物件価格 ▼" <c:if test="${keyOrderType == '4'}">selected="selected"</c:if>>物件価格 ▼</option>
								<option value="5" label="築年数 ▲" <c:if test="${keyOrderType == '5'}">selected="selected"</c:if>>築年数 ▲</option>
								<option value="6" label="築年数 ▼" <c:if test="${keyOrderType == '6'}">selected="selected"</c:if>>築年数 ▼</option>
								<option value="7" label="駅からの距離 ▲" <c:if test="${keyOrderType == '7'}">selected="selected"</c:if>>駅からの距離 ▲</option>
								<option value="8" label="駅からの距離 ▼" <c:if test="${keyOrderType == '8'}">selected="selected"</c:if>>駅からの距離 ▼</option>
							</select>
						</p>
						<input type="hidden" id="keyOrderType" name="keyOrderType" value="<c:out value="${keyOrderType}"/>">
						<input type="hidden" id="sortOrder1Label" name="sortOrder1Label" value="<c:out value="${sortOrder1Label}"/>">
						<input type="hidden" id="sortOrder2Label" name="sortOrder2Label" value="<c:out value="${sortOrder2Label}"/>">
						<input type="hidden" id="sortOrder3Label" name="sortOrder3Label" value="<c:out value="${sortOrder3Label}"/>">
						<input type="hidden" id="sortOrder4Label" name="sortOrder4Label" value="<c:out value="${sortOrder4Label}"/>">
						<input type="hidden" name="sysHousingCd" value="">
						<input type="hidden" name="selectedPage" value="<c:out value="${publicFavoriteHousingListForm.selectedPage}"/>">
						<input type="hidden" name="error" value="">
					</form>
					<p class="right spLeft mt15">※募集が終了した物件は、一覧から削除されます。予めご了承ください。</p>
				</div>
			</div>
			<c:forEach items="${publicFavoriteHousingListForm.visibleRows}" var="favorite" varStatus="status">
			<c:set var="favoriteHousing" value="${favorite.items['favoriteInfo']}"/>
			<div class="estateBlockA01 clearfix" data-number="<c:out value="${housingInfoList.get(status.index).housingCd}"/>">
				<div class="buildingName">
					<ul class="buildingType clearfix">
						<c:if test="${dateList.get(status.index) == '1' }">
						<li class="icoNew01">新着</li>
						</c:if>
						<li class="icoMansion01"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfoList.get(status.index).housingKindCd }" /></li>
						<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd_En">
							<c:if test="${buildingInfoList.get(status.index).housingKindCd == key}">
								<c:set var="housingKindName" value="${value}" />
							</c:if>
						</dm3lookup:lookupForEach>
					</ul>
					<div class="SPdisplayNone">
					<h2>
						<p class="p1" style="width: 500px;" title="<c:out value="${housingInfoList.get(status.index).displayHousingName}"/>">
							<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingInfoList.get(status.index).prefCd}"/>/detail/<c:out value="${favoriteHousing.sysHousingCd}"/>/">
								<c:out value="${housingInfoList.get(status.index).displayHousingName}"/>
							</a>
						</p>
					</h2>
					</div>
					<div class="SPdisplayBlock">
					<h2>
						<a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingInfoList.get(status.index).prefCd}"/>/detail/<c:out value="${favoriteHousing.sysHousingCd}"/>/">
							<c:out value="${housingInfoList.get(status.index).displayHousingName}"/>
						</a>
					</h2>
					</div>
				</div>
				<div class="buildingPhoto">
					<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingInfoList.get(status.index).prefCd}"/>/detail/<c:out value="${favoriteHousing.sysHousingCd}"/>/">
						<c:if test="${!empty housingImgList.get(status.index)}">
							<img src="<c:out value="${housingImgList.get(status.index)}"/>" alt="">
						</c:if>
						<c:if test="${empty housingImgList.get(status.index)}">
							<img src="<c:out value='${commonParameters.resourceRootUrl}${commonParameters.noPhoto200}'/>" alt="">
						</c:if>
					</a></p>
				</div>
				<div class="contactBlock">
					<ul>
						<li><p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${favoriteHousing.sysHousingCd}"/>');">お問い合わせ</a></p></li>
						<li><p class="btnGray01"><a href="../favorite/delete/?sysHousingCd=<c:out value="${favoriteHousing.sysHousingCd}"/>&displayHousingName=<c:out value="${encodeHousingNameList.get(status.index)}"/>" data-fancybox-type="iframe" class="favdelete">お気に入り削除</a></p></li>
					</ul>
				</div>
				<div class="buildingSummary">
					<table class="buildingTableType1" style="width:100%">
						<tbody>
							<tr>
								<th style="width:30%">物件価格</th>
								<c:set var="price" value="${housingInfoList.get(status.index).price / 10000}"/>
								<td><p class="buildingPrice">
								<c:if test="${housingInfoList.get(status.index).price > 0 }">
									<fmt:formatNumber value="${price + (1 - (price % 1)) % 1}" pattern="###,###" />万円
								</c:if>&nbsp;
								</p></td>
							</tr>
							<tr>
								<th>所在地</th>
								<td><c:out value="${prefAddressList.get(status.index)}"/>&nbsp;</td>
							</tr>
							<tr>
								<th>アクセス</th>
								<td>
									<c:forEach items="${buildingStationList.get(status.index)}" var="buildingStation">
										<c:out value="${buildingStation}"/><br>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<c:if test="${buildingInfoList.get(status.index).housingKindCd == '01' }">
								<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span>専有面積</th>
								<td>
									<dm3lookup:lookupForEach lookupName="layoutCd">
										<c:if test="${housingInfoList.get(status.index).layoutCd == key}">
											<c:out value="${value}"/>
										</c:if>
									</dm3lookup:lookupForEach>
									<c:if test="${!empty housingInfoList.get(status.index).layoutCd or !empty housingInfoList.get(status.index).personalArea }">
										<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
									</c:if>
									<c:if test="${!empty housingInfoList.get(status.index).personalArea }">
										<c:out value="${housingInfoList.get(status.index).personalArea}"/>m&sup2;<c:out value="${personalAreaSquareList.get(status.index)}"/>
									</c:if>
								</td>
								</c:if>
								<c:if test="${buildingInfoList.get(status.index).housingKindCd == '02' or buildingInfoList.get(status.index).housingKindCd == '03' }">
								<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span><span>建物面積</span><span class="SPdisplayNone"> &frasl; </span>土地面積</th>
								<td>
									<dm3lookup:lookupForEach lookupName="layoutCd">
										<c:if test="${housingInfoList.get(status.index).layoutCd == key}">
											<c:out value="${value}"/>
										</c:if>
									</dm3lookup:lookupForEach>
									<c:if test="${!empty housingInfoList.get(status.index).layoutCd or !empty buildingDtlInfoList.get(status.index).buildingArea or !empty housingInfoList.get(status.index).landArea}">
										<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
									</c:if>
									<c:if test="${!empty buildingDtlInfoList.get(status.index).buildingArea}">
										<c:out value="${buildingDtlInfoList.get(status.index).buildingArea}"/>m&sup2;<c:out value="${buildingAreaSquareList.get(status.index)}"/>
									</c:if>
									<c:if test="${!empty housingInfoList.get(status.index).layoutCd or !empty buildingDtlInfoList.get(status.index).buildingArea or !empty housingInfoList.get(status.index).landArea}">
										<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
									</c:if>
									<c:if test="${!empty housingInfoList.get(status.index).landArea}">
										<c:out value="${housingInfoList.get(status.index).landArea}"/>m&sup2;<c:out value="${landAreaSquareList.get(status.index)}"/>
									</c:if>
								</td>
								</c:if>
							</tr>
							<tr>
								<th>築年月</th>
								<td><c:out value="${compDateList.get(status.index)}"/></td>
							</tr>
							<c:if test="${buildingInfoList.get(status.index).housingKindCd == '01' }">
							<tr>
								<th>階建<span class="SPdisplayNone"> &frasl; </span><br class="SPdisplayBlock">所在階</th>
								<td>
									<c:out value="${totalFloorList.get(status.index)}"/>
									<c:if test="${!empty totalFloorList.get(status.index) or !empty floorNoList.get(status.index)}">
										<span class="SPdisplayNone">　&frasl;　</span><br class="SPdisplayBlock">
										<c:out value="${floorNoList.get(status.index)}"/>
									</c:if>
								</td>
							</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</div><!-- /.estateBlockA01 -->
			</c:forEach>
		</div><!-- /.section01 -->
		<div class="listPage">
			<c:set var="strBefore1" value="javascript:KeyForm('" scope="request" />
			<c:set var="strAfter1" value="')" scope="request" />
			<c:set var="pagingForm1" value="${publicFavoriteHousingListForm}" scope="request" />

			<c:if test="${pagingForm1.maxPages > 1}">
				<div class="pagination mb30 spMb15">
					<ul>
						<c:if test="${pagingForm1 != null && pagingForm1.rows != null}">
							<c:if test="${pagingForm1.leftNavigationPageNo != pagingForm1.rightNavigationPageNo}">
								<span class="navPaging">
							  	<c:if test="${pagingForm1.maxRows != 0}">
									<c:if test="${pagingForm1.selectedPage != 1}">
										<li class="prev"><a href="<c:out value="${strBefore1}"/><c:out value="${pagingForm1.selectedPage - 1}"/><c:out value="${strAfter1}"/>">Prev&nbsp;</a></li>
									</c:if>
									<c:forEach begin="${pagingForm1.leftNavigationPageNo}"
											end="${pagingForm1.rightNavigationPageNo}"
											var="thisPageNo">
										<c:choose>
											<c:when test="${thisPageNo == pagingForm1.selectedPage}">
												<li class="current"><a><c:out value="${thisPageNo}"/></a></li>
											</c:when>
											<c:otherwise>
												<li><a href="<c:out value="${strBefore1}"/><c:out value="${thisPageNo}"/><c:out value="${strAfter1}"/>"><c:out value="${thisPageNo}"/>&nbsp;</a></li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
									<c:if test="${pagingForm1.selectedPage != pagingForm1.maxPages}">
										<li class="next"><a href="<c:out value="${strBefore1}"/><c:out value="${pagingForm1.selectedPage + 1}"/><c:out value="${strAfter1}"/>">次へ</a></li>
									</c:if>
								</c:if>
								</span>
							</c:if>
						</c:if>
					</ul>
				</div>
			</c:if>
			<c:if test="${pagingForm1.maxPages <= 1}">
				<div class="mb30 spMb15"></div>
			</c:if>
		</div>
		<c:if test="${privateFavoriteHousingList.size() > 0}">
		<div class="generalBlockA02 mb30 spMb15">
			<p>下記の物件は募集終了により、一覧から削除されました。</p>
			<ul>
				<c:forEach items="${privateFavoriteHousingList}" var="privateFavoriteHousing">
				<li>・<c:out value="${privateFavoriteHousing.displayHousingName}"/></li>
				</c:forEach>
			</ul>
		</div><!-- /.generalBlockA01 -->
		</c:if>
		<div class="contentsInner01 pb30 spPb10">
			<p class="center"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/" class="secondaryBtn">マイページトップへ戻る</a></p>
		</div>
		</c:if>

		<c:if test="${publicFavoriteHousingListForm.visibleRows.size() == 0}">
		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">お気に入り物件一覧</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
				<p class="bold mb30 spMb10 f18 spF16 center spLeft">現在、お気に入り登録されている物件はありません。</p>
			</div>
			<div class="contentsInner01">
				<p class="center spPb10"><a href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/#search" class="secondaryBtn">買いたいTOPへ</a></p>
			</div>
		<!-- / .section01 --></div>
		</c:if>


	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>
