<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

			<div class="tabBlock">
				<ul class="tabMenu clearfix">
					<li class="current"><a href="#"><span>リフォーム後</span></a></li>
					<li><a href="#"><span>リフォーム前</span></a></li>
				</ul>
				<div class="sliderBlock clearfix" id="before">
					<div class="slider">
						<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="0" end="0">
							<c:if test="${!empty varImgNoHidden}">
								<p class="imageBlock"><a class="sliderImgLink" href="<c:out value="${outPutForm.getBeforePath2()[varImgNoHidden]}"/>" target="_blank"><img src="<c:out value="${outPutForm.getBeforePath2()[varImgNoHidden]}"/>" alt=""></a></p>
								<div class="youtubeBlock"><iframe src="" frameborder="0" allowfullscreen></iframe></div>
							</c:if>
						</c:forEach>

						<c:if test="${empty outPutForm.getBeforePathNoHidden() && !empty outPutForm.getBeforeMovieUrl()}">
							<p class="imageBlock" style="display: none;"><a class="sliderImgLink fancybox.iframe" href="<c:out value="${outPutForm.getBeforeMovieUrl()}"/>" target="_blank" title=""><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt="" style="opacity: 1;"></a></p>
							<div class="youtubeBlock" style="display: block;"><iframe src="<c:out value="${outPutForm.getBeforeMovieUrl()}"/>" frameborder="0" allowfullscreen=""></iframe></div>
						</c:if>

						<p class="caption"><c:out value="${outPutForm.getBeforePathComment()[0]}"/></p>
						<p class="start-stop"><a href="#" class="play">スライドショーで見る</a></p>
						<p class="arrNext"><a href="#">next</a></p>
						<p class="arrPrev"><a href="#">prev</a></p>
					</div>
					<div class="thumbs">
						<p class="arrNext"><a href="#">next</a></p>
						<div class="thumbsList">
							<ul>
								<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="0" end="0">
									<c:if test="${!empty varImgNoHidden}">
										<li class="current"><a class="" href="<c:out value="${outPutForm.getBeforePath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getBeforePathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getBeforePath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>

								<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="1" end="1">
									<c:if test="${!empty varImgNoHidden}">
										<li><a href="<c:out value="${outPutForm.getBeforePath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getBeforePathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getBeforePath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>

								<c:if test="${!empty outPutForm.getBeforeMovieUrl()}">
									<li <c:if test="${empty outPutForm.getBeforePathNoHidden()}">class="current"</c:if>><a href="<c:out value="${outPutForm.getBeforeMovieUrl()}"/>" data-fancybox-group="slider1" title="" class="youtubeThumb"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt=""></a></li>
								</c:if>

								<c:forEach var="varImgNoHidden" items="${outPutForm.getBeforePathNoHidden()}" begin="2">
									<c:if test="${!empty varImgNoHidden}">
										<li><a href="<c:out value="${outPutForm.getBeforePath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getBeforePathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getBeforePath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<p class="arrPrev"><a href="#">prev</a></p>

					</div>
				</div>
				<div class="sliderBlock clearfix" id="after">
					<div class="slider">
						<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="0" end="0">
							<c:if test="${!empty varImgNoHidden}">
								<p class="imageBlock"><a class="sliderImgLink" href="<c:out value="${outPutForm.getAfterPath2()[varImgNoHidden]}"/>" target="_blank"><img src="<c:out value="${outPutForm.getAfterPath2()[varImgNoHidden]}"/>" alt=""></a></p>
								<div class="youtubeBlock"><iframe src="" frameborder="0" allowfullscreen></iframe></div>
							</c:if>
						</c:forEach>

						<c:if test="${empty outPutForm.getBeforePathNoHidden() && !empty outPutForm.getAfterMovieUrl()}">
							<p class="imageBlock" style="display: none;"><a class="sliderImgLink fancybox.iframe" href="<c:out value="${outPutForm.getAfterMovieUrl()}"/>" target="_blank" title=""><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt="" style="opacity: 1;"></a></p>
							<div class="youtubeBlock" style="display: block;"><iframe src="<c:out value="${outPutForm.getAfterMovieUrl()}"/>" frameborder="0" allowfullscreen=""></iframe></div>
						</c:if>

						<p class="caption"><c:out value="${outPutForm.getAfterPathComment()[0]}"/></p>
						<p class="start-stop"><a href="#" class="play">スライドショーで見る</a></p>
						<p class="arrNext"><a href="#">next</a></p>
						<p class="arrPrev"><a href="#">prev</a></p>
					</div>
					<div class="thumbs">
						<p class="arrNext"><a href="#">next</a></p>
						<div class="thumbsList">
							<ul>
								<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="0" end="0">
									<c:if test="${!empty varImgNoHidden}">
										<li class="current"><a class="" href="<c:out value="${outPutForm.getAfterPath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getAfterPathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getAfterPath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>

								<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="1" end="1">
									<c:if test="${!empty varImgNoHidden}">
										<li><a href="<c:out value="${outPutForm.getAfterPath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getAfterPathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getAfterPath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>

								<c:if test="${!empty outPutForm.getAfterMovieUrl()}">
									<li <c:if test="${empty outPutForm.getAfterPathNoHidden()}">class="current"</c:if>><a href="<c:out value="${outPutForm.getAfterMovieUrl()}"/>" data-fancybox-group="slider1" title="" class="youtubeThumb"><img src="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/img/buy_slider_thumb_03.jpg" alt=""></a></li>
								</c:if>

								<c:forEach var="varImgNoHidden" items="${outPutForm.getAfterPathNoHidden()}" begin="2">
									<c:if test="${!empty varImgNoHidden}">
										<li><a href="<c:out value="${outPutForm.getAfterPath2()[varImgNoHidden]}"/>" data-fancybox-group="slider1" title="<c:out value="${outPutForm.getAfterPathComment()[varImgNoHidden]}"/>"><img src="<c:out value="${outPutForm.getAfterPath1()[varImgNoHidden]}"/>" alt=""></a></li>
									</c:if>
								</c:forEach>
							</ul>
						</div>
						<p class="arrPrev"><a href="#">prev</a></p>

					</div>
				</div>
			</div>