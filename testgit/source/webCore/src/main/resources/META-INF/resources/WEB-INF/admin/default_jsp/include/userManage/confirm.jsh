<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
管理ユーザーメンテナンス機能で使用する入力確認画面の出力 
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">権限</th>
			<td><dm3lookup:lookup lookupName="roleId" lookupKey="${inputForm.roleId}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ログインID</th>
			<td><c:out value="${inputForm.loginId}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">パスワード</th>
			<td><c:out value="******"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ユーザー名</th>
			<td><c:out value="${inputForm.userName}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">メールアドレス</th>
			<td><c:out value="${inputForm.email}"/>&nbsp;</td>
		</tr>
<c:if test="${param.input_mode == 'update'}">
			<tr>
				<th class="head_tr">ロック状態</th>
				<c:if test="${lockStatus}"><td>ロック中</td></c:if>
				<c:if test="${!lockStatus}"><td>通常</td></c:if>
			</tr>
</c:if>
		<tr>
			<th class="head_tr">備考</th>
			<td>${dm3functions:crToHtmlTag(inputForm.note)}
				&nbsp;
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/userManage/searchParams.jsh" />
	<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
	<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>">
	<input type="hidden" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>">
	<input type="hidden" name="roleId" value="<c:out value="${inputForm.roleId}"/>">
	<input type="hidden" name="loginId" value="<c:out value="${inputForm.loginId}"/>">
	<input type="hidden" name="userName" value="<c:out value="${inputForm.userName}"/>">
	<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
	<input type="hidden" name="note" value="<c:out value="${inputForm.note}"/>">
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
