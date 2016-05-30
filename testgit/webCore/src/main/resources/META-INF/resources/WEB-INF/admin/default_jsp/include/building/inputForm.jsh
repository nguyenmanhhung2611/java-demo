<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
建物情報メンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" name="prefName" value="<c:out value="${inputForm.prefName}"/>">
		<input type="hidden" name="addressName" value="<c:out value="${inputForm.addressName}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />

		<table class="confirmBox">
			<tr>
				<th class="head_tr">建物番号</th>
				<td>
					<input type="text" name="buildingCd" value="<c:out value="${inputForm.buildingCd}"/>" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.buildingCd" defaultValue="20"/>" class="input2">&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件種類</th>
				<td>
				    <dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd">
        			  <input type="radio" name="housingKindCd" value="${key}" <c:if test="${inputForm.housingKindCd == key}">checked</c:if>><c:out value="${value}"/>&nbsp;&nbsp;
					</dm3lookup:lookupForEach>
				</td>
				
			</tr>	
			<tr>
				<th class="head_tr">建物構造</th>
				<td>
				    <dm3lookup:lookupForEach lookupName="buildingInfo_structCd">
        			  <input type="radio" name="structCd" value="${key}" <c:if test="${inputForm.structCd == key}">checked</c:if>><c:out value="${value}"/>&nbsp;&nbsp;
					</dm3lookup:lookupForEach>
				</td>
			</tr>	
			<tr>
				<th class="head_tr">表示用建物名&nbsp;&nbsp;<font color="red">※</font></th>
				<td>
					<input type="text" name="displayBuildingName" value="<c:out value="${inputForm.displayBuildingName}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.displayBuildingName" defaultValue="40"/>" class="input2">&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">表示用建物名ふりがな</th>
				<td><input type="text" name="displayBuildingNameKana" value="<c:out value="${inputForm.displayBuildingNameKana}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.displayBuildingNameKana" defaultValue="80"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">所在地・郵便番号</th>
				<td><input type="text" name="zip" value="<c:out value="${inputForm.zip}"/>" size="7" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.zip" defaultValue="7"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">所在地・都道府県&nbsp;&nbsp;<font color="red">※</font></th>
				<td>					
					<select name="prefCd">
					<option></option>
					<c:forEach items="${prefMstList}" var="prefMst">
						<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${inputForm.prefCd == prefMst.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">所在地・市区町村名</th>
				<td>
					<select name="addressCd">
					<option></option>
					<c:forEach items="${addressMstList}" var="addressMst">
						<option value="<c:out value="${addressMst.addressCd}"/>" <c:if test="${inputForm.addressCd == addressMst.addressCd}"> selected</c:if>><c:out value="${addressMst.addressName}"/></option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">所在地・町名番地</th>
				<td><input type="text" name="addressOther1" value="<c:out value="${inputForm.addressOther1}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.addressOther1" defaultValue="50"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">所在地・建物名その他</th>
				<td><input type="text" name="addressOther2" value="<c:out value="${inputForm.addressOther2}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.addressOther2" defaultValue="50"/>" class="input2"></td>
			</tr>
				<th class="head_tr">竣工年月&nbsp;&nbsp;(YYYY/MM)</th>
				<td><input type="text" name="compDate" value="<c:out value="${inputForm.compDate}"/>" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.compDate" defaultValue="7"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">竣工旬</th>
				<td><input type="text" name="compTenDays" value="<c:out value="${inputForm.compTenDays}"/>" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.compTenDays" defaultValue="10"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">総階数</th>
				<td><input type="text" name="totalFloors" value="<c:out value="${inputForm.totalFloors}"/>" size="3" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.input.totalFloors" defaultValue="3"/>" class="input2"></td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $("input[name='prefName']").attr("value",$("select[name='prefCd']").find("option:selected").text());
  $("select[name='prefCd']").change(function(){
    $("input[name='prefName']").attr("value",$("select[name='prefCd']").find("option:selected").text());
    $.getJSON("<c:out value="${pageContext.request.contextPath}"/>/top/building/addressMstList/",{prefCd:$("select[name='prefCd']").val()},function(dataObj){
    $("select[name='addressCd'] option").remove();
     	var opt = document.createElement("option");
		opt.value = null;
		opt.text = "";
		document.inputForm.addressCd.add(opt);
 	   $(dataObj.addressMstList).each(function () {
 	   	var opt = document.createElement("option");
		opt.value = this.addressCd;
		opt.text = this.addressName;
		document.inputForm.addressCd.add(opt);
      });
    });
  });	
  $("select[name='addressCd']").change(function(){
   	 $("input[name='addressName']").attr("value",$("select[name='addressCd']").find("option:selected").text());
  });
});
function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}
// -->
</script>
