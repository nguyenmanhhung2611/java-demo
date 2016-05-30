<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<div class="utilityIcon">
				<ul class="clearfix SPdisplayNone">
					<c:forEach varStatus="Status" items="${outPutForm.getIconCd()}">
						<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>modal/<dm3lookup:lookup lookupName="recommend_point_html" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>" data-fancybox-type="iframe" class="recicon"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/<dm3lookup:lookup lookupName="recommend_point_icon_img_SPdisplayNone" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>" alt="<dm3lookup:lookup lookupName="recommend_point_icon" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>"></a></li>
					</c:forEach>
				</ul>
				<ul class="clearfix SPdisplayBlock">
					<c:forEach varStatus="Status" items="${outPutForm.getIconCd()}">
						<li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>modal/<dm3lookup:lookup lookupName="recommend_point_html" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>" data-fancybox-type="iframe" class="recicon"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/<dm3lookup:lookup lookupName="recommend_point_icon_img_SPdisplayBlock" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>" alt="<dm3lookup:lookup lookupName="recommend_point_icon" lookupKey="${outPutForm.getIconCd()[Status.index]}"/>"></a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="buildingSummary">
				<table class="tableType1" >
					<tbody>
						<tr>
							<th><p class="buildingPrice">物件価格</p></th>
							<td><p class="buildingPrice"><c:out value="${outPutForm.getPrice()}"/></p><p class="btnBlack01"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/loan-simulation/" target="_blank">ローンシミュレーション</a></p></td>
							<th>所在地</th>
							<c:set var="address" value="${fn:trim(outPutForm.getAddress())}"/>
							<td><c:out value="${outPutForm.getAddress()}"/> <c:if test="${!empty address}">（<span>周辺地図</span>）</c:if></td>
						</tr>
						<tr>
							<th>アクセス</th>
							<td>
								<c:forEach var="varAccess" items="${outPutForm.getAccess()}">
									<c:out value="${varAccess}"/><br>
								</c:forEach>
							</td>
								<c:if test="${outPutForm.isPersonalAreaFlg() == 'true'}">
									<th>間取り<br>
										専有面積</th>
									<td>
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${outPutForm.getLayoutCd()}"/>
										<c:if test="${!empty outPutForm.getLayoutCd() || !empty outPutForm.getPersonalArea() || !empty outPutForm.getPersonalAreaMemo()}">　&frasl;　</c:if>
										<c:if test="${!empty outPutForm.getPersonalArea()}"><c:out value="${outPutForm.getPersonalArea()}"/>m&sup2;</c:if>
										<c:out value="${outPutForm.getPersonalAreaSquare()}"/>
										<c:out value="${outPutForm.getPersonalAreaMemo()}"/>
									</td>
								</c:if>

								<c:if test="${outPutForm.isBuildingAreaFlg() == 'true'}">
									<th>間取り<br>
										建物面積<br>
										土地面積</th>
									<td>
										<c:if test="${!empty outPutForm.getLayoutCd() || !empty outPutForm.getBuildingArea() || !empty outPutForm.getBuildingAreaMemo() || !empty outPutForm.getLandArea() || !empty outPutForm.getLandAreaMemo()}" var="frasl"></c:if>
										<dm3lookup:lookup lookupName="layoutCd" lookupKey="${outPutForm.getLayoutCd()}"/>
										<c:if test="${frasl}" >　&frasl;　</c:if>
										<c:if test="${!empty outPutForm.getBuildingArea()}"><c:out value="${outPutForm.getBuildingArea()}"/>m&sup2;</c:if>
										<c:out value="${outPutForm.getBuildingAreaSquare()}"/><c:out value="${outPutForm.getBuildingAreaMemo()}"/>
										<c:if test="${frasl}" >　&frasl;　</c:if>
										<c:if test="${!empty outPutForm.getLandArea()}"><c:out value="${outPutForm.getLandArea()}"/>m&sup2;</c:if>
										<c:out value="${outPutForm.getLandAreaSquare()}"/><c:out value="${outPutForm.getLandAreaMemo()}"/>
									</td>
								</c:if>
						</tr>
						<tr>
							<th>築年月</th>
							<td><c:out value="${outPutForm.getCompDate()}"/></td>
							<c:if test="${outPutForm.isFloorFlg() == 'true'}">
								<th>階建 &frasl; 所在階</th>
								<td><c:out value="${outPutForm.getFloor()}" escapeXml="false"/></td>
							</c:if>
						</tr>
					</tbody>
				</table>
			</div>