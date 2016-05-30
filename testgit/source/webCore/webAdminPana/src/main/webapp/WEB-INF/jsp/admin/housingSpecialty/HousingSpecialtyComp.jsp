<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 物件特長編集完了画面

 2015/04/9		liu.yandong	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="物件特長編集完了画面" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
    function linkToUrl(url,command) {
    	document.searchForm.action = url;
    	document.searchForm.command.value = command;
    	document.searchForm.submit();
    }
// -->

</script>

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件特長編集完了</h2>
	</div>
	<br>
	<div class="compFont">
		<p >登録を完了いたしました。</p>
	</div>
	<br>
	<br>
	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div class="flexBlockB06Inner clear">
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
							<a href="javascript:linkToUrl('../../detail/<c:out value="${inputForm.sysHousingCd}"/>/','');"><span>物件閲覧へ戻る</span></a>
						</p>
					</div>
				</div>
			</div>
			<!--/btnBlockC17 -->
		</div>
	</div>
	<!--/flexBlockB06 -->

</div>
<!--/headingAreaInner -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="searchForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>

</c:param>
</c:import>