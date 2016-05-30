<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 管理者ユーザー削除確認画面

 2015/02/04		H.Mizuno	Shamaison 管理サイトを参考に新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="管理ユーザーメンテナンス" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>管理ユーザー削除</h2>
	</div>

	<c:import url="/WEB-INF/admin/default_jsp/include/userManage/detail.jsh">
			<c:param name="input_mode" value="delete" />
	</c:import>

</div>
<!--/headingAreaInner -->
<br><br><br>
<!--flexBlockConfirm -->
<div class="flexBlockConfirm">
	<div class="flexBlockConfirmInner">
		<p>削除してもよろしいですか？</p><br>
		<div class="flexBlockConfirmBackInner">
			<div class="btnBlockBack">
				<div class="btnBlockBackInner">
					<div class="btnBlockBackInner2">
						<p><a href="javascript:linkToUrl('../../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>戻る</span></a></p>
					</div>
				</div>
			</div>
		</div>

		<div class="flexBlockConfirmCompInner">
			<div class="btnBlockConfirm">
				<div class="btnBlockConfirmInner">
					<div class="btnBlockConfirmInner2">
						<p><a href="javascript:linkToUrl('../result/', '');"><span>確定</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--/flexBlockConfirm -->

</c:param>
</c:import>