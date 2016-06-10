<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
お問合せステータス編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="inquiryId" value="<c:out value="${inputForm.inquiryId}"/>">
		<input type="hidden" name="inquiryType" value="<c:out value="${inputForm.inquiryType}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/inquiryList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" style="width:20%;">問合番号</th>
				<td><c:out value="${inputForm.inquiryId}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">問合種別</th>
				<td>
				<dm3lookup:lookupForEach lookupName="inquiry_type">
					<c:if test="${inputForm.inquiryType == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">ステータス&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<select name="answerStatus" size="1"  value="<c:out value="${inputForm.answerStatus}"/>">
						<option></option>
						<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
							<option value="<c:out value="${key}"/>" <c:if test="${inputForm.answerStatus == key}"> selected</c:if>><c:out value="${value}"/></option>
                    	</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">備考</th>
				<td><textarea cols="45" rows="3" name="answerText"><c:out value="${inputForm.answerText}"/></textarea></td>
			</tr>
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
