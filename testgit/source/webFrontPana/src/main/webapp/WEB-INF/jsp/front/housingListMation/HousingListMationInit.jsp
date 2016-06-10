<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login"%>
<!doctype html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${housingListMationForm.getKeywords()}" />"/>
<meta name="description" content="<c:out value="${housingListMationForm.getDescription()}"/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
<c:if test="${housingKindCd == '01'}">
	<c:out value="${prefName}"/>の中古マンション　物件一覧｜<c:out value="${commonParameters.panaReSmail}"/>
</c:if>

<c:if test="${housingKindCd == '02'}">
	<c:out value="${prefName}"/>の中古戸建・中古住宅　物件一覧｜<c:out value="${commonParameters.panaReSmail}"/>
</c:if>

<c:if test="${housingKindCd == '03'}">
	<c:out value="${prefName}"/>の土地　物件一覧｜<c:out value="${commonParameters.panaReSmail}"/>
</c:if>
</title>

<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/css/building.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/jquery.fancybox.css" rel="stylesheet">



<c:set var="pagingForm" value="${housingListMationForm}" scope="request" />
<c:if test="${housingKindCd == '01'}">
	<c:set var="housingKindName" value="mansion" />
</c:if>
<c:if test="${housingKindCd == '02'}">
	<c:set var="housingKindName" value="house" />
</c:if>
<c:if test="${housingKindCd == '03'}">
	<c:set var="housingKindName" value="ground" />
</c:if>
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
<c:set var="defaultUrl" value="${pageContext.request.contextPath}${urlPath}" scope="request" />
<c:import url="/WEB-INF/jsp/front/include/common/paginate.jsh" />

<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>buy/js/buy.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.tooltipster.min.js"></script>
<script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.ah-placeholder.js"></script>

<script type ="text/javascript">

	function clearPlaceholder() {
	    var val = $("#keyHousingCd").val();
	    var placeholder = $("#keyHousingCd").attr('placeholder');
	    if(val == placeholder) {
	      $("#keyHousingCd").val('');
	    }
	}

	$(document).ready(function(){

	$(".favadd").fancybox({
		fitToView : false,
		width : '450px',
		closeClick : false,
		closeBtn : false,
		openEffect : 'none',
		closeEffect : 'none'
	});
	$(".favadd").on('click', function() {
		setTimeout(function() {
			$.fancybox.close();
			$("#personalInfo .favorite").text(getFavoriteCount());
		}, 1500)
	});

	$(".recicon").fancybox({
		fitToView	: false,
		width		: '400px',
		closeClick	: false,
		openEffect	: 'none',
		closeEffect	: 'none',
		iframe		: {
			scrolling : 'no',
		}
	});

	 $("#sortOrder1").click(function(){
		 if($("#sortUpdDateValue").val() == '4'){
			 $("#sortUpdDateValue").attr("value","3");
			 $("#keyOrderType").attr("value","4");
		 }
		 else{
			 $("#sortUpdDateValue").attr("value","4");
			 $("#keyOrderType").attr("value","3");
		 }

	 });
	  $("#sortOrder2").click(function(){

			 if($("#sortPriceValue").val() == '2'){
				 $("#sortPriceValue").attr("value","1");
				 $("#keyOrderType").attr("value","2");
			 }
			 else{
				 $("#sortPriceValue").attr("value","2");
				 $("#keyOrderType").attr("value","1");
			 }
	 });
	  $("#sortOrder3").click(function(){

			 if($("#sortBuildDateValue").val() == '6'){
				 $("#sortBuildDateValue").attr("value","5");
				 $("#keyOrderType").attr("value","6");
			 }
			 else{
				 $("#sortBuildDateValue").attr("value","6");
				 $("#keyOrderType").attr("value","5");
			 }
	 });
	  $("#sortOrder4").click(function(){

			 if($("#sortWalkTimeValue").val() == '8'){
				 $("#sortWalkTimeValue").attr("value","7");
				 $("#keyOrderType").attr("value","8");
			 }
			 else{
				 $("#sortWalkTimeValue").attr("value","8");
				 $("#keyOrderType").attr("value","7");
			 }
	 });

	});

	function getFavoriteCount() {
		var arrStr = document.cookie.split(";");
		for (var i = 0; i < arrStr.length; i++) {
			var temp = arrStr[i].split("=");
			if ($.trim(temp[0]) == "favoriteCount") {
				return unescape(temp[1]);
			}
		}
	}

	function orderLinkToUrl(url, keyOrder) {
		document.inputForm.keyOrderType.value = keyOrder;
		clearPlaceholder();
		searchLinkToUrl(encodeURI(url + "?selectedPage=1"), $("#housingKindCd").val());

	}

	function linkToUrl(url, sysHousingCd) {
		document.inputForm.action=url;
		document.inputForm.sysHousingCd.value=sysHousingCd;
		document.inputForm.submit();
	}

	function areaLinkToUrl(url, housingKindCd) {

		document.inputForm.action=url;
		document.inputForm.clearFlg.value = 1;
		document.inputForm.housingKindCd.value=housingKindCd;
		document.inputForm.submit();
	}

	function orderSelect(url) {
		document.inputForm.action = url;
		document.inputForm.keyOrderType.value = $("#selKeyOrderType").val();
		document.inputForm.submit();
	}

