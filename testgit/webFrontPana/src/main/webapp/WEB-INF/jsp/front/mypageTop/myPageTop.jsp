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

<title>マイページ｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>mypage/css/mypage.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript">
<!--
function linkToSubmit(informationNo) {

	var url = "<c:out value='${pageContext.request.contextPath}'/>/mypage/information/" + informationNo + "/";
	document.inputForm.target = "";
	document.inputForm.action = url;
	document.inputForm.submit();
}

function housingDetailToSubmit(prefCd, sysHousingCd, housingKindCd) {
	var url = "";
	if (prefCd == "" || sysHousingCd == "" || housingKindCd == "") {
		return "#";
	} else {

		url = "<c:out value='${pageContext.request.contextPath}'/>/buy/";

		if ("01" == housingKindCd) {
			url = url +"mansion/";

		} else if ("02" == housingKindCd) {
			url = url + "house/";

		} else if ("03" == housingKindCd) {
			url = url + "ground/";

		}
		url = url + prefCd + "/detail/" + sysHousingCd + "/";

		window.location.href = url;
	}
}

function linkToHousingInquory(sysHousingCd) {
	var url = "";

	if (sysHousingCd == "") {
		return "#";
	} else {
		url = "<c:out value='${pageContext.request.contextPath}'/>/buy/inquiry/input/";
		document.inputForm.sysHousingCd.value = sysHousingCd;
		document.inputForm.action = url;
		document.inputForm.submit();
	}
}

function linkToUpd(url, housingRequestId) {
	document.inputForm.action = url;
	document.inputForm.housingRequestId.value = housingRequestId;
	document.inputForm.model.value = 'confirm';
	document.inputForm.submit();
}

function linkToDel(url, housingRequestId) {
	document.inputForm.action = url;
	document.inputForm.housingRequestId.value = housingRequestId;
	document.inputForm.model.value = 'delete';
	document.inputForm.submit();
}
$(document).ready(function(){
	var p2Height;
	var p2;
	var p2Text = "";
	var title = "";

	// 物件情報のタイトルを設定する
	$(".p2").each(function(){
		title = $(this).text();

		// 物件情報の編集
		p2Height = $(this).height();
		p2 = $(this).children("a");
		while(p2.outerHeight() > p2Height) {
			p2Text = p2.text();
			p2Text = p2Text.replace(/(\s)*([a-zA-Z0-9]+|\W)(\.\.\.)?$/,"...");
			p2.text(p2Text);
		};

		title = title.replace(/(\r)+|(\n)+|(\ )+|(\t)+/g,"");
		$(this).find("a").attr("title",title);
	});

});
-->
</script>
<style type="text/css">
.p1{
display: block;
overflow: hidden;
word-break: break-all;
white-space: nowrap;
text-overflow: ellipsis;
width: 100px;
}
.sp1{
display: block;
overflow: hidden;
word-break: break-all;
white-space: nowrap;
text-overflow: ellipsis;
width: 160px;
}
.sp2{
display: block;
overflow: hidden;
word-break: break-all;
white-space: nowrap;
text-overflow: ellipsis;
width: 250px;
}

.p2{
height: 34px;
}

