<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
住宅診断編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="updConfirm" method="post" enctype="multipart/form-data" name="HousingInspectionForm">
		<input type="hidden" name="command" value="update">
		<input type="hidden" name="housingCd" value="<c:out value="${HousingInspectionForm.housingCd}"/>">
        <input type="hidden" name="sysHousingCd" value="<c:out value="${HousingInspectionForm.sysHousingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${HousingInspectionForm.displayHousingName}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${HousingInspectionForm.housingKindCd}"/>">

		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="24%">物件番号</th>
				<td>
					<c:out value="${HousingInspectionForm.housingCd}"/>
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td>
					<c:out value="${HousingInspectionForm.displayHousingName}"/>
				</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="24%" class="head_tr"/>
				<col width="26%"/>
				<col width="24%"/>
				<col width="26%"/>
			</colgroup>
			<tr>
				<th class="head_tr">住宅診断実施有無</th>
				<td colspan="3">
				<dm3lookup:lookupForEach lookupName="housingInspection">
					<label>
					<input type="radio" name="housingInspection" value="<c:out value="${key}"/>"
					<c:if test="${HousingInspectionForm.housingInspection == key}">checked</c:if>><c:out value="${value}"/>
					</label>
				</dm3lookup:lookupForEach>
				</td>
			</tr>
			<c:set var="index" value="0" />
			<c:if test="${HousingInspectionForm.housingKindCd == '01'}">
				<dm3lookup:lookupForEach lookupName="inspectionTrustMansion" >
					<c:set var="indexKey" value="${key}"/>
					<tr>
	                     <th class="head_tr">
							<c:out value="${value}　評価基準"/>
							<input type="hidden" name="inspectionKey" value="<c:out value="${key}"/>">
						 </th>
						 <td>
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
								<tr>
									<td>
									<c:set var="checkFlg" value="false" />
									<c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
										<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
											<c:set var="checkFlg" value="true" />
										</c:if>
									</c:forEach>

									<c:if test="${checkFlg == 'false'}">
										<input type="hidden" name="inspectionValue_label" value=""/>
										<dm3lookup:lookupForEach lookupName="inspectionResult">
										<label><input type="radio" name="<c:out value="${'inspectionValue_label'}${indexKey}"/>" value="<c:out value="${key}"/>"
								           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
							            </dm3lookup:lookupForEach>
									</c:if>
									<c:if test="${checkFlg == 'true'}">
										<input type="hidden" name="inspectionValue_label" value="<c:out value="${HousingInspectionForm.inspectionValue_label[index]}"/>"/>
										<dm3lookup:lookupForEach lookupName="inspectionResult">
										<label><input type="radio" name="<c:out value="${'inspectionValue_label'}${indexKey}"/>"  value="<c:out value="${key}"/>"
								           <c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
										   	<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
								              	<c:if test="${HousingInspectionForm.inspectionValue_label[index] == key}">checked</c:if>
								           	</c:if>
										   </c:forEach>
								           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
							            </dm3lookup:lookupForEach>
									</c:if>

									</td>
								</tr>
							</table>
						</td>
						<th class="head_tr">
							<c:out value="${value}　確認範囲"/>
						</th>
						<td>
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
								<tr>
									<td>
										<c:set var="checkFlg" value="false" />
										<c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
											<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
												<c:set var="checkFlg" value="true" />
											</c:if>
										</c:forEach>
										<c:if test="${checkFlg == 'false'}">
											<input type="hidden" name="inspectionTrust_result" value=""/>
											<dm3lookup:lookupForEach lookupName="inspectionLabel">
											<label><input type="radio" name="<c:out value="${'inspectionTrust_result'}${indexKey}"/>" value="<c:out value="${key}"/>"
									           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
								            </dm3lookup:lookupForEach>
								            <c:set var="index" value="${index-1}" />
										</c:if>
										<c:if test="${checkFlg == 'true'}">
											<input type="hidden" name="inspectionTrust_result" value="<c:out value="${HousingInspectionForm.inspectionTrust_result[index]}"/>"/>
											<dm3lookup:lookupForEach lookupName="inspectionLabel">
											<label><input type="radio" name="<c:out value="${'inspectionTrust_result'}${indexKey}"/>" value="<c:out value="${key}"/>"
							                   		<c:if test="${HousingInspectionForm.inspectionTrust_result[index] == key}">checked</c:if>
									           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
								            </dm3lookup:lookupForEach>
										</c:if>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<c:set var="index" value="${index +1 }" />
				</dm3lookup:lookupForEach>
			</c:if>
			<c:if test="${HousingInspectionForm.housingKindCd == '02'}">
				<dm3lookup:lookupForEach lookupName="inspectionTrustHouse" >
					<c:set var="indexKey" value="${key}"/>
					<tr>
	                     <th class="head_tr">
							<c:out value="${value}　評価基準"/>
							<input type="hidden" name="inspectionKey" value="<c:out value="${key}"/>"/>
						 </th>
						 <td>
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
								<tr>
									<td>

									<c:set var="checkFlg" value="false" />
									<c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
										<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
											<c:set var="checkFlg" value="true" />
										</c:if>
									</c:forEach>

									<c:if test="${checkFlg == 'false'}">
										<input type="hidden" name="inspectionValue_label" value=""/>
										<dm3lookup:lookupForEach lookupName="inspectionResult">
										<label><input type="radio" name="<c:out value="${'inspectionValue_label'}${indexKey}"/>" value="<c:out value="${key}"/>"
								           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
							            </dm3lookup:lookupForEach>
									</c:if>
									<c:if test="${checkFlg == 'true'}">
										<input type="hidden" name="inspectionValue_label" value="<c:out value="${HousingInspectionForm.inspectionValue_label[index]}"/>"/>
										<dm3lookup:lookupForEach lookupName="inspectionResult">
								           <label><input type="radio" name="<c:out value="${'inspectionValue_label'}${indexKey}"/>"  value="<c:out value="${key}"/>"
								           <c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
										   	<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
								              	<c:if test="${HousingInspectionForm.inspectionValue_label[index] == key}">checked</c:if>
								           	</c:if>
										   </c:forEach>
								           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>
							            </dm3lookup:lookupForEach>
									</c:if>

									</td>
								</tr>
							</table>
						</td>
						<th class="head_tr">
							<c:out value="${value}　確認範囲"/>
						</th>
						<td>
							<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
								<tr>
									<td>
										<c:set var="checkFlg" value="false" />
										<c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="EditItem">
											<c:if test="${HousingInspectionForm.inspectionKey[EditItem.index] == indexKey}">
												<c:set var="checkFlg" value="true" />
											</c:if>
										</c:forEach>
										<c:if test="${checkFlg == 'false'}">
											<input type="hidden" name="inspectionTrust_result" value=""/>
											<dm3lookup:lookupForEach lookupName="inspectionLabel">
											<label><input type="radio" name="<c:out value="${'inspectionTrust_result'}${indexKey}"/>" value="<c:out value="${key}"/>"
									           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>

								            </dm3lookup:lookupForEach>
								            <c:set var="index" value="${index-1}" />
										</c:if>
										<c:if test="${checkFlg == 'true'}">
											<input type="hidden" name="inspectionTrust_result" value="<c:out value="${HousingInspectionForm.inspectionTrust_result[index]}"/>"/>
											<dm3lookup:lookupForEach lookupName="inspectionLabel">
											<label><input type="radio" name="<c:out value="${'inspectionTrust_result'}${indexKey}"/>" value="<c:out value="${key}"/>"
						                   		<c:if test="${HousingInspectionForm.inspectionTrust_result[index] == key}">checked</c:if>
									           onclick="clickInspectionTrustRadio(this);"><c:out value="${value}"/></label>

								            </dm3lookup:lookupForEach>
										</c:if>
									</td>
								</tr>
							</table>
						</td>

					</tr>
					<c:set var="index" value="${index +1 }" />
				</dm3lookup:lookupForEach>
			</c:if>
			<input type="hidden" name="index" value="<c:out value="${index}"/>"/>
			<tr>
				<th class="head_tr">レーダーチャート画像</th>
				<td colspan="3">
					<c:if test="${HousingInspectionForm.imgFile != null && HousingInspectionForm.imgFile != ''}">
						<a id="demo2" href="<c:out value="${HousingInspectionForm.hidImgPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" alt="" width="40" height="35" style="vertical-align:middle;"/></a>
						<label><input type="checkBox" name="housingImgDel"
							<c:if test="${HousingInspectionForm.housingImgDel == 'on'}">checked="true"</c:if>/>削除する</label>

					</c:if>
					<input type="file" name="housingImgFile" class="input2">
					<input type="hidden" name="imgFile" value="<c:out value="${HousingInspectionForm.imgFile}"/>">
					<input type="hidden" name="imgFilePath" value="<c:out value="${HousingInspectionForm.imgFilePath}"/>">
					<input type="hidden" name="hidImgPath" value="<c:out value="${HousingInspectionForm.hidImgPath}"/>">
				</td>
			</tr>
			<tr>
				<th class="head_tr">住宅診断ファイル</th>
				<td colspan="3">
					<c:if test="${HousingInspectionForm.loadFile != null && HousingInspectionForm.loadFile != ''}">
						<a href="<c:out value="${HousingInspectionForm.hidPath}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" style="vertical-align:middle;"/></a>
						<label><input type="checkBox" name="housingDel"
							<c:if test="${HousingInspectionForm.housingDel == 'on'}">checked="true"</c:if>/>削除する</label>

					</c:if>
					<input type="file" name="housingFile" class="input2">
					<input type="hidden" name="loadFile" value="<c:out value="${HousingInspectionForm.loadFile}"/>">
					<input type="hidden" name="loadFilePath" value="<c:out value="${HousingInspectionForm.loadFilePath}"/>">
					<input type="hidden" name="hidPath" value="<c:out value="${HousingInspectionForm.hidPath}"/>">
				</td>
			</tr>

		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.HousingInspectionForm.action = url;
		document.HousingInspectionForm.command.value = cmd;
		document.HousingInspectionForm.submit();
	}
	$(function(){
	    $("#demo2").fancybox();
	});


// -->

	/**
	 *
	 *
	 */
	function clickInspectionTrustRadio(obj) {
		obj.parentNode.parentNode.parentNode.parentNode.rows[0].cells[0].childNodes[0].value = obj.value;
	}


</script>
