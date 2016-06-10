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
		<input type="hidden" name="buildingCd" value="<c:out value="${inputForm.buildingCd}"/>">
		<input type="hidden" name="displayBuildingName" value="<c:out value="${inputForm.displayBuildingName}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
		<table class="confirmBox">
		<dm3token:oneTimeToken/>
			<tr>
				<th class="head_tr">物件番号</th>
				<td>
					<c:out value="${inputForm.buildingCd}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td>
					<c:out value="${inputForm.displayBuildingName}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<c:forEach items="${inputForm.landmarkType}" varStatus="status">
			<input type="hidden" name="landmarkType" value="<c:out value="${inputForm.landmarkType[status.index]}"/>">
			<input type="hidden" name="wayFromLandmark" value="<c:out value="${inputForm.wayFromLandmark[status.index]}"/>">
			<input type="hidden" name="sortOrder" value="<c:out value="${inputForm.sortOrder[status.index]}"/>">
			<input type="hidden" name="landmarkName" value="<c:out value="${inputForm.landmarkName[status.index]}"/>">
			<input type="hidden" name="timeFromLandmark" value="<c:out value="${inputForm.timeFromLandmark[status.index]}"/>">
			<input type="hidden" name="distanceFromLandmark" value="<c:out value="${inputForm.distanceFromLandmark[status.index]}"/>">
			<tr>
				<th class="head_tr"><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="${inputForm.landmarkType[status.index]}"/></th>
				<td>
					<c:if test="${inputForm.landmarkName[status.index] != ''}">
					<c:out value="${inputForm.landmarkName[status.index]}"/>&nbsp;
					手段:<dm3lookup:lookup lookupName="buildingLandmark_wayFromLandmark" lookupKey="${inputForm.wayFromLandmark[status.index]}"/>&nbsp;
					徒歩<c:out value="${inputForm.timeFromLandmark[status.index]}"/>分&nbsp;
					距離<c:out value="${inputForm.distanceFromLandmark[status.index]}"/>km&nbsp;
					表示順：<c:out value="${inputForm.sortOrder[status.index]}"/>
					&nbsp;
					</c:if>
					&nbsp;
				</td>
			</tr>
			</c:forEach>
		</table>
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


