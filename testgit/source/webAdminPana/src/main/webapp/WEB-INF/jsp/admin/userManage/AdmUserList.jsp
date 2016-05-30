<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<%-- ----------------------------------------------------------------
 名称： 管理者ユーザー一覧画面

 2015/04/20		チョ夢		新規作成
 2015/08/20     Vinh.Ly Add CSRF Token for User Delete
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="管理ユーザ一覧画面" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
	function search(url){
		document.userform.action = url;
		document.userform.submit();
	}
	function csvOut(url){
		document.userform.action = url;
		document.userform.target = "_blank";
		document.userform.submit();
		document.userform.target = "_self";
	}
	function newlist(url){
		document.inputdate.action = url;
		document.inputdate.submit();
	}
	function linkToUpd(url, id) {
		document.inputdate.action = url;
		document.inputdate.userId.value = id;
		document.inputdate.submit();
	}
	function linkToDel(url, id) {
		var returenValue = window.confirm("削除を行います。よろしいですか？")
		if (returenValue == true) {
			document.inputdate.action = url;
			document.inputdate.userId.value = id;
			document.inputdate.command.value = 'delete';
			document.inputdate.submit();
		}
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
			<h2>管理ユーザの検索</h2>
		</div>
		<form action="../list/" method="post" name="userform">
		<input type="hidden" name="command" value="list"/>
		<input type="hidden" name="userId" value="">

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="20%">ログインID</th>
					<td><input name="keyLoginId" value="<c:out value="${searchForm.keyLoginId}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyLoginId" defaultValue="16"/>" class="input2 ime-disabled"></td>
				</tr>
				<tr>
					<th class="head_tr" width="20%">ユーザー名</th>
					<td><input name="keyUserName"  value="<c:out value="${searchForm.keyUserName}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyUserName" defaultValue="40"/>" class="input2">を含む</td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div class="flexBlockB06Inner clear">
			<!--btnBlockC14 -->
			<c:choose>
				<c:when test="${hitcont != 0 && hitcont != null}">
					<div class="btnBlockC14">
						<div class="btnBlockC14Inner">
							<div class="btnBlockC14Inner2">
								<p>
									<a href="javascript:csvOut('../csv/');"><span>CSV&nbsp;出力</span></a>
								</p>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div class="btnBlockC14">
						<div class="btnBlockC14Inner">
							<div class="btnBlockC14Inner2">
								<p>
									<a disabled="disabled"><span>CSV&nbsp;出力</span></a>
								</p>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			<!--/btnBlockC14 -->

			<!--btnBlockC14 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:search('../list/');"><span>検&nbsp;&nbsp;索</span></a>
						</p>
					</div>
				</div>
			</div>
			<!--/btnBlockC14 -->
		</div>
	</div>
	<!--/flexBlockB06 -->

	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div class="flexBlockB06Inner clear">
			<!--btnBlockC14 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p><a href="javascript:newlist('../new/input/');"><span>新規追加<span></a></p>
					</div>
				</div>
			</div>
			<!--/btnBlockC14 -->
		</div>
	</div>
	<!--/flexBlockB06 -->

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${command == 'list'}">
		<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください。</span></c:if>
	</c:if>
	</p></div>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>管理ユーザ一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="0">
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

			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr class="head_tr">
					<td width="20%">ログインID</td>
					<td width="32%">管理ユーザ名</td>
					<td width="31%">最終ログイン日時</td>
					<td width="17%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
				<c:forEach items="${searchForm.visibleRows}" var="adminUser">
					<c:set var="userInfo" value="${adminUser.items['adminLoginInfo']}"/>
						<tr>
							<td><a href="javascript:linkToUpd('../update/input/','<c:out value="${userInfo.userId}"/>')"><c:out value="${userInfo.loginId}"/></a>&nbsp;</td>
							<td><c:out value="${userInfo.userName}"/>&nbsp;</td>
							<td><fmt:formatDate value="${userInfo.lastLoginDate}" pattern="yyyy/MM/dd HH:mm:ss"/>&nbsp;</td>
		 					<td>
								<div class="btnBlockC11">
									<div class="btnBlockC11Inner">
										<div class="btnBlockC11Inner2">
												<p><a class="tblSmallBtn" href="javascript:linkToUpd('../update/input/','<c:out value="${userInfo.userId}"/>');"><span>編集</span></a></p>
										</div>
									</div>
								</div>
								<div class="btnBlockC12">
									<div class="btnBlockC12Inner">
										<div class="btnBlockC12Inner2">
												<p><a class="tblSmallBtn" href="javascript:linkToDel('../delete/','<c:out value="${userInfo.userId}"/>');"><span>削除</span></a></p>
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
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>