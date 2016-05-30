<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
管理ユーザーメンテナンス機能で使用する情報確認画面（詳細閲覧、削除確認）の出力 
--%>

<!--flexBlockA01 -->
<c:set var="userInfo" value="${adminUser.items['adminLoginInfo']}" />
<c:set var="roleInfo" value="${adminUser.items['adminRoleInfo']}" />
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">権限</th>
			<td><dm3lookup:lookup lookupName="roleId" lookupKey="${roleInfo.roleId}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ログインID</th>
			<td><c:out value="${userInfo.loginId}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">パスワード</th>
			<td><c:out value="******"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ユーザー名</th>
			<td><c:out value="${userInfo.userName}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">メールアドレス</th>
			<td><c:out value="${userInfo.email}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ロック状態</th>
			<c:if test="${lockStatus}"><td>ロック中&nbsp;<c:if test="${param.input_mode == 'view'}"><input type="button" value="ロック解除" onclick="javascript:linkToUrl('../update/unlock/', '');"></c:if></td></c:if>
			<c:if test="${!lockStatus}"><td>通常</td></c:if>
		</tr>
		<tr>
			<th class="head_tr">備考</th>
			<td>${dm3functions:crToHtmlTag(userInfo.note)}
				&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">最終更新日</th>
			<td><fmt:formatDate value="${userInfo.updDate}" pattern="yyyy年MM月dd日" />&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- 管理ユーザ入力formパラメータ引き継ぎ --%>
<form method="post" name="userselect" action="../list/">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="userId" value="<c:out value="${userInfo.userId}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/userManage/searchParams.jsh" />
	<c:if test="${param.input_mode == 'delete'}">
		<dm3token:oneTimeToken/>
	</c:if>
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.userselect.action = url;
		document.userselect.command.value = cmd;
		document.userselect.submit();
	}
// -->
</script>