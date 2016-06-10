<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
地域情報編集機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">
<!--
	function changeTime(obj) {
		var inputFields = $(obj).closest("td").find("input:text");
		$(obj).val($.trim($(obj).val()));
		if (!$.isNumeric($(obj).val())) {
			$(inputFields[2]).val('');
			return;
		}
		var distance = Number(obj.value);
		var time = Math.ceil(distance/80);
		obj.value = distance;
		$(inputFields[2]).val(time);
	}

// -->
</script>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" style="width:20%">物件番号</th>
				<td>
					<c:out value="${inputForm.housingCd}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td>
					<c:out value="${inputForm.displayHousingName}"/>&nbsp;
				</td>
			</tr>

			<dm3lookup:lookupForEach lookupName="buildingLandmark_landmarkType">
			<tr>
				<th class="head_tr"><c:out value="${value}"/></th>
				<td>
					<input type="hidden" name="landmarkType" value="<c:out value="${key}"/>">
					<c:set var="exitFlg" value="0"></c:set>
					<c:forEach items="${inputForm.landmarkType}" varStatus="status">
						<c:if test="${key == inputForm.landmarkType[status.index]}">
							<input type="text" name="landmarkName" value="<c:out value="${inputForm.landmarkName[status.index]}"/>" size="20" maxlength="50" class="input2">
							&nbsp;：距離&nbsp;&nbsp;
							<input type="text" name="distanceFromLandmark" value="<c:out value="${inputForm.distanceFromLandmark[status.index]}" />" size="6" maxlength="5" class="input2 ime-disabled" onblur="changeTime(this);">
							&nbsp;m&nbsp;&nbsp;
							&nbsp;：徒歩&nbsp;
							<input type="text" name="timeFromLandmark" value="<c:out value="${inputForm.timeFromLandmark[status.index]}"/>" size="1" maxlength="3" class="input2" readonly="readonly" >
							分
							<input type="hidden" name="wayFromLandmark" value="">
							<input type="hidden" name="sortOrder" value="">
							<c:set var="exitFlg" value="1"></c:set>
						</c:if>
					</c:forEach>

					<c:if test="${exitFlg != '1'}">
						<input type="text" name="landmarkName" value="" size="20" maxlength="50" class="input2">
						&nbsp;：距離&nbsp;&nbsp;
						<input type="text" name="distanceFromLandmark" value="" size="6" maxlength="5" class="input2 ime-disabled" onblur="changeTime(this);">
						&nbsp;m&nbsp;&nbsp;
						&nbsp;：徒歩&nbsp;
						<input type="text" name="timeFromLandmark" value="" size="1" maxlength="3" class="input2" readonly="readonly">
						分
						<input type="hidden" name="wayFromLandmark" value="">
						<input type="hidden" name="sortOrder" value="">
					</c:if>
				</td>
			</tr>
			</dm3lookup:lookupForEach>

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
