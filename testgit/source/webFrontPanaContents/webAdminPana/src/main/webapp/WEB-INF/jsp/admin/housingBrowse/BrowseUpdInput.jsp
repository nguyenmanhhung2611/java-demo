<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- ----------------------------------------------------------------
 名称： 物件閲覧編集画面

 2015/03/11		Ma.Shuangshuang	新規作成
 2015/08/14		Duong.Nguyen	Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="物件閲覧編集画面" />
<c:param name="contents">
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<script type ="text/JavaScript">
<!--
	$(function(){
	    $("#imgLink").fancybox();
	});
	$(function(){
	    $("#staffImgLink").fancybox();
	});
	$(function(){
	    $("#inspectionImageLink").fancybox();
	});
	$(function(){
	    $("#inspectionFileLink").fancybox();
	});

	function delReformPlan(urlInfo, sysReformCd) {
		if(window.confirm("削除を行います。よろしいですか？")){
			document.inputForm.command.value = 'delReformPlan';
			document.inputForm.action = urlInfo;
			document.inputForm.sysReformCd.value = sysReformCd;
			document.inputForm.target = "";
			document.inputForm.submit();
		}
	}
	var entityMap = {
		"&": "&amp;",
		"<": "&lt;",
		">": "&gt;",
		'"': '&quot;',
		"'": '&#39;',
		"/": '&#x2F;'
	};

	function escapeHtml(string) {
		return String(string).replace(/[&<>"'\/]/g, function (s) {
			return entityMap[s];
		});
	}

	function linkToUrl(url, sysReformCd) {
		document.inputForm.action = url;
		document.inputForm.target = "";
		document.inputForm.command.value = 'update';
		document.inputForm.sysReformCd.value = sysReformCd;
		document.inputForm.target = "";
		document.inputForm.submit();
	}

	function popupPreview(url) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.submit();
	}

	function addCommas(nStr) {
		nStr += '';

		var x = nStr.split('.');
		var x1 = x[0];
		var x2 = x.length > 1 ? '.' + x[1] : '';
		var rgx = /(\d+)(\d{3})/;

		while (rgx.test(x1)) {
			x1 = x1.replace(rgx, '$1' + ',' + '$2');
		}
		return x1 + x2;
	}
	function clearUserId() {
		document.inputForm.userId.value = "";
		document.inputForm.memberName.value = "";
		document.inputForm.tel.value = "";
		document.inputForm.email.value = "";
		document.getElementById("memberNameTd").innerHTML = "&nbsp;";
		document.getElementById("telTd").innerHTML = "&nbsp;";
		document.getElementById("emailTd").innerHTML = "&nbsp;";
	}
	function backLinkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.target = "";
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>

<form method="post" name="inputForm">
<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
<input type="hidden" name="pageId" value="housingStatus">
<input type="hidden" name="command" value="update">
<input type="hidden" name="sysHousingCd" value="<c:out value="${housingBrowse.sysHousingCd}"/>" >
<input type="hidden" name="sysReformCd" value="<c:out value="${housingBrowse.sysReformCd}"/>">
<input type="hidden" name="housingKindCd" value="<c:out value="${housingBrowse.housingKindCd}"/>" >
<input type="hidden" name="sysBuildingCd" value="<c:out value="${housingBrowse.sysBuildingCd}"/>" >
<input type="hidden" name="housingCd" value="<c:out value="${housingBrowse.housingCd}"/>" >
<input type="hidden" name="displayHousingName" value="<c:out value="${housingBrowse.displayHousingName}"/>" >
<dm3token:oneTimeToken/>
<!-- 物件基礎情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件基礎情報</h2>
	</div>

	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="18%" class="head_tr"/>
				<col width="32%"/>
				<col width="18%" class="head_tr"/>
				<col width="32%"/>
			</colgroup>
			<tr>
				<th>物件番号</th>
				<td colspan="3"><c:out value="${housingBrowse.housingCd}"/>&nbsp;</td>
			</tr>
			<tr>
				<th>登録日時</th>
				<td><c:out value="${housingBrowse.insDate}"/>&nbsp;</td>
				<th>更新日時</th>
				<td><c:out value="${housingBrowse.updDate}"/>&nbsp;</td>
			</tr>
			<tr>
				<th>最終更新者（ID）</th>
				<td id="updUserId"><c:out value="${housingBrowse.updUserId}"/>&nbsp;</td>
				<th>最終更新者（氏名）</th>
				<td id="updUserName"><c:out value="${housingBrowse.userName}"/>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<br><br>
