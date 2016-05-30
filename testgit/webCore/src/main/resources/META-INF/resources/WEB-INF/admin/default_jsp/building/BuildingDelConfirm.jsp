<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 建物削除確認画面

 2015/03/20		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="建物削除確認画面" />
<c:param name="contents">

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>建物基本情報</h2>
		</div>

		<c:import url="/WEB-INF/admin/default_jsp/include/building/buildingInfo.jsh" >
			<c:param name="input_mode" value="delete" />
		</c:import>

	</div>
	<br/>
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

	<!-- flexBlockConfirm -->
	<div class="flexBlockConfirm">
		<div class="flexBlockConfirmInner">
			<p>上記の内容で削除してもよろしいですか？</p><br>
			<div class="flexBlockConfirmBackInner">
				<div class="btnBlockBack">
					<div class="btnBlockBackInner">
						<div class="btnBlockBackInner2">
							<p><a href="javascript:linkToUrl('../../list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>戻る</span></a></p>
						</div>
					</div>
				</div>
			</div>
			<div class="flexBlockConfirmCompInner">
				<div class="btnBlockComp">
					<div class="btnBlockCompInner">
						<div class="btnBlockCompInner2">
							<p><a href="javascript:linkToUrl('../result/', 'delete');"><span>確定</span></a></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockConfirm -->
	
	<script type ="text/JavaScript">
	<!--
		function linkToUrl(url, cmd) {
			document.building.action = url;
			document.building.command.value = cmd;
			document.building.submit();
		}
	// -->
	</script>
</c:param>
</c:import>