<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
管理ユーザ情報編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/userManage/searchParams.jsh" />

		<p style="color:#ff0000;">※印は入力必須項目です。</p>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="25%">ログインID&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="loginId" value="<c:out value="${inputForm.loginId}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.loginId" defaultValue="16"/>" class="input2 ime-disabled"></td>
			</tr>
			<tr>
				<th class="head_tr">パスワード<c:if test="${param.input_mode == 'insert'}">&nbsp;&nbsp;<font color="red">※</font></c:if></th>
				<td>
					<div class="inputStyle">
						<input type="password" id="password" name="password" value="<c:out value="${inputForm.password}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.password" defaultValue="16"/>" class="input2">
						&nbsp;&nbsp;
					</div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="javascript:mkPwd();"><span>ランダム生成<span></a></p>
							</div>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th class="head_tr">パスワード確認<c:if test="${param.input_mode == 'insert'}">&nbsp;&nbsp;<font color="red">※</font></c:if></th>
				<td><input type="password" id="passwordChk" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.rePassword" defaultValue="16"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">氏名&nbsp;&nbsp;<font color="red">※</font></th>
				<td><input type="text" name="userName" value="<c:out value="${inputForm.userName}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.adminUserName" defaultValue="40"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">メールアドレス&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<input type="text" name="email" value="<c:out value="${inputForm.email}"/>" size="35" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.input.email" defaultValue="255"/>" class="input2 ime-disabled"><br/>
				</td>
			</tr>
			<tr>
				<th class="head_tr">権限</th>
				<td>
<dm3lookup:lookupForEach lookupName="radio_roleId">
					<label>
						<input type="radio" name="roleId" value="<c:out value="${key}"/>" <c:if test="${inputForm.roleId == key || param.default_roleId == key}">checked</c:if>><c:out value="${value}"/>
					</label>&nbsp;&nbsp;&nbsp;&nbsp;
</dm3lookup:lookupForEach>
				</td>
			</tr>
<c:if test="${param.input_mode == 'update'}">
			<tr>
				<th class="head_tr">アカウントロック状態</th>
				<c:if test="${lockStatus}">
					<td>
						<div style="width: 50px; padding-top: 6px; float: left; padding-right: 10px;">ロック中</div>
						<div class="btnBlockC13">
							<div class="btnBlockC13Inner">
								<div class="btnBlockC13Inner2">
									<p><a class="tblNormalBtn" href="javascript:linkToUrl('../../unlock/', '');"><span>ロック解除</span></a></p>
								</div>
							</div>
						</div>
					</td>
				</c:if>
				<c:if test="${!lockStatus}"><td>通常</td></c:if>
			</tr>
</c:if>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">
<!--
	function mkPwd() {
		$.getJSON("../../mkpwd/", function(data, status){
			$("#password").val(data.password);
			$("#passwordChk").val(data.password);
		});
	}

	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
