<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyCommentId" value="<c:out value="${searchForm.keyCommentId}"/>">
<input type="hidden" name="keyNewId" value="<c:out value="${searchForm.keyNewId}"/>">
<input type="hidden" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>">
<input type="hidden" name="keyCommentContent" value="<c:out value="${searchForm.keyCommentContent}"/>">
<input type="hidden" name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>">
<input type="hidden" name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">