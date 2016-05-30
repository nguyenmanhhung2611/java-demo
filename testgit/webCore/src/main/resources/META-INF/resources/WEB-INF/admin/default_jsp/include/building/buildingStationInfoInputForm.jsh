<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
建物情報メンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="sysBuildingCd" value="<c:out value="${inputForm.sysBuildingCd}"/>">
		<input type="hidden" id="prefCd" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
		<c:forEach begin="0" end="2" varStatus="status">
		<br/>
		<input type="hidden" id="routeName<c:out value="${status.index + 1}"/>" name="routeName" value="<c:out value="${inputForm.routeName[status.index]}"/>">
		<input type="hidden" id="stationName<c:out value="${status.index + 1}"/>" name="stationName" value="<c:out value="${inputForm.stationName[status.index]}"/>">
		<div class="headingAreaB01 start">
			<h2>最寄り駅情報<c:out value="${status.index + 1}"/>編集</h2>
		</div>
		<table class="inputBox">
			<tr>
				<th class="head_tr">路線</th>
				<td>
					<select id="defaultRouteCd<c:out value="${status.index + 1}"/>" name="defaultRouteCd">
					<option></option>
					<c:forEach items="${routeMstList}" var="routeMst">
						<option value="<c:out value="${routeMst.routeCd}"/>" <c:if test="${inputForm.defaultRouteCd[status.index] == routeMst.routeCd}"> selected</c:if>><c:out value="${routeMst.routeName}"/></option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">駅</th>
				<td>
					<select id="stationCd<c:out value="${status.index + 1}"/>" name="stationCd">
					<option></option>
					<c:forEach items="${stationMstList[status.index]}" var="stationMst">
						<option value="<c:out value="${stationMst.stationCd}"/>" <c:if test="${inputForm.stationCd[status.index] == stationMst.stationCd}"> selected</c:if>><c:out value="${stationMst.stationName}"/></option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">最寄り駅からの手段</th>
				<td>
					<select name="wayFromStation">
					<option></option>
					<dm3lookup:lookupForEach lookupName="buildingStationInfo_wayFromStation">
        			 	<option value="${key}"<c:if test="${inputForm.wayFromStation[status.index] == key}">selected</c:if>><c:out value="${value}"/></option>
					</dm3lookup:lookupForEach>
					</select>

				</td>
			</tr>
			<tr>
				<th class="head_tr">駅からの距離</th>
				<td>
					<input type="text" name="distanceFromStation" value="<c:out value="${inputForm.distanceFromStation[status.index]}"/>" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.distanceFromStation" defaultValue="8"/>" class="input2">&nbsp;km&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">駅からの徒歩時間</th>
				<td>
					<input type="text" name="timeFromStation" value="<c:out value="${inputForm.timeFromStation[status.index]}"/>" size="3" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.timeFromStation" defaultValue="3"/>" class="input2">&nbsp;分&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス会社名</th>
				<td>
					<input type="text" name="busCompany" value="<c:out value="${inputForm.busCompany[status.index]}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.busCompany" defaultValue="40"/>" class="input2">&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バスの所要時間</th>
				<td>
					<input type="text" name="busRequiredTime" value="<c:out value="${inputForm.busRequiredTime[status.index]}"/>" size="3" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.busRequiredTime" defaultValue="3"/>" class="input2">&nbsp;分&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス停名</th>
				<td>
					<input type="text" name="busStopName" value="<c:out value="${inputForm.busStopName[status.index]}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.busStopName" defaultValue="40"/>" class="input2">&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">バス停からの徒歩時間</th>
				<td>
					<input type="text" name="timeFromBusStop" value="<c:out value="${inputForm.timeFromBusStop[status.index]}"/>" size="3" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.timeFromBusStop" defaultValue="3"/>" class="input2">&nbsp;分&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">表示順</th>
				<td>
					<input type="text" name="sortOrder" value="<c:out value="${inputForm.sortOrder[status.index]}"/>" size="3" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="buildingStationInfo.input.sortOrder" defaultValue="3"/>" class="input2">&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</c:forEach>
	</form>
</div>
<!--/flexBlockA01 -->
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type="text/javascript">
<!--
$(document).ready(function(){
  $("#routeName1").attr("value",$("#defaultRouteCd1").find("option:selected").text());
  $("#routeName2").attr("value",$("#defaultRouteCd2").find("option:selected").text());
  $("#routeName3").attr("value",$("#defaultRouteCd3").find("option:selected").text());
  $("#stationName1").attr("value",$("#stationCd1").find("option:selected").text());
  $("#stationName2").attr("value",$("#stationCd2").find("option:selected").text());
  $("#stationName3").attr("value",$("#stationCd3").find("option:selected").text());
  
  $("#defaultRouteCd1").change(function(){
    $("#routeName1").attr("value",$("#defaultRouteCd1").find("option:selected").text());
    $.getJSON("<c:out value="${pageContext.request.contextPath}"/>/top/building/stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd1").val()},function(dataObj){
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
    $.getJSON("<c:out value="${pageContext.request.contextPath}"/>/top/building/stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd2").val()},function(dataObj){
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
    $.getJSON("<c:out value="${pageContext.request.contextPath}"/>/top/building/stationMstList/",{prefCd:$("#prefCd").val(), routeCd:$("#defaultRouteCd3").val()},function(dataObj){
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
  });
  $("#stationCd2").change(function(){
   	 $("#stationName2").attr("value",$("#stationCd2").find("option:selected").text());
  });
  $("#stationCd3").change(function(){
   	 $("#stationName3").attr("value",$("#stationCd3").find("option:selected").text());
  });
});

function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}
// -->
</script>



