<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： 会員検索画面

 2015/04/07		I.Shu	新規作成
 ---------------------------------------------------------------- --%>
 <!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/import.css" type="text/css" media="screen,print" />
		<title><c:out value="${param.htmlTitle}"/></title>
	<head> 
	<body  style="background:#fff;">
<script type ="text/JavaScript">
<!--
	function search(url){
		document.userform.action = url;
		document.userform.submit();
	}
	function returnResult(userId, userName, email) {
		window.close();
		/* input項目のリターン */
		if(window.opener.document.getElementById('inputUserId')!=null){
			window.opener.document.getElementById('inputUserId').value=userId;
		}
		if(window.opener.document.getElementById('inputUserName')!=null){
			window.opener.document.getElementById('inputUserName').value=userName;
		}
		if(window.opener.document.getElementById('inputEmail')!=null){
			window.opener.document.getElementById('inputEmail').value=email;
		}
		/* label項目のリターン */
		if(window.opener.document.getElementById('labelUserId')!=null){
			window.opener.document.getElementById('labelUserId').innerText=userId;
		}
		if(window.opener.document.getElementById('labelUserName')!=null){
			window.opener.document.getElementById('labelUserName').innerText=userName;
		}
		if(window.opener.document.getElementById('labelEmail')!=null){
			window.opener.document.getElementById('labelEmail').innerText=email;
		}
	}
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
// -->
</script>
	<br/>
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>会員の検索</h2>
		</div>
		<form action="./" method="post" name="userform">
	    <input type="hidden" name="command" value="list"/>
		<input type="hidden" name="userId" value="">

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table class="searchBox">
				<tr>
					<th class="head_tr">メールアドレス</th>
					<td colspan="3"><input name="keyEmail" value="<c:out value="${searchForm.keyEmail}"/>" type="text" size="40" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="user.search.keyEmail" defaultValue="256"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">お名前（姓）</th>
					<td><input name="keyMemberLname" value="<c:out value="${searchForm.keyMemberLname}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.search.keyMemberLname" defaultValue="40"/>" class="input2"></td>
					<th class="head_tr">お名前（名）</th>
					<td><input name="keyMemberFname"  value="<c:out value="${searchForm.keyMemberFname}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.search.keyMemberFname" defaultValue="40"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">フリガナ（セイ）</th>
					<td><input name="keyMemberLnameKana" value="<c:out value="${searchForm.keyMemberLnameKana}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.search.keyMemberLnameKana" defaultValue="40"/>" class="input2"></td>
					<th class="head_tr">フリガナ（メイ）</th>
					<td><input name="keyMemberFnameKana"  value="<c:out value="${searchForm.keyMemberFnameKana}"/>" type="text" size="16" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="mypage.search.keyMemberFnameKana" defaultValue="40"/>" class="input2"></td>
				</tr>
				<tr>
					<th class="head_tr">登録日</th>
					<td colspan="3"><input name="keyInsDateFrom" value="<c:out value="${searchForm.keyInsDateFrom}"/>" type="text" size="10" maxlength="10" class="input2"> ～ <input name="keyInsDateTo" value="<c:out value="${searchForm.keyInsDateTo}"/>" type="text" size="10" maxlength="10" class="input2"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!--flexBlockPopMemSrch -->
	<div class="flexBlockPopMemSrch" >
		<div class="flexBlockPopMemSrchInner" >
			<div class="btnBlockSearch">
				<div class="btnBlockSearchInner">
					<div class="btnBlockSearchInner2">
						<p><a href="javascript:search('../search/');"><span>検索</span></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--/flexBlockPopMemSrch -->

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${command == 'list'}">
		<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果がありません。</span></c:if>
	</c:if>
	</p></div><br>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>会員一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table border="0">
				<tr>
					<td>
		<c:set var="strBefore" value="javascript:KeyForm('" scope="request"/>
		<c:set var="strAfter" value="')" scope="request"/>
		<c:set var="pagingForm" value="${form}" scope="request"/>
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingCnt.jsh" />
						&nbsp;&nbsp;&nbsp;
		<c:import url="/WEB-INF/admin/default_jsp/include/pagingjs.jsh" />
					</td>
				</tr>
			</table>
			<table class="searchListBox">
				<tr class="head_tr">
					<td width="130">メールアドレス</td>
					<td width="150">ユーザー名</td>
					<td width="240">ユーザー名（カナ）</td>
					<td width="80">最終<br/>ログイン日</td>
					<td width="90">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="mypageUser">
			<c:set var="memberInfo" value="${mypageUser.items['memberInfo']}"/>
				<tr>
					<td><a href="javascript:returnResult('<c:out value="${memberInfo.userId}"/>','<c:out value="${memberInfo.memberLname}"/>　<c:out value="${memberInfo.memberFname}"/>','<c:out value="${memberInfo.email}"/>');"><c:out value="${memberInfo.email}"/></a>&nbsp;</td>
					<td><c:out value="${memberInfo.memberLname}"/>　<c:out value="${memberInfo.memberFname}"/>&nbsp;</td>
					<td><c:out value="${memberInfo.memberLnameKana}"/>　<c:out value="${memberInfo.memberFnameKana}"/>&nbsp;</td>
					<td><fmt:formatDate value="${memberInfo.lastLogin}" pattern="yyyy/MM/dd "/>&nbsp;</td>
 					<td>
 						<div class="btnBlockListSelect">
							<div class="btnBlockListSelectInner">
								<div class="btnBlockListSelectInner2">
									<p><a href="javascript:returnResult('<c:out value="${memberInfo.userId}"/>','<c:out value="${memberInfo.memberLname}"/>　<c:out value="${memberInfo.memberFname}"/>','<c:out value="${memberInfo.email}"/>');"><span>選択</span></a></p>
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
	<!--/headingAreaInner -->
	<%-- 入力formパラメータ引き継ぎ --%>
	<form action="" method="post" name="inputdate">
	<input type="hidden" name="command" value="">
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
	<input type="hidden" name="userId" value="">
	<c:import url="/WEB-INF/admin/default_jsp/include/mypage/searchParams.jsh" />
	</form>
	</body>
</html>