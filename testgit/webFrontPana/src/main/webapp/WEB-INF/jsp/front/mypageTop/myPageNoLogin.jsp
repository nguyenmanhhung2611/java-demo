<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="マイページ,お気に入り,物件リクエスト,不動産売買,パナソニック,<c:out value='${commonParameters.panasonicSiteEnglish}'/>,<c:out value='${commonParameters.panasonicSiteJapan}'/>,Re2,リー・スクエア">
<meta name="description" content="お気に入り登録や物件リクエストが出来るパナソニック<c:out value='${commonParameters.panasonicSiteEnglish}'/>(<c:out value='${commonParameters.panasonicSiteJapan}'/>)のマイページ。会員登録いただければ、リフォームの詳細なパース図や3D動画もご覧いただけます。">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>マイページ｜<c:out value='${commonParameters.panaReSmail}'/></title>

<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>mypage/css/mypage.css" type="text/css" media="screen,print" />
<link rel="stylesheet" href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/jquery.fancybox.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.heightLine.js"></script>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>

<script type="text/javascript">
<!--
$(function(){
	$("#block01>dl>dt>.title").heightLine({minWidth: 640});
	$("#block01>dl>dd").heightLine({minWidth: 640});
	$("#block02>dl>dt>.title").heightLine({minWidth: 640});
	$("#block02>dl>dd").heightLine({minWidth: 640});
	$(".bnrBlock>.columnBlock>.blockInner>a>dl").heightLine({minWidth: 640});
});

function linkToHousingDetail(prefCd, sysHousingCd, housingKindCd) {
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

		document.detailForm.action = url;
		document.detailForm.submit();
	}
}

function linkToHousingInquiry(sysHousingCd) {
	var url = "<c:out value='${pageContext.request.contextPath}'/>/buy/inquiry/division/";

	document.inputForm.action = url;
	document.inputForm.sysHousingCd.value = sysHousingCd;
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

<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />

<div id="ptop"></div>

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />

		<!--#include virtual="/mypage/ssi/mypage_service01.html"-->

				<c:if test="${!empty recentlyInfoList || recentlyInfoList.size() > '0' }">
				<div class="viewedProperty mb00">
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
									<a href="javascript:void(0);" onclick="linkToHousingDetail('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:choose>
											<c:when test="${!empty recentlyInfoList && recentlyInfoImgList.get(varStatus.index) != null && recentlyInfoImgList.get(varStatus.index).length() > 0}">
												<img src="<c:out value='${recentlyInfoImgList.get(varStatus.index)}'/>" alt="">
											</c:when>
											<c:otherwise>
												<img src="<c:out value='${commonParameters.resourceRootUrl}${commonParameters.noPhoto86}'/>" alt="">
											</c:otherwise>
										</c:choose>
									</a>
								</dt>
								<dd>
									<p class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
									</p>
									<p class="name p1" title="<c:out value="${housingInfo.displayHousingName}"/>">
										<a href="javascript:void(0);" onclick="linkToHousingDetail('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
										<c:out value="${housingInfo.displayHousingName}"/>
										</a>
									</p>
									<p class="p2">
										<a style="text-decoration: none; color: black;">
										<c:if test="${!empty housingInfo.price}">
											<fmt:formatNumber type="number" maxFractionDigits="3" value="${Math.ceil(housingInfo.price / 10000)}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>
										<c:if test="${!empty housingInfo.price || !empty housingInfo.layoutCd || !empty buildingStationInfoList}">/</c:if>
										<c:if test="${!empty housingInfo.layoutCd}">
											<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>
										<c:if test="${!empty housingInfo.price || !empty housingInfo.layoutCd || !empty buildingStationInfoList}">/</c:if>
										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>
										</a>
									</p>
								</dd>
							</dl>
							<p class="btnOrange01"><a href="javascript:void(0);" onclick="linkToHousingInquiry('<c:out value="${housingInfo.sysHousingCd}"/>');">お問い合わせ</a></p>
							<p class="btnGray01"><a href="javascript:void(0);" onclick="linkToHousingDetail('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">詳細を見る</a></p>
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
								<a href="javascript:void(0);" onclick="linkToHousingDetail('<c:out value="${buildingInfo.prefCd}"/>', '<c:out value="${housingInfo.sysHousingCd}"/>', '<c:out value="${buildingInfo.housingKindCd}"/>');">
									<p>
										<span class="icoMansion01">
										<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${buildingInfo.housingKindCd}"/>
										</span>
										<span class="name sp1"><c:out value="${housingInfo.displayHousingName}"/></span>
									</p>
									<p class="sp2">
										<c:if test="${!empty housingInfo.price}">
										<fmt:formatNumber type="number" maxFractionDigits="3" value="${Math.ceil(housingInfo.price / 10000)}" pattern="###,###"></fmt:formatNumber>万円
										</c:if>
										<c:if test="${!empty housingInfo.price || !empty housingInfo.layoutCd || !empty buildingStationInfoList}">/</c:if>
										<c:if test="${!empty housingInfo.layoutCd}">
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${housingInfo.layoutCd}"/>
										</c:if>
										<c:if test="${!empty housingInfo.price || !empty housingInfo.layoutCd || !empty buildingStationInfoList}">/</c:if>
										<c:forEach var="items" items="${buildingStationInfoList}">
											<c:set var="buildingStationInfo" value="${items.items['buildingStationInfo']}"></c:set>
											<c:set var="stationMst" value="${items.items['stationMst']}"></c:set>
											<c:if test="${empty stationMst.stationName}"><c:out value="${buildingStationInfo.stationName}"/></c:if>
											<c:if test="${!empty stationMst.stationName}"><c:out value="${stationMst.stationName}"/></c:if>
										</c:forEach>
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

			</div>
		</div>
		<!--#include virtual="/mypage/ssi/mypage_service02.html"-->
	</div>
</div>

<form action="" method="post" name="detailForm" target="_blank">
</form>
<form action="" method="post" name="inputForm" target="_blank">
	<input type="hidden" name="sysHousingCd" value="" />
</form>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>
</html>
