<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="contents">

	<c:set var="categoryCnt" value="0" />
	<c:forEach var="menuCategory" items="${gcCurrentMenu.menuCategorys}">
		<%-- メニューカテゴリー情報のロールが未設定の場合、デフォルトのロール名を設定 --%>
		<c:set var="categoryRole" value="${menuCategory.value.roles}"/>
		<c:if test="${empty menuCategory.value.roles}">
			<c:set var="categoryRole" value="${gcDefaultRole}"/>
		</c:if>

		<dm3login:hasRole roleName="${categoryRole}" negative="false">

			<c:if test="${categoryCnt % 2 == 0}">
			<!--flexMenuLineBlock -->
			<div class="flexMenuLineBlock">
				<!--floatBlock -->
				<div class="floatLeftBlock <c:if test="${categoryCnt < 2}">start</c:if>">
			</c:if>
			<c:if test="${categoryCnt % 2 != 0}">
				<!--floatBlock -->
				<div class="floatRightBlock <c:if test="${categoryCnt < 2}">start</c:if>">
			</c:if>

					<div class="headingAreaA01">
						<h1><c:out value="${menuCategory.value.menuItemCategoryName}"/></h1>
					</div>

			<c:forEach var="menuItem" items="${menuCategory.value.menuItems}" varStatus="ItemStatus">
				<%-- メニュー Item 情報のロールが未設定の場合、デフォルトのロール名を設定 --%>
				<c:set var="itemRole" value="${menuItem.value.roles}"/>
				<c:if test="${empty menuItem.value.roles}">
					<c:set var="itemRole" value="${gcDefaultRole}"/>
				</c:if>
			
				<%-- メニュー Item を権限をチェックして表示 --%>
				<dm3login:hasRole roleName="${itemRole}" negative="false">
					<!--headingAreaInner -->
					<div class="headingAreaInner">
						<p>
							<c:set var="menuItemName" value="${menuItem.value.menuItemName}" />
							<a href="<c:out value="${pageContext.request.contextPath}"/><c:out value="${menuItem.value.menuItemUrl}"/>" class="btnLineA01"><c:out value="${menuItemName}"></c:out></a>
						</p>
					</div>
					<!--/headingAreaInner -->
				</dm3login:hasRole>
			</c:forEach>

				</div>
				<!--/floatBlock -->

			<c:if test="${categoryCnt % 2 != 0}">
			</div>
			<!-- /flexMenuLineBlock -->
			</c:if>
			<c:set var="categoryCnt" value="${categoryCnt + 1}" />
		</dm3login:hasRole>
	</c:forEach>

	<c:if test="${categoryCnt % 2 != 0}">
			</div>
			<!-- /flexMenuLineBlock -->
	</c:if>

</c:param>
</c:import>