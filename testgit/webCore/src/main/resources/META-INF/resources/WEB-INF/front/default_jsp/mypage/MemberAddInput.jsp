<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： マイページ会員新規登録入力画面

 2015/04/14		Y.Cho	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/front/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="新規会員登録" />
<c:param name="contents">
<script type ="text/JavaScript">
<!--
$(function(){
	var $submitBtn = $("#confirmBtn");
	var $agreeCheck = $("#agreeChk");
	$submitBtn.attr('disabled', 'disabled').addClass('disabled');
	$agreeCheck.on("click", function() {
		if ($(this).prop('checked')) {
			$submitBtn.removeAttr('disabled').removeClass('disabled');
		} else {
			$submitBtn.attr('disabled', 'disabled').addClass('disabled');
		}
	});
});
// -->
</script>
<c:import url="/WEB-INF/front/default_jsp/include/mypage/inputForm.jsh">
	<c:param name="input_mode" value="insert" />
</c:import>

<div class="itemBlockA03">
	<div class="columnInner">
		<p class="txt01 center spLeft">ご入力いただいた情報は、当社の個人情報保護方針に従い、適正に管理いたします。<br><span class="bold">下記の「会員規約」および「個人情報の取り扱い」を必ずご一読いただき、同意のうえお申し込みください。</span></p>
		<textarea class="textAreaType01"></textarea>
		<p class="mb15 center spLeft"><label onClick=""><input type="checkbox" id="agreeChk"> 個人情報の取り扱いに同意する</label></p>
		<p class="center mb15"><button type="button" name="confirmBtn" id="confirmBtn" class="primaryBtn01" onClick="linkToUrl('../confirm/', '');" disabled>入力内容を確認する</button></p>
	</div><!-- /.columnInner -->
</div>

</c:param>
</c:import>