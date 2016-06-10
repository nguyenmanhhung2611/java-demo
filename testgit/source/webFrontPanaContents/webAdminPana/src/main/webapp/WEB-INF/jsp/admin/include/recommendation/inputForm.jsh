<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
おすすめポイント編集画面で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
	function mailFlgDisable(allFlg) {
		if(allFlg) {
			document.inputForm.mailFlg.disabled=true;
		} else {
			document.inputForm.mailFlg.disabled=false;
		}
	}
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="">
		<input type="hidden" name="userId" value="">

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr">物件番号</th>
				<td>&nbsp;
					<c:out value=""/>
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件名称</th>
				<td>&nbsp;
					<c:out value=""/>
				</td>
			</tr>
		</table>
		<br>
		<table width="50%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr">住宅診断済</th>
				<td width="200">					
					<input type="checkBox" name="住宅診断済" value="1" checked/>あり						
				</td>
			</tr>	
			<tr>
				<th class="head_tr">瑕疵保険可能</th>
				<td>
					<input type="checkBox" name="瑕疵保険可能" value="1" checked/>あり
				</td>
			</tr>
			<tr>
				<th class="head_tr">保障継承付</th>
				<td>
					<input type="checkBox" name="保障継承付" value="1"/>あり
				</td>
			</tr>
			<tr>
				<th class="head_tr">SumStock</th>
				<td>
					<input type="checkBox" name="SumStock" value="1"/>あり
				</td>
			</tr>
			<tr>
				<th class="head_tr">一体型ローン可</th>
				<td>
					<input type="checkBox" name="一体型ローン可" value="1"/>あり
				</td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
