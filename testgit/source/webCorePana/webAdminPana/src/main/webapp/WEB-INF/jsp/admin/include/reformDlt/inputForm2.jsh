<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム詳細アップロード機能で使用する入力フォームの出力
--%>

<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
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
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="10%">画像</th>
			<th class="head_tr" width="38%">名称</th>
			<th class="head_tr" width="13%">閲覧権限</th>
			<th class="head_tr" width="22%">価格</th>
			<th class="head_tr" width="12%">削除</th>
		</tr>
		<c:forEach items="${reformDtlForm.divNo}" varStatus="status" step="1">
		<input type="hidden" name="divNo" value="<c:out value="${reformDtlForm.divNo[status.index]}"/>">
        <input type="hidden" name="oldRoleId" value="<c:out value="${reformDtlForm.oldRoleId[status.index]}"/>">
        <input type="hidden" name="pathName" value="<c:out value="${reformDtlForm.pathName[status.index]}"/>">
        <input type="hidden" name="updHidFileName" value="<c:out value="${reformDtlForm.updHidFileName[status.index]}"/>">
        <input type="hidden" name="hidPath" value="<c:out value="${reformDtlForm.hidPath[status.index]}"/>">
		<tr>
	    	<td>
	    		<input type="text" name="sortOrder" value="<c:out value="${reformDtlForm.sortOrder[status.index]}"/>" size="2" maxlength="3" class="input2 ime-disabled" style="text-align:right;" >
	    	</td>
			<td>
                <p><a href="<c:out value="${reformDtlForm.hidPath[status.index]}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a></p>
	    	</td>
	    	<td>
	    		<input type="text" name="imgName" value="<c:out value="${reformDtlForm.imgName[status.index]}"/>" size="20" maxlength="20" class="input2">
	    	</td>
	    	<td>
	    		<select name="roleId" style="width: 100%; ">
                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                    <option value="<c:out value="${key}"/>" <c:if test="${reformDtlForm.roleId[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
				</dm3lookup:lookupForEach>s
                </select>
	    	</td>
	    	<td>
	    		<input type="text" name="reformPrice" value="<c:out value="${reformDtlForm.reformPrice[status.index]}"/>" size="8" maxlength="7" class="input2 ime-disabled" style="text-align:right;">円
	    	</td>
	    	<td>
    	    	<input type="checkbox" name="delFlg" value="<c:out value="${status.index}"/>" <c:if test="${reformDtlForm.delFlg[status.index] == 1}"> checked="true"</c:if>>
	    	</td>
	    </tr>
	    </c:forEach>
	</table>

</div>


