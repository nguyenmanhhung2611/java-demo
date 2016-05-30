<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
マイページ会員メンテナンス機能で使用する入力確認画面の出力 
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">メールアドレス</th>
			<td colspan="3"><c:out value="${inputForm.email}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">パスワード</th>
			<td colspan="3"><c:out value="******"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">お名前（姓）</th>
			<td><c:out value="${inputForm.memberLname}"/>&nbsp;</td>
			<th class="head_tr">お名前（名）</th>
			<td><c:out value="${inputForm.memberFname}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">フリガナ（セイ）</th>
			<td><c:out value="${inputForm.memberLnameKana}"/>&nbsp;</td>
			<th class="head_tr">フリガナ（メイ）</th>
			<td><c:out value="${inputForm.memberFnameKana}"/>&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/mypage/searchParams.jsh" />
	<input type="hidden" name="userId" value="<c:out value="${searchForm.userId}"/>">
	<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
	<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>">
	<input type="hidden" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>">
	<input type="hidden" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>">
	<input type="hidden" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>">
	<input type="hidden" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>">
	<input type="hidden" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>">
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
