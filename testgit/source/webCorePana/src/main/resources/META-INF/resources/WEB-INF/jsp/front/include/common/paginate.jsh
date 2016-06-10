<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="canonical" href="<c:out value="${defaultUrl}"/>" />
<c:if test="${pagingForm != null && pagingForm.rows != null}">
	<c:if test="${pagingForm.leftNavigationPageNo != pagingForm.rightNavigationPageNo}">
		<c:set var="pageOptionParams" value="${pagingForm.optionParams}"/>
		<c:set var="paramsLength" value="${pageOptionParams.length()}"/>
		<c:set var="paramsLengthMax" value="1900"/>

		<c:if test="${pagingForm.maxRows != 0}">
			<c:if test="${pagingForm.selectedPage != 1}">
				<%-- 前へ --%>
				<c:if test="${paramsLength <= paramsLengthMax}" >
					<link rel="prev" href="<c:out value="${defaultUrl}"/>?selectedPage=<c:out value="${pagingForm.selectedPage - 1}"/>${pageOptionParams}" />
				</c:if>
			</c:if>
			<c:if test="${pagingForm.selectedPage != pagingForm.maxPages}">
				<%-- 次へ --%>
				<c:if test="${paramsLength <= paramsLengthMax}" >
					<link rel="next" href="<c:out value="${defaultUrl}"/>?selectedPage=<c:out value="${pagingForm.selectedPage + 1}"/>${pageOptionParams}" />
				</c:if>
			</c:if>
		</c:if>
	</c:if>
</c:if>
