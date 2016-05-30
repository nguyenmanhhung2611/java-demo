<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
物件一覧の検索条件
--%>
<script type ="text/JavaScript">

function searchLinkToUrl(url, housingKindCd) {
	var priceLowerUrl = "";
	var priceUpperUrl = "";
	var reformPriceCheckUrl = "";
	var personalAreaLowerUrl = "";
	var personalAreaUpperUrl = "";
	var buildingAreaLowerUrl = "";
	var buildingAreaUpperUrl = "";
	var landAreaLowerUrl = "";
	var landAreaUpperUrl = "";
	var layoutCdUrl = "";
	var compDateUrl = "";
	var iconUrl = "";
	var partSrchCdWalkArrayUrl = "";
	var partSrchCdFloorArrayUrl = "";
	var partSrchCdArrayUrl = "";
	var moneinTimingUrl = "";
	var housingCdUrl = "";
	var searchCount = 0;
	var partCount = 0;
	var housingCdUrl = ""
	var housingCd = "";
	var val = $("#keyHousingCd").val();
    var placeholder = $("#keyHousingCd").attr('placeholder');
    if(val != placeholder) {
      housingCd = val;
    }

	if($("#priceLower").val() != ""){
		priceLowerUrl = "&priceLower=" + $("#priceLower").val();
		searchCount = searchCount + 1;
	}

	if($("#priceUpper").val() != ""){
		priceUpperUrl = "&priceUpper=" + $("#priceUpper").val();
		searchCount = searchCount + 1;
	}

	if(housingKindCd != '03'){
		if($("#reformPriceCheck").val() != ""){
			reformPriceCheckUrl = "&reformPriceCheck=" + $("#reformPriceCheck").val();
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd == '01'){
		if($("#personalAreaLower").val() != ""){
			personalAreaLowerUrl = "&personalAreaLower=" + $("#personalAreaLower").val();
			searchCount = searchCount + 1;
		}

		if($("#personalAreaUpper").val() != ""){
			personalAreaUpperUrl = "&personalAreaUpper=" + $("#personalAreaUpper").val();
			searchCount = searchCount + 1;
		}
	}else{
		if($("#buildingAreaLower").val() != ""){
			buildingAreaLowerUrl = "&buildingAreaLower=" + $("#buildingAreaLower").val();
			searchCount = searchCount + 1;
		}

		if($("#buildingAreaUpper").val() != ""){
			buildingAreaUpperUrl = "&buildingAreaUpper=" + $("#buildingAreaUpper").val();
			searchCount = searchCount + 1;
		}

		if($("#landAreaLower").val() != ""){
			landAreaLowerUrl = "&landAreaLower=" + $("#landAreaLower").val();
			searchCount = searchCount + 1;
		}

		if($("#landAreaUpper").val() != ""){
			landAreaUpperUrl = "&landAreaUpper=" + $("#landAreaUpper").val();
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd != '03'){
		if($("#layoutCdArray").val() != ""){
			layoutCdUrl = "&keyLayoutCd=" + $("#layoutCdArray").val() + ",";
			searchCount = searchCount + 1;
		}

		if($("#keyCompDateArray").val() != "" && $("#keyCompDateArray").val() != "999"){
			compDateUrl = "&keyCompDate=" + $("#keyCompDateArray").val();
			searchCount = searchCount + 1;
		}

		if($("#iconCdArray").val() != ""){
			iconUrl = "&keyIconCd=" + $("#iconCdArray").val() + ",";
			searchCount = searchCount + 1;
		}
	}

	if($("#partSrchCdWalkArray").val() != ""){
		if($("#partSrchCdWalkArray").val() != "999"){
			partSrchCdWalkArrayUrl = "&partSrchCdWalkArray=" + $("#partSrchCdWalkArray").val();
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd != '03'){
		if($("#partSrchCdArray").val() != ""){
			partSrchCdArrayUrl = "&partSrchCdArray=" + $("#partSrchCdArray").val();
			searchCount = searchCount + 1;
		}
	}

	if(housingKindCd == '01'){
		if($("#partSrchCdFloorArray").val() != ""){
			partSrchCdFloorArrayUrl = "&partSrchCdFloorArray=" + $("#partSrchCdFloorArray").val();
			searchCount = searchCount + 1;
		}
	}

	if($("#moveinTiming").val() != ""){
		moneinTimingUrl = "&moveinTiming=" + $("#moveinTiming").val();
		searchCount = searchCount + 1;
	}

	if(housingCd != ""){
		housingCdUrl = "&keyHousingCd=" + housingCd;
		searchCount = searchCount + 1;
	}

	url = url + buildingAreaUpperUrl + moneinTimingUrl + priceUpperUrl + buildingAreaLowerUrl + priceLowerUrl  + "&sortPriceValue=" + $("#sortPriceValue").val()
			+ "&sortUpdDateValue=" + $("#sortUpdDateValue").val() + landAreaUpperUrl + iconUrl + "&keyOrderType=" + $("#keyOrderType").val() + layoutCdUrl + compDateUrl + reformPriceCheckUrl
			+ landAreaLowerUrl + "&sortWalkTimeValue=" + $("#sortWalkTimeValue").val() + partSrchCdWalkArrayUrl + personalAreaLowerUrl + personalAreaUpperUrl
			+ partSrchCdArrayUrl + "&sortBuildDateValue=" + $("#sortBuildDateValue").val() + partSrchCdFloorArrayUrl + housingCdUrl;

	var reg = new RegExp(",", "g");
	document.inputForm.action=url.replace(reg, "%2C");
	document.inputForm.submit();
}

function searchArea(obj) {
	if($(obj).next('.box_cmn_accordion').is(':visible')) {
		$(obj).next('.box_cmn_accordion').slideUp(300);

	} else {
		$(obj).next('.box_cmn_accordion').slideDown(300).siblings('.box_cmn_accordion').slideUp(300);
	}
	$(obj).toggleClass("current");
	$(obj).children().toggleClass("open");
}


function changePriceLower(prefCd, housingKindName, paramUrl) {

	var optsUpperValue = document.getElementById("priceUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsLowerValue = document.getElementById("priceLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsHidden = document.getElementById("priceHidden").options;

	var optsLength = optsHidden.length;

	$("#priceUpper option").remove();

	var optUpper = document.createElement("option");
	optUpper.value = "";
	optUpper.text = "上限なし";
	document.inputForm.priceUpper.add(optUpper);

	if(optsLowerValue!='0'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.priceUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.priceUpper.value = optsUpperValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.priceUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.priceUpper.value = optsUpperValue;
			}
		}
	}
	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changePriceUpper(prefCd, housingKindName, paramUrl) {

	var optsUpperValue = document.getElementById("priceUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsLowerValue = document.getElementById("priceLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsHidden = document.getElementById("priceHidden").options;

	var optsLength = optsHidden.length;

	$("#priceLower option").remove();

	var optLower = document.createElement("option");
	optLower.value = "";
	optLower.text = "下限なし";
	document.inputForm.priceLower.add(optLower);

	if(optsUpperValue!='999'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.priceLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.priceLower.value = optsLowerValue;
			}

		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.priceLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.priceLower.value = optsLowerValue;
			}

		}
	}


	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changePersonalAreaLower(prefCd, housingKindName, paramUrl) {

	var optsUpperValue = document.getElementById("personalAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsLowerValue = document.getElementById("personalAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsHidden = document.getElementById("personalAreaHidden").options;

	var optsLength = optsHidden.length;

	$("#personalAreaUpper option").remove();

	var optUpper = document.createElement("option");
	optUpper.value = "";
	optUpper.text = "上限なし";
	document.inputForm.personalAreaUpper.add(optUpper);

	if(optsLowerValue!='0'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.personalAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.personalAreaUpper.value = optsUpperValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if((opt.value - 0) >= (optsLowerValue - 0)){
				document.inputForm.personalAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.personalAreaUpper.value = optsUpperValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changePersonalAreaUpper(prefCd, housingKindName, paramUrl) {

	var optsUpperValue = document.getElementById("personalAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsLowerValue = document.getElementById("personalAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsHidden = document.getElementById("personalAreaHidden").options;

	var optsLength = optsHidden.length;

	$("#personalAreaLower option").remove();

	var optLower = document.createElement("option");
	optLower.value = "";
	optLower.text = "下限なし";
	document.inputForm.personalAreaLower.add(optLower);
	if(optsUpperValue!='999'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.personalAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.personalAreaLower.value = optsLowerValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if((opt.value - 0) <= (optsUpperValue - 0)){
				document.inputForm.personalAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.personalAreaLower.value = optsLowerValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changeBuildingAreaLower(prefCd, housingKindName, paramUrl) {

	var optsLowerValue = document.getElementById("buildingAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsUpperValue = document.getElementById("buildingAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsHidden = document.getElementById("buildingAreaHidden").options;

	var optsLength = optsHidden.length;

	$("#buildingAreaUpper option").remove();

	var optUpper = document.createElement("option");
	optUpper.value = "";
	optUpper.text = "上限なし";
	document.inputForm.buildingAreaUpper.add(optUpper);
	if(optsLowerValue!='0'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.buildingAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.buildingAreaUpper.value = optsUpperValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.buildingAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.buildingAreaUpper.value = optsUpperValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changeBuildingAreaUpper(prefCd, housingKindName, paramUrl) {

	var optsLowerValue = document.getElementById("buildingAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsUpperValue = document.getElementById("buildingAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsHidden = document.getElementById("buildingAreaHidden").options;
	var optsLower = document.getElementById("buildingAreaLower").options;

	var optsLength = optsHidden.length;

	$("#buildingAreaLower option").remove();

	var optLower = document.createElement("option");
	optLower.value = "";
	optLower.text = "下限なし";
	document.inputForm.buildingAreaLower.add(optLower);
	if(optsUpperValue!='999'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.buildingAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.buildingAreaLower.value = optsLowerValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.buildingAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.buildingAreaLower.value = optsLowerValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changeLandAreaLower(prefCd, housingKindName, paramUrl) {

	var optsLowerValue = document.getElementById("landAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsUpperValue = document.getElementById("landAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsHidden = document.getElementById("landAreaHidden").options;

	var optsLength = optsHidden.length;

	$("#landAreaUpper option").remove();

	var optUpper = document.createElement("option");
	optUpper.value = "";
	optUpper.text = "上限なし";
	document.inputForm.landAreaUpper.add(optUpper);
	if(optsLowerValue!='0'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.landAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.landAreaUpper.value = optsUpperValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) >= (optsLowerValue - 0))){
				document.inputForm.landAreaUpper.add(opt);
			}
			if(i == optsUpperValue-1){
				document.inputForm.landAreaUpper.value = optsUpperValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function changeLandAreaUpper(prefCd, housingKindName, paramUrl) {

	var optsLowerValue = document.getElementById("landAreaLower").value;
	if(optsLowerValue == ""){
		optsLowerValue = 0;
	}
	var optsUpperValue = document.getElementById("landAreaUpper").value;
	if(optsUpperValue == ""){
		optsUpperValue = 999;
	}
	var optsHidden = document.getElementById("landAreaHidden").options;

	var optsLength = optsHidden.length;

	$("#landAreaLower option").remove();

	var optLower = document.createElement("option");
	optLower.value = "";
	optLower.text = "下限なし";
	document.inputForm.landAreaLower.add(optLower);
	if(optsUpperValue!='999'){
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.landAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.landAreaLower.value = optsLowerValue;
			}
		}
	}else{
		for (var i = 0;i < optsLength;i ++) {
			var opt = document.createElement("option");
			opt.value = optsHidden[i].value;
			opt.text = optsHidden[i].text;
			if(((opt.value - 0) <= (optsUpperValue - 0))){
				document.inputForm.landAreaLower.add(opt);
			}
			if(i == optsLowerValue-1){
				document.inputForm.landAreaLower.value = optsLowerValue;
			}
		}
	}

	getHousingCount(prefCd, housingKindName, paramUrl);
}

function clearList(prefCd, layoutCount, compdateCount, iconCount, housingKindCd, housingKindName, paramUrl) {
		var housingCd = "";
		var val = $("#keyHousingCd").val();
	    var placeholder = $("#keyHousingCd").attr('placeholder');
	    if(val != placeholder) {
	      housingCd = val;
	    }
		$.getJSON(paramUrl + "/buy/" + housingKindName + "/" +prefCd+"/searchCount/",

	    		{
	    			// 予算下限
	    			priceLower:""
	    			// 予算上限
	    			, priceUpper:""
	    			// 「リフォーム価格込みで検索する」のチェック状態
	    			, reformPriceCheck:""
	    			// 専有面積下限
	    			, personalAreaLower:""
	    			// 専有面積上限
	    			, personalAreaUpper:""
	    			// 建物面積下限
	    			, buildingAreaLower:""
	    			// 建物面積上限
	    			, buildingAreaUpper:""
	    			// 土地面積下限
	    			, landAreaLower:""
	    			// 土地面積上限
	    			, landAreaUpper:""
	    			// 間取り
	    			, layoutCd:""
	    			// 築年月
	    			, keyCompDate:""
	    			// おすすめのポイント
	    			, iconCd:""
    				// 駅徒歩
	    			, partSrchCdWalkArray:$("#partSrchCdWalkArray").val()
					// 物件画像情報、物件特長
	    			, partSrchCdArray:$("#partSrchCdArray").val()
					// 引渡時期
	    			, moveinTiming:$("#moveinTiming").val()
					// 所在階数
	    			, partSrchCdFloorArray:$("#partSrchCdFloorArray").val()
					// 物件番号
	    			, keyHousingCd:housingCd
	    			, keyHousingKindCd:$("#keyHousingKindCd").val()
	    			, keyRouteCd:$("#keyRouteCd").val()
	    			, keyStationCd:$("#keyStationCd").val()
	    			, keyAddressCd:$("#keyAddressCd").val()

	    		},function(dataObj){
			$(dataObj.listSize).each(function () {
				document.inputForm.priceLower.value = "";
				document.inputForm.priceUpper.value = "";

				if(housingKindCd == '01'){
					document.inputForm.personalAreaLower.value = "";
					document.inputForm.personalAreaUpper.value = "";
				}
				if(housingKindCd != '01'){
					document.inputForm.buildingAreaLower.value = "";
					document.inputForm.buildingAreaUpper.value = "";
					document.inputForm.landAreaLower.value = "";
					document.inputForm.landAreaUpper.value = "";
				}
				if(housingKindCd != '03'){
					document.inputForm.reformPriceCheck.checked = false;
					document.inputForm.reformPriceCheck.value="";

					for(var i=0;i<layoutCount;i++){
						document.inputForm.layoutCd[i].checked = false;
						document.inputForm.layoutCdArray.value = "";
					}
					for(var i=0;i<compdateCount;i++){
						document.inputForm.keyCompDate[i].checked = false;
						document.inputForm.keyCompDateArray.value = "";
					}
					for(var i=0;i<iconCount;i++){
						document.inputForm.iconCd[i].checked = false;
						document.inputForm.iconCdArray.value = "";
					}
				}
				$("#housingDtlListSize").html(this.listSize + " ");
				$("#housingDtlListSizePart").html(this.listSize + " ");


				changePriceUpper(prefCd);
				changePriceLower(prefCd);
				if(housingKindCd != '01'){
					changeLandAreaUpper(prefCd);
					changeLandAreaLower(prefCd);
					changeBuildingAreaUpper(prefCd);
					changeBuildingAreaLower(prefCd);
				}
				if(housingKindCd == '01'){
					changePersonalAreaUpper(prefCd);
					changePersonalAreaLower(prefCd);
				}

			});
	    });

	}

	function clearKodawariList(prefCd, partSrchCdWalkCount, partSrchCdCount, partSrchCdFloorCount, housingKindCd, housingKindName, paramUrl) {

		$.getJSON(paramUrl + "/buy/" + housingKindName + "/" +prefCd+"/searchCount/",
	    		{
					// 予算下限
					priceLower:$("#priceLower").val()
					// 予算上限
					, priceUpper:$("#priceUpper").val()
					// 「リフォーム価格込みで検索する」のチェック状態
					, reformPriceCheck:$("#reformPriceCheck").val()
					// 専有面積下限
					, personalAreaLower:$("#personalAreaLower").val()
					// 専有面積上限
					, personalAreaUpper:$("#personalAreaUpper").val()
					// 建物面積下限
					, buildingAreaLower:$("#buildingAreaLower").val()
					// 建物面積上限
					, buildingAreaUpper:$("#buildingAreaUpper").val()
					// 土地面積下限
					, landAreaLower:$("#landAreaLower").val()
					// 土地面積上限
					, landAreaUpper:$("#landAreaUpper").val()
					// 間取り
					, layoutCd:$("#layoutCdArray").val()
					// 築年月
					, keyCompDate:$("#keyCompDateArray").val()
					// おすすめのポイント
					, iconCd:$("#iconCdArray").val()
					// 駅徒歩
	    			, partSrchCdWalkArray:""
					// 物件画像情報、物件特長
	    			, partSrchCdArray:""
					// 引渡時期
	    			, moveinTiming:""
					// 所在階数
	    			, partSrchCdFloorArray:""
					// 物件番号
	    			, keyHousingCd:""
	    			, keyHousingKindCd:$("#keyHousingKindCd").val()
	    			, keyRouteCd:$("#keyRouteCd").val()
	    			, keyStationCd:$("#keyStationCd").val()
	    			, keyAddressCd:$("#keyAddressCd").val()

	    		},function(dataObj){
			$(dataObj.listSize).each(function () {

				for(var i=0;i<partSrchCdWalkCount;i++){
					if(document.inputForm.partSrchCdWalk[i]!=null){
						document.inputForm.partSrchCdWalk[i].checked = false;
						document.inputForm.partSrchCdWalkArray.value = "";
					}else{
						document.inputForm.partSrchCdWalk.checked = false;
						document.inputForm.partSrchCdWalkArray.value = "";
					}

				}

				if(housingKindCd != '03'){
					for(var i=0;i<partSrchCdCount;i++){
						if(document.inputForm.partSrchCd[i]!=null){
							document.inputForm.partSrchCd[i].checked = false;
							document.inputForm.partSrchCdArray.value = "";
						}else{
							document.inputForm.partSrchCd.checked = false;
							document.inputForm.partSrchCd.value = "";
						}
					}

					for(var i=0;i<partSrchCdFloorCount;i++){
						if(document.inputForm.partSrchCdFloor[i]!=null){
							document.inputForm.partSrchCdFloor[i].checked = false;
							document.inputForm.partSrchCdFloorArray.value = "";
						}else{
							document.inputForm.partSrchCdFloor.checked = false;
							document.inputForm.partSrchCdFloorArray.value = "";
						}

					}
				}

				document.inputForm.moveinTiming.checked = false;
				document.inputForm.moveinTiming.value = "";

				document.inputForm.keyHousingCd.value = "";

				$("#housingDtlListSize").html(this.listSize + " ");
				$("#housingDtlListSizePart").html(this.listSize + " ");
				$("#keyHousingCd").focus();
				$("#clearButton").focus();
			});
	    });
	}

function getHousingCount(prefCd, housingKindName, paramUrl) {
		var housingCd = "";
		var val = $("#keyHousingCd").val();
	    var placeholder = $("#keyHousingCd").attr('placeholder');
	    if(val != placeholder) {
	      housingCd = val;
	    }
		$.getJSON(paramUrl + "/buy/" + housingKindName + "/" +prefCd+"/searchCount/",
	    		{
	    			// 予算下限
	    			priceLower:$("#priceLower").val()
	    			// 予算上限
	    			, priceUpper:$("#priceUpper").val()
	    			// 「リフォーム価格込みで検索する」のチェック状態
	    			, reformPriceCheck:$("#reformPriceCheck").val()
	    			// 専有面積下限
	    			, personalAreaLower:$("#personalAreaLower").val()
	    			// 専有面積上限
	    			, personalAreaUpper:$("#personalAreaUpper").val()
	    			// 建物面積下限
	    			, buildingAreaLower:$("#buildingAreaLower").val()
	    			// 建物面積上限
	    			, buildingAreaUpper:$("#buildingAreaUpper").val()
	    			// 土地面積下限
	    			, landAreaLower:$("#landAreaLower").val()
	    			// 土地面積上限
	    			, landAreaUpper:$("#landAreaUpper").val()
	    			// 間取り
	    			, layoutCd:$("#layoutCdArray").val()
	    			// 築年月
	    			, keyCompDate:$("#keyCompDateArray").val()
	    			// おすすめのポイント
	    			, iconCd:$("#iconCdArray").val()
					// 駅徒歩
	    			, partSrchCdWalkArray:$("#partSrchCdWalkArray").val()
					// 物件画像情報、物件特長
	    			, partSrchCdArray:$("#partSrchCdArray").val()
					// 引渡時期
	    			, moveinTiming:$("#moveinTiming").val()
					// 所在階数
	    			, partSrchCdFloorArray:$("#partSrchCdFloorArray").val()
					// 物件番号
	    			, keyHousingCd:housingCd
	    			// 物件種類
	    			, housingKindCd:$("#housingKindCd").val()
	    			, keyHousingKindCd:$("#keyHousingKindCd").val()
	    			, keyRouteCd:$("#keyRouteCd").val()
	    			, keyStationCd:$("#keyStationCd").val()
	    			, keyAddressCd:$("#keyAddressCd").val()

	    		},function(dataObj){
			$(dataObj.listSize).each(function () {
				$("#housingDtlListSize").html(this.listSize + " ");
				$("#housingDtlListSizePart").html(this.listSize + " ");
			});
	    });
	}


	function clickLayoutCd(prefCd, housingKindName, paramUrl) {

		var layoutValue = document.all["layoutCd"];
		var layoutArray = "";
		if (layoutValue.length>0) {
			for (var i = 0;i<layoutValue.length;i++) {
				if (layoutValue[i].checked) {
					if(layoutArray == ""){
						layoutArray = layoutValue[i].value;
					}else{
						layoutArray = layoutArray + "," + layoutValue[i].value;
					}
				}
			}
			$("#layoutCdArray").attr("value", layoutArray);
		}

		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickIconCd(prefCd, housingKindName, paramUrl) {

		var iconCdValue = document.all["iconCd"];
		var iconCdArray = "";
		if (iconCdValue.length>0) {
			for (var i = 0;i<iconCdValue.length;i++) {
				if (iconCdValue[i].checked) {
					if(iconCdArray == ""){
						iconCdArray = iconCdValue[i].value;
					}else{
						iconCdArray = iconCdArray + "," + iconCdValue[i].value;
					}
				}
			}
			$("#iconCdArray").attr("value", iconCdArray);
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickPartSrchCdWalk(prefCd, housingKindName, paramUrl) {

		var partSrchCdWalkValue = document.all["partSrchCdWalk"];
		var partSrchCdWalkArray = "";
		if (partSrchCdWalkValue.length>0) {
			for (var i = 0;i<partSrchCdWalkValue.length;i++) {
				if (partSrchCdWalkValue[i].checked) {
					if(partSrchCdWalkValue[i].value == "0" || partSrchCdWalkValue[i].value == "999"){
						partSrchCdWalkArray = "";
					}
					if(partSrchCdWalkArray == ""){
						partSrchCdWalkArray = partSrchCdWalkValue[i].value;
					}else{
						partSrchCdWalkArray = partSrchCdWalkArray + "," + partSrchCdWalkValue[i].value;
					}
				}
			}
			$("#partSrchCdWalkArray").attr("value", partSrchCdWalkArray);
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickPartSrchCd(prefCd, housingKindName, paramUrl) {

		var partSrchCdValue = document.all["partSrchCd"];
		var partSrchCdArray = "";
		if (partSrchCdValue.length>0) {
			for (var i = 0;i<partSrchCdValue.length;i++) {
				if (partSrchCdValue[i].checked) {
					if(partSrchCdValue[i].value == "0"){
						partSrchCdArray = "";
					}
					if(partSrchCdArray == ""){
						partSrchCdArray = partSrchCdValue[i].value;
					}else{
						partSrchCdArray = partSrchCdArray + "," + partSrchCdValue[i].value;
					}
				}
			}
			$("#partSrchCdArray").attr("value", partSrchCdArray);
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickPartSrchCdFloor(prefCd, housingKindName, paramUrl) {

		var partSrchCdFloorValue = document.all["partSrchCdFloor"];
		var partSrchCdFloorArray = "";
		if (partSrchCdFloorValue.length>0) {
			for (var i = 0;i<partSrchCdFloorValue.length;i++) {
				if (partSrchCdFloorValue[i].checked) {
					if(partSrchCdFloorValue[i].value == "0"){
						partSrchCdFloorArray = "";
					}
					if(partSrchCdFloorArray == ""){
						partSrchCdFloorArray = partSrchCdFloorValue[i].value;
					}else{
						partSrchCdFloorArray = partSrchCdFloorArray + "," + partSrchCdFloorValue[i].value;
					}
				}
			}
			$("#partSrchCdFloorArray").attr("value", partSrchCdFloorArray);
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickReformCheck(obj, prefCd, housingKindName, paramUrl) {

		if(obj.checked){
			$("#reformPriceCheck").attr("value", "on");
		}else{
			$("#reformPriceCheck").attr("value", "");
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickMoveinTiming(obj, prefCd, housingKindName, paramUrl) {

		if(obj.checked){
			$("#moveinTiming").attr("value", "01");
		}else{
			$("#moveinTiming").attr("value", "");
		}
		getHousingCount(prefCd, housingKindName, paramUrl);
	}

	function clickCompDate(obj, prefCd, housingKindName, paramUrl) {

		if(obj.value != "999"){
			$("#keyCompDateArray").attr("value", obj.value);
		}else{
			$("#keyCompDateArray").attr("value", "");
		}

		getHousingCount(prefCd, housingKindName, paramUrl);
	}



</script>
<c:if test="${housingDtlListSize == '0'}">
	<div class="itemBlockA01 parts-sp-accordion">
		<div class="headingBlockD01 clearfix">
			<h2 class="ttl"><span>条件を変更する</span></h2>
		</div>
		<div class="box-sp-accordion listPage">
			<div class="refineBlock spMb00">
				<div class="rerefineBox01">

					<div class="linkArea clearfix">

						<dl class="rerefineLink01 mb00 clearfix">
						<dt><span>都道府県</span></dt>
						<dd>
							<p><c:out value="${prefName}"/></p>
							<p><a href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/#search">都道府県を再選択する</a></p>
						</dd>
					</dl>
					</div>
</c:if>
<c:if test="${housingDtlListSize != '0'}">
	<p class="refinetitle01" id="cmn_accordion_button" onclick="searchArea(this);"><span>条件を絞り込む</span></p>
	<div class="box_cmn_accordion">
		<div class="rerefineBox01">
</c:if>

		<c:if test="${housingKindCd == '01'}">
			<c:set var="housingKindName" value="mansion" />
		</c:if>
		<c:if test="${housingKindCd == '02'}">
			<c:set var="housingKindName" value="house" />
		</c:if>
		<c:if test="${housingKindCd == '03'}">
			<c:set var="housingKindName" value="ground" />
		</c:if>

		<!--<div class="linkArea clearfix">
			<dl class="rerefineLink01 clearfix">
				<dt><span>市区町村</span></dt>
				<dd>
					<p>指定なし</p>
					<p><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindCd}"/>/<c:out value="${prefCd}"/>/area/">市区町村を絞り込む</a></p>
				</dd>
			</dl>

			<dl class="rerefineLink02 clearfix">
				<dt><span>沿&nbsp;線</span></dt>
				<dd>
					<p>指定なし</p>
					<p><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindCd}"/>/<c:out value="${prefCd}"/>/route/">沿線を絞り込む</a></p>
				</dd>
			</dl>
		</div>-->


		<table class="tableType3">
			<tbody>
				<tr>
					<th>予算</th>
					<td><p>
					<select id="priceHidden" name="priceHidden" style="display: none">
	                    <dm3lookup:lookupForEach lookupName="price">
	                        <c:if test="${value != '10000'}">
	                        	<option value="<c:out value="${key}"/>"><c:out value="${value}"/>万</option>
	                        </c:if>

							<c:if test="${value == '10000'}">
	                        	<option value="<c:out value="${key}"/>"><c:out value="1億"/></option>
	                        </c:if>
						</dm3lookup:lookupForEach>
                    </select>

					<select id="priceLower" name="priceLower" onchange="changePriceLower('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
						<option value="">下限なし</option>
	                    <dm3lookup:lookupForEach lookupName="price">
	                        <c:if test="${value != '10000'}">
	                        	<option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPriceLower() == key}"> selected</c:if> ><c:out value="${value}"/>万</option>
	                        </c:if>

							<c:if test="${value == '10000'}">
	                        	<option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPriceLower() == key}"> selected</c:if> ><c:out value="1億"/></option>
	                        </c:if>
						</dm3lookup:lookupForEach>
                    </select>

					<span class="W4em">円～</span>

					<select id="priceUpper" name="priceUpper" onchange="changePriceUpper('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
						<option value="">上限なし</option>
	                    <dm3lookup:lookupForEach lookupName="price">
	                        <c:if test="${value != '10000'}">
	                        	<option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPriceUpper() == key}"> selected</c:if> ><c:out value="${value}"/>万</option>
	                        </c:if>

							<c:if test="${value == '10000'}">
	                        	<option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPriceUpper() == key}"> selected</c:if> ><c:out value="1億"/></option>
	                        </c:if>
						</dm3lookup:lookupForEach>
                    </select>

					円</p>

					<c:if test="${housingKindCd != '03'}">
						<p class="reformCheck"><label><input id="reformPriceCheck" name="reformPriceCheck" type="checkbox" value="<c:out value="${housingListMationForm.getReformPriceCheck()}"/>"
						<c:if test="${housingListMationForm.getReformPriceCheck() == 'on'}"> checked</c:if> onclick="clickReformCheck(this, '<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">リフォーム価格込みで検索する</label></p></td>
					</c:if>
				</tr>

				<c:if test="${housingKindCd == '01'}">
					<tr>
						<th>専有面積</th>
						<td><p>
							<select id="personalAreaHidden" name="personalAreaHidden" style="display: none">
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>"><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>

							<select id="personalAreaLower" name="personalAreaLower" onchange="changePersonalAreaLower('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">下限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPersonalAreaLower() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
							<span class="W4em">～</span>

							<select id="personalAreaUpper" name="personalAreaUpper"  onchange="changePersonalAreaUpper('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">上限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getPersonalAreaUpper() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
						</p></td>
					</tr>
				</c:if>

				<c:if test="${housingKindCd != '01'}">
					<tr>
						<th>建物面積</th>
						<td><p>
							<select id="buildingAreaHidden" name="buildingAreaHidden" style="display: none">
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>"><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>

							<select id="buildingAreaLower" name="buildingAreaLower" onchange="changeBuildingAreaLower('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">下限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getBuildingAreaLower() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
							<span class="W4em">～</span>
							<select id="buildingAreaUpper" name="buildingAreaUpper" onchange="changeBuildingAreaUpper('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">上限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getBuildingAreaUpper() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
						</p></td>
					</tr>
					<tr>
						<th>土地面積</th>
						<td><p>
							<select id="landAreaHidden" name="landAreaHidden" style="display: none">
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>"><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
							<select id="landAreaLower" name="landAreaLower" onchange="changeLandAreaLower('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">下限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getLandAreaLower() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
							<span class="W4em">～</span>
							<select id="landAreaUpper" name="landAreaUpper" onchange="changeLandAreaUpper('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">
								<option value="">上限なし</option>
			                    <dm3lookup:lookupForEach lookupName="area">
			                        <option value="<c:out value="${key}"/>" <c:if test="${housingListMationForm.getLandAreaUpper() == key}"> selected</c:if>><c:out value="${value}"/>m&sup2;</option>
								</dm3lookup:lookupForEach>
	                    	</select>
						</p></td>
					</tr>
				</c:if>

<c:if test="${housingKindCd != '03'}">
				<tr>
					<th>間取り</th>
					<td>

					<ul>
						<c:set var="layoutValue" value="" />
						<c:set var="layoutCount" value="0" />

						<dm3lookup:lookupForEach lookupName="layoutCd">
						<c:set var="layoutCount" value="${ layoutCount +1  }" />
	                        <li><label>

	                        <input type="checkBox" id="layoutCd" name="layoutCd"  value="<c:out value="${key}"/>"
		                        <c:forEach items="${housingListMationForm.getKeyLayoutCd()}" varStatus="LayoutCd">
									<c:if test="${housingListMationForm.getKeyLayoutCd().split(',')[LayoutCd.index] == key}"> checked
										<c:set var="layoutValue" value="${ layoutValue},${key}" />
									</c:if>
		                        </c:forEach>
	                        onclick="clickLayoutCd('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"/>
	                        <c:out value="${value}"/></label></li>
						</dm3lookup:lookupForEach>

						<c:if test="${empty layoutValue}">
							<input type="hidden" id="layoutCdArray" name="layoutCdArray" value="<c:out value="${layoutValue}"/>" onchange="changeLayoutCd();"/>
						</c:if>
						<c:if test="${!empty layoutValue}">
							<input type="hidden" id="layoutCdArray" name="layoutCdArray" value="<c:out value="${layoutValue.substring(1)}"/>" onchange="changeLayoutCd();"/>
						</c:if>
					</ul>
					</td>
				</tr>

				<tr>
					<th>築年数</th>
					<td>
					<ul>
						<c:set var="compdateCount" value="0" />
						<c:set var="compdateValue" value="" />
						<dm3lookup:lookupForEach lookupName="compDate">
						<c:set var="compdateCount" value="${ compdateCount +1  }" />
	                        <li><label><input type="radio" id="keyCompDate" name="keyCompDate" onclick="clickCompDate(this, '<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');" value="${key}"
	                         <c:if test="${housingListMationForm.getKeyCompDate() == key}"> checked
	                         	<c:set var="compdateValue" value="${key}" />
	                         </c:if>/><c:out value="${value}"/></label></li>
						</dm3lookup:lookupForEach>
						<input type="hidden" id="keyCompDateArray" value="<c:out value="${compdateValue}"/>" name="keyCompDateArray"/>
					</ul>
					</td>
				</tr>

				<tr>
					<th>おすすめのポイント</th>
					<td>
					<ul>
						<c:set var="iconCountValue" value="" />
						<c:set var="iconCount" value="0" />

						<dm3lookup:lookupForEach lookupName="recommend_point_icon_list">
							<c:set var="iconCount" value="${ iconCount +1  }" />
	                        <li><label><input type="checkBox" id="iconCd" name="iconCd"  value="<c:out value="${key}"/>"
	                        	<c:forEach items="${housingListMationForm.getKeyIconCd()}" varStatus="iconCd">
									<c:if test="${housingListMationForm.getKeyIconCd().split(',')[iconCd.index] == key}"> checked
										<c:set var="iconCountValue" value="${ iconCountValue},${key}" />
									</c:if>
		                        </c:forEach>
	                        onclick="clickIconCd('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"/>
	                        <c:out value="${value}"/></label></li>
						</dm3lookup:lookupForEach>

						<c:if test="${empty iconCountValue}">
							<input type="hidden" id="iconCdArray" name="iconCdArray" value="<c:out value="${iconCountValue}"/>"/>
						</c:if>
						<c:if test="${!empty iconCountValue}">
							<input type="hidden" id="iconCdArray" name="iconCdArray" value="<c:out value="${iconCountValue.substring(1)}"/>"/>
						</c:if>
					</ul>

					</td>
				</tr>
</c:if>
			</tbody>
		</table>

		<div class="btnArea clearfix">
			<p class="resultNum"><span id="housingDtlListSize" class="totalNum">

				<c:out value="${housingDtlListSize}"/>

			</span>件見つかりました。</p>
			<ul>
				<c:set var="urlPath" value="/buy/${housingKindName}/${prefCd}/list/" />
				<c:if test="${housingListMationForm.getKeyAddressCd() != null}">
					<c:set var="addressUrl" value="${housingListMationForm.getKeyAddressCd()}" />
					<c:set var="urlPath" value="/buy/${housingKindName}/${prefCd}/area/${addressUrl }/" />
				</c:if>
				<c:if test="${housingListMationForm.getKeyRouteCd() != null}">
					<c:set var="routeCd" value="${housingListMationForm.getKeyRouteCd()}" />
					<c:set var="urlPath" value="/buy/${housingKindName}/${prefCd}/route/${routeCd}/" />

					<c:if test="${housingListMationForm.getKeyStationCd() != null}">
						<c:set var="urlPath" value="${urlPath}station/${housingListMationForm.getKeyStationCd() }/" />
					</c:if>

				</c:if>

				<c:if test="${housingDtlListSize != '0'}">
					<li class="btnBlack01"><button id="search" onclick="javascript:searchLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/><c:out value="?selectedPage=1"/>','<c:out value="${housingKindCd}"/>');">この条件で絞り込む</button></li>
				</c:if>
				<c:if test="${housingDtlListSize == '0'}">
					<li class="btnBlack01"><button id="search" onclick="javascript:searchLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/><c:out value="?selectedPage=1"/>','<c:out value="${housingKindCd}"/>');">再検索する</button></li>
				</c:if>

				<li class="btnGray01"><a href="javascript:clearList('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${layoutCount}"/>', '<c:out value="${compdateCount}"/>', '<c:out value="${iconCount}"/>', '<c:out value="${housingKindCd}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">この条件を<br class="SPdisplayBlock">クリアする</a></li>
			</ul>
		</div>
	</div>


	<div class="rerefineBox02">
		<p class="title">こだわり条件で絞り込む</p>

		<input type="hidden" id="partSrchCdWalkArray" name="partSrchCdWalkArray" value="<c:out value="${housingListMationForm.getPartSrchCdWalkArray()}"/>"/>
		<input type="hidden" id="partSrchCdArray" name="partSrchCdArray" value="<c:out value="${housingListMationForm.getPartSrchCdArray()}"/>"/>
		<input type="hidden" id="partSrchCdFloorArray" name="partSrchCdFloorArray" value="<c:out value="${housingListMationForm.getPartSrchCdFloorArray()}"/>"/>
		<table class="tableType3">
			<tbody>
				<tr>
					<th>駅徒歩</th>
					<td>
					<ul>
						<c:set var="partSrchCdWalkCount" value="1" />
						<c:forEach var="searchPartSrchMst" items="${searchPartSrchMst}" varStatus="EditItem">
							<c:if test="${searchPartSrchMst.partSrchCd.substring(0, 1) == 'S'}">
							<c:set var="partSrchCdWalkCount" value="${ partSrchCdWalkCount +1  }" />
								<li><label>
								<input id="partSrchCdWalk" name="partSrchCdWalk" type="radio" value="<c:out value="${searchPartSrchMst.partSrchCd}"/>"
									<c:if test="${housingListMationForm.getPartSrchCdWalkArray() == searchPartSrchMst.partSrchCd}">checked</c:if>
								onclick="clickPartSrchCdWalk('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"><c:out value="${searchPartSrchMst.partSrchName}"/></label></li>
							</c:if>
						</c:forEach>

						<li><label>
						<input id="partSrchCdWalk" name="partSrchCdWalk" type="radio" value="999"
							<c:if test="${housingListMationForm.getPartSrchCdWalkArray() == '999'}">checked</c:if>
						 onclick="clickPartSrchCdWalk('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">指定なし</label></li>
					</ul>
					</td>
				</tr>

<c:set var="partSrchCdList" value="${housingListMationForm.getPartSrchCdArray().split(',')}" />

<c:if test="${housingKindCd != '03'}">
				<tr>
					<th>物件画像情報</th>
					<td>
					<ul>
						<c:set var="partSrchCdCount" value="0" />
						<c:forEach var="searchPartSrchMst" items="${searchPartSrchMst}" varStatus="EditItem">
							<c:if test="${searchPartSrchMst.partSrchCd.substring(0, 1) == 'I'}">
							<c:set var="partSrchCdCount"  value="${ partSrchCdCount +1  }" />
								<li><label>


								<input id="partSrchCd" type="checkbox" value="<c:out value="${searchPartSrchMst.partSrchCd}"/>"
									<c:forEach items="${partSrchCdList}" varStatus="EditItem">
										<c:if test="${partSrchCdList[EditItem.index] == searchPartSrchMst.partSrchCd}" >checked </c:if>
									</c:forEach>
								onclick="clickPartSrchCd('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"><c:out value="${searchPartSrchMst.partSrchName}"/></label></li>
							</c:if>
						</c:forEach>
					</ul>
					</td>
				</tr>
</c:if>
				<tr>
					<th>引渡時期</th>
					<td>
					<ul>
						<li><label><input id="moveinTiming" name="moveinTiming" value="<c:out value="${housingListMationForm.getMoveinTiming()}"/>" onclick="clickMoveinTiming(this, '<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"
								   <c:if test="${housingListMationForm.getMoveinTiming() == '01'}"> checked</c:if> type="checkbox">即時</label></li>
					</ul>
					</td>
				</tr>
<c:if test="${housingKindCd == '01'}">
				<tr>
					<th>所在階数</th>
					<td>
					<ul>
						<c:set var="partSrchCdFloorCount" value="0" />
						<c:forEach var="searchPartSrchMst" items="${searchPartSrchMst}" varStatus="EditItem">
							<c:if test="${searchPartSrchMst.partSrchCd.substring(0, 1) == 'F'}">
							<c:set var="partSrchCdFloorCount" value="${ partSrchCdFloorCount +1  }" />
								<li><label>

								<c:set var="partSrchCdFloorList" value="${housingListMationForm.getPartSrchCdFloorArray().split(',')}" />

								<input name="partSrchCdFloor" id="partSrchCdFloor" type="checkbox" value="<c:out value="${searchPartSrchMst.partSrchCd}"/>"
									<c:forEach items="${partSrchCdFloorList}" varStatus="EditItem">
										<c:if test="${partSrchCdFloorList[EditItem.index] == searchPartSrchMst.partSrchCd}" >checked </c:if>
									</c:forEach>
								onclick="clickPartSrchCdFloor('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"><c:out value="${searchPartSrchMst.partSrchName}"/></label></li>
							</c:if>
						</c:forEach>
					</ul>
					</td>
				</tr>
</c:if>
<c:if test="${housingKindCd != '03'}">
				<tr>
					<th>物件特長</th>
					<td>
					<ul>
						<c:if test="${housingKindCd == '01'}">
							<c:set var="equipName" value="hosuing_equip_mansion" />
						</c:if>
						<c:if test="${housingKindCd == '02'}">
							<c:set var="equipName" value="hosuing_equip_house" />
						</c:if>

						<dm3lookup:lookupForEach lookupName="${equipName}">

								<c:set var="partSrchCdCount" value="${ partSrchCdCount +1  }" />
								<li><label>
								<input name="partSrchCd" id="partSrchCd" type="checkbox" value="<c:out value="${key}"/>"
									<c:forEach items="${partSrchCdList}" varStatus="EditItem">
										<c:if test="${partSrchCdList[EditItem.index] == key}" >checked </c:if>
									</c:forEach>
								onclick="clickPartSrchCd('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');"><c:out value="${value}"/></label></li>

						</dm3lookup:lookupForEach>

					</ul>
					</td>
				</tr>
</c:if>
				<tr>
					<th>物件番号</th>
					<td><input class="buildingNum" id="keyHousingCd" onchange="getHousingCount('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');" name="keyHousingCd" type="text" placeholder="半角英数で入力してください" value="<c:out value="${housingListMationForm.getKeyHousingCd()}"/>" ></td>
				</tr>
			</tbody>
		</table>

		<div class="btnArea clearfix">
			<p class="resultNum"><span id="housingDtlListSizePart" class="totalNum">

			<c:out value="${housingDtlListSize}"/>

			</span>件見つかりました。</p>
			<ul>

				<c:if test="${housingDtlListSize != '0'}">
					<li class="btnBlack01"><button id="search" onclick="javascript:searchLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/><c:out value="?selectedPage=1"/>', '<c:out value="${housingKindCd}"/>');">この条件で絞り込む</button></li>
				</c:if>
				<c:if test="${housingDtlListSize == '0'}">
					<li class="btnBlack01"><button id="search" onclick="javascript:searchLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/><c:out value="?selectedPage=1"/>','<c:out value="${housingKindCd}"/>');">再検索する</button></li>
				</c:if>

				<li class="btnGray01"><a id="clearButton" href="javascript:clearKodawariList('<c:out value="${housingListMationForm.getKeyPrefCd()}"/>', '<c:out value="${partSrchCdWalkCount}"/>', '<c:out value="${partSrchCdCount}"/>', '<c:out value="${partSrchCdFloorCount}"/>', '<c:out value="${housingKindCd}"/>', '<c:out value="${housingKindName}"/>', '<c:out value="${pageContext.request.contextPath}"/>');">この条件を<br class="SPdisplayBlock">クリアする</a></li>
			</ul>
		</div>
	</div>
	<c:if test="${housingDtlListSize == '0'}">
			</div>
		</div>
	</c:if>
</div>

<!--/flexBlockA01 -->
