<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
マイページ会員メンテナンス機能で使用する入力確認画面の出力 
--%>

<!--itemBlockA01 -->
<div class="contentsInner01">
	<p class="f14">登録内容をご確認ください。</p>
</div>
<div class="itemBlockA01 spMb00">
		<div class="headingBlockD01 clearfix">
				<h2 class="ttl">ログイン情報</h2>
		</div><!-- /.headingBlockD01 -->
		<div class="columnInner">
		<table class="tableBlockA01">
					<tr>
				<th>お名前</th>
				<td><c:out value="${inputForm.memberLname}"/>&nbsp;&nbsp;<c:out value="${inputForm.memberFname}"/>&nbsp;</td>
			</tr>
				<tr>
						<th>メールアドレス</th>
						<td><c:out value="${inputForm.email}"/>&nbsp;</td>
				</tr>
				<tr>
						<th>パスワード</th>
						<td>●●●●●●●●●●●●</td>
				</tr>
		</table>
			</div><!-- /.columnInner -->
		</div><!-- /.itemBlockA01 -->
		<div class="itemBlockA01 spMb00">
		<div class="headingBlockD01 clearfix">
			<h2 class="ttl">お客様情報</h2>
		</div><!-- /.headingBlockD01 -->
		<div class="columnInner">
			<table class="tableBlockA01">
				<tr>
					<th>お名前</th>
					<td><c:out value="${inputForm.memberLname}"/>&nbsp;&nbsp;<c:out value="${inputForm.memberFname}"/>&nbsp;</td>
				</tr>
				<tr>
					<th>お名前（フリガナ）</th>
					<td><c:out value="${inputForm.memberLnameKana}"/>&nbsp;&nbsp;<c:out value="${inputForm.memberFnameKana}"/>&nbsp;</td>
				</tr>
			</table>
		</div><!-- /.columnInner -->
	</div><!-- /.itemBlockA01 -->

<!--/itemBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
	<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>">
	<input type="hidden" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>">
	<input type="hidden" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>">
	<input type="hidden" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>">
	<input type="hidden" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>">
	<input type="hidden" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>">
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
