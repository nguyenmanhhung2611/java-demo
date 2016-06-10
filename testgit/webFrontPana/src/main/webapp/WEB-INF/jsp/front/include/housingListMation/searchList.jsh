<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<c:forEach  var="buildingResults" items="${buildingList}" varStatus="EditItem">
	<div class="searchResult">
			<div class="buildingName">
				<ul class="buildingType clearfix">

					<c:if test="${dateList.get(EditItem.index) == '1'}">
						<li class="icoNew01">新着</li>
					</c:if>
					<dm3lookup:lookupForEach lookupName="hosuing_kind">
						<c:if test="${housingKindCd == key}">
							<li class="icoMansion01"><c:out value="${value}"/></li>
						</c:if>
					</dm3lookup:lookupForEach>
					<c:if test="${housingKindCd != '03' && reformPlanList.get(EditItem.index) != null}">
					   <li class="icoReform01">リフォームプランあり</li>
					</c:if>

				</ul>
				<div class="buildingFeatures clearfix">

					<p class="caption"><c:out value="${housingDtlList.get(EditItem.index).getDtlComment()}"  escapeXml="false"/></p>
					<p class="number">物件番号:<c:out value="${housingList.get(EditItem.index).getHousingCd()}"/></p>

				</div>

				<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
					<c:set var="housingKindName" value="mansion" />
				</c:if>
				<c:if test="${buildingList.get(EditItem.index).housingKindCd == '02'}">
					<c:set var="housingKindName" value="house" />
				</c:if>
				<c:if test="${buildingList.get(EditItem.index).housingKindCd == '03'}">
					<c:set var="housingKindName" value="ground" />
				</c:if>
				<h2><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><c:out value="${housingList.get(EditItem.index).getDisplayHousingName()}"/></a></h2>

			</div>

			<div class="utilityIcon">
				<ul class="clearfix">
					<c:forEach varStatus="Status" items="${iconInfoList.get(EditItem.index)}">
							<li><a href="<c:out value="${commonParameters.resourceRootUrl}"/>modal/<dm3lookup:lookup lookupName="recommend_point_html" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>" data-fancybox-type="iframe" class="recicon"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>buy/img/<dm3lookup:lookup lookupName="recommend_point_icon_img_SPdisplayNone" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>" alt="<dm3lookup:lookup lookupName="recommend_point_icon" lookupKey="${iconInfoList.get(EditItem.index)[Status.index]}"/>"></a></li>
					</c:forEach>
				</ul>
			</div>

			<div class="buildingPhoto">
					<c:if test="${searchHousingImgList.get(EditItem.index).get(0) != null}">
							<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><img src="<c:out value="${searchHousingImgList.get(EditItem.index).get(0)}"/>" alt=""></a></p>
					</c:if>
					<c:if test="${searchHousingImgList.get(EditItem.index).get(0) == null}">
						<p><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/"><img src="<c:out value='${commonParameters.resourceRootUrl}'/>${commonParameters.getNoPhoto200()}" alt=""></a></p>
					</c:if>
			</div>

			<div class="buildingSummary">
				<table class="tableType1">
					<tbody>
						<tr>
							<th>物件価格</th>
							<td><p class="buildingPrice">
							<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
								<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
								<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円</p>
							</c:if>
						</tr>
						<tr>
							<th>所在地</th>
							<td><c:out value="${prefName}"/>
							    <c:out value="${buildingList.get(EditItem.index).getAddressName()}"/>
							    <c:out value="${buildingList.get(EditItem.index).getAddressOther1()}"/>
							    <c:out value="${buildingList.get(EditItem.index).getAddressOther2()}"/>
							</td>
						</tr>
						<tr>
							<th>アクセス</th>
							<td>
							<c:forEach  var="buildingStationInfo" items="${buildingStationInfoList.get(EditItem.index)}" varStatus="buildingStationInfoItem">

								<c:if test="${rrMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRrName() != null}">
									<c:out value="${rrMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRrName()}"/>
								</c:if>

								<c:if test="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName() == null}">
									<c:out value="${buildingStationInfo.getDefaultRouteName()}"/>
								</c:if>
								<c:if test="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName() != null}">
									<c:out value="${routeMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getRouteName()}"/>
								</c:if>
								<c:if test="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName() == null}">
									<c:out value="${buildingStationInfo.getStationName()}"/>駅
								</c:if>
								<c:if test="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName() != null}">
									<c:out value="${stationMstInfoList.get(EditItem.index).get(buildingStationInfoItem.index).getStationName()}"/>駅
								</c:if>
								<c:if test="${!empty buildingStationInfo.getBusCompany()}">
									<c:out value="${buildingStationInfo.getBusCompany()}"/>
								</c:if>
								<c:if test="${!empty buildingStationInfo.getTimeFromBusStop()}">
									徒歩<c:out value="${buildingStationInfo.getTimeFromBusStop()}"/>分
								</c:if>
								 <br>
							</c:forEach>
							</td>
						</tr>
						<tr>
							<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
								<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span>専有面積</th>
								<td>
									<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd() || !empty housingList.get(EditItem.index).getPersonalArea()}">
										<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd()}">
											<span>
												<dm3lookup:lookupForEach lookupName="layoutCd">
													<c:if test="${housingList.get(EditItem.index).getLayoutCd() == key}">
														<c:out value="${value}"/>
													</c:if>
												</dm3lookup:lookupForEach>
											</span>
										</c:if>
										<c:if test="${empty housingList.get(EditItem.index).getLayoutCd()}">
											<span>
												&nbsp;
											</span>
										</c:if>
										<span class="SPdisplayNone">　&frasl;　</span>
										<c:if test="${!empty housingList.get(EditItem.index).getPersonalArea()}">
											<c:out value="${housingList.get(EditItem.index).getPersonalArea()}"/>m&sup2;（約<c:out value="${personalTubosuuList.get(EditItem.index)}"/>坪）
										</c:if>
										<c:if test="${empty housingList.get(EditItem.index).getPersonalArea()}">
											&nbsp;
										</c:if>
										<c:out value="${housingList.get(EditItem.index).getPersonalAreaMemo()}"/>
									</c:if>
								</td>
							</c:if>
							<c:if test="${buildingList.get(EditItem.index).housingKindCd != '01'}">
								<th><span>間取り</span><span class="SPdisplayNone"> &frasl; </span>建物面積<span class="SPdisplayNone"> &frasl; </span>土地面積</th>
								<td>
									<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd() || !empty buildingDtlList.get(EditItem.index).buildingArea || !empty housingList.get(EditItem.index).landArea || !empty buildingDtlList.get(EditItem.index).getBuildingAreaMemo() || !empty housingList.get(EditItem.index).getLandAreaMemo()}">
										<c:if test="${!empty housingList.get(EditItem.index).getLayoutCd()}">
											<span>
												<dm3lookup:lookupForEach lookupName="layoutCd">
													<c:if test="${housingList.get(EditItem.index).getLayoutCd() == key}">
														<c:out value="${value}"/>
													</c:if>
												</dm3lookup:lookupForEach>
											</span>
										</c:if>
										<c:if test="${empty housingList.get(EditItem.index).getLayoutCd()}">
											<span>
												&nbsp;
											</span>
										</c:if>
										<span class="SPdisplayNone"> &frasl; </span>
										<c:if test="${!empty buildingDtlList.get(EditItem.index).buildingArea}">
											<c:out
												value="${buildingDtlList.get(EditItem.index).buildingArea}" />m&sup2;（約<c:out
												value="${houseAreaList.get(EditItem.index)}" />坪）
										</c:if>
										<c:if test="${empty buildingDtlList.get(EditItem.index).buildingArea}">
											&nbsp;
										</c:if>
											<c:out
												value="${buildingDtlList.get(EditItem.index).getBuildingAreaMemo()}" />

										<span class="SPdisplayNone"> &frasl; </span>
										<c:if test="${!empty housingList.get(EditItem.index).landArea}">
											<c:out
												value="${housingList.get(EditItem.index).landArea}" />m&sup2;（約<c:out
												value="${landAreaList.get(EditItem.index)}" />坪）
										</c:if>
										<c:if test="${empty housingList.get(EditItem.index).landArea}">
											&nbsp;
										</c:if>
											<c:out
												value="${housingList.get(EditItem.index).getLandAreaMemo()}" />

									</c:if>
								</td>
							</c:if>
						</tr>
						<tr>
							<th>築年月</th>
							<td>
								<c:if test="${!empty compDateList.get(EditItem.index)}">
									<c:out value="${compDateList.get(EditItem.index).substring(0, 4)}"/>年<c:out value="${compDateList.get(EditItem.index).substring(4, 6)}"/>月築
								</c:if>
							</td>
						</tr>
