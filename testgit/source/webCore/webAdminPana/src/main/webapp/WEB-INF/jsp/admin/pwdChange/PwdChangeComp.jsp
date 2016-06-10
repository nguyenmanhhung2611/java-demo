<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- ----------------------------------------------------------------
 名称： パスワード変更完了画面

 2015/04/20		チョ夢		新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="パスワード編集完了画面" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>パスワード編集完了</h2>
	</div>

	<br>

	<!--compFont -->
	<div class="compFont">
		<p>登録を完了いたしました。</p>
	</div>
	<!--/compFont -->
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>
