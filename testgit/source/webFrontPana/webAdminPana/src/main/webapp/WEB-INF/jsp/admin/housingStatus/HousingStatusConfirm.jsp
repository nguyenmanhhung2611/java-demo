<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 物件ステータス変更確認画面

 2015/03/04		Ma.Shuangshuang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="物件ステータス変更確認画面" />
<c:param name="contents">

<!--headingAreaInner -->
<form method="post" name="inputForm" >
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>ステータス編集確認</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/housingStatus/confirm.jsh" />

</div>
<!--/headingAreaInner -->
<br><br><br>
<!--flexBlockB03 -->

<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">

		<!--btnBlockB01 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/status/result/', '<c:out value="${inputForm.command}"/>');"><span>登録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockB01 -->
		<!--btnBlockC03 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:back();"><span>戻る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC03 -->

	</div>
</div>
<!--/flexBlockB03 -->
</form>
</c:param>
</c:import>