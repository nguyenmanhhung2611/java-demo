<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	// 業務パターン(XXX-99-99)
	String pageFlg = request.getParameter("pageFlg");

%>



<p class="resultNum">

		<% if("01".equals(pageFlg)){ %>
			<span class="totalNum"><c:out value="${pagingForm.maxRows}"/></span>&nbsp;<span>件が見つかりました。</span>
		<% } %>

		<% if("02".equals(pageFlg)){ %>
			<p class="resultNum"><span>お気に入り物件登録数</span><span class="totalNum">${pagingForm.maxRows}</span><span>件</span>
		<% } %>

	<c:if test="${pagingForm != null && pagingForm.rows != null}">
		<c:if test="${pagingForm.maxRows != 0}">
			&nbsp;<br class="SPdisplayBlock">(<c:out value="${pagingForm.startIndex + 1}"/>～<c:out value="${pagingForm.endIndex}"/>件を表示)
		</c:if>
	</c:if>
</p>

<ul>
	<c:if test="${pagingForm != null && pagingForm.rows != null}">
		<c:if test="${pagingForm.leftNavigationPageNo != pagingForm.rightNavigationPageNo}">
			<span class="navPaging">
		  	<c:if test="${pagingForm.maxRows != 0}">
				<c:if test="${pagingForm.selectedPage != 1}">
					<li class="prev"><a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage - 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend">前へ</a></li>
				</c:if>
				<c:forEach begin="${pagingForm.leftNavigationPageNo}"
						end="${pagingForm.rightNavigationPageNo}"
						var="thisPageNo">
					<c:choose>
						<c:when test="${thisPageNo == pagingForm.selectedPage}">
							<li class="current"><a><c:out value="${thisPageNo}"/></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="<c:out value="${strBefore}"/><c:out value="${thisPageNo}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage"><c:out value="${thisPageNo}"/></a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${pagingForm.selectedPage != pagingForm.maxPages}">
					<li class="next"><a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage + 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend">次へ</a></li>
				</c:if>
			</c:if>
			</span>
		</c:if>
	</c:if>
</ul>
