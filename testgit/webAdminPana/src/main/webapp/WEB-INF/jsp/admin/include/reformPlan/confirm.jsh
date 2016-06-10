<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%--
リフォーム情報編集機能で使用する入力確認画面の出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" >
<!--flexBlockA01 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr" width="20%">物件番号</th>
			<td width="20%"><c:out value="${inputForm.housingCd}"/></td>
			<th class="head_tr" width="20%">物件名称</th>
			<td width="40%"><c:out value="${inputForm.displayHousingName}"/></td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">公開区分</th>
			<td colspan="3" width="80%">
            	<dm3lookup:lookupForEach lookupName="hiddenFlg">
					<c:if test="${inputForm.hiddenFlg == key}"><c:out value="${value}"/></c:if>
            	</dm3lookup:lookupForEach>
			</td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">リフォームプラン名</th>
			<td colspan="3" width="80%"><c:out value="${inputForm.planName}"/></td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">価格</th>
			<td  align="right" width="20%"><c:if test="${ !empty inputForm.planPrice}"><fmt:formatNumber value="${inputForm.planPrice}" pattern="###,###" />円</c:if>&nbsp;</td>
			<th class="head_tr" width="20%">工期</th>
			<td width="40%"><c:out value="${inputForm.constructionPeriod}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">セールスポイント<br>(コンセプト)</th>
			<td colspan="3" width="80%"><c:out value="${inputForm.salesPoint1}" escapeXml="false"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr" width="20%">備考</th>
			<td colspan="3" width="80%"><c:out value="${inputForm.note1}" escapeXml="false"/>&nbsp;</td>
		</tr>
        <tr>
            <th class="head_tr" width="20%">カテゴリ1</th>
            <td width="30%">
                <c:if test="${inputForm.planCategory1 != null}">
                    <c:forEach var="category1" items="${inputForm.supperCategories}">
                        <c:if test="${category1.id == inputForm.planCategory1}">
                            <c:out value="${category1.name}"/>
                        </c:if>
                    </c:forEach>
                </c:if>
            </td>
            <th class="head_tr" width="20%">カテゴリ2</th>
            <td width="30%">
                <c:if test="${inputForm.planCategory2 != null}">
                    <c:forEach var="category1" items="${inputForm.supperCategories}">
                        <c:if test="${category1.id == inputForm.planCategory1}">
                            <c:forEach items="${category1.children}" var="category2">
                                <c:if test="${category2.id == inputForm.planCategory2}">
                                  <c:out value="${category2.name}"/>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                </c:if>
            </td>
        </tr>
	</table>

	<c:if test="${inputForm.housingKindCd == '01' || inputForm.housingKindCd == '02'}">
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">

			<c:set var="index" value="0" />
			<c:if test="${inputForm.housingKindCd == '01'}">
						<dm3lookup:lookupForEach lookupName="inspectionTrustMansion">

								<c:set	 var="radioName" value="${key}" />
								<input type="hidden" name="chartKey" value="<c:out value="${inputForm.chartKey[index]}"/>" />
								<input type="hidden" name="chartValue" value="<c:out value="${inputForm.chartValue[index]}"/>" />
								<th class="head_tr" width="22%"><c:out value="${value}" />　評価基準</th>

								<td width="28%">
									<dm3lookup:lookupForEach lookupName="inspectionResult">
										<c:if test="${inputForm.chartValue[index] == key}"><c:out value="${value}" /></c:if>
									</dm3lookup:lookupForEach>
								</td>
							<c:if test="${key % 2 == 0}"> </tr><tr></c:if>

							<c:set var="index" value="${ index +1  }" />

						</dm3lookup:lookupForEach>
			</c:if>

			<c:if test="${inputForm.housingKindCd == '02'}">
	                	<dm3lookup:lookupForEach lookupName="inspectionTrustHouse">

								<c:set	 var="radioName" value="${key}" />
								<input type="hidden" name="chartKey" value="<c:out value="${inputForm.chartKey[index]}"/>" />
								<input type="hidden" name="chartValue" value="<c:out value="${inputForm.chartValue[index]}"/>" />
								<th class="head_tr" width="22%"><c:out value="${value}" />　評価基準</th>

								<td width="28%">
									<dm3lookup:lookupForEach lookupName="inspectionResult">
										<c:if test="${inputForm.chartValue[index] == key}"><c:out value="${value}" /></c:if>
									</dm3lookup:lookupForEach>
								</td>
							<c:if test="${key % 2 == 0}"> </tr><tr></c:if>

							<c:set var="index" value="${ index +1  }" />

						</dm3lookup:lookupForEach>
			</c:if>

			<tr>
				<th class="head_tr" width="22%">アプロード画像</th>
				<td colspan="3" width="78%">
				<c:if test="${inputForm.imgFile1 != ''}">
				<c:if test="${inputForm.reformImgDel != 'on'}">
					<a id="demo2" href="<c:out value="${inputForm.imgFile1}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" alt="" style="vertical-align:middle;"/></a>
				</c:if>
				</c:if>&nbsp;
				</td>
			</tr>
		</table>
	</c:if>
	<br>
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<tr>
			<th class="head_tr">&nbsp;&nbsp;</th>
			<th class="head_tr">Before</th>
			<th class="head_tr">After</th>
			<th class="head_tr">閲覧権限</th>
		</tr>

		<tr>
			<th class="head_tr">動画</th>
			<td><c:out value="${inputForm.beforeMovieUrl}"/>&nbsp;</td>
			<td><c:out value="${inputForm.afterMovieUrl}"/>&nbsp;</td>
			<td>
      			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
          			<c:if test="${inputForm.roleId == key}"><c:out value="${value}"/></c:if>
      			</dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
	</table>

