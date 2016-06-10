<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="commentBlock clearfix">
				<div class="img">
					<p>売り主さまの<br class="SPdisplayNone">コメント</p>
				</div>
				<div class="comment">
					<span></span>
					<div class="commentInner">
						<p><c:out value="${outPutForm.getSalesComment()}" escapeXml="false"/></p>
					</div>
				</div>
			</div>