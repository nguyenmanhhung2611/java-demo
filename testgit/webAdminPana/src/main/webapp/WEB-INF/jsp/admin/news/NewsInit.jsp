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

	function linkTo(url) {
	
		document.newsform.action = url;
		document.newsform.submit();
	}
	
	
	function newlist(url){
		document.inputdate.action = url;
		document.inputdate.submit();
	}
	
	function linkToUpd(url, newsId) {
		document.inputdate.action = url;
		document.inputdate.newsId.value = newsId;
		document.inputdate.command.value = 'update';
		document.inputdate.submit();
	}
	function linkToDel(url, newsId) {
		var returenValue = window.confirm("削除を行います。よろしいですか？")
		if (returenValue == true) {
			document.inputdate.action = url;
			document.inputdate.newsId.value = newsId;
			document.inputdate.command.value = 'delete';
			document.inputdate.submit();
		}
	}
   

</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>Search</h2>
		</div>
		<form action="./list/" method="post" name="newsform">
	    <input type="hidden" name="command" value="list">
	    			
	    
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" width="15%">News ID</th>
					<td><input name="keyNewsId" value="<c:out value="${searchForm.keyNewsId}"/>" 
						type="text" size="25" maxlength="<dm3lookup:lookup lookupName="inputLength" 
						lookupKey="information.input.informationNo" defaultValue="13"/>" 
						class="input2 ime-disabled"></td>
				</tr>
				
				<tr>
					<th class="head_tr" width="15%">Title</th>
					<td ><input name="keyNewsTitle"  value="<c:out value="${searchForm.keyNewsTitle}"/>" 
					type="text" size="25" maxlength="<dm3lookup:lookup lookupName="inputLength" 
					lookupKey="information.input.title" defaultValue="200"/>" ></td>
				</tr>
				
				<tr>
					<th class="head_tr" width="15%">Content</th>
					<td ><input name="keyNewsContent" value="<c:out value="${searchForm.keyNewsContent}"/>"
					 type="text" size="25" maxlength="<dm3lookup:lookup lookupName="inputLength" 
					 lookupKey="information.input.insDateFrom" defaultValue="25"/>" class="input2 ime-disabled"></td>
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
						<p><a href="javascript:newlist('../new/input/');"><span>Add News</span></a></p>
					</div>
				</div>
			</div>
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkTo('../list/');"><span>Search</span></a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>

	

	<div class="headingAreaInner"><p>
	<c:import url="/WEB-INF/admin/default_jsp/include/news/validationerrors.jsh" />
	<c:if test="${hitcont == 0}"> <span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください。</span></c:if>
	</p></div><br>

	<div class="headingAreaInner">
	<!-- 初期画面表示判定 -->
	<c:if test="${hitcont != 0 && hitcont != null}">
		<div class="headingAreaB01 start">
			<h2>List News</h2>
		</div>

		<div class="flexBlockA01">
			<table border="0" width="100%">
				<tr>
					<td>
						<c:set var="strBefore" value="javascript:KeyForm('" scope="request"/>
						<c:set var="strAfter" value="')" scope="request"/>
						<c:set var="pagingForm" value="${searchForm}" scope="request"/>
						<c:import url="/WEB-INF/admin/default_jsp/include/news/pagingCnt.jsh" />
										
						<c:import url="/WEB-INF/admin/default_jsp/include/news/pagingjs.jsh" />
					</td>
				</tr>
			</table>
			<br>
			
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr class="head_tr">
					<td width="20%">News ID</td>
					<td width="45%">News Title</td>
					<td width="18%">News Content</td>
					<td width="17%">Select</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
				<c:forEach items="${searchForm.visibleRows}" var="information">
					<tr>
						<td width="420"><a href="javascript:linkToUpd('../update/input/','<c:out value="${information.getNewsId()}"/>')"><c:out value="${information.getNewsId()}"/></a>&nbsp;</td>
						<td width="420"><c:out value="${information.getNewsTitle()}"/>&nbsp;</td>
						<td width="420"><c:out value="${information.newsContent}"/>&nbsp;</td>
	 					<td width="150">
	 						<div class="btnBlockC11">
								<div class="btnBlockC11Inner">
									<div class="btnBlockC11Inner2">
											<p><a class="tblSmallBtn" href="javascript:linkToUpd('../update/input/','<c:out value="${information.newsId}"/>');"><span>編集</span></a></p>
									</div>
								</div>
							</div>
							<dm3login:hasRole roleName="admin">
							<div class="btnBlockC12">
								<div class="btnBlockC12Inner">
									<div class="btnBlockC12Inner2">
											<p><a class="tblSmallBtn" href="javascript:linkToDel('../delConfirm/','<c:out value="${information.newsId}"/>');"><span>削除</span></a></p>
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
	<input type="hidden" name="command" value="search">
	<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
 	<input type="hidden" name="newsId" value=""/>
	<c:import url="/WEB-INF/jsp/admin/include/news/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>