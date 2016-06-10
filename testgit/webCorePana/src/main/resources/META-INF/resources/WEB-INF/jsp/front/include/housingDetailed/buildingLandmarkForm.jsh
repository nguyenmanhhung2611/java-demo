<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<div class="localInfo parts-sp-accordion">
				<h2><span>地域情報</span></h2>
				<div class="box-sp-accordion">
					<table class="tableType1">
						<tbody>
							<tr>
								<th>周辺施設</th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
                                       <c:set var="landmarkType" value="${codeLookupManager.lookupValue('buildingLandmark_landmarkType', outPutForm.getLandmarkType()[varLandmarkNoHidden])}"/>
                                       <c:if test="${!empty landmarkType && !empty outPutForm.getLandmarkName()[varLandmarkNoHidden]}">
                                           <p>
                                               <c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] !='008'}">
                                                    <c:out value="${landmarkType}" /> 
                                                </c:if>
                                                <c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/>
                                                <c:if test="${!empty outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}">：<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
                                               </c:if>
                                           </p>
                                       </c:if>
									</c:forEach>
								</td>	
							</tr>
							
						</tbody>
					</table>
				</div>
			</div>