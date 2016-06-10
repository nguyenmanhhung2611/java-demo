<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： パスワード変更入力画面

 2015/02/23		H.Mizuno	Shamaison 管理サイトを参考に新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="パスワード変更" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>パスワード変更</h2>
	</div>

	<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<c:if test="${sessionScope['passwordExpireFailed']}" >
	<p>パスワードの有効期限が切れています。　パスワードを変更してください。</p>
</c:if>
	<form method="post" name="userPassUpdate" action="../change/">
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="inputBox">
				<tr>
					<th class="head_tr">最終更新日時</th>
					<td><c:out value=""/><fmt:formatDate pattern="yyyy/M/d"
					value="${pwdChangeDate}"/>　<fmt:formatDate pattern="H:m:s"
					value="${pwdChangeDate}"/>&nbsp;</td>
				</tr>
				<tr>
					<th class="head_tr">現在のパスワード&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="oldPassword" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.oldPassword" defaultValue="16"/>" class="input2">&nbsp;</td>
				</tr>
				<tr>
					<th class="head_tr">新パスワード&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="newPassword" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.newPassword" defaultValue="16"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">新パスワード確認&nbsp;&nbsp;<font color="red">※</font></th>
					<td><input type="password" name="newRePassword" value="" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.pwd.newRePassword" defaultValue="16"/>" class="input2"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->
		<dm3token:oneTimeToken/>
	</form>
</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockInput -->
<div class="flexBlockDtl">
	<div class="flexBlockDtlInner">
		<div class="btnBlockComp">
			<div class="btnBlockCompInner">
				<div class="btnBlockCompInner2">
					<p><a href="javascript:document.userPassUpdate.submit();"><span>変更</span></a></p>
				</div>
			</div>
		</div>
	</div>
</div>
<!--/flexBlockInput -->

</c:param>
</c:import>
