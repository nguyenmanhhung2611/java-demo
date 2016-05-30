<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<div class="buildingDetail parts-sp-accordion">
				<h2><span>物件詳細情報</span></h2>
				<div class="box-sp-accordion">
					<table class="tableType1">
						<tbody>
							<c:choose>
								<c:when test="${outPutForm.getHousingKindCd() == '01'}">
									<tr>
										<th>バルコニー</th>
										<td>
											<c:if test="${!empty outPutForm.getBalconyArea()}">
												<c:out value="${outPutForm.getBalconyArea()}"/>m&sup2;
											</c:if>
										</td>
										<th>主要採光</th>
										<td><c:out value="${outPutForm.getDirection()}"/></td>
									</tr>
									<tr>
										<th>管理形態</th>
										<td><c:out value="${outPutForm.getUpkeepType()}"/></td>
										<th>管理費等</th>
										<td><c:out value="${outPutForm.getUpkeep()}" escapeXml="false"/></td>
									</tr>
									<tr>
										<th>修繕積立金</th>
										<td><c:out value="${outPutForm.getMenteFee()}" escapeXml="false"/></td>
										<th>敷地権利</th>
										<td>
											<dm3lookup:lookup lookupName="landRight" lookupKey="${outPutForm.getLandRight()}"/>
										</td>
									</tr>
									<tr>
										<th>構造</th>
										<td><c:out value="${outPutForm.getStruct()}"/></td>
										<th>総戸数</th>
										<td><c:out value="${outPutForm.getTotalHouseCnt()}"/></td>
									</tr>
									<tr>
										<th>規模</th>
										<td>
											<dm3lookup:lookup lookupName="scaleDataValue" lookupKey="${outPutForm.getScale()}"/>
										</td>
										<th>用途地域</th>
										<td>
											<dm3lookup:lookup lookupName="usedAreaCd" lookupKey="${outPutForm.getUsedAreaCd()}"/>
										</td>
									</tr>
									<tr>
										<th>現況</th>
										<td><c:out value="${outPutForm.getStatus()}"/></td>
										<th>引渡し</th>
										<td>
											<dm3lookup:lookup lookupName="moveinTiming" lookupKey="${outPutForm.getMoveinTiming()}"/>
											<c:if test="${!empty outPutForm.getMoveinNote()}"> &frasl; </c:if>
											<c:out value="${outPutForm.getMoveinNote()}"/>
										</td>
									</tr>
									<tr>
										<th>取引形態</th>
										<td>
											<dm3lookup:lookup lookupName="transactTypeDiv" lookupKey="${outPutForm.getTransactTypeDiv()}"/>
										</td>
										<th>駐車場</th>
										<td><c:out value="${outPutForm.getParkingSituation()}"/></td>
									</tr>
									<tr>
										<th>インフラ</th>
										<td><c:out value="${outPutForm.getInfrastructure()}"/></td>
										<th>特記事項</th>
										<td><c:out value="${outPutForm.getSpecialInstruction()}"/></td>
									</tr>
									<tr>
										<th>備考</th>
										<td><c:out value="${outPutForm.getUpkeepCorp()}"/></td>
										<th>更新日</th>
										<td><c:out value="${outPutForm.getUpdDate()}"/><br>
										<c:out value="${outPutForm.getNextUpdDate()}"/></td>
									</tr>
								</c:when>

								<c:otherwise>
									<tr>
										<th>私道負担</th>
										<td><c:out value="${outPutForm.getPrivateRoad()}"/></td>
										<th>土地権利</th>
										<td>
											<dm3lookup:lookup lookupName="landRight" lookupKey="${outPutForm.getLandRight()}"/>
										</td>
									</tr>
									<tr>
										<th>構造</th>
										<td><c:out value="${outPutForm.getStruct()}"/></td>
										<th>用途地域</th>
										<td>
											<dm3lookup:lookup lookupName="usedAreaCd" lookupKey="${outPutForm.getUsedAreaCd()}"/>
										</td>
									</tr>
									<tr>
										<th>建ぺい率</th>
										<td><c:out value="${outPutForm.getCoverage()}"/></td>
										<th>容積率</th>
										<td><c:out value="${outPutForm.getBuildingRate()}"/></td>
									</tr>
									<tr>
										<th>現況</th>
										<td><c:out value="${outPutForm.getStatus()}"/></td>
										<th>引渡し</th>
										<td>
											<dm3lookup:lookup lookupName="moveinTiming" lookupKey="${outPutForm.getMoveinTiming()}"/>
											<c:if test="${!empty outPutForm.getMoveinNote()}"> &frasl; </c:if>
											<c:out value="${outPutForm.getMoveinNote()}"/>
										</td>
									</tr>
									<tr>
										<th>取引形態</th>
										<td>
											<dm3lookup:lookup lookupName="transactTypeDiv" lookupKey="${outPutForm.getTransactTypeDiv()}"/>
										</td>
										<th>駐車場</th>
										<td><c:out value="${outPutForm.getParkingSituation()}"/></td>
									</tr>
									<tr>
										<th>接道</th>
										<td>
											<c:out value="${outPutForm.getContactRoad()}"/>
											<c:if test="${!empty outPutForm.getContactRoadDir()}"> &frasl; </c:if>
											<c:out value="${outPutForm.getContactRoadDir()}"/>
										</td>
										<th>瑕疵保険</th>
										<td>
											<dm3lookup:lookup lookupName="insurExist" lookupKey="${outPutForm.getInsurExist()}"/>
										</td>
									</tr>
									<tr>
										<th>インフラ</th>
										<td><c:out value="${outPutForm.getInfrastructure()}"/></td>
										<th>特記事項</th>
										<td><c:out value="${outPutForm.getSpecialInstruction()}"/></td>
									</tr>
									<tr>
										<th>備考</th>
										<td><c:out value="${outPutForm.getUpkeepCorp()}"/></td>
										<th>更新日</th>
										<td><c:out value="${outPutForm.getUpdDate()}"/><br>
										<c:out value="${outPutForm.getNextUpdDate()}"/></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>