<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
地域情報編集確認機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="pageId" value="housingLandmark">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" style="width:20%">物件番号</th>
			<td><c:out value="${inputForm.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
		</tr>
		<c:forEach items="${inputForm.landmarkType}" varStatus="status">
			<input type="hidden" name="landmarkType" value="<c:out value="${inputForm.landmarkType[status.index]}"/>">
			<input type="hidden" name="wayFromLandmark" value="<c:out value="${inputForm.wayFromLandmark[status.index]}"/>">
			<input type="hidden" name="sortOrder" value="<c:out value="${inputForm.sortOrder[status.index]}"/>">
			<input type="hidden" name="landmarkName" value="<c:out value="${inputForm.landmarkName[status.index]}"/>">
			<input type="hidden" name="timeFromLandmark" value="<c:out value="${inputForm.timeFromLandmark[status.index]}"/>">
			<input type="hidden" name="distanceFromLandmark" value="<c:out value="${inputForm.distanceFromLandmark[status.index]}"/>">
			<dm3token:oneTimeToken/>
			<tr>
				<th class="head_tr"><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="${inputForm.landmarkType[status.index]}"/></th>
				<td><c:out value="${inputForm.landmarkName[status.index]}"/>
					<c:if test="${!empty inputForm.timeFromLandmark[status.index]}">
					：徒歩<c:out value="${inputForm.timeFromLandmark[status.index]}"/>分（<c:out value="${inputForm.distanceFromLandmark[status.index]}"/>m ）
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
	function popupPreview(url) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.submit();
	}
// -->
</script>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.target = "";
		document.inputForm.submit();
	}
// -->
</script>
