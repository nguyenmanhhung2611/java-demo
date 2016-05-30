<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： NotFound共通画面

 2015/03/19		Trans	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="contents">
<!--headingAreaInner -->
<div class="headingAreaInner">
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>既に他のユーザに更新された可能性があります。最新の情報を確認してください。</p>
	</div>
	<!--/flexBlockB02 -->
</div>
<!--/headingAreaInner -->
</c:param>
</c:import>