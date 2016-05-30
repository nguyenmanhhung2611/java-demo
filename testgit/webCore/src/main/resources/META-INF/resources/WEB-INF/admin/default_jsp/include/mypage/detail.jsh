<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
マイページ会員メンテナンス機能で使用する情報確認画面（詳細閲覧、削除確認）の出力 
--%>

<!--flexBlockA01 -->
<c:set var="memberInfo" value="${mypageUser.items['memberInfo']}"/>
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">メールアドレス</th>
			<td colspan="3"><c:out value="${memberInfo.email}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">パスワード</th>
			<td colspan="3"><c:out value="******"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">お名前（姓）</th>
			<td><c:out value="${memberInfo.memberLname}"/>&nbsp;</td>
			<th class="head_tr">お名前（名）</th>
			<td><c:out value="${memberInfo.memberFname}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">フリガナ（セイ）</th>
			<td><c:out value="${memberInfo.memberLnameKana}"/>&nbsp;</td>
			<th class="head_tr">フリガナ（メイ）</th>
			<td><c:out value="${memberInfo.memberFnameKana}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">最終更新日</th>
			<td colspan="3"><fmt:formatDate value="${memberInfo.updDate}" pattern="yyyy年MM月dd日" />&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- マイページ会員入力formパラメータ引き継ぎ --%>
<form method="post" name="userselect" action="../list/">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="userId" value="<c:out value="${memberInfo.userId}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/mypage/searchParams.jsh" />
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