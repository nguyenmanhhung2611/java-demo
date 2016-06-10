<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員情報変更入力画面

 2015/04/14		Y.Cho	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/front/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="会員情報変更" />
<c:param name="contents">
<c:import url="/WEB-INF/front/default_jsp/include/mypage/inputForm.jsh">
	<c:param name="input_mode" value="update" />
</c:import>

<div class="itemBlockA03">
	<div class="columnInner">
		<p class="center mb15 spPb10 spMt10"><button type="button" name="confirmBtn1" id="confirmBtn1" class="primaryBtn01" onClick="linkToUrl('../confirm/', ''); ">入力内容を確認する</button></p>
	</div><!-- /.columnInner -->
</div>

</c:param>
</c:import>