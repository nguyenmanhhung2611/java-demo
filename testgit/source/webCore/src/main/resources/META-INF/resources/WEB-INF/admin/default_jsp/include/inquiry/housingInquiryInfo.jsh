<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%-- お問合せメンテナンス機能で使用する入力確認画面の出力 --%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>物件問合情報</h2>
		</div>
	</div>
	<!--/headingAreaInner -->
	
	<table class="confirmBox">
		<c:set var="housing" value="${housingInfo.items['housingInfo']}"/>
		<tr>
			<th class="head_tr">物件番号</th>
			<td colspan="5"><c:out value="${housing.housingCd}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">物件名称</th>
			<td colspan="5"><c:out value="${housing.displayHousingName}"/>&nbsp;</td>
		</tr>
	</table>
	<table class="confirmBox" style="margin-top:2px;">
		<c:set var="inquiryHeader" value="${inquiryHeaderInfo.inquiryHeader}"/>
		<c:set var="inquiryDtlInfos" value="${inquiryHeaderInfo.inquiryDtlInfos}"/>
		<tr>
			<th class="head_tr">お問い合わせ種別</th>
			<td colspan="3">
				<dm3lookup:lookupForEach lookupName="inquiry_dtlType">
					<c:if test="${inquiryDtlInfos[0].inquiryDtlType == key}">
						<c:out value="${value}"/>
					</c:if>
				</dm3lookup:lookupForEach>
			&nbsp;</td>
		</tr>
		<tr rowspan="3">
			<th class="head_tr">お問い合せ内容</th>
			<td colspan="3"><c:out value="${inquiryHeader.inquiryText}"/>&nbsp;</td>
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
// -->
</script>
