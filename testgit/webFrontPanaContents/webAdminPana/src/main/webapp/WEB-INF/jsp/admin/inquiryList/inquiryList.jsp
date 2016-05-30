<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<%-- ----------------------------------------------------------------
 名称： 問合一覧画面

 2015/03/11		Ma.Shuangshuang	新規作成
 2015/08/20     Vinh.Ly Add CSRF Token for Inquiry Delete
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="問合一覧画面" />
<c:param name="contents">
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">
<!--
	function csvOut(url) {
		var action = document.searchForm.action;
		document.searchForm.action = url;
		document.searchForm.target = "_blank";
		document.searchForm.submit();
		document.searchForm.action = action;
		document.searchForm.target = "_self";
	}
	function newlist(url){
   		document.inputdate.action = url;
   		document.inputdate.submit();
	}
	function linkToUpd(url, inquiryId) {
		document.inputdate.action = url;
		document.inputdate.inquiryId.value = inquiryId;
		document.inputdate.command.value = 'update';
		document.inputdate.submit();
	}
	function linkToDel(url, inquiryId) {
		var returenValue = window.confirm("削除してもよろしいですか？")
		if (returenValue == true) {
			document.inputdate.action = url;
			document.inputdate.inquiryId.value = inquiryId;
			document.inputdate.command.value = 'delete';
			document.inputdate.submit();
		}
	}
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
    function popupMember(page) {
    	var url = '../../mypage/search/';
    	window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
    }

    $(document).ready(function(){

		$("input[name=keyInquiryDtlType]").each(function(index){
			$(this).attr("disabled","disabled");
		});

    	// 汎用問合せの初期化設定
    	$("input[name=keyInquiryType][type=checkbox]").each(function(index){
    		if("01" == $(this).val()) {
    			if($(this).attr("checked") == "checked") {
	    			$("input[name=keyInquiryDtlType]").each(function(index,keyInquiryDtlType){
	    				$(keyInquiryDtlType).removeAttr("disabled");
	    			});
    			}

    			// 汎用問合せをクリックする
    			$(this).click(function(){
    				$("input[name=keyInquiryType][type=checkbox]").each(function(index,keyInquiryType){
    					if("01" == $(keyInquiryType).val()) {
    						if($(keyInquiryType).attr("checked") == "checked") {
    							$("input[name=keyInquiryDtlType]").each(function(index,keyInquiryDtlType){
    								$(keyInquiryDtlType).attr("disabled","disabled");
    								$(keyInquiryDtlType).val("");
    							});
    							$(keyInquiryType).removeAttr("checked");
    						} else {
    							$("input[name=keyInquiryDtlType]").each(function(index,keyInquiryDtlType){
    								$(keyInquiryDtlType).removeAttr("disabled");
    							});
    							$(keyInquiryType).attr("checked","checked");
    						}
    					}
    				});
    			});
    		}
    	});
    });


// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>問合の検索</h2>
		</div>
		<form action="../list/" method="post" name="searchForm">
	    <input type="hidden" name="command" value="list"/>
		<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" style="width: 18%">物件番号</th>
					<td><input name="keyHousingCd" value="<c:out value="${searchForm.keyHousingCd}"/>" type="text" size="12" maxlength="10" class="input2 ime-disabled"></td>
					<th class="head_tr" style="width: 18%">物件名</th>
					<td nowrap="nowrap"><input name="keyDisplayHousingName"  value="<c:out value="${searchForm.keyDisplayHousingName}"/>" type="text" size="12" maxlength="25" class="input2">を含む</td>
				</tr>
				<tr>
					<th class="head_tr">会員番号</th>
					<td>
						<div class="inputStyle inputStyle2">
							<input id="userId" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>" type="text" size="10" maxlength="20" class="input2 ime-disabled">
						</div>
					    <!--btnBlockC11 -->
					    <div class="btnBlockC11">
					        <div class="btnBlockC11Inner">
					            <div class="btnBlockC11Inner2">
										<p><a class= "delBtn" href="javascript:popupMember();"><span>参照</span></a></p>
								</div>
							</div>
						</div>
					</td>
					<th class="head_tr">メールアドレス</th>
					<td><input name="keyEmail"  value="<c:out value="${searchForm.keyEmail}"/>" type="text" size="12" maxlength="255" class="input2 ime-disabled">を含む</td>
				</tr>
				<tr>
					<th class="head_tr">問合日時</th>
					<td colspan="3">
						<input name="keyInquiryDateStart" value="<c:out value="${searchForm.keyInquiryDateStart}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiryList.search.inquiryDateStart" defaultValue="10"/>" class="input2 ime-disabled">
						～
						<input name="keyInquiryDateEnd" value="<c:out value="${searchForm.keyInquiryDateEnd}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiryList.search.inquiryDateEnd" defaultValue="10"/>" class="input2 ime-disabled">
						(yyyy/mm/dd)
					</td>
				</tr>
				<tr rowspan="3">
					<th class="head_tr">問合種別</th>
					<td colspan="3">
						<dm3lookup:lookupForEach lookupName="inquiry_type">
							<c:forEach items="${searchForm.keyInquiryType}" var="keyInquiryType">
								<c:choose>
									<c:when test="${keyInquiryType == key}">
										<c:set var="chkValue" value="true"/>
									</c:when>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${chkValue == true}">
									<label><input type="checkBox" name="keyInquiryType" value="<c:out value="${key}"/>" checked/><c:out value="${value}"/></label>&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
									<label><input type="checkBox" name="keyInquiryType" value="<c:out value="${key}"/>" /><c:out value="${value}"/></label>&nbsp;&nbsp;
								</c:otherwise>
							</c:choose>
							<c:set var="chkValue" value="false"/>
						</dm3lookup:lookupForEach>
						<br><br>
						（汎用問合せの場合）
						<br>
						<dm3lookup:lookupForEach lookupName="inquiry_dtl_type">
							<c:forEach items="${searchForm.keyInquiryDtlType}" var="keyInquiryDtlType">
								<c:choose>
									<c:when test="${keyInquiryDtlType == key}">
										<c:set var="chkValue" value="true"/>
									</c:when>
								</c:choose>
							</c:forEach>
							<c:choose>
								<c:when test="${chkValue == true}">
									<label><input type="checkBox" name="keyInquiryDtlType" value="<c:out value="${key}"/>" checked/><c:out value="${value}"/></label>&nbsp;&nbsp;
								</c:when>
								<c:otherwise>
									<label><input type="checkBox" name="keyInquiryDtlType" value="<c:out value="${key}"/>" /><c:out value="${value}"/></label>&nbsp;&nbsp;
								</c:otherwise>
							</c:choose>
							<c:set var="chkValue" value="false"/>
						</dm3lookup:lookupForEach>
					</td>
				</tr>
				<tr>
					<th class="head_tr">ステータス</th>
					<td>
						<select name="keyAnswerStatus">
							<option value=""></option>
						<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
							<option value="<c:out value="${key}"/>" <c:if test="${searchForm.keyAnswerStatus == key}">selected</c:if>><c:out value="${value}"/></option>
						</dm3lookup:lookupForEach>
						</select>
					</td>
					<th class="head_tr">問合番号</th>
					<td><input name="keyInquiryId"  value="<c:out value="${searchForm.keyInquiryId}"/>" type="text" size="12" maxlength="13" class="input2 ime-disabled"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div class="flexBlockB06Inner clear">
			<!--btnBlockC06 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:document.searchForm.submit();"><span>検索</span></a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<!--/btnBlockC06 -->
	</div>
	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div class="flexBlockB06Inner clear">
			<dm3login:hasRole roleName="admin">
				<c:choose>
					<c:when test="${hitcont != 0 && hitcont != null}">
						<div class="btnBlockC14">
						<div class="btnBlockC14Inner">
							<div class="btnBlockC14Inner2">
								<p>
									<a href="javascript:csvOut('../csv/');"><span>CSV出力</span></a>
								</p>
							</div>
						</div>
					</div>
					</c:when>
					<c:otherwise>
						<div class="btnBlockC14">
						<div class="btnBlockC14Inner">
							<div class="btnBlockC14Inner2">
								<p>
									<a disabled="disabled"><span>CSV 出力</span></a>
								</p>
							</div>
						</div>
					</div>
					</c:otherwise>
				</c:choose>
			</dm3login:hasRole>
		</div>
	</div>

	<br>
	<!--/flexBlockB06 -->

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください</span></c:if>
	</p></div><br>


	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
		<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>問合一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
				<table border="0" width="100%">
				<tr>
					<td>
						<c:set var="strBefore" value="javascript:KeyForm('" scope="request"/>
						<c:set var="strAfter" value="')" scope="request"/>
						<c:set var="pagingForm" value="${searchForm}" scope="request"/>
						<c:import url="/WEB-INF/admin/default_jsp/include/pagingCnt.jsh" />
						&nbsp;&nbsp;&nbsp;
						<c:import url="/WEB-INF/admin/default_jsp/include/pagingjs.jsh" />
					</td>
				</tr>
				</table>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr class="head_tr">
					<td width="16%">問合番号</td>
					<td width="16%">種別</td>
					<td >物件名</td>
					<td >問合日時</td>
					<td width="12%">ステータス</td>
					<td width="18%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="inquiryList">
				<c:set var="inquiryHeader" value="${inquiryList.items['inquiry']}"/>
				<tr>
					<td><a href="javascript:linkToUpd('../status/input/','<c:out value="${inquiryHeader.inquiryId}"/>')"><c:out value="${inquiryHeader.inquiryId}"/></a>&nbsp;</td>
					<td>
						<dm3lookup:lookupForEach lookupName="inquiry_type">
							<c:if test="${inquiryHeader.inquiryType == key}">
								<c:out value="${value}"/>&nbsp;
							</c:if>
						</dm3lookup:lookupForEach>
					</td>
					<td><c:out value="${inquiryHeader.displayHousingName}"/>&nbsp;</td>
					<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${inquiryHeader.inquiryDate}"/>&nbsp;</td>
					<td>
						<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
							<c:if test="${inquiryHeader.answerStatus == key}">
								<c:out value="${value}"/>&nbsp;
							</c:if>
						</dm3lookup:lookupForEach>
					</td>
 					<td>
 						<div class="btnBlockC11">
							<div class="btnBlockC11Inner">
								<div class="btnBlockC11Inner2">
										<p><a class="tblSmallBtn" href="javascript:linkToUpd('../status/input/','<c:out value="${inquiryHeader.inquiryId}"/>');"><span>編集</span></a></p>
								</div>
							</div>
						</div>
						<dm3login:hasRole roleName="admin">
						<div class="btnBlockC12">
							<div class="btnBlockC12Inner">
								<div class="btnBlockC12Inner2">
										<p><a class="tblSmallBtn" href="javascript:linkToDel('../delete/','<c:out value="${inquiryHeader.inquiryId}"/>');"><span>削除</span></a></p>
								</div>
							</div>
						</div>
						</dm3login:hasRole>
					</td>
				</tr>
		</c:forEach>
			</table>
		</div>
		<!--/flexBlockA01 -->
		</c:if>

	</div>
	<%-- 入力formパラメータ引き継ぎ --%>
	<form action="" method="post" name="inputdate">
	<input type="hidden" name="inquiryId" value="">
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/inquiryList/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>