<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： お知らせ情報新規登録入力画面

 2015/02/09		I.zhang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>Add News Confirm</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/news/confirm.jsh" />
</div>
<!--/headingAreaInner -->
<br><br><br>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../result/', '');"><span>OK</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../input/', 'back');"><span>Back</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->

</c:param>
</c:import>