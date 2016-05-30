<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： 物件閲覧_新規登録画面

 2015/03/10		Ma.Shuangshuang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}"/>
<c:param name="pageTitle" value="物件閲覧" />
<c:param name="contents">

<script type ="text/JavaScript">
	function backLinkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.target = "";
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
</script>
<form method="post" name="inputForm" >
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件基礎情報</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/housingStatus/inputForm.jsh" />
</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockB06 -->
 <div class="flexBlockB06">

	<!--btnBlockC02 -->
	<div class="btnBlockC14">
		<div class="btnBlockC14Inner">
			<div class="btnBlockC14Inner2">
				<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/status/confirm/', '<c:out value="${inputForm.command}"/>');"><span>登録</span></a></p>
			</div>
		</div>
	</div>
	<!--/btnBlockC02 -->
		<!--btnBlockC02 -->
	<div class="btnBlockC14">
		<div class="btnBlockC14Inner">
			<div class="btnBlockC14Inner2">
				<p><a href="javascript:backLinkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/list/','list');"><span>戻る</span></a></p>
			</div>
		</div>
	</div>
 </div>
<!--/flexBlockB06 -->
</form>
</c:param>
</c:import>