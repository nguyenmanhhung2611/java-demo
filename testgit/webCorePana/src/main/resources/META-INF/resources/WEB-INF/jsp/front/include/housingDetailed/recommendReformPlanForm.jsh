<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

				<div class="planDetail">
					<div class="title clearfix">
						<h2><c:out value="${outPutForm.getPlanName()}"/><br>
						<span><c:out value="${outPutForm.getSalesPoint()}" escapeXml="false"/></span></h2>
						<div class="catchcopy">
							<p><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/reform/" target="_blank"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_img_01.png" alt=""></a></p>
						</div>
					</div>
					<div class="detailList">
						<dl class="listType03">
							<dt><p class="price">総額</p></dt>
							<dd><p class="price"><span><c:out value="${outPutForm.getTotalDtlPrice1()}"/></span><br>
							<c:out value="${outPutForm.getTotalDtlPrice2()}"/></p><p class="btnBlack01"><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/loan-simulation/" target="_blank">ローンシミュレーション</a></p></dd>
						</dl>
						<dl class="listType02">
							<dt><p class="detail">リフォーム詳細<br>
							<span>※各項目をクリックすると詳細をご覧いただけます。</span></p></dt>
							<dd><table class="tableType3">
								<tbody>
									<c:forEach var="varReformNoHidden" items="${outPutForm.getReformNoHidden()}" step="2">
										<tr>
											<c:if test="${!empty varReformNoHidden && !empty outPutForm.getReformPathName()[varReformNoHidden]}">
												<th><a href="<c:out value="${outPutForm.getReformPathName()[varReformNoHidden]}"/>" target="_blank"><c:out value="${outPutForm.getReformImgName()[varReformNoHidden]}"/></a></th>
												<td><c:out value="${outPutForm.getReformPrice()[varReformNoHidden]}"/></td>
											</c:if>
										</tr>
										<tr>
											<c:if test="${!empty varReformNoHidden && !empty outPutForm.getReformPathName()[varReformNoHidden + 1]}">
												<th class="even"><a href="<c:out value="${outPutForm.getReformPathName()[varReformNoHidden + 1]}"/>" target="_blank"><c:out value="${outPutForm.getReformImgName()[varReformNoHidden + 1]}"/></a></th>
												<td class="even"><c:out value="${outPutForm.getReformPrice()[varReformNoHidden + 1]}"/></td>
											</c:if>
										</tr>
									</c:forEach>
								</tbody>
							</table></dd>
						</dl>
						<dl>
							<dt><p class="duration">工期</p></dt>
							<dd><c:out value="${outPutForm.getConstructionPeriod()}"/></dd>
						</dl>
						<dl>
							<dt><p class="remarks">備考</p></dt>
							<dd><c:out value="${outPutForm.getReformNote()}" escapeXml="false"/></dd>
						</dl>
					</div>
				</div>