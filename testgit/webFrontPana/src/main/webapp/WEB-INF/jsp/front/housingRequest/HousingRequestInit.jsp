<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
物件リクエスト｜<c:out value="${commonParameters.panaReSmail}"/>
</title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/css/building.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>buy/js/buy.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.tooltipster.min.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

<script type ="text/javascript">
	function linkToUrl(url) {
		document.inputForm.action=url;
		document.inputForm.submit();
	}

	function radioChangeMansion(housingKindCd, prefCd){
		if(housingKindCd == '02'){
			$(".refineBlock div.mansion, .refineBlock div.land").hide();
		}else if(housingKindCd == '03'){
			$(".refineBlock div.mansion, .refineBlock div.house").hide();
		}else{
			$(".refineBlock div.house, .refineBlock div.land").hide();
		}

		$("#radioForType li input").on("change", function(){
			if($(this).prop('checked')){
				$(".refineBlock div").hide();
                var selecttype = $("#radioForType li input").index($(this));
                     $('.refineBlock').find('div').eq(selecttype).show();
			}
		});
		if(prefCd != ""){
			$(".SwitchBox .mask").hide();
		}else{
			$(".SwitchBox .mask").show();
		}
	}

	$(function(){
		$("#prefCd").on("change", function(){
			var prefectures = $(this).find('option:selected').text()
				if(prefectures !== '選択してください'){
				$(".SwitchBox .mask").hide();
				} else {
				$(".SwitchBox .mask").show();
			}
		});
	});

	function clearBtnClick(layoutCount, compdateCount, iconCount){

		$(".refineBlock div.mansion").show();
		$(".refineBlock div.house, .refineBlock div.land").hide();
		document.getElementById("radioMansion").checked = true;
		$(".SwitchBox .mask").show();
		document.getElementById("prefCd").value = "";

		// 予算
		document.getElementById("priceLowerMansion").value="";
		document.getElementById("priceUpperMansion").value="";

		document.getElementById("priceLowerHouse").value="";
		document.getElementById("priceUpperHouse").value="";

		document.getElementById("priceLowerLand").value="";
		document.getElementById("priceUpperLand").value="";

		// 専有面積
		document.getElementById("personalAreaLowerMansion").value="";
		document.getElementById("personalAreaUpperMansion").value="";

		// 建物面積
		document.getElementById("buildingAreaLowerHouse").value="";
		document.getElementById("buildingAreaUpperHouse").value="";
		document.getElementById("buildingAreaLowerLand").value="";
		document.getElementById("buildingAreaUpperLand").value="";

		// 土地面積
		document.getElementById("landAreaLowerHouse").value="";
		document.getElementById("landAreaUpperHouse").value="";
		document.getElementById("landAreaLowerLand").value="";
		document.getElementById("landAreaUpperLand").value="";

		document.getElementById("reformCheckMansion").checked = false;
		document.getElementById("reformCheckHouse").checked = false;

		for(var i=0;i<layoutCount;i++){
			document.inputForm.layoutCdMansion[i].checked = false;
			document.inputForm.layoutCdHouse[i].checked = false;
		}
		for(var i=0;i<compdateCount;i++){
			document.inputForm.compDateMansion[i].checked = false;
			document.inputForm.compDateHouse[i].checked = false;
		}
		for(var i=0;i<iconCount;i++){
			document.inputForm.iconCdMansion[i].checked = false;
			document.inputForm.iconCdHouse[i].checked = false;
		}

	}

	function clickReformCheckMansion(obj) {

		$.ajax({
			success: function(){
				if(obj.checked){
					$("#reformCheckMansion").attr("value", "1");
				}else{
					$("#reformCheckMansion").attr("value", "");
				}
			}
		});
	}

	function clickReformCheckHouse(obj) {

		$.ajax({
			success: function(){
				if(obj.checked){
					$("#reformCheckHouse").attr("value", "1");
				}else{
					$("#reformCheckhouse").attr("value", "");
				}
			}
		});
	}

</script>

</head>
<body onload="radioChangeMansion('<c:out value="${housingRequestForm.getHousingKindCd()}"/>', '<c:out value="${housingRequestForm.getPrefCd()}"/>');">
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />

