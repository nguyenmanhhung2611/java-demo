<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
物件特長編集確認で使用する入力確認画面の出力
--%>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="25%">物件番号</th>
			<td><c:out value="${inputForm.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr" width="25%">物件名称</th>
			<td><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA5">
		<tr>
	    <c:forEach items="${inputForm.mstEquipCd}" var="key" varStatus="currentVarStatus">
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
				<c:when test="${chkValue == true}">あり</c:when>
				<c:otherwise>なし</c:otherwise>
			</c:choose>&nbsp;
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
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="pageId" value="housingSpecialty">
	<input type="hidden" name="command" value="">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
	<c:forEach items="${inputForm.equipCd}" var="selectedEquipCd">
		<input type="hidden" name="equipCd" value="<c:out value="${selectedEquipCd}"/>">
	</c:forEach>
	<c:forEach items="${inputForm.mstEquipCd}" var="key">
		<input type="hidden" name="mstEquipCd" value="<c:out value="${key}"/>">
	</c:forEach>
	<c:forEach items="${inputForm.equipName}" var="value">
		<input type="hidden" name="equipName" value="<c:out value="${value}"/>">
	</c:forEach>
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>

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
