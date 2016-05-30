<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- 
建物情報メンテナンス機能で使用する地域情報の出力 
--%>

<!--flexBlockA01 -->
<c:set var="buildingInfo" value="${building.items['buildingInfo']}"/>
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr class="head_tr">
			<td  width="150">施設名</td>
			<td>施設情報（名称/所要時間/距離）</td>
		</tr>
		<dm3lookup:lookupForEach lookupName="buildingLandmark_landmarkType">
		<tr>
			<td><c:out value="${value}"/>&nbsp;&nbsp;</td>
			<td>
			<c:forEach items="${buildingLandmarkList}" var="buildingLandmark">
			<c:if test="${key == buildingLandmark.landmarkType}">
				<c:out value="${buildingLandmark.landmarkName}"/>:
				<c:if test="${buildingLandmark.wayFromLandmark == 1}">
				徒歩<c:out value="${buildingLandmark.timeFromLandmark}"/>分
				</c:if>
				<c:if test="${buildingLandmark.wayFromLandmark == 3}">
				距離<c:out value="${buildingLandmark.distanceFromLandmark}"/>km
				</c:if>&nbsp;&nbsp;
			</c:if>
			</c:forEach>
			&nbsp;&nbsp;
			</td>
		</tr>
		</dm3lookup:lookupForEach>
	</table>
</div>
<c:if test="${inputForm.command != 'delete'}">
<!--/flexBlockA01 -->
<div class="flexBlockBldDtl">
	<div class="flexBlockBldDtlInner">
		<div class="btnBlockEdit">
			<div class="btnBlockEditInner">
				<div class="btnBlockEditInner2">
					<p><a href="javascript:linkToUpdBuildingLandmarkInput('../landmark/input/');"><span>編集</span></a></p>
				</div>
			</div>
		</div>
	</div>
</div>
<br/>
<br/>
</c:if>
<%-- 建物閲覧formパラメータ引き継ぎ --%>
<form method="post" name="buildingLandmark" action="">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="sysBuildingCd" value="<c:out value='${buildingInfo.sysBuildingCd}'/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
</form>