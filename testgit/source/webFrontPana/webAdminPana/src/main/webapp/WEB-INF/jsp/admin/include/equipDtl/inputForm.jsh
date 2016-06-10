<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%--
管理者用設備情報編集画面で使用する入力フォームの出力
--%>
<p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
</p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="" />
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>" />
		<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>" />
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}" />" />

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
					<th class="head_tr" width="30%">設備項目<p style="font-size: 85%">（30文字）</p></th>
					<th class="head_tr" width="50%">設備<p style="font-size: 85%">（30文字）</p></th>
					<th class="head_tr" width="15%">リフォーム</th>
				</tr>

				<c:forEach var="varKeyCd" items="${inputForm.keyCd}" >
					<tr>
						<td><input type="text" name="equipCategory" value="<c:out value="${inputForm.equipCategory[varKeyCd-1]}"/>" size="15" maxlength="30" class="input2" /></td>
						<td><input type="text" name="equip" value="<c:out value="${inputForm.equip[varKeyCd-1]}"/>" size="25" maxlength="30" class="input2" /></td>
						<td><input type="checkBox" name="reformCheckBox" <c:if test="${inputForm.reform[varKeyCd-1] == '1'}"> checked="true"</c:if> onClick="setReform(<c:out value="${varKeyCd-1}"/>);" />あり</td>
					</tr>
					<input type="hidden" name="reform" value="<c:out value="${inputForm.reform[varKeyCd-1]}"/>" />
					<input type="hidden" name="keyCd" value="<c:out value="${inputForm.keyCd[varKeyCd-1]}"/>" />
				</c:forEach>

			</table>
		</div>
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	</form>
</div>
<!--/flexBlockA01 -->

<script type="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
	function setReform(varKeyCd) {
		if (document.getElementsByName("reformCheckBox")[varKeyCd].checked == true) {
			document.getElementsByName("reform")[varKeyCd].value = "1";
		} else {
			document.getElementsByName("reform")[varKeyCd].value = "0";
		}
	}
// -->
</script>
