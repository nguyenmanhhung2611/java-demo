<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 地域情報編集確認画面

 2015/03/05		荊学ギョク	新規作成
 2015/08/14		Duong.Nguyen	Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="地域情報編集確認" />
<c:param name="contents">

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>地域情報編集確認</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/region/confirm.jsh" />

</div>
<!--/headingAreaInner -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:linkToUrl('../result/', '');"><span>登&nbsp;&nbsp;録</span></a></p>
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

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:linkToUrl('../input/', 'back');"><span>戻&nbsp;&nbsp;る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->
</c:param>
</c:import>