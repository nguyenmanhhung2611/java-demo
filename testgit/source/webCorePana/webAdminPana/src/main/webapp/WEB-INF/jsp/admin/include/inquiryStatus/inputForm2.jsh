<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%--
お問合せステータス編集画面で使用する査定問合情報の出力
--%>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>査定問合情報</h2>
		</div>
	</div>
	<!--/headingAreaInner -->

	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col style="width: 15%"></col>
			<col></col>
			<col style="width: 15%"></col>
			<col></col>
			<col style="width: 15%"></col>
			<col></col>
		</colgroup>
		<tr>
			<th class="head_tr">売却物件の種別</th>
			<td colspan="5">
				<dm3lookup:lookupForEach lookupName="buy_housing_type">
					<c:if test="${inquiryAssessment.buyHousingType == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">建物面積</th>
			<td>
				<c:if test="${!empty inquiryAssessment.buildingArea}">
				<c:out value="${inquiryAssessment.buildingArea}"/>
				<c:if test="${inquiryAssessment.buildingAreaCrs == 00}"><c:out value="坪"/></c:if>
				<c:if test="${inquiryAssessment.buildingAreaCrs == 01}">m&sup2</c:if>
				<c:if test="${inquiryAssessment.buildingAreaCrs != 00 && inquiryAssessment.buildingAreaCrs != 01}"></c:if>
				(公簿)
				</c:if>
			</td>
			<th class="head_tr">土地面積</th>
			<td>
				<c:if test="${!empty inquiryAssessment.landArea}">
				<c:out value="${inquiryAssessment.landArea}"/>
				<c:if test="${inquiryAssessment.landAreaCrs == 00}"><c:out value="坪"/></c:if>
				<c:if test="${inquiryAssessment.landAreaCrs == 01}">m&sup2</c:if>
				<c:if test="${inquiryAssessment.landAreaCrs != 00 && inquiryAssessment.landAreaCrs != 01}"></c:if>
				(公簿)
				</c:if>
			</td>
			<th class="head_tr">専有面積</th>
			<td>
				<c:if test="${!empty inquiryAssessment.personalArea}">
					<c:out value="${inquiryAssessment.personalArea}"/>m&sup2(壁芯)
				</c:if>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">所在地</th>
			<td colspan="5"><c:out value="${assessmentAddress}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">築年</th>
			<td>
				<c:if test="${!empty inquiryAssessment.buildAge}">
				<c:out value="${inquiryAssessment.buildAge}"/>年
				</c:if>&nbsp;
			</td>
			<th class="head_tr">現況</th>
			<td>
				<dm3lookup:lookupForEach lookupName="inquiry_present_cd">
					<c:if test="${inquiryAssessment.presentCd == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
			<th class="head_tr">間取り</th>
			<td>
				<dm3lookup:lookupForEach lookupName="layoutCd">
					<c:if test="${inquiryAssessment.layoutCd == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">売却予定時期</th>
			<td colspan="2">
				<dm3lookup:lookupForEach lookupName="inquiry_buy_time_cd">
					<c:if test="${inquiryAssessment.buyTimeCd == key}"><c:out value="${value}"/></c:if>
				</dm3lookup:lookupForEach>&nbsp;
			</td>
			<th class="head_tr">買い替えの有無</th>
			<td colspan="2">
				<c:if test="${inquiryAssessment.replacementFlg == 0}">なし</c:if>
				<c:if test="${inquiryAssessment.replacementFlg == 1}">あり</c:if>&nbsp;
			</td>
		</tr>
	</table>

	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1" style="margin-top:2px;">
		<colgroup>
			<col style="width: 20%"></col>
			<col></col>
			<col style="width: 20%"></col>
			<col></col>
		</colgroup>
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
			<th class="head_tr">現住所</th>
			<td colspan="3"><c:out value="${headerAddress}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">電話番号</th>
			<td><c:out value="${inquiryHeader.tel}"/>&nbsp;</td>
			<th class="head_tr">FAX番号</th>
			<td><c:out value="${inquiryHeader.fax}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">ご希望の連絡方法</th>
			<td>
				<dm3lookup:lookupForEach lookupName="inquiry_contact_type">
					<c:if test="${inquiryAssessment.contactType == key}"><c:out value="${value}"/></c:if>
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
		<tr>
			<th class="head_tr">ご意見・ご要望</th>
			<td colspan="3">${dm3functions:crToHtmlTag(inquiryAssessment.requestText)}&nbsp;</td>
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
