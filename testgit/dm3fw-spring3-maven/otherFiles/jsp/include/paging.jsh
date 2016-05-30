<%@ page pageEncoding="UTF-8" %>
<c:if test="${pagingForm.leftNavigationPageNo != pagingForm.rightNavigationPageNo}">
  <c:set var="pageOptionParams" value="${pagingForm.optionParams}"/>
  <div class="navPaging">
    <c:if test="${pagingForm.selectedPage != 1}">
      <a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${pagingForm.selectedPage - 1}"/>${pageOptionParams}" class="navNotSelectedPage navBookend">前へ</a>
    </c:if>
    <c:forEach begin="${pagingForm.leftNavigationPageNo}" 
               end="${pagingForm.rightNavigationPageNo}" 
               var="thisPageNo">
      <c:choose>
        <c:when test="${thisPageNo == pagingForm.selectedPage}">
          <span class="navSelectedPage"><c:out value="${thisPageNo}"/></span>
        </c:when>
        <c:otherwise>
          <a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${thisPageNo}"/>${pageOptionParams}" class="navNotSelectedPage"><c:out value="${thisPageNo}"/></a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <c:if test="${pagingForm.selectedPage != pagingForm.maxPages}">
      <a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${pagingForm.selectedPage + 1}"/>${pageOptionParams}" class="navNotSelectedPage navBookend">次へ</a>
    </c:if>
  </div>
</c:if>
