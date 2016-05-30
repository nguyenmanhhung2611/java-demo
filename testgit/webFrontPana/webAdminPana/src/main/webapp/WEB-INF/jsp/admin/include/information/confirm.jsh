<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
お知らせメンテナンス機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr height="50">
			<th class="head_tr" width="15%">お知らせ番号</th>
			<td><c:out value="${inputForm.informationNo}"/>&nbsp;</td>
		</tr>
		<tr height="50">
			<th class="head_tr width="15%"">種別</th>
			<td><dm3lookup:lookup lookupName="information_type" lookupKey="${inputForm.informationType}"/></td>
		</tr>

		<tr height="50">
			<th class="head_tr" width="15%">公開対象区分</th>
			<td>
				<dm3lookup:lookup lookupName="information_dspFlg" lookupKey="${inputForm.dspFlg}"/>
				<%-- 個人指定した場合、個乳の入力属性を表示する。 コードがハードコーディングされているので、コード変更時は注意する事。--%>
				<c:if test="${inputForm.dspFlg == '2'}">
					（<c:out value="${inputForm.userId}"/>：<c:out value="${inputForm.memberName}"/>）
				</c:if>
				<c:if test="${inputForm.mailFlg == '1'}">
						メール送信する
						<div style="float:right;padding-right:10px;">
							<span><font color="red" style="font-size:11px">※メールは登録後すぐに配信されます。</font></span>
						</div>
				</c:if>
			</td>
		</tr>
		<tr height="50">
			<th class="head_tr" width="15%">タイトル</th>
			<td><c:out value="${inputForm.title}"/></td>
		</tr>
		<tr height="50">
			<th class="head_tr" width="15%">お知らせ内容</th>
			<td>${dm3functions:crToHtmlTag(inputForm.informationMsg)}</td>
		</tr>
		<tr height="50">
			<th class="head_tr" width="15%">表示期間</th>
			<td>
				<div style="float:left;margin-top:10px;">
				<c:if test="${!empty inputForm.startDate || !empty inputForm.endDate}">
				<c:out value="${inputForm.startDate}"/> ～ <c:out value="${inputForm.endDate}"/>
				</c:if>
				</div>
				<div style="float:right;padding-right:110px;"><font color="red" style="font-size:11px">※表示期間に指定がない場合、登録後すぐに表示開始し、<br>　また永久に表示されます。</font></div>
			</td>
		</tr>
		<tr height="50">
			<th class="head_tr" width="15%">リンク先URL</th>
			<td><a href="<c:out value="${inputForm.url}"/>" target="_blank"><c:out value="${inputForm.url}"/></a></td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />
	<input type="hidden" name="informationNo" value="<c:out value="${inputForm.informationNo}"/>">
	<input type="hidden" name="informationType" value="<c:out value="${inputForm.informationType}"/>">
	<input type="hidden" name="title" value="<c:out value="${inputForm.title}"/>">
	<input type="hidden" name="startDate" value="<c:out value="${inputForm.startDate}"/>">
	<input type="hidden" name="endDate" value="<c:out value="${inputForm.endDate}"/>">
	<input type="hidden" name="url" value="<c:out value="${inputForm.url}"/>">
	<input type="hidden" name="dspFlg" value="<c:out value="${inputForm.dspFlg}"/>">
	<input type="hidden" name="memberName" value="<c:out value="${inputForm.memberName}"/>">
	<input type="hidden" name="mailFlg" value="<c:out value="${inputForm.mailFlg}"/>">
	<input type="hidden" name="informationMsg" value="<c:out value="${inputForm.informationMsg}"/>">
	<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
	<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">
<!--
function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}
//-->
</script>
