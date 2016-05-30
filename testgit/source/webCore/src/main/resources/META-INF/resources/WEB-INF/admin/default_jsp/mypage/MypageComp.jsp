<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員登録・更新完了画面

 2015/03/05		H.Mizuno	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="マイページ会員メンテナンス" />
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
		<h2>マイページ会員追加・編集</h2>
	</div>
						
	<!--flexBlockB02 -->
	<div class="flexBlockB02">
		<p>マイページ会員のＤＢ更新が完了しました。</p>
	</div>
	<!--/flexBlockB02 -->
						
	<!-- flexBlockComp -->
	<div class="flexBlockComp">
		<div class="flexBlockCompInner">
			<div class="btnBlockBackToList">
				<div class="btnBlockBackToListInner">
					<div class="btnBlockBackToListInner2">
						<p><a href="javascript:linkToUser('../../list/','list');"><span>マイページ会員検索・一覧に戻る</span></a></p>
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
	<c:import url="/WEB-INF/admin/default_jsp/include/mypage/searchParams.jsh" />
</form>

</c:param>
</c:import>