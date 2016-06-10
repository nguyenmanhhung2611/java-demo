<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- お問合せメンテナンス機能で使用する入力確認画面の出力 --%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table class="confirmBox">
	<c:set var="inquiryHeader" value="${inquiryHeaderInfo.inquiryHeader}"/>
		<tr>
			<th class="head_tr">問合番号</th>
			<td><c:out value="${inquiryHeader.inquiryId}"/></td>
		</tr>
		<tr>
			<th class="head_tr">問合種別</th>
			<td>
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
				<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
					<c:if test="${inquiryHeader.answerStatus == key}">
						<c:out value="${value}"/>
					</c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">備考</th>
			<td>
				<c:out value="${inquiryHeader.answerText}"/>
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- お問合せ入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" action="./list/">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="inquiryId" value="<c:out value="${inputForm.inquiryId}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/searchParams.jsh" />
</form>

<script type ="text/JavaScript">
<!--
// -->
</script>
