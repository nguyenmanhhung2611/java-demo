<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${listComment.size() != 0}">
	<div style="margin-top: 35px;">
		<c:forEach items="${searchForm.visibleRows}" var="commentList">
			<c:set var="userComment" value="${commentList.items['user_comment']}" />
			<c:set var="memberInfor" value="${commentList.items['member_info']}" />
			<div class="commentItem">
				<div class="CommentHeader">
					<div class="imgAvatarComment">
						<a> <img src="/img/index_pic_02.jpg" width="30px"
							height="30px">
						</a>
					</div>
					<div class="commentContent">
						<p>${userComment.commentContent}</p>
					</div>
				</div>
				<div class="footerComment">
					<p>
						<i style="color: #92DAAF;">
							<c:out value="${userComment.updDate}" /> - Write by <c:out value="${memberInfor.memberLnameKana}" /> <c:out value="${memberInfor.memberFnameKana}" />
						</i>
					</p>
				</div>
			</div>
		</c:forEach>
	</div>
	<div class="paging">
		<c:set var="strBefore" value="javascript:KeyForm('" scope="request" />
		<c:set var="strAfter" value="')" scope="request" />
		<c:set var="pagingForm" value="${searchForm}" scope="request" />
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingCnt.jsh" />
		&nbsp;&nbsp;&nbsp;
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingjs.jsh" />
	</div>
</c:if>