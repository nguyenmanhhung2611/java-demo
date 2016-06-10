<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 
ページ制御用リンク表示jsh 
例: 前へ 1 2 3 4 次へ
--%>

<c:if test="${pagingForm != null && pagingForm.rows != null}">
	<c:if test="${pagingForm.leftNavigationPageNo != pagingForm.rightNavigationPageNo}">
		<span class="navPaging">
	  	<c:if test="${pagingForm.maxRows != 0}">
			<c:if test="${pagingForm.selectedPage != 1}">
				<a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage - 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend">前へ</a>
			</c:if>
			<c:forEach begin="${pagingForm.leftNavigationPageNo}" 
					end="${pagingForm.rightNavigationPageNo}" 
					var="thisPageNo">
				<c:choose>
					<c:when test="${thisPageNo == pagingForm.selectedPage}">
						<span class="navSelectedPage"><c:out value="${thisPageNo}"/></span>
					</c:when>
					<c:otherwise>
						<a href="<c:out value="${strBefore}"/><c:out value="${thisPageNo}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage"><c:out value="${thisPageNo}"/></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${pagingForm.selectedPage != pagingForm.maxPages}">
				<a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage + 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend">次へ</a>
			</c:if>
		</c:if>
		</span>
	</c:if>
</c:if>