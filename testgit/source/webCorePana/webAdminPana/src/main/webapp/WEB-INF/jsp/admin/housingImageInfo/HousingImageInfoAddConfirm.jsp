<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- ----------------------------------------------------------------
 名称： 画像アップロード編集確認画面

 2015/04/05		fan		新規作成
 2015/08/14		Duong.Nguyen		Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="画像アップロード編集確認" />
<c:param name="contents">
<script type ="text/JavaScript">
<!--
	function popupPreview(url) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.submit();
	}
// -->
</script>

<script type="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.target = "";
		document.inputForm.submit();
	}
//-->
</script>
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>画像アップロード編集確認</h2>
	</div>

	<c:import url="/WEB-INF/jsp/admin/include/housingImageInfo/confirm.jsh" />

</div>
<!--/headingAreaInner -->

<!--btnBlockC06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:linkToUrl('../result/','<c:out value="${housingImageInfoForm.command}"/>');"><span>登&nbsp;&nbsp;録</span></a></p>
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