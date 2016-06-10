<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
画像アップロード機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
		<input type="hidden" name="command" value="" />
		<input type="hidden" name="housingCd" value="<c:out value="${housingImageInfoForm.housingCd}"/>">
        <input type="hidden" name="sysHousingCd" value="<c:out value="${housingImageInfoForm.sysHousingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${housingImageInfoForm.displayHousingName}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${housingImageInfoForm.housingKindCd}"/>">


		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="20%">物件番号</th>
				<td>
					<c:out value="${housingImageInfoForm.housingCd}"/>
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="80%">物件名称</th>
				<td>
					<c:out value="${housingImageInfoForm.displayHousingName}"/>
				</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="10%">表示順</th>
				<th class="head_tr" width="20%">アップロード画像</th>
				<th class="head_tr" width="15%">種別</th>
				<th class="head_tr" width="15%">閲覧権限</th>
				<th class="head_tr" width="40%">コメント</th>
			</tr>

			<c:forEach begin="0" end="2" varStatus="status" step="1">
			<input type="hidden" name="addHidPath" value="<c:out value="${housingImageInfoForm.addHidPath[status.index]}"/>">
			<tr>
			    <td>
			    	<input type="text" name="addSortOrder" value="<c:out value="${housingImageInfoForm.addSortOrder[status.index]}"/>" size="3" maxlength="3" class="input2 ime-disabled" style="width: 100%; ">
			    </td>
				<td>
			        <input type="file" name="addFilePath" size="4"/>
			    </td>
			    <td>
					<select id="addImageType<c:out value="${status.index}"/>" name="addImageType" style="width: 100%; " onchange="addonch('<c:out value="${status.index}"/>')">
					<option></option>
	                <dm3lookup:lookupForEach lookupName="ImageType">
	                    <option value="<c:out value="${key}"/>" <c:if test="${housingImageInfoForm.addImageType[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
	                </select>
			    </td>
			    <td>
					<select id="addRoleId"  name="addRoleId" style="width: 100%; ">
					<option></option>
	                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
	                    <option value="<c:out value="${key}"/>" <c:if test="${housingImageInfoForm.addRoleId[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
	                </select>
			    </td>
			    <td>
			    	<input type="text" name="addImgComment" value="<c:out value="${housingImageInfoForm.addImgComment[status.index]}"/>" size="20" maxlength="50" class="input2" style="width: 100%; ">
			    </td>
			</tr>
			</c:forEach>
		</table>
</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function addonch(idx){
	  	if($("#addImageType"+idx).val()=="03"){
	  		var roleIdValue = document.all["addRoleId"];
			roleIdValue[idx].options[2].selected="selected";
		}
	}

// -->
</script>

