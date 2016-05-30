<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
管理ユーザ機能で持ちまわる検索条件の hidden 出力 
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>">
<input type="hidden" name="keyHousingCd" value="<c:out value="${searchForm.keyHousingCd}"/>">
<input type="hidden" name="keyDisplayHousingName" value="<c:out value="${searchForm.keyDisplayHousingName}"/>">
<input type="hidden" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>">
<input type="hidden" name="keyInquiryDateStart" value="<c:out value="${searchForm.keyInquiryDateStart}"/>">
<input type="hidden" name="keyInquiryDateEnd" value="<c:out value="${searchForm.keyInquiryDateEnd}"/>">
<c:forEach items="${searchForm.keyInquiryType}" var="keyInquiryType">
	<input type="hidden" name="keyInquiryType" value="<c:out value="${keyInquiryType}"/>">
</c:forEach>
<c:forEach items="${searchForm.keyInquiryDtlType}" var="keyInquiryDtlType">
	<input type="hidden" name="keyInquiryDtlType" value="<c:out value="${keyInquiryDtlType}"/>">
</c:forEach>
<input type="hidden" name="keyAnswerStatus" value="<c:out value="${searchForm.keyAnswerStatus}"/>">
<input type="hidden" name="keyInquiryId" value="<c:out value="${searchForm.keyInquiryId}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">