<!-- /物件基礎情報 -->
<!-- ステータス編集 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>ステータス編集</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/housingBrowse/inputForm.jsh" />
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/status/confirm/', '');"><span>登&nbsp;&nbsp;録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:popupPreview('<c:out value="${pageContext.request.contextPath}"/>/top/housing/preview/');" ><span>プレビュー</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
		 <div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:backLinkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/list/', 'list');"><span>戻る</span></a></p>
				</div>
			</div>
	    </div>
	</div>
</div>
<br>
<!-- /ステータス編集 -->
<!-- 物件基本情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件基本情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="15%"/>
				<col width="20%"/>
				<col width="12%"/>
				<col width="20%"/>
				<col width="12%"/>
				<col width="20%"/>
			</colgroup>
			<tr>
				<th class="head_tr">物件名称</th>
				<td colspan="5"><c:out value="${housingBrowse.displayHousingName}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">物件種別</th>
				<td colspan="5">
	            	<dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd">
						<c:if test="${housingBrowse.housingKindCd == key}"><c:out value="${value}"/></c:if>
	            	</dm3lookup:lookupForEach>
	            	&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">価格</th>
				<td><c:if test="${!empty housingBrowse.price}"><fmt:formatNumber value="${housingBrowse.price}" pattern="###,###" />円</c:if>&nbsp;</td>
				<th class="head_tr">築年</th>
				<td><c:out value="${housingBrowse.compDate}"/>&nbsp;</td>
				<th class="head_tr">間取り</th>
				<td>
	            	<dm3lookup:lookupForEach lookupName="layoutCd">
						<c:if test="${housingBrowse.layoutCd == key}"><c:out value="${value}"/></c:if>
	            	</dm3lookup:lookupForEach>
	            	&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">住所</th>
				<td colspan="5">
					<c:out value="${housingBrowse.address}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">最寄り駅</th>
				<td colspan="5">
					<c:set var="index" value="0" />
					<c:forEach items="${nearStationList}" var="buildingStation" varStatus="status">
					<c:if test="${nearStationList[status.index] != ''}">
						<c:out value="${buildingStation}"/>
						<br>
						<c:set var="index" value="${index+1}" />
					</c:if>
					</c:forEach>
					<c:if test="${index == 0}">
					&nbsp;
					</c:if>
				</td>
			</tr>
			<tr>
				<th class="head_tr">建物面積</th>
				<td>
					<c:if test="${housingBrowse.buildingArea1 != '' && housingBrowse.buildingArea1 != null}">
						<c:out value="${housingBrowse.buildingArea1}"/>m&sup2;
					</c:if>
					<c:if test="${housingBrowse.buildingArea2 != '' && housingBrowse.buildingArea2 != null}">
						(約<c:out value="${housingBrowse.buildingArea2}"/>坪)
					</c:if>
						<br>
					<c:if test="${housingBrowse.buildingArea3 != '' && housingBrowse.buildingArea3 != null}">
						/<c:out value="${housingBrowse.buildingArea3}"/>&nbsp;
					</c:if>
				</td>
				<th class="head_tr">土地面積</th>
				<td>
					<c:if test="${housingBrowse.landArea1 != '' && housingBrowse.landArea1 != null}">
						<c:out value="${housingBrowse.landArea1}"/>m&sup2;
					</c:if>
					<c:if test="${housingBrowse.landArea2 != '' && housingBrowse.landArea2 != null}">
						(約<c:out value="${housingBrowse.landArea2}"/>坪)
					</c:if>
					<br>
					<c:if test="${housingBrowse.landArea3 != '' && housingBrowse.landArea3 != null}">
						/<c:out value="${housingBrowse.landArea3}"/>&nbsp;
					</c:if>
				</td>
				<th class="head_tr">専有面積</th>
				<td>
					<c:if test="${housingBrowse.personalArea1 != '' && housingBrowse.personalArea1 != null}">
						<c:out value="${housingBrowse.personalArea1}"/>m&sup2;
					</c:if>
					<c:if test="${housingBrowse.personalArea2 != '' && housingBrowse.personalArea2 != null}">
						(約<c:out value="${housingBrowse.personalArea2}"/>坪)
					</c:if>
					<br>
					<c:if test="${housingBrowse.personalArea3 != '' && housingBrowse.personalArea3 != null}">
						/<c:out value="${housingBrowse.personalArea3}"/>&nbsp;
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/info/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
<!-- /物件基本情報 -->
<!-- 物件詳細情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件詳細情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="15%"/>
				<col width="17%"/>
				<col width="16%"/>
				<col width="16%"/>
				<col width="15%"/>
				<col width="20%"/>
			</colgroup>
			<tr>
				<th class="head_tr">土地権利</th>
				<td >
	                <dm3lookup:lookupForEach lookupName="landRight">
						<c:if test="${housingBrowse.landRight == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>&nbsp;
	            </td>
				<th class="head_tr">建物構造</th>
				<td>
					<c:out value="${housingBrowse.structCd}"/>&nbsp;
				</td>
				<th class="head_tr">取引形態</th>
				<td>
	                <dm3lookup:lookupForEach lookupName="transactTypeDiv">
						<c:if test="${housingBrowse.transactTypeDiv == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>&nbsp;
	            </td>
			</tr>
			<tr>
				<th class="head_tr">駐車場</th>
				<td colspan="5"><c:out value="${housingBrowse.displayParkingInfo}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">現況</th>
				<td><c:out value="${housingBrowse.status}"/>&nbsp;</td>
				<th class="head_tr">用途地域</th>
				<td colspan="3">
	                <dm3lookup:lookupForEach lookupName="usedAreaCd">
						<c:if test="${housingBrowse.usedAreaCd == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>
	                &nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">引渡時期</th>
				<td>
	                <dm3lookup:lookupForEach lookupName="moveinTiming">
						<c:if test="${housingBrowse.moveinTiming == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>
	                &nbsp;
				</td>
				<th class="head_tr">引渡時期コメント</th>
				<td colspan="3"><c:out value="${housingBrowse.moveinNote}"/>&nbsp;</td>
			</tr>
		<c:if test="${housingBrowse.housingKindCd != 01}">
			<tr>
				<th class="head_tr">接道状況</th>
				<td><c:out value="${housingBrowse.contactRoad}"/>&nbsp;</td>
				<th class="head_tr">接道方向/幅員</th>
				<td><c:out value="${housingBrowse.contactRoadDir}"/>&nbsp;</td>
				<th class="head_tr">私道負担</th>
				<td><c:out value="${housingBrowse.privateRoad}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">建ぺい率</th>
				<td><c:if test="${!empty housingBrowse.coverageMemo}"><c:out value="${housingBrowse.coverageMemo}"/></c:if>&nbsp;</td>
				<th class="head_tr">容積率</th>
				<td colspan="3"><c:if test="${!empty housingBrowse.buildingRateMemo}"><c:out value="${housingBrowse.buildingRateMemo}"/></c:if>&nbsp;</td>
			</tr>
		</c:if>
		<c:if test="${housingBrowse.housingKindCd == 01}">
			<tr>
				<th class="head_tr">総戸数</th>
				<td><c:out value="${housingBrowse.totalHouseCnt}"/>&nbsp;</td>
				<th class="head_tr">建物階数</th>
				<td>
					<c:if test="${housingBrowse.totalFloorsTxt != '' && housingBrowse.totalFloorsTxt != null}">
						<c:out value="${housingBrowse.totalFloorsTxt}"/>階建て
					</c:if>&nbsp;
				</td>
				<th class="head_tr">規模</th>
				<td>
	                <dm3lookup:lookupForEach lookupName="scaleDataValue">
						<c:if test="${housingBrowse.scale == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>
					&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">所在階数</th>
				<td>
					<c:if test="${housingBrowse.floorCd != '' && housingBrowse.floorCd != null}">
						<c:out value="${housingBrowse.floorCd}"/>階
					</c:if>&nbsp;
				</td>
				<th class="head_tr">所在階数コメント</th>
				<td colspan="3"><c:out value="${housingBrowse.floorNoNote}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">向き</th>
				<td colspan="2"><c:out value="${housingBrowse.direction}"/>&nbsp;</td>
				<th class="head_tr">バルコニー面積</th>
				<td colspan="2">
					<c:if test="${housingBrowse.balconyArea != '' && housingBrowse.balconyArea != null}">
						<c:out value="${housingBrowse.balconyArea}"/>m&sup2;
					</c:if>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">管理費</th>
				<td>
					<c:if test="${housingBrowse.upkeep != '' && housingBrowse.upkeep != null}">
						<fmt:formatNumber value="${housingBrowse.upkeep}" pattern="###,###" />円／月
					</c:if>
					&nbsp;
				</td>
				<th class="head_tr">修繕積立費</th>
				<td>
					<c:if test="${housingBrowse.menteFee != '' && housingBrowse.menteFee != null}">
						<fmt:formatNumber value="${housingBrowse.menteFee}" pattern="###,###" />円／月
					</c:if>
					&nbsp;
				</td>
				<th class="head_tr">管理形態・方式</th>
				<td><c:out value="${housingBrowse.upkeepType}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">管理会社</th>
				<td colspan="5"><c:out value="${housingBrowse.upkeepCorp}"/>&nbsp;</td>
			</tr>
		</c:if>

			<tr>
				<th class="head_tr">瑕疵保険</th>
				<td colspan="2">
	                <dm3lookup:lookupForEach lookupName="insurExist">
						<c:if test="${housingBrowse.insurExist == key}"><c:out value="${value}"/></c:if>
	                </dm3lookup:lookupForEach>
	                &nbsp;
				</td>
				<th class="head_tr">担当者名</th>
				<td colspan="2"><c:out value="${housingBrowse.staffName}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">担当者写真</th>
				<td colspan="5">
					<c:if test="${housingBrowse.staffImageFlg == '1'}">
						<span><a id="staffImgLink" href="<c:out value="${housingBrowse.staffImagePreviewPath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" alt="" style="vertical-align:middle;"/></a>&nbsp;あり</span>
					</c:if>
					&nbsp;
	            </td>
			</tr>
			<tr>
				<th class="head_tr">会社名</th>
				<td colspan="2"><c:out value="${housingBrowse.companyName}"/>&nbsp;</td>
				<th class="head_tr">支社名</th>
				<td colspan="2"><c:out value="${housingBrowse.branchName}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">免許番号</th>
				<td colspan="2"><c:out value="${housingBrowse.licenseNo}"/>&nbsp;</td>
				<th class="head_tr">インフラ</th>
				<td colspan="2"><c:out value="${housingBrowse.infrastructure}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">特記事項</th>
				<td colspan="5"><c:out value="${housingBrowse.specialInstruction}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">物件コメント</th>
				<td colspan="5"><c:out value="${housingBrowse.dtlComment}" escapeXml="false" />&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">担当者のコ<br>メント</th>
				<td colspan="5"><c:out value="${housingBrowse.basicComment}" escapeXml="false" />&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">売主コメ<br>ント</th>
				<td colspan="5"><c:out value="${housingBrowse.vendorComment}" escapeXml="false" />&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">リフォーム<br>準備中コ<br>メント</th>
				<td colspan="5"><c:out value="${housingBrowse.reformComment}" escapeXml="false" />&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">動画リン<br>クURL</th>
				<td colspan="5"><c:out value="${housingBrowse.movieUrl}"/>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/dtl/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
<!-- /物件詳細情報 -->
<c:if test="${housingBrowse.housingKindCd != 03}">
<!-- 物件特長 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件特長</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<td><c:out value="${housingBrowse.equipName}"/>&nbsp;</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/part/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
</c:if>

<!-- /物件特長 -->
<!-- 管理者用設備情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>管理者用設備情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<div style="height: 250px; overflow-y: scroll;">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<colgroup>
					<col width="30%"/>
					<col width="50%"/>
					<col width="20%"/>
				</colgroup>
				<tr>
					<th class="head_tr">設備項目</th>
					<th class="head_tr">設備</th>
					<th class="head_tr">リフォーム</th>
				</tr>
				<c:forEach var="varKeyCd" items="${housingBrowse.keyCd}">
					<tr>
						<td><c:out value="${housingBrowse.adminEquipName[varKeyCd-1]}" />&nbsp;</td>
						<td><c:out value="${housingBrowse.adminEquipInfo[varKeyCd-1]}" />&nbsp;</td>
						<td>
							<c:if test="${housingBrowse.adminEquipName[varKeyCd-1] != ''}">
								<c:if test="${housingBrowse.adminEquipReform[varKeyCd-1] == '1'}">あり</c:if>
								<c:if test="${housingBrowse.adminEquipReform[varKeyCd-1] == '0'}">なし</c:if>
							</c:if>
							&nbsp;
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/equip/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
<!-- /管理者用設備情報 -->
<!-- おすすめポイント情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>おすすめポイント情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<td>
					<dm3lookup:lookupForEach lookupName="recommend_point_icon">
					    <c:forEach items="${housingBrowse.iconCd}" var="icon" varStatus="currentVarStatus">
						    <c:if test="${icon == key}">
						    	<c:choose>
									<c:when test="${!currentVarStatus.first}">／<c:out value="${value}"/></c:when>
									<c:otherwise><c:out value="${value}"/></c:otherwise>
								</c:choose>
						    </c:if>
					    </c:forEach>
					</dm3lookup:lookupForEach>&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/recopoint/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
<!-- /おすすめポイント情報 -->
<!-- 住宅診断情報 -->
<c:if test="${housingBrowse.housingKindCd != '03'}">
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>住宅診断情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
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
	                   <c:if test="${housingBrowse.inspectionExist == key}"><c:out value="${value}"/> </c:if>
					</dm3lookup:lookupForEach>&nbsp;
				</td>
			</tr>
			<c:if test="${housingBrowse.housingKindCd == '01'}">
				<c:forEach  items="${housingBrowse.inspectionKey}" varStatus="status">
					<tr>
						<th class="head_tr">
							<dm3lookup:lookupForEach lookupName="inspectionTrustMansion">
			                   <c:if test="${housingBrowse.inspectionKey[status.index] == key}"><c:out value="${value}　評価基準"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</th>
						<td>
							<dm3lookup:lookupForEach lookupName="inspectionResult">
			                   <c:if test="${housingBrowse.inspectionResult[status.index] == key}"><c:out value="${value}"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</td>
						<th class="head_tr">
						    <dm3lookup:lookupForEach lookupName="inspectionTrustMansion">
			                   <c:if test="${housingBrowse.inspectionKey[status.index] == key}"><c:out value="${value}　確認範囲"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</th>
						<td>
							<dm3lookup:lookupForEach lookupName="inspectionLabel">
			                   <c:if test="${housingBrowse.inspectionLevel[status.index] == key}"><c:out value="${value}"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${housingBrowse.housingKindCd == '02'}">
				<c:forEach items="${housingBrowse.inspectionKey}" varStatus="status">
					<tr>
						<th class="head_tr">
							<dm3lookup:lookupForEach lookupName="inspectionTrustHouse">
			                   <c:if test="${housingBrowse.inspectionKey[status.index] == key}"><c:out value="${value}　評価基準"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</th>
						<td>
							<dm3lookup:lookupForEach lookupName="inspectionResult">
			                   <c:if test="${housingBrowse.inspectionResult[status.index] == key}"><c:out value="${value}"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</td>
						<th class="head_tr">
						    <dm3lookup:lookupForEach lookupName="inspectionTrustHouse">
			                   <c:if test="${housingBrowse.inspectionKey[status.index] == key}"><c:out value="${value}　確認範囲"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</th>
						<td>
							<dm3lookup:lookupForEach lookupName="inspectionLabel">
			                   <c:if test="${housingBrowse.inspectionLevel[status.index] == key}"><c:out value="${value}"/> </c:if>
							</dm3lookup:lookupForEach>&nbsp;
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<th class="head_tr">レーダーチャート画像</th>
				<td colspan="3">
				<c:if test="${housingBrowse.reformChartImageFlg == '1'}">
					<span><a id="inspectionImageLink" href="<c:out value="${housingBrowse.inspectionImagePath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" alt="" style="vertical-align:middle;"/></a>&nbsp;あり</span>
				</c:if>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">住宅診断ファイル</th>
				<td colspan="3">
				<c:if test="${housingBrowse.inspectionFileFlg == '1'}">
					<span><a id="inspectionFileLink" target="_blank" href="<c:out value="${housingBrowse.inspectionFilePath}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" style="vertical-align:middle;"/></a>&nbsp;あり</span>
				</c:if>&nbsp;
				</td>
			</tr>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/inspection/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
</c:if>
<!-- /住宅診断情報 -->
<!-- リフォーム情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table id="rfmTbl" width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="45%"/>
				<col width="20%"/>
				<col width="15%"/>
				<col width="20%"/>
			</colgroup>
			<tr>
				<th class="head_tr">リフォーム名称</th>
				<th class="head_tr">金額</th>
				<th class="head_tr">公開区分</th>
				<th class="head_tr">&nbsp;</th>
			</tr>
			<c:forEach var="plan" items="${reformPlanList}" varStatus="ItemStatus">
				<tr id="plan${ItemStatus.index + 1}">
				<c:if test="${plan.sysHousingCd != null && plan.sysHousingCd != ''}">
					<td><c:out value="${plan.planName}" /></td>
					<td><c:if test="${!empty plan.planPrice }"><fmt:formatNumber value="${plan.planPrice}" pattern="###,###" />円</c:if>&nbsp;</td>
					<td>
		                <dm3lookup:lookupForEach lookupName="hiddenFlg">
							<c:if test="${plan.hiddenFlg == key}"><c:out value="${value}"/></c:if>
		                </dm3lookup:lookupForEach>
					</td>
				</c:if>
				<c:if test="${plan.sysHousingCd == null || plan.sysHousingCd == ''}">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</c:if>
					<td style="text-align:left">
					    <!--btnBlockC11 -->
					    <div class="btnBlockC11">
					        <div class="btnBlockC11Inner">
					            <div class="btnBlockC11Inner2">
					                <p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/input/','<c:out value="${plan.sysReformCd}"/>');"><span>編&nbsp;集</span></a></p>
					            </div>
					        </div>
					    </div>
				<c:if test="${plan.sysHousingCd != null && plan.sysHousingCd != ''}">
					<dm3login:hasRole roleName="admin">
					    <!--btnBlockC11 -->
					    <div class="btnBlockC11">
					        <div class="btnBlockC11Inner">
					            <div class="btnBlockC11Inner2">
					                <p><a href="javascript:delReformPlan('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/delete/','<c:out value="${plan.sysReformCd}"/>');"><span>削&nbsp;除</span></a></p>
					            </div>
					        </div>
					    </div>
					</dm3login:hasRole>
				</c:if>
					</td>
				</tr>

			</c:forEach>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<br><br>
<!-- /リフォーム情報 -->
<!-- 物件画像情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>物件画像情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="10%"/>
				<col width="15%"/>
				<col width="15%"/>
				<col width="15%"/>
				<col width="45%"/>
			</colgroup>
			<tr>
				<th class="head_tr">表示順</th>
				<th class="head_tr">画像</th>
				<th class="head_tr">種別</th>
				<th class="head_tr">閲覧権限</th>
				<th class="head_tr">コメント</th>
			</tr>
			<c:forEach var="housingImage" items="${housingImageList}" varStatus="status" step="1">
			<tr>
				<td>
	    			<c:out value="${housingImage.sortOrder}"/>&nbsp;
	    		</td>
				<td>
	    	    	<p><a id="imgLink" href="<c:out value="${housingImage.pathName}"/>">
	                   <img src="<c:out value="${housingImage.fileName}"/>" alt="" />
	                </a></p>
		    	</td>
	    		<td>
	    			<dm3lookup:lookupForEach lookupName="ImageType">
	    				<c:if test="${housingImage.imageType == key}"><c:out value="${value}"/> </c:if>
	    			</dm3lookup:lookupForEach>&nbsp;
	    		</td>
	    		<td>
	    			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
	                   <c:if test="${housingImage.roleId == key}"><c:out value="${value}"/> </c:if>
					</dm3lookup:lookupForEach>&nbsp;
	    		</td>
	    		<td>
	    			<c:out value="${housingImage.imgComment}"/>&nbsp;
	    		</td>
		    </tr>
		    </c:forEach>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/image/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>
<!-- /物件画像情報 -->
<!-- 地域情報 -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>地域情報</h2>
	</div>
	<!--flexBlockA01 -->
	<div class="flexBlockA01">
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA5">
			<colgroup>
				<col width="25%"/>
				<col width="75%"/>
			</colgroup>
			<tr>
				<th class="head_tr">施設名</th>
				<th class="head_tr">施設情報（名称/所要時間/距離）</th>
			</tr>
			<dm3lookup:lookupForEach lookupName="buildingLandmark_landmarkType">
				<tr>
					<td><c:out value="${value}"/>&nbsp;</td>
					<td>
						<c:forEach var="buildingLandmark" items="${buildingLandmarkList}">
							<c:if test="${key == buildingLandmark.landmarkType}">
								<c:out value="${buildingLandmark.landmarkName}" /><c:if test="${buildingLandmark.distanceFromLandmark != null && buildingLandmark.distanceFromLandmark != '' }" >：徒歩<c:out value="${buildingLandmark.timeFromLandmark}" />分（<c:out value="${buildingLandmark.distanceFromLandmark}" />m）</c:if>
							</c:if>
						</c:forEach>
						&nbsp;
					</td>
				</tr>
			</dm3lookup:lookupForEach>
		</table>
	</div>
	<!--/flexBlockA01 -->
</div>
<br>
<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/landmark/input/', '');"><span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->
<!-- /地域情報 -->
</form>
</c:param>
</c:import>