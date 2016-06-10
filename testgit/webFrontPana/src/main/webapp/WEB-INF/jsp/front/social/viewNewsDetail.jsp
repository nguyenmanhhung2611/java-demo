<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="<c:out value="${commonParameters.defaultKeyword}"/>">
<meta name="description" content="<c:out value="${commonParameters.defaultDescription}"/>">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>Social</title>

<link href="<c:out value='${commonParameters.resourceRootUrl}'/>mypage/css/mypage.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/common.css" rel="stylesheet">
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/header_footer.css" rel="stylesheet">

<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/main.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.fancybox.pack.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.tooltipster.min.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/html5.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>common/js/jquery.heightLine.js"></script>

<script type="text/javascript">
	<!-- 
		function KeyForm(page) {
			document.tempForm.action = '';
			document.tempForm.selectedPage.value = page;
			document.tempForm.submit();
		}
		
		function linkToAddComment(url, newsId) {
			document.addCommentForm.action = url;
			document.addCommentForm.newsId.value = newsId;
			document.addCommentForm.submit();
		}
	// -->
</script>

<style>
	.newsDetail {
		width: 100%;
		min-height: 200px;
	}
	
	.newsHeader {
		width: 100%;
		height: 40px;
		background: greenyellow;
    	padding: 5px;
	}
	
	.imgAvatar {
		width: 40px;
		height: 40px;
		float: left;
	}
	
	.newsTitle {
		margin-left: 45px;
	}
	
	.newsDate {
		background: inherit;
   	 	width: 100%;
    	text-align: right;
    	padding: 5px;
	}
	
	.newsContent {
    	background: darkgray;
    	min-height: 150px;
    	width: 100%;
    	padding: 5px;
    }
    
    .userOfNews {
    	margin-top: 5px;
    	width: 100%;
    	text-align: right;
    	background: darkgray;
    	padding: 5px;
    }
    
    .commentItem {
    	margin-top: 10px;
	    width: 100%;
	    padding: 5px;
	    background: antiquewhite;
    }
    
    .imgAvatarComment {
    	width: 30px;
		height: 30px;
		float: left;
    }
    
    .commentContent {
        margin-left: 35px;
    }
    
    .clickDetail {
    	width: 100%;
    	text-align: right;
    }
    
    .footerComment {
    	width: 100%;
    	text-align: right;
    	margin-top: 5px;
    }
    
    .paging {
		float: right;
	}
</style>
</head>
<body>
<c:import url="/WEB-INF/jsp/front/include/common/google_analytics.jsh" />
<c:import url="/WEB-INF/jsp/front/include/common/header.jsh?subNav=off" />
<div id="ptop"></div>

<div id="contents">
	<div id="contentsInner" class="mypageInfo">
		<div id="topicPath">
			<div id="topicInner">
				<ul class="clearfix">
					<li><a href="<c:out value='${commonParameters.resourceRootUrl}'/>">トップ</a></li>
					<li><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/">マイページ</a></li>
				</ul>
			</div>
		</div>

		<div class="section01 clearfix">
			<c:import url="/WEB-INF/jsp/front/include/mypageTop/sideBlock.jsh" />

			<div class="mainBlock">
				<c:if test="${news == null}"> <span class="errorMessage">Data is empty!</span></c:if>
				<c:if test="${news != null}">
					<c:import url="/WEB-INF/jsp/front/include/social/detailNews.jsh" />
					<div style="float:right; margin-top: 5px;">
						<p style="float:left; color: #92DAAF;">User: ${userLogin.memberLnameKana} ${userLogin.memberFnameKana}</p>&nbsp;
						<button onclick="javascript:linkToAddComment('../addComment/input/','<c:out value="${news.newsId}"/>')">Add Comment</button>
					</div>
					<c:import url="/WEB-INF/jsp/front/include/social/listComment.jsh" />
				</c:if>
			</div>
		</div>

		<div class="bnrBlock">
			<!--#include virtual="/common/ssi/column.html"-->
		</div>
	</div>
	<c:if test="${news != null}">
		<form action="" method="post" name="tempForm">
			<c:import url="/WEB-INF/jsp/front/include/social/searchParam.jsh" />
			<dm3token:oneTimeToken/>
		</form>
		<form action="" method="post" name="addCommentForm">
			<input type="hidden" name="newsId" value="<c:out value="${news.newsId}"/>">
			<input type="hidden" name="keyNewsId" value="<c:out value="${news.newsId}"/>">
		</form>
	</c:if>
</div>
<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>