<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="newsDetail">
	<div class="newsHeader">
		<div class="imgAvatar">
			<a> <img src="/img/index_pic_03.jpg" width="40px" height="40px">
			</a>
		</div>
		<div class="newsTitle">
			<b>${news.newsTitle}</b>
		</div>
	</div>
	<div class="newsDate">
		<i><c:out value="${news.updDate}" /></i>
	</div>
	<div class="newsContent">
		<p>${news.newsContent}</p>
	</div>
</div>
<div class="userOfNews">
	<p style="color: #92DAAF;">
		Write by: <c:out value="${userAddNews}" />
	</p>
</div>