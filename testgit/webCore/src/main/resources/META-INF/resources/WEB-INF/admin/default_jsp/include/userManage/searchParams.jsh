<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
管理ユーザーメンテナンス機能で持ちまわる検索条件の hidden 出力 
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyLoginId" value="<c:out value="${searchForm.keyLoginId}"/>">
<input type="hidden" name="keyUserName" value="<c:out value="${searchForm.keyUserName}"/>">
<input type="hidden" name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>">
<input type="hidden" name="keyRoleId" value="<c:out value="${searchForm.keyRoleId}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">
