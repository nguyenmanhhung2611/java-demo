<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム画像編集完了画面

 2015/04/04		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム画像編集完了" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
    function linkToUrl(url,command) {
    	document.inputForm.action = url;
    	document.inputForm.submit();
    }

//-->
</script>

<!--headingAreaInner -->
<form action=""  method="post" name="inputForm">
	<input type="hidden" name="sysReformCd" value="<c:out value="${sysReformCd}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${sysHousingCd}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${housingCd}"/>">
    <input type="hidden" name="housingKindCd" value="<c:out value="${housingKindCd}"/>">
    <c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム画像編集完了</h2>
	</div>
	<br>
	<!--flexBlockB02 -->
	<div class="compFont">
		<p>登録を完了いたしました。</p>
	</div>
	<br>
	<br>
	<div class="flexBlockB06">
		<!--btnBlockB01 -->
		<div class="btnBlockC15">
            <div class="btnBlockC15Inner">
                <div class="btnBlockC15Inner2">
                    <p><a href="javascript:linkToUrl('../../../detail/<c:out value="${sysHousingCd}"/>/','back');"><span>物件閲覧へ戻る</span></a></p>
                </div>
            </div>
        </div>
		<!--/btnBlockB01 -->

		<!--btnBlockC02 -->
		 <div class="btnBlockC15">
            <div class="btnBlockC15Inner">
                <div class="btnBlockC15Inner2">
                    <p><a href="javascript:linkToUrl('../../input/', 'back');"><span>リフォーム編集画面へ戻る</span></a></p>
                </div>
            </div>
        </div>
		<!--/btnBlockC02 -->

	</div>
	<!--/flexBlockB02 -->
</div>
<!--/headingAreaInner -->

</c:param>
</c:import>