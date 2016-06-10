<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 
件数表示用jsh 
例: 25件 (11-20件目表示) 
--%>

<c:if test="${pagingForm != null && pagingForm.rows != null}">
	<span class="navPaging">
	<c:if test="${pagingForm.maxRows != 0}">
		<c:out value="${pagingForm.maxRows}"/>件 (<c:out value="${pagingForm.startIndex + 1}"/>-<c:out value="${pagingForm.endIndex}"/>件目表示)
	</c:if>
	</span>
</c:if>