<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 404 （処理対象ないエラー）画面

 2015/05/15		Trans	新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="処理対象ないエラー" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>処理対象が存在しません。</p>
		<p>他のリクエストから変更・削除された可能性があります。　最初からやり直してください。</p>
	</div>
	<!--/flexBlockB02 -->
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>






