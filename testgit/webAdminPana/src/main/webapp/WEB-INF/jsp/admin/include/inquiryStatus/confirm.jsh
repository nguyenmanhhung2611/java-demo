<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
お問合せ情報編集画面で使用する入力確認画面の出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr">問合番号</th>
			<td colspan="3"><c:out value="${inputForm.inquiryId}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">問合種別</th>
			<td colspan="3">
				<dm3lookup:lookupForEach lookupName="inquiry_type">
					<c:if test="${inputForm.inquiryType == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td colspan="3"><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ステータス&nbsp;&nbsp;<font color="red">※</font></th>
			<td colspan="3">
				<option></option>
				<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
					<c:if test="${inputForm.answerStatus == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">備考</th>
			<td colspan="3"><c:out value="${inputForm.showAnswerText}" escapeXml="false"/>&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/inquiryList/searchParams.jsh" />
	<input type="hidden" name="inquiryId" value="<c:out value="${inputForm.inquiryId}"/>">
	<input type="hidden" name="inquiryType" value="<c:out value="${inputForm.inquiryType}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
	<input type="hidden" name="answerStatus" value="<c:out value="${inputForm.answerStatus}"/>">
	<input type="hidden" name="answerText" value="<c:out value="${inputForm.answerText}"/>">
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function back(url, cmd) {
		document.inputForm.action = url + "?inquiryId=" + document.inputForm.inquiryId.value;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>