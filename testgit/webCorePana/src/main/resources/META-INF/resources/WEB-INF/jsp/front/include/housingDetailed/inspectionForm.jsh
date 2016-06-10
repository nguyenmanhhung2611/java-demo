<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

				<div class="diagnosisInfo clearfix">
					<div class="title clearfix">
						<h2>住宅診断情報</h2>
						<p><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/re2/detail/inspection.html" target="_blank">住宅診断とは</a></p>
					</div>
					<div class="blockLeft">
						<dl>
							<dt><span>住宅診断実施状況</span></dt>
							<dd><dm3lookup:lookup lookupName="housingInspectionFront" lookupKey="${outPutForm.getInspectionExist()}"/></dd>
						</dl>
						<div class="h3Block clearfix">
							<h3>住宅診断結果チャート</h3>
							<p><a class="tooltip01" title="住宅診断項目のうち、指摘があった割合を&lt;br&gt;0～5段階で表示しています。&lt;br&gt;5：指摘事項なし&lt;br&gt;4：指摘事項が25％未満&lt;br&gt;3：指摘事項が25％以上、50％未満&lt;br&gt;2：指摘事項が50％以上、75％未満&lt;br&gt;1：指摘事項が75％以上、100％未満&lt;br&gt;0：全項目に指摘事項あり">評価基準（5～0）</a></p>
						</div>
						<p><img src="<c:if test="${!empty outPutForm.getInspectionImagePathName()}"><c:out value="${outPutForm.getInspectionImagePathName()}"/></c:if><c:if test="${empty outPutForm.getInspectionImagePathName()}"><c:out value="${commonParameters.commonResourceRootUrl}${commonParameters.noPhoto200}"/></c:if>" alt="住宅診断情報"></p>
					</div>

					<div class="blockRight">
						<c:if test="${!empty outPutForm.getInspectionPathName()}">
							<p class="btnBlack01"><a href="<c:out value="${outPutForm.getInspectionPathName()}"/>" target="_blank">住宅診断結果の詳細はこちら (PDF)</a></p>
						</c:if>
						<c:if test="${empty outPutForm.getInspectionPathName()}">
							<p class="btnBlack01"></p><br>
							<p class="btnBlack01"></p><br>
							<p class="btnBlack01"></p>
						</c:if>
						<div class="h3Block clearfix">
							<h3>診断箇所と確認範囲</h3>
							<p><a class="tooltip01" title="確認範囲&lt;br&gt;S　全て、またはほとんど確認できた&lt;br&gt;A　過半部分が確認できた&lt;br&gt;B　過半部分が確認できなかった&lt;br&gt;C　ほとんど、または全く確認できなかった">確認範囲（S～C）</a></p>
						</div>
						<table class="detailList">
							<thead>
							<tr>
								<th colspan="2">診断箇所</th>
								<th class="w20per">確認<br class="SPdisplayBlock">範囲</th>
							</tr>
							</thead>
							<tbody>
							<c:choose>
								<c:when test="${outPutForm.getHousingKindCd() == '01'}">
									<c:set var="inspectionTrust" value="inspectionTrustMansion"/>
									<c:set var="inspectionTrustExplain" value="inspectionTrustMansionExplain"/>
								</c:when>
								<c:otherwise>
									<c:set var="inspectionTrust" value="inspectionTrustHouse"/>
									<c:set var="inspectionTrustExplain" value="inspectionTrustHouseExplain"/>
								</c:otherwise>
							</c:choose>
							<dm3lookup:lookupForEach lookupName="${inspectionTrust}">
								<c:forEach var="varInspectionNoHidden" items="${outPutForm.getInspectionNoHidden()}">
									<c:if test="${outPutForm.getInspectionKey()[varInspectionNoHidden] == key}">
										<tr>
											<c:set var="valueSPdisplayBlockBefore" value="${fn:substringBefore(value, '・')}"/>
											<c:set var="valueSPdisplayBlockAfter" value="${fn:substringAfter(value, '・')}"/>
											<th><c:choose><c:when test="${!empty valueSPdisplayBlockBefore && !empty valueSPdisplayBlockAfter}"><c:out value="${valueSPdisplayBlockBefore}"/><br class="SPdisplayBlock">・<c:out value="${valueSPdisplayBlockAfter}"/></c:when><c:otherwise><c:out value="${value}"/></c:otherwise></c:choose></th>
											<td><dm3lookup:lookup lookupName="${inspectionTrustExplain}" lookupKey="${outPutForm.getInspectionKey()[varInspectionNoHidden]}"/></td>
											<td><p class="rank<dm3lookup:lookup lookupName="inspectionLabel" lookupKey="${outPutForm.getInspectionTrust()[varInspectionNoHidden]}"/>"><dm3lookup:lookup lookupName="inspectionLabel" lookupKey="${outPutForm.getInspectionTrust()[varInspectionNoHidden]}"/></p></td>
										</tr>
									</c:if>
								</c:forEach>
							</dm3lookup:lookupForEach>
						</table>
					</div>
				</div>