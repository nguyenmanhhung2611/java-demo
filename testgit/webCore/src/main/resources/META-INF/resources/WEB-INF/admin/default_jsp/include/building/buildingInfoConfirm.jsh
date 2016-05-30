<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
建物情報メンテナンス機能で使用する建物基本情報確認画面の出力 
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">建物番号</th>
			<td><c:out value="${inputForm.buildingCd}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件種類</th>
			<td>
				<dm3lookup:lookup lookupName="buildingInfo_housingKindCd" lookupKey="${inputForm.housingKindCd}"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">建物構造</th>
			<td>
				<dm3lookup:lookup lookupName="buildingInfo_structCd" lookupKey="${inputForm.structCd}"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">表示用建物名</th>
			<td><c:out value="${inputForm.displayBuildingName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">表示用建物名ふりがな</th>
			<td><c:out value="${inputForm.displayBuildingNameKana}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・郵便番号</th>
			<td><c:out value="${inputForm.zip}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・都道府県</th>
			<td><c:out value="${inputForm.prefName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・市区町村</th>
			<td><c:out value="${inputForm.addressName}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・町名番地</th>
			<td><c:out value="${inputForm.addressOther1}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在地・建物名その他</th>
			<td><c:out value="${inputForm.addressOther2}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">竣工年月</th>
			<td><c:out value="${inputForm.compDate}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">竣工旬</th>
			<td><c:out value="${inputForm.compTenDays}"/>&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">総階数</th>
			<td><c:out value="${inputForm.totalFloors}"/>&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>
<%-- 建物閲覧formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
	<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
	<input type="hidden" name="buildingCd" value="<c:out value="${inputForm.buildingCd}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
	<input type="hidden" name="structCd" value="<c:out value="${inputForm.structCd}"/>">
	<input type="hidden" name="displayBuildingName" value="<c:out value="${inputForm.displayBuildingName}"/>">
	<input type="hidden" name="displayBuildingNameKana" value="<c:out value="${inputForm.displayBuildingNameKana}"/>">
	<input type="hidden" name="zip" value="<c:out value="${inputForm.zip}"/>">
	<input type="hidden" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
	<input type="hidden" name="prefName" value="<c:out value="${inputForm.prefName}"/>">
	<input type="hidden" name="addressCd" value="<c:out value="${inputForm.addressCd}"/>">
	<input type="hidden" name="addressName" value="<c:out value="${inputForm.addressName}"/>">
	<input type="hidden" name="addressOther1" value="<c:out value="${inputForm.addressOther1}"/>">
	<input type="hidden" name="addressOther2" value="<c:out value="${inputForm.addressOther2}"/>">
	<input type="hidden" name="compDate" value="<c:out value="${inputForm.compDate}"/>">
	<input type="hidden" name="compTenDays" value="<c:out value="${inputForm.compTenDays}"/>">
	<input type="hidden" name="totalFloors" value="<c:out value="${inputForm.totalFloors}"/>">
	<dm3token:oneTimeToken/>
</form>
<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>