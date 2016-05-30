<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%-- ----------------------------------------------------------------
 名称： 403 （アクセス権エラー）画面

 2015/04/02		H.Mizuno	新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout_nonavi.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="システムエラー" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="flexBlockA01">
		Error ID:<%= request.getAttribute("ErrorServlet.ErrorID") %><br/><br/>

		<p>システムエラーが発生しました。</p>
		<p>管理者にお問合せしてください。</p>
	</div>
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>




					
					
