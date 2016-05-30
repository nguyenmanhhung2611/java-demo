<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
物件特長編集画面で使用する入力フォームの出力
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
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="25%">物件番号</th>
				<td>
					<c:out value="${inputForm.housingCd}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="25%">物件名称</th>
				<td>
					<c:out value="${inputForm.displayHousingName}"/>&nbsp;
				</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA5">
			<tr>
		    <c:forEach items="${inputForm.mstEquipCd}" var="key" varStatus="currentVarStatus">
		    <input type="hidden" name="mstEquipCd" value="<c:out value="${inputForm.mstEquipCd[currentVarStatus.index]}"/>">
		    <input type="hidden" name="equipName" value="<c:out value="${inputForm.equipName[currentVarStatus.index]}"/>">
				<th class="head_tr" width="25%"><c:out value="${inputForm.equipName[currentVarStatus.index]}" /></th>
				<c:forEach items="${inputForm.equipCd}" var="selectedEquip">
					<c:choose>
						<c:when test="${selectedEquip == key}">
							<c:set var="chkValue" value="true"/>
						</c:when>
					</c:choose>
				</c:forEach>
				<td width="25%">
					<c:choose>
						<c:when test="${chkValue == true}">
						<label>
							<input type="checkBox" name="equipCd" value="<c:out value="${inputForm.mstEquipCd[currentVarStatus.index]}" />" checked/>あり
						</label>
						</c:when>
						<c:otherwise>
						<label>
							<input type="checkBox" name="equipCd" value="<c:out value="${inputForm.mstEquipCd[currentVarStatus.index]}" />" />あり
						</label>
						</c:otherwise>
					</c:choose>
				</td>
		    <c:if test="${currentVarStatus.index%2==1&&!currentVarStatus.last}">
		    </tr>
		    <tr>
		    </c:if>
		    <c:if test="${currentVarStatus.last}">
			    <c:if test="${currentVarStatus.index%2==0}">
			    	<td colspan="2">&nbsp;</td>
			    </c:if>
		    	</tr>
		    </c:if>
			<c:set var="chkValue" value="false"/>
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
