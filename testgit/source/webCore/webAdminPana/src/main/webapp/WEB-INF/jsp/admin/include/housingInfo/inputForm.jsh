<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
物件基本情報編集画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="inputCommand" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${housingInfoForm.command}"/>">
		<input type="hidden" name="comflg" value="<c:out value="${housingInfoForm.comflg}"/>">
		<input type="hidden" name="sysHousingCd" value="<c:out value="${housingInfoForm.sysHousingCd}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${housingInfoForm.sysBuildingCd}"/>">
		<input type="hidden" name="housingCd" value="<c:out value="${housingInfoForm.housingCd}"/>">
		<input type="hidden" name="prefName" value="<c:out value="${housingInfoForm.prefName}"/>">
		<input type="hidden" name="addressName" value="<c:out value="${housingInfoForm.addressName}"/>">
		<input type="hidden" id="prefCd" name="prefCd" value="<c:out value="${housingInfoForm.prefCd}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="15%" class="head_tr"/>
				<col width="20%"/>
				<col width="11%" class="head_tr"/>
				<col width="20%"/>
				<col width="11%" class="head_tr"/>
				<col width="23%"/>
			</colgroup>
			<tr>
				<th class="head_tr">物件番号</th>
				<td colspan="5">
					<c:out value="${housingInfoForm.housingCd}"/>
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称<font color="red">※</font></th>
				<td colspan="5">
					<input type="text" name="displayHousingName" value="<c:out value="${housingInfoForm.displayHousingName}"/>" size="45" maxlength="25" class="input2"  style="width: 98%;">
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件種別<font color="red">※</font></th>
				<td colspan="5">
					<input type="hidden" name="housingKindCd" value="<c:out value="${housingInfoForm.housingKindCd}"/>">
					<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd">
						<label >
						<input type="radio" name="housingKindCd" disabled="disabled" value="<c:out value="${key}"/>"
						<c:if test="${housingInfoForm.housingKindCd == key}">checked</c:if>><c:out value="${value}"/>
						</label>
					</dm3lookup:lookupForEach>
				</td>
			</tr>
			<tr>
				<th class="head_tr">価格<font color="red">※</font></th>
				<td><input type="text" name="price" value="<c:out value="${housingInfoForm.price}"/>" size="7" maxlength="11" class="input2 ime-disabled">円</td>
				<th class="head_tr">築年<c:if test="${housingInfoForm.housingKindCd == '02' || housingInfoForm.housingKindCd == '01'}"><font color="red">※</font></c:if></th>
				<td><input type="text" name="compDate" value="<c:out value="${housingInfoForm.compDate}"/>" size="3" maxlength="6" class="input2 ime-disabled">(yyyymm)</td>
				<th class="head_tr">間取り<c:if test="${housingInfoForm.housingKindCd == '02' || housingInfoForm.housingKindCd == '01'}"><font color="red">※</font></c:if></th>
				<td>
					<select name="layoutCd" style="width: 95%;">
					<option></option>
	                <dm3lookup:lookupForEach lookupName="layoutCd">
	                    <option value="<c:out value="${key}"/>" <c:if test="${housingInfoForm.layoutCd == key}"> selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
	                </select>
				</td>
			</tr>
			<tr rowspan="2">
				<th class="head_tr">住所<font color="red">※</font></th>
				<td colspan="5">
					<div class="inputStyle" style="width: 120px; ">〒<input type="text" name="zip" value="<c:out value="${housingInfoForm.zip}"/>" size="7" maxlength="7" class="input2 ime-disabled" style="width: 90px; "></div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="javascript:linkToUrl('../input/','address');"><span>住所検索</span></a></p>
							</div>
						</div>
					</div>
					<select name="prefCd" style="width: 70px; ">
					<option></option>
					<c:forEach items="${prefMstList}" var="prefMst">
						<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${housingInfoForm.prefCd == prefMst.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
					</c:forEach>
					</select>
					<select name="addressCd" style="width: 120px; ">
					<option></option>
					<c:forEach items="${addressMstList}" var="addressMst">
						<option value="<c:out value="${addressMst.addressCd}"/>" <c:if test="${housingInfoForm.addressCd == addressMst.addressCd}"> selected</c:if>><c:out value="${addressMst.addressName}"/></option>
					</c:forEach>
					</select>
					<input type="text" name="addressOther1" value="<c:out value="${housingInfoForm.addressOther1}"/>" size="15" maxlength="30" class="input2" style="width: 177px; ">
					<br>
					<div class="inputStyle">&nbsp;&nbsp;&nbsp;<input type="text" name="addressOther2" value="<c:out value="${housingInfoForm.addressOther2}"/>" size="46" maxlength="30" class="input2" style="width: 580px; "></div>
				</td>
			</tr>
			<tr rowspan="3">
				<th class="head_tr">最寄り駅</th>
				<td colspan="5">
					<c:forEach begin="0" end="2" varStatus="status">
					<input type="hidden" id="routeName<c:out value="${status.index + 1}"/>" name="routeName" value="<c:out value="${housingInfoForm.routeName[status.index]}"/>">
					<input type="hidden" id="stationName<c:out value="${status.index + 1}"/>" name="stationName" value="<c:out value="${housingInfoForm.stationName[status.index]}"/>">

					<input type="hidden" name="oldRouteName" value="<c:out value="${housingInfoForm.oldRouteName[status.index]}"/>">
					<input type="hidden"  name="oldStationName" value="<c:out value="${housingInfoForm.oldStationName[status.index]}"/>">
					<input type="hidden" name="oldDefaultRouteCd" value="<c:out value="${housingInfoForm.oldDefaultRouteCd[status.index]}"/>">
					<input type="hidden"  name="oldStationCd" value="<c:out value="${housingInfoForm.oldStationCd[status.index]}"/>">

					<select id="defaultRouteCd<c:out value="${status.index + 1}"/>" name="defaultRouteCd" style="width: 230px; ">
					<option value="<c:out value="${housingInfoForm.oldDefaultRouteCd[status.index]}"/>"><c:out value="${housingInfoForm.oldRouteName[status.index]}" /></option>
					<c:forEach items="${routeMstList}" var="routeMst">
						<option value="<c:out value="${routeMst.routeCd}"/>" <c:if test="${housingInfoForm.defaultRouteCd[status.index] == routeMst.routeCd}"> selected</c:if>><c:out value="${routeMst.routeName}"/></option>
					</c:forEach>
					</select>

					<select id="stationCd<c:out value="${status.index + 1}"/>" name="stationCd" style="width: 90px; ">
					<option value="<c:out value="${housingInfoForm.oldStationCd[status.index]}"/>"><c:out value="${housingInfoForm.oldStationName[status.index]}"/></option>
					<c:forEach items="${stationMstList[status.index]}" var="stationMst">
						<option value="<c:out value="${stationMst.stationCd}"/>" <c:if test="${housingInfoForm.stationCd[status.index] == stationMst.stationCd}"> selected</c:if>><c:out value="${stationMst.stationName}"/></option>
					</c:forEach>
					</select>

					<input type="text" name="busCompany" value="<c:out value="${housingInfoForm.busCompany[status.index]}"/>" size="20" maxlength="40" class="input2" style="width: 170px; ">
					徒歩<input type="text" name="timeFromBusStop" value="<c:out value="${housingInfoForm.timeFromBusStop[status.index]}"/>" size="4" maxlength="3" class="input2 ime-disabled" style="width: 50px; ">分
					<br>
					</c:forEach>

				</td>
			</tr>
			<tr>
				<th class="head_tr">建物面積<c:if test="${housingInfoForm.housingKindCd == '02'}"><font color="red">※</font></c:if></th>
				<td colspan="3">
					<div class="inputStyle"><input type="text" name="buildingArea" id ="buildingArea" value="<c:out value="${housingInfoForm.buildingArea}"/>" size="5" maxlength="8" class="input2 ime-disabled"  style="width: 95px;">m²&nbsp;</div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="#" id="areaCon1" name="areaCon1"><span>坪計算</span></a></p>
							</div>
						</div>
					</div>
					約<input type="text" name="buildingAreaCon" id ="buildingAreaCon" value="<c:out value="${housingInfoForm.buildingAreaCon}"/>" size="5" class="input2" readonly="readonly" style="width: 95px;">坪
				</td>
				<th class="head_tr">コメント</th>
				<td>
					<input type="text" name="buildingAreaMemo" value="<c:out value="${housingInfoForm.buildingAreaMemo}"/>" size="8" maxlength="10" class="input2" style="width: 95%;">
				</td>
			</tr>
			<tr>
				<th class="head_tr">土地面積<c:if test="${housingInfoForm.housingKindCd == '02' || housingInfoForm.housingKindCd == '03'}"><font color="red">※</font></c:if></th>
				<td colspan="3">
					<div class="inputStyle"><input type="text" name="landArea" id ="landArea" value="<c:out value="${housingInfoForm.landArea}"/>" size="5" maxlength="8" class="input2 ime-disabled" style="width: 95px;">m²&nbsp;</div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="#" id="areaCon2" name="areaCon2"><span>坪計算</span></a></p>
							</div>
						</div>
					</div>
					約<input type="text" name="landAreaCon" id ="landAreaCon"  value="<c:out value="${housingInfoForm.landAreaCon}"/>" size="5" class="input2" readonly="readonly" style="width: 95px;">坪
				</td>
				<th class="head_tr">コメント</th>
				<td>
					<input type="text" name="landAreaMemo" value="<c:out value="${housingInfoForm.landAreaMemo}"/>" size="8" maxlength="10" class="input2" style="width: 95%;">
				</td>
			</tr>
			<tr>
				<th class="head_tr">専有面積<c:if test="${housingInfoForm.housingKindCd == '01'}"><font color="red">※</font></c:if></th>
				<td colspan="3">
					<div class="inputStyle"><input type="text" name="personalArea" id ="personalArea"  value="<c:out value="${housingInfoForm.personalArea}"/>" size="5" maxlength="8" class="input2 ime-disabled" style="width: 95px;">m²&nbsp;</div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="#" id="areaCon3" name="areaCon3"><span>坪計算</span></a></p>
							</div>
						</div>
					</div>
					約<input type="text" name="personalAreaCon" id ="personalAreaCon"  value="<c:out value="${housingInfoForm.personalAreaCon}"/>" size="5" class="input2" readonly="readonly" style="width: 95px;">坪
				</td>
				<th class="head_tr">コメント</th>
				<td>
					<input type="text" name="personalAreaMemo" value="<c:out value="${housingInfoForm.personalAreaMemo}"/>" size="8" maxlength="10" class="input2" style="width: 95%;">
				</td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $("input[name='prefName']").attr("value",$("select[name='prefCd']").find("option:selected").text());
  $("input[name='addressName']").attr("value",$("select[name='addressCd']").find("option:selected").text());
  $("select[name='prefCd']").change(function(){
    $("input[name='prefName']").attr("value",$("select[name='prefCd']").find("option:selected").text());
    $("input[name='addressName']").attr("value","");
 	$("input[name='addressCd']").attr("value","");
    $.getJSON("../addressMstList/",{prefCd:$("select[name='prefCd']").val()},function(dataObj){
    	$("select[name='addressCd'] option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.addressCd.add(opt);
 	   	$(dataObj.addressMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.addressCd;
			opt.text = this.addressName;
			document.inputForm.addressCd.add(opt);
      	});
    });
  });
  $("select[name='addressCd']").change(function(){
   	 $("input[name='addressName']").attr("value",$("select[name='addressCd']").find("option:selected").text());
  });
});

