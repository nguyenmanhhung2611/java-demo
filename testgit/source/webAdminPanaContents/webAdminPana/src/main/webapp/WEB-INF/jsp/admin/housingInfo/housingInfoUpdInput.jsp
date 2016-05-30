<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： 物件基本情報編集画面

 2015/04/05		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="物件基本情報編集画面" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件基本情報編集<span style="font-weight: normal"><font color="red">　※</font>入力必須項目</span></h2>
	</div>


	<c:import url="/WEB-INF/jsp/admin/include/housingInfo/inputForm.jsh" />

</div>
<!--/headingAreaInner -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../confirm/','<c:out value="${housingInfoForm.command}"/>');"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../../detail/<c:out value="${housingInfoForm.sysHousingCd}"/>/', 'back');"><span>戻&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->

</c:param>
</c:import>