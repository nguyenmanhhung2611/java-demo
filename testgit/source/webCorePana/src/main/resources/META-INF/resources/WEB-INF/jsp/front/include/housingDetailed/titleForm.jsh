<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
			<c:if test="${outPutForm.isReformPlanExists() == 'true'}">
				<div class="ancBlock"><a href="#anc01"><span>この物件にはおすすめリフォームプラン例があります。</span></a><span class="SPdisplayBlock"></span></div>
			</c:if>
			<div class="buildingName clearfix">
				<div class="blockRight">
					<p class="btnGray01"><a onClick="print_new()" href="javascript:void(0);">印刷する</a></p>
					<p class="buildingNum">物件番号:<c:out value="${outPutForm.getHousingCd()}"/></p>
				</div>

				<div class="blockLeft">
					<ul class="buildingType clearfix">
						<c:if test="${outPutForm.isFreshFlg() == 'true'}">
							<li class="icoNew01">新着</li>
						</c:if>
						<li class="icoMansion01"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${outPutForm.getHousingKindCd()}"/></li>
                        <c:if test="${outPutForm.isReformPlanDisplayFlg() == 'true'}">
                            <li class="icoNew01">リフォームプランあり</li>
                        </c:if>
					</ul>

					<p><c:out value="${outPutForm.getDtlComment()}" escapeXml="false"/></p>
					<h1><c:out value="${outPutForm.getDisplayHousingName()}"/></h1>
				</div>
			</div>