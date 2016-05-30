<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： お知らせ一覧画面

 2015/02/06		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
	function newlist(url){
   		document.inputdate.action = url;
   		document.inputdate.submit();
	}
	function linkToUpd(url, informationNo) {
		document.inputdate.action = url;
		document.inputdate.informationNo.value = informationNo;
		document.inputdate.command.value = 'update';
		document.inputdate.submit();
	}
	function linkToDel(url, informationNo) {
		document.inputdate.action = url;
		document.inputdate.informationNo.value = informationNo;
		document.inputdate.command.value = 'delete';
		document.inputdate.submit();
	}
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
	function linkTo(url) {
   		document.informationform.action = url;
   		document.informationform.submit();
	}
	function openMemberSearch() {
		var child=window.open("<c:out value="${pageContext.request.contextPath}"/>/top/mypage/search/","list","height=500,width=750,status=yes,toolbar=no,menubar=no,location=no,scrollbars=yes,left=200");
	}
// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>お知らせの検索</h2>
		</div>
		<form action="./" method="post" name="informationform">
	    <input type="hidden" name="command" value="list"/>
	    <input type="hidden" name="informationNo" value=""/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="searchBox">
				<tr>
					<th class="head_tr">お知らせ番号</th>
					<td><input name="keyInformationNo" value="<c:out value="${searchForm.keyInformationNo}"/>" type="text" size="18" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.informationNo" defaultValue="13"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">タイトル</th>
					<td><input name="keyTitle"  value="<c:out value="${searchForm.keyTitle}"/>" type="text" size="25" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.title" defaultValue="200"/>" class="input2">を含む</td>
				</tr>
				<tr>
					<th class="head_tr">登録日</th>
					<td><input name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.insDateFrom" defaultValue="10"/>" class="input2"> ～ <input name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.insDateTo" defaultValue="10"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">対象会員</th>
					<td>
						<div style="float:left;">
							<input id="inputUserId" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>" type="text" size="15" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.userId" defaultValue="20"/>" class="input2">&nbsp;&nbsp;
						</div>
						<div class="btnBlockMemSrch" style="float:left;">
							<div class="btnBlockMemSrchInner">
								<div class="btnBlockMemSrchInner2">
									<p id="mkPwd"><a href="javascript:openMemberSearch()"><span>参照</span></a></p>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!--flexBlockList -->
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
	<br/>
	<br/>
	<div class="flexBlockList">
		<div class="flexBlockListInner">
			<div class="btnBlockAdd">
				<div class="btnBlockAddInner">
					<div class="btnBlockAddInner2">
						<p><a href="javascript:newlist('../new/input/');"><span>新規追加</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/flexBlockB03 -->

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果がありません。</span></c:if>
	</p></div><br>


	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>お知らせ一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
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
					<td width="95">お知らせ番号</td>
					<td width="300">タイトル</td>
					<td width="275">対象会員</td>
					<td width="100">&nbsp;</td>
				</tr>
			<!-- 検索結果画面表示判定 -->
			<c:forEach items="${searchForm.visibleRows}" var="informationList">
				<c:set var="information" value="${informationList.items['information']}"/>
				<c:set var="informationTarget" value="${informationList.items['informationTarget']}"/>
				<c:set var="memberInfo" value="${informationList.items['memberInfo']}"/>
					<tr>
						<td><a href="javascript:linkToUpd('../detail/','<c:out value="${information.informationNo}"/>')"><c:out value="${information.informationNo}"/></a>&nbsp;</td>
						<td><c:out value="${information.title}"/>&nbsp;</td>
						<td>
						<c:if test="${information.dspFlg==0}">
						サイトTOP
						</c:if>
						<c:if test="${information.dspFlg==1}">
						全員
						</c:if>
						<c:if test="${information.dspFlg==2}">
						<c:choose> 
						<c:when test="${memberInfo.memberLnameKana!=null}">
						<c:out value="${memberInfo.memberLnameKana}"/>
						</c:when>
						<c:otherwise>
						退会済み
						</c:otherwise>
						</c:choose>
						</c:if>
						&nbsp;</td>
	 					<td>
	 						<div class="btnBlockListEdit">
								<div class="btnBlockListEditInner">
									<div class="btnBlockListEditInner2">
										<p><a href="javascript:linkToUpd('../update/input/','<c:out value="${information.informationNo}"/>');"><span>編集</span></a></p>
									</div>
								</div>
							</div>
	 						<div class="btnBlockListDel">
								<div class="btnBlockListDelInner">
									<div class="btnBlockListDelInner2">
										<p><a href="javascript:linkToDel('../delete/confirm/','<c:out value="${information.informationNo}"/>');"><span>削除</span></a></p>
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
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
	<input type="hidden" name="informationNo" value=""/>
	<input type="hidden" name="title" value=""/>
	<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />
	</form>

</c:param>
</c:import>