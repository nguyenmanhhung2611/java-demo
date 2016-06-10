<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%-- ----------------------------------------------------------------
 名称： お知らせ変更入力画面

 2015/04/04		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="住宅診断編集画面" />
	<c:param name="contents">
		<head>
<style type="text/css">
.flexBlockB06 .btnBlockC01 {
	float: right;
	padding-right: 10px;
}

.btnBlockC01Inner2 a span {
	padding: 0px 0px 0px 0px;
	background: none;
}

.flexBlockB07 .btnBlockC01 {
	float: left;
	padding-right: 10px;
}
</style>
		</head>
		<!--headingAreaInner -->
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>住宅診断編集</h2>
			</div>
			<c:import url="/WEB-INF/jsp/admin/include/housinginspection/housinginspectioninputForm.jsh" />
		</div>
		<!--/headingAreaInner -->

		<!--flexBlockB03 -->
		<div class="flexBlockB06">
			<!--btnBlockC14 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkToUrl('../confirm/', 'update');"><span>登録</span></a>
						</p>
					</div>
				</div>
			</div>
			<!--/btnBlockC14 -->
			<!--btnBlockC14 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a
								href="javascript:linkToUrl('../../detail/<c:out value="${HousingInspectionForm.sysHousingCd}"/>/', 'back');"><span>戻&nbsp;る</span></a>
						</p>
					</div>
				</div>
			</div>
			<!--/btnBlockC14 -->
		</div>
		<!--/flexBlockB03 -->

	</c:param>
</c:import>