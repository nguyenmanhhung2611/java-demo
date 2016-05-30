<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 問合ステータス編集完了画面

 2015/04/09		cho.yu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="問合ステータス編集完了" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
    function linkToUser(url,command) {
    	document.inputdate.action = url;
    	document.inputdate.command.value = command;
    	document.inputdate.submit();
    }
// -->

</script>

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>問合ステータス編集完了</h2>
	</div>
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>お問合せのＤＢ更新が完了しました。</p>
	</div>
	<!--/flexBlockB02 -->

	<!-- flexBlockComp -->
	<div class="flexBlockComp">
		<div class="flexBlockCompInner">
			<div class="btnBlockBackToList">
				<div class="btnBlockBackToListInner">
					<div class="btnBlockBackToListInner2">
						<p><a href="javascript:linkToUser('../../list/','list');"><span>一覧へ戻る</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockComp -->


</div>
<!--/headingAreaInner -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputdate" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/searchParams.jsh" />
</form>

</c:param>
</c:import>