<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<div id="tnavBlockA01">

<c:forEach var="menuPage" items="${gcMenuInfo}" varStatus="status">

	<%-- メニューページ情報のロールが未設定の場合、デフォルトのロール名を設定 --%>
	<c:set var="menuPageRole" value="${menuPage.value.roles}"/>
	<c:if test="${empty menuPage.value.roles}">
		<c:set var="menuPageRole" value="${gcDefaultRole}"/>
	</c:if>

	<dm3login:hasRole roleName="${menuPageRole}" negative="false">

		<%-- メニュー画像が設定されている場合、画像ファイルでタブを表示 --%>
		<c:if test="${!empty menuPage.value.menuTabOnImage}">
			<c:if test="${menuPage.value.menuPageName==gcCurrentMenu.menuPageName}">
	<a href="<c:out value="${pageContext.request.contextPath}"/>/<c:out value="${menuPage.key}"/>/"><img src="<c:out value="${commonParameters.resourceRootUrl}"/><c:out value="${menuPage.value.menuTabOnImage}"/>" alt="<c:out value="${menuPage.value.menuPageName}"/>" width="175" height="27"></a>
			</c:if>
			<c:if test="${menuPage.value.menuPageName!=gcCurrentMenu.menuPageName}">
	<a href="<c:out value="${pageContext.request.contextPath}"/>/<c:out value="${menuPage.key}"/>/"><img src="<c:out value="${commonParameters.resourceRootUrl}"/><c:out value="${menuPage.value.menuTabOffImage}"/>" alt="<c:out value="${menuPage.value.menuPageName}"/>" width="175" height="27"></a>
			</c:if>
		</c:if>

		<%-- メニュー画像が設定されていない場合、テキストでタブを表示 --%>
		<c:if test="${empty menuPage.value.menuTabOnImage}">
	<a href="<c:out value="${pageContext.request.contextPath}"/>/<c:out value="${menuPage.key}"/>/"><c:out value="${menuPage.value.menuPageName}"/></a>
		</c:if>
		
	</dm3login:hasRole>
</c:forEach>

</div>