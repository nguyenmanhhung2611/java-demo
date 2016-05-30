<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<%--
邵ｺ鬘碑｡咲ｹｧ蟲ｨ笳狗ｹ晢ｽ｡郢晢ｽｳ郢晢ｿｽ郢晉ｿｫﾎｦ郢ｧ�ｽｹ隶匁ｺｯ�ｿｽ�ｽｽ邵ｺ�ｽｧ闖ｴ�ｽｿ騾包ｽｨ邵ｺ蜷ｶ�ｽ玖怦�ｽ･陷牙ｸ吶Ψ郢ｧ�ｽｩ郢晢ｽｼ郢晢ｿｽ邵ｺ�ｽｮ陷�ｽｺ陷会ｿｽ
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script type ="text/JavaScript">

</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<form action="" method="post" name="inputForm">
		<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
		<input type="hidden" name="newsId" value="<c:out value="${inputForm.newsId}"/>">
		
		<c:import url="/WEB-INF/admin/default_jsp/include/news/searchParams.jsh" />

		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="15%">Tiltle</th>
				<td><input type="text" name="newsTitle" value="<c:out value="${inputForm.newsTitle}"/>" size="40" maxlength="50" class="input2 ime-disabled"></td>
			</tr>
			
			<tr>
				<th class="head_tr" width="15%">Content</th>
				<td><input type="text" name="newsContent" value="<c:out value="${inputForm.newsContent}"/>" size="40" maxlength="200" class="input2 ime-disabled"></td>
			</tr>
		</table>
	</form>
</div>

<!--/flexBlockA01 -->


<form action="" method="post" name="copydate">
	<input type="hidden" name="newsId" value="">
	<c:import url="/WEB-INF/jsp/admin/include/news/searchParams.jsh" />
</form>

<script type ="text/JavaScript">
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

</script>
