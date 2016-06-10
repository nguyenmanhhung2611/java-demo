<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
物件ステータス変更確認画面で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="18%"/>
			<col width="82%"/>
		</colgroup>
		<tr>
			<th class="head_tr">物件番号</th>
			<td><c:out value="${inputForm.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
		</tr>
	</table>
	<br />
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="18%"/>
			<col width="32%"/>
			<col width="18%"/>
			<col width="32%"/>
		</colgroup>
		<c:if test="${inputForm.command == 'insert'}">
			<tr>
				<th class="head_tr">物件種別※</th>
				<td colspan="3">
	                <dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="${inputForm.housingKindCd}"/>&nbsp;
				</td>
			</tr>
		</c:if>

		<tr>
			<th class="head_tr">公開区分※</th>
			<td colspan="3">
                <dm3lookup:lookup lookupName="hiddenFlg" lookupKey="${inputForm.hiddenFlg}"/>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">ステータス※</th>
			<td colspan="3">
                <dm3lookup:lookup lookupName="statusCd" lookupKey="${inputForm.statusCd}"/>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">会員番号</th>
			<td><c:out value="${inputForm.userId}"/>&nbsp;</td>
			<th class="head_tr">氏名</th>
			<td><c:out value="${inputForm.memberName}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">備考</th>
			<td colspan="3"><c:out value="${inputForm.showNote}" escapeXml="false"/>&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
<input type="hidden" name="hiddenFlg" value="<c:out value="${inputForm.hiddenFlg}"/>">
<input type="hidden" name="statusCd" value="<c:out value="${inputForm.statusCd}"/>">
<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
<input type="hidden" name="memberName" value="<c:out value="${inputForm.memberName}"/>">
<input type="hidden" name="tel" value="<c:out value="${inputForm.tel}"/>">
<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
<input type="hidden" name="note" value="<c:out value="${inputForm.note}"/>">
<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
<input type="hidden" name="readonlyFlg" value="<c:out value="${inputForm.readonlyFlg}"/>">
<dm3token:oneTimeToken/>
<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function back() {
		var frm = document.forms[0];
		var cmd = document.forms[0].command.value;
		var sysHousingCd = '<c:out value="${inputForm.sysHousingCd}"/>';

		if (cmd == 'insert') {
			frm.command.value = 'iBack';
			frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/";
		} else {
			frm.command.value = 'uBack';
			frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/" + sysHousingCd  + "/";
		}
		frm.submit();
	}
// -->
</script>
