<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- ----------------------------------------------------------------
 名称： 会員情報一覧画面

 2015/04/15		tang.tianyun	新規作成
 2015/08/20     Vinh.Ly Add CSRF Token for Member Delete
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="Comment Manage Page" />
<c:param name="contents">

<!-- 	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script> -->
<!--     <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.min.css" /> -->
<!--     <script src="http://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script> -->
  <script src="//code.jquery.com/jquery-1.8.3.js"></script>
  <script src="//code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<!--  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css"> -->
 <link rel="stylesheet" href="//code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">


 


<script type ="text/JavaScript">

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
	function linkToDel(url, commentId) {
		var returenValue = window.confirm("Are you sure to delete this comment？")
		if (returenValue == true) {
			document.inputForm.action = url;
			document.inputForm.keyCommentId.value = commentId;
			document.inputForm.submit();
		}
	}
    function KeyForm(page) {
   		document.inputForm.action = '';
   		document.inputForm.selectedPage.value = page;
    	document.inputForm.command.value = 'list';
   		document.inputForm.submit();
    }
    
    function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

    $(document).ready(function() {
   		    $( ".opener" ).click(function() {
   		    	var a = $(this).val();
   		    	//var cut1 = a.split(" ", 3);
   		    	var cut1 = a.substring(0, 20);
   		    	var cut3 = a.substring(20, 1000);
   		   		var cut2 = cut1;
   		    	var pe1 = $(this).parent();
   		    	var pe2 = pe1.parent().parent();
   	     		var dialogElement = pe2.children(".dialog");
   	     		dialogElement.dialog({
   	     				width: "500",
   	                    height: "300",
   	                    modal: true,
   	                    close: function(event, ui) {
   	                            $(this).dialog("destroy");
   	                    }
   	     		});
   	     		var itemValue = dialogElement.children(".showinfo");
   		    	itemValue.val(a);
   		    	itemValue.html("<h2> * * * Ban Dang Xem Thong Tin Comment Co ID La " +cut2+ "  * * * </h2><br />" + " - " + cut3);
   	     		dialogElement.dialog("open");
   		    });
   		
   		    
   		 $("#clearinput").click(function(){
   			$("#txtcommentcontent").val('');
			$("#txtcreateuser").val('');
			$("#txtFromDate").val('');
			$("#txtToDate").val('');
   			 
   		 }); 
   		 
   		 
   		//-----------------------------FORMAT DATE----------------------------------------------- 
   		
//    		$("#txtToDate").datepicker({
//    		        numberOfMonths: 1,
//    		     	//minDate: 0,
//     	        //maxDate: "+60D",
//    		        dateFormat: 'yy-mm-dd',
//    		     	onSelect: function(selected) {
//       	          $("#txtFromDate").datepicker("option","maxDate", selected)
//       	        }
//    		    });
   	    	
//    		    $("#txtFromDate").datepicker({ 
//    		        numberOfMonths: 1,
//    		     	//minDate: 0,
//     	        //maxDate:"+60D",
//    		        dateFormat: 'yy-mm-dd',
//    		    	onSelect: function(selected) {
//      	           $("#txtToDate").datepicker("option","minDate", selected)
//      	        }
//    		    });  

			$("#txtToDate").datepicker({
   		        numberOfMonths: 1,
   		        dateFormat: 'yy-mm-dd',
   		     	onSelect: function(selected) {
      	          $("#txtFromDate").datepicker("option","maxDate", selected)
      	        }
   		    });
   	    	
   		    $("#txtFromDate").datepicker({ 
   		        numberOfMonths: 1,
   		        dateFormat: 'yy-mm-dd',
   		    	onSelect: function(selected) {
     	           $("#txtToDate").datepicker("option","minDate", selected)
     	        }
   		    });  
   	    
   		    
   		    
   		var format = "yyyy-mm-dd";
   		var match = new RegExp(format
   		    .replace(/(\w+)\W(\w+)\W(\w+)/, "^\\s*($1)\\W*($2)?\\W*($3)?([0-9]*).*")
   		    .replace(/m|d|y/g, "\\d"));
   		var replace = "$1/$2/$3$4"
   		    .replace(/\//g, format.match(/\W/));

   		function doFormat(target)
   		{
   		    target.value = target.value
   		        .replace(/(^|\W)(?=\d\W)/g, "$10")   
   		        .replace(match, replace)             
   		        .replace(/(\W)+/g, "$1");            
   		}

   		$("#txtToDate").keyup(function(e) {
   		   if(!e.ctrlKey && !e.metaKey && (e.keyCode == 32 || e.keyCode > 46))
   		      doFormat(e.target)
   		});
   		
   		$("#txtFromDate").keyup(function(e) {
    		   if(!e.ctrlKey && !e.metaKey && (e.keyCode == 32 || e.keyCode > 46))
    		      doFormat(e.target)
    		});
   		    
    });

</script>

	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>Comment Info</h2>
		</div>
		<form action="./list/" method="post" name="searchForm">
	    <input type="hidden" name="command" value="list"/>
		<!--flexBlockA01 -->
		<div class="flexBlockA01">
			<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
				<tr>
					<th class="head_tr">News Title</th>
					<td colspan="3" style="font-weight: bold;">
						<h2><label style="font-size: 20px; color: red">${news.newsTitle}</label></h2>
						<c:set var="searchForm.keyNewId" value="${news.newsId}"/>
					</td>
				</tr>
				<tr>
					<th class="head_tr">News Content</th>
					<td colspan="3" style="font-size: 12px;">
						<textarea  cols="72" rows="20" disabled ><c:out value="${news.newsContent}" /></textarea>
					</td>
				</tr>
				
				<tr>
					<th class="head_tr">Contents Comment</th>
					<td colspan="3">
						<input type="text" id="txtcommentcontent" name="keyCommentContent" value="<c:out value="${searchForm.keyCommentContent}"/>" type="text" size="50"  class="input2"    />
					</td>
				</tr>
				
				<tr>
					<th class="head_tr">Created User</th>
					<td colspan="3">
						<input type="text" id="txtcreateuser"  name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>" type="text" size="50"  class="input2"    />
					</td>
				</tr>
				
				<tr>
					<th class="head_tr">Created Date</th>
					<td colspan="3">
						From: <input id="txtFromDate" name="keyInsDateFrom" type="text"  value="<c:out value="${searchForm.keyInsDateFrom}"/>" size="15"  class="input2" maxlength= "10" />
						To: <input id="txtToDate" name="keyInsDateTo" type="text" value="<c:out value="${searchForm.keyInsDateTo}"/>"  size="15"  class="input2" maxlength= "10" /> (yyyy-MM-dd)
					</td>
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
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkTo('../list/<c:out value="${news.newsId}"/>');"><span>Search</span></a>
						</p>
					</div>
				</div>
			</div>
			
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<span id="clearinput" style="cursor: pointer;">Clear</span>
						</p>
					</div>
				</div>
			</div>
			
			<div class="btnBlockC14">
				<div class="btnBlockC14Inner">
					<div class="btnBlockC14Inner2">
						<p>
							<a href="javascript:linkToUrl('../../news/list/', '<c:out value="${searchForm.searchCommand}"/>');"><span>Back</span></a>
						</p>
					</div>
				</div>
			</div>
			
		</div>
		<!--/btnBlockC14 -->
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
			<h2>List Comment</h2>
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
					<td width="22%">ID Comment</td>
					<td width="30%">Content Comment</td>
					<td width="30%">Created User</td>
					<td width="30%">Created Date</td>
					<td width="30%">&nbsp;</td>
				</tr>
				<!-- 検索結果画面表示判定 -->
		<c:forEach items="${searchForm.visibleRows}" var="comment" varStatus="loop">
<%-- 			<c:set var="memberInfo" value="${comment.items['memberInfo']}"/> --%>
<%-- 			<c:set var="prefMst" value="${comment.items['prefMst']}"/> --%>
				<tr>
					<td width="22%"><a><c:out value="${comment.commentId}"/></a></td>
<%-- 					<td width="30%"><c:out value="${fn:substring(comment.commentContent, 0, 40)}"/>...&nbsp;</td> --%>
<%-- 					<td width="30%"><c:out value="${comment.commentContent.length > 40 ? fn:substring(comment.commentContent, 0, 40) : comment.commentContent}"/>&nbsp;</td> --%>
					<td width="30%">
						<c:if test="${fn:length(comment.commentContent)>40}">
							<c:out value="${fn:substring(comment.commentContent, 0, 40)}"/>...
						</c:if>
						<c:if test="${fn:length(comment.commentContent)<40}">
							<c:out value="${comment.commentContent}"/>
						</c:if>
					</td>
					<td width="30%"><c:out value="${comment.insUserId}"/>&nbsp;</td>
					<td width="30%"><c:out value="${comment.insDate}"/>&nbsp;</td>
 					<td width="30%">
						<div class="btnBlockC11">
							<div class="btnBlockC11Inner">
								<div class="btnBlockC11Inner2">
										<p><button class="opener" value="${comment.commentId},${comment.commentContent}" ><span>Detail</span></button></p>
								</div>
								<div class="dialog" title="Detail Comment Info" >
									<div class="showinfo">
								
									</div>
								</div>
							</div>
						</div>
						<dm3login:hasRole roleName="admin">
							<div class="btnBlockC12">
								<div class="btnBlockC12Inner">
									<div class="btnBlockC12Inner2">
											<p><a class="tblSmallBtn" href="javascript:linkToDel('../delConfirm/<c:out value="${news.newsId}"/>','<c:out value="${comment.commentId}"/>');"><span>Delete</span></a></p>
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
	<c:import url="/WEB-INF/jsp/admin/include/commentInfo/searchParams.jsh" />
	<dm3token:oneTimeToken/>
	</form>

</c:param>
</c:import>




