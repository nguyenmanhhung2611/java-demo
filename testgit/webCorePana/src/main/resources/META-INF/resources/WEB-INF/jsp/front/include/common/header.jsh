<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<header id="header">
	<div id="SPheader" class="clearfix">
		<c:set var="mypageUrl" value="${pageContext.request.contextPath}/mypage_service/"/>
		<dm3login:hasRole roleName="mypage" negative="false">
			<c:set var="isLogin" value="true"/>
			<c:set var="mypageUrl" value="${pageContext.request.contextPath}/mypage/"/>
		</dm3login:hasRole>

		<div id="logoBlock"><p class="brandlogo"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/header_logo_01.png" alt="Panasonic"></p><p class="sitelogo"><a href="/"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/header_logo_02.png" alt="<c:out value='${commonParameters.panasonicSiteEnglish}'/>"></a></p></div>
		<ul id="mainnav">
			<c:choose>
				<c:when test="${isLogin == null}">
					<li class="login"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/">ログイン</a></li>
					<li class="member"><a href="<c:out value="${pageContext.request.contextPath}"/>/account/member/new/input/">会員登録</a></li>
				</c:when>
				<c:otherwise>
					<li class="login"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/logout/">ログアウト</a></li>
				</c:otherwise>
			</c:choose>
			<li class="menu"><span>メニュー</span></li>
		</ul>
		<ul class="head_accordion">
			<li>
				<p><span>買いたい</span></p>
				<ul>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/">TOP</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/re2/index.html">リー・スクエアについて</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/reform/index.html">リフォームについて</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/payment/">お支払いについて</a></li>
				</ul>
			</li>
			<li>
				<p><span>売りたい</span></p>
				<ul>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/">TOP</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/rearie/index.html"><c:out value='${commonParameters.panasonicSiteJapan}'/>について</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/flow/index.html">ご売却の流れ</a></li>
				</ul>
			</li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>brand/"><c:out value='${commonParameters.panasonicSiteJapan}'/>について</a></li>
			<li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/help.html">会員ページ利用の手引き</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>event/">セミナー・イベント</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>column/index.html">お役立ちコラム</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>usersvoice/index.html">お客さまの声</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/link.html">リンク集</a></li>
		</ul>
	</div>

	<div class="headerInner clearfix">
		<div id="logoBlock"><p class="brandlogo"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/header_logo_01.png" alt="Panasonic"></p><p class="sitelogo"><a href="/"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/header_logo_02.png" alt="<c:out value='${commonParameters.panasonicSiteEnglish}'/>"></a></p></div>
		<ul id="utilityNav" class="clearfix">
			<c:choose>
				<c:when test="${isLogin == null}">
					<li class="login"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/login/">ログイン</a></li>
					<li class="member"><a href="<c:out value="${pageContext.request.contextPath}"/>/account/member/new/input/">会員登録</a></li>
				</c:when>
				<c:otherwise>
					<li class="login"><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/logout/">ログアウト</a></li>
				</c:otherwise>
			</c:choose>
			<li class="contact"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>inquiry/">お問い合わせ</a></li>
			<li class="sitemap"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/sitemap.html">サイトマップ</a></li>
		</ul>
	</div>
</header>

<div id="mainNav" class="<c:choose><c:when test="${param.subNav != 'off'}">mnav_buy</c:when><c:otherwise>mnav_other</c:otherwise></c:choose>">
	<nav class="clearfix">
		<div id="<c:choose><c:when test="${isLogin == null}">personalInfo02</c:when><c:otherwise>personalInfo</c:otherwise></c:choose>">
			<ul class="clearfix">
				<c:if test="${isLogin == 'true'}">
					<li><a href="<c:out value="${pageContext.request.contextPath}"/>/mypage/favorite/">お気に入り<span class="number favorite"><c:out value="${favoriteCount}"/></span><span class="bold">件</span></a></li>
				</c:if>
				<li><a href="<c:out value="${pageContext.request.contextPath}"/>/history/">最近見た<span class="number"><c:out value="${historyCount}"/></span><span class="bold">件</span></a></li>
			</ul>
		</div>
		<ul class="mainnav clearfix">
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/nav_img_01.gif" alt="買いたい"></a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/nav_img_02.gif" alt="売りたい"></a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>brand/"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/nav_img_03.gif" alt="<c:out value='${commonParameters.panasonicSiteJapan}'/>について"></a></li>
			<li><a href="<c:out value="${mypageUrl}"/>"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>common/img/nav_img_04.gif" alt="マイページ"></a></li>
		</ul>
	</nav>
</div>

<c:if test="${param.subNav != 'off'}">
<div id="subNav" class="snav_buy">
	<nav class="clearfix">
		<ul class="subnav01 clearfix">
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/">買いたいTOP</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/re2/index.html">リー・スクエアについて</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/reform/index.html">リフォームについて</a></li>
			<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/payment/">お支払いについて</a></li>
		</ul>
	</nav>
</div>
</c:if>