<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript">
$(function(){
    $("#demo1").fancybox();
});

</script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />

<!--flexBlockA01 -->
<div class="flexBlockA01">
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
				<th class="head_tr" width="15%">画像</th>
				<th class="head_tr" width="15%">種別</th>
				<th class="head_tr" width="15%">閲覧権限</th>
				<th class="head_tr" width="35%">コメント</th>
				<th class="head_tr" width="10%">削除</th>
			</tr>
			<c:forEach items="${housingImageInfoForm.divNo}" varStatus="status" step="1">
			<input type="hidden" name="divNo" value="<c:out value="${housingImageInfoForm.divNo[status.index]}"/>">
        	<input type="hidden" name="oldRoleId" value="<c:out value="${housingImageInfoForm.oldRoleId[status.index]}"/>">
        	<input type="hidden" name="oldImageType" value="<c:out value="${housingImageInfoForm.oldImageType[status.index]}"/>">
        	<input type="hidden" name="pathName" value="<c:out value="${housingImageInfoForm.pathName[status.index]}"/>">
        	<input type="hidden" name="fileName" value="<c:out value="${housingImageInfoForm.fileName[status.index]}"/>">
	    	<input type="hidden" name="hidPathMax" value="<c:out value="${housingImageInfoForm.hidPathMax[status.index]}"/>">
	    	<input type="hidden" name="hidPathMin" value="<c:out value="${housingImageInfoForm.hidPathMin[status.index]}"/>">
			<tr>
		    	<td>
		    		<input type="text" name="sortOrder" value="<c:out value="${housingImageInfoForm.sortOrder[status.index]}"/>" size="3" maxlength="3" class="input2 ime-disabled">
		    	</td>
				<td align="center">
		    	    <p><a id="demo1" href="<c:out value="${housingImageInfoForm.hidPathMax[status.index]}"/>">
                    <img src="<c:out value="${housingImageInfoForm.hidPathMin[status.index]}"/>" alt="" />
	                </a></p>
		    	</td>
		    	<td>
					<select id="imageType<c:out value="${status.index}"/>" name="imageType" style="width: 100%; " onchange="onch('<c:out value="${status.index}"/>')">
	                <dm3lookup:lookupForEach lookupName="ImageType">
	                    <option value="<c:out value="${key}"/>" <c:if test="${housingImageInfoForm.imageType[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
	                </select>
		    	</td>
		    	<td>
					<select id="roleId" name="roleId" style="width: 100%; ">
	                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
	                    <option value="<c:out value="${key}"/>" <c:if test="${housingImageInfoForm.roleId[status.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
	                </select>
		    	</td>
		    	<td>
		    		<input type="text" name="imgComment" value="<c:out value="${housingImageInfoForm.imgComment[status.index]}"/>" size="15" maxlength="50" class="input2" style="width: 100%; ">
		    	</td>
		    	<td>
		    		<input type="checkbox" name="delFlg" value="<c:out value="${status.index}"/>" <c:if test="${housingImageInfoForm.delFlg[status.index] == 1}"> checked="true"</c:if>>
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

	function onch(idx){
	  	if($("#imageType"+idx).val()=="03"){
	  		var roleIdValue = document.all["roleId"];
			roleIdValue[idx].options[1].selected="selected";
		}
	}

// -->
</script>
