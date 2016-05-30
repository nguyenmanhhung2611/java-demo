<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
物件詳細情報編集確認画面で使用する入力確認画面の出力
--%>

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" style="width:18%">物件番号</th>
			<td><c:out value="${inputForm.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td><c:out value="${inputForm.displayHousingName}"/>&nbsp;</td>
		</tr>
	</table>
	<br>
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="15%" class="head_tr"/>
			<col width="21%"/>
			<col width="16%" class="head_tr"/>
			<col width="21%"/>
			<col width="15%"/>
			<col width="12%"/>
		</colgroup>
		<tr>
			<th class="head_tr">土地権利</th>
			<td>
    			<dm3lookup:lookupForEach lookupName="landRight">
                   <c:if test="${inputForm.landRight == key}"><c:out value="${value}"/> </c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
			<th class="head_tr">建物構造</th>
			<td><c:out value="${inputForm.buildingDataValue}"/>&nbsp;</td>
			<th class="head_tr">取引形態</th>
			<td>
               <dm3lookup:lookupForEach lookupName="transactTypeDiv">
                   <c:if test="${inputForm.transactTypeDiv == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">駐車場</th>
			<td colspan="5"><c:out value="${inputForm.displayParkingInfo}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">現況</th>
			<td><c:out value="${inputForm.preDataValue}"/>&nbsp;</td>
			<th class="head_tr">用途地域</th>
			<td colspan="3">
                <dm3lookup:lookupForEach lookupName="usedAreaCd">
                    <c:if test="${inputForm.usedAreaCd == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">引渡時期</th>
			<td><c:if test="${inputForm.moveinTiming == '01'}">即時</c:if>&nbsp;</td>
			<th class="head_tr">引渡時期コメント</th>
			<td colspan="3"><c:out value="${inputForm.moveinNote}"/>&nbsp;</td>
		</tr>
		<c:if test="${inputForm.housingKindCd != '01'}">
		<tr>
			<th class="head_tr">接道状況</th>
			<td><c:out value="${inputForm.contactRoad}"/>&nbsp;</td>
			<th class="head_tr">接道方向/幅員</th>
			<td><c:out value="${inputForm.contactRoadDir}"/>&nbsp;</td>
			<th class="head_tr">私道負担</th>
			<td><c:out value="${inputForm.privateRoad}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">建ぺい率</th>
			<td><c:if test="${ !empty inputForm.coverageMemo}"><c:out value="${inputForm.coverageMemo}"/></c:if>&nbsp;</td>
			<th class="head_tr">容積率</th>
			<td colspan="3"><c:if test="${ !empty inputForm.buildingRateMemo}"><c:out value="${inputForm.buildingRateMemo}"/></c:if>&nbsp;</td>
		</tr>
		</c:if>
		<c:if test="${inputForm.housingKindCd == '01'}">
		<tr>
			<th class="head_tr">総戸数</th>
			<td><c:out value="${inputForm.totalHouseCntDataValue}"/>&nbsp;</td>
			<th class="head_tr">建物階数</th>
			<td><c:if test="${ !empty inputForm.totalFloors}"><c:out value="${inputForm.totalFloors}"/>階建て</c:if>&nbsp;</td>
			<th class="head_tr">規模</th>
			<td>
                <dm3lookup:lookupForEach lookupName="scaleDataValue">
                    <c:if test="${inputForm.scaleDataValue == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">所在階数</th>
			<td><c:if test="${ !empty inputForm.floorNo}"><c:out value="${inputForm.floorNo}"/>階</c:if>&nbsp;</td>
			<th class="head_tr">所在階数コメント</th>
			<td colspan="3"><c:out value="${inputForm.floorNoNote}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">向き</th>
			<td colspan="2"><c:out value="${inputForm.orientedDataValue}"/>&nbsp;</td>
			<th class="head_tr">バルコニー面積</th>
			<td colspan="3"><c:if test="${ !empty inputForm.balconyArea}"><c:out value="${inputForm.balconyArea}"/>m&sup2;</c:if>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">管理費</th>
			<td><c:if test="${ !empty inputForm.upkeep}"><fmt:formatNumber value="${inputForm.upkeep}" pattern="###,###" />円／月</c:if>&nbsp;</td>
			<th class="head_tr">修繕積立費</th>
			<td><c:if test="${ !empty inputForm.menteFee}"><fmt:formatNumber value="${inputForm.menteFee}" pattern="###,###" />円／月</c:if>&nbsp;</td>
			<th class="head_tr">管理形態・方式</th>
			<td><c:out value="${inputForm.upkeepType}"/>&nbsp;</td>
		</tr>
		</c:if>
		<tr>
			<th class="head_tr">管理会社</th>
			<td colspan="5"><c:out value="${inputForm.upkeepCorp}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">瑕疵保険</th>
			<td colspan="2">
                <dm3lookup:lookupForEach lookupName="insurExist">
                    <c:if test="${inputForm.insurExist == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
			<th class="head_tr">担当者名</th>
			<td colspan="3"><c:out value="${inputForm.workerDataValue}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">担当者写真</th>
			<td colspan="5">
			<c:if test="${inputForm.pictureDataDelete != '1' and !empty inputForm.pictureDataPath and !empty inputForm.pictureDataFileName}">
			<a id="demo2" href="<c:out value="${inputForm.previewImgPath}"/>">
			<img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="30" height="27"/></a>&nbsp;あり
			</c:if>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">会社名</th>
			<td colspan="2"><c:out value="${inputForm.companyDataValue}"/>&nbsp;</td>
			<th class="head_tr">支社名</th>
			<td colspan="3"><c:out value="${inputForm.branchDataValue}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">免許番号</th>
			<td colspan="2"><c:out value="${inputForm.freeCdDataValue}"/>&nbsp;</td>
			<th class="head_tr">インフラ</th>
			<td colspan="3"><c:out value="${inputForm.infDataValue}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">特記事項</th>
			<td colspan="5"><c:out value="${inputForm.specialInstruction}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件コメント</th>
			<td colspan="5"><c:out value="${inputForm.dtlComment1}" escapeXml="false"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">担当者のコメ<br>ント</th>
			<td colspan="5"><c:out value="${inputForm.basicComment1}" escapeXml="false"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">売主コメ<br>ント</th>
			<td colspan="5"><c:out value="${inputForm.vendorComment1}" escapeXml="false"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">リフォーム<br>準備中コ<br>メント</th>
			<td colspan="5"><c:out value="${inputForm.reformComment1}" escapeXml="false"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">動画リン<br>クURL</th>
			<td colspan="5"><c:out value="${inputForm.urlDataValue}"/>&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="pageId" value="housingDtl">
	<input type="hidden" name="command" value="">
	<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
	<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
	<input type="hidden" name="landRight" value="<c:out value="${inputForm.landRight}"/>">
	<input type="hidden" name="buildingDataValue" value="<c:out value="${inputForm.buildingDataValue}"/>">
	<input type="hidden" name="transactTypeDiv" value="<c:out value="${inputForm.transactTypeDiv}"/>">
	<input type="hidden" name="displayParkingInfo" value="<c:out value="${inputForm.displayParkingInfo}"/>">
	<input type="hidden" name="preDataValue" value="<c:out value="${inputForm.preDataValue}"/>">
	<input type="hidden" name="usedAreaCd" value="<c:out value="${inputForm.usedAreaCd}"/>">
	<input type="hidden" name="moveinTiming" value="<c:out value="${inputForm.moveinTiming}"/>">
	<input type="hidden" name="moveinNote" value="<c:out value="${inputForm.moveinNote}"/>">
	<input type="hidden" name="contactRoad" value="<c:out value="${inputForm.contactRoad}"/>">
	<input type="hidden" name="contactRoadDir" value="<c:out value="${inputForm.contactRoadDir}"/>">
	<input type="hidden" name="privateRoad" value="<c:out value="${inputForm.privateRoad}"/>">
	<input type="hidden" name="coverageMemo" value="<c:out value="${inputForm.coverageMemo}"/>">
	<input type="hidden" name="buildingRateMemo" value="<c:out value="${inputForm.buildingRateMemo}"/>">
	<input type="hidden" name="totalHouseCntDataValue" value="<c:out value="${inputForm.totalHouseCntDataValue}"/>">
	<input type="hidden" name="totalFloors" value="<c:out value="${inputForm.totalFloors}"/>">
	<input type="hidden" name="scaleDataValue" value="<c:out value="${inputForm.scaleDataValue}"/>">
	<input type="hidden" name="floorNo" value="<c:out value="${inputForm.floorNo}"/>">
	<input type="hidden" name="floorNoNote" value="<c:out value="${inputForm.floorNoNote}"/>">
	<input type="hidden" name="orientedDataValue" value="<c:out value="${inputForm.orientedDataValue}"/>">
	<input type="hidden" name="balconyArea" value="<c:out value="${inputForm.balconyArea}"/>">
	<input type="hidden" name="upkeep" value="<c:out value="${inputForm.upkeep}"/>">
	<input type="hidden" name="menteFee" value="<c:out value="${inputForm.menteFee}"/>">
	<input type="hidden" name="upkeepType" value="<c:out value="${inputForm.upkeepType}"/>">
	<input type="hidden" name="upkeepCorp" value="<c:out value="${inputForm.upkeepCorp}"/>">
	<input type="hidden" name="insurExist" value="<c:out value="${inputForm.insurExist}"/>">
	<input type="hidden" name="workerDataValue" value="<c:out value="${inputForm.workerDataValue}"/>">
	<input type="hidden" name="pictureDataPath" value="<c:out value="${inputForm.pictureDataPath}"/>">
	<input type="hidden" name="pictureDataFileName" value="<c:out value="${inputForm.pictureDataFileName}"/>">
	<input type="hidden" name="pictureDataDelete" value="<c:out value="${inputForm.pictureDataDelete}"/>">
	<input type="hidden" name="pictureUpFlg" value="<c:out value="${inputForm.pictureUpFlg}"/>">
	<input type="hidden" name="companyDataValue" value="<c:out value="${inputForm.companyDataValue}"/>">
	<input type="hidden" name="branchDataValue" value="<c:out value="${inputForm.branchDataValue}"/>">
	<input type="hidden" name="freeCdDataValue" value="<c:out value="${inputForm.freeCdDataValue}"/>">
	<input type="hidden" name="infDataValue" value="<c:out value="${inputForm.infDataValue}"/>">
	<input type="hidden" name="specialInstruction" value="<c:out value="${inputForm.specialInstruction}"/>">
	<input type="hidden" name="dtlComment" value="<c:out value="${inputForm.dtlComment}"/>">
	<input type="hidden" name="basicComment" value="<c:out value="${inputForm.basicComment}"/>">
	<input type="hidden" name="vendorComment" value="<c:out value="${inputForm.vendorComment}"/>">
	<input type="hidden" name="reformComment" value="<c:out value="${inputForm.reformComment}"/>">
	<input type="hidden" name="urlDataValue" value="<c:out value="${inputForm.urlDataValue}"/>">
	<input type="hidden" name="previewImgPath" value="<c:out value="${inputForm.previewImgPath}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.target = "";
		document.inputForm.submit();
	}
	function popupPreview(url) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.submit();
	}
	$(function(){
	    $("#demo2").fancybox();
	});
// -->
</script>
