<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
管理ユーザ情報編集確認画面で使用する入力確認画面の出力 
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="25%">ログインID</th>
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
		<tr>
			<th class="head_tr">権限</th>
			<td><dm3lookup:lookup lookupName="radio_roleId" lookupKey="${inputForm.roleId}"/>&nbsp;</td>
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