<c:if test="${buildingList.get(EditItem.index).housingKindCd == '01'}">
						<tr>
							<th><span>階建</span><span class="SPdisplayNone"> &frasl; </span>所在階</th>
							<td>
								<c:if test="${!empty buildingList.get(EditItem.index).getTotalFloors() || !empty housingList.get(EditItem.index).getFloorNo()}">
									<c:if test="${!empty buildingList.get(EditItem.index).getTotalFloors()}">
										<span><c:out value="${buildingList.get(EditItem.index).getTotalFloors()}" />階建</span>
									</c:if>
									<c:if test="${empty buildingList.get(EditItem.index).getTotalFloors()}">
										<span>&nbsp;</span>
									</c:if>
									<span class="SPdisplayNone"> &frasl; </span>
									<c:if test="${!empty housingList.get(EditItem.index).getFloorNo()}">
										<c:out value="${housingList.get(EditItem.index).getFloorNo()}" />階
									</c:if>
									<c:if test="${empty housingList.get(EditItem.index).getFloorNo()}">
										&nbsp;
									</c:if>
								</c:if>
							</td>
						</tr>
</c:if>
					</tbody>
				</table>
			</div>
			<c:if test="${!empty housingList.get(EditItem.index).getBasicComment()}">
				<p class="comment">＜担当者からのおすすめ＞<c:out value="${housingList.get(EditItem.index).getBasicComment()}"  escapeXml="false"/></p>
			</c:if>
			<div class="contactBlock">
				<p class="btnBlack01"><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/">詳細はこちら</a></p>
				<c:if test="${loginFlg == '0'}">
					<p class="btnOrange02"><a href="<c:out value="${pageContext.request.contextPath}"/>/modal/favorite/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/" data-fancybox-type="iframe" class="favadd">お気に入り登録</a></p>
				</c:if>
				<c:if test="${housingKindCd != '03'}">
					<c:if test="${loginFlg != '0'}">
						<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この物件に関するお問い合わせ</a></p>
					</c:if>
					<c:if test="${loginFlg == '0'}">
						<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この物件に関するお問い合わせ</a></p>
					</c:if>
				</c:if>
				<c:if test="${housingKindCd == '03'}">
					<c:if test="${loginFlg != '0'}">
						<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/division/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この土地に関するお問い合わせ</a></p>
					</c:if>
					<c:if test="${loginFlg == '0'}">
						<p class="btnOrange01"><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/buy/inquiry/input/', '<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>');">この土地に関するお問い合わせ</a></p>
					</c:if>
				</c:if>

			</div>

