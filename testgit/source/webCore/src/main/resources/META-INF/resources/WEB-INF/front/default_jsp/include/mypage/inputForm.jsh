<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
マイページ会員メンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>

<!--itemBlockA01 -->
<form action="" method="post" name="inputForm">
<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
<div class="itemBlockA01 spMb00">
	<div class="headingBlockD01 clearfix">
		<h2 class="ttl">ログイン情報</h2>
	</div><!-- /.headingBlockD01 -->
	<div class="columnInner">
		<p class="mb10"><span class="mustIcon">必須</span> 項目は必ずご入力ください。</p>
			<table class="tableBlockA01">
				<tr>
						<th><label for="mail01">メールアドレス<span class="mustIcon">必須</span></label></th>
						<td>
							<div class="errorTxt01">メールアドレスを入力してください</div>
								<div><input type="text" name="email" value="<c:out value="${inputForm.email}"/>" class="inputType04 error" id="mail01" placeholder="tci＠tci.com" 
										maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.email" defaultValue="255"/>"><span class="spDisBlock mt05">（半角英数）</span></div>
						</td>
				</tr>
				<tr>
						<th class="pb00"><label for="newPassword01">パスワード<span class="mustIcon">必須</span></label></th>
						<td class="pb00">
							<div><input type="password" class="inputType04" id="newPassword01" id="password" name="password" value="<c:out value="${inputForm.password}"/>"  maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.password" defaultValue="16"/>" placeholder=""><span class="spDisBlock mt05">（半角英数）</span></div>
							<div class="mt10">※8文字以上32文字以下で入力してください。<br>
							※英大文字と英小文字を必ず混在させてください。</div>
						</td>
				</tr>
				<tr>
						<th></th>
						<td class="pb00"><input type="password" class="inputType04" id="passwordChk" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.rePassword" defaultValue="16"/>" placeholder="">
						<div class="mt10">※確認のため、もう一度パスワードを入力してください。</div></td>
				</tr>
			</table>
		</div><!-- /.columnInner -->
	</div>
<!-- /.itemBlockA01 -->
	<div class="itemBlockA01 spMb00">
		<div class="headingBlockD01 clearfix">
			<h2 class="ttl">お客様情報</h2>
		</div><!-- /.headingBlockD01 -->
		<div class="columnInner">
			<table class="tableBlockA01">
				<tr>
					<th><label for="name01">お名前<span class="mustIcon">必須</span></label></th>
					<td>
						<div>姓 <input type="text" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>" class="inputType05" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="40"/>" placeholder=""> 
						名 <input type="text" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="40"/>" class="inputType05" placeholder=""></div>
					</td>
				</tr>
				<tr>
					<th><label for="name02">お名前（フリガナ）<span class="mustIcon">必須</span></label></th>
					<td>
						<div>姓 <input type="text" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberLname" defaultValue="40"/>" class="inputType05" placeholder="ヤマダ"> 
						名 <input type="text" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.input.memberFname" defaultValue="40"/>" class="inputType05" placeholder="タロウ"></div>
					</td>
				</tr>
			</table>
		</div><!-- /.columnInner -->
	</div><!-- /.itemBlockA01 -->

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
