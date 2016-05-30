<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--===||| headerArea |||=== -->
<div id="headerArea">
	<div id="headerAreaInner" class="clear">

		<!--headerlogoArea -->
		<div id="headerlogoArea">
			<a href="<c:out value="${pageContext.request.contextPath}"/>/top"><img src="<c:out value="${pageContext.request.contextPath}"/>/cmn/imgs/logo.gif" alt="デモサイト" width="71" height="32"></a>
		</div>
		<!--/headerlogoArea -->

		<!--headerNavArea -->
		<div id="headerNavArea">
			<ul>
				<li class="logout">
					<a href="<c:out value="${pageContext.request.contextPath}"/>/login/logout/">
						<img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/bt_logout.gif"></a>
				</li>
				<li class="home" style="padding-top:5px;">
					※ブラウザの｢戻る｣ボタンを使用しないでください。入力したデータが消えることがあります。
				</li>
			</ul>
		</div>
		<!--/headerNavArea -->

	</div>
</div>
<!--/===||| headerArea |||=== -->