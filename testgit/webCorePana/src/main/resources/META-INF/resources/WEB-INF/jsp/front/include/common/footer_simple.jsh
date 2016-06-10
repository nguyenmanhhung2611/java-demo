<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="date" class="java.util.Date"/>
<p id="pageTop"><a href="#ptop"><img src="<c:out value='${commonParameters.commonResourceRootUrl}'/>common/img/footer_pagetop.png" alt="ページトップへ戻る"></a></p>

<footer id="simplefooter">
	<div id="copyright">
		<div class="copyrightInner">
			<p>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Panasonic Corporation</p>
		</div>
	</div>
</footer>