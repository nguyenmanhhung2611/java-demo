<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： 問合一覧画面

 2015/04/07		cho.yu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="問合一覧画面" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
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
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
	function linkTo(url){
   		document.inquiryform.action = url;
   		document.inquiryform.submit();
	}
// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>問合の検索</h2>
		</div>
		<form action="./" method="post" name="inquiryform">
	    <input type="hidden" name="command" value="list"/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="searchBox">
				<tr>
					<th class="head_tr">氏名（姓）</th>
					<td><input name="keyLname" value="<c:out value="${searchForm.keyLname}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.lname" defaultValue="30"/>" class="input2">を含む</td>
					<th class="head_tr">氏名（名）</th>
					<td><input name="keyFname"  value="<c:out value="${searchForm.keyFname}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.fname" defaultValue="30"/>" class="input2">を含む</td>
				</tr>
				<tr>
					<th class="head_tr">氏名・カナ（セイ）</th>
					<td>
						<input name="keyLnameKana" value="<c:out value="${searchForm.keyLnameKana}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.lnameKana" defaultValue="30"/>" class="input2" >を含む
					</td>
					<th class="head_tr">氏名・カナ（メイ）</th>
					<td><input name="keyFnameKana"  value="<c:out value="${searchForm.keyFnameKana}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.fnameKana" defaultValue="30"/>" class="input2">を含む</td>
				</tr>
				<tr>
					<th class="head_tr">メールアドレス</th>
					<td colspan="3">
						<input name="keyEmail"  value="<c:out value="${searchForm.keyEmail}"/>" type="text" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.email" defaultValue="255"/>" class="input2">
					</td>
				</tr>
				<tr>
					<th class="head_tr">ステータス</th>
					<td>
						<select name="keyAnswerStatus">
							<option></option>
							<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
								<option value="<c:out value="${key}"/>" <c:if test="${searchForm.keyAnswerStatus == key}">selected</c:if>><c:out value="${value}"/></option>&nbsp;
							</dm3lookup:lookupForEach>
						</select>
					</td>
					<th class="head_tr">電話番号</th>
					<td><input name="keyTel"  value="<c:out value="${searchForm.keyTel}"/>" type="text" size="12" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="inquiry.input.tel" defaultValue="13"/>" class="input2"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!-- flexBlockList -->
	<div class="flexBlockList">
		<div class="flexBlockListInner">
			<div class="btnBlockCsv">
				<div class="btnBlockCsvInner">
					<div class="btnBlockCsvInner2">
						<p><a href="javascript:linkTo('../csv/');"><span>CSV 出力</span></a></p>
					</div>
				</div>
			</div>
		</div>
		<div class="flexBlockListInner">
			<div class="btnBlockSearch">
				<div class="btnBlockSearchInner">
					<div class="btnBlockSearchInner2">
						<p><a href="javascript:linkTo('./');"><span>検索</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockList -->
	<br>
	<br>
	<br>

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果がありません。</span></c:if>
	</p></div><br>


	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>問合一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
<!--
			<table border="0">
-->
			<table>
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
			<table class="searchListBox">
				<tr class="head_tr">
					<td width="95">問合番号</td>
					<td width="220">問合日時</td>
					<td width="355">ステータス</td>
					<td width="100">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
				<c:forEach items="${searchForm.visibleRows}" var="inquiryList">
					<c:set var="inquiryHeader" value="${inquiryList.items['inquiryHeader']}"/>
					<tr>
						<td><a href="javascript:linkToUpd('../detail/','<c:out value="${inquiryHeader.inquiryId}"/>')"><c:out value="${inquiryHeader.inquiryId}"/></a>&nbsp;</td>
						<td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${inquiryHeader.inquiryDate}"/>&nbsp;</td>
						<td>
							<dm3lookup:lookupForEach lookupName="inquiry_answerStatus">
								<c:if test="${inquiryHeader.answerStatus == key}">
									<c:out value="${value}"/>
								</c:if>
							</dm3lookup:lookupForEach>
							&nbsp;
						</td>
						<td>
							<div class="btnBlockListEdit">
								<div class="btnBlockListEditInner">
									<div class="btnBlockListEditInner2">
										<p><a href="javascript:linkToUpd('../status/input/','<c:out value="${inquiryHeader.inquiryId}"/>');"><span>編集</span></a></p>
									</div>
								</div>
							</div>
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
	<input type="hidden" name="command" value="">
	<input type="hidden" name="inquiryId" value="">
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/inquiry/searchParams.jsh" />
	</form>

</c:param>
</c:import>