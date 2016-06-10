<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/core/functions" prefix="webcore" %>
<%-- ----------------------------------------------------------------
 名称：物件詳細画面

 2015/07/08		H.Mizuno	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/front/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="物件詳細" />
<c:param name="contents">

<c:set var="d" value="${displayAdapter}" />
<c:set var="buildingInfo" value="${housing.building.buildingInfo.items.buildingInfo}" />
<c:set var="buildingDtlInfo" value="${housing.building.buildingInfo.items.buildingDtlInfo}" />
<c:set var="housingInfo" value="${housing.housingInfo.items.housingInfo}" />
<c:set var="housingDtlInfo" value="${housing.housingInfo.items.housingDtlInfo}" />

<div><c:out value="${webcore:display(d, housingInfo, 'displayHousingName')}"/>&nbsp;&nbsp;<span id="newMark"><c:if test="${webcore:display(d, housingInfo, 'new')}">【新着】</c:if></span></div>
<div>物件番号：<span id="housingCd"><c:out value="${webcore:display(d, housingInfo, 'housingCd')}"/></span></div>
<div>物件種別：<span id="housingKind"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="${buildingInfo.housingKindCd}"/></span></div>
<div>郵便番号：<span id="zip"><c:out value="${webcore:display(d, buildingInfo, 'zip')}"/></span></div>
<div>所在地：<span id="displayAddress"><c:out value="${webcore:display(d, housing, 'displayAddress')}"/></span></div>
<div>価格：<span id="price"><c:out value="${webcore:display(d, housingInfo, 'price')}"/></span></div>
<div>築年月：<span id="compDate"><c:out value="${webcore:display(d, buildingInfo, 'compDate')}"/></span></div>

<p>
<b>【建物情報】</b><br>
<div>建物構造：<span id="struct"><dm3lookup:lookup lookupName="buildingInfo_structCd" lookupKey="${buildingInfo.structCd}"/></span></div>
<div>建物階数：<span id="totalFloors"><c:out value="${webcore:display(d, buildingInfo, 'totalFloors')}"/></span></div>
<div>建物面積：
	<span id="buildingArea"><c:out value="${webcore:display(d, buildingDtlInfo, 'buildingArea')}"/></span>&nbsp;&nbsp;
	<span id="buildingAreaMemo"><c:out value="${webcore:display(d, buildingDtlInfo, 'buildingAreaMemo')}"/></span>
</div>
<div>建ぺい率：
	<span id="coverage"><c:out value="${dm3functions:addSuffix(webcore:display(d, buildingDtlInfo, 'coverage'), '%')}"/></span>&nbsp;&nbsp;
	<span id="coverageMemo"><c:out value="${webcore:display(d, buildingDtlInfo, 'coverageMemo')}"/></span>
</div>
<div>容積率：
	<span id="buildingRate"><c:out value="${dm3functions:addSuffix(webcore:display(d, buildingDtlInfo, 'buildingRate'), '%')}"/></span>&nbsp;&nbsp;
	<span id="buildingRateMemo"><c:out value="${webcore:display(d, buildingDtlInfo, 'buildingRateMemo')}"/></span>
</div>
<div>総戸数：<span id="totalHouseCnt"><c:out value="${webcore:display(d, buildingDtlInfo, 'totalHouseCnt')}"/></span></div>
<div>賃貸戸数：<span id="totalHouseCnt"><c:out value="${webcore:display(d, buildingDtlInfo, 'leaseHouseCnt')}"/></span></div>
<div>建物緯度：<span id="totalHouseCnt"><c:out value="${webcore:display(d, buildingDtlInfo, 'buildingLatitude')}"/></span></div>
<div>建物経度：<span id="totalHouseCnt"><c:out value="${webcore:display(d, buildingDtlInfo, 'buildingLongitude')}"/></span></div>

<p>
<b>【物件情報】</b><br>
<div>窓の向き：<span id="windowDirection"><dm3lookup:lookup lookupName="direction" lookupKey="${housingInfo.windowDirection}"/></span></div>
<div>駐車場情報：<span id="displayParkingInfo"><c:out value="${webcore:display(d, housingInfo, 'displayParkingInfo')}"/></span></div>
<div>間取り：<span id="layout"><dm3lookup:lookup lookupName="layout" lookupKey="${housingInfo.layoutCd}"/></span></div>
<div>間取りコメント：<br>
<span id="layoutComment">${dm3functions:crToHtmlTag(webcore:display(d, housingInfo, "layoutComment"))}</span>
</div>
<div>基本情報コメント：<br>
<span id="basicComment">${dm3functions:crToHtmlTag(webcore:display(d, housingInfo, 'basicComment'))}</span>
</div>

<p>
<b>【表示用アイコン情報】</b>
<ul>
<c:forEach var="iconCd" items="${webcore:display(d, housingInfo, 'iconCd')}">
	<li><c:out value="${iconCd}" /></li>
</c:forEach>
</ul>

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputdate" >
	<input type="hidden" name="command" value="">
</form>

</c:param>
</c:import>