<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- ----------------------------------------------------------------
 名称： ログイン画面

 2006/12/25   j.nishimoto  新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout_nonavi.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="ログイン" />
<c:param name="contents">

	<!--headingAreaInner -->
	<div class="headingAreaInner">

		<%-- エラー時文字列出力 --%>
		<p>
			<span class="errorMessage">
				<jsp:include page="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
			</span>
		</p>
            
		<p>お手持ちのユーザーID、パスワードを入力してログインしてください。</p>
		<br><br><br><br>
		<form method="post" name="loginForm" action="<c:out value="${pageContext.request.contextPath}"/>/login/check/">
			<!--flexBlockA01 -->
			<div class="flexBlockA01" align="center">
				<table class="loginBox" >
					<tr align="left">
						<th class="head_tr">ユーザID</th>
							<td><input name="loginID" type="text" value="" size="30" maxlength="16" class="input2"></td>
					</tr>
					<tr align="left">
						<th class="head_tr">パスワード</th>
						<td><input name="password" type="password" value="" size="30" maxlength="16" class="input2"></td>
					</tr>
				</table>

				<!--flexBlock -->
				<div class="fixBlockLogin">
					<div class="flexBlockLoginInner">
						<div class="flexBlockLoginExecInner">
							<div class="btnBlockLogin">
								<div class="btnBlockLoginInner">
									<div class="btnBlockLoginInner2">
										<p><a href="#" onClick="javascript:loginForm.submit()"><span>ログイン</span></a></p>
									</div>
								</div>
							</div>													
						</div>
						<div class="flexBlockLoginRemindInner">
							<p style="float: right;"><span><a href="<c:out value="${pageContext.request.contextPath}"/>/login/remind_form.do">&gt;パスワードを忘れた時</a></span></p>
						</div>
					</div>
				</div>
				<!--/flexBlock -->
			</div>
			<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->
</c:param>
</c:import>

    
