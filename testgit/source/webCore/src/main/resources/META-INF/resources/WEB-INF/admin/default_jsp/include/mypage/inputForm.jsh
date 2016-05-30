<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
マイページ会員メンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="userId" value="<c:out value="${searchForm.userId}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/mypage/searchParams.jsh" />

		<p style="color:#ff0000;">※印は入力必須項目です。</p>
		<table class="inputBox">
			<tr>
				<th class="head_tr">メールアドレス&nbsp;&nbsp;<font color="red">※</th>
				<td colspan="3">
					<input type="text" name="email" value="<c:out value="${inputForm.email}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.email" defaultValue="255"/>" class="input2"><br/>
					※半角英数字のみ
				</td>
			</tr>
			<tr>
				<th class="head_tr">パスワード<c:if test="${param.input_mode == 'insert'}">&nbsp;&nbsp;<font color="red">※</font></c:if></th>
				<td colspan="3">
					<div style="float:left;">
						<input type="password" id="password" name="password" value="<c:out value="${inputForm.password}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.password" defaultValue="16"/>" class="input2">
						&nbsp;&nbsp;
					</div>
					<div class="btnBlockRand" style="float:left;">
						<div class="btnBlockRandInner">
							<div class="btnBlockRandInner2">
								<p id="mkPwd"><a href="javascript:return null;"><span>ランダム生成</span></a></p>
							</div>
						</div>
					</div>
<c:if test="${param.input_mode == 'update'}">
					<br><br><div>パスワードを変更する場合に入力してください。</div>
</c:if>
				</td>
			</tr>
			<tr>
				<th class="head_tr">パスワード確認<c:if test="${param.input_mode == 'insert'}">&nbsp;&nbsp;<font color="red">※</font></c:if></th>
				<td colspan="3"><input type="password" id="passwordChk" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.rePassword" defaultValue="16"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">お名前（姓）&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>" size="15" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="40"/>" class="input2"></td>
				<th class="head_tr">お名前（名）&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>" size="15" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="40"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">フリガナ（セイ）&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>" size="15" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="40"/>" class="input2"></td>
				<th class="head_tr">フリガナ（メイ）&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>" size="15" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="40"/>" class="input2"></td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">
<!--
	$(document).ready( function() {
		$("#mkPwd").click(function(){
			$.getJSON("../../mkpwd/", function(data, status){
				$("#password").val(data.password);
				$("#passwordChk").val(data.password);
			});
		});
	});

	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
