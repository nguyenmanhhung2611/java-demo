<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： パスワード変更入力画面

 2015/04/20		チョ夢		新規作成
 2015/08/17     Vinh.Ly Add CSRF Token for Change Password
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="パスワード編集画面" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>パスワード編集</h2>
	</div>

	<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<c:if test="${sessionScope['passwordExpireFailed']}" >
	<p>パスワードの有効期限が切れています。　パスワードを変更してください。</p>
</c:if>
	<form method="post" name="inputForm" action="../change/">
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="25%">最終更新日時</th>
					<td><c:out value=""/><fmt:formatDate pattern="yyyy/MM/dd"
					value="${pwdChangeDate}"/>　<fmt:formatDate pattern="HH:mm:ss"
					value="${pwdChangeDate}"/>&nbsp;</td>
				</tr>
				<tr>
					<th class="head_tr">現在のパスワード&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="oldPassword" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.oldPassword" defaultValue="16"/>" class="input2" value="<c:out value="${inputform.oldPassword}"/>">&nbsp;</td>
				</tr>
				<tr>
					<th class="head_tr">新パスワード&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="newPassword" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.newPassword" defaultValue="16"/>" class="input2" value="<c:out value="${inputform.newPassword}"/>"></td>
				</tr>
				<tr>
					<th class="head_tr">新パスワード確認&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="newRePassword" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.newRePassword" defaultValue="16"/>" class="input2" value="<c:out value="${inputform.newRePassword}"/>"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->
		<dm3token:oneTimeToken/>
	</form>
</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>
						<a href="javascript:document.inputForm.submit();"><span>登&nbsp;&nbsp;録</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->

</c:param>
</c:import>
