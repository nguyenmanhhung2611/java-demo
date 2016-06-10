<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${outPutForm.getKeywords()}"/>">
<meta name="description" content="<c:out value="${outPutForm.getDescription()}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><c:out value="${outPutForm.getDisplayHousingName()}"/>｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/css/building.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/css/jquery.fancybox.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/css/tooltipster.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/main.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/js/buy.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/jquery.heightLine.js"></script>

<script type="text/javascript">
<!--
var encAddress = encodeURI('<c:out value="${outPutForm.getAddress()}"/>');
var encDisplayHousingName = encodeURI('<c:out value="${outPutForm.getDisplayHousingName()}"/>');

$.event.add(window, "load", function(){

	initialize();

	if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {

		var $areaMap = $('.buildingSummary'),
			$areaMapTd = $areaMap.find('td'),
			$areaMapSpan = $areaMapTd.find('span');
			<c:if test="${outPutForm.isPreviewFlg() == 'false'}">
			$areaMapSpan.wrap('<a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getMapUrl()}"/>map/?address=' + encAddress + '&displayHousingName=' + encDisplayHousingName + '" target="_blank"></a>');
			</c:if>
			<c:if test="${outPutForm.isPreviewFlg() == 'true'}">
			$areaMapSpan.wrap('<a href="javascript:void(0);"></a>');
			</c:if>

		$(".favadd").fancybox({
			fitToView	: false,
			padding		: 10,
			margin		: 10,
			width		: '265px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
		$(".favadd").on('click',function(){
				setTimeout(function(){
				$.fancybox.close();
				$("[id^='personalInfo'] .favorite").text(getFavoriteCount());
			}, 1500)
		});

		$(".recicon").fancybox({
			fitToView	: false,
			padding		: 10,
			margin		: 10,
			width		: '265px',
			closeClick	: false,
			openEffect	: 'none',
			closeEffect	: 'none',
			iframe		: {
				scrolling : 'auto',
			}
		});
	}

	else {

		var $areaMap = $('.buildingSummary'),
			$areaMapTd = $areaMap.find('td'),
			$areaMapSpan = $areaMapTd.find('span');
			<c:if test="${outPutForm.isPreviewFlg() == 'false'}">
			$areaMapSpan.wrap('<a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getMapUrl()}"/>map/?address=' + encAddress + '&displayHousingName=' + encDisplayHousingName + '" onClick="newWin(this.href,null,680,820); return false;"></a>');
			</c:if>
			<c:if test="${outPutForm.isPreviewFlg() == 'true'}">
			$areaMapSpan.wrap('<a href="javascript:void(0);"></a>');
			</c:if>

		$(".favadd").fancybox({
			fitToView	: false,
			width		: '450px',
			closeClick	: false,
			closeBtn	: false,
			openEffect	: 'none',
			closeEffect	: 'none'
		});
		$(".favadd").on('click',function(){
				setTimeout(function(){
				$.fancybox.close();
				$("[id^='personalInfo'] .favorite").text(getFavoriteCount());
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
	}

});
-->
</script>

<script type="text/javascript">
<!--
$(function(){
	$('.tooltip01').tooltipster({
		trigger: 'click',
		contentAsHTML: true
	});
});
-->
</script>

<script type="text/javascript" src="//maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript">
<!--
function initialize() {

	var geocoder = new google.maps.Geocoder();
	var address = "<c:out value="${outPutForm.getAddress()}"/>";
	var map;
	geocoder.geocode({
		'address' : address,
	}, function(results, status) {
		if (status == google.maps.GeocoderStatus.OK) {
			var latlng = results[0].geometry.location;
			var mapOptions = {
				zoom : 16,
				center : latlng,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			}
			map = new google.maps.Map(document.getElementById('mapInner'),
					mapOptions);
		} else {
			$("#mapInner").text("登録されている住所情報から地図が表示できませんでした。");
		}
	});
}
-->
</script>

<script type="text/javascript">
<!--
$.event.add(window, "load", function(){
        $(".section02>.tabPlanBlock>ul>li>a").heightLine();
        $(".section04>.columnBlock>.blockInner>a").heightLine({minWidth: 640});
});
-->
</script>

<script type="text/javascript">
<!--
function linkToSubmit(sysHousingCd, flg) {

	if (flg) {
		var url = "<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/";
	} else {
		var url = "<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/";
	}

	document.inputForm.action = url;
	document.inputForm.sysHousingCd.value = sysHousingCd;
	document.inputForm.submit();
}
-->
</script>


<!--[if lte IE 9]><script src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->
</head>

<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<div id="ptop"></div>

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />

<div id="contents">
	<div id="contentsInner" class="detailPage">

		<c:if test="${outPutForm.getHousingKindCd() == '01'}">
			<c:import url="/WEB-INF/jsp/front/include/common/topicPath.jsh?pattern=BUY-04-00&prefName=${outPutForm.getPrefName()}&housingKindCd=${outPutForm.getHousingKindCd()}&housingName=${outPutForm.getDisplayHousingName()}" />
		</c:if>

		<c:if test="${outPutForm.getHousingKindCd() == '02'}">
			<c:import url="/WEB-INF/jsp/front/include/common/topicPath.jsh?pattern=BUY-05-00&prefName=${outPutForm.getPrefName()}&housingKindCd=${outPutForm.getHousingKindCd()}&housingName=${outPutForm.getDisplayHousingName()}" />
		</c:if>

		<c:if test="${outPutForm.getHousingKindCd() == '03'}">
			<c:import url="/WEB-INF/jsp/front/include/common/topicPath.jsh?pattern=BUY-08-00&prefName=${outPutForm.getPrefName()}&housingKindCd=${outPutForm.getHousingKindCd()}&housingName=${outPutForm.getDisplayHousingName()}" />
		</c:if>

		<div class="section01">

			<c:if test="${outPutForm.isTitleDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/titleForm.jsh" />
			</c:if>

			<c:if test="${outPutForm.isHousingInfoDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/housingInfoForm.jsh" />
			</c:if>

			<c:import url="/WEB-INF/jsp/front/include/housingDetailed/reformPlanForm.jsh" />

			<c:if test="${outPutForm.isImgDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/housingImageInfoForm.jsh" />
			</c:if>

			<div class="contactBlock mb30 SPdisplayNone">
				<div class="blockInner clearfix">
					<p class="text">物件を見たい、詳しい情報を知りたいなどの<br>
					<span>お問い合わせ</span>はこちら</p>
					<p class="btnOrange01"><a href="javascript:void(0);" <c:if test="${outPutForm.isPreviewFlg() == 'false'}">onClick="linkToSubmit('<c:out value="${outPutForm.getSysHousingCd()}"/>',<c:out value="${outPutForm.isMemberFlg()}"/>);"</c:if>>この物件に関するお問い合わせ</a></p>
					<c:if test="${outPutForm.isMemberFlg()}">
						<p class="btnOrange02"><a <c:if test="${outPutForm.isPreviewFlg() == 'false'}">href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${outPutForm.getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd"</c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">href="javascript:void(0);"</c:if>>お気に入り登録</a></p>
					</c:if>
				</div>
			</div>

			<div class="printBlock">
				<ul class="img2Column clearfix">
					<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="0" end="1">
						<c:if test="${!empty varImgNoHidden}">
							<li><img src="<c:out value="${outPutForm.getHousingImgPath1Hidden()[varImgNoHidden]}"/>"></li>
						</c:if>
					</c:forEach>
				</ul>

				<ul class="img4Column clearfix">
					<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="2" end="5">
						<c:if test="${!empty varImgNoHidden}">
							<li><img src="<c:out value="${outPutForm.getHousingImgPath1Hidden()[varImgNoHidden]}"/>"></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

			<c:if test="${outPutForm.isSalesCommentDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/saleCommentForm.jsh" />
			</c:if>

			<c:if test="${outPutForm.isRecommendDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/recommendForm.jsh" />
			</c:if>

			<c:if test="${outPutForm.isLoginDisplayFlg() == 'true'}">
				<div class="memberBlock">
					<p class="text"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">会員登録</a>していただくと、<br class="SPdisplayBlock">内観写真、住宅診断情報がご覧いただけます！
					</p>
					<p class="btnBlack01"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">詳細はこちら</a></p>
				</div>
			</c:if>

			<c:if test="${outPutForm.isHousingDtlInfoDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/housingDtlInfoForm.jsh" />
			</c:if>

			<c:if test="${outPutForm.isHousingPropertyDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/housingEquipInfoForm.jsh" />
			</c:if>

			<c:if test="${outPutForm.isLandmarkDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/buildingLandmarkForm.jsh" />
			</c:if>

			<div class="contactBlock SPdisplayNone">
				<div class="blockInner clearfix">
					<p class="text">物件を見たい、詳しい情報を知りたいなどの<br>
					<span>お問い合わせ</span>はこちら</p>
					<p class="btnOrange01"><a href="javascript:void(0);" <c:if test="${outPutForm.isPreviewFlg() == 'false'}">onClick="linkToSubmit('<c:out value="${outPutForm.getSysHousingCd()}"/>',<c:out value="${outPutForm.isMemberFlg()}"/>);"</c:if>>この物件に関するお問い合わせ</a></p>
					<c:if test="${outPutForm.isMemberFlg()}">
						<p class="btnOrange02"><a <c:if test="${outPutForm.isPreviewFlg() == 'false'}">href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${outPutForm.getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd"</c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">href="javascript:void(0);"</c:if>>お気に入り登録</a></p>
					</c:if>
				</div>
			</div>

			<div class="printMap">
				<div id="mapInner"></div>
			</div>
		</div>

		<c:if test="${outPutForm.getHousingKindCd() != '03'}">
		<div class="section02">
			<c:choose>
				<c:when test="${outPutForm.isRecommendReformPlanDisplayFlg() == 'true'}">
                <div id="anc01" class="tabPlanBlock">
                 <ul>
                     <c:forEach var="varPlanNoHidden" items="${outPutForm.getPlanNoHidden()}" end="2">

                      <li <c:if test="${outPutForm.reformCd == outPutForm.reformCdHidden[varPlanNoHidden]}">class="current"</c:if>>
                          <a href="<c:if test="${outPutForm.isPreviewFlg() == 'false'}"><c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getReformUrl()[varPlanNoHidden]}#anc01"/></c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">javascript:void(0);</c:if>"><c:out value="${outPutForm.getPlanType()[varPlanNoHidden]}"/></a>
                      </li>
                     </c:forEach>
                 </ul>
                  </div>

		    	<div class="sectionInner01">

					<c:if test="${outPutForm.isRecommendReformPlanDisplayFlg() == 'true'}">
						<c:import url="/WEB-INF/jsp/front/include/housingDetailed/recommendReformPlanForm.jsh" />
					</c:if>

					<c:if test="${outPutForm.isReformImgDisplayFlg() == 'true'}">
						<c:import url="/WEB-INF/jsp/front/include/housingDetailed/reformImgForm.jsh" />
					</c:if>

					<div class="printBlock">
						<ul class="img2Column clearfix">
							<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="0" end="0">
								<c:if test="${!empty varImgNoHidden}">
									<li><img src="<c:out value="${outPutForm.getAfterPath1()[varImgNoHidden]}"/>"></li>
								</c:if>
							</c:forEach>
							<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="0" end="0">
								<c:if test="${!empty varImgNoHidden}">
									<li><img src="<c:out value="${outPutForm.getBeforePath1()[varImgNoHidden]}"/>"></li>
								</c:if>
							</c:forEach>
						</ul>

						<ul class="img4Column clearfix">
							<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="1" end="2">
								<c:if test="${!empty varImgNoHidden}">
									<li><img src="<c:out value="${outPutForm.getAfterPath1()[varImgNoHidden]}"/>"></li>
								</c:if>
							</c:forEach>
							<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="1" end="2">
								<c:if test="${!empty varImgNoHidden}">
									<li><img src="<c:out value="${outPutForm.getBeforePath1()[varImgNoHidden]}"/>"></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<c:if test="${outPutForm.getInspectionExist() == 1 && outPutForm.isLoginDisplayFlg() == 'true'}">
                        <div class="diagnosisInfo clearfix">
                            <div class="title clearfix">
                                <h2>住宅診断情報</h2>
                                <p><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/re2/detail/inspection.html" target="_blank">住宅診断とは</a></p>
                            </div>
                            <div class="nologinBlock">
                                <p class="SPdisplayNone"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_img_04.jpg" alt=""></p>
                                <p class="SPdisplayBlock"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_img_05.jpg" alt=""></p>
                                <div class="btnArea">
                                    <p class="text">会員登録をしていただくと、<br>住宅診断情報をご覧いただけます</p>
                                    <p class="btnOrange02"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">会員登録はこちら</a></p>
                                </div>
                            </div>
                        </div>
    				</c:if>
					<c:if test="${outPutForm.isHousingInspectionDisplayFlg() == 'true'}">
						<c:import url="/WEB-INF/jsp/front/include/housingDetailed/inspectionForm.jsh" />
					</c:if>

				</div>
				</c:when>

				<c:otherwise>
					<c:if test="${outPutForm.getInspectionExist() == 1 && outPutForm.isLoginDisplayFlg() == 'true'}">
                        <div class="sectionInner01">
                            <div class="diagnosisInfo clearfix">
                                <div class="title clearfix">
                                    <h2>住宅診断情報</h2>
                                    <p><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/re2/detail/inspection.html" target="_blank">住宅診断とは</a></p>
                                </div>
                                <div class="nologinBlock">
                                    <p class="SPdisplayNone"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_img_04.jpg" alt=""></p>
                                    <p class="SPdisplayBlock"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_img_05.jpg" alt=""></p>
                                    <div class="btnArea">
                                        <p class="text">会員登録をしていただくと、<br>住宅診断情報をご覧いただけます</p>
                                        <p class="btnOrange02"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">会員登録はこちら</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
					</c:if>

					<c:if test="${outPutForm.isHousingInspectionDisplayFlg() == 'true'}">
						<div class="sectionInner02">
							<c:import url="/WEB-INF/jsp/front/include/housingDetailed/inspectionForm.jsh" />
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>

		</div>
		</c:if>

		<c:if test="${outPutForm.getHousingKindCd() != '03'}">
		<div class="section03 SPdisplayNone">

			<c:if test="${outPutForm.isLoginDisplayFlg() == 'true'}">
				<div class="memberBlock">
					<p class="text"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">会員登録</a>していただくと、<br class="SPdisplayBlock">内観写真、住宅診断情報がご覧いただけます！
					</p>
					<p class="btnBlack01"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/?redirect=<c:out value="${outPutForm.getCurrentUrl()}"/>">詳細はこちら</a></p>
				</div>
			</c:if>

			<c:if test="${outPutForm.isOtherReformPlanDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/otherReformPlanForm.jsh" />
			</c:if>

		</div>
		</c:if>

		<div class="section04">

			<c:if test="${outPutForm.isRecentlyDisplayFlg() == 'true'}">
				<c:import url="/WEB-INF/jsp/front/include/housingDetailed/recentlyInfoForm.jsh" />
			</c:if>

			<div class="researchBlock">
				<div class="title clearfix">
					<h2>再検索</h2>
				</div>

				<ul class="clearfix">
					<c:forEach var="researchUrl" items="${outPutForm.getResearchUrl()}" varStatus="status">
						<c:if test="${!empty researchUrl}"><li><a href=<c:if test="${outPutForm.isPreviewFlg() == 'false'}">"<c:out value="${pageContext.request.contextPath}"/>${researchUrl}" target="_blank"</c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">"javascript:void(0);"</c:if>><c:out value="${outPutForm.getResearchPrefName()[status.index]}"/></a></li></c:if>
					</c:forEach>
						<li><a href=<c:if test="${outPutForm.isPreviewFlg() == 'false'}">"<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/#search" target="_blank"</c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">"javascript:void(0);"</c:if>>その他の地域</a></li>
				</ul>
			</div>

		<!--#include virtual="/common/ssi/column.html"-->
		</div>
	</div>

	<div class="SPcontactBlock SPdisplayBlock">
		<p class="btnOrange01"><a href="javascript:void(0);" <c:if test="${outPutForm.isPreviewFlg() == 'false'}">onClick="linkToSubmit('<c:out value="${outPutForm.getSysHousingCd()}"/>',<c:out value="${outPutForm.isMemberFlg()}"/>);"</c:if>>お問い合わせ<span>(無料)</span></a></p>
		<c:if test="${outPutForm.isMemberFlg()}">
			<p class="btnOrange02"><a <c:if test="${outPutForm.isPreviewFlg() == 'false'}">href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${outPutForm.getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd"</c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">href="javascript:void(0);"</c:if>>お気に入り登録</a></p>
		</c:if>
	</div>
</div>

<!--#include virtual="/common/ssi/footer-S.html"-->

<form action="" method="post" name="inputForm" target="_blank">
	<input type="hidden" name="sysHousingCd" value="" />
</form>
</body>
</html>