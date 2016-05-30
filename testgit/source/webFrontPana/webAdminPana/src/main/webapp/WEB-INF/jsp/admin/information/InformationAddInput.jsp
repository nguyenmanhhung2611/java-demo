<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： お知らせ情報新規登録入力画面

 2015/02/06		I.zhang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>お知らせ情報編集</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/information/inputForm.jsh" />

</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockB03 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">

		<div class="btnBlockC18">
			<div class="btnBlockC18Inner">
				<div class="btnBlockC18Inner2">
					<p><a href="javascript:linkToUrl('../confirm/', '');"><span>登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>

		<!--/btnBlockC15 -->

		<div class="btnBlockC18">
			<div class="btnBlockC18Inner">
				<div class="btnBlockC18Inner2">
					<p><a disabled="disabled"><span>コピーして新規作成</span></a></p>
				</div>
			</div>
		</div>

		<!--/btnBlockC15 -->
		<div class="btnBlockC18">
			<div class="btnBlockC18Inner">
				<div class="btnBlockC18Inner2">
					<p><a href="javascript:linkToUrl('../../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>戻&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>

	</div>
</div>
<!--/flexBlockB03 -->

</c:param>
</c:import>