<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： お知らせ削除完了画面

 2015/02/10		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
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
		<h2>お知らせ情報削除完了</h2>
	</div>
						
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>お知らせのＤＢ削除が完了しました。</p>
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
	<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />
</form>

</c:param>
</c:import>