<c:if test="${housingKindCd != '03'}">

			<div class="reformPlan">
				<c:if test="${reformPlanList.get(EditItem.index) == null}">
					<c:if test="${!empty housingList.get(EditItem.index).getReformComment()}">
						<h3 class="titleReform">この物件のリフォームプラン例</h3>
						<p class="note"><c:out value="${housingList.get(EditItem.index).getReformComment()}" escapeXml="false"/></p>
					</c:if>
				</c:if>

				<c:if test="${reformPlanList.get(EditItem.index) != null}">
					<h3 class="titleReform">この物件のリフォームプラン例</h3>
					<table class="tableType2 SPdisplayNone">
						<thead>
							<tr>
								<th>リフォームプラン</th>
								<th>総額<span>※下段は内訳総額 </span></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach  var="reformPlanList" items="${reformPlanList.get(EditItem.index)}" varStatus="reformPlanListItem" end="2">
							<tr>
								<c:if test="${housingListMationForm.getReformPriceCheck() == 'on'}">
									<c:if test="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice() > housingListMationForm.getKeyPriceUpper()}">
										<c:set var="priceFlag" value="disable"/>
									</c:if>

									<c:if test="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice() < housingListMationForm.getKeyPriceLower()}">
										<c:set var="priceFlag" value="disable"/>
									</c:if>
								</c:if>

								<c:if test="${housingListMationForm.getReformPriceCheck() != 'on'}">
									<c:if test="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice() <= housingListMationForm.getKeyPriceUpper()}">
										<c:if test="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice() >= housingListMationForm.getKeyPriceLower()}">
										<c:set var="priceFlag" value=""/>
										</c:if>
									</c:if>
								</c:if>

									<th class="${priceFlag}">
									   <a class="<dm3lookup:lookup lookupName="iconPlanCategories" lookupKey="${reformPlanList.getPlanCategory1()}"/>" target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/<c:out value="${reformPlanList.getSysReformCd()}"/>/#anc01"><c:out value="${reformPlanList.getPlanName()}"/></a>
									</th>
									<td class="${priceFlag}">
										<c:if test="${!empty housingList.get(EditItem.index).getPrice() || !empty reformPlanList.getPlanPrice()}">
											<span class="buildingPrice">
												<c:set var="totalPrice" value="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice()}"/>
												<c:set var="price" value="${totalPrice / 10000}"/>
												約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
											</span><br>
										</c:if>

										<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
														物件価格：
											<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
											<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
										</c:if>
										<c:if test="${!empty housingList.get(EditItem.index).getPrice() && !empty reformPlanList.getPlanPrice()}">
										＋
										</c:if>
										<c:if test="${!empty reformPlanList.getPlanPrice()}">
										リフォーム価格：
											<c:set var="price" value="${reformPlanList.getPlanPrice() / 10000}"/>
											約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
										</c:if>
									</td>
								</tr>

							</c:forEach>

						</tbody>
					</table>

					<ul class="SPdisplayBlock">
						<c:forEach  var="reformPlanList" items="${reformPlanList.get(EditItem.index)}" varStatus="reformPlanListItem" end="2">
							<li><a target="_blank" href="<c:out value="${pageContext.request.contextPath}"/>/buy/<c:out value="${housingKindName}"/>/<c:out value="${buildingList.get(EditItem.index).prefCd}"/>/detail/<c:out value="${housingList.get(EditItem.index).getSysHousingCd()}"/>/<c:out value="${reformPlanList.getSysReformCd()}"/>/#anc01"><span><span class="<dm3lookup:lookup lookupName="iconPlanCategories" lookupKey="${reformPlanList.getPlanCategory1()}" />"><c:out value="${reformPlanList.getPlanName()}"/></span>
							<c:set var="totalPrice" value="${housingList.get(EditItem.index).getPrice() + reformPlanList.getPlanPrice()}"/>
							<c:set var="price" value="${totalPrice / 10000}"/>
							総額：約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円</span>
							<c:if test="${!empty housingList.get(EditItem.index).getPrice()}">
							物件：
								<c:set var="price" value="${housingList.get(EditItem.index).getPrice() / 10000}"/>
								<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
							</c:if>

							<c:if test="${!empty housingList.get(EditItem.index).getPrice() && !empty reformPlanList.getPlanPrice()}">
							＋
							</c:if>
							<c:if test="${!empty reformPlanList.getPlanPrice()}">
							リフォーム価格：
								<c:set var="price" value="${reformPlanList.getPlanPrice() / 10000}"/>
								約<fmt:formatNumber value="${price + (1 - (price%1))%1}" pattern="###,###" />万円
							</c:if>
							</a></li>
						</c:forEach>
					</ul>

					<p class="comment">※リフォームプランは一例です。その他にも多彩なプランをご提案します。<br>
						※リフォーム価格は概算です。</p>
				</c:if>
			</div>
</c:if>
	</div>
</c:forEach>


