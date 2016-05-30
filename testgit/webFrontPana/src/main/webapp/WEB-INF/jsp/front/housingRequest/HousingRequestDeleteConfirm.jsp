<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>
物件リクエスト削除　確認｜<c:out value="${commonParameters.panaReSmail}"/>
</title>


<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/common.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/header_footer.css" rel="stylesheet">
<link href="<c:out value="${commonParameters.resourceRootUrl}"/>common/css/parts.css" rel="stylesheet">

<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/jquery.min.js"></script>
<script type="text/javascript" src="<c:out value="${commonParameters.resourceRootUrl}"/>common/js/main.js"></script>

<!--[if lte IE 9]><script src="/common/js/html5.js" type="text/javascript"></script>
<![endif]-->
<!--[if lt IE 9]><![endif]-->
<script type ="text/javascript">
function linkToUrl(url, command) {
	document.inputForm.action=url;
	document.inputForm.command.value=command;
	document.inputForm.submit();
}
</script>

</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<!--[if lte IE 9]><script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js" type="text/javascript"></script>
	<![endif]-->
<!--[if lt IE 9]><![endif]-->

<c:import url="/WEB-INF/jsp/front/include/common/header.jsh" />
<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner">
		<c:import url="../include/common/topicPath.jsh?pattern=REQ-01-09" />

		<div class="section01">
			<div class="headingBlockA01 clearfix">
				<h1 class="ttl">物件リクエスト</h1>
			</div><!-- /.headingBlockA01 -->
			<div class="contentsInner01">
					<p class="f14">この物件リクエストを削除しますか？<br>削除した場合、この条件の物件情報は通知されません。</p>
			</div>
			<form action="comfirm" method="post" name="inputForm">

			<input type="hidden" name="command" value=""/>
			<input type="hidden" name="housingRequestId" value="<c:out value="${housingRequestForm.getHousingRequestId()}"/>">

				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件種別</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<p class="contentsInner01 mb00">
							<input type="hidden" name="housingKindCd" value="<c:out value="${housingRequestForm.getHousingKindCd()}"/>"/>
							<dm3lookup:lookupForEach lookupName="hosuing_kind">
								<c:if test="${housingRequestForm.getHousingKindCd() == key}">
									<c:out value="${value}"/>
								</c:if>
							</dm3lookup:lookupForEach>
						</p>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">物件所在地</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>都道府県</th>
								<td><c:out value="${prefName}"/>
									<input type="hidden" name="prefCd" value="<c:out value="${housingRequestForm.getPrefCd()}"/>"/></td>
							</tr>
							<tr style="display: none">
								<th class="normal">※「市区町村」または<br class="SPdisplayNone">「沿線」の、<br class="SPdisplayBlock">どちらかで<br class="SPdisplayNone">検索してください。</th>
								<td>
									<ul class="listTypeB01">
										<li>市区町村　｜　指定なし</li>
										<li>沿線　｜　東京メトロ銀座線／渋谷</li>
									</ul>
								</td>
							</tr>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="itemBlockA01 spMb00">
					<div class="headingBlockD01 clearfix">
						<h2 class="ttl">基本条件</h2>
					</div><!-- /.headingBlockD01 -->
					<div class="columnInner">
						<table class="tableBlockA01">
							<tr>
								<th>予算</th>
								<td><dm3lookup:lookupForEach lookupName="price">
										<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
											<input type="hidden" name="priceLowerMansion" value="<c:out value="${housingRequestForm.getPriceLowerMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerMansion() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<input type="hidden" name="priceLowerHouse" value="<c:out value="${housingRequestForm.getPriceLowerHouse()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerHouse() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<input type="hidden" name="priceLowerLand" value="<c:out value="${housingRequestForm.getPriceLowerLand()}"/>"/>
											<c:if test="${housingRequestForm.getPriceLowerLand() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

									</dm3lookup:lookupForEach>

									<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
										<c:if test="${!empty housingRequestForm.getPriceLowerMansion() || !empty housingRequestForm.getPriceUpperMansion()}">
											　～　
										</c:if>
									</c:if>
									<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
										<c:if test="${!empty housingRequestForm.getPriceLowerHouse() || !empty housingRequestForm.getPriceUpperHouse()}">
											　～　
										</c:if>
									</c:if>
									<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
										<c:if test="${!empty housingRequestForm.getPriceLowerLand() || !empty housingRequestForm.getPriceUpperLand()}">
											　～　
										</c:if>
									</c:if>

									<dm3lookup:lookupForEach lookupName="price">
										<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
											<input type="hidden" name="priceUpperMansion" value="<c:out value="${housingRequestForm.getPriceUpperMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperMansion() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<input type="hidden" name="priceUpperHouse" value="<c:out value="${housingRequestForm.getPriceUpperHouse()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperHouse() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<input type="hidden" name="priceUpperLand" value="<c:out value="${housingRequestForm.getPriceUpperLand()}"/>"/>
											<c:if test="${housingRequestForm.getPriceUpperLand() == value}">
												<c:if test="${value != '10000'}">
													<fmt:formatNumber value="${value}" pattern="###,###" />万円
												</c:if>
												<c:if test="${value == '10000'}">
													<c:out value="1億円"/>
												</c:if>
											</c:if>
										</c:if>

									</dm3lookup:lookupForEach>

									<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
										<c:if test="${housingRequestForm.getReformCheckMansion() == '1'}">
											<input type="hidden" name="reformCheckMansion" id="reformCheckMansion" value="<c:out value="${housingRequestForm.getReformCheckMansion()}"/>"/>
											リフォーム価格込みで検索する
										</c:if>
									</c:if>

									<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
										<c:if test="${housingRequestForm.getReformCheckHouse() == '1'}">
											<input type="hidden" name="reformCheckHouse" id="reformCheckHouse" value="<c:out value="${housingRequestForm.getReformCheckHouse()}"/>"/>
											リフォーム価格込みで検索する
										</c:if>
									</c:if>
								</td>
							</tr>
							<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
								<tr>
									<th>専有面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<input type="hidden" name="personalAreaLowerMansion" value="<c:out value="${housingRequestForm.getPersonalAreaLowerMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPersonalAreaLowerMansion() == value}"><c:out value="${value}"/>m&sup2;</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${!empty housingRequestForm.getPersonalAreaLowerMansion() || !empty housingRequestForm.getPersonalAreaUpperMansion()}">
										　～　
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<input type="hidden" name="personalAreaUpperMansion" value="<c:out value="${housingRequestForm.getPersonalAreaUpperMansion()}"/>"/>
											<c:if test="${housingRequestForm.getPersonalAreaUpperMansion() == value}"><c:out value="${value}"/>m&sup2;</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
							</c:if>
							<c:if test="${housingRequestForm.getHousingKindCd() != '01'}">
								<tr>
									<th>建物面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="buildingAreaLowerHouse" value="<c:out value="${housingRequestForm.getBuildingAreaLowerHouse()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaLowerHouse() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="buildingAreaLowerLand" value="<c:out value="${housingRequestForm.getBuildingAreaLowerLand()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaLowerLand() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<c:if test="${!empty housingRequestForm.getBuildingAreaLowerHouse() || housingRequestForm.getBuildingAreaUpperHouse() }">
												　～　
											</c:if>
										</c:if>
										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<c:if test="${!empty housingRequestForm.getBuildingAreaLowerLand() || !empty housingRequestForm.getBuildingAreaUpperLand()}">
												　～　
											</c:if>
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="buildingAreaUpperHouse" value="<c:out value="${housingRequestForm.getBuildingAreaUpperHouse()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaUpperHouse() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="buildingAreaUpperLand" value="<c:out value="${housingRequestForm.getBuildingAreaUpperLand()}"/>"/>
												<c:if test="${housingRequestForm.getBuildingAreaUpperLand() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
								<tr>
									<th>土地面積</th>
									<td>
										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="landAreaLowerHouse" value="<c:out value="${housingRequestForm.getLandAreaLowerHouse()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaLowerHouse() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="landAreaLowerLand" value="<c:out value="${housingRequestForm.getLandAreaLowerLand()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaLowerLand() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

										<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
											<c:if test="${!empty housingRequestForm.getLandAreaLowerHouse() || !empty housingRequestForm.getLandAreaUpperHouse()}">
												　～　
											</c:if>
										</c:if>
										<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
											<c:if test="${!empty housingRequestForm.getLandAreaLowerLand() || !empty housingRequestForm.getLandAreaUpperLand()}">
												　～　
											</c:if>
										</c:if>

										<dm3lookup:lookupForEach lookupName="area">
											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="landAreaUpperHouse" value="<c:out value="${housingRequestForm.getLandAreaUpperHouse()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaUpperHouse() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
											<c:if test="${housingRequestForm.getHousingKindCd() == '03'}">
												<input type="hidden" name="landAreaUpperLand" value="<c:out value="${housingRequestForm.getLandAreaUpperLand()}"/>"/>
												<c:if test="${housingRequestForm.getLandAreaUpperLand() == value}"><c:out value="${value}"/>m&sup2;</c:if>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
							</c:if>

							<c:if test="${housingRequestForm.getHousingKindCd() != '03'}">
								<tr>
									<th>間取り</th>
									<td>
										<dm3lookup:lookupForEach lookupName="layoutCd">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<c:forEach items="${housingRequestForm.getLayoutCdMansion()}" varStatus="LayoutCd">
													<c:if test="${housingRequestForm.getLayoutCdMansion()[LayoutCd.index] == key}">
														<input type="hidden" name="layoutCdMansion" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"></c:out>
													</c:if>

						                        </c:forEach>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<c:forEach items="${housingRequestForm.getLayoutCdHouse()}" varStatus="LayoutCd">

													<c:if test="${housingRequestForm.getLayoutCdHouse()[LayoutCd.index] == key}">
														<input type="hidden" name="layoutCdHouse" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　">
													</c:out></c:if>
						                        </c:forEach>
											</c:if>
										</dm3lookup:lookupForEach>

									</td>
								</tr>
								<tr>
									<th>築年数</th>
									<td>
										<dm3lookup:lookupForEach lookupName="compDate">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<input type="hidden" name="compDateMansion" value="<c:out value="${housingRequestForm.getCompDateMansion()}"/>"/>
												<c:if test="${housingRequestForm.getCompDateMansion() == key}"><c:out value="${value}"></c:out></c:if>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<input type="hidden" name="compDateHouse" value="<c:out value="${housingRequestForm.getCompDateHouse()}"/>"/>
												<c:if test="${housingRequestForm.getCompDateHouse() == key}"><c:out value="${value}"></c:out></c:if>
											</c:if>
										</dm3lookup:lookupForEach>
									</td>
								</tr>
								<tr>
									<th>おすすめのポイント</th>
									<td>
										<dm3lookup:lookupForEach lookupName="recommend_point_icon_list">
											<c:if test="${housingRequestForm.getHousingKindCd() == '01'}">
												<c:forEach items="${housingRequestForm.getIconCdMansion()}" varStatus="iconCd">
													<c:if test="${housingRequestForm.getIconCdMansion()[iconCd.index] == key}">
														<input type="hidden" name="iconCdMansion" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"></c:out>
													</c:if>

						                        </c:forEach>
											</c:if>

											<c:if test="${housingRequestForm.getHousingKindCd() == '02'}">
												<c:forEach items="${housingRequestForm.getIconCdHouse()}" varStatus="iconCd">
													<c:if test="${housingRequestForm.getIconCdHouse()[iconCd.index] == key}">
														<input type="hidden" name="iconCdHouse" value="<c:out value="${key}"/>"/>
														<c:out value="${value}　"/>
													</c:if>
						                        </c:forEach>
											</c:if>
										</dm3lookup:lookupForEach>
									</td>
								</tr>
							</c:if>
						</table>
					</div><!-- /.columnInner -->
				</div><!-- /.itemBlockA01 -->
				<div class="contentsInner01 mt30 mb15 spMt10 spPb10">
					<ul class="btnList01">
						<li><button type="button" name="" class="backBtn" onclick="linkToUrl('<c:out value='${pageContext.request.contextPath}'/>/mypage/', 'back');">キャンセル</button></li>
						<li><button type="button" name="" class="primaryBtn01" onclick="linkToUrl('../result/', 'comp');">削除する</button></li>
					</ul>
				</div>
				<dm3token:oneTimeToken/>
			</form>
		<!-- / .section01 --></div>
	<!-- / #contentsInner --></div>
<!-- / #contents --></div>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>