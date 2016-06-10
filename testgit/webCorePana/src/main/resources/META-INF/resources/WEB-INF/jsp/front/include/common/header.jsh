<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>

    <script type ="text/javascript">

    $(document).ready(function(){
        if ((navigator.userAgent.indexOf('iPhone') > 0 && navigator.userAgent.indexOf('iPad') == -1) || navigator.userAgent.indexOf('iPod') > 0 || navigator.userAgent.indexOf('Android') > 0) {
            $('#mainnav .logout').fancybox({
                fitToView   : false,
                width       : '250px',
                closeClick  : false,
                closeBtn    : false,
                openEffect  : 'none',
                closeEffect : 'none',
                afterClose   : function() {
                    $('#mainnav .logout').show();
                }
            });
        }
        else {
            $('#utilityNav .logout').fancybox({
                fitToView   : false,
                width       : '400px',
                closeClick  : false,
                closeBtn    : false,
                openEffect  : 'none',
                closeEffect : 'none',
                afterClose   : function() {
                    $('#utilityNav .logout').show();
                }
            });
        }
        $("[id^='personalInfo'] .favorite").text(getFavoriteCount());
    });
    function getFavoriteCount() {
        $("[id^='personalInfo'] .history").text(getHistoryCount());
        return <c:out value="${favoriteCount}" default="0"/>;
    }
    function getHistoryCount() {
        return <c:out value="${historyCount}" default="0"/>;
    }
    </script>
	
	<dm3login:hasRole roleName="mypage" negative="false">
		<c:set var="isLogin" value="true"/>
	</dm3login:hasRole>

	<c:choose>
		<%-- ログイン前／買いたい --%>
		<c:when test="${isLogin == null && param.subNav != 'off'}">
			<!--#include virtual="/common/ssi/buy-before-header-D.html"-->
		</c:when>
		<%-- ログイン後／買いたい --%>
		<c:when test="${isLogin == 'true' && param.subNav != 'off'}">
			<!--#include virtual="/common/ssi/buy-after-header-D.html"-->
		</c:when>
		<%-- ログイン前／買いたい以外 --%>
		<c:when test="${isLogin == null && param.subNav == 'off'}">
			<!--#include virtual="/common/ssi/mypage-before-header-D.html"-->
		</c:when>
		<%-- ログイン後／買いたい以外 --%>
		<c:when test="${isLogin == 'true' && param.subNav == 'off'}">
			<!--#include virtual="/common/ssi/mypage-after-header-D.html"-->
		</c:when>
		<c:otherwise>
			<!--#include virtual="/common/ssi/simple-header-D.html"-->
		</c:otherwise>
	</c:choose>
