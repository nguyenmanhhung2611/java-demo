<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： Cookie （Cookie利用エラー）画面

 2015/05/15		Trans	新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="Cookie利用エラー" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>Cookie が使用できません。　ブラウザーの設定を変更してください。</p>
	</div>
	<!--/flexBlockB02 -->
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>






