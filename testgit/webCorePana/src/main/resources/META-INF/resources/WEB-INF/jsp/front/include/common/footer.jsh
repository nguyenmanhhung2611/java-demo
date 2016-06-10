<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>
<p id="pageTop"><a href="#ptop"><img src="<c:out value='${commonParameters.commonResourceRootUrl}'/>common/img/footer_pagetop.png" alt="ページトップへ戻る"></a></p>

<footer id="footer">

	<div id="SPfooterNav">
		<ul>
			<li class="linkTop"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>">TOP</a></li>
			<li class="category01"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/">買いたい</a></li>
			<li class="category02"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/">売りたい</a></li>
			<li class="category03"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/<c:out value='${commonParameters.panasonicSiteEnglish}'/>/"><c:out value='${commonParameters.panasonicSiteJapan}'/><br>について</a></li>
		</ul>
	</div>

	<div id="siteCategory" class="clearfix">
		<ul>
			<li>
				<dl class="category01">
					<dt><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/">買いたい</a></dt>
					<dd><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/rexre/">Re×Reについて</a></dd>
					<dd><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/renovation/">リフォームについて</a></dd>
					<dd><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/payment/">お支払いについて</a></dd>
				</dl>
			</li>
			<li>
				<dl class="category02">
					<dt><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/">売りたい</a></dt>
					<dd><a href="#">ご売却について</a></dd>
					<dd><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/sale/">ご売却の流れ</a></dd>
				</dl>
			</li>
			<li>
				<p class="category03"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>sell/<c:out value='${commonParameters.panasonicSiteEnglish}'/>/"><c:out value='${commonParameters.panasonicSiteJapan}'/>について</a></p>
			</li>
			<li>
				<ul>
					<li><a href="#">パナソニックについて</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/help.html">利用案内</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>event/">セミナー・イベント</a></li>
					<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/link.html">リンク集</a></li>
				</ul>
			</li>
		</ul>
	</div>

	<div id="footerMenu">
		<div class="menuInner clearfix">
			<ul class="clearfix">
				<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/regulation.html">サイトについて・免責事項</a></li>
				<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/agreement.html">会員規約</a></li>
				<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>info/privacy.html">個人情報の取り扱いについて</a></li>
				<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>inquiry/">お問い合わせ</a></li>
				<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>">サイトマップ</a></li>
			</ul>
			<p id="copyright">Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Panasonic Corporation</p>
		</div>
	</div>
</footer>

