<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： 住所マスタ取り込み画面

 2015/02/19		H.Mizuno	Shamaison 管理サイトを参考に新規作成
---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="マスタ管理" />
<c:param name="contents">

<script type="text/javascript">
<!--
function btnLock(url){
	document.uploadForm.action = url;
	document.getElementById("uploadBt").style.display="none";
	document.getElementById("uploadBtDisable").style.display="";
	document.uploadForm.submit();
}
//-->
</script>

<form action="../upload/?command=upload" method="post" name="uploadForm" enctype="multipart/form-data">
	<input type="hidden" name="submitFlg" value="">

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>郵便マスタ取込み</h2>
		</div>

		<p id="errormsg"><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<p>参照ボタンをクリックしてアップロードするファイルを選択してください。</p>
			<br>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="18%">アップロードファイル</th>
					<td width="65%"><input type="file" name="csvFile" size="35"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

	</div>
	<!--/headingAreaInner -->

	<br><br><br>
	<!--flexBlockB01 -->

		<div class="btnBlockC14">
		<div class="btnBlockC14Inner">
		   <div class="btnBlockC14Inner2">
		        <div id="uploadBt"><p><a href="javascript:btnLock('../upload/?command=upload')"><span>登　録</span></a></p></div>
		        <div id="uploadBtDisable" style="display: none;"><p><a disabled="disabled"><span>登　録</span></a></p></div>
		   </div>
		 </div>
	 	</div>
	<!--/flexBlockB01 -->

</form>

</c:param>
</c:import>






