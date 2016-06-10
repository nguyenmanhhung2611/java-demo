<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/import.css" type="text/css" media="screen,print" />
		<title><c:out value="${param.htmlTitle}"/></title>
	<head>
	<body>
	<c:set var="user" value="${sessionScope['jp.co.transcosmos.dm3.foundation.User']}"/>

		<!--===||| wrap |||=== -->
		<div id="wrap">
	<c:import url="/WEB-INF/jsp/admin/layout/header.jsh" />

			<!--===||| mainArea |||=== -->
			<div id="mainArea" class="clear">
	<c:import url="/WEB-INF/jsp/admin/include/menu/sideMenu.jsh" />
				<!--=== contentsArea === -->
				<div id="contentsArea">
					<!--contentsAreaInner -->
					<div class="contentsAreaInner">

	<c:if test="${!empty param.pageTitle}" >
						<div class="headingAreaA01 start">
							<h1>${param.pageTitle}</h1>
						</div>
	</c:if>

						${param.contents}
					</div>
					<!--/contentsAreaInner -->

				</div>
				<!--/=== contentsArea === -->
			</div>
			<!--/===||| mainArea |||=== -->
		</div>
		<!--/===||| wrap |||=== -->
	</body>
</html>