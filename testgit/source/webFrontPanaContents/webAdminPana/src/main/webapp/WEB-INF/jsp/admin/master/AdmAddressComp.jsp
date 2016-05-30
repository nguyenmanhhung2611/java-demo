<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 住所マスタ取り込み完了画面

 2015/02/05		H.Mizuno	Shamaison 管理サイトを参考に新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="マスタ管理" />
<c:param name="contents">

<script type="text/javascript">
<!--
function btnLock(){
	if(document.uploadForm.submitFlg.value=="1"){
		return false;
	}
	document.uploadForm.submitFlg.value="1";
	document.uploadForm.btnSubmit.value="更新中...";
	document.getElementById('errormsg').innerText = "更新中です。しばらくお待ちください。\n\n";
}
//-->
</script>

<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>郵便マスタ取込み</h2>
	</div>

	<!--flexBlockB02 -->
	<div class="compFont">
		<p >郵便マスタ情報の更新が完了しました。</p>
	</div>
	<!--/flexBlockB02 -->

	<!--flexBlockB02 -->
		<!--btnBlockC02 -->
		 <div class="btnBlockC15">
            <div class="btnBlockC15Inner">
                <div class="btnBlockC15Inner2">
					<p><a href="../upload/"><span>郵便マスタ取込みに戻る</span></a></p>
                </div>
            </div>
        </div>
		<!--/btnBlockB01 -->

</div>
<!--/headingAreaInner -->

</c:param>
</c:import>