</div>
<!--/flexBlockA01 -->
	<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
	<input type="hidden" name="pageId" value="reformPlan">
	<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
	<input type="hidden" name="housingCd" value="<c:out value="${inputForm.housingCd}"/>">
	<input type="hidden" name="housingKindCd" value="<c:out value="${inputForm.housingKindCd}"/>">
	<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>">
	<input type="hidden" name="sysReformCd" value="<c:out value="${inputForm.sysReformCd}"/>">
	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>">
	<input type="hidden" name="planName" value="<c:out value="${inputForm.planName}"/>">
	<input type="hidden" name="planPrice" value="<c:out value="${inputForm.planPrice}"/>">
	<input type="hidden" name="salesPoint" value="<c:out value="${inputForm.salesPoint}"/>">
	<input type="hidden" name="constructionPeriod" value="<c:out value="${inputForm.constructionPeriod}"/>">
	<input type="hidden" name="note" value="<c:out value="${inputForm.note}"/>">
	<input type="hidden" name="beforeMovieUrl" value="<c:out value="${inputForm.beforeMovieUrl}"/>">
	<input type="hidden" name="afterMovieUrl" value="<c:out value="${inputForm.afterMovieUrl}"/>">
	<input type="hidden" name="roleId" value="<c:out value="${inputForm.roleId}"/>">
	<input type="hidden" name="hiddenFlg" value="<c:out value="${inputForm.hiddenFlg}"/>">
	<input type="hidden" name="imgName" value="<c:out value="${inputForm.imgName}"/>">
	<input type="hidden" name="temPath" value="<c:out value="${inputForm.temPath}"/>">
	<input type="hidden" name="imgFile1" value="<c:out value="${inputForm.imgFile1}"/>">
	<input type="hidden" name="imgFile2" value="<c:out value="${inputForm.imgFile2}"/>">
	<input type="hidden" name="reformImgDel" value="<c:out value="${inputForm.reformImgDel}"/>">
	<input type="hidden" name="wkImgFlg" value="<c:out value="${inputForm.wkImgFlg}"/>">
	<input type="hidden" name="dtlFlg" value="<c:out value="${inputForm.dtlFlg}"/>" />
	<input type="hidden" name="imgFlg" value="<c:out value="${inputForm.imgFlg}"/>" />
	<input type="hidden" name="imgSelFlg" value="<c:out value="${inputForm.imgSelFlg}"/>" />
    <input type="hidden" name="planCategory1" value="<c:out value="${inputForm.planCategory1}"/>" />
    <input type="hidden" name="planCategory2" value="<c:out value="${inputForm.planCategory2}"/>" />
	<dm3token:oneTimeToken/>
</form>

<script type ="text/JavaScript">
<!--
	function comp() {
		var frm = document.forms[0];
		frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/result/";
		frm.target = "";
		frm.submit();
	}

	function back() {
		var frm = document.forms[0];
		var cmd = document.forms[0].command.value;

		if (cmd == 'insert') {
			frm.command.value = "iBack";
		} else {
			frm.command.value = "uBack";
		}
		frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/back/";
		frm.target = "";
		frm.submit();
	}

	function popupPreview(url) {
		document.inputForm.action = url;
		document.inputForm.target = "_blank";
		document.inputForm.submit();
	}
	$(function(){
	    $("#demo2").fancybox();
	});
// -->
</script>
