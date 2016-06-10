<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 管理者用設備情報編集完了画面

 2015/4/14		郭中レイ			新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="管理者用設備情報編集完了画面" />
<c:param name="contents">
<script type="text/JavaScript">
<!--
	function linkToUser(url, command) {
		document.inputForm.action = url;
		document.inputForm.command.value = command;
		document.inputForm.submit();
	}
// -->
</script>

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>管理者用設備情報編集完了</h2>
	</div>
	<br>
	<div class="compFont">
		<p >登録を完了いたしました。</p>
	</div>

	<br>
	<br>

	<!--flexBlockB06 -->
	<div class="flexBlockB06">

		<!--btnBlockC17 -->
		<div class="btnBlockC17">
			<div class="btnBlockC17Inner">
				<div class="btnBlockC17Inner2">
					<p>
						<a href="javascript:linkToUser('<c:out value="${pageContext.request.contextPath}"/>/top/housing/list/','list');"><span>一覧へ戻る</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockC17 -->

		<!--btnBlockC17 -->
		<div class="btnBlockC17">
			<div class="btnBlockC17Inner">
				<div class="btnBlockC17Inner2">
					<p>
						<a href="javascript:linkToUser('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/<c:out value="${inputForm.sysHousingCd}"/>/','update');"><span>物件閲覧へ戻る</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockC17 -->

	</div>
	<!--/flexBlockB06 -->

</div>
<!--/headingAreaInner -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="" />
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>
</c:param>
</c:import>