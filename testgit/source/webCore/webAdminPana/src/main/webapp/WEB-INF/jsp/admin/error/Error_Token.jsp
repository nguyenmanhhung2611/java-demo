<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
Name: CSRF error page

 2015/08/14		Duong.Nguyen	Create this file
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="ページ遷移エラー" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>ページの遷移が正しくありません。</p>
		<p>ページ操作の途中でのブラウザの「戻る」ボタンのご使用や、ブックマークなどからページへアクセスされた場合が考えられます。<br>お手数ですが、再度Topページからアクセスをお願いいたします。</p>
	</div>
	<!--/flexBlockB02 -->
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>




					
					
