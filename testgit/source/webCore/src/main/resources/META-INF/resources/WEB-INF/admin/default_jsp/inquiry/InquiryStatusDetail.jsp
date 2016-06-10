<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： お問合せ情報詳細画面

 2015/03/30		cho.yu	
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value=" お問合せ情報詳細" />
<c:param name="contents">

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>お問合せ詳細</h2>
		</div>

		<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/detail.jsh"/>

	</div>
	<!--/headingAreaInner -->

	<!-- flexBlockDtl -->
	<div class="flexBlockDtl">
		<div class="flexBlockDtlInner">
			<div class="btnBlockBack">
				<div class="btnBlockBackInner">
					<div class="btnBlockBackInner2">
						<p><a href="javascript:linkToUrl('../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>戻る</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockDtl -->
	<br><br>

	<div class="headingAreaInner">
		<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/housingInquiryInfo.jsh" />
	</div>

</c:param>
</c:import>