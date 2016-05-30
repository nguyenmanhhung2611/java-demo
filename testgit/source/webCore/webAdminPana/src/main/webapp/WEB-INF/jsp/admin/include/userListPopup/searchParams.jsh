<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
会員検索機能で持ちまわる検索条件の hidden 出力
--%>
<input type="hidden" name="keyUserNo" value="<c:out value="${searchForm.keyUserNo}"/>">
<input type="hidden" name="keyPrefCd" value="<c:out value="${searchForm.keyPrefCd}"/>">
<input type="hidden" name="keyMemberLname" value="<c:out value="${searchForm.keyMemberLname}"/>">
<input type="hidden" name="keyMemberFname" value="<c:out value="${searchForm.keyMemberFname}"/>">
<input type="hidden" name="keyMemberLnameKana" value="<c:out value="${searchForm.keyMemberLnameKana}"/>">
<input type="hidden" name="keyMemberFnameKana" value="<c:out value="${searchForm.keyMemberFnameKana}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">