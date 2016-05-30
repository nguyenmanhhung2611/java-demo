<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： 管理者ユーザー一覧画面

 2015/01/28		H.Mizuno	Shamaison 管理サイトを参考に新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="管理ユーザーメンテナンス" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
	function search(url){
		document.userform.action = url;
		document.userform.submit();
	}
	function newlist(url){
   		document.inputdate.action = url;
   		document.inputdate.submit();
	}
	function linkTo(url, id) {
		document.inputdate.action = url;
		document.inputdate.userId.value = id;
		document.inputdate.submit();
	}
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>管理ユーザーの検索</h2>
		</div>
		<form action="./" method="post" name="userform">
	    <input type="hidden" name="command" value="list"/>
		<input type="hidden" name="userId" value="">

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="searchBox">
				<tr>
					<th class="head_tr">ログインID</th>
					<td><input name="keyLoginId" value="<c:out value="${searchForm.keyLoginId}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyLoginId" defaultValue="16"/>" class="input2"></td>
					<th class="head_tr">ユーザー名</th>
					<td><input name="keyUserName"  value="<c:out value="${searchForm.keyUserName}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyUserName" defaultValue="20"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">メールアドレス</th>
					<td><input name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyEmail" defaultValue="256"/>" class="input2"></td>
					<th class="head_tr">権限</th>
					<td><select name="keyRoleId">
	<dm3lookup:lookupForEach lookupName="roleId">
						<option value="<c:out value="${key}"/>" <c:if test="${searchForm.keyRoleId == key}"> selected</c:if>><c:out value="${value}"/></option>
	</dm3lookup:lookupForEach>
					</select></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!-- flexBlockList -->
	<div class="flexBlockList">
		<div class="flexBlockListInner">
			<div class="btnBlockCsv">
				<div class="btnBlockCsvInner">
					<div class="btnBlockCsvInner2">
						<p><a href="javascript:search('../csv/');"><span>CSV 出力</span></a></p>
					</div>
				</div>
			</div>
		</div>

		<div class="flexBlockListInner">
			<div class="btnBlockSearch">
				<div class="btnBlockSearchInner">
					<div class="btnBlockSearchInner2">
						<p><a href="javascript:search('./');"><span>検索</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockList -->

	<p>　検索結果はページ下部の管理ユーザー一覧に表示されます</p><br>
	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${command == 'list'}">
		<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果がありません。</span></c:if>
	</c:if>
	</p></div><br>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>管理ユーザー新規登録</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<p>管理ユーザーを新規に登録する場合はこちら</p>
		</div>
		<!--/flexBlockA01 -->

	</div>
	<!--/headingAreaInner -->

	<!-- flexBlockList -->
	<div class="flexBlockList">
		<div class="flexBlockListInner">
			<div class="btnBlockAdd">
				<div class="btnBlockAddInner">
					<div class="btnBlockAddInner2">
						<p><a href="javascript:newlist('../new/input/');"><span>新規登録</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockList -->

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>管理ユーザー一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table>
				<tr>
					<td>
		<c:set var="strBefore" value="javascript:KeyForm('" scope="request"/>
		<c:set var="strAfter" value="')" scope="request"/>
		<c:set var="pagingForm" value="${searchForm}" scope="request"/>
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingCnt.jsh" />
						&nbsp;&nbsp;&nbsp;
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingjs.jsh" />
					</td>
				</tr>
			</table>
			<table class="searchListBox">
				<tr class="head_tr">
					<td width="95">権限</td>
					<td width="150">ユーザー名</td>
					<td width="125">ログインID</td>
					<td width="230">メールアドレス</td>
					<td width="70">最終<br/>ログイン日</td>
					<td width="100">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="adminUser">
			<c:set var="userInfo" value="${adminUser.items['adminLoginInfo']}"/>
			<c:set var="roleInfo" value="${adminUser.items['adminRoleInfo']}"/>
				<tr>
					<td><dm3lookup:lookup lookupName="roleId" lookupKey="${roleInfo.roleId}"/></td>
					<td><a href="javascript:linkTo('../detail/','<c:out value="${userInfo.userId}"/>')"><c:out value="${userInfo.userName}"/></a>&nbsp;</td>
					<td><c:out value="${userInfo.loginId}"/>&nbsp;</td>
					<td><c:out value="${userInfo.email}"/>&nbsp;</td>
					<td><fmt:formatDate value="${userInfo.lastLoginDate}" pattern="yyyy/MM/dd "/>&nbsp;</td>
 					<td>
 						<div class="btnBlockListEdit">
							<div class="btnBlockListEditInner">
								<div class="btnBlockListEditInner2">
									<p><a href="javascript:linkTo('../update/input/','<c:out value="${userInfo.userId}"/>');"><span>編集</span></a></p>
								</div>
							</div>
						</div>
 						<div class="btnBlockListDel">
							<div class="btnBlockListDelInner">
								<div class="btnBlockListDelInner2">
									<p><a href="javascript:linkTo('../delete/confirm/','<c:out value="${userInfo.userId}"/>');"><span>削除</span></a></p>
								</div>
							</div>
						</div>
					</td>
				</tr>
		</c:forEach>
			</table>
		</div>
		<!--/flexBlockA01 -->
	</c:if>

	</div>
	<!--/headingAreaInner -->

	<%-- 入力formパラメータ引き継ぎ --%>
	<form action="" method="post" name="inputdate">
	<input type="hidden" name="command" value="">
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
	<input type="hidden" name="userId" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/userManage/searchParams.jsh" />
	</form>

</c:param>
</c:import>