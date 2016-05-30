<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
リフォーム情報編集機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<c:set var="inquiryHeader" value="${inquiryHeaderInfo.inquiryHeader}"/>
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="inquiryId" value="<c:out value="${inputForm.inquiryId}"/>">
		<input type="hidden" name="inquiryType" value="<c:out value="${inquiryHeader.inquiryType}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/searchParams.jsh" />

		<table class="inputBox">
			<tr>
				<th class="head_tr">問合番号</th>
				<td colspan="3">
					<c:out value="${inputForm.inquiryId}"/>
				</td>
			</tr>
			<tr>
				<th class="head_tr">問合種別</th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="inquiry_type">
						<c:if test="${inquiryHeader.inquiryType == key}">
							<c:out value="${value}"/>
						</c:if>
					</dm3lookup:lookupForEach>
				</td>
			</tr>
			<tr>
				<th class="head_tr">ステータス&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<select name="answerStatus">
						<option></option>
						<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
							<option value="<c:out value="${key}"/>" <c:if test="${inputForm.answerStatus == key}">selected</c:if>><c:out value="${value}"/></option>&nbsp;
						</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">備考</th>
				<td colspan="3"><textarea cols="50" rows="3" name="answerText"><c:out value="${inputForm.answerText}"/></textarea></td>
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