$(document).ready(function(){
  $("select[name='prefCd']").change(function(){
  $("input[name='prefCd']").attr("value",$("select[name='prefCd']").find("option:selected").val());

    $.getJSON("../routeMstList/",{prefCd:$("select[name='prefCd']").val()},function(dataObj){
    	$("#defaultRouteCd1 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.defaultRouteCd1.add(opt);
 	   	$(dataObj.routeMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.routeCd;
			opt.text = this.routeName;
			document.inputForm.defaultRouteCd1.add(opt);
      	});

		$("#stationCd1 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd1.add(opt);

    	$("#defaultRouteCd2 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.defaultRouteCd2.add(opt);
 	   	$(dataObj.routeMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.routeCd;
			opt.text = this.routeName;
			document.inputForm.defaultRouteCd2.add(opt);
      	});

		$("#stationCd2 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd2.add(opt);

    	$("#defaultRouteCd3 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.defaultRouteCd3.add(opt);
 	   	$(dataObj.routeMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.routeCd;
			opt.text = this.routeName;
			document.inputForm.defaultRouteCd3.add(opt);
      	});

    	$("#stationCd3 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd3.add(opt);
    });
  $("#routeName1").attr("value","");
  $("#routeName2").attr("value","");
  $("#routeName3").attr("value","");
  $("#stationName1").attr("value","");
  $("#stationName2").attr("value","");
  $("#stationName3").attr("value","");
  });
});

$(document).ready(function(){
  $("#routeName1").attr("value",$("#defaultRouteCd1").find("option:selected").text());
  $("#routeName2").attr("value",$("#defaultRouteCd2").find("option:selected").text());
  $("#routeName3").attr("value",$("#defaultRouteCd3").find("option:selected").text());
  $("#stationName1").attr("value",$("#stationCd1").find("option:selected").text());
  $("#stationName2").attr("value",$("#stationCd2").find("option:selected").text());
  $("#stationName3").attr("value",$("#stationCd3").find("option:selected").text());

  $("#defaultRouteCd1").change(function(){
    $("#routeName1").attr("value",$("#defaultRouteCd1").find("option:selected").text());

	if(document.inputForm.defaultRouteCd1.options[0].value != ""){
		document.inputForm.defaultRouteCd1.options[0] = new Option("","");
	}

    $.getJSON("../stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd1").val()},function(dataObj){
    	$("#stationCd1 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd1.add(opt);
 	   	$(dataObj.stationMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.stationCd;
			opt.text = this.stationName;
			document.inputForm.stationCd1.add(opt);
      	});
    });
    $("#stationName1").attr("value","");
  });
  $("#defaultRouteCd2").change(function(){
    $("#routeName2").attr("value",$("#defaultRouteCd2").find("option:selected").text());

    if(document.inputForm.defaultRouteCd2.options[0].value != ""){
		document.inputForm.defaultRouteCd2.options[0] = new Option("","");
	}

    $.getJSON("../stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd2").val()},function(dataObj){
    	$("#stationCd2 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd2.add(opt);
 	   	$(dataObj.stationMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.stationCd;
			opt.text = this.stationName;
			document.inputForm.stationCd2.add(opt);
      	});
    });
    $("#stationName2").attr("value","");
  });
  $("#defaultRouteCd3").change(function(){
    $("#routeName3").attr("value",$("#defaultRouteCd3").find("option:selected").text());

    if(document.inputForm.defaultRouteCd3.options[0].value != ""){
		document.inputForm.defaultRouteCd3.options[0] = new Option("","");
	}

    $.getJSON("../stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd3").val()},function(dataObj){
    	$("#stationCd3 option").remove();
     	var opt = document.createElement("option");
		opt.value = "";
		opt.text = "";
		document.inputForm.stationCd3.add(opt);
 	   	$(dataObj.stationMstList).each(function () {
	 	   	var opt = document.createElement("option");
			opt.value = this.stationCd;
			opt.text = this.stationName;
			document.inputForm.stationCd3.add(opt);
      	});
    });
    $("#stationName3").attr("value","");
  });
  $("#stationCd1").change(function(){
   	 $("#stationName1").attr("value",$("#stationCd1").find("option:selected").text());
   	 if(document.inputForm.stationCd1.options[0].value != ""){
		document.inputForm.stationCd1.options[0] = new Option("","");
	}
  });
  $("#stationCd2").change(function(){
   	 $("#stationName2").attr("value",$("#stationCd2").find("option:selected").text());
   	 if(document.inputForm.stationCd2.options[0].value != ""){
		document.inputForm.stationCd2.options[0] = new Option("","");
	}
  });
  $("#stationCd3").change(function(){
   	 $("#stationName3").attr("value",$("#stationCd3").find("option:selected").text());
   	 if(document.inputForm.stationCd3.options[0].value != ""){
		document.inputForm.stationCd3.options[0] = new Option("","");
	}
  });

  $("#stationCd1").change(function(){
   	 $("#stationName1").attr("value",$("#stationCd1").find("option:selected").text());
   	 if(document.inputForm.stationCd1.options[0].value != ""){
		document.inputForm.stationCd1.options[0] = new Option("","");
	}
  });
});

