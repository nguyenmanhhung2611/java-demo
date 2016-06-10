<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
建物情報メンテナンス機能で使用する入力フォームの出力 
--%>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" id="prefCd" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
		<dm3token:oneTimeToken/>
		<c:forEach begin="0" end="2" varStatus="status">
		<br/>
		<input type="hidden" name="routeName" value="<c:out value="${inputForm.routeName[status.index]}"/>">
		<input type="hidden" name="stationName" value="<c:out value="${inputForm.stationName[status.index]}"/>">
		<input type="hidden" name="sortOrder" value="<c:out value="${inputForm.sortOrder[status.index]}"/>">
		<input type="hidden" name="defaultRouteCd" value="<c:out value="${inputForm.defaultRouteCd[status.index]}"/>">
		<input type="hidden" name="stationCd" value="<c:out value="${inputForm.stationCd[status.index]}"/>">
		<input type="hidden" name="wayFromStation" value="<c:out value="${inputForm.wayFromStation[status.index]}"/>">
		<input type="hidden" name="distanceFromStation" value="<c:out value="${inputForm.distanceFromStation[status.index]}"/>">
		<input type="hidden" name="timeFromStation" value="<c:out value="${inputForm.timeFromStation[status.index]}"/>">
		<input type="hidden" name="busCompany" value="<c:out value="${inputForm.busCompany[status.index]}"/>">
		<input type="hidden" name="busRequiredTime" value="<c:out value="${inputForm.busRequiredTime[status.index]}"/>">
		<input type="hidden" name="busStopName" value="<c:out value="${inputForm.busStopName[status.index]}"/>">
		<input type="hidden" name="timeFromBusStop" value="<c:out value="${inputForm.timeFromBusStop[status.index]}"/>">
		<div class="headingAreaB01 start">
			<h2>最寄り駅情報<c:out value="${status.index + 1}"/></h2>
		</div>
		<table class="confirmBox">
			<tr>
				<th class="head_tr">路線</th>
				<td>
					<c:out value="${inputForm.routeName[status.index]}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">駅</th>
				<td>
					<c:out value="${inputForm.stationName[status.index]}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">最寄り駅からの手段</th>
				<td>
					<dm3lookup:lookup lookupName="buildingStationInfo_wayFromStation" lookupKey="${inputForm.wayFromStation[status.index]}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">駅からの距離</th>
				<td>
				<c:if test="${inputForm.distanceFromStation[status.index] != ''}">
					<c:out value="${inputForm.distanceFromStation[status.index]}"/>&nbsp;km
				</c:if>
				&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">駅からの徒歩時間</th>
				<td>
				<c:if test="${inputForm.distanceFromStation[status.index] != ''}">
					<c:out value="${inputForm.timeFromStation[status.index]}"/>&nbsp;分
				</c:if>
				&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス会社名</th>
				<td>
					<c:out value="${inputForm.busCompany[status.index]}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バスの所要時間</th>
				<td>
				<c:if test="${inputForm.busRequiredTime[status.index] != ''}">
					<c:out value="${inputForm.busRequiredTime[status.index]}"/>&nbsp;分
				</c:if>
				&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス停名</th>
				<td>
					<c:out value="${inputForm.busStopName[status.index]}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス停からの徒歩時間</th>
				<td>
				<c:if test="${inputForm.timeFromBusStop[status.index] != ''}">
					<c:out value="${inputForm.timeFromBusStop[status.index]}"/>&nbsp;分
				</c:if>
				&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">表示順</th>
				<td>
					<c:out value="${inputForm.sortOrder[status.index]}"/>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</c:forEach>
	</form>
</div>
<!--/flexBlockA01 -->
<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>


