<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 
建物情報メンテナンス機能で持ちまわる検索条件の hidden 出力 
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyBuildingCd" value="<c:out value="${searchForm.keyBuildingCd}"/>">
<input type="hidden" name="keyDisplayBuildingName" value="<c:out value="${searchForm.keyDisplayBuildingName}"/>">
<input type="hidden" name="keyPrefCd" value="<c:out value="${searchForm.keyPrefCd}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">
