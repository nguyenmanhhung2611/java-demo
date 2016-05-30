<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 物件ステータス変更完了画面

 2015/03/04		Ma.Shuangshuang	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}"/>
<c:param name="pageTitle" value="物件ステータス変更完了画面" />
<c:param name="contents">

<form method="post" name="inputForm" >
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>ステータス編集完了</h2>
	</div>
	<br>
	<!--flexBlockB02 -->
	<div class="compFont">
		<p>登録を完了いたしました。</p>
	</div>
	<br>
	<br>
    <!--flexBlockB03 -->
    <div class="flexBlockB03">

        <!--/btnBlockC02 -->
        <c:if test="${inputForm.command == 'update'}">
	        <div class="btnBlockC17">
	            <div class="btnBlockC17Inner">
	                <div class="btnBlockC17Inner2">
	                    <p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/list/', 'list');"><span>一覧へ戻る</span></a></p>
	                </div>
	            </div>
	        </div>
		</c:if>

        <!--btnBlockC02 -->
        <div class="btnBlockC17">
            <div class="btnBlockC17Inner">
                <div class="btnBlockC17Inner2">
                    <p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/<c:out value="${inputForm.sysHousingCd}"/>/', '');"><span>物件閲覧へ戻る</span></a></p>
                </div>
            </div>
        </div>
        <!--/btnBlockC02 -->
    </div>
</div>
<!--/headingAreaInner -->
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>

</c:param>
</c:import>