<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

			<div class="reformPlan mb00">
				<c:if test="${'detail' == outPutForm.getMode()}">
					<h2 class="reformPlan">この物件のリフォームプラン例</h2>
				</c:if>
				<c:if test="${'reform' == outPutForm.getMode()}">
					<h2 class="reformPlan">その他のリフォームプラン例</h2>
				</c:if>
				<table class="tableType2 SPdisplayNone">
					<thead>
						<tr>
						    <th>&nbsp;</th>
							<th>リフォームプラン</th>
							<th>総額<span>※下段は内訳価格 </span></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="varOtherPlanNoHidden" items="${outPutForm.getOtherPlanNoHidden()}">
							<c:if test="${!empty outPutForm.getOtherPlanType()[varOtherPlanNoHidden]}">
								<tr>
								    <th><c:out value="${outPutForm.getReformCategory()[varOtherPlanNoHidden] }"/></th>
									<td><a href="<c:if test="${outPutForm.isPreviewFlg() == 'false'}"><c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getOtherReformUrl()[varOtherPlanNoHidden]}#anc01"/></c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">javascript:void(0);</c:if>"><c:out value="${outPutForm.getOtherPlanType()[varOtherPlanNoHidden]}"/></a></td>
									<td><span class="buildingPrice"><c:out value="${outPutForm.getOtherTotalPrice1()[varOtherPlanNoHidden]}"/></span><br>
									<c:out value="${outPutForm.getOtherTotalPrice2()[varOtherPlanNoHidden]}"/></td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<ul class="SPdisplayBlock">
						<c:forEach var="varPlanNoHidden" items="${outPutForm.getOtherPlanNoHidden()}">
							<li><a href="<c:if test="${outPutForm.isPreviewFlg() == 'false'}"><c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getOtherReformUrl()[varPlanNoHidden]}#anc01"/></c:if><c:if test="${outPutForm.isPreviewFlg() == 'true'}">javascript:void(0);</c:if>"><span><c:out value="${outPutForm.getOtherPlanType()[varPlanNoHidden]}"/><br>
							<c:if test="${!empty outPutForm.getOtherTotalPrice1()[varPlanNoHidden]}">総額：</c:if><c:out value="${outPutForm.getOtherTotalPrice1()[varPlanNoHidden]}"/></span><br>
							<c:set var="SPTotalPrice2Before" value="${fn:substringBefore(outPutForm.getOtherTotalPrice2()[varPlanNoHidden], '＋')}"/>
							<c:set var="SPTotalPrice2After" value="${fn:substringAfter(outPutForm.getOtherTotalPrice2()[varPlanNoHidden], '＋')}"/>
							<c:choose><c:when test="${!empty SPTotalPrice2Before && !empty SPTotalPrice2After}"><c:out value="${SPTotalPrice2Before}<br>＋${SPTotalPrice2After}" escapeXml="false"/></c:when><c:otherwise><c:out value="${outPutForm.getOtherTotalPrice2()[varPlanNoHidden]}"/></c:otherwise></c:choose></a></li>
						</c:forEach>
				</ul>
				<p>※リフォームプランは一例です。その他にも多彩なプランをご提案いたします。</p>
				<p>※リフォーム価格は概算です。</p>
			</div>