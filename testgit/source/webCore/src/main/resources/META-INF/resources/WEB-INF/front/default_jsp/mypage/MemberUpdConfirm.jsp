<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員情報変更入力確認画面

 2015/04/16		Y.Cho	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/front/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="会員情報変更" />
<c:param name="contents">

<c:import url="/WEB-INF/front/default_jsp/include/mypage/confirm.jsh"/>

<!--contentsInner01 -->
<div class="contentsInner01 mt30 mb15 spMt10 spPb10">
	<ul class="btnList01">
		<li><button type="button" name="inputBtn" class="backBtn" onClick="linkToUrl('../input/', 'back');">修正する</button></li>
		<li><button type="button" name="updateBtn" class="primaryBtn01" onClick="linkToUrl('../update/', '');">この内容で<br class="SPdisplayBlock">登録する</button></li>
	</ul>
</div>
<!--/contentsInner01 -->

</c:param>
</c:import>