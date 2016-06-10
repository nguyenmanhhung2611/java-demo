<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員新規登録入力画面

 2015/03/05		H.Mizuno	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="マイページ会員メンテナンス" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>マイページ会員新規登録</h2>
	</div>

	<c:import url="/WEB-INF/admin/default_jsp/include/mypage/inputForm.jsh">
		<c:param name="input_mode" value="insert" />
	</c:import>

</div>
<!--/headingAreaInner -->

<br><br><br>
<!-- flexBlockInput -->
<div class="flexBlockInput">
	<div class="flexBlockInputInner">

		<div class="flexBlockInputBackInner">
			<div class="btnBlockBack">
				<div class="btnBlockBackInner">
					<div class="btnBlockBackInner2">
						<p><a href="javascript:linkToUrl('../../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>戻る</span></a></p>
					</div>
				</div>
			</div>
		</div>

		<div class="flexBlockInputConfirmInner">
			<div class="btnBlockConfirm">
				<div class="btnBlockConfirmInner">
					<div class="btnBlockConfirmInner2">
						<p><a href="javascript:linkToUrl('../confirm/', '');"><span>確認画面へ</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /flexBlockInput -->


</c:param>
</c:import>