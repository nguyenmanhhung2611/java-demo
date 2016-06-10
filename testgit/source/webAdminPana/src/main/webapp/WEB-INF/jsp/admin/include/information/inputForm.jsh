<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<%--
お知らせメンテナンス機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">
<!--
	function mailFlgDisable(allFlg) {
		document.inputForm.mailFlg.disabled=allFlg;
		document.inputForm.memberName.disabled=allFlg;
		$("#memberList").attr("disabled", allFlg);
	}
	function openMemberSearch(url) {
		window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
	}

	$(document).ready(function(){
		var dspFlg = "<c:out value='${inputForm.dspFlg}' />";

		if(dspFlg == 0) {
			$("input[name=dspFlg]:eq(0)").attr("checked","checked");
		}

	});
// -->
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="informationNo" value="<c:out value="${inputForm.informationNo}"/>">
		<input type="hidden" id="inputUserId" name="userId" value="<c:out value="${inputForm.userId}"/>">
		<input type="hidden" id="email" name="email" value="<c:out value="${inputForm.email}"/>">
		<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="15%">お知らせ番号</th>
				<td width="35%">
					<c:out value="${inputForm.informationNo}"/>&nbsp;
				</td>
				<th class="head_tr" width="15%">登録日時</th>
				<td width="35%">
				    <div style="white-space: nowrap"><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${informationInsDate.insDate}"/>&nbsp</div>
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="15%">種別</th>
				<td colspan="3" width="85%">
					<dm3lookup:lookupForEach lookupName="information_type">
					<label>
					<input type="radio" name="informationType" value="<c:out value="${key}"/>" <c:if test="${inputForm.informationType == key}">checked</c:if>><c:out value="${value}"/>&nbsp;
					</label>
					</dm3lookup:lookupForEach>
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="15%">対象会員</th>
				<td colspan="3" width="85%"><div class="inputStyle">
					<div class="inputStyle inputStyle2">
						<dm3lookup:lookupForEach lookupName="information_dspFlg">
						<%-- 個人指定した場合、個乳の入力属性を表示する。 コードがハードコーディングされているので、コード変更時は注意する事。--%>
						<c:set var="inpMode" value="true" />
						<c:if test="${key == '2'}" >
							<c:set var="inpMode" value="false" />
						</c:if>
						<label>
							<input type="radio" name="dspFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.dspFlg == key}"> checked </c:if> onclick="mailFlgDisable(<c:out value="${inpMode}"/>)"><c:out value="${value}"/>&nbsp;
						</label>
						</dm3lookup:lookupForEach>
						<input type="text" id="inputUserName" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if> name="memberName" value="<c:out value="${inputForm.memberName}"/>" size="10" class="input2" readonly="readonly">
					</div>
				    <!--btnBlockC11 -->
				    <div class="btnBlockC11">
				        <div class="btnBlockC11Inner">
				            <div class="btnBlockC11Inner2">
								<p><a id="memberList" class="delBtn" href="javascript:openMemberSearch('<c:out value="${pageContext.request.contextPath}"/>/top/mypage/search/');" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if>><span>参照</span></a></p>
							</div>
						</div>
					</div>
					<div>
						<label><input type="checkBox" name="mailFlg" <c:if test="${inputForm.dspFlg != '2'}">disabled="ture"</c:if> value="1" <c:if test="${inputForm.mailFlg == '1'}">checked</c:if>/>メール送信する</label>&nbsp;&nbsp;

						<div style="float:right;padding-right:1px;"><span><font color="red" style="font-size:11px">※メールは登録後<br>　すぐに配信されます。</font></span></div>
					</div>
				</td>
			</tr>
			<tr>
				<th class="head_tr"  width="15%">タイトル</th>
				<td colspan="3" width="85%"><input type="text" name="title" value="<c:out value="${inputForm.title}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.title" defaultValue="200"/>" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr"  width="15%">お知らせ<br>内容</th>
				<td colspan="3" width="85%"><textarea cols="45" rows="8" name="informationMsg"><c:out value="${inputForm.informationMsg}"/></textarea></td>
			</tr>
			<tr>
				<th class="head_tr"  width="15%">表示期間</th>
				<td colspan="3" width="85%">
					 <input name="startDate" value="<c:out value="${inputForm.startDate}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled"> ～ <input name="endDate" value="<c:out value="${inputForm.endDate}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled">
					 &nbsp;&nbsp;(yyyy/mm/dd)<div style="float:right;padding-right:20px;"><font color="red" style="font-size:11px">※表示期間に指定がない場合、<br>登録後すぐに表示開始し、<br>また永久に表示されます。</font></div>
				</td>
			</tr>
			<tr>
				<th class="head_tr"  width="15%">リンク先URL</th>
				<td colspan="3" width="85%"><input type="text" name="url" value="<c:out value="${inputForm.url}"/>" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.url" defaultValue="255"/>" class="input2 ime-disabled"></td>
			</tr>
		</table>

	</form>
</div>
<!--/flexBlockA01 -->

<%-- コピーして新規作成form引き継ぎ --%>
<form action="" method="post" name="copydate">
	<input type="hidden" name="informationNo" value="">
	<c:import url="/WEB-INF/jsp/admin/include/information/searchParams.jsh" />
</form>

<script type ="text/JavaScript">
<!--
	function linkToCopy (informationNo) {
		document.copydate.action = "../../new/copy/";
		document.copydate.informationNo.value = informationNo;
		document.copydate.submit();
	}

	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
