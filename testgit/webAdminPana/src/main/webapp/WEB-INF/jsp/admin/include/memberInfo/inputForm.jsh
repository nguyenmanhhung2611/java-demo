<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム情報編集機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="lastLogin" value="<c:out value="${inputForm.lastLogin}"/>">
		<input type="hidden" name="insDate" value="<c:out value="${inputForm.insDate}"/>">
		<input type="hidden" name="updDate" value="<c:out value="${inputForm.updDate}"/>">
		<input type="hidden" name="promoCd" value="<c:out value="${inputForm.promoCd}"/>">
		<input type="hidden" name="refCd" value="<c:out value="${inputForm.refCd}"/>">
		<c:import url="/WEB-INF/jsp/admin/include/memberInfo/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="20%">会員番号</th>
				<td width="30%">
					<c:out value="${searchForm.userId}"/>&nbsp;
				</td>
				<th class="head_tr" width="20%">最終ログイン日時</th>
				<td width="30%">
					<c:out value="${inputForm.lastLogin}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">登録日時</th>
				<td>
					<c:out value="${inputForm.insDate}"/>&nbsp;
				</td>
				<th class="head_tr">最終更新日時</th>
				<td>
					<c:out value="${inputForm.updDate}"/>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr">プロモコード</th>
				<td colspan="3"><c:out value="${inputForm.promoCd}"/>&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">メールアドレス<font color="red">※</font></th>
				<td colspan="3"><input type="text" name="email" value="<c:out value="${inputForm.email}"/>" size="40" maxlength="255" class="input2 ime-disabled"></td>
			</tr>
			<tr>
				<th class="head_tr">パスワード<font color="red">※</font></th>
				<td colspan="3">
					<div class="inputStyle" style="padding-right:10px;"><input type="password" id="password" name="password" maxlength="16" value="<c:out value="${inputForm.password}"/>" size="14" class="input2 ime-disabled"></div>
 				<%--<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" id="mkPwd" href="javascript:void(0);"><span>ランダム生成<span></a></p>
							</div>
						</div>
					</div>--%>
				</td>
			</tr>
			<tr>
				<th class="head_tr">パスワード確認<font color="red">※</font></th>
				<td colspan="3"><input type="password" id="passwordChk" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>" size="14" maxlength="16" class="input2 ime-disabled"></td>
			</tr>
			<tr>
				<th class="head_tr">お名前（姓）<font color="red">※</font></th>
				<td><input type="text" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>" size="14" maxlength="30" class="input2"></td>
				<th class="head_tr">お名前（名）<font color="red">※</font></th>
				<td><input type="text" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>" size="14" maxlength="30" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">フリガナ（セイ）<font color="red">※</font></th>
				<td><input type="text" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>" size="14" maxlength="30" class="input2"></td>
				<th class="head_tr">フリガナ（メイ）<font color="red">※</font></th>
				<td><input type="text" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>" size="14" maxlength="30" class="input2"></td>
			</tr>
			<tr>
				<th class="head_tr">住所<font color="red">※</font></th>
				<td colspan="3">
					<div class="inputStyle" style="padding-right:10px;">〒
					<input type="text" name="zip" value="<c:out value="${inputForm.zip}"/>" size="5" maxlength="7" class="input2 ime-disabled"></div>
					<div class="btnBlockC13">
						<div class="btnBlockC13Inner">
							<div class="btnBlockC13Inner2">
								<p><a class= "tblNormalBtn" href="javascript:linkToUrl('../getAddress/','back');"><span>住所検索<span></a></p>
							</div>
						</div>
					</div>
					<div class="inputStyle">
					<select name="prefCd">
						<option></option>
						<c:forEach items="${prefMstList}" var="prefMst">
							<option value="<c:out value="${prefMst.prefCd}"/>"<c:if test="${prefMst.prefCd == inputForm.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
						</c:forEach>
					</select>
					</div>
					<div class="inputStyle" style="padding-left:8px;"><input type="text" name="address" value="<c:out value="${inputForm.address}"/>" size="18" maxlength="50" class="input2"></div>
					<BR><BR>
					<div class="inputStyle" style="padding-left:15px;">
					<input type="text" name="addressOther" value="<c:out value="${inputForm.addressOther}"/>" size="43" maxlength="30" style="width: 540px;" class="input2">
					</div>
				</td>
			</tr>
			<tr>
				<th class="head_tr">居住形態</th>
				<td colspan="3">
					<select name="residentFlg">
						<dm3lookup:lookupForEach lookupName="residentFlg">
							<option value="<c:out value="${key}"/>" <c:if test="${inputForm.residentFlg == key}"> selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
					</select>
				</td>
			</tr>
			<tr>
				<th class="head_tr">物件希望地域<font color="red">※</font></th>
				<td colspan="3">
					<select name="hopePrefCd">
						<option></option>
						<c:forEach items="${prefMstList}" var="prefMst">
							<option value="<c:out value="${prefMst.prefCd}"/>"<c:if test="${prefMst.prefCd == inputForm.hopePrefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
						</c:forEach>
					</select>
					<input type="text" name="hopeAddress" value="<c:out value="${inputForm.hopeAddress}"/>" size="" maxlength="50" class="input2">
				</td>
			</tr>
			<tr>
				<th class="head_tr">電話番号<font color="red">※</font></th>
				<td><input type="text" name="tel" value="<c:out value="${inputForm.tel}"/>" size="14" maxlength="11" class="input2 ime-disabled"></td>
				<th class="head_tr">FAX番号</th>
				<td><input type="text" name="fax" value="<c:out value="${inputForm.fax}"/>" size="14" maxlength="11" class="input2 ime-disabled"></td>
			</tr>
			<tr>
				<th class="head_tr">メルマガ配信<font color="red">※</font></th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="mailSendFlg">
						<label><input type="radio" name="mailSendFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.mailSendFlg == key}">checked</c:if>><c:out value="${value}"/></label>&nbsp;&nbsp;
					</dm3lookup:lookupForEach>
				</td>
			</tr>
			<tr>
				<th class="head_tr">アンケート</th>
				<td colspan="3">
					<dm3lookup:lookup lookupName="question" lookupKey="001"/><br>
					<ul style="list-style-type:none;">
						<dm3lookup:lookupForEach lookupName="ans_etcNs">
							<li style="width:48%; display:inline-block;"><label><input type="checkbox" name="questionId" value="<c:out value="${key}"/>"
									<c:forEach items="${inputForm.questionId}" var="selectedQuestion"><c:if test="${selectedQuestion == key}">checked</c:if></c:forEach>>
								<c:out value="${value}"/></label>
							</li>
						</dm3lookup:lookupForEach>
					</ul>
					<ul style="list-style-type:none;">
						<li><div style="margin-bottom:5px !important;"><label><input type="checkbox" id="questionId08" name="questionId" value="008" <c:forEach items="${inputForm.questionId}" var="selectedQuestion"><c:if test="${selectedQuestion == '008'}">checked</c:if></c:forEach>>
								<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="008"/></label>
							</div>
							<input type="text" id="etcAnswer1" name="etcAnswer1" value="<c:out value="${inputForm.etcAnswer1}"/>" maxlength="50" style="width: 450px"></li>
						<li><div style="margin-bottom:5px !important;"><label><input type="checkbox" id="questionId09" name="questionId" value="009" <c:forEach items="${inputForm.questionId}" var="selectedQuestion"><c:if test="${selectedQuestion == '009'}">checked</c:if></c:forEach>>
								<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="009"/>
							</label></div>
							<input type="text" id="etcAnswer2" name="etcAnswer2" value="<c:out value="${inputForm.etcAnswer2}"/>" maxlength="50" style="width: 450px"></li>
						<li><div style="margin-bottom:5px !important;"><label><input type="checkbox" id="questionId10" name="questionId" value="010" <c:forEach items="${inputForm.questionId}" var="selectedQuestion"><c:if test="${selectedQuestion == '010'}">checked</c:if></c:forEach>>
								<dm3lookup:lookup lookupName="ans_etcAr" lookupKey="010"/>
							</label></div>
							<input type="text" id="etcAnswer3" name="etcAnswer3" value="<c:out value="${inputForm.etcAnswer3}"/>" maxlength="50" style="width: 450px"></li>
					</ul>
				</td>
			</tr>
			<tr>
				<th class="head_tr">登録経路&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="entryRoute">
						<c:if test="${key != '000'}">
						<label><input type="radio" name="entryRoute" value="<c:out value="${key}"/>" <c:if test="${inputForm.entryRoute == key}">checked</c:if>><c:out value="${value}"/></label>&nbsp;&nbsp;
						</c:if>
					</dm3lookup:lookupForEach>
				</td>
			</tr>
			<tr>
				<th class="head_tr">特定流入元</th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="refCd">
						<c:if test="${inputForm.refCd == key}"><c:out value="${value}"/></c:if>
					</dm3lookup:lookupForEach>
				&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr">有効区分&nbsp;&nbsp;<font color="red">※</font></th>
				<td colspan="3">
					<dm3lookup:lookupForEach lookupName="lockFlg">
						<label><input type="radio" name="lockFlg" value="<c:out value="${key}"/>" <c:if test="${inputForm.lockFlg == key}">checked</c:if>><c:out value="${value}"/></label>&nbsp;&nbsp;
					</dm3lookup:lookupForEach>
				</td>
			</tr>
		</table>
	</form>
