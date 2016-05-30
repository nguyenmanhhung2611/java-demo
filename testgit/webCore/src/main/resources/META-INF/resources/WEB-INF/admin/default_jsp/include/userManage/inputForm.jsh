<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
管理ユーザーメンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/userManage/searchParams.jsh" />

		<p style="color:#ff0000;">※印は入力必須項目です。</p>
		<table class="inputBox">
			<tr>
				<th class="head_tr">権限&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<select name="roleId">
<dm3lookup:lookupForEach lookupName="roleId">
						<option value="<c:out value="${key}"/>" <c:if test="${inputForm.roleId == key}"> selected</c:if>><c:out value="${value}"/></option>
</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">ログインID&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="loginId" value="<c:out value="${inputForm.loginId}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.loginId" defaultValue="16"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">パスワード<c:if test="${param.input_mode == 'insert'}">&nbsp;&nbsp;<font color="red">※</font></c:if></th>
				<td>
				    <div style="float:left;"> 
						<input type="password" id="password" name="password" value="<c:out value="${inputForm.password}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.password" defaultValue="16"/>" class="input2">
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
				<td><input type="password" id="passwordChk" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.rePassword" defaultValue="16"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">ユーザー名&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="userName" value="<c:out value="${inputForm.userName}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.adminUserName" defaultValue="40"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">メールアドレス&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<input type="text" name="email" value="<c:out value="${inputForm.email}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.email" defaultValue="255"/>" class="input2"><br/>
				</td>
			</tr>
<c:if test="${param.input_mode == 'update'}">
			<tr>
				<th class="head_tr">ロック状態</th>
				<c:if test="${lockStatus}"><td>ロック中&nbsp;<input type="button" value="ロック解除" onclick="javascript:linkToUrl('../unlock/', '');"></td></c:if>
				<c:if test="${!lockStatus}"><td>通常</td></c:if>
			</tr>
</c:if>
			<tr>
				<th class="head_tr">備考</th>
				<td><textarea cols="40" rows="3" name="note"><c:out value="${inputForm.note}"/></textarea></td>
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
