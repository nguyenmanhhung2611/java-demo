<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム情報編集完了画面

 2015/03/04		荊学ギョク	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム情報編集完了" />
<c:param name="contents">

<head>
<style type="text/css">
.flexBlockB06 {
	margin-top: 15px;
	width: 100%;
}
.flexBlockB06 .btnBlockC01{
	float: left;
}
.btnBlockC01Inner2 a span{
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
	<!--flexBlockC01 -->
	<div class="compFont">
		<p>登録を完了いたしました。</p>
	</div>
	<br>
	<!--/flexBlockC01 -->
	<br>
	<!--flexBlockB06 -->
	<div class="flexBlockB06">
	    <!--btnBlockC15 -->
	    <div class="btnBlockC15">
	        <div class="btnBlockC15Inner">
	            <div class="btnBlockC15Inner2">
					<p>	<a href="javascript:linkToUser('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/<c:out value="${inputForm.sysHousingCd}"/>/','update');"><span>物件閲覧へ戻る</span></a></p>
				</div>
			</div>
		</div>
		<!--btnBlockC15 -->
	</div>
	<!--flexBlockB06 -->

</div>
<!--/headingAreaInner -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputdate" >

	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>

</c:param>
</c:import>