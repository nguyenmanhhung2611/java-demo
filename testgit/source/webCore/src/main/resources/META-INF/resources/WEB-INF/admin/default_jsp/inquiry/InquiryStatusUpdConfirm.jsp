<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 問合ステータス編集確認画面

 2015/04/09		cho.yu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="問合ステータス編集確認" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>問合ステータス編集確認</h2>
	</div>

	<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/confirm.jsh" />

</div>
<!--/headingAreaInner -->

<!-- flexBlockConfirm -->
<div class="flexBlockConfirm">
	<div class="flexBlockConfirmInner">
		<p>上記の内容で変更してもよろしいですか？</p><br>
		<div class="flexBlockConfirmBackInner">
			<div class="btnBlockBack">
				<div class="btnBlockBackInner">
					<div class="btnBlockBackInner2">
						<p><a href="javascript:linkToUrl('../input/', 'back');"><span>戻る</span></a></p>
					</div>
				</div>
			</div>
		</div>

		<div class="flexBlockConfirmCompInner">
			<div class="btnBlockComp">
				<div class="btnBlockCompInner">
					<div class="btnBlockCompInner2">
						<p><a href="javascript:linkToUrl('../result/', '');"><span>確定</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /flexBlockConfirm -->

</c:param>
</c:import>