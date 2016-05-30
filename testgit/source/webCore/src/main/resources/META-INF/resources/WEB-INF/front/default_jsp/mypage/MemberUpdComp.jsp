<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員登録・更新完了画面

 2015/04/17		Y.Cho	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/front/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="会員情報更新完了" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
    function linkToUrl(url,command) {
    	document.inputdate.action = url;
    	document.inputdate.command.value = command;
    	document.inputdate.submit();
    }
// -->

</script>

<!--section01 -->
<div class="headingBlockA01 clearfix">
	<h1 class="ttl">お客様情報の編集</h1>
</div><!-- /.headingBlockA01 -->
<div class="contentsInner01 mb50 spMb15">
	<p class="bold mb30 spMb10 f18 spF16 center spLeft">お客様情報の変更が完了しました。</p>
</div>
<div class="contentsInner01">
	<p class="center spPb10"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/mypage', '');" class="secondaryBtn">マイページへ</a></p>
</div>
<!--/section01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputdate" >
	<input type="hidden" name="command" value="">
</form>

</c:param>
</c:import>