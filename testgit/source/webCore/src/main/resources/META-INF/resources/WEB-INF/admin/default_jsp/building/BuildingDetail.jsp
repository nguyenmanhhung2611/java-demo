<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 建物閲覧画面

 2015/03/06		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="建物閲覧画面" />
<c:param name="contents">

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>建物基本情報</h2>
		</div>

		<c:import url="/WEB-INF/admin/default_jsp/include/building/buildingInfo.jsh" />

	</div>
	
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>最寄り駅情報</h2>
		</div>
		<c:import url="/WEB-INF/admin/default_jsp/include/building/buildingStationInfo.jsh" />
	</div>
	<br/>
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>地域情報</h2>
		</div>
		<c:import url="/WEB-INF/admin/default_jsp/include/building/buildingLandmark.jsh" />
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
	
	<script type ="text/JavaScript">
	<!--
		function linkToUrl(url, cmd) {
			document.building.action = url;
			document.building.command.value = cmd;
			document.building.submit();
		}
		function linkToUpd(url, sysBuildingCd) {
			document.building.action = url;
			document.building.command.value = 'update';
			document.building.submit();
		}
		function linkToUpdBuildingStationInfo(url) {
			document.buildingStationInfo.action = url;
			document.buildingStationInfo.command.value = 'update';
			document.buildingStationInfo.submit();
		}
		function linkToUpdBuildingLandmarkInput(url) {
			document.buildingLandmark.action = url;
			document.buildingLandmark.command.value = 'update';
			document.buildingLandmark.submit();
		}
	// -->
	</script>
</c:param>
</c:import>