<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

  <div id="topicPath">
   <div id="topicInner">
    <ul class="clearfix">
     <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>">トップ</a></li>
<!--業務パターン設定 -->
<c:choose>
	<c:when test="${param.pattern!=null}">
		<c:set var="pattern1" value="${fn:substring(param.pattern, 0,3)}"/>
		<c:set var="pattern2" value="${fn:substring(param.pattern, 4,6)}"/>
		<c:set var="pattern3" value="${fn:substring(param.pattern, 7,-1)}" />
	</c:when>
	<c:otherwise>
		<c:set var="pattern1" value=""/>
		<c:set var="pattern2" value=""/>
		<c:set var="pattern3" value=""/>
	</c:otherwise>
</c:choose>
<c:set var="mypageUrl" value="${pageContext.request.contextPath}/mypage_service/"/>
<dm3login:hasRole roleName="mypage" negative="false">
	<c:set var="mypageUrl" value="${pageContext.request.contextPath}/mypage/"/>
</dm3login:hasRole>
<c:choose>
	<c:when test="${fn:startsWith(param.pattern, 'BUY')}">
	     <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/#search">買いたい</a></li>
	     <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/#search"><dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/></a></li>
	     <li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/list/"><c:out value="${param.prefName}"/></a></li>

		<c:choose>
			<c:when test="${pattern2==01 || pattern2==02 ||pattern2==03 ||pattern2==07}">
				<c:choose>
					<c:when test="${pattern3==00 || pattern3==10 ||pattern3==20}">
		     			<li class="current"><c:out value="${param.prefName}"/>の<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/>一覧</li>
		     		</c:when>
					<c:when test="${pattern3==02}">
					    <li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/list/"><c:out value="${param.prefName}"/>の<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/>一覧</a></li>
					    <li class="current">市区町村を絞り込む</li>
		     	    </c:when>
					<c:when test="${pattern3==03}">
					    <li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/list/"><c:out value="${param.prefName}"/>の<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/>一覧</a></li>
					    <li class="current">沿線を絞り込む</li>
					</c:when>
					<c:when test="${pattern3==05}">
					    <li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/list/"><c:out value="${param.prefName}"/>の<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/>一覧</a></li>
					    <li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/route/">沿線を絞り込む</a></li>
					    <li class="current">駅を選択する</li>
					</c:when>
				</c:choose>
			</c:when>

			<c:when test="${pattern2==04 || pattern2==05 ||pattern2==08}">
	     		<li><a href="<c:out value="${pageContext.request.contextPath}"/>/buy/<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_En" lookupKey="${param.housingKindCd}"/>/<c:out value="${param.prefCd}"/>/list/"><c:out value="${param.prefName}"/>の<dm3lookup:lookup lookupName="buildingInfo_housingKindCd_front_icon" lookupKey="${param.housingKindCd}"/>一覧</a></li>
	     		<li class="current"><c:out value="${param.housingName}"/></li>
	     	</c:when>
	     </c:choose>
	</c:when>

	<c:when test="${fn:startsWith(param.pattern, 'INQ')}">
    	<li class="current">物件お問い合わせ</li>
    </c:when>

	<c:when test="${fn:startsWith(param.pattern, 'SPE')}">
		<c:choose>
			<c:when test="${pattern2==00}">
			    <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/#search">買いたい</a></li>
			    <li class="current">特集一覧</li>
			</c:when>
			<c:when test="${pattern2==01}">
			    <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/#search">買いたい</a></li>
			    <li><a href="<c:out value="${commonParameters.commonResourceRootUrl}"/>buy/special/">住まいの特集</a></li>
			    <li class="current"><c:out value="${param.featureName}"/></li>
			</c:when>
		</c:choose>
	</c:when>

	<c:when test="${fn:startsWith(param.pattern, 'MYP')}">
		<c:choose>
			<c:when test="${pattern2==00}">
			    <li class="current">マイページ</li>
			</c:when>
			<c:when test="${pattern2==01}">
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">お客様へのお知らせ</li>
			</c:when>
			<c:when test="${pattern2==02}">
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">ログイン</li>
			</c:when>
			<c:when test="${pattern2==03}">
			    <c:choose>
			    	<c:when test="${pattern3==00 || pattern3==04}">
			    		<li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    		<li class="current">パスワードのお問い合わせ</li>
			    	</c:when>
			    	<c:when test="${pattern3==02 || pattern3==03}">
			    		<li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    		<li class="current">パスワードの再設定</li>
			    	</c:when>
			    </c:choose>
			</c:when>
			<c:when test="${pattern2==04}">
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">お客様情報の変更</li>
			</c:when>
		</c:choose>
	</c:when>

	<c:when test="${fn:startsWith(param.pattern, 'TOO')}">
		<c:choose>
			<c:when test="${pattern2==01}">
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">お気に入り物件一覧</li>
			</c:when>
			<c:when test="${pattern2==02}">
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">最近見た物件一覧</li>
			</c:when>
		</c:choose>
	</c:when>

	<c:when test="${fn:startsWith(param.pattern, 'REQ')}">
		<c:choose>
			<c:when test="${pattern3==04}">
			    <li class="current">会員ログイン</li>
			</c:when>
			<c:otherwise>
			    <li><a href="<c:out value="${mypageUrl}"/>">マイページ</a></li>
			    <li class="current">物件リクエスト</li>
			</c:otherwise>
		</c:choose>
	</c:when>

</c:choose>
    </ul>
   </div>
  </div>
