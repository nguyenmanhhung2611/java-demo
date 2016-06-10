<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： お知らせ変更入力画面

 2015/01/10		I.zhang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>Update News</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/news/inputForm.jsh" />

</div>
<!--/headingAreaInner -->

<br><br><br>

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<div class="btnBlockC18">
			<div class="btnBlockC18Inner">
				<div class="btnBlockC18Inner2">
					<p><a href="javascript:linkToUrl('../confirm/', '');"><span>OK</span></a></p>
				</div>
			</div>
		</div>

		

		<!--/btnBlockC15 -->
		<div class="btnBlockC18">
			<div class="btnBlockC18Inner">
				<div class="btnBlockC18Inner2">
					<p><a href="javascript:linkToUrl('../../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>Back</span></a></p>
				</div>
			</div>
		</div>

	</div>
</div>
<!--/flexBlockB06 -->

</c:param>
</c:import>