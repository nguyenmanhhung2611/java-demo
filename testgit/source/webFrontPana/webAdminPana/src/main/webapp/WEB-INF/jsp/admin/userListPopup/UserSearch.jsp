<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ja">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Content-Style-Type" content="text/css">
		<meta http-equiv="Content-Script-Type" content="text/javascript">
		<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/import.css" type="text/css" media="screen,print" />
		<title><c:out value="${commonParameters.adminPageTitle}"/></title>
		<style type="text/css">
			body {
				color: #000;
				background: white;
		        height: 100%;
	        }
		</style>
	<head>
	<body>


<%-- ----------------------------------------------------------------
 名称： 会員検索画面画面

 2015/04/13		tang.tianyun	新規作成
 ---------------------------------------------------------------- --%>

<script type ="text/JavaScript">
<!--
    function KeyForm(page) {
   		document.inputdate.action = '';
   		document.inputdate.selectedPage.value = page;
    	document.inputdate.command.value = 'list';
   		document.inputdate.submit();
    }
    function closeWindow(userId,memberLname,memberFname,memberLnameKana,memberFnameKana,tel,email) {
    	if (window.opener && !window.opener.closed) {
    		if (window.opener.document.all.userId != undefined && window.opener.document.all.userId.value != undefined) {
            	window.opener.document.all.userId.value = userId;
    		}
    		if (window.opener.document.getElementById("keyUserId") != undefined && window.opener.document.getElementById("keyUserId").value != undefined) {
            	window.opener.document.getElementById("keyUserId").value = userId;
    		}
    		if (window.opener.document.all.memberName != undefined && window.opener.document.all.memberName.value != undefined) {
    			window.opener.document.all.memberName.value = memberLname + memberFname;
    		}

   			if (window.opener.document.all.memberNameTd != undefined && window.opener.document.all.memberNameTd.innerHTML != undefined) {
           		window.opener.document.all.memberNameTd.innerHTML = memberLname + memberFname + "&nbsp;";
           	}
    		if (window.opener.document.all.memberNameKana != undefined && window.opener.document.all.memberNameKana.value != undefined) {
    			window.opener.document.all.memberNameKana.value = memberLnameKana + memberFnameKana;
    		}
   			if (window.opener.document.all.memberNameKanaTd != undefined && window.opener.document.all.memberNameKanaTd.innerHTML != undefined) {
           		window.opener.document.all.memberNameKanaTd.innerHTML = memberLnameKana + memberFnameKana + "&nbsp;";
           	}
    		if (window.opener.document.all.tel != undefined && window.opener.document.all.tel.value != undefined) {
    			window.opener.document.all.tel.value = tel;
    		}
   			if (window.opener.document.all.telTd != undefined && window.opener.document.all.telTd.innerHTML != undefined) {
           		window.opener.document.all.telTd.innerHTML = tel + "&nbsp;";
           	}
    		if (window.opener.document.all.email != undefined && window.opener.document.all.email.value != undefined) {
    			window.opener.document.all.email.value = email;
    		}
   			if (window.opener.document.all.emailTd != undefined && window.opener.document.all.emailTd.innerHTML != undefined) {
           		window.opener.document.all.emailTd.innerHTML = email + "&nbsp;";
           	}
    	}
   		window.close();
    }
