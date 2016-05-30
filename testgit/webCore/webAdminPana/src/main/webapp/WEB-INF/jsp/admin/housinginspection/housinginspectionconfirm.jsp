<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions"
	prefix="dm3functions"%>
<%-- ----------------------------------------------------------------
 名称： 住宅診断編集確認画面

 2015/04/04		fan		新規作成
 2015/08/14		Duong.Nguyen		Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="住宅診断編集確認画面" />
	<c:param name="contents">
		<head>
<style type="text/css">
.flexBlockB06Inner {
	width: 750px;
	margin: 0 auto;
}

.flexBlockB06 {
	margin-top: 15px;
	width: 100%;
	text-align: right;
}

.flexBlockB06 .btnBlockC01 {
	float: right;
	padding-right: 10px;
}

.btnBlockC01Inner2 a span {
	padding: 0px 0px 0px 0px;
	background: none;
}
</style>
		</head>

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
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
	$(function(){
	    $("#demo2").fancybox();
	});
//-->
</script>

		<form method="post" name="inputForm">

			<!--headingAreaInner -->
			<div class="headingAreaInner">
				<div class="headingAreaB01 start">
					<h2>住宅診断編集確認</h2>
				</div>

				<c:import
					url="/WEB-INF/jsp/admin/include/housinginspection/housinginspectionconfirm.jsh" />

			</div>
			<!--/headingAreaInner -->
			<!--flexBlockB03 -->
			<div class="flexBlockB06">
				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:linkToUrl('../result/', 'update');"><span>登録</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockC03 -->
				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:popupPreview('<c:out value="${pageContext.request.contextPath}"/>/top/housing/preview/');" ><span>プレビュー</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockC02 -->
				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:linkToUrl('../input/', 'back');"><span>戻る</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockB01 -->
			</div>
			<!--/flexBlockB03 -->
		</form>
	</c:param>
</c:import>