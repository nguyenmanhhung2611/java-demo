<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
お知らせメンテナンス機能で持ちまわる検索条件の hidden 出力 
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyNewsId" value="<c:out value="${searchForm.keyNewsId}"/>">
<input type="hidden" name="keyNewsTitle" value="<c:out value="${searchForm.keyNewsTitle}"/>">
<input type="hidden" name="keyNewsContent" value="<c:out value="${searchForm.keyNewsContent}"/>">

<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">