<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： 画像アップロード編集画面

 2015/04/05		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="画像アップロード編集" />
<c:param name="contents">
<form action="" method="post" enctype="multipart/form-data"  name="inputForm">
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>画像アップロード</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/housingImageInfo/inputForm1.jsh" />

</div>
<!--/headingAreaInner -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:linkToUrl('../confirm/','insert');"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../../detail/<c:out value="${housingImageInfoForm.sysHousingCd}"/>/', 'back');"><span>戻&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->
<br><br>

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>画像アップロード編集</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/housingImageInfo/inputForm2.jsh" />

</div>
<!--/headingAreaInner -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<c:if test="${housingImageInfoForm.divNo != null}">
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:linkToUrl('../confirm/','update');"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		</c:if>
		<c:if test="${housingImageInfoForm.divNo == null}">
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a disabled="disabled"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		</c:if>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('../../detail/<c:out value="${housingImageInfoForm.sysHousingCd}"/>/', 'back');"><span>戻&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>
</c:param>
</c:import>