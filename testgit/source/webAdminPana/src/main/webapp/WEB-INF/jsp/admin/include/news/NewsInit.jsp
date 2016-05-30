<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="問合一覧画面" />
<c:param name="contents">
<script type="text/javascript" src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script type ="text/JavaScript">

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
</script>

	
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>Admin News</h2>
		</div>
		
		
		<form action="../list/" method="post" name="searchForm">
	    <input type="hidden" name="command" value="list"/>
		<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
		
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr" style="width: 18%">Title</th>
					<td><input name="keyHousingCd" value="<c:out value="${searchForm.keyHousingCd}"/>" type="text" size="12" maxlength="10" class="input2 ime-disabled"></td>
				</tr>
				<tr>
					<th class="head_tr" style="width: 18%">Comments</th>
					<td nowrap="nowrap"><input name="keyDisplayHousingName"  value="<c:out value="${searchForm.keyDisplayHousingName}"/>" type="text" size="12" maxlength="25" class="input2"></td>
				</tr>
			
			</table>
		</div>
		
		<div class="flexBlockB06">
			<div class="flexBlockB06Inner clear">
				<!--btnBlockC06 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:document.searchForm.submit();"><span>Search</span></a>
							</p>
						</div>
					</div>
				</div>
			</div>
		<!--/btnBlockC06 -->
		</div>
		
		<div class="flexBlockB06">
			<div class="flexBlockB06Inner clear">
				<!--btnBlockC06 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:document.searchForm.submit();"><span>Add News</span></a>
							</p>
						</div>
					</div>
				</div>
			</div>
		<!--/btnBlockC06 -->
		</div>
		
		<div>==========================================================</div>
		
		
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
		
		<table id="bugTable" border="1"
			class="table table-bordered table-striped">
			<thead style="background: #3366FF">
				<tr>
					<th style="width: 10% !important; word-break: break-all;">News ID</th>
					<th style="width: 30% !important; word-break: break-all;">News Title</th>
					<th style="width: 35% !important; word-break: break-all;">News Content</th>
					<th style="width: 25% !important; word-break: break-all;">Action</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="List" items="${NewsList}">
					<tr>
						<td style="width: 10% !important; word-break: break-all;">${List.getNewsId()}</td>
						<td style="width: 30% !important;  word-break: break-all;">${List.getNewsTitle()}</td>					
						<td style="width: 35% !important; word-break: break-all;">${List.getNewsContent()}</td>	
						<td style="width: 25% !important; word-break: break-all;">
								<a href="./salary?idEm=${List.getNewsId()}">Update</a>
								<a href="./salary?idEm=${List.getNewsId()}">Delete</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
		<div>=========================================================</div>
		
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
		
		
	</div>
</form>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
</c:param>
</c:import>