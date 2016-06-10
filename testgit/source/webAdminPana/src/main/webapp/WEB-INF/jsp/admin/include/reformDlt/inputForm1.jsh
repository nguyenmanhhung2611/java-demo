<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム詳細アップロード機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<div class="flexBlockA01">
		<input type="hidden" name="command" value="insert" />
		<input type="hidden" name="sysReformCd" value="<c:out value="${reformDtlForm.sysReformCd}"/>">
        <input type="hidden" name="sysHousingCd" value="<c:out value="${reformDtlForm.sysHousingCd}"/>">

		<input type="hidden" name="housingCd" value="<c:out value="${housingInfo.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${housingInfo.displayHousingName}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${reformDtlForm.housingKindCd}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr"  width="20%">物件番号</th>
				<td colspan="3" width="80%">
					<c:out value="${housingInfo.housingCd}"/>
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">物件名称</th>
				<td colspan="3" width="80%">
					<c:out value="${housingInfo.displayHousingName}"/>
				</td>
			</tr>
		</table>

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" style="width: 11%; ">表示順</th>
				<th class="head_tr" style="width: 30%; ">アップロード画像</th>
				<th class="head_tr" style="width: 28%; ">名称</th>
				<th class="head_tr" style="width: 13%; ">閲覧権限</th>
				<th class="head_tr" style="width: 18%; ">価格</th>
			</tr>
        <c:forEach begin="0" end="2" varStatus="status" step="1">
        <input type="hidden" name="addHidPath" value="<c:out value="${reformDtlForm.addHidPath[status.index]}"/>">
			<tr>
			    <td>
			    	<input type="text" name="addSortOrder" value="<c:out value="${reformDtlForm.addSortOrder[status.index]}"/>" size="30" maxlength="3" class="input2 ime-disabled" style="width: 100%; text-align:right">
			    </td>
				<td>
			        <input type="file" name="addFilePath" class="input2" style="width: 100%;" >
                </td>
			    <td>
			    	<input type="text" name="addImgName" value="<c:out value="${reformDtlForm.addImgName[status.index]}"/>" size="180" maxlength="20" class="input2" style="width: 100%; ">
			    </td>
			    <td>
	    		<select name="addRoleId" style="width: 100%; ">
	    		<option></option>
                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                    <option value="<c:out value="${key}"/>" <c:if test="${reformDtlForm.addRoleId[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
				</dm3lookup:lookupForEach>
                </select>
	    		</td>
			    <td>
			    	<input type="text" name="addReformPrice" value="<c:out value="${reformDtlForm.addReformPrice[status.index]}"/>" size="50" maxlength="7" class="input2 ime-disabled" style="width: 75%; text-align:right">円
			    </td>
			</tr>
        </c:forEach>
		</table>
</div>
