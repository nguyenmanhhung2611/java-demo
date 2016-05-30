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
		
		<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
		<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/main.js"></script>
		<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.ah-placeholder.js"></script>
		<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/html5.js"></script>
	</head> 
	<body>
	<div id="ptop"></div>
	<c:import url="/WEB-INF/front/layout/header-S.jsh" />
	<c:set var="user" value="${sessionScope['jp.co.transcosmos.dm3.foundation.User']}"/>
	
	<div id="contents">
		<div id="contentsInner">
			<div class="section01">
				<c:if test="${!empty param.pageTitle}" >
					<div class="headingBlockA01 clearfix">
						<h1 class="ttl">${param.pageTitle}</h1>
					</div>
				</c:if>
				${param.contents}
			</div>
		</div>
	</div>
	<c:import url="/WEB-INF/front/layout/footer-S.jsh" />
	</body>
</html>