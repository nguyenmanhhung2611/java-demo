<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		
		<tr height="50">
			<th class="head_tr" width="15%">News Title</th>
			<td><c:out value="${inputForm.newsTitle}"/></td>
		</tr>
		
		<tr height="50">
			<th class="head_tr" width="15%">News Content</th>
			<td><c:out value="${inputForm.newsContent}"/></td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<form method="post" name="inputForm" >
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/news/searchParams.jsh" />
	<input type="hidden" name="newsId" value="<c:out value="${inputForm.newsId}"/>">
	<input type="hidden" name="newsTitle" value="<c:out value="${inputForm.newsTitle}"/>">
	<input type="hidden" name="newsContent" value="<c:out value="${inputForm.newsContent}"/>">
	<input type="hidden" name="userId" value="<c:out value="${inputForm.userId}"/>">
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">

function linkToUrl(url, cmd) {
	document.inputForm.action = url;
	document.inputForm.command.value = cmd;
	document.inputForm.submit();
}

</script>
