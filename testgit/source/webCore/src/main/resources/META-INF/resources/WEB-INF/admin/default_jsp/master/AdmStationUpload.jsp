<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 駅マスタ取り込み画面

 2015/02/05		H.Mizuno	Shamaison 管理サイトを参考に新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
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

<form action="../upload/?command=upload" method="post" name="uploadForm" enctype="multipart/form-data" onsubmit="return btnLock()">
	<input type="hidden" name="submitFlg" value="">

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>沿線・駅マスタ取込み</h2>
		</div>

		<p id="errormsg"><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<p>参照ボタンをクリックしてアップロードするファイルを選択してください。</p>

			<table class="inputBox">
				<tr>
					<th class="head_tr">アップロードファイル</th>
					<td><input type="file" name="csvFile" size="40"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

	</div>
	<!--/headingAreaInner -->

	<br><br><br>
	<!--flexBlockB01 -->
	<div class="flexBlockB01">
		<input type="submit" name="btnSubmit" value="　更新　">
	</div>
	<!--/flexBlockB01 -->

</form>

</c:param>
</c:import>




					
					
