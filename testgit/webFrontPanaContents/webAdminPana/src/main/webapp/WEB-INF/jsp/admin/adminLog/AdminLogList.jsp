<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup"%>

<%-- ----------------------------------------------------------------
 名称： 管理サイト画面

 2015/08/24		Duong.Nguyen	Create new for management admin log
 2015/08/24		vinh.Ly			Add validation for management admin log
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="管理サイトログダウンロード" />
	<c:param name="contents">
		<script type="text/JavaScript">
			function CsvOut(url) {
				document.adminLoggingForm.action = url;
				document.adminLoggingForm.target = "_blank";
				document.adminLoggingForm.submit();
				document.adminLoggingForm.target = "_self";
			}
		</script>

		<!--headingAreaInner -->
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>管理サイトログダウンロード</h2>
			</div>
			<form action="" method="post" name="adminLoggingForm">
				<input type="hidden" name="command" value="search" />
				<!--flexBlockA01 -->
				<div class="flexBlockA01">
					<table width="100%" border="1" cellspacing="0" cellpadding="0"
						class="tableA1">
						<tr>
							<th class="head_tr" width="20%">ログインID</th>
							<td><input name="keyLoginId"
								value="<c:out value="${searchForm.keyLoginId}"/>" type="text"
								size="16"
								maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyLoginId" defaultValue="16"/>"
								class="input2 ime-disabled"></td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">ユーザー名</th>
							<td><input name="keyUserName"
								value="<c:out value="${searchForm.keyUserName}"/>" type="text"
								size="16"
								maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyUserName" defaultValue="40"/>"
								class="input2">を含む</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">期間</th>
							<td width="80%"><input name="keyInsDateStart"
								value="<c:out value="${searchForm.keyInsDateStart}"/>"
								type="text" size="10" maxlength="10" class="input2 ime-disabled">
								～ <input name="keyInsDateEnd"
								value="<c:out value="${searchForm.keyInsDateEnd}"/>" type="text"
								size="10" maxlength="10" class="input2 ime-disabled">
								(yyyy/mm/dd)</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">機能</th>
							<td width="80%"><select name="keyAdminLogFC">
									<option value=""></option>
									<dm3lookup:lookupForEach lookupName="functionCd">
										<option value="<c:out value="${key}"/>"
											<c:if test="${searchForm.keyAdminLogFC == key}">selected</c:if>><c:out
												value="${value}" /></option>&nbsp;
								</dm3lookup:lookupForEach>
							</select></td>
						</tr>
					</table>
				</div>
				<!--/flexBlockA01 -->
			</form>
		</div>
		<!--/headingAreaInner -->
		<div class="flexBlockB06">
			<dm3login:hasRole roleName="admin">
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:CsvOut('../csv/');"><span>CSV 出力</span></a>
							</p>
						</div>
					</div>
				</div>
			</dm3login:hasRole>
		</div>
		<br>
		<!--/flexBlockB06 -->

		<div class="headingAreaInner">
			<p>
				<c:import
					url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
					<c:if test="${hitcont == 0}">
						<span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください。</span>
					</c:if>
			</p>
		</div>
	</c:param>
</c:import>