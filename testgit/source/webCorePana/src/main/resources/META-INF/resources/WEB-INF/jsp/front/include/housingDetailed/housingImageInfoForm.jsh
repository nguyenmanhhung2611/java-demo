<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="sliderBlock clearfix">
				<div class="slider">
					<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="0" end="0">
						<c:if test="${!empty varImgNoHidden}">
							<p class="imageBlock"><a class="sliderImgLink" href="<c:out value="${outPutForm.getHousingImgPath2Hidden()[varImgNoHidden]}"/>" target="_blank"><img src="<c:out value="${outPutForm.getHousingImgPath2Hidden()[varImgNoHidden]}"/>" alt=""></a></p>
							<div class="youtubeBlock"><iframe src="" frameborder="0" allowfullscreen></iframe></div>
						</c:if>
					</c:forEach>

					<c:if test="${empty outPutForm.getImgNoHidden() && !empty outPutForm.getMovieUrl()}">
						<p class="imageBlock" style="display: none;"><a class="sliderImgLink fancybox.iframe" href="<c:out value="${outPutForm.getMovieUrl()}"/>" target="_blank" title=""><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt="" style="opacity: 1;"></a></p>
						<div class="youtubeBlock" style="display: block;"><iframe src="<c:out value="${outPutForm.getMovieUrl()}"/>" frameborder="0" allowfullscreen=""></iframe></div>
					</c:if>

					<p class="caption"><c:out value="${outPutForm.getHousingImgCommentHidden()[0]}"/></p>
					<p class="arrNext"><a href="#">next</a></p>
					<p class="arrPrev"><a href="#">prev</a></p>
				</div>
				<div class="thumbs">
					<p class="arrNext"><a href="#">next</a></p>
					<div class="thumbsList">
						<ul>
							<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="0" end="0">
								<c:if test="${!empty varImgNoHidden}">
									<li class="current"><a class="" href="<c:out value="${outPutForm.getHousingImgPath2Hidden()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getHousingImgCommentHidden()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getHousingImgPath1Hidden()[varImgNoHidden]}"/>" alt=""></a></li>
								</c:if>
							</c:forEach>

							<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="1" end="1">
								<c:if test="${!empty varImgNoHidden}">
									<li><a href="<c:out value="${outPutForm.getHousingImgPath2Hidden()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getHousingImgCommentHidden()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getHousingImgPath1Hidden()[varImgNoHidden]}"/>" alt=""></a></li>
								</c:if>
							</c:forEach>

							<c:if test="${!empty outPutForm.getMovieUrl()}">
								<li <c:if test="${empty outPutForm.getImgNoHidden()}">class="current"</c:if>><a href="<c:out value="${outPutForm.getMovieUrl()}"/>" data-fancybox-group="slider1" title="" class="youtubeThumb"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt=""></a></li>
							</c:if>

							<c:forEach var="varImgNoHidden" items="${outPutForm.getImgNoHidden()}" begin="2">
								<c:if test="${!empty varImgNoHidden}">
									<li><a href="<c:out value="${outPutForm.getHousingImgPath2Hidden()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getHousingImgCommentHidden()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getHousingImgPath1Hidden()[varImgNoHidden]}"/>" alt=""></a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<p class="arrPrev"><a href="#">prev</a></p>

				</div>
			</div>