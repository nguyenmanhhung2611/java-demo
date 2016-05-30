<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
画像アップロード編集確認機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
			<td><c:out value="${housingImageInfoForm.housingCd}"/></td>
		</tr>
		<tr>
			<th class="head_tr" width="80%">物件名称</th>
			<td><c:out value="${housingImageInfoForm.displayHousingName}"/></td>
		</tr>
	</table>
	<br>
	<c:if test="${housingImageInfoForm.command == 'update'}">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="15%">画像</th>
			<th class="head_tr" width="15%">種別</th>
			<th class="head_tr" width="15%">閲覧権限</th>
			<th class="head_tr" width="45%">コメント</th>
		</tr>
		<c:forEach items="${housingImageInfoForm.divNo}" varStatus="status" step="1">
		<tr>
			<td style="text-align:right;">
    			<c:out value="${housingImageInfoForm.sortOrder[status.index]}"/>
    		</td>
			<td>
	    	    <img src="<c:out value="${housingImageInfoForm.hidPathMin[status.index]}"/>" alt="" />
	    	</td>
			<c:if test="${housingImageInfoForm.delFlg[status.index] == 1}">
			<td colspan="3">
				<c:out value="削除"/>
			</td>
			</c:if>
			<c:if test="${housingImageInfoForm.delFlg[status.index] != 1}">
    		<td>
    			<dm3lookup:lookupForEach lookupName="ImageType">
                   <c:if test="${housingImageInfoForm.imageType[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td>
    			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                   <c:if test="${housingImageInfoForm.roleId[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td>
    			<c:out value="${housingImageInfoForm.imgComment[status.index]}"/>&nbsp;
    		</td>
	    	</c:if>
	    </tr>
	    </c:forEach>
	</table>
	</c:if>
	<c:if test="${housingImageInfoForm.command == 'insert'}">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="15%">画像</th>
			<th class="head_tr" width="15%">種別</th>
			<th class="head_tr" width="15%">閲覧権限</th>
			<th class="head_tr" width="45%">コメント</th>
		</tr>
        <c:forEach items="${housingImageInfoForm.addHidPath}" varStatus="status" step="1">
        <c:if test="${housingImageInfoForm.addHidPath[status.index] != null}">
		<tr>
			<td style="text-align:right;">
    			<c:out value="${housingImageInfoForm.addSortOrder[status.index]}"/>
    		</td>
			<td>
				<img src="<c:out value="${housingImageInfoForm.addHidPathMin[status.index]}"/>" alt="" />
	    	</td>
    		<td>
    			<dm3lookup:lookupForEach lookupName="ImageType">
                   <c:if test="${housingImageInfoForm.addImageType[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td>
    			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                   <c:if test="${housingImageInfoForm.addRoleId[status.index] == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
    		</td>
    		<td>
    			<c:out value="${housingImageInfoForm.addImgComment[status.index]}"/>&nbsp;
    		</td>
	    </tr>
	    </c:if>
        </c:forEach>
	</table>
	</c:if>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >

	<input type="hidden" name="pageId" value="housingImage">
	<input type="hidden" name="command" value="<c:out value="${housingImageInfoForm.command}"/>">
	<input type="hidden" name="confirm" value="confirm"/>
	<input type="hidden" name="housingCd" value="<c:out value="${housingImageInfoForm.housingCd}"/>">
    <input type="hidden" name="sysHousingCd" value="<c:out value="${housingImageInfoForm.sysHousingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${housingImageInfoForm.displayHousingName}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${housingImageInfoForm.housingKindCd}"/>">
	<dm3token:oneTimeToken/>

	<c:forEach items="${housingImageInfoForm.addHidPath}" varStatus="status" step="1">
		<c:if test="${housingImageInfoForm.addHidPath[status.index] != null}">
        <input type="hidden" name="addHidPath" value="<c:out value="${housingImageInfoForm.addHidPath[status.index]}"/>">
        <input type="hidden" name="addHidPathMin" value="<c:out value="${housingImageInfoForm.addHidPathMin[status.index]}"/>">
        <input type="hidden" name="addHidFileName" value="<c:out value="${housingImageInfoForm.addHidFileName[status.index]}"/>">
        <input type="hidden" name="addSortOrder" value="<c:out value="${housingImageInfoForm.addSortOrder[status.index]}"/>">
        <input type="hidden" name="addImageType" value="<c:out value="${housingImageInfoForm.addImageType[status.index]}"/>">
        <input type="hidden" name="addRoleId" value="<c:out value="${housingImageInfoForm.addRoleId[status.index]}"/>">
        <input type="hidden" name="addImgComment" value="<c:out value="${housingImageInfoForm.addImgComment[status.index]}"/>">
        </c:if>
    </c:forEach>
	<c:forEach items="${housingImageInfoForm.divNo}" varStatus="status" step="1">
		<input type="hidden" name="divNo" value="<c:out value="${housingImageInfoForm.divNo[status.index]}"/>">
		<input type="hidden" name="delFlg" value="<c:out value="${housingImageInfoForm.delFlg[status.index]}"/>">
		<input type="hidden" name="sortOrder" value="<c:out value="${housingImageInfoForm.sortOrder[status.index]}"/>">
		<input type="hidden" name="imageType" value="<c:out value="${housingImageInfoForm.imageType[status.index]}"/>">
		<input type="hidden" name="roleId" value="<c:out value="${housingImageInfoForm.roleId[status.index]}"/>">
		<input type="hidden" name="imgComment" value="<c:out value="${housingImageInfoForm.imgComment[status.index]}"/>">
		<input type="hidden" name="pathName" value="<c:out value="${housingImageInfoForm.pathName[status.index]}"/>">
		<input type="hidden" name="fileName" value="<c:out value="${housingImageInfoForm.fileName[status.index]}"/>">
		<input type="hidden" name="oldRoleId" value="<c:out value="${housingImageInfoForm.oldRoleId[status.index]}"/>">
		<input type="hidden" name="oldImageType" value="<c:out value="${housingImageInfoForm.oldImageType[status.index]}"/>">
    	<input type="hidden" name="hidPathMax" value="<c:out value="${housingImageInfoForm.hidPathMax[status.index]}"/>">
    	<input type="hidden" name="hidPathMin" value="<c:out value="${housingImageInfoForm.hidPathMin[status.index]}"/>">
	</c:forEach>
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
</form>

