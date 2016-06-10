<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
お知らせメンテナンス機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="15%" class="head_tr"/>
			<col width="21%"/>
			<col width="11%" class="head_tr"/>
			<col width="21%"/>
			<col width="11%" class="head_tr"/>
			<col width="21%"/>
		</colgroup>
		<tr>
			<th class="head_tr">物件番号</th>
			<td colspan="5"><c:out value="${housingInfoForm.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td colspan="5"><c:out value="${housingInfoForm.displayHousingName}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件種別</th>
			<td colspan="5">
				<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd">
                   <c:if test="${housingInfoForm.housingKindCd == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">価格</th>
			<td><c:if test="${!empty housingInfoForm.price}"><fmt:formatNumber value="${housingInfoForm.price}" pattern="###,###" />円</c:if>&nbsp;</td>
			<th class="head_tr">築年</th>
			<td>
			<c:if test="${housingInfoForm.compDate != '' && housingInfoForm.compDate != null}">
				<c:out value="${year}"/>年<c:out value="${month}"/>月
			</c:if>&nbsp;
			</td>
			<th class="head_tr">間取り</th>
			<td>
				<dm3lookup:lookupForEach lookupName="layoutCd">
                   <c:if test="${housingInfoForm.layoutCd == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">住所</th>
			<td colspan="5">
				<c:out value="${address}"/>
			</td>
		</tr>
		<tr rowspan="3">
			<th class="head_tr">最寄り駅</th>
			<td colspan="5">
			<c:set var="index" value="0" />
			<c:forEach items="${housingInfoForm.defaultRouteCd}" varStatus="status" step="1">
				<c:if test="${housingInfoForm.defaultRouteCd[status.index] != ''}">
				<c:out value="${housingInfoForm.routeName[status.index]}"/>
				<c:out value="${stationName[status.index]}"/>
				<c:out value="${housingInfoForm.busCompany[status.index]}"/>
				<c:if test="${housingInfoForm.timeFromBusStop[status.index] != '' && housingInfoForm.timeFromBusStop[status.index] != null}">
				徒歩<c:out value="${housingInfoForm.timeFromBusStop[status.index]}"/>分
				</c:if>
				<br>
				<c:set var="index" value="${index+1}" />
				</c:if>
			</c:forEach>
			<c:if test="${index == 0}">
			&nbsp;
			</c:if>
			</td>
		</tr>
		<tr rowspan="2">
			<th class="head_tr">建物面積</th>
			<td width="350">
				<c:if test="${housingInfoForm.buildingArea != '' && housingInfoForm.buildingArea != null}">
				<c:out value="${housingInfoForm.buildingArea}"/>m²
				</c:if>
				<c:if test="${housingInfoForm.buildingAreaCon != '' && housingInfoForm.buildingAreaCon != null}">
				(約<c:out value="${housingInfoForm.buildingAreaCon}"/>坪)
				</c:if>
				<br>
				<c:if test="${housingInfoForm.buildingAreaMemo != '' && housingInfoForm.buildingAreaMemo != null}">
				/<c:out value="${housingInfoForm.buildingAreaMemo}"/>
				</c:if>
			</td>
			<th class="head_tr">土地面積</th>
			<td width="350">
				<c:if test="${housingInfoForm.landArea != '' && housingInfoForm.landArea != null}">
				<c:out value="${housingInfoForm.landArea}"/>m²
				</c:if>
				<c:if test="${housingInfoForm.landAreaCon != '' && housingInfoForm.landAreaCon != null}">
				(約<c:out value="${housingInfoForm.landAreaCon}"/>坪)
				</c:if>
				<br>
				<c:if test="${housingInfoForm.landAreaMemo != '' && housingInfoForm.landAreaMemo != null}">
				/<c:out value="${housingInfoForm.landAreaMemo}"/>
				</c:if>
			</td>
			<th class="head_tr">専有面積</th>
			<td width="350">
				<c:if test="${housingInfoForm.personalArea != '' && housingInfoForm.personalArea != null}">
				<c:out value="${housingInfoForm.personalArea}"/>m²
				</c:if>
				<c:if test="${housingInfoForm.personalAreaCon != '' && housingInfoForm.personalAreaCon != null}">
				(約<c:out value="${housingInfoForm.personalAreaCon}"/>坪)
				</c:if>
				<br>
				<c:if test="${housingInfoForm.personalAreaMemo != '' && housingInfoForm.personalAreaMemo != null}">
				/<c:out value="${housingInfoForm.personalAreaMemo}"/>
				</c:if>
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="pageId" value="housingInfo">
	<input type="hidden" name="command" value="<c:out value="${housingInfoForm.command}"/>">
	<input type="hidden" name="comflg" value="<c:out value="${housingInfoForm.comflg}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${housingInfoForm.sysHousingCd}"/>">
	<input type="hidden" name="sysBuildingCd" value="<c:out value="${housingInfoForm.sysBuildingCd}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${housingInfoForm.housingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${housingInfoForm.displayHousingName}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${housingInfoForm.housingKindCd}"/>">
	<input type="hidden" name="price" value="<c:out value="${housingInfoForm.price}"/>">
	<input type="hidden" name="compDate" value="<c:out value="${housingInfoForm.compDate}"/>">
	<input type="hidden" name="layoutCd" value="<c:out value="${housingInfoForm.layoutCd}"/>">
	<input type="hidden" name="zip" value="<c:out value="${housingInfoForm.zip}"/>">
	<input type="hidden" name="prefCd" value="<c:out value="${housingInfoForm.prefCd}"/>">
	<input type="hidden" name="prefName" value="<c:out value="${housingInfoForm.prefName}"/>">
	<input type="hidden" name="addressCd" value="<c:out value="${housingInfoForm.addressCd}"/>">
	<input type="hidden" name="addressName" value="<c:out value="${housingInfoForm.addressName}"/>">
	<input type="hidden" name="addressOther1" value="<c:out value="${housingInfoForm.addressOther1}"/>">
	<input type="hidden" name="addressOther2" value="<c:out value="${housingInfoForm.addressOther2}"/>">

	<c:forEach items="${housingInfoForm.defaultRouteCd}" varStatus="status" step="1">
		<c:if test="${housingInfoForm.defaultRouteCd[status.index] != ''}">
		<input type="hidden" name="defaultRouteCd" value="<c:out value="${housingInfoForm.defaultRouteCd[status.index]}"/>">
		<input type="hidden" name="routeNameRr" value="<c:out value="${housingInfoForm.routeNameRr[status.index]}"/>">
		<input type="hidden" name="routeName" value="<c:out value="${housingInfoForm.routeName[status.index]}"/>">
		<input type="hidden" name="stationCd" value="<c:out value="${housingInfoForm.stationCd[status.index]}"/>">
		<input type="hidden" name="stationName" value="<c:out value="${housingInfoForm.stationName[status.index]}"/>">
		<input type="hidden" name="oldRouteName" value="<c:out value="${housingInfoForm.oldRouteName[status.index]}"/>">
		<input type="hidden" name="oldStationName" value="<c:out value="${housingInfoForm.oldStationName[status.index]}"/>">
		<input type="hidden" name="oldDefaultRouteCd" value="<c:out value="${housingInfoForm.oldDefaultRouteCd[status.index]}"/>">
		<input type="hidden" name="oldStationCd" value="<c:out value="${housingInfoForm.oldStationCd[status.index]}"/>">
		<input type="hidden" name="busCompany" value="<c:out value="${housingInfoForm.busCompany[status.index]}"/>">
		<input type="hidden" name="timeFromBusStop" value="<c:out value="${housingInfoForm.timeFromBusStop[status.index]}"/>">
		</c:if>
	</c:forEach>

	<input type="hidden" name="buildingArea" value="<c:out value="${housingInfoForm.buildingArea}"/>">
	<input type="hidden" name="buildingAreaCon" value="<c:out value="${housingInfoForm.buildingAreaCon}"/>">
	<input type="hidden" name="buildingAreaMemo" value="<c:out value="${housingInfoForm.buildingAreaMemo}"/>">
	<input type="hidden" name="landArea" value="<c:out value="${housingInfoForm.landArea}"/>">
	<input type="hidden" name="landAreaCon" value="<c:out value="${housingInfoForm.landAreaCon}"/>">
	<input type="hidden" name="landAreaMemo" value="<c:out value="${housingInfoForm.landAreaMemo}"/>">
	<input type="hidden" name="personalArea" value="<c:out value="${housingInfoForm.personalArea}"/>">
	<input type="hidden" name="personalAreaCon" value="<c:out value="${housingInfoForm.personalAreaCon}"/>">
	<input type="hidden" name="personalAreaMemo" value="<c:out value="${housingInfoForm.personalAreaMemo}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>

