<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<!-- === sideBlock === -->

			<div class="sideBlock">
				<div class="registInfo">
					<p class="name"><c:out value="${memberInfo.memberLname}"/> <c:out value="${memberInfo.memberFname}"/> 様</p>
					<p class="linktext"><a href="<c:out value='${pageContext.request.contextPath}'/>/mypage/member/change/input/">登録情報を変更する</a></p>
					<p class="note">パスワードの定期的な変更をお願いいたします。</p>
				</div>

				<div class="inquiryCounter">
					<p class="title">ご相談窓口</p>
					<p class="text">物件のご購入、売却査定についてなど、まずはお気軽にお問い合わせください。</p>
					<p class="btnBlack01"><a href="<c:out value='${commonParameters.resourceRootUrl}'/>inquiry/">まずはご相談</a></p>
				</div>

			</div>
<!-- /=== sideBlock === -->