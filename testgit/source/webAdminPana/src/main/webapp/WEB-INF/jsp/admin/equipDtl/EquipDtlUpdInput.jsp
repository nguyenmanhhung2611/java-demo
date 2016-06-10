<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 管理者用設備情報編集画面

  2015/4/14		郭中レイ			新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="管理者用設備情報編集画面" />
<c:param name="contents">
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>管理者用設備情報編集</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/equipDtl/inputForm.jsh" />

</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<!--btnBlockC14 -->
	<div class="btnBlockC14">
		<div class="btnBlockC14Inner">
			<div class="btnBlockC14Inner2">
				<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/equip/confirm/', '');"><span>登&nbsp;&nbsp;録</span></a>
			</div>
		</div>
	</div>
	<!--/btnBlockC14 -->

	<!--btnBlockC14 -->
	<div class="btnBlockC14">
		<div class="btnBlockC14Inner">
			<div class="btnBlockC14Inner2">
				<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/<c:out value="${inputForm.sysHousingCd}"/>/', 'update');"><span>戻&nbsp;&nbsp;る</span></a></p>
			</div>
		</div>
	</div>
	<!--/btnBlockC14 -->
</div>
<!--/flexBlockB06 -->

</c:param>
</c:import>