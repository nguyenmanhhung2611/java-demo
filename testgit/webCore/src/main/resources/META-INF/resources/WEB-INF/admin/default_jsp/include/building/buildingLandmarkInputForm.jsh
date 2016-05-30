<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
建物情報メンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" name="buildingCd" value="<c:out value="${inputForm.buildingCd}"/>">
		<input type="hidden" name="displayBuildingName" value="<c:out value="${inputForm.displayBuildingName}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
		<table class="inputBox">
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
			<dm3lookup:lookupForEach lookupName="buildingLandmark_landmarkType">
			<tr>
				<th class="head_tr"><c:out value="${value}"/>&nbsp;&nbsp;</th>
				<td>
					<input type="hidden" name="landmarkType" value="<c:out value="${key}"/>">
					<c:set var="exitFlg" value="0"></c:set>
					<c:forEach items="${inputForm.landmarkType}" varStatus="status">
					<c:if test="${key == inputForm.landmarkType[status.index]}">
						<input type="text" name="landmarkName" value="<c:out value="${inputForm.landmarkName[status.index]}"/>" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.landmarkName" defaultValue="80"/>" class="input2">
						:
						<select name="wayFromLandmark">
						<option></option>
						<dm3lookup:lookupForEach lookupName="buildingLandmark_wayFromLandmark">
	        			 	<option value="${key}"<c:if test="${inputForm.wayFromLandmark[status.index] == key}">selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
						</select>
						&nbsp;
						徒歩
						<input type="text" name="timeFromLandmark" value="<c:out value="${inputForm.timeFromLandmark[status.index]}"/>" size="1" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.timeFromLandmark" defaultValue="3"/>" class="input2">
						分&nbsp;
						距離
						<input type="text" name="distanceFromLandmark" value="<c:out value="${inputForm.distanceFromLandmark[status.index]}"/>" size="6" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.distanceFromLandmark" defaultValue="8"/>" class="input2">
						km&nbsp;
						表示順
						<input type="text" name="sortOrder" value="<c:out value="${inputForm.sortOrder[status.index]}"/>" size="1" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.sortOrder" defaultValue="3"/>" class="input2">
						<c:set var="exitFlg" value="1"></c:set>
					</c:if>

					</c:forEach>
					<c:if test="${exitFlg != '1'}">
						<input type="text" name="landmarkName" value="" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.landmarkName" defaultValue="80"/>" class="input2">
						:
						<select name="wayFromLandmark">
						<option></option>
						<dm3lookup:lookupForEach lookupName="buildingLandmark_wayFromLandmark">
	        			 	<option value="${key}"><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
						</select>
						&nbsp;
						徒歩
						<input type="text" name="timeFromLandmark" value="" size="1" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.timeFromLandmark" defaultValue="3"/>" class="input2">
						分&nbsp;
						距離
						<input type="text" name="distanceFromLandmark" value="" size="6" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.distanceFromLandmark" defaultValue="8"/>" class="input2">
						km&nbsp;
						表示順
						<input type="text" name="sortOrder" value="" size="1" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingLandmark.input.sortOrder" defaultValue="3"/>" class="input2">
					</c:if>
					
				</td>
			</tr>
			</dm3lookup:lookupForEach>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->
<script type="text/javascript">
<!--
function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}
// -->
</script>



