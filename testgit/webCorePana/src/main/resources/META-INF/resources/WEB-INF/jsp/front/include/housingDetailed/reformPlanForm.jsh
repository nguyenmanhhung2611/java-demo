<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<c:if test="${outPutForm.isReformPlanDisplayFlg() == 'true'}">
				<div class="reformPlan">
					<h2 class="reformPlan">この物件のリフォームプラン例</h2>
					<table class="tableType2 SPdisplayNone">
						<thead>
							<tr>
								<th>リフォームプラン</th>
								<th>総額<span>※下段は内訳総額 </span></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="varPlanNoHidden" items="${outPutForm.getPlanNoHidden()}" end="2">
								<tr>
									<th><a class="<dm3lookup:lookup lookupName="iconPlanCategories" lookupKey="${outPutForm.getReformCategory()[varPlanNoHidden]}"/>" href="<c:if test="${outPutForm.isPreviewFlg() == 'false'}"><c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getReformUrl()[varPlanNoHidden]}#anc01"/></c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">javascript:void(0);</c:if>"><c:out value="${outPutForm.getPlanType()[varPlanNoHidden]}"/></a></th>
									<td><span class="buildingPrice"><c:out value="${outPutForm.getTotalPrice1()[varPlanNoHidden]}"/></span><br>
									<c:out value="${outPutForm.getTotalPrice2()[varPlanNoHidden]}"/></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<ul class="SPdisplayBlock">
						<c:forEach var="varPlanNoHidden" items="${outPutForm.getPlanNoHidden()}" end="2">
							<li><a href="<c:if test="${outPutForm.isPreviewFlg() == 'false'}"><c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getReformUrl()[varPlanNoHidden]}#anc01"/></c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">javascript:void(0);</c:if>">
							<span class="<dm3lookup:lookup lookupName="iconPlanCategories" lookupKey="${outPutForm.getReformCategory()[varPlanNoHidden]}"/>">
							     <c:out value="${outPutForm.getPlanType()[varPlanNoHidden]}"/>
							</span>
							<c:if test="${!empty outPutForm.getTotalPrice1()[varPlanNoHidden]}"><span>総額：<c:out value="${outPutForm.getTotalPrice1()[varPlanNoHidden]}"/></span></c:if>
							<c:set var="SPTotalPrice2Before" value="${fn:substringBefore(outPutForm.getTotalPrice2()[varPlanNoHidden], '＋')}"/>
							<c:set var="SPTotalPrice2After" value="${fn:substringAfter(outPutForm.getTotalPrice2()[varPlanNoHidden], '＋')}"/>
							<c:choose><c:when test="${!empty SPTotalPrice2Before && !empty SPTotalPrice2After}"><c:out value="${SPTotalPrice2Before}＋${SPTotalPrice2After}" escapeXml="false"/></c:when><c:otherwise><c:out value="${outPutForm.getTotalPrice2()[varPlanNoHidden]}"/></c:otherwise></c:choose></a></li>
						</c:forEach>
					</ul>
					<p>※リフォームプランは一例です。その他にも多彩なプランをご提案いたします。</p>
					<p>※リフォーム価格は概算です。</p>
				</div>
			</c:if>

			<c:if test="${outPutForm.isReformPlanReadyDisplayFlg() == 'true'}">

				<div class="reformPlan">
					<h2 class="reformPlan">この物件のリフォームプラン例</h2>
					<p class="note"><c:out value="${outPutForm.getReformPlanReadyComment()}" escapeXml="false"/></p>
				</div>
			</c:if>