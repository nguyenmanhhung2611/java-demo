<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
お知らせメンテナンス機能で持ちまわる検索条件の hidden 出力 
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyInformationNo" value="<c:out value="${searchForm.keyInformationNo}"/>">
<input type="hidden" name="keyTitle" value="<c:out value="${searchForm.keyTitle}"/>">
<input type="hidden" name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>">
<input type="hidden" name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>">
<input type="hidden" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">