<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- ----------------------------------------------------------------
 名称： リフォーム画像編集確認画面

 2015/04/04		fan		新規作成
 2015/08/14		Duong.Nguyen		Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム画像編集確認" />
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
		<h2>リフォーム画像編集確認</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/reformImg/confirm.jsh" />
</div>
<!--/headingAreaInner -->
<br>
<!--flexBlockB03 -->
<div class="flexBlockB06">
		<!--btnBlockC02 -->
		    <div class="btnBlockC14">
		        <div class="btnBlockC14Inner">
		            <div class="btnBlockC14Inner2">
					<p>
						<a href="javascript:linkToUrl('../result/', '<c:out value="${ReformImgForm.command}"/>');"><span>登録</span></a>
					</p>
				</div>
			</div>
		</div>
		<!--/btnBlockC02 -->
		<!--btnBlockC02 -->
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

</c:param>

</c:import>