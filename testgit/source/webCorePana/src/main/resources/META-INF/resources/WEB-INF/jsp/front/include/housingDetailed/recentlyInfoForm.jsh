<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

			<div class="viewedProperty">
				<div class="title clearfix">
					<h2>最近見た物件</h2>
					<p><a href="<c:out value="${pageContext.request.contextPath}"/>/history/" target="_blank">もっと見る</a></p>
				</div>
				<div class="listBlock clearfix SPdisplayNone">
					<c:forEach var="varRecentlyNoHidden" items="${outPutForm.getRecentlyNoHidden()}">
						<c:if test="${!empty varRecentlyNoHidden}">
							<div class="listBox clearfix" data-number="<c:out value="${outPutForm.getRecentlyHousingCdHidden()[varRecentlyNoHidden]}"/>">
								<dl class="clearfix">
									<dt><a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getRecentlyUrl()[varRecentlyNoHidden]}"/>" target="_blank"><img src="<c:if test="${!empty outPutForm.getRecentlyPathName()[varRecentlyNoHidden]}"><c:out value="${outPutForm.getRecentlyPathName()[varRecentlyNoHidden]}"/></c:if><c:if test="${empty outPutForm.getRecentlyPathName()[varRecentlyNoHidden]}"><c:out value="${commonParameters.commonResourceRootUrl}${commonParameters.noPhoto86}"/></c:if>" alt=""></a></dt>
									<dd>
										<p class="icoMansion01"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${outPutForm.getRecentlyHousingKindCd()[varRecentlyNoHidden]}"/></p>
										<p class="name">
											<a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getRecentlyUrl()[varRecentlyNoHidden]}"/>" target="_blank" title="<c:out value="${outPutForm.getRecentlyDisplayHousingNameFull()[varRecentlyNoHidden]}"/>"><c:out value="${outPutForm.getRecentlyDisplayHousingName()[varRecentlyNoHidden]}"/></a>
										</p>
										<p title="<c:out value="${outPutForm.getRecentlyDtlFull()[varRecentlyNoHidden]}" escapeXml="false"/>"><c:out value="${outPutForm.getRecentlyDtl()[varRecentlyNoHidden]}" escapeXml="false"/></p>
									</dd>
								</dl>
								<p class="btnOrange01"><a href="javascript:void(0);" target="_blank" onClick="linkToSubmit('<c:out value="${outPutForm.getRecentlySysHousingCdHidden()[varRecentlyNoHidden]}"/>',<c:out value="${outPutForm.isMemberFlg()}"/>);">お問い合わせ</a></p>
								<p class="btnGray01"><a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getRecentlyUrl()[varRecentlyNoHidden]}"/>" target="_blank">詳細を見る</a></p>
							</div>
						</c:if>
					</c:forEach>
				</div>


				<ul class="SPlistBlock SPdisplayBlock">
					<c:forEach var="varRecentlyNoHidden" items="${outPutForm.getRecentlyNoHidden()}">
						<c:if test="${!empty varRecentlyNoHidden}">
							<li><a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${outPutForm.getRecentlyUrl()[varRecentlyNoHidden]}"/>" target="_blank"><p>
							<span class="icoMansion01"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${outPutForm.getRecentlyHousingKindCd()[varRecentlyNoHidden]}"/></span>
							<span class="name"><c:out value="${outPutForm.getRecentlyDisplayHousingNameFull()[varRecentlyNoHidden]}" escapeXml="false"/></span></p>
							<p><c:out value="${outPutForm.getRecentlyDtlFull()[varRecentlyNoHidden]}" escapeXml="false"/></p></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</div>