<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
管理者用設備情報編集確認画面で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
			<td><c:out value="${inputForm.housingCd}" />&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">物件名称</th>
			<td><c:out value="${inputForm.displayHousingName}" />&nbsp;
			</td>
		</tr>
	</table>
	<br>
	<div style="height: 510px; overflow-y: scroll;">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="30%">設備項目</th>
				<th class="head_tr" width="50%">設備</th>
				<th class="head_tr" width="15%">リフォーム</th>
			</tr>
			<c:forEach var="varKeyCd" items="${inputForm.keyCd}">
				<tr>
					<td><c:out value="${inputForm.equipCategory[varKeyCd-1]}" />&nbsp;</td>
					<td><c:out value="${inputForm.equip[varKeyCd-1]}" />&nbsp;</td>
					<td>
						<c:if test="${inputForm.equipCategory[varKeyCd-1] != ''}">
							<c:choose>
								<c:when test="${inputForm.reform[varKeyCd-1] == '1'}">
									あり
								</c:when>
								<c:otherwise>
									なし
								</c:otherwise>
							</c:choose>
						</c:if>
						&nbsp;
					</td>
				</tr>
			</c:forEach>
			<form method="post" name="inputForm">
				<input type="hidden" name="command" value="" />
				<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
				<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>" />
				<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>" />
				<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}" />" />
				<c:forEach var="varKeyCd" items="${inputForm.keyCd}">
					<input type="hidden" name="equipCategory" value="<c:out value="${inputForm.equipCategory[varKeyCd-1]}"/>" />
					<input type="hidden" name="equip" value="<c:out value="${inputForm.equip[varKeyCd-1]}"/>" />
					<input type="hidden" name="reform" value="<c:out value="${inputForm.reform[varKeyCd-1]}"/>" />
					<input type="hidden" name="keyCd" value="<c:out value="${inputForm.keyCd[varKeyCd-1]}"/>" />
				</c:forEach>
				<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
				<dm3token:oneTimeToken/>
			</form>

		</table>
	</div>
</div>
<!--/flexBlockA01 -->

<script type="text/JavaScript">
<!--
function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}
// -->
</script>
