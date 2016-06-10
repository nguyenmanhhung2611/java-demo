<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%-- 
お知らせメンテナンス機能で使用する入力フォームの出力 
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
	function mailFlgDisable(allFlg) {
		document.inputForm.mailFlg.disabled=allFlg;
		document.inputForm.memberName.disabled=allFlg;
		document.inputForm.memberList.disabled=allFlg;
	}
	function openMemberSearch() {
		var child=window.open("<c:out value="${pageContext.request.contextPath}"/>/top/mypage/search/","list","height=500,width=750,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,left=200");
	}
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="informationNo" value="<c:out value="${inputForm.informationNo}"/>">
		<input type="hidden" id="inputUserId" name="userId" value="<c:out value="${inputForm.userId}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />

		<table class="inputBox">
			<tr>
				<th class="head_tr">お知らせ番号</th>
				<td width="200"><c:out value="${inputForm.informationNo}"/>&nbsp;</td>
				<th class="head_tr" width="50">登録日時</th>
				<td width="100">
				    <div style="white-space: nowrap"><c:out value="${informationInsDate.insDate}"/>&nbsp</div>
				</td>
			</tr>
			<tr>
				<th class="head_tr">種別&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="information_type">
					<input type="radio" name="informationType" value="<c:out value="${key}"/>" <c:if test="${inputForm.informationType == key}">checked</c:if>><c:out value="${value}"/>&nbsp;
					</dm3lookup:lookupForEach>
				</td>
			</tr>	
			<tr>
				<th class="head_tr">公開対象区分&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="information_dspFlg">
					<%-- 個人指定した場合、個乳の入力属性を表示する。 コードがハードコーディングされているので、コード変更時は注意する事。--%>
					<c:set var="inpMode" value="true" />
					<c:if test="${key == '2'}" >
						<c:set var="inpMode" value="false" />
					</c:if>
					<input type="radio" name="dspFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.dspFlg == key}">checked</c:if> onclick="mailFlgDisable(${inpMode})"><c:out value="${value}"/>&nbsp;
					</dm3lookup:lookupForEach>
					<input type="text" id="inputUserName" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if> name="memberName" value="<c:out value="${inputForm.memberName}"/>" size="10" class="input2" readonly="readonly">
					<input type="button" name="memberList" onclick="openMemberSearch()" value="参照" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if>/>
					&nbsp;&nbsp;
					<input type="checkBox" name="mailFlg" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if> value="1"/>メール送信する<br/>
					<div style="width=100%; text-align: right;"><font color="red">※メールは登録後すぐに配信されます。</font></div>
				</td>
			</tr>
			<tr>
				<th class="head_tr">タイトル&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3"><input type="text" name="title" value="<c:out value="${inputForm.title}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.title" defaultValue="200"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">お知らせ<br>内容&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3"><textarea cols="45" rows="8" name="informationMsg"><c:out value="${inputForm.informationMsg}"/></textarea></td>
			</tr>
			<tr>
				<th class="head_tr">表示期間</th>
				<td colspan="3">
					<input name="startDate" value="<c:out value="${inputForm.startDate}"/>" type="text" size="10" maxlength="10" class="input2"> ～ <input name="endDate" value="<c:out value="${inputForm.endDate}"/>" type="text" size="10" maxlength="10" class="input2"><br/>
					<font color="red">※表示期間に指定がない場合、登録後すぐに表示開始し、また永久に表示されます。</font>
				</td>
			</tr>
			<tr>
				<th class="head_tr">リンク先URL</th>
				<td colspan="3"><input type="text" name="url" value="<c:out value="${inputForm.url}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.url" defaultValue="255"/>" class="input2"></td>
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
