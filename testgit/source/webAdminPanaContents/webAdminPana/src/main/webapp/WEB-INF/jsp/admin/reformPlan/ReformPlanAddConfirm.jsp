<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム情報編集確認画面

 2015/03/03		荊学ギョク	新規作成
 2015/08/14		Duong.Nguyen	Add CRSF token
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム情報編集確認" />
<c:param name="contents">

<head>
<style type="text/css">
.flexBlockB06 {
	margin-top: 15px;
	width: 100%;
}
.flexBlockB06 .btnBlockC01{
	float: right;
	padding-right: 10px;
}
.btnBlockC01Inner2 a span{
	padding: 0px 0px 0px 0px;
	background: none;
}
</style>
</head>

<!--headingAreaInner -->
<div class="headingAreaInner">

	<c:import url="/WEB-INF/jsp/admin/include/reformPlan/confirm.jsh" />

</div>
<!--/headingAreaInner -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
    <!--btnBlockC14 -->
    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
				<p>	<a href="javascript:comp();"><span>登&nbsp;&nbsp;録</span></a></p>
			</div>
		</div>
	</div>
	<!--btnBlockC14 -->

    <!--btnBlockC14 -->
    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
				<p>	<a href="javascript:popupPreview('<c:out value="${pageContext.request.contextPath}"/>/top/housing/preview/');" ><span>プレビュー</span></a></p>
			</div>
		</div>
	</div>
	<!--btnBlockC14 -->

    <!--btnBlockC14 -->
    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
				<p>	<a href="javascript:back();"><span>戻&nbsp;&nbsp;る</span></a></p>
			</div>
		</div>
	</div>
	<!--btnBlockC14 -->
</div>
<!--flexBlockB06 -->

</c:param>
</c:import>