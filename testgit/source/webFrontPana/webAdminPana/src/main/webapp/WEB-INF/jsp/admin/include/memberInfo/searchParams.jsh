<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
会員検索機能で持ちまわる検索条件の hidden 出力
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="userId" value="<c:out value="${searchForm.userId}"/>">
<input type="hidden" name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>">
<input type="hidden" name="keyPrefCd" value="<c:out value="${searchForm.keyPrefCd}"/>">
<input type="hidden" name="keyPromo" value="<c:out value="${searchForm.keyPromo}"/>">
<input type="hidden" name="keyMemberLname" value="<c:out value="${searchForm.keyMemberLname}"/>">
<input type="hidden" name="keyMemberFname" value="<c:out value="${searchForm.keyMemberFname}"/>">
<input type="hidden" name="keyMemberLnameKana" value="<c:out value="${searchForm.keyMemberLnameKana}"/>">
<input type="hidden" name="keyMemberFnameKana" value="<c:out value="${searchForm.keyMemberFnameKana}"/>">
<input type="hidden" name="keyEntryRoute" value="<c:out value="${searchForm.keyEntryRoute}"/>">
<c:forEach items="${searchForm.keyInflowRoute}" var="selectedInflowRoute">
	<input type="hidden" name="keyInflowRoute" value="<c:out value="${selectedInflowRoute}"/>">
</c:forEach>
<input type="hidden" name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>">
<input type="hidden" name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">