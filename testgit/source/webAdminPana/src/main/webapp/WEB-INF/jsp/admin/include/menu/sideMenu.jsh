<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!-- === sideArea === -->
<div id="sideArea">
	<!-- manageInfo -->
	<div id="manageInfo">
		<p class="h7">
			<strong><c:out value="${sessionScope['loggedInUser'].userName}"/>様</strong>
		</p>
<c:if test="${empty sessionScope['loggedInUser'].preLoginDate}">
			&nbsp;<br/>
</c:if>
<c:if test="${!empty sessionScope['loggedInUser'].preLoginDate}">
		<p class="small">前回ログイン日時<br><fmt:formatDate pattern="yyyy/M/d"
				value="${sessionScope['loggedInUser'].preLoginDate}"/>　<fmt:formatDate pattern="H:mm"
				value="${sessionScope['loggedInUser'].preLoginDate}"/></p>
</c:if>
	</div>
	<!-- /manageInfo -->

	<!-- lnavBlockA01 -->
	<div id="lnavBlockA01">
		<dl>
			<dt>
<c:if test="${!empty gcCurrentMenu.menuPageTitleIamge}">
				<img src="<c:out value="${commonParameters.resourceRootUrl}"/><c:out value="${gcCurrentMenu.menuPageTitleIamge}"/>" alt="<c:out value="${gcCurrentMenu.menuPageName}"/>">
</c:if>
<c:if test="${empty gcCurrentMenu.menuPageTitleIamge}">
				<span><c:out value="${gcCurrentMenu.menuPageName}"/></span>
</c:if>
			</dt>
			<dd>
				<ul>
<c:forEach var="menuCategory" items="${gcCurrentMenu.menuCategorys}" varStatus="status">
	<%-- メニューカテゴリー情報のロールが未設定の場合、デフォルトのロール名を設定 --%>
	<c:set var="categoryRole" value="${menuCategory.value.roles}"/>
	<c:if test="${empty menuCategory.value.roles}">
		<c:set var="categoryRole" value="${gcDefaultRole}"/>
	</c:if>

	<dm3login:hasRole roleName="${categoryRole}" negative="false">
					<li<c:if test="${fn:startsWith(selectedMainNav, 'enquiry.')}"> class="lnavParentsListA01"</c:if>>
						<p><c:out value="${menuCategory.value.menuItemCategoryName}"/></p>
						<ul>
		<c:forEach var="menuItem" items="${menuCategory.value.menuItems}" varStatus="status">
			<%-- メニュー Item 情報のロールが未設定の場合、デフォルトのロール名を設定 --%>
			<c:set var="itemRole" value="${menuItem.value.roles}"/>
			<c:if test="${empty menuItem.value.roles}">
				<c:set var="itemRole" value="${gcDefaultRole}"/>
			</c:if>

			<%-- メニュー Item を権限をチェックして表示 --%>
			<dm3login:hasRole roleName="${itemRole}" negative="false">
						<ul>
							<li class="lnavChildListFirst<c:if test="${menuItem.key==gcCurrentMenuItem}"> lnavChildListActive</c:if>">
								<c:set var="menuItemName" value="${menuItem.value.menuItemName}" />
								<a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${menuItem.value.menuItemUrl}"/>"><c:out value="${dm3functions:crToHtmlTag(menuItemName)}"/></a>
							</li>
						</ul>
			</dm3login:hasRole>
		</c:forEach>
						</ul>
					</li>
	</dm3login:hasRole>
</c:forEach>
				</ul>
			</dd>
		</dl>
	</div>
	<!-- /lnavBlockA01 -->

</div>
<!-- /=== sideArea === -->