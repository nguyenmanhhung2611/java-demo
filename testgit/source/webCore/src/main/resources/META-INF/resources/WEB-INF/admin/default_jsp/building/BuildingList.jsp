<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： 建物情報一覧画面

 2015/02/27		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="建物情報せメンテナンス" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
	function newlist(url){
   		document.inputdate.action = url;
   		document.inputdate.submit();
	}
	function linkToUpd(url, sysBuildingCd) {
		document.inputdate.action = url;
		document.inputdate.sysBuildingCd.value = sysBuildingCd;
		document.inputdate.command.value = 'update';
		document.inputdate.submit();
	}
	function linkToDel(url, sysBuildingCd, title) {
		document.inputdate.action = url;
		document.inputdate.sysBuildingCd.value = sysBuildingCd;
		document.inputdate.command.value = 'delete';
		document.inputdate.submit();
	}
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
	function linkTo(url, sysBuildingCd) {
		document.inputdate.action = url;
		document.inputdate.sysBuildingCd.value = sysBuildingCd;
		document.inputdate.submit();
	}
// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>建物情報の検索</h2>
		</div>
		<form action="./" method="post" name="buildingForm">
	    <input type="hidden" name="command" value="list"/>
	    <input type="hidden" name="sysBuildingCd" value=""/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="searchBox">
				<tr>
					<th class="head_tr">建物番号</th>
					<td><input name="keyBuildingCd" value="<c:out value="${searchForm.keyBuildingCd}"/>" type="text" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.search.keyBuildingCd" defaultValue="20"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">表示用建物名</th>
					<td><input name="keyDisplayBuildingName" value="<c:out value="${searchForm.keyDisplayBuildingName}"/>" type="text" size="20" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="building.search.keyDisplayBuildingName" defaultValue="40"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">所在地・都道府県</th>
					<td>
						<select name="keyPrefCd" >
						<option></option>
						<c:forEach items="${prefMstList}" var="prefMst">
							<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${searchForm.keyPrefCd == prefMst.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
						</c:forEach>
						</select>
					</td>
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
			<div class="btnBlockSearch">
				<div class="btnBlockSearchInner">
					<div class="btnBlockSearchInner2">
						<p><a href="javascript:document.buildingForm.submit();"><span>検索</span></a></p>
					</div>
				</div>
			</div>
		</div>
		<br/>
		<br/>
		<br/>
		<div class="flexBlockListInner">
			<div class="btnBlockAdd">
				<div class="btnBlockAddInner">
					<div class="btnBlockAddInner2">
						<p><a href="javascript:newlist('../info/new/input/');"><span>新規追加</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /flexBlockList -->

	<br/>
	<br/>
	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果がありません。</span></c:if>
	</p></div><br>


	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>建物情報一覧</h2>
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
					<td width="200">建物番号</td>
					<td width="390">表示用建物名</td>
					<td width="80">所在地<br>都道府県</td>
					<td width="100">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="buildingInfoList">
			<c:set var="buildingInfo" value="${buildingInfoList.items['buildingInfo']}"/>
			<c:set var="prefMst" value="${buildingInfoList.items['prefMst']}"/>
				<tr>
					<td><a href="javascript:linkTo('../detail/','<c:out value="${buildingInfo.sysBuildingCd}"/>')"><c:out value="${buildingInfo.buildingCd}"/></a>&nbsp;</td>
					<td>
					<c:out value="${buildingInfo.displayBuildingName}"/>
					&nbsp;</td>
					<td><c:out value="${prefMst.prefName}"/>
					&nbsp;</td>
 					<td>
 						<div class="btnBlockListEdit">
							<div class="btnBlockListEditInner">
								<div class="btnBlockListEditInner2">
									<p><a href="javascript:linkTo('../detail/','<c:out value="${buildingInfo.sysBuildingCd}"/>');"><span>編集</span></a></p>
								</div>
							</div>
						</div>
 						<div class="btnBlockListDel">
							<div class="btnBlockListDelInner">
								<div class="btnBlockListDelInner2">
									<p><a href="javascript:linkToDel('../delete/confirm/','<c:out value="${buildingInfo.sysBuildingCd}"/>');"><span>削除</span></a></p>
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
	<input type="hidden" name="sysBuildingCd" value=""/>
	<c:import url="/WEB-INF/admin/default_jsp/include/building/searchParams.jsh" />
	</form>

</c:param>
</c:import>