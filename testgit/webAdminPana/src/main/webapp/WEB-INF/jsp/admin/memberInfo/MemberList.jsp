<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<%-- ----------------------------------------------------------------
 名称： 会員情報一覧画面

 2015/04/15		tang.tianyun	新規作成
 2015/08/20     Vinh.Ly Add CSRF Token for Member Delete
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="会員情報一覧" />
<c:param name="contents">

<script type ="text/JavaScript">
<!--
	function linkTo(url){
   		document.searchForm.action = url;
   		document.searchForm.submit();
	}
	function linkToCsv(url){
		document.searchForm.action = url;
		document.searchForm.target = "_blank";
		document.searchForm.submit();
		document.searchForm.target = "_self";
	}
	function linkToUpd(url, userId, command) {
		document.inputForm.action = url;
		document.inputForm.userId.value = userId;
		document.inputForm.command.value = command;
		document.inputForm.submit();
	}
	function linkToDel(url, userId) {
		var returenValue = window.confirm("削除を行います。よろしいですか？")
		if (returenValue == true) {
			document.inputForm.action = url;
			document.inputForm.userId.value = userId;
			document.inputForm.submit();
		}
	}
    function KeyForm(page) {
   		document.inputForm.action = '';
   		document.inputForm.selectedPage.value = page;
    	document.inputForm.command.value = 'list';
   		document.inputForm.submit();
    }
// -->
</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>会員の検索</h2>
		</div>
		<form action="./list/" method="post" name="searchForm">
	    <input type="hidden" name="command" value="list"/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="15%">メールアドレス</th>
					<td width="35%"><input name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>" type="text" size="17" maxlength="255" class="input2 ime-disabled"></td>
					<th class="head_tr" width="15%">プロモコード</th>
					<td width="35%"><input name="keyPromo" value="<c:out value="${searchForm.keyPromo}"/>" type="text" size="10" maxlength="20" class="input2 ime-disabled"></td>
				</tr>
				<tr>
					<th class="head_tr">会員住所</th>
					<td colspan="3">
						<select name="keyPrefCd" >
						<option></option>
						<c:forEach items="${prefMstList}" var="prefMst">
							<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${searchForm.keyPrefCd == prefMst.prefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
						</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<th class="head_tr">お名前（姓）</th>
					<td><input name="keyMemberLname" value="<c:out value="${searchForm.keyMemberLname}"/>" type="text" size="15" maxlength="30" class="input2"></td>
					<th class="head_tr">お名前（名）</th>
					<td><input name="keyMemberFname"  value="<c:out value="${searchForm.keyMemberFname}"/>" type="text" size="15" maxlength="30" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">フリガナ(セイ)</th>
					<td><input name="keyMemberLnameKana" value="<c:out value="${searchForm.keyMemberLnameKana}"/>" type="text" size="15" maxlength="30" class="input2"></td>
					<th class="head_tr">フリガナ(メイ)</th>
					<td><input name="keyMemberFnameKana"  value="<c:out value="${searchForm.keyMemberFnameKana}"/>" type="text" size="15" maxlength="30" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">登録経路</th>
					<td colspan="3">
						<dm3lookup:lookupForEach lookupName="entryRoute">
						<label>
							<input type="radio" name="keyEntryRoute" value="<c:out value="${key}"/>" <c:if test="${searchForm.keyEntryRoute == key}">checked</c:if>><c:out value="${value}"/>&nbsp;&nbsp;
						</label>
						</dm3lookup:lookupForEach>
					</td>
				</tr>
				<tr>
					<th class="head_tr">流入経路</th>
					<td colspan="3">
						<dm3lookup:lookupForEach lookupName="refCd">
						<c:forEach items="${searchForm.keyInflowRoute}" var="selectedInflowRoute">
							<c:choose>
								<c:when test="${selectedInflowRoute == key}">
									<c:set var="chkValue" value="true"/>
								</c:when>
							</c:choose>
						</c:forEach>
						<c:choose>
							<c:when test="${chkValue == true}">
							<label>
								<input type="checkBox" name="keyInflowRoute" value="<c:out value="${key}"/>" checked /><c:out value="${value}"/>&nbsp;&nbsp;
							</label>
							</c:when>
							<c:otherwise>
							<label>
								<input type="checkBox" name="keyInflowRoute" value="<c:out value="${key}"/>" /><c:out value="${value}"/>&nbsp;&nbsp;
							</label>
							</c:otherwise>
						</c:choose>
						<c:set var="chkValue" value="false"/>
						</dm3lookup:lookupForEach>
					</td>
				</tr>
				<tr>
					<th class="head_tr">登録日</th>
					<td colspan="3"><input name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled"> ～ <input name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled">(yyyy/mm/dd)</td>
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
							<a href="javascript:linkToUpd('../new/input/','','');"><span>新規追加</span></a>
						</p>
					</div>
				</div>
			</div>
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkTo('../list/');"><span>検索</span></a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
	<div class="flexBlockB06">
		<div style="width: 87px; margin: 0 auto;padding-top: 6px;float: right; padding-right: 10px;"></div>
		<dm3login:hasRole roleName="admin">
			<c:choose>
				<c:when test="${hitcont != 0 && hitcont != null}">
					<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:linkToCsv('../csv/');"><span>CSV 出力</span></a>
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
								<a disabled="disabled"><span>CSV出力</span></a>
							</p>
						</div>
					</div>
				</div>
				</c:otherwise>
			</c:choose>
		</dm3login:hasRole>
	</div>
	<br>
	<!--/flexBlockB06 -->

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください</span></c:if>
	</p>
	</div>



	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>会員一覧</h2>
		</div>

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
			<br>
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr class="head_tr">
					<td width="22%">会員番号</td>
					<td width="30%">フリガナ(セイ)</td>
					<td width="30%">会員住所</td>
					<td width="18%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="member">
			<c:set var="memberInfo" value="${member.items['memberInfo']}"/>
			<c:set var="prefMst" value="${member.items['prefMst']}"/>
				<tr>
					<td width="22%"><a href="javascript:linkToUpd('../update/input/','<c:out value="${memberInfo.userId}"/>','');"><c:out value="${memberInfo.userId}"/></a></td>
					<td width="30%"><c:out value="${memberInfo.memberLnameKana}"/>&nbsp;</td>
					<td width="30%"><c:out value="${prefMst.prefName}"/><c:out value="${memberInfo.address}"/><c:out value="${memberInfo.addressOther}"/>&nbsp;</td>
 					<td width="18%">
						<div class="btnBlockC11">
							<div class="btnBlockC11Inner">
								<div class="btnBlockC11Inner2">
										<p><a class="tblSmallBtn" href="javascript:linkToUpd('../update/input/','<c:out value="${memberInfo.userId}"/>','');"><span>編集</span></a></p>
								</div>
							</div>
						</div>
						<dm3login:hasRole roleName="admin">
							<div class="btnBlockC12">
								<div class="btnBlockC12Inner">
									<div class="btnBlockC12Inner2">
											<p><a class="tblSmallBtn" href="javascript:linkToDel('../delConfirm/','<c:out value="${memberInfo.userId}"/>');"><span>削除</span></a></p>
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
	<form action="" method="post" name="inputForm">
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/memberInfo/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>