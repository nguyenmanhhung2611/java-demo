<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<%-- ----------------------------------------------------------------
 名称： お知らせ一覧画面

 2015/02/06		I.Shu	新規作成
 2015/08/20     Vinh.Ly Add CSRF Token for Information Delete
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="お知らせメンテナンス" />
<c:param name="contents">

<script type ="text/JavaScript">
	function popupMember() {
		var url = '../../mypage/search/';
		window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
	}

	function linkTo(url) {
		document.newsform.action = url;
		document.newsform.submit();
	}
	function linkToCsv(url) {
		document.newsform.action = url;
		document.newsform.target = "_blank";
		document.newsform.submit();
		document.newsform.target = "_self";
	}
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
	function linkToDel(url, informationNo, title) {
		var returenValue = window.confirm("削除を行います。よろしいですか？")
		if (returenValue == true) {
			document.inputdate.action = url;
			document.inputdate.informationNo.value = informationNo;
			document.inputdate.title.value = title;
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
	function openMemberSearch(url) {
		window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
	}

</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>お知らせの検索</h2>
		</div>
		<form action="./list/" method="post" name="newsform">
	    <input type="hidden" name="command" value="list">
<!-- 	    <input type="hidden" name="informationNo" value=""/> -->
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="15%">お知らせ番号</th>
					<td><input name="keyNewsId" value="<c:out value="${searchForm.keyNewsId}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.informationNo" defaultValue="13"/>" class="input2 ime-disabled"></td>
				</tr>
				<tr>
					<th class="head_tr" width="15%">タイトル</th>
					<td><input name="keyNewsTitle"  value="<c:out value="${searchForm.keyNewsTitle}"/>" type="text" size="25" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.title" defaultValue="200"/>" class="input2">を含む</td>
				</tr>
				<tr>
					<th class="head_tr" width="15%">登録日</th>
					<td><input name="keyNewsContent" value="<c:out value="${searchForm.keyNewsContent}"/>" type="text" size="10" maxlength="<dm3lookup:lookup lookupName="inputLength" lookupKey="information.input.insDateFrom" defaultValue="10"/>" class="input2 ime-disabled"> </td>
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
						<p><a href="javascript:newlist('../new/input/');"><span>新規追加</span></a></p>
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
	</div>

	<!--flexBlockB06 -->
	<div class="flexBlockB06">
		<div style="width: 87px; margin: 0 auto;padding-top: 6px;float: right; padding-right: 10px;"></div>
		<dm3login:hasRole roleName="admin">
			<c:if test="${hitcont != 0 && hitcont != null}">
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkToCsv('../csv/');"><span>CSV 出力</span></a>
						</p>
					</div>
				</div>
			</div>
			</c:if>
			<c:if test="${hitcont == 0 || hitcont == null}">
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p><a disabled="disabled"><span>CSV 出力</span></a></p>
					</div>
				</div>
			</div>
		</c:if>
		</dm3login:hasRole>
	</div>

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください。</span></c:if>
	</p></div><br>


	<div class="headingAreaInner">
	<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>お知らせ一覧</h2>
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
					<td width="20%">お知らせ番号</td>
					<td width="45%">タイトル</td>
					<td width="18%">対象会員</td>
					<td width="17%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
				<c:forEach items="${searchForm.visibleRows}" var="informationList">
					<c:set var="information" value="${informationList.items['information']}"/>
					<c:set var="informationTarget" value="${informationList.items['informationTarget']}"/>
					<c:set var="memberInfo" value="${informationList.items['memberInfo']}"/>
						<tr>
							<td width="95"><a href="javascript:linkToUpd('../update/input/','<c:out value="${information.informationNo}"/>')"><c:out value="${information.informationNo}"/></a>&nbsp;</td>
							<td width="420"><c:out value="${information.title}"/>&nbsp;</td>
							<td width="300">
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
		 					<td width="150">
		 						<div class="btnBlockC11">
									<div class="btnBlockC11Inner">
										<div class="btnBlockC11Inner2">
												<p><a class="tblSmallBtn" href="javascript:linkToUpd('../update/input/','<c:out value="${information.informationNo}"/>');"><span>編集</span></a></p>
										</div>
									</div>
								</div>
								<dm3login:hasRole roleName="admin">
								<div class="btnBlockC12">
									<div class="btnBlockC12Inner">
										<div class="btnBlockC12Inner2">
												<p><a class="tblSmallBtn" href="javascript:linkToDel('../delConfirm/','<c:out value="${information.informationNo}"/>','<c:out value="${information.title}"/>');"><span>削除</span></a></p>
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
	<input type="hidden" name="command" value="">
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
	<input type="hidden" name="informationNo" value=""/>
	<input type="hidden" name="title" value=""/>
	<c:import url="/WEB-INF/jsp/admin/include/information/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>