</style>
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner" class="mypageTop">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="<c:out value='${commonParameters.resourceRootUrl}'/>">トップ</a></li>
					<li class="current">マイページ</li>
				</ul>
			</div>
		</div>

		<div class="mypageTitle">
			<h1>マイページ</h1>
		</div>

		<div class="section01 clearfix">
			<c:import url="/WEB-INF/jsp/front/include/mypageTop/sideBlock.jsh" />

			<div class="mainBlock">
				<div class="noticeBlock">
					<h2>お客様へのお知らせ</h2>

					<dl class="clearfix">
						<c:forEach items="${infoList}" var="information">
							<dt>
								<dm3lookup:lookupForEach lookupName="information_type">
									<c:if test="${005 == information.informationType}">
										<c:if test="${key == information.informationType}">
										<span class="iconNotice02"><c:out value="${value}"/></span><fmt:formatDate value="${information.insDate}" pattern="yyyy.MM.dd"/>
										</c:if>
									</c:if>
									<c:if test="${005 != information.informationType}">
										<c:if test="${key == information.informationType}">
										<span class="iconNotice01"><c:out value="${value}"/></span><fmt:formatDate value="${information.insDate}" pattern="yyyy.MM.dd"/>
										</c:if>
									</c:if>
								</dm3lookup:lookupForEach>
							</dt>
							<dd><a href="javascript:void(0);" onclick="linkToSubmit('<c:out value="${information.informationNo}"/>');"><c:out value="${information.title}"/></a></dd>
						</c:forEach>
					</dl>
				</div>

				<c:if test="${!empty favoriteInfoList || favoriteInfoList.size() > '0' }">
				<div class="favoritesProperty">
					<div class="title clearfix">
						<h2>お気に入り物件<br class="SPdisplayBlock"><span>※最大30件までご登録いただけます。</span></h2>
						<p><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/favorite/">もっと見る</a></p>
					</div>
					<div class="listBlock clearfix SPdisplayNone">
					<c:set var="count" value="${favoriteInfoList.size()}"/>
					<c:forEach var="favoriteInfo" items="${favoriteInfoList}" varStatus="varStatus">
						<c:if test="${varStatus.count <= 3}">

						<c:set var="housingInfo" value="${favoriteInfo.getHousingInfo().items['housingInfo']}"/>
						<c:set var="buildingInfo" value="${favoriteInfo.getBuilding().getBuildingInfo().items['buildingInfo']}"/>
						<c:set var="buildingStationInfoList" value="${favoriteInfo.getBuilding().getBuildingStationInfoList()}"/>
						<c:set var="housingImageInfoList" value="${housing.getHousingImageInfos()}"/>

						<div class="listBox clearfix" data-number="<c:out value="${housingInfo.housingCd}"/>">
							<dl class="clearfix">
								<dt>
									<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:if test="${empty favoriteInfoImgList.get(varStatus.index)}">
										<img src='<c:out value="${commonParameters.resourceRootUrl}${commonParameters.noPhoto86}"/>' alt="">
										</c:if>
										<c:if test="${!empty favoriteInfoImgList.get(varStatus.index)}">
										<img src="<c:out value='${favoriteInfoImgList.get(varStatus.index)}'/>" alt="">
										</c:if>
									</a>
								</dt>
								<dd>
									<p class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
									</p>
									<p class="name p1" title="<c:out value="${housingInfo.displayHousingName}"/>">
										<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:out value="${housingInfo.displayHousingName}"/>
										</a>
									</p>
									<p class="p2">
										<a style="text-decoration: none; color: black;">
										<c:if test="${!empty housingInfo.price or !empty housingInfo.layoutCd or buildingStationInfoList.size() > 0}">

										<c:if test="${!empty housingInfo.price}">
										<c:set var="price" value="${housingInfo.price / 10000}"/>
										<fmt:formatNumber type="number" value="${price + (1 - (price%1))%1}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>/

										<c:if test="${!empty housingInfo.layoutCd}">
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>/

										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>

										</c:if>
										</a>
									</p>
								</dd>
							</dl>
							<p class="btnOrange01"><a href="javascript:void(0);" onclick="linkToHousingInquory('<c:out value="${housingInfo.sysHousingCd}"/>');">お問い合わせ</a></p>
							<p class="btnGray01"><a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">詳細を見る</a></p>
						</div>

						</c:if>
					</c:forEach>
					</div>

					<div class="SPlistBlock SPdisplayBlock">
						<ul>
							<c:set var="count" value="${favoriteInfoList.size()}"/>
							<c:forEach var="favoriteInfo" items="${favoriteInfoList}" varStatus="varStatus">
							<c:if test="${varStatus.count <= 3}">

							<c:set var="housingInfo" value="${favoriteInfo.getHousingInfo().items['housingInfo']}"/>
							<c:set var="buildingInfo" value="${favoriteInfo.getBuilding().getBuildingInfo().items['buildingInfo']}"/>
							<c:set var="buildingStationInfoList" value="${favoriteInfo.getBuilding().getBuildingStationInfoList()}"/>

							<li>
								<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
									<p>
										<span class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
										</span>
										<span class="name sp1"><c:out value="${housingInfo.displayHousingName}"/></span>
									</p>
									<p class="sp2">
									<c:if test="${!empty housingInfo.price or !empty housingInfo.layoutCd or buildingStationInfoList.size() > 0}">

										<c:if test="${!empty housingInfo.price}">
										<c:set var="price" value="${housingInfo.price / 10000}"/>
										<fmt:formatNumber type="number" value="${price + (1 - (price%1))%1}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>/

										<c:if test="${!empty housingInfo.layoutCd}">
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>/

										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>

									</c:if>
									</p>
								</a>
							</li>
							</c:if>
							</c:forEach>
						</ul>
						<div class="btn">
						<p class="btnBlack01"><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/favorite/">もっと見る</a></p>
						</div>
					</div>
				</div>
				</c:if>

				<c:if test="${!empty recentlyInfoList || recentlyInfoList.size() > '0' }">
				<div class="viewedProperty">
					<div class="title clearfix">
						<h2>最近見た物件</h2>
						<p><a href="<c:out value='${pageContext.request.contextPath}'/>/history/">もっと見る</a></p>
					</div>
					<div class="listBlock clearfix SPdisplayNone">
					<c:set var="count" value="${recentlyInfoList.size()}"/>
					<c:forEach var="recentlyInfo" items="${recentlyInfoList}" varStatus="varStatus">
						<c:if test="${varStatus.count <= 3}">

						<c:set var="housingInfo" value="${recentlyInfo.getHousingInfo().items['housingInfo']}"/>
						<c:set var="buildingInfo" value="${recentlyInfo.getBuilding().getBuildingInfo().items['buildingInfo']}"/>
						<c:set var="buildingStationInfoList" value="${recentlyInfo.getBuilding().getBuildingStationInfoList()}"/>
						<c:set var="housingImageInfoList" value="${housing.getHousingImageInfos()}"/>

						<div class="listBox clearfix" data-number="<c:out value="${housingInfo.housingCd}"/>">
							<dl class="clearfix">
								<dt>
									<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:if test="${empty recentlyInfoImgList.get(varStatus.index)}">
										<img src='<c:out value="${commonParameters.resourceRootUrl}${commonParameters.noPhoto86}"/>' alt="">
										</c:if>
										<c:if test="${!empty recentlyInfoImgList.get(varStatus.index)}">
										<img src="<c:out value='${recentlyInfoImgList.get(varStatus.index)}'/>" alt="">
										</c:if>
									</a>
								</dt>
								<dd>
									<p class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
									</p>
									<p class="name p1" title="<c:out value="${housingInfo.displayHousingName}"/>">
										<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:out value="${housingInfo.displayHousingName}"/>
										</a>
									</p>
									<p class="p2">
										<a style="text-decoration: none; color: black;">
										<c:if test="${!empty housingInfo.price or !empty housingInfo.layoutCd or buildingStationInfoList.size() > 0}">

										<c:if test="${!empty housingInfo.price}">
										<c:set var="price" value="${housingInfo.price / 10000}"/>
										<fmt:formatNumber type="number" value="${price + (1 - (price%1))%1}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>/

										<c:if test="${!empty housingInfo.layoutCd}">
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>/

										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>

										</c:if>
										</a>
									</p>
								</dd>
							</dl>
							<p class="btnOrange01"><a href="javascript:void(0);" onclick="linkToHousingInquory('<c:out value="${housingInfo.sysHousingCd}"/>');">お問い合わせ</a></p>
							<p class="btnGray01"><a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">詳細を見る</a></p>
						</div>

						</c:if>
					</c:forEach>
					</div>

					<div class="SPlistBlock SPdisplayBlock">
						<ul>
							<c:set var="count" value="${recentlyInfoList.size()}"/>
							<c:forEach var="recentlyInfo" items="${recentlyInfoList}" varStatus="varStatus">
							<c:if test="${varStatus.count <= 3}">

							<c:set var="housingInfo" value="${recentlyInfo.getHousingInfo().items['housingInfo']}"/>
							<c:set var="buildingInfo" value="${recentlyInfo.getBuilding().getBuildingInfo().items['buildingInfo']}"/>
							<c:set var="buildingStationInfoList" value="${recentlyInfo.getBuilding().getBuildingStationInfoList()}"/>

							<li>
								<a href="javascript:void(0);" onclick="housingDetailToSubmit('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
									<p>
										<span class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
										</span>
										<span class="name sp1"><c:out value="${housingInfo.displayHousingName}"/></span>
									</p>
									<p class="sp2">
									<c:if test="${!empty housingInfo.price or !empty housingInfo.layoutCd or buildingStationInfoList.size() > 0}">

										<c:if test="${!empty housingInfo.price}">
										<c:set var="price" value="${housingInfo.price / 10000}"/>
										<fmt:formatNumber type="number" value="${price + (1 - (price%1))%1}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>/

										<c:if test="${!empty housingInfo.layoutCd}">
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>/

										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>

									</c:if>
									</p>
								</a>
							</li>
							</c:if>
							</c:forEach>
						</ul>
						<div class="btn">
						<p class="btnBlack01"><a href="<c:out value='${pageContext.request.contextPath}'/>/history/">もっと見る</a></p>
						</div>
					</div>
				</div>
				</c:if>

				<div class="requestProperty">
					<div class="title">
						<h2>物件リクエスト</h2>
						<p>お客様のリクエスト条件は以下のように登録されています。ご要望に合致する、または近い物件が見つかり次第、不動産会社担当者よりご連絡させていただきます。</p>
					</div>

					<div class="conditionsList">
						<p class="mb05 f14">リクエスト条件は、最大5件までご登録いただけます。</p>
						<h3>登録条件一覧<span>※最新順</span></h3>
						<div class="listInner">
						<c:set var="requestCount"/>
						<c:forEach var="housingRequestList" items="${housingRequestLst}" varStatus="varStatus">
							<c:set value="${housingRequestList.items['housingRequest']}" var="housingRequest"/>
							<c:if test="${varStatus.count <= 5}">
							<c:set var="requestCount" value="${varStatus.count}"/>
							<div class="listBox clearfix">
								<dl class="conditions clearfix">
									<dt>物件種別</dt>
									<dd><dm3lookup:lookup lookupName="hosuing_kind" lookupKey="${housingRequest.housingKindCd}"/></dd>
									<dt>都道府県</dt>
									<dd><c:out value="${housingRequest.prefName}"/></dd>
									<dt style="display: none">市区町村</dt>
									<dd style="display: none"><c:out value="${housingRequest.addressCd}"/></dd>
									<dt>検索条件</dt>
									<dd><c:out value="${housingRequest.requests}"/></dd>
								</dl>

								<div class="btn">
									<p class="btnBlack01"><a href="javascript:linkToUpd('./request/confirm/','<c:out value="${housingRequest.housingRequestId}"/>');">確認・変更</a></p>
									<p class="btnGray01"><a href="javascript:linkToDel('./request/delete/confirm/','<c:out value="${housingRequest.housingRequestId}"/>');">削除する</a></p>
								</div>
							</div>
							</c:if>
						</c:forEach>
						<c:if test="${requestCount < '5'}">
							<div class="btnBox">
								<p class="btnOrange01"><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/request/input/">物件リクエスト条件を登録する</a></p>
							</div>
						</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="bnrBlock">
			<!--#include virtual="/common/ssi/column.html"-->
		</div>
	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->
<form action="" method="post" name="inputForm" target="_blank">
	<input type="hidden" name="housingRequestId" value="" />
	<input type="hidden" name="model" value="" />
	<input type="hidden" name="sysHousingCd" value="" />
</form>
</body>
</html>
