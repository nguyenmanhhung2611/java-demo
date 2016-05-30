<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： おすすめポイント編集画面

 2015/4/10		郭中レイ			新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="おすすめポイント編集画面" />
<c:param name="contents">
<head>
<style type="text/css">
.flexBlockB06 .btnBlockC01 {
	float: right;
	padding-right: 10px;
}

.btnBlockC01Inner2 a span{
	padding: 0px 0px 0px 0px;
	background: none;
}
</style>
</head>
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>おすすめポイント編集画面</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/recommendPoint/inputForm.jsh" />

</div>
<!--/headingAreaInner -->

<br><br><br>
<!--flexBlockB06 -->
 <div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC02 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../confirm/', '');"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC02 -->

		<!--btnBlockB01 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../../detail/<c:out value="${inputForm.sysHousingCd}"/>/', 'back');"><span>戻&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockB01 -->


	</div>
 </div>
<!--/flexBlockB06 -->

</c:param>
</c:import>