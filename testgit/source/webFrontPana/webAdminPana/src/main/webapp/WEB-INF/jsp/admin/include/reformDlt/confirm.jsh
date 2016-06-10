<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
リフォーム詳細情報編集機能で使用する入力確認画面の出力
--%>

<form action="compConfirm" method="post" name="inputForm">
	<input type="hidden" name="pageId" value="reformDtl">
	<input type="hidden" name="command" value="<c:out value="${reformDtlForm.command}"/>">
	<input type="hidden" name="sysReformCd" value="<c:out value="${reformDtlForm.sysReformCd}"/>">
    <input type="hidden" name="sysHousingCd" value="<c:out value="${reformDtlForm.sysHousingCd}"/>">

	<input type="hidden" name="displayHousingName" value="<c:out value="${reformDtlForm.displayHousingName}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${reformDtlForm.housingCd}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${reformDtlForm.housingKindCd}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
    <c:forEach items="${reformDtlForm.addHidPath}" varStatus="status" step="1">
        <input type="hidden" name="addHidPath" value="<c:out value="${reformDtlForm.addHidPath[status.index]}"/>">
        <input type="hidden" name="addSortOrder" value="<c:out value="${reformDtlForm.addSortOrder[status.index]}"/>">
        <input type="hidden" name="addHidFileName" value="<c:out value="${reformDtlForm.addHidFileName[status.index]}"/>">
        <input type="hidden" name="addRoleId" value="<c:out value="${reformDtlForm.addRoleId[status.index]}"/>">
        <input type="hidden" name="addImgName" value="<c:out value="${reformDtlForm.addImgName[status.index]}"/>">
        <input type="hidden" name="addReformPrice" value="<c:out value="${reformDtlForm.addReformPrice[status.index]}"/>">
    </c:forEach>
	<c:forEach items="${reformDtlForm.divNo}" varStatus="status" step="1">
		<input type="hidden" name="divNo" value="<c:out value="${reformDtlForm.divNo[status.index]}"/>">
		<input type="hidden" name="delFlg" value="<c:out value="${reformDtlForm.delFlg[status.index]}"/>">
		<input type="hidden" name="sortOrder" value="<c:out value="${reformDtlForm.sortOrder[status.index]}"/>">
		<input type="hidden" name="imgName" value="<c:out value="${reformDtlForm.imgName[status.index]}"/>">
		<input type="hidden" name="roleId" value="<c:out value="${reformDtlForm.roleId[status.index]}"/>">
        <input type="hidden" name="oldRoleId" value="<c:out value="${reformDtlForm.oldRoleId[status.index]}"/>">
		<input type="hidden" name="reformPrice" value="<c:out value="${reformDtlForm.reformPrice[status.index]}"/>">
		<input type="hidden" name="pathName" value="<c:out value="${reformDtlForm.pathName[status.index]}"/>">
        <input type="hidden" name="updHidFileName" value="<c:out value="${reformDtlForm.updHidFileName[status.index]}"/>">
        <input type="hidden" name="hidPath" value="<c:out value="${reformDtlForm.hidPath[status.index]}"/>">
	</c:forEach>

    <div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
			<td width="80%"><c:out value="${reformDtlForm.housingCd}"/></td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">物件名称</th>
			<td width="80%"><c:out value="${reformDtlForm.displayHousingName}"/></td>
		</tr>
	</table>
	<br>

	<c:if test="${reformDtlForm.command == 'update'}">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="10%">画像</th>
			<th class="head_tr" width="38%">名称</th>
			<th class="head_tr" width="10%">閲覧権限</th>
			<th class="head_tr" width="15%">価格</th>
		</tr>
		<c:forEach items="${reformDtlForm.divNo}" varStatus="status" step="1">

		<tr>
			<td width="10%" style="text-align:right;">
    			<c:out value="${reformDtlForm.sortOrder[status.index]}"/>
    		</td>
			<td  width="10%">
	    	    <p><a href="<c:out value="${reformDtlForm.hidPath[status.index]}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a></p>
	    	</td>
			<c:if test="${reformDtlForm.delFlg[status.index] == 1}">
			<td colspan="3"  width="90%">
				<c:out value="削除"/>
			</td>
			</c:if>
			<c:if test="${reformDtlForm.delFlg[status.index] != 1}">
    		<td width="38%">
    			<c:out value="${reformDtlForm.imgName[status.index]}"/>&nbsp;
    		</td>
    		<td width="10%">
    			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                   <c:if test="${reformDtlForm.roleId[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td width="15%" style="text-align:right;">
    			<c:if test="${!empty reformDtlForm.reformPrice[status.index]}"><fmt:formatNumber value="${reformDtlForm.reformPrice[status.index]}" pattern="###,###" />円</c:if>&nbsp;
    		</td>
	    	</c:if>
	    </tr>
	    </c:forEach>
	</table>
	</c:if>
	<c:if test="${reformDtlForm.command == 'insert'}">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="10%">画像</th>
			<th class="head_tr" width="38%">名称</th>
			<th class="head_tr" width="10%">閲覧権限</th>
			<th class="head_tr" width="15%">価格</th>
		</tr>
        <c:forEach items="${reformDtlForm.addHidPath}" varStatus="status" step="1">
        <c:if test="${reformDtlForm.addHidPath[status.index] != null}">
		<tr>
			<td width="10%" style="text-align:right;">
    			<c:out value="${reformDtlForm.addSortOrder[status.index]}"/>
    		</td>
			<td width="10%">
				<p><a href="<c:out value="${reformDtlForm.addHidPath[status.index]}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a></p>
	    	</td>
    		<td width="38%">
    			<c:out value="${reformDtlForm.addImgName[status.index]}"/>&nbsp;
    		</td>
    		<td width="10%">
    			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                   <c:if test="${reformDtlForm.addRoleId[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td width="15%" style="text-align:right;">
    			<c:if test="${!empty reformDtlForm.addReformPrice[status.index]}"><fmt:formatNumber value="${reformDtlForm.addReformPrice[status.index]}" pattern="###,###" />円</c:if>&nbsp;
    		</td>
	    </tr>
	    </c:if>
        </c:forEach>
	</table>
	</c:if>

</div>
</form>

