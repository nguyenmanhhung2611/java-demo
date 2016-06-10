<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">
<input type="hidden" name="keyNewsId" value="<c:out value="${news.newsId}"/>">