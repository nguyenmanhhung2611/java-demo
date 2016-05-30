<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
リフォーム情報編集機能で使用する入力確認画面の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
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
			<th class="head_tr">メールアドレス</th>
			<td colspan="3"><c:out value="${inputForm.email}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">パスワード</th>
			<td colspan="3">
				<c:if test="${empty inputForm.password }">
					&nbsp;
				</c:if>
				<c:if test="${!empty inputForm.password }">
					<c:out value="********"/>
				</c:if>

			</td>
		</tr>
		<tr>
			<th class="head_tr">お名前（姓）</th>
			<td><c:out value="${inputForm.memberLname}"/>&nbsp;</td>
			<th class="head_tr">お名前（名）</th>
			<td><c:out value="${inputForm.memberFname}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">フリガナ（セイ）</th>
			<td><c:out value="${inputForm.memberLnameKana}"/>&nbsp;</td>
			<th class="head_tr">フリガナ（メイ）</th>
			<td><c:out value="${inputForm.memberFnameKana}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">住所</th>
			<td colspan="3">
				〒<c:out value="${inputForm.zip}"/><c:out value="${inputForm.prefName}"/><c:out value="${inputForm.address}"/><c:out value="${inputForm.addressOther}"/>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">居住形態</th>
			<td colspan="3">
                <dm3lookup:lookupForEach lookupName="residentFlg">
                    <c:if test="${inputForm.residentFlg == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">物件希望地域</th>
			<td colspan="3">
				<c:out value="${inputForm.hopePrefName}"/>
				<c:out value="${inputForm.hopeAddress}"/>
			</td>
		</tr>
		<tr>
			<th class="head_tr">電話番号</th>
			<td><c:out value="${inputForm.tel}"/>&nbsp;</td>
			<th class="head_tr">FAX番号</th>
			<td><c:out value="${inputForm.fax}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">メルマガ配信</th>
			<td colspan="3">
                <dm3lookup:lookupForEach lookupName="mailSendFlg">
                    <c:if test="${inputForm.mailSendFlg == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">アンケート</th>
			<td colspan="3">
				<dm3lookup:lookup lookupName="question" lookupKey="001"/><br>
				<c:forEach items="${inputForm.questionId}" var="questionId">
					<dm3lookup:lookup lookupName="ans_all" lookupKey="${questionId}"/>
					<c:if test="${questionId == '008' && inputForm.etcAnswer1 != null && inputForm.etcAnswer1.length() > 0}">
						<br>内容（<c:out value="${inputForm.etcAnswer1}"/>）
					</c:if>
					<c:if test="${questionId == '009' && inputForm.etcAnswer2 != null && inputForm.etcAnswer2.length() > 0}">
						<br>内容（<c:out value="${inputForm.etcAnswer2}"/>）
					</c:if>
					<c:if test="${questionId == '010' && inputForm.etcAnswer3 != null && inputForm.etcAnswer3.length() > 0}">
						<br>内容（<c:out value="${inputForm.etcAnswer3}"/>）
					</c:if>
					<br>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr">登録経路</th>
			<td colspan="3">
                <dm3lookup:lookupForEach lookupName="entryRoute">
                    <c:if test="${inputForm.entryRoute == key}"><c:out value="${value}"/></c:if>
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
			<th class="head_tr">有効区分</th>
			<td colspan="3">
                <dm3lookup:lookupForEach lookupName="lockFlg">
                    <c:if test="${inputForm.lockFlg == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>
			</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
	<input type="hidden" name="lastLogin" value="<c:out value="${inputForm.lastLogin}"/>">
	<input type="hidden" name="insDate" value="<c:out value="${inputForm.insDate}"/>">
	<input type="hidden" name="updDate" value="<c:out value="${inputForm.updDate}"/>">
	<input type="hidden" name="promoCd" value="<c:out value="${inputForm.promoCd}"/>">
	<input type="hidden" name="refCd" value="<c:out value="${inputForm.refCd}"/>">
	<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">
	<input type="hidden" name="password" value="<c:out value="${inputForm.password}"/>">
	<input type="hidden" name="passwordChk" value="<c:out value="${inputForm.passwordChk}"/>">
	<input type="hidden" name="memberLname" value="<c:out value="${inputForm.memberLname}"/>">
	<input type="hidden" name="memberFname" value="<c:out value="${inputForm.memberFname}"/>">
	<input type="hidden" name="memberLnameKana" value="<c:out value="${inputForm.memberLnameKana}"/>">
	<input type="hidden" name="memberFnameKana" value="<c:out value="${inputForm.memberFnameKana}"/>">
	<input type="hidden" name="zip" value="<c:out value="${inputForm.zip}"/>">
	<input type="hidden" name="prefCd" value="<c:out value="${inputForm.prefCd}"/>">
	<input type="hidden" name="address" value="<c:out value="${inputForm.address}"/>">
	<input type="hidden" name="addressOther" value="<c:out value="${inputForm.addressOther}"/>">
	<input type="hidden" name="residentFlg" value="<c:out value="${inputForm.residentFlg}"/>">
	<input type="hidden" name="hopePrefCd" value="<c:out value="${inputForm.hopePrefCd}"/>">
	<input type="hidden" name="hopeAddress" value="<c:out value="${inputForm.hopeAddress}"/>">
	<input type="hidden" name="tel" value="<c:out value="${inputForm.tel}"/>">
	<input type="hidden" name="fax" value="<c:out value="${inputForm.fax}"/>">
	<input type="hidden" name="mailSendFlg" value="<c:out value="${inputForm.mailSendFlg}"/>">
	<c:forEach items="${inputForm.questionId}" var="selectedQuestionId">
		<input type="hidden" name="questionId" value="<c:out value="${selectedQuestionId}"/>">
	</c:forEach>
	<input type="hidden" name="etcAnswer1" value="<c:out value="${inputForm.etcAnswer1}"/>">
	<input type="hidden" name="etcAnswer2" value="<c:out value="${inputForm.etcAnswer2}"/>">
	<input type="hidden" name="etcAnswer3" value="<c:out value="${inputForm.etcAnswer3}"/>">
	<input type="hidden" name="entryRoute" value="<c:out value="${inputForm.entryRoute}"/>">
	<input type="hidden" name="lockFlg" value="<c:out value="${inputForm.lockFlg}"/>">
	<c:import url="/WEB-INF/jsp/admin/include/memberInfo/searchParams.jsh" />
	<dm3token:oneTimeToken/>
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
