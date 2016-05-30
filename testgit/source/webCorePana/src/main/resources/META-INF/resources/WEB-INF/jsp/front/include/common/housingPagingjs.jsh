<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="strBefore" value="javascript:submitList('" scope="request"/>
<c:set var="strAfter" value="')" scope="request"/>
<script>
	function submitList(page) {
		document.srchListForm.selectedPage.value = page;
		document.srchListForm.submit();
	}
</script>

<p class="resultNum">

		<c:if test="${param.pageFlg == '01'}">
			<span class="totalNum"><c:out value="${pagingForm.maxRows}"/></span>&nbsp;<span>件見つかりました。</span>
		</c:if>

		<c:if test="${param.pageFlg == '02'}">
			<p class="resultNum"><span>お気に入り物件登録数</span><span class="totalNum">${pagingForm.maxRows}</span><span>件</span>
		</c:if>

	<c:if test="${pagingForm != null && pagingForm.rows != null && param.pageFlg != null}">
		<c:if test="${pagingForm.maxRows != 0}">
			<br class="SPdisplayBlock">(<c:out value="${pagingForm.startIndex + 1}"/>～<c:out value="${pagingForm.endIndex}"/>件を表示)
		</c:if>
	</c:if>
</p>



<ul>
<c:if test="${pagingForm != null && pagingForm.rows != null}">
	<c:if test="${pagingForm.leftNavigationPageNo != pagingForm.rightNavigationPageNo}">
		<c:set var="pageOptionParams" value="${pagingForm.optionParams}"/>
		<c:set var="paramsLength" value="${pageOptionParams.length()}"/>
		<c:set var="paramsLengthMax" value="1900"/>
		<span class="navPaging">
		<c:if test="${pagingForm.maxRows != 0}">
			<c:if test="${pagingForm.selectedPage != 1}">
				<%-- 前へ --%>
				<c:if test="${paramsLength <= paramsLengthMax}" >
					<li class="prev"><a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${pagingForm.selectedPage - 1}"/>${pageOptionParams}" class="navNotSelectedPage navBookend" >前へ</a></li>
				</c:if>
				<c:if test="${paramsLength > paramsLengthMax}" >
					<li class="prev"><a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage - 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend" >前へ</a></li>
				</c:if>
			</c:if>
			<c:forEach begin="${pagingForm.leftNavigationPageNo}" end="${pagingForm.rightNavigationPageNo}" var="thisPageNo">
				<c:choose>
					<c:when test="${thisPageNo == pagingForm.selectedPage}">
						<%-- カレント --%>
						<li class="current"><a><c:out value="${thisPageNo}"/></a></li>
					</c:when>
					<c:otherwise>
						<%-- ページリンク --%>
						<c:if test="${paramsLength <= paramsLengthMax}" >
							<li><a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${thisPageNo}"/>${pageOptionParams}" class="navNotSelectedPage"><c:out value="${thisPageNo}"/></a></li>
						</c:if>
						<c:if test="${paramsLength > paramsLengthMax}" >
							<li><a href="<c:out value="${strBefore}"/><c:out value="${thisPageNo}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage"><c:out value="${thisPageNo}"/></a></li>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${pagingForm.selectedPage != pagingForm.maxPages}">
				<%-- 次へ --%>
				<c:if test="${paramsLength <= paramsLengthMax}" >
					<li class="next"><a href="<c:out value="${pagingURL}"/>?selectedPage=<c:out value="${pagingForm.selectedPage + 1}"/>${pageOptionParams}" class="navNotSelectedPage navBookend" >次へ</a></li>
				</c:if>
				<c:if test="${paramsLength > paramsLengthMax}" >
					<li class="next"><a href="<c:out value="${strBefore}"/><c:out value="${pagingForm.selectedPage + 1}"/><c:out value="${strAfter}"/>" class="navNotSelectedPage navBookend" >次へ</a></li>
				</c:if>
			</c:if>
		</c:if>
		</span>
	</c:if>
</c:if>
</ul>