// -->
</script>

	<div class="headingAreaA01 start">
		<h1>会員の検索画面</h1>
	</div>
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>会員の検索</h2>
		</div>
		<form action="../search/" method="post" name="userSearchForm">
	    <input type="hidden" name="command" value="list"/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01" style="width: 100%">
			<table border="1" cellspacing="0" cellpadding="0" class="tableA1" style="width: 98%">
			<colgroup>
				<col width="15%" class="head_tr"/>
				<col width="35%"/>
				<col width="15%" class="head_tr"/>
				<col width="35%"/>
			</colgroup>
				<tr>
					<th>会員番号</th>
					<td colspan="3"><input name="keyUserNo" value="<c:out value="${searchForm.keyUserNo}"/>" type="text" size="15" maxlength="20" class="input2 ime-disabled"></td>
				</tr>
				<tr>
					<th class="head_tr" style="width: 15%">会員住所</th>
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
					<th>お名前（姓）</th>
					<td><input name="keyMemberLname" value="<c:out value="${searchForm.keyMemberLname}"/>" type="text" size="20" maxlength="30" class="input2"></td>
					<th>お名前（名）</th>
					<td><input name="keyMemberFname"  value="<c:out value="${searchForm.keyMemberFname}"/>" type="text" size="20" maxlength="30" class="input2"></td>
				</tr>
				<tr>
					<th>フリガナ（セイ）</th>
					<td><input name="keyMemberLnameKana" value="<c:out value="${searchForm.keyMemberLnameKana}"/>" type="text" size="20" maxlength="30" class="input2"></td>
					<th>フリガナ（メイ）</th>
					<td><input name="keyMemberFnameKana"  value="<c:out value="${searchForm.keyMemberFnameKana}"/>" type="text" size="20" maxlength="30" class="input2"></td>
				</tr>
			</table>
		</div>
		<!--/flexBlockA01 -->

		</form>
	</div>
	<!--/headingAreaInner -->

	<!--flexBlockB06 -->
	<div class="flexBlockB06" style="width: 98%">
		<div class="flexBlockB06Inner clear">
			<!--btnBlockC14 -->
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p style="font-size: 12px">
							<a href="javascript:document.userSearchForm.submit();"><span>検 索</span></a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<br>
	</div>
		<!--/btnBlockC14 -->

	<!--/flexBlockB06 -->

	<div class="headingAreaInner"><p style="font-size: 12px;">
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${command == 'list'}">
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください</span></c:if>
	</c:if>
	</p></div><br>


	<div class="headingAreaInner" style="width: 98%">
		<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>会員一覧</h2>
		</div>

		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table border="0">
				<tr>
					<td style="width: 100%">
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
					<td width="20%">会員番号</td>
					<td width="25%">フリガナ(セイ)</td>
					<td width="25%">フリガナ(メイ)</td>
					<td width="22%">会員住所（都道府県まで）</td>
					<td width="8%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="user">
			<c:set var="memberInfo" value="${user.items['memberInfo']}"/>
			<c:set var="prefMst" value="${user.items['prefMst']}"/>
				<tr>
					<td width="20%"><a href="javascript:closeWindow('<c:out value="${memberInfo.userId}"/>','<c:out value="${memberInfo.memberLname}"/>','<c:out value="${memberInfo.memberFname}"/>','<c:out value="${memberInfo.memberLnameKana}"/>','<c:out value="${memberInfo.memberFnameKana}"/>','<c:out value="${memberInfo.tel}"/>','<c:out value="${memberInfo.email}"/>')"><c:out value="${memberInfo.userId}"/></a></td>
					<td width="25%"><c:out value="${memberInfo.memberLnameKana}"/>&nbsp;</td>
					<td width="25%"><c:out value="${memberInfo.memberFnameKana}"/>&nbsp;</td>
					<td width="22%"><c:out value="${prefMst.prefName}"/>&nbsp;</td>
 					<td width="8%">
						<div class="btnBlockC11">
							<div class="btnBlockC11Inner">
								<div class="btnBlockC11Inner2">
										<p><a class="tblSmallBtn" href="javascript:closeWindow('<c:out value="${memberInfo.userId}"/>','<c:out value="${memberInfo.memberLname}"/>','<c:out value="${memberInfo.memberFname}"/>','<c:out value="${memberInfo.memberLnameKana}"/>','<c:out value="${memberInfo.memberFnameKana}"/>','<c:out value="${memberInfo.tel}"/>','<c:out value="${memberInfo.email}"/>')"><span>選択</span></a></p>
								</div>
							</div>
						</div>
					</td>
				</tr>
		</c:forEach>
			</table>
			<br>
			<br>
		</div>
		<!--/flexBlockA01 -->
	</c:if>

	</div>
	<%-- 入力formパラメータ引き継ぎ --%>
	<form action="" method="post" name="inputdate">
	<input type="hidden" name="command" value="">
	<c:import url="/WEB-INF/jsp/admin/include/userListPopup/searchParams.jsh" />
	</form>

	</body>
</html>