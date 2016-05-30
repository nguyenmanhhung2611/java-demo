<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%--
リフォーム情報編集機能で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<script>
   
</script>
<!--flexBlockA01 -->
<div class="flexBlockA01">
       	<input type="hidden" name="sysHousingCd" value="<c:out value="${inputForm.sysHousingCd}"/>" />
		<input type="hidden" name="sysReformCd" value='<c:out value="${inputForm.sysReformCd}"/>' />
		<input type="hidden" name="housingCd" value='<c:out value="${inputForm.housingCd}"/>' />
		<input type="hidden" name="housingKindCd" value='<c:out value="${inputForm.housingKindCd}"/>' />
       	<input type="hidden" name="updDate" value="<c:out value="${inputForm.updDate}"/>" />
       	<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>" />
		<input type="hidden" name="dtlFlg" value="<c:out value="${inputForm.dtlFlg}"/>" />
		<input type="hidden" name="imgFlg" value="<c:out value="${inputForm.imgFlg}"/>" />
       	<input type="hidden" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>" />
		<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
        
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<tr>
				<th class="head_tr" width="20%">物件番号</th>
				<td width="20%">
					<c:out value="${inputForm.housingCd}"/>&nbsp;&nbsp;
				</td>
				<th class="head_tr" width="20%">物件名称</th>
				<td width="40%">
					<c:out value="${inputForm.displayHousingName}"/>&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">公開区分<font color="red">※</font></th>
				<td colspan="3" width="80%">
        			<dm3lookup:lookupForEach lookupName="hiddenFlg">
            			<label><input type="radio" name="hiddenFlg" value="<c:out value="${key}" />"
						<c:if test="${inputForm.hiddenFlg == key}"> checked</c:if> ><c:out value="${value}"/></label>
                    </dm3lookup:lookupForEach>&nbsp;
				</td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">リフォームプラン名<font color="red">※</font><p style="font-size: 85%">（50文字）</p></th>
				<td colspan="3" width="80%"><input type="text" name="planName" value="<c:out value="${inputForm.planName}"/>" size="45" maxlength="50" class="input2">&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">価格<font color="red">※</font></th>
				<td width="20%"><input type="text" name="planPrice" value="<c:out value="${inputForm.planPrice}"/>" size="6" maxlength="11" class="input2 ime-disabled">円</td>
				<th class="head_tr" width="20%">工期<p style="font-size: 85%">（20文字）</p></th>
				<td width="40%"><input type="text" name="constructionPeriod" value="<c:out value="${inputForm.constructionPeriod}"/>" size="20" maxlength="20"  class="input2">&nbsp;</td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">セールスポイント<br>（コンセプト）<p style="font-size: 85%">（200文字）</p></th>
				<td colspan="3" width="80%"><textarea cols="50" rows="3" name="salesPoint" ><c:out value="${inputForm.salesPoint}"/></textarea></td>
			</tr>
			<tr>
				<th class="head_tr" width="20%">備考<p style="font-size: 85%">（100文字）</p></th>
				<td colspan="3" width="80%"><textarea cols="50" rows="2" name="note" ><c:out value="${inputForm.note}"/></textarea></td>
			</tr>
			<tr>
                <th class="head_tr" width="20%">カテゴリ1</th>
                <td width="30%">
                    <select id="category1" style="width: 95%;" name="planCategory1">
                        <option></option> 
                        <c:forEach var="category1" items="${inputForm.supperCategories}">
                            <option value="<c:out value="${category1.id}"/>" <c:if test="${inputForm.planCategory1 == category1.id}"> selected</c:if>><c:out value="${category1.name}"/></option>
                        </c:forEach>
                    </select>
                </td>
                <th class="head_tr" width="20%">カテゴリ2</th>
                <td width="30%">
                    <select style="width: 95%;" name="planCategory2">
                        <option></option>
                        <c:forEach items="${inputForm.supperCategories}" var="category1">
                            <c:if test="${category1.id == inputForm.planCategory1 }">
                                <c:forEach items="${category1.children}" var="category2">
                                  <option value="<c:out value="${category2.id}"/>" <c:if test="${inputForm.planCategory2 == category2.id}"> selected</c:if>><c:out value="${category2.name}"/></option>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>
		</table>

		<c:if test="${inputForm.housingKindCd == '01' || inputForm.housingKindCd == '02'}">
			<br>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<c:set var="index" value="0" />
					<c:if test="${inputForm.housingKindCd == '01'}">
						<dm3lookup:lookupForEach lookupName="inspectionTrustMansion">

								<input type="hidden" name="chartKey" value="<c:out value="${inputForm.chartKey[index]}"/>" />
								<input type="hidden" name="chartValue" value="<c:out value="${inputForm.chartValue[index]}"/>" />
								<th class="head_tr" width="22%"><c:out value="${value}" />　評価基準</th>

								<td  width="28%">
									<dm3lookup:lookupForEach lookupName="inspectionResult">
									<c:set	 var="radioValue" value="${key}" />
									<label><input type="radio"
											name="chartValue<c:out value="${index}" />"
											value="<c:out value="${key}" />"
											<c:if test="${inputForm.chartValue[index] == key}">checked</c:if>
											onClick = "changeChartValue(<c:out value="${index}" />, <c:out value="${radioValue}" />)">

											<c:out value="${value}" /></label>
									</dm3lookup:lookupForEach>&nbsp;
								</td>
							<c:if test="${key % 2 == 0}"></tr><tr></c:if>
							<c:set var="index" value="${ index +1  }" />
						</dm3lookup:lookupForEach>
						<c:if test="${index % 2 != 0}">
							<th class="head_tr" width="22%">&nbsp;</th><td width="28%">&nbsp;</td></tr>
						</c:if>
					</c:if>

					<c:if test="${inputForm.housingKindCd == '02'}">
						<dm3lookup:lookupForEach lookupName="inspectionTrustHouse">

								<input type="hidden" name="chartKey" value="<c:out value="${inputForm.chartKey[index]}"/>" />
								<input type="hidden" name="chartValue" value="<c:out value="${inputForm.chartValue[index]}"/>" />
								<th class="head_tr" width="22%"><c:out value="${value}" />　評価基準</th>

								<td  width="28%">
									<dm3lookup:lookupForEach lookupName="inspectionResult">
									<c:set	 var="radioValue" value="${key}" />
									<label><input type="radio"
											name="chartValue<c:out value="${index}" />"
											value="<c:out value="${key}" />"
											<c:if test="${inputForm.chartValue[index] == key}">checked</c:if>
											onClick = "changeChartValue(<c:out value="${index}" />, <c:out value="${radioValue}" />)">

											<c:out value="${value}" /></label>
									</dm3lookup:lookupForEach>&nbsp;
								</td>
							<c:if test="${key % 2 == 0}"> </tr><tr></c:if>

							<c:set var="index" value="${ index +1  }" />

						</dm3lookup:lookupForEach>
						<c:if test="${index % 2 != 0}">
							<th class="head_tr" width="22%">&nbsp;</th><td width="28%">&nbsp;</td></tr>
						</c:if>
					</c:if>
				</tr>
				<tr>
					<th class="head_tr" width="22%">レーダーチャート画像</th>
					<td colspan="3" width="78%">
						<c:if test="${inputForm.wkImgFlg == '1'}">
							<p><a id="demo2" href="<c:out value="${inputForm.imgFile1}"/>"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/img_icon.gif" alt="" style="vertical-align:middle;"/></a>
			    	    	<input type="checkBox" name="reformImgDel" <c:if test="${inputForm.reformImgDel == 'on'}">checked="true"</c:if>/>削除する
							<input type="hidden" name="wkImgFlg" value="<c:out value="${inputForm.wkImgFlg}"/>"/>
						</c:if>
						<input type="file" name="reformImgFile"  value=""  style="width: 200px;" class="input2"></p>
						<input type="hidden" name="imgName" value="<c:out value="${inputForm.imgName}"/>"/>
						<input type="hidden" name="temPath" value="<c:out value="${inputForm.temPath}"/>"/>
						<input type="hidden" name="imgFile1" value="<c:out value="${inputForm.imgFile1}"/>"/>
						<input type="hidden" name="imgFile2" value="<c:out value="${inputForm.imgFile2}"/>"/>
						<input type="hidden" name="imgSelFlg" value="<c:out value="${inputForm.imgSelFlg}"/>" />
					</td>
				</tr>
			</table>
		</c:if>
		<br>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
			<colgroup>
				<col width="10%"/>
				<col width="35%"/>
				<col width="35%"/>
				<col width="20%"/>
			</colgroup>
			<tr>
				<th class="head_tr">&nbsp;&nbsp;</th>
				<th class="head_tr">Before</th>
				<th class="head_tr">After</th>
				<th class="head_tr">閲覧権限</th>
			</tr>

			<tr>
				<th class="head_tr">動画</th>
				<td><input type="text" name="beforeMovieUrl" value="<c:out value="${inputForm.beforeMovieUrl}"/>" size="17"  maxlength="255" class="input2"></td>
				<td><input type="text" name="afterMovieUrl" value="<c:out value="${inputForm.afterMovieUrl}"/>" size="17"  maxlength="255" class="input2"></td>
				<td>
	    			<select name="roleId">
	    				<option></option>
	        			<dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
	            			<option name = "roleId" value="<c:out value="${key}"/>" <c:if test="${inputForm.roleId == key}"> selected</c:if>><c:out value="${value}"/></option>
	        			</dm3lookup:lookupForEach>
	    			</select>

				</td>
			</tr>
		</table>

</div>
<!--/flexBlockA01 -->

<script type ="text/JavaScript">
<!--
	$(function(){
	    $("#demo2").fancybox();
	});
// -->
</script>
