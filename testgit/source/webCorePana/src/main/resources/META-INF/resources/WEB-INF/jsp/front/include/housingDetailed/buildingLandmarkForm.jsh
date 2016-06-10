<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<div class="localInfo parts-sp-accordion">
				<h2><span>地域情報</span></h2>
				<div class="box-sp-accordion">
					<table class="tableType1">
						<tbody>
							<tr>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="001"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '001'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="006"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '006'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="002"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '002'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="007"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '007'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="003"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '003'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="004"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '004'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="005"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '005'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
								<th><dm3lookup:lookup lookupName="buildingLandmark_landmarkType" lookupKey="008"/></th>
								<td>
									<c:forEach var="varLandmarkNoHidden" items="${outPutForm.getLandmarkNoHidden()}">
										<c:if test="${outPutForm.getLandmarkType()[varLandmarkNoHidden] == '008'}">
											<c:out value="${outPutForm.getLandmarkName()[varLandmarkNoHidden]}"/><br>
											<c:out value="${outPutForm.getDistanceFromLandmark()[varLandmarkNoHidden]}"/>
										</c:if>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>