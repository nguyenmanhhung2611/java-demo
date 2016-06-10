<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- 
建物情報メンテナンス機能で使用する最寄り駅情報の出力 
--%>

<c:set var="buildingInfo" value="${building.items['buildingInfo']}"/>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">最寄り駅</th>
			<td>
			<c:forEach items="${buildingStationList}" var="buildingStation" varStatus="status">
				<c:set var="buildingStationInfo" value="${buildingStation.items['buildingStationInfo']}"/>
				<c:set var="routeMst" value="${buildingStation.items['routeMst']}"/>
				<c:set var="stationMst" value="${buildingStation.items['stationMst']}"/>
				<c:out value="${routeMst.routeName}"/>&nbsp;
				<c:out value="${stationMst.stationName}"/>&nbsp;
				<c:if test="${buildingStationInfo.wayFromStation == 1}">
					徒歩<c:out value="${buildingStationInfo.timeFromStation}"/>分&nbsp;
				</c:if>
				<c:if test="${buildingStationInfo.wayFromStation == 2}">
					バス<c:out value="${buildingStationInfo.busRequiredTime}"/>下車&nbsp;
					徒歩<c:out value="${buildingStationInfo.timeFromBusStop}"/>分&nbsp;
				</c:if>
				<c:if test="${buildingStationInfo.wayFromStation == 3}">
					<c:out value="${buildingStationInfo.distanceFromStation}"/>km&nbsp;
				</c:if>
				<c:if test="${status.index != 2}">
				<br/>
				</c:if>
			</c:forEach>&nbsp;
			</td>
		</tr>
	</table>
</div>
<c:if test="${inputForm.command != 'delete'}">
<!--/flexBlockA01 -->
<div class="flexBlockBldDtl">
	<div class="flexBlockBldDtlInner">
		<div class="btnBlockEdit">
			<div class="btnBlockEditInner">
				<div class="btnBlockEditInner2">
					<p><a href="javascript:linkToUpdBuildingStationInfo('../station/input/');"><span>編集</span></a></p>
				</div>
			</div>
		</div>
	</div>
</div>
<br/>
<br/>
</c:if>
<form method="post" name="buildingStationInfo" action="">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="sysBuildingCd" value="<c:out value='${buildingInfo.sysBuildingCd}'/>">
	<input type="hidden" name="prefCd" value="<c:out value="${buildingInfo.prefCd}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
</form>
