<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- ----------------------------------------------------------------
 名称： ログイン画面 (ADM-COM-010Login)

 2015/03/09   荊学ギョク  新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout_nonavi.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="ログイン" />
<c:param name="contents">

	<!--headingAreaInner -->
	<div class="headingAreaInner">

		<%-- エラー時文字列出力 --%>
		<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

		<p>お手持ちのユーザーID、パスワードを入力してログインしてください。</p>
		<br><br><br><br>
		<form method="post" name="loginForm" action="<c:out value="${pageContext.request.contextPath}"/>/login/check/">
			<c:if test="${!empty redirectURL}">
			    <input type="hidden" name="redirectURL" value="<c:out value="${redirectURL}"/>"/>
			</c:if>
			<c:if test="${empty redirectURL}">
			    <input type="hidden" name="redirectURL" value="<c:out value="${pageContext.request.contextPath}"/>/top/"/>
			</c:if>

			<!--flexBlockA01 -->
			<div class="flexBlockA01" align="center">
				<table width="370px" border="1" cellspacing="0" cellpadding="0" class="tableA1" >
					<tr align="left">
						<th class="head_tr" width="30%">ユーザID</th>
						<td><input name="loginID" type="text" value="<c:out value="${loginForm.loginID}"/>" size="16" maxlength="16" class="input2 ime-disabled"></td>
					</tr>
					<tr align="left">
						<th class="head_tr">パスワード</th>
						<td><input name="password" type="password" value="" size="16" maxlength="16" class="input2"></td>
					</tr>
				</table>
			</div>
			<!--/flexBlockA01 -->

			<table width="800px" align="center">
				<tr width="100%">
				    <div class="flexBlockB06">
				        <div class="btnBlockC14">
				            <div class="btnBlockC14Inner">
				                <div class="btnBlockC14Inner2">
				                    <p><a href="#" onClick="javascript:loginForm.submit()"><span>ログイン</span></a></p>
				                </div>
				            </div>
				        </div>
				    </div>
				</tr>
			</table>

		</form>
	</div>
	<!--/headingAreaInner -->

</c:param>
</c:import>

<script>
document.body.style.background="#ffffff";
</script>