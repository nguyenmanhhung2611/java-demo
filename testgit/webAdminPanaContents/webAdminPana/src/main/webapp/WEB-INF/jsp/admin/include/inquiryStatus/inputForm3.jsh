<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<%--
お問合せステータス編集画面で使用する汎用問合情報の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">

		<!--headingAreaInner -->
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>汎用問合情報</h2>
			</div>
		</div>
		<!--/headingAreaInner -->

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col style="width: 20%"></col>
			<col></col>
			<col style="width: 20%"></col>
			<col></col>
		</colgroup>
		<tr>
			<th class="head_tr">お問い合わせ種別</th>
			<td colspan="3">
				<dm3lookup:lookupForEach lookupName="inquiry_dtl_type">
					<c:if test="${inquiryDtlInfo.inquiryDtlType == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">セミナー・イベント名</th>
			<td><c:out value="${inquiryGeneral.eventName}"/>&nbsp;</td>
			<th class="head_tr">日時</th>
			<td><c:out value="${fmtDate}"/>&nbsp;</td>
		</tr>
		<tr rowspan="3">
			<th class="head_tr">お問い合せ内容</th>
			<td colspan="3">${dm3functions:crToHtmlTag(inquiryHeader.inquiryText)}&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">メールアドレス</th>
			<td colspan="3"><c:out value="${inquiryHeader.email}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">お名前（姓）</th>
			<td><c:out value="${inquiryHeader.lname}"/>&nbsp;</td>
			<th class="head_tr">お名前（名）</th>
			<td><c:out value="${inquiryHeader.fname}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">フリガナ（セイ）</th>
			<td><c:out value="${inquiryHeader.lnameKana}"/>&nbsp;</td>
			<th class="head_tr">フリガナ（メイ）</th>
			<td><c:out value="${inquiryHeader.fnameKana}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">電話番号</th>
			<td colspan="3"><c:out value="${inquiryHeader.tel}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ご希望の連絡方法</th>
			<td>
				<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
					<c:if test="${inquiryGeneral.contactType == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
			<th class="head_tr">連絡可能な時間帯</th>
			<td>
				<c:forEach items="${contactTimes}" var="contactTime" varStatus="status">
					<dm3lookup:lookupForEach lookupName="inquiry_contact_time">
						<c:if test="${contactTime == key}"><c:out value="${value}"/>&nbsp;<br></c:if>
					</dm3lookup:lookupForEach>
				</c:forEach>
			</td>
		</tr>
		<tr rowspan="7">
			<th class="head_tr">アンケート</th>
			<td colspan="3">
				<dm3lookup:lookup lookupName="question" lookupKey="001"/><br><br>
				<c:forEach items="${inquiryHousingQuestion}" var="inq">
					<dm3lookup:lookupForEach lookupName="ans_all">
						<c:choose>
							<c:when test="${inq.ansCd == key}">
								<c:out value="${value}"/>
							</c:when>
						</c:choose>
					</dm3lookup:lookupForEach>
					<br>
					<c:if test="${!empty inq.note}">
						内容&nbsp;（<c:out value="${inq.note}"/>）<br>
					</c:if>
				</c:forEach>
			</td>
		</tr>
		</table>
</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	//$(document).ready(function(){
		//$(window).on("beforeunload", function(){
		//window.onbeforeunload = function(event){
			//return "123";
		//};

		//});
	//});
// -->
</script>
