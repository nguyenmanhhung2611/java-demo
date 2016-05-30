<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions"
	prefix="dm3functions"%>
<%-- ----------------------------------------------------------------
 名称： リフォーム詳細情報編集確認画面

 2015/04/04		fan		新規作成
 2015/08/14		Duong.Nguyen		Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="リフォーム詳細情報編集確認" />
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
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>リフォーム詳細情報編集確認</h2>
			</div>

			<c:import url="/WEB-INF/jsp/admin/include/reformDlt/confirm.jsh" />

		</div>
		<div class="flexBlockB06">
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a
								href="javascript:linkToUrl('../result/','<c:out value="${reformDtlForm.command}"/>');"><span>登録</span></a>
						</p>
					</div>
				</div>
			</div>

			<div class="btnBlockC14">
        		<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>	<a href="javascript:popupPreview('<c:out value="${pageContext.request.contextPath}"/>/top/housing/preview/');" ><span>プレビュー</span></a></p>
					</div>
				</div>
			</div>

			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkToUrl('../input/', 'back');"><span>戻る</span></a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</c:param>
</c:import>