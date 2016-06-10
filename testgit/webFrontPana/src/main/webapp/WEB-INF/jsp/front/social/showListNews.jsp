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
<link href="<c:out value='${commonParameters.resourceRootUrl}'/>common/css/parts.css" rel="stylesheet">
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
		
		function linkToView(url, newsId) {
			document.viewDetailForm.action = url;
			document.viewDetailForm.keyNewsId.value = newsId;
			document.viewDetailForm.submit();
		}
	// -->
</script>

<style>
	.itemNews {
		width: 100%;
		height: 100px;
		padding: 5px;
	}
	
	.imgAvatar {
		width: 100px;
		height: 100px;
		float: left;
	}
	
	.contentItemNews {
		background: yellowgreen;
    	margin-left: 115px;
		padding: 5px;
		height: 90px;
	}
	
	.titleName {
		float: left;
		font-size: 15px;
	}
	
	.commentOfNews {
		font-size: 15px;
    	float: right;
	}
	
	.dateNews {
		text-align: right;
		padding: 5px;
	}
	
	.textLimit {
		white-space: nowrap;
	    overflow: hidden;
	    text-overflow: ellipsis;
	}
	
	.paging {
		float: right;
	}
	
	.errorMessage {
		display: block;
		color: red;
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
				<c:if test="${listNews.size() == 0}"> <span class="errorMessage">Data is empty!</span></c:if>
				<c:if test="${listNews.size() != 0}">
					<c:forEach items="${searchForm.visibleRows}" var="newsItem">
						<div class="itemNews">
							<div class="imgAvatar">
								<a>
									<img src="/img/index_pic_03.jpg" width="100px" height="100px">
								</a>
							</div>
							<div class="contentItemNews">
								<div class="titleNews">
									<div class="titleName">
										<b>
											<a href="javascript:linkToView('../viewNews/','<c:out value="${newsItem.newsId}"/>')">
												${newsItem.newsTitle}
											</a>
										</b>
									</div>
									<div class="commentOfNews">
										<b>
											<c:if test="${newsItem.count != 0}">
												${newsItem.count} comment
											</c:if>
										</b>
									</div>
								</div>
								<br>
								<div class="dateNews">
									<i><c:out value="${newsItem.updDate}"/></i>
								</div>
								<br>
								<div class="contentNews">
									<p class="textLimit">${newsItem.newsContent}</p>
								</div>
							</div>
						</div>
					</c:forEach>
					<div class="paging">
						<c:set var="strBefore" value="javascript:KeyForm('" scope="request" />
						<c:set var="strAfter" value="')" scope="request" />
						<c:set var="pagingForm" value="${searchForm}" scope="request" />
						<c:import url="/WEB-INF/admin/default_jsp/include/pagingCnt.jsh" />
										&nbsp;&nbsp;&nbsp;
						<c:import url="/WEB-INF/admin/default_jsp/include/pagingjs.jsh" />
					</div>
				</c:if>
				
			</div>
		</div>
		<div class="bnrBlock">
			<!--#include virtual="/common/ssi/column.html"-->
		</div>
	</div>
</div>
<c:if test="${listNews.size() != 0}">
	<form action="" method="post" name="tempForm">
		<c:import url="/WEB-INF/jsp/front/include/social/searchParam.jsh" />
		<dm3token:oneTimeToken/>
	</form>
	<form action="" method="post" name="viewDetailForm">
		<input type="hidden" name="keyNewsId" value="">
	</form>
</c:if>

<!--#include virtual="/common/ssi/footer-S.html"-->
</body>
</html>
