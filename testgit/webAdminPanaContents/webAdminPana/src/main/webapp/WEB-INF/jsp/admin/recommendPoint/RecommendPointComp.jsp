<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： おすすめポイント編集完了画面

  2015/4/10		郭中レイ			新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="おすすめポイント編集完了画面" />
<c:param name="contents">
<head>
<style type="text/css">
.flexBlockB06 {
	margin-top: 15px;
	width: 100%;
}

.flexBlockB06 .btnBlockC15 {
	float: left;
	padding-right: 10px;
}

.btnBlockC15Inner2 a span{
	padding: 0px 0px 0px 0px;
	background: none;
}
</style>
</head>
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
		<h2>おすすめポイント編集完了</h2>
	</div>
	<br>
	<!--flexBlockB02 -->
	<div class="compFont">
		<p>登録を完了いたしました。</p>
	</div>
		<br>
			<br>
	<!--/flexBlockB02 -->
	<!--flexBlockB02 -->
	<div class="flexBlockB06">
		<!--btnBlockB01 -->
		<div class="btnBlockC17">
			<div class="btnBlockC17Inner">
				<div class="btnBlockC17Inner2">
					<p>
						<a href="javascript:linkToUser('../../list/','list');"><span>一覧へ戻る</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockB01 -->

		<!--btnBlockB02 -->
		<div class="btnBlockC17">
			<div class="btnBlockC17Inner">
				<div class="btnBlockC17Inner2">
					<p>
						<a href="javascript:linkToUser('../../detail/<c:out value="${inputForm.sysHousingCd}"/>/', 'back');"><span>物件閲覧へ戻る</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockB02 -->

	</div>
	<!--/flexBlockB02 -->

</div>
<!--/headingAreaInner -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputdate" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>

</c:param>
</c:import>