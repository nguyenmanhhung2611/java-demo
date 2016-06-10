<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
物件詳細情報編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm" enctype="multipart/form-data">
		<input type="hidden" name="command" value="">
		<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
		<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
		<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
		<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" style="width:18%">物件番号</th>
				<td>
					<c:out value="${inputForm.housingCd}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td>
					<c:out value="${inputForm.displayHousingName}"/>&nbsp;
				</td>
			</tr>
		</table>
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="12%" class="head_tr"/>
				<col width="21%"/>
				<col width="16%" class="head_tr"/>
				<col width="21%"/>
				<col width="15%"/>
				<col width="15%"/>
			</colgroup>
			<tr>
				<th class="head_tr">土地権利</th>
				<td>
					<select name="landRight">
		                <dm3lookup:lookupForEach lookupName="landRight">
		                    <option value="<c:out value="${key}"/>" <c:if test="${inputForm.landRight == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select>
				</td>
				<th class="head_tr">建物構造<c:if test="${inputForm.housingKindCd != '03'}"><font color="red">※</font></c:if></th>
				<td><input type="text" name="buildingDataValue" value="<c:out value="${inputForm.buildingDataValue}"/>" size="9" maxlength="30" class="input2"></td>
				<th class="head_tr">取引形態<font color="red">※</font></th>
				<td>
					<select name="transactTypeDiv">
		                <dm3lookup:lookupForEach lookupName="transactTypeDiv">
		                    <option value="<c:out value="${key}"/>" <c:if test="${inputForm.transactTypeDiv == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select></td>
			</tr>
			<tr>
				<th class="head_tr">駐車場</th>
				<td colspan="5"><input type="text" name="displayParkingInfo" value="<c:out value="${inputForm.displayParkingInfo}"/>" size="15" maxlength="30" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">現況<font color="red">※</font><br><p style="font-size: 85%">（10文字）</p></th>
				<td><input type="text" name="preDataValue" value="<c:out value="${inputForm.preDataValue}"/>" size="9" maxlength="10" class="input2">
				</td>
				<th class="head_tr">用途地域<font color="red">※</font></th>
				<td colspan="3">
					<select name="usedAreaCd">
		                <dm3lookup:lookupForEach lookupName="usedAreaCd">
		                    <option value="<c:out value="${key}"/>" <c:if test="${inputForm.usedAreaCd == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">引渡時期</th>
				<td><label><input type="checkBox" name="moveinTiming" value="01"<c:if test="${inputForm.moveinTiming == '01'}"> checked</c:if> />即時</label></td>
				<th class="head_tr">引渡時期コメント<br><p style="font-size: 75%"><p style="font-size: 85%">（20文字）</</p></th>
				<td colspan="3"><input type="text" name="moveinNote" value="<c:out value="${inputForm.moveinNote}"/>" size="12" maxlength="20" class="input2"></td>
			</tr>
			<c:if test="${inputForm.housingKindCd != '01'}">
			<tr>
				<th class="head_tr">接道状況<font color="red">※</font><br><p style="font-size: 85%">（30文字）</p></th>
				<td><input type="text" name="contactRoad" value="<c:out value="${inputForm.contactRoad}"/>" size="9" maxlength="30" class="input2"></td>
				<th class="head_tr">接道方向/幅員<font color="red">※</font><br><p style="font-size: 85%">（30文字）</p></th>
				<td><input type="text" name="contactRoadDir" value="<c:out value="${inputForm.contactRoadDir}"/>" size="9" maxlength="30" class="input2"></td>
				<th class="head_tr">私道負担<font color="red">※</font><br><p style="font-size: 85%">（30文字）</p></th>
				<td><input type="text" name="privateRoad" value="<c:out value="${inputForm.privateRoad}"/>" size="6" maxlength="30" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">建ぺい率<font color="red">※</font></th>
				<td><input type="text" name="coverageMemo" value="<c:out value="${inputForm.coverageMemo}"/>" size="8" maxlength="10" class="input2 ime-disabled"></td>
				<th class="head_tr">容積率<font color="red">※</font></th>
				<td colspan="3"><input type="text" name="buildingRateMemo" value="<c:out value="${inputForm.buildingRateMemo}"/>" size="9" maxlength="10" class="input2 ime-disabled"></td>
			</tr>
			</c:if>
			<c:if test="${inputForm.housingKindCd == '01'}">
			<tr>
				<th class="head_tr">総戸数</th>
				<td><input type="text" name="totalHouseCntDataValue" value="<c:out value="${inputForm.totalHouseCntDataValue}"/>" size="9" maxlength="10" class="input2"></td>
				<th class="head_tr">建物階数<font color="red">※</font></th>
				<td colspan="3"><input type="text" name="totalFloors" value="<c:out value="${inputForm.totalFloors}"/>" size="6" maxlength="3" class="input2 ime-disabled">階建て</td>
			</tr>
			<tr>
				<th class="head_tr">規模</th>
				<td colspan="5">
					<select name="scaleDataValue">
		                <dm3lookup:lookupForEach lookupName="scaleDataValue">
		                    <option value="<c:out value="${key}"/>" <c:if test="${inputForm.scaleDataValue == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">所在階数</th>
				<td>
					<input type="text" name="floorNo" value="<c:out value="${inputForm.floorNo}"/>" size="7" maxlength="3" class="input2 ime-disabled">階
				</td>
				<th class="head_tr">所在階数コメント</th>
				<td colspan="4"><input type="text" name="floorNoNote" value="<c:out value="${inputForm.floorNoNote}"/>" size="10" maxlength="10" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">向き</th>
				<td colspan="2"><input type="text" name="orientedDataValue" value="<c:out value="${inputForm.orientedDataValue}"/>" size="18" maxlength="20" class="input2">
				</td>
				<th class="head_tr">バルコニー面積</th>
				<td colspan="2"><input type="text" name="balconyArea" value="<c:out value="${inputForm.balconyArea}"/>" size="10" maxlength="8" class="input2 ime-disabled">m&sup2;</td>
			</tr>
			<tr>
				<th class="head_tr">管理費</th>
				<td><input type="text" name="upkeep" value="<c:out value="${inputForm.upkeep}"/>" size="5" maxlength="11" class="input2 ime-disabled">円／月</td>
				<th class="head_tr">修繕積立費</th>
				<td><input type="text" name="menteFee" value="<c:out value="${inputForm.menteFee}"/>" size="5" maxlength="11" class="input2 ime-disabled">円／月</td>
				<th class="head_tr">管理形態・方式</th>
				<td><input type="text" name="upkeepType" value="<c:out value="${inputForm.upkeepType}"/>" size="4" maxlength="20" class="input2"></td>
			</tr>
			</c:if>
			<tr>
				<th class="head_tr">管理会社<br><p style="font-size: 85%">（20文字）</p></th>
				<td colspan="5"><input type="text" name="upkeepCorp" value="<c:out value="${inputForm.upkeepCorp}"/>" size="20" maxlength="20" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">瑕疵保険</th>
				<td colspan="2">
					<select name="insurExist">
		                <dm3lookup:lookupForEach lookupName="insurExist">
		                    <option value="<c:out value="${key}"/>" <c:if test="${inputForm.insurExist == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select>
				</td>
				<th class="head_tr">担当者名<br><p style="font-size: 85%">（20文字）</p></th>
				<td colspan="2"><input type="text" name="workerDataValue" value="<c:out value="${inputForm.workerDataValue}"/>" size="14" maxlength="20" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">担当者<br>写真</th>
				<td colspan="5">
					<c:if test="${!empty inputForm.pictureDataPath and !empty inputForm.pictureDataFileName}">
					<div class="inputStyle">
						<a id="demo2" href="<c:out value="${inputForm.previewImgPath}"/>">
						<img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" width="30" height="27"/></a>&nbsp;
						<label><input type="checkBox" name="pictureDataDelete" value="1"<c:if test="${inputForm.pictureDataDelete == '1'}"> checked</c:if> />削除</label>&nbsp;
						<input type="hidden" name="pictureDataPath" value="<c:out value="${inputForm.pictureDataPath}"/>">
						<input type="hidden" name="pictureDataFileName" value="<c:out value="${inputForm.pictureDataFileName}"/>">
						<input type="hidden" name="pictureUpFlg" value="<c:out value="${inputForm.pictureUpFlg}"/>">
						<input type="hidden" name="previewImgPath" value="<c:out value="${inputForm.previewImgPath}"/>">
					</div>
					</c:if>
					<div>
						<input type="file" name="pictureDataValue" size="15"/>
					</div>

				</td>
			</tr>
			<tr>
				<th class="head_tr">会社名<font color="red">※</font><br><p style="font-size: 85%">（20文字）</p></th>
				<td colspan="2"><input type="text" name="companyDataValue" value="<c:out value="${inputForm.companyDataValue}"/>" size="18" maxlength="20" class="input2"></td>
				<th class="head_tr">支店名<br><p style="font-size: 85%">（20文字）</p></th>
				<td colspan="2"><input type="text" name="branchDataValue" value="<c:out value="${inputForm.branchDataValue}"/>" size="14" maxlength="20" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">免許番号<font color="red">※</font><br><p style="font-size: 85%">（20文字）</p></th>
				<td colspan="2"><input type="text" name="freeCdDataValue" value="<c:out value="${inputForm.freeCdDataValue}"/>" size="18" maxlength="20" class="input2"></td>
				<th class="head_tr">インフラ<br><p style="font-size: 85%">（50文字）</p></th>
				<td colspan="2"><input type="text" name="infDataValue" value="<c:out value="${inputForm.infDataValue}"/>" size="14" maxlength="50" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">特記事項<br><p style="font-size: 85%">（100文字）</p></th>
				<td colspan="5"><input type="text" name="specialInstruction" value="<c:out value="${inputForm.specialInstruction}"/>" size="48" maxlength="100" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">物件コメ<br>ント<br><p style="font-size: 85%">（40文字）</p></th>
				<td colspan="5"><input type="text" name="dtlComment" value="<c:out value="${inputForm.dtlComment}"/>" size="48" maxlength="40" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">担当者コ<br>メント<br><p style="font-size: 85%">（500文字）</p></th>
				<td colspan="5"><textarea name="basicComment" rows="2" cols="53" maxlength="500" class="input2"><c:out value="${inputForm.basicComment}"/></textarea></td>
			</tr>
			<tr>
				<th class="head_tr">売主コメ<br>ント<br><p style="font-size: 85%">（500文字）</p></th>
				<td colspan="5"><textarea name="vendorComment" rows="2" cols="53" maxlength="500" class="input2"><c:out value="${inputForm.vendorComment}"/></textarea></td>
			</tr>
			<tr>
				<th class="head_tr">リフォーム<br>準備中コ<br>メント<br><p style="font-size: 85%">（50文字）</p></th>
				<td colspan="5"><input type="text" name="reformComment" value="<c:out value="${inputForm.reformComment}"/>" size="48" maxlength="50" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">動画リン<br>クURL</th>
				<td colspan="5"><input type="text" name="urlDataValue" value="<c:out value="${inputForm.urlDataValue}"/>" size="48" maxlength="255" class="input2 ime-disabled"></td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
	$(function(){
	    $("#demo2").fancybox();
	});
// -->
</script>
