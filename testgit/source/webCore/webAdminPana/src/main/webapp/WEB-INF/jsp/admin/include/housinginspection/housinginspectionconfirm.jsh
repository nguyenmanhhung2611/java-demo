<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
住宅診断編集確認画面で使用する入力確認画面の出力
--%>
<%-- ユーザ編集入力formパラメータ引き継ぎ --%>

	<input type="hidden" name="pageId" value="housingInspection">
	<input type="hidden" name="command" value="<c:out value="${HousingInspectionForm.command}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${HousingInspectionForm.sysHousingCd}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${HousingInspectionForm.housingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${HousingInspectionForm.displayHousingName}"/>">
	<input type="hidden" name="housingInspection" value="<c:out value="${HousingInspectionForm.housingInspection}"/>">
	<input type="hidden" name="housingFile" value="<c:out value="${HousingInspectionForm.housingFile}"/>">
	<input type="hidden" name="loadFlg" value="<c:out value="${HousingInspectionForm.loadFlg}"/>">
	<input type="hidden" name="imgFlg" value="<c:out value="${HousingInspectionForm.imgFlg}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${HousingInspectionForm.housingKindCd}"/>">
	<input type="hidden" name="loadFile" value="<c:out value="${HousingInspectionForm.loadFile}"/>">
	<input type="hidden" name="imgFile" value="<c:out value="${HousingInspectionForm.imgFile}"/>">
	<input type="hidden" name="loadFilePath" value="<c:out value="${HousingInspectionForm.loadFilePath}"/>">
	<input type="hidden" name="imgFilePath" value="<c:out value="${HousingInspectionForm.imgFilePath}"/>">
	<input type="hidden" name="housingDel" value="<c:out value="${HousingInspectionForm.housingDel}"/>">
	<input type="hidden" name="housingImgDel" value="<c:out value="${HousingInspectionForm.housingImgDel}"/>">
	<input type="hidden" name="hidPath" value="<c:out value="${HousingInspectionForm.hidPath}"/>">
	<input type="hidden" name="hidImgPath" value="<c:out value="${HousingInspectionForm.hidImgPath}"/>">
	<input type="hidden" name="addHidFileName" value="<c:out value="${HousingInspectionForm.addHidFileName}"/>">
	<input type="hidden" name="addHidImgName" value="<c:out value="${HousingInspectionForm.addHidImgName}"/>">
	<input type="hidden" name="hidNewPath" value="<c:out value="${HousingInspectionForm.hidNewPath}"/>">
	<input type="hidden" name="hidNewImgPath" value="<c:out value="${HousingInspectionForm.hidNewImgPath}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="24%" class="head_tr"/>
			<col width="76%"/>
		</colgroup>
		<tr>
			<th class="head_tr" width="26%">物件番号</th>
			<td><c:out value="${HousingInspectionForm.housingCd}"/></td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td><c:out value="${HousingInspectionForm.displayHousingName}"/></td>
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
                   <c:if test="${HousingInspectionForm.housingInspection == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<c:if test="${HousingInspectionForm.housingKindCd == '01'}">
			<c:forEach  items="${HousingInspectionForm.inspectionKey}" varStatus="status">
			<input type="hidden" name="inspectionKey" value="<c:out value="${HousingInspectionForm.inspectionKey[status.index]}"/>">
			<input type="hidden" name="inspectionTrust_result" value="<c:out value="${HousingInspectionForm.inspectionTrust_result[status.index]}"/>">
			<input type="hidden" name="inspectionValue_label" value="<c:out value="${HousingInspectionForm.inspectionValue_label[status.index]}"/>">
				<tr>
					<th class="head_tr">
						<dm3lookup:lookupForEach lookupName="inspectionTrustMansion">
		                   <c:if test="${HousingInspectionForm.inspectionKey[status.index] == key}"><c:out value="${value}　評価基準"/> </c:if>
						</dm3lookup:lookupForEach>
					</th>
					<td>
						<dm3lookup:lookupForEach lookupName="inspectionResult">
		                   <c:if test="${HousingInspectionForm.inspectionValue_label[status.index] == key}"><c:out value="${value}"/> </c:if>
						</dm3lookup:lookupForEach>
					</td>
					<th class="head_tr" >
					    <dm3lookup:lookupForEach lookupName="inspectionTrustMansion">
		                   <c:if test="${HousingInspectionForm.inspectionKey[status.index] == key}"><c:out value="${value}　確認範囲"/> </c:if>
						</dm3lookup:lookupForEach>
					</th>
					<td >
						<dm3lookup:lookupForEach lookupName="inspectionLabel">
		                   <c:if test="${HousingInspectionForm.inspectionTrust_result[status.index] == key}"><c:out value="${value}"/> </c:if>
						</dm3lookup:lookupForEach>
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${HousingInspectionForm.housingKindCd != '01'}">
			<c:forEach items="${HousingInspectionForm.inspectionKey}" varStatus="status">
			<input type="hidden" name="inspectionKey" value="<c:out value="${HousingInspectionForm.inspectionKey[status.index]}"/>">
			<input type="hidden" name="inspectionTrust_result" value="<c:out value="${HousingInspectionForm.inspectionTrust_result[status.index]}"/>">
			<input type="hidden" name="inspectionValue_label" value="<c:out value="${HousingInspectionForm.inspectionValue_label[status.index]}"/>">
				<tr>
					<th class="head_tr">
						<dm3lookup:lookupForEach lookupName="inspectionTrustHouse">
		                   <c:if test="${HousingInspectionForm.inspectionKey[status.index] == key}"><c:out value="${value}　評価基準"/> </c:if>
						</dm3lookup:lookupForEach>
					</th>
					<td>
						<dm3lookup:lookupForEach lookupName="inspectionResult">
		                   <c:if test="${HousingInspectionForm.inspectionValue_label[status.index] == key}"><c:out value="${value}"/> </c:if>
						</dm3lookup:lookupForEach>
					</td>
					<th class="head_tr" >
					    <dm3lookup:lookupForEach lookupName="inspectionTrustHouse">
		                   <c:if test="${HousingInspectionForm.inspectionKey[status.index] == key}"><c:out value="${value}　確認範囲"/> </c:if>
						</dm3lookup:lookupForEach>
					</th>
					<td>
						<dm3lookup:lookupForEach lookupName="inspectionLabel">
		                   <c:if test="${HousingInspectionForm.inspectionTrust_result[status.index] == key}"><c:out value="${value}"/> </c:if>
						</dm3lookup:lookupForEach>
					</td>
				</tr>
			</c:forEach>
		</c:if>

		<tr>
			<th class="head_tr">レーダーチャート画像</th>
			<td colspan="3">
			<c:if test="${HousingInspectionForm.imgFlg == '1' }">
				<c:if test="${!empty HousingInspectionForm.hidNewImgPath}">
				<a id="demo2" href="<c:out value="${HousingInspectionForm.hidNewImgPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="40" height="35"  alt="" style="vertical-align:middle;"/></a>
				</c:if>
				<c:if test="${empty HousingInspectionForm.hidNewImgPath}">
				<a id="demo2" href="<c:out value="${HousingInspectionForm.hidImgPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="40" height="35"  alt="" style="vertical-align:middle;"/></a>
				</c:if>
			</c:if>
			<c:if test="${HousingInspectionForm.imgFlg != '1' && HousingInspectionForm.imgFile != '' && HousingInspectionForm.housingImgDel != 'on'}">
				<c:if test="${!empty HousingInspectionForm.hidNewImgPath}">
				<a id="demo2" href="<c:out value="${HousingInspectionForm.hidNewImgPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="40" height="35"  alt="" style="vertical-align:middle;"/></a>
				</c:if>
				<c:if test="${empty HousingInspectionForm.hidNewImgPath}">
				<a id="demo2" href="<c:out value="${HousingInspectionForm.hidImgPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="40" height="35"  alt="" style="vertical-align:middle;"/></a>
				</c:if>
			</c:if>
			&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">住宅診断ファイル</th>
			<td colspan="3">
			<c:if test="${HousingInspectionForm.loadFlg == '1'}">
				<c:if test="${!empty HousingInspectionForm.hidNewPath}">
				<a href="<c:out value="${HousingInspectionForm.hidNewPath}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a>
				</c:if>
				<c:if test="${empty HousingInspectionForm.hidNewPath}">
				<a href="<c:out value="${HousingInspectionForm.hidPath}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a>
				</c:if>
			</c:if>
			<c:if test="${HousingInspectionForm.loadFlg != '1' && HousingInspectionForm.loadFile != '' && HousingInspectionForm.housingDel != 'on'}">
				<c:if test="${!empty HousingInspectionForm.hidNewPath}">
				<a href="<c:out value="${HousingInspectionForm.hidNewPath}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a>
				</c:if>
				<c:if test="${empty HousingInspectionForm.hidNewPath}">
				<a href="<c:out value="${HousingInspectionForm.hidPath}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a>
				</c:if>
			</c:if>
			&nbsp;
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

