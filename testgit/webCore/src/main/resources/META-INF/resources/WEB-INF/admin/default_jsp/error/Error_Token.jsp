<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- ----------------------------------------------------------------
 名称： CSRF対策用トークンチェックエラー画面

 2015/07/24		S.Tanaka	新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout_nonavi.jsh">
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




					
					
