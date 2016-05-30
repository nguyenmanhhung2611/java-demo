<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- お問合せ一覧画面で持ちまわる検索条件の hidden 出力 --%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyLname" value="<c:out value="${searchForm.keyLname}"/>">
<input type="hidden" name="keyFname" value="<c:out value="${searchForm.keyFname}"/>">
<input type="hidden" name="keyLnameKana" value="<c:out value="${searchForm.keyLnameKana}"/>">
<input type="hidden" name="keyFnameKana" value="<c:out value="${searchForm.keyFnameKana}"/>">
<input type="hidden" name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>">
<input type="hidden" name="keyAnswerStatus" value="<c:out value="${searchForm.keyAnswerStatus}"/>">
<input type="hidden" name="keyTel" value="<c:out value="${searchForm.keyTel}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">