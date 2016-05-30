<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- ----------------------------------------------------------------
 名称： 住宅診断編集完了画面

 2015/04/04		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="住宅診断編集完了画面" />
	<c:param name="contents">

<script type ="text/JavaScript">
<!--
    function linkToUrl(url,command) {
    	document.HousingInspectionForm.action = url;
    	document.HousingInspectionForm.command.value = command;
    	document.HousingInspectionForm.submit();
    }
// -->

</script>
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>住宅診断編集完了</h2>
		</div>
	<br>
		<div class="compFont">
			<p >登録を完了いたしました。</p>
		</div>
	<br>
	<br>
	<!--flexBlockB02 -->
			<div class="flexBlockB06">

				<!--btnBlockC17 -->
				<div class="btnBlockC17">
					<div class="btnBlockC17Inner">
						<div class="btnBlockC17Inner2">
							<p>
								<a href="javascript:linkToUrl('../../list/','list');"><span>一覧へ戻る</span></a>
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
								<a href="javascript:linkToUrl('../../detail/<c:out value="${sysHousingCd}"/>/','back');"><span>物件閲覧へ戻る</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockC17 -->

			</div>
			<!--/flexBlockB02 -->

		</div>
		<!--/headingAreaInner -->

		<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
		<form method="post" name="HousingInspectionForm">
			<input type="hidden" name="command" value="update">
			<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
		</form>

	</c:param>
</c:import>