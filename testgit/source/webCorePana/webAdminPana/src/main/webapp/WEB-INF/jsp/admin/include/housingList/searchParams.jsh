<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
物件一覧で持ちまわる検索条件の hidden 出力
--%>
<input type="hidden" name="keySearchCommand" value="<c:out value="${searchForm.keySearchCommand}"/>">
<input type="hidden" name="keyHousingCd" value="<c:out value="${searchForm.keyHousingCd}"/>">
<input type="hidden" name="keyDisplayHousingName" value="<c:out value="${searchForm.keyDisplayHousingName}"/>">
<input type="hidden" name="keyPrefCd" value="<c:out value="${searchForm.keyPrefCd}"/>">
<input type="hidden" name="keyInsDateStart" value="<c:out value="${searchForm.keyInsDateStart}"/>">
<input type="hidden" name="keyInsDateEnd" value="<c:out value="${searchForm.keyInsDateEnd}"/>">
<input type="hidden" name="keyUpdDate" value="<c:out value="${searchForm.keyUpdDate}"/>">
<input type="hidden" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>">
<input type="hidden" name="keyHiddenFlg" value="<c:out value="${searchForm.keyHiddenFlg}"/>">
<input type="hidden" name="keyStatusCd" value="<c:out value="${searchForm.keyStatusCd}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">
