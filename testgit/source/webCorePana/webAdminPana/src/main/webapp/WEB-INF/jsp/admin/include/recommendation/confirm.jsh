<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- 
お知らせメンテナンス機能で使用する入力確認画面の出力 
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
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
			<td width="200">&nbsp;
				<c:if test="">あり</c:if>
			</td>
		</tr>	
		<tr>
			<th class="head_tr">瑕疵保険可能</th>
			<td>&nbsp;
				<c:if test="">あり</c:if>
			</td>
		</tr>
		<tr>
			<th class="head_tr">保障継承付</th>
			<td>&nbsp;
				<c:if test="">なし</c:if>
			</td>
		</tr>
		<tr>
			<th class="head_tr">SumStock</th>
			<td>&nbsp;
				<c:if test="">なし</c:if>
			</td>
		</tr>
		<tr>&nbsp;
			<th class="head_tr">一体型ローン可</th>
			<td>&nbsp;
				<c:if test="">なし</c:if>
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">	
	<input type="hidden" name="住宅診断済" value="">
	<input type="hidden" name="瑕疵保険可能" value="">
	<input type="hidden" name="保障継承付" value="">
	<input type="hidden" name="SumStock" value="">
	<input type="hidden" name="一体型ローン可" value="">
	<input type="hidden" name="userId" value="">
</form>

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