$(document).ready(function(){

    $.getJSON("../panaCalcUtil/",{area:$("#buildingArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#buildingAreaCon").attr("value",this.areaCon);
		});
    });

	$.getJSON("../panaCalcUtil/",{area:$("#landArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#landAreaCon").attr("value",this.areaCon);
		});
    });

	$.getJSON("../panaCalcUtil/",{area:$("#personalArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
    		$("#personalAreaCon").attr("value",this.areaCon);
		});
    });

 $("#areaCon1").click(function(){
    $.getJSON("../panaCalcUtil/",{area:$("#buildingArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#buildingAreaCon").attr("value",this.areaCon);
		});
    });
 });
  $("#areaCon2").click(function(){
	$.getJSON("../panaCalcUtil/",{area:$("#landArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#landAreaCon").attr("value",this.areaCon);
		});
    });
 });
  $("#areaCon3").click(function(){
	$.getJSON("../panaCalcUtil/",{area:$("#personalArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
    		$("#personalAreaCon").attr("value",this.areaCon);
		});
    });
 });

 $("#buildingArea").blur(function(){
    $.getJSON("../panaCalcUtil/",{area:$("#buildingArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#buildingAreaCon").attr("value",this.areaCon);
		});
    });
 });
  $("#landArea").blur(function(){
	$.getJSON("../panaCalcUtil/",{area:$("#landArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
	    	$("#landAreaCon").attr("value",this.areaCon);
		});
    });
 });
  $("#personalArea").blur(function(){
	$.getJSON("../panaCalcUtil/",{area:$("#personalArea").val()},function(dataObj){
		$(dataObj.areaCon).each(function () {
    		$("#personalAreaCon").attr("value",this.areaCon);
		});
    });
 });
});

function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}

// -->
</script>