// -->
</script>


</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>
<!--[if lte IE 9]><script src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<form action="" method="post" name="inputForm">
	<input type="hidden" name="command" value="confirmUploadCommand">
	<input type="hidden" id="housingKindCd" name="housingKindCd" value="<c:out value="${housingKindCd}"/>">
	<input type="hidden" id="keyHousingKindCd" name="keyHousingKindCd" value="<c:out value="${housingKindCd}"/>">
	<input type="hidden" id="hiddenFlg" name="hiddenFlg" value="0">
	<input type="hidden" name="keyPrefCd" value="<c:out value="${prefCd}"/>">
	<input type="hidden" name="keyAddressCd" id="keyAddressCd" value="<c:out value="${housingListMationForm.getKeyAddressCd()}"/>">
	<input type="hidden" name="keyRouteCd" id="keyRouteCd" value="<c:out value="${housingListMationForm.getKeyRouteCd()}"/>">
	<input type="hidden" name="keyStationCd" id="keyStationCd" value="<c:out value="${housingListMationForm.getKeyStationCd()}"/>">
	<input type="hidden" name="clearFlg" value="">
	<input type="hidden" name="sysHousingCd" value="">
	<input type="hidden" name="selectedPage" value="<c:out value="${housingListMationForm.getSelectedPage()}"/>">
	<input type="hidden" name="keyOrderType"  id="keyOrderType" value="<c:out value="${housingListMationForm.getKeyOrderType()}"/>">
	<div id="contents">
		<div id="contentsInner" class="listPage">

			<c:if test="${housingKindCd == '01'}">
				<c:import url="../include/common/topicPath.jsh?pattern=BUY-01-00&prefCd=${prefCd}&prefName=${prefName}&housingKindCd=01" />
			</c:if>
			<c:if test="${housingKindCd == '02'}">
				<c:import url="../include/common/topicPath.jsh?pattern=BUY-02-00&prefCd=${prefCd}&prefName=${prefName}&housingKindCd=02" />
			</c:if>
			<c:if test="${housingKindCd == '03'}">
				<c:import url="../include/common/topicPath.jsh?pattern=BUY-07-00&prefCd=${prefCd}&prefName=${prefName}&housingKindCd=03" />
			</c:if>


			<div class="section01">
					<div class="headingBlockA01 clearfix">

						<h1>
							<dm3lookup:lookupForEach lookupName="hosuing_kind">
								<c:if test="${housingKindCd == key}">
									<c:out value="${prefName}" />&nbsp;の<c:out value="${value}" />一覧
							</c:if>
							</dm3lookup:lookupForEach>

						</h1>


						<p class="linkAnotherType">
							<c:if test="${housingDtlListSize != '0'}">
								<c:set var="changeUrlPath" value="list/" />
								<c:if test="${housingListMationForm.getKeyAddressCd() != null}">
									<c:set var="addressUrl"
										value="${housingListMationForm.getKeyAddressCd()}" />
									<c:set var="changeUrlPath" value="area/${addressUrl }/" />
								</c:if>
								<c:if test="${housingListMationForm.getKeyRouteCd() != null}">
									<c:set var="routeCd"
										value="${housingListMationForm.getKeyRouteCd()}" />
									<c:set var="changeUrlPath" value="route/${routeCd}/" />

									<c:if test="${housingListMationForm.getKeyStationCd() != null}">
										<c:set var="changeUrlPath"
											value="${changeUrlPath}station/${housingListMationForm.getKeyStationCd() }/" />
									</c:if>
								</c:if>

								<c:if test="${housingKindCd == '01'}">

									<a href="<c:out value="${pageContext.request.contextPath}"/>/buy/house/<c:out value="${prefCd}"/>/<c:out value="${changeUrlPath}"/>">同じ地域の中古<br
										class="SPdisplayBlock">戸建を探す
									</a>
								</c:if>
								<c:if test="${housingKindCd == '02'}">
									<a href="<c:out value="${pageContext.request.contextPath}"/>/buy/mansion/<c:out value="${prefCd}"/>/<c:out value="${changeUrlPath}"/>">同じ地域の<br
										class="SPdisplayBlock">中古マンションを探す
									</a>
								</c:if>
							</c:if>
						</p>


						<ul class="changeArea clearfix">
							<c:if test="${housingDtlListSize != '0'}">
								<li>都道府県</li>
								<li><c:out value="${prefName}" /></li>
								<li><a
									href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/#search">都道府県を<br
										class="SPdisplayBlock">再選択する
								</a></li>
							</c:if>
						</ul>
					</div>
					<c:if test="${housingDtlListSize == '0'}">
					<div class="contentsInner01 mt30 mb50 spMt15 spMb15">
						<p class="bold mb30 spMb10 f18 spF16 center spLeft">ご希望された条件に該当する物件はございませんでした。<br>条件を変更の上、再度検索してください。</p>
					</div>
					<div class="contentsInner01 mb30 spMb00">
						<p class="center mb10">
						<a href="<c:out value="${commonParameters.resourceRootUrl}"/>buy/#search" class="secondaryBtn spMb10">買いたいTOPへ</a>
						<dm3login:hasRole roleName="mypage">
								<!-- User is logged in -->
								<a href="<c:out value="${commonParameters.resourceRootUrl}"/>mypage/request/input/" class="secondaryBtn pcMl15">物件をリクエストする</a>
							</dm3login:hasRole>
							<dm3login:hasRole roleName="mypage" negative="true">
								<!-- User is not logged in -->
								<a href="<c:out value="${commonParameters.resourceRootUrl}"/>mypage_service/" class="secondaryBtn pcMl15">物件をリクエストする</a>
						</dm3login:hasRole>
						</p>
						<p class="pcCenter spMb10 indentTextA01">※物件リクエストのご利用にはログインが必要です。</p>
					</div>
					
				</c:if>

				<div class="refineBlock">
					<c:import url="/WEB-INF/jsp/front/include/housingListMation/searchJyouken.jsh" />
					<c:if test="${buildingList.size() != '0'}">
						<div class="pagination clearfix">

							<c:set var="strBefore" value="javascript:KeyForm('" scope="request" />
							<c:set var="strAfter" value="')" scope="request" />
							<c:set var="pagingForm" value="${housingListMationForm}" scope="request" />
							<c:import url="/WEB-INF/jsp/front/include/common/housingPagingjs.jsh?pageFlg=01" />
						</div>
					</c:if>
				</div>

				<div class="sortingArea" <c:if test="${buildingList.size() == '0'}"> style="display: none"</c:if>>
					<ul>
						<li>並べ替え</li>
						<li>
						<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
							<c:if test="${housingListMationForm.getSortUpdDateValue() == key}">
								<input type="hidden" id ="sortUpdDateValue"  name="sortUpdDateValue"  value="<c:out value="${key}"/>">
								<a id="sortOrder1" href="javascript:orderLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
							</c:if>
						</dm3lookup:lookupForEach>
						</li>

						<li>
						<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
							<c:if test="${housingListMationForm.getSortPriceValue() == key}">
								<input type="hidden" id ="sortPriceValue"  name="sortPriceValue"  value="<c:out value="${key}"/>">
								<a id="sortOrder2" href="javascript:orderLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
							</c:if>
						</dm3lookup:lookupForEach>
						</li>

						<li>
						<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
							<c:if test="${housingListMationForm.getSortBuildDateValue() == key}">
								<input type="hidden" id ="sortBuildDateValue"  name="sortBuildDateValue"  value="<c:out value="${key}"/>">
								<a id="sortOrder3" href="javascript:orderLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
							</c:if>
						</dm3lookup:lookupForEach>
						</li>

						<li>
						<dm3lookup:lookupForEach lookupName="hosuing_sortOrder">
							<c:if test="${housingListMationForm.getSortWalkTimeValue() == key}">
								<input type="hidden" id ="sortWalkTimeValue"  name="sortWalkTimeValue"  value="<c:out value="${key}"/>">
								<a id="sortOrder4" href="javascript:orderLinkToUrl('<c:out value="${pageContext.request.contextPath}"/><c:out value="${urlPath}"/>', '<c:out value="${key}"/>');"><c:out value="${value}"/></a>
							</c:if>
						</dm3lookup:lookupForEach>
						</li>

					</ul>

					<p class="SPdisplayBlock"><span>並べ替え</span>
						<select name="selKeyOrderType" id="selKeyOrderType" onchange="javascript:orderSelect('<c:out value="${sortOrder}"/>');">
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '4'}">selected="selected"</c:if> value="4" label="物件登録日（新着順） ▼">物件登録日（新着順） ▼</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '3'}">selected="selected"</c:if> value="3" label="物件登録日（新着順） ▲">物件登録日（新着順） ▲</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '1'}">selected="selected"</c:if> value="1" label="物件価格 ▲">物件価格 ▲</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '2'}">selected="selected"</c:if> value="2" label="物件価格 ▼">物件価格 ▼</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '5'}">selected="selected"</c:if> value="5" label="築年数 ▲">築年数 ▲</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '6'}">selected="selected"</c:if> value="6" label="築年数 ▼">築年数 ▼</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '7'}">selected="selected"</c:if> value="7" label="駅からの距離 ▲">駅からの距離 ▲</option>
							<option <c:if test="${housingListMationForm.getKeyOrderType() == '8'}">selected="selected"</c:if> value="8" label="駅からの距離 ▼">駅からの距離 ▼</option>
						</select>
					</p>
				</div>

				<div class="resultArea">
						<c:import url="/WEB-INF/jsp/front/include/housingListMation/searchList.jsh" />
				</div>
			</div>

			<c:if test="${buildingList.size() != '0'}">
					<c:if test="${pagingForm.maxPages > 1}">
						<div class="pagination">
							<c:import url="/WEB-INF/jsp/front/include/common/housingPagingjs.jsh" />
						</div>
					</c:if>
				</c:if>
		</div>
	</div>
</form>

<!--#include virtual="/common/ssi/footer-S.html"-->

</body>