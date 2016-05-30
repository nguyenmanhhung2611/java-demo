<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム画像編集機能で使用する入力フォームの出力
--%>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript">
$(function(){
    $("#demo1").fancybox();
     $("#demo2").fancybox();
});
</script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" style="width: 10%; ">表示順</th>
			<th class="head_tr" style="width: 35%; ">Before</th>
			<th class="head_tr" style="width: 35%; ">After</th>
			<th class="head_tr" style="width: 13%; ">閲覧権限</th>
			<th class="head_tr" style="width: 7%; ">削除</th>
		</tr>

		<c:forEach items="${ReformImgForm.divNo}" varStatus="EditItem">
		<input type="hidden" name="divNo" value="<c:out value="${ReformImgForm.divNo[EditItem.index]}"/>">
		<input type="hidden" name="editBeforePathName" value="<c:out value="${ReformImgForm.editBeforePathName[EditItem.index]}"/>">
		<input type="hidden" name="editBeforeFileName" value="<c:out value="${ReformImgForm.editBeforeFileName[EditItem.index]}"/>">
		<input type="hidden" name="editAfterPathName" value="<c:out value="${ReformImgForm.editAfterPathName[EditItem.index]}"/>">
		<input type="hidden" name="editAfterFileName" value="<c:out value="${ReformImgForm.editAfterFileName[EditItem.index]}"/>">

		<input type="hidden" name="beforeHidPathMax" value="<c:out value="${ReformImgForm.beforeHidPathMax[EditItem.index]}"/>">
		<input type="hidden" name="beforeHidPathMin" value="<c:out value="${ReformImgForm.beforeHidPathMin[EditItem.index]}"/>">
		<input type="hidden" name="afterHidPathMax" value="<c:out value="${ReformImgForm.afterHidPathMax[EditItem.index]}"/>">
		<input type="hidden" name="afterHidPathMin" value="<c:out value="${ReformImgForm.afterHidPathMin[EditItem.index]}"/>">
		<input type="hidden" name="editOldRoleId" value="<c:out value="${ReformImgForm.editOldRoleId[EditItem.index]}"/>">
			<tr>
		    	<td>
		    		<input type="text" name="editSortOrder" style="text-align:right" value="<c:out value="${ReformImgForm.editSortOrder[EditItem.index]}"/>" size="3" maxlength="3" class="input2 ime-disabled">
		    	</td>
		    	<td>
			    	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA4">
			    	<tr>
				    	<td>
				    		<p><a id="demo1" href="<c:out value="${ReformImgForm.beforeHidPathMax[EditItem.index]}"/>">
			                <img src="<c:out value="${ReformImgForm.beforeHidPathMin[EditItem.index]}"/>" alt="" />
			                </a>
				    	    </p>
				    	</td>
				    	<td style='text-align:right; width: 65%;'>
				    		<input type="text" name="editBeforeComment" value="<c:out value="${ReformImgForm.editBeforeComment[EditItem.index]}"/>"  maxlength="50" class="input2"  style="width: 95%; height:100%; ">
				    	</td>
		    		</tr>
					</table>
		    	</td>
		    	<td>
		    	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA4">
			    	<tr>
				    	<td>
			                <p><a id="demo2" href="<c:out value="${ReformImgForm.afterHidPathMax[EditItem.index]}"/>">
		                    <img src="<c:out value="${ReformImgForm.afterHidPathMin[EditItem.index]}"/>" alt="" />
			                </a>
				    	    </p>
			    	    </td>
					    <td style='text-align:right; width: 65%;'>
			    	    	<input type="text" name="editAfterComment" value="<c:out value="${ReformImgForm.editAfterComment[EditItem.index]}"/>"  maxlength="50" class="input2"  style="width: 95%; height:100%; ">
			    	    </td>
		    		</tr>
					</table>
		    	</td>
		    	<td>
		    		<select name="editRoleId" style="width: 100%; ">
	                    <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
	                        <option value="<c:out value="${key}"/>" <c:if test="${ReformImgForm.editRoleId[EditItem.index] == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
                    </select>
		    	</td>
		    	<td>
		    		<input type="checkbox" name="delFlg" value="<c:out value="${EditItem.index}"/>" <c:if test="${ReformImgForm.delFlg[EditItem.index] == 1}"> checked="true"</c:if>>
		    	</td>
	    	</tr>
	    </c:forEach>
	</table>

</div>
<!--/flexBlockA01 -->

