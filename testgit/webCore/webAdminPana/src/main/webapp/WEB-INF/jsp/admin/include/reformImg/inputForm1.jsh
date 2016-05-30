<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム画像アップロード機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
			document.inputForm.action = url;
			document.inputForm.command.value = cmd;
			document.inputForm.submit();
		}
	function linkToUrlBack(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.enctype="application/x-www-form-urlencoded";
		document.inputForm.encoding="application/x-www-form-urlencoded";
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">

<input type="hidden" name="command" value="" />
<input type="hidden" name="sysHousingCd" value="<c:out value="${ReformImgForm.sysHousingCd}"/>">
<input type="hidden" name="housingCd" value="<c:out value="${housingInfo.housingCd}"/>">
<input type="hidden" name="displayHousingName" value="<c:out value="${housingInfo.displayHousingName}"/>">

<input type="hidden" name="sysReformCd" value="<c:out value="${ReformImgForm.sysReformCd}"/>">
<input type="hidden" name="housingKindCd" value="<c:out value="${ReformImgForm.housingKindCd}"/>">
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
	<tr>
		<th class="head_tr" width="10%">表示順</th>
		<th class="head_tr" width="14%">閲覧権限</th>
		<th class="head_tr" width="38%">Before</th>
		<th class="head_tr" width="38%">After</th>
	</tr>
	<tr>
	    <td width="10%">
	    	<input type="text" name="uploadSortOrder" style="text-align:right" value="<c:out value="${ReformImgForm.uploadSortOrder[0]}"/>" maxlength="3" class="input2 ime-disabled" size="3" >
	    </td>
	    <td width="12%">
    		<select name="uploadRoleId" style="width: 100%; ">
    			<option></option>
                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
                    <option value="<c:out value="${key}"/>" <c:if test="${ReformImgForm.uploadRoleId[0] == key}"> selected</c:if>><c:out value="${value}"/></option>
				</dm3lookup:lookupForEach>
            </select>
    	</td>
		<td width="39%">
	        <input type="file" name="uploadBeforePathName" value="" style="width: 100%; "class="input2">
	        コメント&nbsp;
			<input type="text" name="uploadBeforeComment" style="width: 190px; " value="<c:out value="${ReformImgForm.uploadBeforeComment[0]}"/>" maxlength="50" class="input2">
	    </td>
		<td width="39%">
	        <input type="file" name="uploadAfterPathName"  style="width: 100%; " value="" class="input2">
	        コメント&nbsp;
			<input type="text" name="uploadAfterComment" style="width: 190px; " value="<c:out value="${ReformImgForm.uploadAfterComment[0]}"/>" maxlength="50" class="input2">
	    </td>

	</tr>
</table>

</div>
<!--/flexBlockA01 -->
