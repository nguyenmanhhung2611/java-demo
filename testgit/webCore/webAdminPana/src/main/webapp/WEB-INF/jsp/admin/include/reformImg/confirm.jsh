<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
リフォーム画像アップロード確認機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
			<td width="80%"><c:out value="${ReformImgForm.housingCd}"/></td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">物件名称</th>
			<td width="80%"><c:out value="${ReformImgForm.displayHousingName}"/></td>
		</tr>
	</table>
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="10%">表示順</th>
			<th class="head_tr" width="39%">Before</th>
			<th class="head_tr" width="39%">After</th>
			<th class="head_tr" width="12%">閲覧権限</th>
		</tr>

	<c:if test="${ReformImgForm.command == 'update'}">
	<c:forEach items="${ReformImgForm.divNo}"  varStatus="EditItem">
		<c:if test="${ReformImgForm.delFlg[EditItem.index] == 1}">
			<tr>
				<td width="10%" style="text-align:right" >
		    		<c:out value="${ReformImgForm.editSortOrder[EditItem.index]}"/>
		    	</td>
				<td colspan="3" width="90%">
					<c:out value="削除"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${ReformImgForm.delFlg[EditItem.index] != 1}">
			<tr>
				<td width="10%" style="text-align:right" >
		    		<c:out value="${ReformImgForm.editSortOrder[EditItem.index]}"/>
		    	</td>
				<td width="39%">
					<div style="width:35%;float:left" >
		    		<img src="<c:out value="${ReformImgForm.beforeHidPathMin[EditItem.index]}"/>" alt=""  />
					</div>
					<div  style="width:65%;float:left;" >
		    		<c:out value="${ReformImgForm.editBeforeComment[EditItem.index]}"/>
					</div>
	    		</td>
	    		<td width="39%">
					<div style="width:35%;float:left" >
		    		<img src="<c:out value="${ReformImgForm.afterHidPathMin[EditItem.index]}"/>" alt=""  />
					</div>
					<div  style="width:65%;float:left" >
		    		<c:out value="${ReformImgForm.editAfterComment[EditItem.index]}"/>
					</div>
	    		</td>
	    		<td width="12%">
                    <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                        <c:if test="${ReformImgForm.editRoleId[EditItem.index] == key}"><c:out value="${value}"/> </c:if>
					</dm3lookup:lookupForEach>
	    		</td>
    		</tr>
		</c:if>
    </c:forEach>
	</c:if>

	<c:if test="${ReformImgForm.command == 'insert'}">
	<tr>
		<td width="10%">
    		<c:out value="${ReformImgForm.uploadSortOrder[0]}"/>
    	</td>
		<td width="39%">
			<div style="width:35%;float:left" >
    		<img src="<c:out value="${ReformImgForm.uploadBeforeHidPathMin[0]}"/>" />
			</div>
			<div  style="width:65%;float:left;" >
    		<c:out value="${ReformImgForm.uploadBeforeComment[0]}"/>
			</div>
		</td>
		<td width="39%">
			<div style="width:35%;float:left" >
    		<img src="<c:out value="${ReformImgForm.uploadAfterHidPathMin[0]}"/>" />
			</div>
			<div  style="width:65%;float:left;" >
    		<c:out value="${ReformImgForm.uploadAfterComment[0]}"/>
			</div>
		</td>
		<td width="12%">
            <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                <c:if test="${ReformImgForm.uploadRoleId[0] == key}"><c:out value="${value}"/> </c:if>
			</dm3lookup:lookupForEach>
		</td>
	</tr>
	</c:if>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form action="commit" method="post" name="inputForm">
	<input type="hidden" name="pageId" value="reformImg">
	<input type="hidden" name="command" value="<c:out value="${ReformImgForm.command}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${ReformImgForm.sysHousingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${ReformImgForm.displayHousingName}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${ReformImgForm.housingCd}"/>">
	<input type="hidden" name="sysReformCd" value="<c:out value="${ReformImgForm.sysReformCd}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${ReformImgForm.housingKindCd}"/>">

	<input type="hidden" name="uploadSortOrder" value="<c:out value="${ReformImgForm.uploadSortOrder[0]}"/>">
	<input type="hidden" name="uploadBeforeComment" value="<c:out value="${ReformImgForm.uploadBeforeComment[0]}"/>">
	<input type="hidden" name="uploadBeforeHidPath" value="<c:out value="${ReformImgForm.uploadBeforeHidPath[0]}"/>">
	<input type="hidden" name="uploadBeforeHidPathMin" value="<c:out value="${ReformImgForm.uploadBeforeHidPathMin[0]}"/>">
	<input type="hidden" name="uploadBeforeFileName" value="<c:out value="${ReformImgForm.uploadBeforeFileName[0]}"/>">
	<input type="hidden" name="uploadAfterComment" value="<c:out value="${ReformImgForm.uploadAfterComment[0]}"/>">
	<input type="hidden" name="uploadAfterHidPath" value="<c:out value="${ReformImgForm.uploadAfterHidPath[0]}"/>">
	<input type="hidden" name="uploadAfterHidPathMin" value="<c:out value="${ReformImgForm.uploadAfterHidPathMin[0]}"/>">
	<input type="hidden" name="uploadAfterFileName" value="<c:out value="${ReformImgForm.uploadAfterFileName[0]}"/>">
	<input type="hidden" name="uploadRoleId" value="<c:out value="${ReformImgForm.uploadRoleId[0]}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	<c:forEach items="${ReformImgForm.divNo}"  varStatus="EditItem">
		<input type="hidden" name="editSortOrder" value="<c:out value="${ReformImgForm.editSortOrder[EditItem.index]}"/>">
		<input type="hidden" name="editBeforeComment" value="<c:out value="${ReformImgForm.editBeforeComment[EditItem.index]}"/>">
		<input type="hidden" name="editBeforePathName" value="<c:out value="${ReformImgForm.editBeforePathName[EditItem.index]}"/>">
		<input type="hidden" name="editBeforeFileName" value="<c:out value="${ReformImgForm.editBeforeFileName[EditItem.index]}"/>">
		<input type="hidden" name="editAfterPathName" value="<c:out value="${ReformImgForm.editAfterPathName[EditItem.index]}"/>">
		<input type="hidden" name="editAfterFileName" value="<c:out value="${ReformImgForm.editAfterFileName[EditItem.index]}"/>">
		<input type="hidden" name="editAfterComment" value="<c:out value="${ReformImgForm.editAfterComment[EditItem.index]}"/>">
		<input type="hidden" name="editRoleId" value="<c:out value="${ReformImgForm.editRoleId[EditItem.index]}"/>">
		<input type="hidden" name="divNo" value="<c:out value="${ReformImgForm.divNo[EditItem.index]}"/>">
		<input type="hidden" name="delFlg" value="<c:out value="${ReformImgForm.delFlg[EditItem.index]}"/>">
		<input type="hidden" name="beforeHidPathMax" value="<c:out value="${ReformImgForm.beforeHidPathMax[EditItem.index]}"/>">
		<input type="hidden" name="beforeHidPathMin" value="<c:out value="${ReformImgForm.beforeHidPathMin[EditItem.index]}"/>">
		<input type="hidden" name="afterHidPathMax" value="<c:out value="${ReformImgForm.afterHidPathMax[EditItem.index]}"/>">
		<input type="hidden" name="afterHidPathMin" value="<c:out value="${ReformImgForm.afterHidPathMin[EditItem.index]}"/>">
		<input type="hidden" name="editOldRoleId" value="<c:out value="${ReformImgForm.editOldRoleId[EditItem.index]}"/>">
	</c:forEach>
</form>
