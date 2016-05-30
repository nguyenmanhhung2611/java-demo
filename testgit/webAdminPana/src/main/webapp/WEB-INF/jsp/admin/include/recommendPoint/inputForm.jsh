<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
おすすめポイント編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
	function mailFlgDisable(allFlg) {
		if(allFlg) {
			document.inputForm.mailFlg.disabled=true;
		} else {
			document.inputForm.mailFlg.disabled=false;
		}
	}
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="userId" value="">
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>" >
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" style="width:20%;">物件番号</th>
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
		</table>
		<br>
		<table width="50%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<dm3lookup:lookupForEach lookupName="recommend_point_icon">
			<tr>
				<th class="head_tr" style="width:65%;"><c:out value="${value}" /></th>
				<c:forEach items="${inputForm.icon}" var="selectedIcon">
					<c:choose>
						<c:when test="${selectedIcon == key}">
							<c:set var="chkValue" value="true"/>
						</c:when>
					</c:choose>
				</c:forEach>
				<td><label>
					<c:choose>
						<c:when test="${chkValue == true}">
							<input type="checkBox" name="icon" value="<c:out value="${key}" />" checked/>あり
						</c:when>
						<c:otherwise>
							<input type="checkBox" name="icon" value="<c:out value="${key}" />" />あり
						</c:otherwise>
					</c:choose>
				</label></td>
			</tr>
			<c:set var="chkValue" value="false"/>
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
