<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
建物情報メンテナンス機能で使用する情報閲覧画面の出力 
--%>

<!--flexBlockA01 -->
<c:set var="buildingInfo" value="${building.items['buildingInfo']}"/>
<c:set var="prefMst" value="${building.items['prefMst']}"/>
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">建物番号</th>
			<td><c:out value="${buildingInfo.buildingCd}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件種類</th>
			<td>
				<dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="${buildingInfo.housingKindCd}"/>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">建物構造</th>
			<td>
				<dm3lookup:lookup lookupName="buildingInfo_structCd" lookupKey="${buildingInfo.structCd}"/>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">表示用建物名</th>
			<td><c:out value="${buildingInfo.displayBuildingName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">表示用建物名ふりがな</th>
			<td><c:out value="${buildingInfo.displayBuildingNameKana}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・郵便番号</th>
			<td><c:out value="${buildingInfo.zip}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・都道府県</th>
			<td><c:out value="${prefMst.prefName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・市区町村</th>
			<td><c:out value="${buildingInfo.addressName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・町名番地</th>
			<td><c:out value="${buildingInfo.addressOther1}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・建物名その他</th>
			<td><c:out value="${buildingInfo.addressOther2}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">竣工年月&nbsp;&nbsp;(YYYY/MM)</th>
			<td><fmt:formatDate value="${buildingInfo.compDate}" pattern="yyyy/MM" />&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">竣工旬</th>
			<td><c:out value="${buildingInfo.compTenDays}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">総階数</th>
			<td><c:out value="${buildingInfo.totalFloors}"/>&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->
<c:if test="${inputForm.command != 'delete'}">
<div class="flexBlockBldDtl">
	<div class="flexBlockBldDtlInner">
		<div class="btnBlockEdit">
			<div class="btnBlockEditInner">
				<div class="btnBlockEditInner2">
					<p><a href="javascript:linkToUpd('../info/update/input/');"><span>編集</span></a></p>
				</div>
			</div>
		</div>
	</div>
</div>
<br/>
<br/>
</c:if>

<%-- 建物閲覧formパラメータ引き継ぎ --%>
<form method="post" name="building" action="">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="sysBuildingCd" value="<c:out value='${buildingInfo.sysBuildingCd}'/>">
	<input type="hidden" name="prefCd" value="<c:out value="${buildingInfo.prefCd}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
	<c:if test="${param.input_mode == 'delete'}">
		<dm3token:oneTimeToken/>
	</c:if>
</form>