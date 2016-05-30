<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="companyBlock">
				<div class="person clearfix">
					<p class="img"><img src="<c:if test="${!empty outPutForm.getStaffimagePathName()}"><c:out value="${outPutForm.getStaffimagePathName()}"/></c:if><c:if test="${empty outPutForm.getStaffimagePathName()}"><c:out value="${commonParameters.commonResourceRootUrl}${commonParameters.noPhoto110}"/></c:if>" alt="<c:out value="${outPutForm.getStaffName()}"/>"></p>
					<div class="comment">
						<p class="title">担当者からのおすすめ</p>
						<p><c:out value="${outPutForm.getRecommendComment()}" escapeXml="false"/></p>
					</div>
				</div>

				<div class="company">
					<dl class="clearfix">
						<dt>担当：<c:out value="${outPutForm.getStaffName()}"/></dt>
						<dd><c:out value="${outPutForm.getCompanyName()}"/>　<c:out value="${outPutForm.getBranchName()}"/></dd>
						<dd><c:out value="${outPutForm.getLicenseNo()}"/></dd>
					</dl>
				</div>
			</div>