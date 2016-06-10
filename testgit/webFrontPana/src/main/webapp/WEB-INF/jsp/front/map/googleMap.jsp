<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="<c:out value="${param.displayHousingName}"/>,地図,パナソニック,<c:out value="${commonParameters.panasonicSiteEnglish}"/>,<c:out value="${commonParameters.panasonicSiteJapan}"/>,Re2,リー・スクエア">
	<meta name="description" content="<c:out value='${commonParameters.defaultDescription}'/>">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><c:out value="${param.displayHousingName}"/>の周辺地図｜<c:out value="${commonParameters.panaReSmail}"/></title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>buy/css/building.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>

<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=true"></script>
<script type="text/javascript">
	function initialize() {

		var geocoder = new google.maps.Geocoder();
		var address = decodeURI("<c:out value='${param.address}'/>");
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
</script>


<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->

</head>
<body id="map" onload="initialize()">

<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
	<header id="mapheader">
		<div class="mapheaderInner">
			<div id="logoBlock">
				<p class="sitelogo"><a href="/"><img src="<c:out value='${commonParameters.resourceRootUrl}'/>common/img/header_logo_02.png" alt="ReaRie"></a></p>
				<p class="lead">パナソニックの中古不動産売買＆リフォーム紹介サイト「リアリエ」</p>
			</div>
		</div>
    </header>

	<div id="contents">
		<div id="contentsInner">
			<h1>周辺地図</h1>

			<div class="mapBlock">
				<div id="mapInner"></div>
			</div>

			<p class="note">表示されている場所は物件の周辺地図です。<br>
			詳しい物件所在地は、不動産会社までお問い合わせください。</p>

			<div class="btnBlock">
				<p class="btnBlack01"><a href="#" onClick="if (/Chrome/i.test(navigator.userAgent)) { window.close(); } else { window.open('about:blank', '_self').close(); }">閉じる</a></p>
			</div>
		</div>
	</div>

	<footer id="mapfooter">
		<div id="copyright">
			<div class="copyrightInner">
				<p>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Panasonic Corporation</p>
			</div>
		</div>
	</footer>
</body>
</html>