<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム詳細情報編集画面

 2015/04/04		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム詳細情報編集" />
<c:param name="contents">
<script type ="text/JavaScript">
<!--
function linkToUrl(url, cmd) {
	document.reformDtlInsForm.action=url;
    document.reformDtlInsForm.command.value=cmd;
	document.reformDtlInsForm.submit();
}
function linkToUrlBack(url, cmd) {
	document.reformDtlInsForm.action=url;
	document.reformDtlInsForm.enctype="application/x-www-form-urlencoded";
	document.reformDtlInsForm.encoding="application/x-www-form-urlencoded";
    document.reformDtlInsForm.command.value=cmd;
	document.reformDtlInsForm.submit();
}
//-->
</script>
<form action="addConfirm" method="post" enctype="multipart/form-data"  name="reformDtlInsForm">

<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム詳細情報登録</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/reformDlt/inputForm1.jsh"/>
</div>
<div class="flexBlockB06">
    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
                <p><a href="javascript:linkToUrl('../confirm/', 'insert');"><span>登録</span></a></p>
            </div>
        </div>
    </div>

    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
                <p><a href="javascript:linkToUrlBack('../../input/', 'back');"><span>戻る</span></a></p>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム詳細情報編集</h2>
	</div>
	<c:import url="/WEB-INF/jsp/admin/include/reformDlt/inputForm2.jsh"/>

</div>
<div class="flexBlockB06">
		<c:if test="${reformDtlForm.divNo != null}">
        <div class="btnBlockC14">
            <div class="btnBlockC14Inner">
                <div class="btnBlockC14Inner2">
                    <p><a href="javascript:linkToUrl('../confirm/', 'update');"><span>登録</span></a></p>
                </div>
            </div>
        </div>
		</c:if>
		<c:if test="${reformDtlForm.divNo == null}">
        <div class="btnBlockC14">
            <div class="btnBlockC14Inner">
                <div class="btnBlockC14Inner2">
                    <p><a disabled="disabled"><span>登録</span></a></p>
                </div>
            </div>
        </div>
		</c:if>
        <div class="btnBlockC14">
            <div class="btnBlockC14Inner">
                <div class="btnBlockC14Inner2">
                    <p><a href="javascript:linkToUrlBack('../../input/', 'back');"><span>戻る</span></a></p>
                </div>
            </div>
        </div>
    </div>
</form>
</c:param>
</c:import>