<!--[if lte IE 9]><script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=REQ-01-00" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">物件リクエスト</h1>
			</div><!-- /.headingBlockA01 -->
			<nav class="stepChartNav step01">
				<ul>
					<li class="current"><span>入力</span></li>
					<li><span>確認</span></li>
					<li><span>完了</span></li>
				</ul>
			</nav>
			<p><c:import url="/WEB-INF/front/default_jsp/include/validationerrors.jsh" /></p>
			<form action="comfirm" method="post" name="inputForm">
				<input type="hidden" name="housingRequestId" value="<c:out value="${housingRequestForm.getHousingRequestId()}"/>">
				<input type="hidden" name="model" value="<c:out value="${housingRequestForm.getModel()}"/>"/>

				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件種別</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<ul class="listTypeA01" id="radioForType">
							<dm3lookup:lookupForEach lookupName="hosuing_kind">
								<li><label><input type="radio" id="radioMansion"
								name="housingKindCd" value="<c:out value="${key}"/>"
								<c:if test="${empty housingRequestForm.getHousingKindCd() && key=='01'}">checked</c:if>
								<c:if test="${housingRequestForm.getHousingKindCd() == key}">checked</c:if>><c:out value="${value}"/></label></li>
							</dm3lookup:lookupForEach>
						</ul>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00" id="selectSwitchArea">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件所在地</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>都道府県<span class="mustIcon">必須</span></th>
								<td>
									<div>
										<select name="prefCd" id="prefCd">
											<option selected="selected" value="">選択してください</option>
											<c:forEach  var="prefMstInfo" items="${prefMstList}" varStatus="prefMstListItem">
												<option value="<c:out value="${prefMstInfo.getPrefCd()}"/>" <c:if test="${housingRequestForm.getPrefCd() == prefMstInfo.getPrefCd()}">selected</c:if>><c:out value="${prefMstInfo.getPrefName()}"/></option>
											</c:forEach>
										</select>
										</div>
								</td>
							</tr>
							<tr style="display: none">
								<th class="normal">※「市区町村」または<br class="SPdisplayNone">「沿線」の、<br class="SPdisplayBlock">どちらかで<br class="SPdisplayNone">検索してください。</th>
								<td class="pr00 spPb00">
									<div class="SwitchBox">
										<ul class="layoutBox02">
											<li>
												<div class="layoutInner">
													<p class="ttl">市区町村から検索</p>
													<dl>
														<dt><span>市区町村</span></dt>
														<dd><a href="javascript:linkToUrl('../addressMst/addressMstSecrch');">市区町村を<br class="SPdisplayBlock">絞り込む</a></dd>
													</dl>
													<p class="txt01">現在の設定：<br class="SPdisplayBlock">指定なし</p>
													<div class="mask"></div>
												</div>
											</li>
											<li>
												<div class="layoutInner">
													<p class="ttl">沿線から検索</p>
													<dl>
														<dt><span>沿線</span></dt>
														<dd><a href="javascript:linkToUrl('../addressMst/addressMstSecrch');">沿線を<br class="SPdisplayBlock">絞り込む</a></dd>
													</dl>
													<p class="txt01">現在の設定：<br class="SPdisplayBlock">指定なし</p>
													<div class="mask"></div>
												</div>
											</li>
										</ul>
									</div>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->

				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">基本条件</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="listPage">
						<div class="refineBlock">
								<div class="rerefineBox01 mansion">
									<table class="tableType3">
									<tbody>
										<tr>
											<th>予算</th>
											<td><p>
											<select id="priceLowerMansion" name="priceLowerMansion">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">
							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerMansion() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
							                        </c:if>

													<c:if test="${value == '10000'}">
							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerMansion() == key}">selected</c:if> ><c:out value="1億"/></option>
							                        </c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											<span class="W4em">円 ～</span>
											<select id="priceUpperMansion" name="priceUpperMansion">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">
						                        		<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperMansion() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
													</c:if>
													<c:if test="${value == '10000'}">
														<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperMansion() == key}">selected</c:if> ><c:out value="1億"/></option>
													</c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											円</p>
											<p class="reformCheck"><label><input id="reformCheckMansion" name="reformCheckMansion" type="checkbox"
											<c:if test="${housingRequestForm.getReformCheckMansion() == '1'}">checked</c:if> value="<c:out value="${housingRequestForm.getReformCheckMansion()}"/>"
											onclick="clickReformCheckMansion(this);">リフォーム価格込みで検索する</label></p></td>
										</tr>
										<tr>
											<th>専有面積</th>
											<td><p>
											<select id="personalAreaLowerMansion" name="personalAreaLowerMansion">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPersonalAreaLowerMansion() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											<span class="W4em">～</span>

											<select id="personalAreaUpperMansion" name="personalAreaUpperMansion">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPersonalAreaUpperMansion() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											</p></td>
										</tr>
										<tr>
											<th>間取り</th>
											<td>
											<ul>
												<c:set var="layoutCount" value="0" />
												<dm3lookup:lookupForEach lookupName="layoutCd">
												<c:set var="layoutCount" value="${ layoutCount +1  }" />
							                        <li><label>
							                        	<input type="checkBox" id="layoutCdMansion" name="layoutCdMansion"  value="<c:out value="${key}"/>"
							                        		<c:forEach items="${housingRequestForm.getLayoutCdMansion()}" varStatus="LayoutCd">
																<c:if test="${housingRequestForm.getLayoutCdMansion()[LayoutCd.index] == key}"> checked</c:if>
									                        </c:forEach>
							                        	/> <c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
										<tr>
											<th>築年数</th>
											<td>
											<ul>
												<c:set var="compdateCount" value="0" />
												<dm3lookup:lookupForEach lookupName="compDate">
												<c:set var="compdateCount" value="${ compdateCount +1  }" />
							                        <li><label>
							                        	<input type="radio" id="compDateMansion" name="compDateMansion" value="<c:out value="${key}"/>"
							                        	<c:if test="${housingRequestForm.getCompDateMansion() == key}">checked</c:if>
							                        	/><c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
										<tr>
											<th>おすすめのポイント</th>
											<td>
											<ul>
												<c:set var="iconCount" value="0" />
												<dm3lookup:lookupForEach lookupName="recommend_point_icon_list">
												<c:set var="iconCount" value="${ iconCount +1  }" />
							                        <li><label>
							                        	<input type="checkBox" id="iconCdMansion" name="iconCdMansion"
							                        	 	<c:forEach items="${housingRequestForm.getIconCdMansion()}" varStatus="iconCd">
																<c:if test="${housingRequestForm.getIconCdMansion()[iconCd.index] == key}">
							                        	 			checked
							                        	 		</c:if>
						                       				</c:forEach>
						                       			value="<c:out value="${key}"/>" /><c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- / .rerefineBox01 --></div>

								<div class="rerefineBox01 house">
									<table class="tableType3">
									<tbody>
										<tr>
											<th>予算</th>
											<td><p>
											<select id="priceLowerHouse" name="priceLowerHouse">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">
							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerHouse() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
							                        </c:if>

													<c:if test="${value == '10000'}">
							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerHouse() == key}">selected</c:if> ><c:out value="1億"/></option>
							                        </c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											<span class="W4em">円 ～</span>
											<select id="priceUpperHouse" name="priceUpperHouse">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">
						                        		<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperHouse() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
													</c:if>
													<c:if test="${value == '10000'}">
														<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperHouse() == key}">selected</c:if> ><c:out value="1億"/></option>
													</c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											円</p>
											<p class="reformCheck"><label><input id="reformCheckHouse" name="reformCheckHouse" type="checkbox"
			                        		<c:if test="${housingRequestForm.getReformCheckHouse() == '1'}">checked</c:if> value="<c:out value="${housingRequestForm.getReformCheckHouse()}"/>"
											onclick="clickReformCheckHouse(this);">リフォーム価格込みで検索する</label></p></td>
										</tr>
										<tr>
											<th>建物面積</th>
											<td><p>
											<select id="buildingAreaLowerHouse" name="buildingAreaLowerHouse">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getBuildingAreaLowerHouse() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											<span class="W4em">～</span>
											<select id="buildingAreaUpperHouse" name="buildingAreaUpperHouse">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getBuildingAreaUpperHouse() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											</p></td>
										</tr>
										<tr>
											<th>土地面積</th>
											<td><p>
											<select id="landAreaLowerHouse" name="landAreaLowerHouse">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getLandAreaLowerHouse() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											<span class="W4em">～</span>
											<select id="landAreaUpperHouse" name="landAreaUpperHouse">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getLandAreaUpperHouse() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											</p></td>
										</tr>
										<tr>
											<th>間取り</th>
											<td>
											<ul>
												<dm3lookup:lookupForEach lookupName="layoutCd">
							                        <li><label>
							                        	<input type="checkBox" id="layoutCdHouse" name="layoutCdHouse"  value="<c:out value="${key}"/>"
							                        	<c:forEach items="${housingRequestForm.getLayoutCdHouse()}" varStatus="LayoutCd">
															<c:if test="${housingRequestForm.getLayoutCdHouse()[LayoutCd.index] == key}"> checked</c:if>
								                        </c:forEach>
							                        	 /> <c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
										<tr>
											<th>築年数</th>
											<td>
											<ul>
												<dm3lookup:lookupForEach lookupName="compDate">
							                        <li><label>
							                        	<input type="radio" id="compDateHouse" name="compDateHouse" value="<c:out value="${key}"/>"
							                        	<c:if test="${housingRequestForm.getCompDateHouse() == key}">checked</c:if>
							                        	/><c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
										<tr>
											<th>おすすめのポイント</th>
											<td>
											<ul>
												<dm3lookup:lookupForEach lookupName="recommend_point_icon_list">
							                        <li><label>
							                        	<input type="checkBox" id="iconCdHouse" name="iconCdHouse"
							                        		<c:forEach items="${housingRequestForm.getIconCdHouse()}" varStatus="iconCd">
																<c:if test="${housingRequestForm.getIconCdHouse()[iconCd.index] == key}">
							                        	 			checked
							                        	 		</c:if>
						                       				</c:forEach>
							                        	  value="<c:out value="${key}"/>" /><c:out value="${value}"/>
							                        </label></li>
												</dm3lookup:lookupForEach>
											</ul>
											</td>
										</tr>
									</tbody>
								</table>
								<!-- / .rerefineBox01 --></div>

								<div class="rerefineBox01 land">
									<table class="tableType3">
									<tbody>
										<tr>
											<th>予算</th>
											<td><p>
											<select id="priceLowerLand" name="priceLowerLand">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">

							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerLand() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
							                        </c:if>

													<c:if test="${value == '10000'}">
							                        	<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceLowerLand() == key}">selected</c:if> ><c:out value="1億"/></option>
							                        </c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											<span class="W4em">円 ～</span>
											<select id="priceUpperLand" name="priceUpperLand">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="price">
							                        <c:if test="${value != '10000'}">
						                        		<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperLand() == key}">selected</c:if> ><c:out value="${value}"/>万</option>
													</c:if>
													<c:if test="${value == '10000'}">
														<option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getPriceUpperLand() == key}">selected</c:if> ><c:out value="1億"/></option>
													</c:if>
												</dm3lookup:lookupForEach>
						                    </select>
											円</p>
										</tr>
										<tr>
											<th>建物面積</th>
											<td><p>
											<select id="buildingAreaLowerLand" name="buildingAreaLowerLand">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getBuildingAreaLowerLand() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											<span class="W4em">～</span>
											<select id="buildingAreaUpperLand" name="buildingAreaUpperLand">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getBuildingAreaUpperLand() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											</p></td>
										</tr>
										<tr>
											<th>土地面積</th>
											<td><p>
											<select id="landAreaLowerLand" name="landAreaLowerLand">
												<option value="">下限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getLandAreaLowerLand() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											<span class="W4em">～</span>
											<select id="landAreaUpperLand" name="landAreaUpperLand">
												<option value="">上限なし</option>
							                    <dm3lookup:lookupForEach lookupName="area">
							                        <option value="<c:out value="${key}"/>" <c:if test="${housingRequestForm.getLandAreaUpperLand() == key}">selected</c:if> ><c:out value="${value}"/>m&sup2;</option>
												</dm3lookup:lookupForEach>
					                    	</select>
											</p></td>
										</tr>
									</tbody>
								</table>
								<!-- / .rerefineBox01 --></div>
						<!-- / .refineBlock --></div>
					<!-- / .listPage --></div>
				<!-- / .itemBlockA01 --></div>
				<div class="contentsInner01 mt30 mb15 spMt10 spPb10">
				<ul class="btnList01">
					<li><button type="reset" class="secondaryBtn02 pt05 pb05" onclick="clearBtnClick('<c:out value="${layoutCount}"/>', '<c:out value="${compdateCount}"/>', '<c:out value="${iconCount}"/>');">この条件をクリアする</button></li>
					<li><button type="button" class="primaryBtn01" onclick="linkToUrl('../confirm/');">設定条件を<br class="SPdisplayBlock">確認する</button></li>
				</ul>
				</div>
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>
</html>