</div>
<!--/flexBlockA01 -->

<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">
<!--

	$(document).ready( function() {
		$("#mkPwd").click(function(){
			$.getJSON("../../mkpwd/", function(data, status){
				$("#password").val(data.password);
				$("#passwordChk").val(data.password);
			});
		});

		if (!$("#questionId08").prop('checked')) {
			$("#etcAnswer1").attr('disabled', 'disabled');
		}

		if (!$("#questionId09").prop('checked')) {
			$("#etcAnswer2").attr('disabled', 'disabled');
		}

		if (!$("#questionId10").prop('checked')) {
			$("#etcAnswer3").attr('disabled', 'disabled');
		}
		$("#questionId08").on("change", function() {
			if ($("#questionId08").prop('checked')) {
				$("#etcAnswer1").removeAttr('disabled');
			} else {
				$("#etcAnswer1").attr('disabled', 'disabled');
			}
		});
		$("#questionId09").on("change", function() {
			if ($("#questionId09").prop('checked')) {
				$("#etcAnswer2").removeAttr('disabled');
			} else {
				$("#etcAnswer2").attr('disabled', 'disabled');
			}
		});
		$("#questionId10").on("change", function() {
			if ($("#questionId10").prop('checked')) {
				$("#etcAnswer3").removeAttr('disabled');
			} else {
				$("#etcAnswer3").attr('disabled', 'disabled');
			}
		});
	});

	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}
// -->
</script>
