<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<%-- ----------------------------------------------------------------
 名称： リフォーム情報編集画面

 2015/03/10     TRANS    新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム情報編集" />
<c:param name="contents">
<link rel="stylesheet" href="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/css/jquery.fancybox.css" type="text/css" media="screen,print" />
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery-1.11.2.js"></script>
<script src="<c:out value='${commonParameters.resourceRootUrl}'/>cmn/js/jquery.fancybox.pack.js"></script>
<script type ="text/JavaScript">
<!--
	$(function(){
	    $("#demo2").fancybox();
	});
	$(function(){
	    $("#demo3").fancybox();
	});
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	// リフォーム詳細情報編集へ
	function initReformDlt() {
		var frm = document.forms[0];
		frm.enctype = "application/x-www-form-urlencoded";
		frm.encoding = "application/x-www-form-urlencoded";
		frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/detail/input/";
		frm.submit();
	}

	// リフォーム画像情報編集へ
	function initReformImg() {
		var frm = document.forms[0];
		frm.enctype = "application/x-www-form-urlencoded";
		frm.encoding = "application/x-www-form-urlencoded";
		frm.action="<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/image/input/";
		frm.submit();
	}

	// 選択したの診断結果設定
	function changeChartValue (index, chartValue) {
		var frm = document.forms[0];
		frm.chartValue[index].value = chartValue;
	}
    function changeCategory(category1, categories){
        var frm = document.forms[0];
        var category2 = frm.planCategory2;
        category2.options.length = 0;
        var emptyOption = document.createElement("option");
        category2.appendChild(emptyOption);
        
        for (var i = 0; i < categories.length; i++) {
            if(categories[i].id == category1){
                var categories2Data = categories[i].children;
                for(var j = 0; j < categories2Data.length; j++){
                    var option = document.createElement("option");
                    option.value = categories2Data[j].id;
                    option.text = categories2Data[j].name;
                    category2.appendChild(option);
                }
                break;
            }
        }
    }
    
    $(document).ready(function() {
           var categories = <c:out value="${inputForm.superCategoriesAsJson}" escapeXml="false"/>;
           $('#category1').change(function(e){
               var category1 = $(e.target).val();
               changeCategory(category1, categories);
           });
    });
// -->
</script>

<form action="./list" enctype="multipart/form-data"  method="post" name="inputForm">
<!--headingAreaInner -->
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム情報編集<span style="font-weight: normal"><font color="red">　※</font>入力必須項目</span></h2>
	</div>
    <c:import url="/WEB-INF/jsp/admin/include/reformPlan/inputForm.jsh" />
</div>

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/reform/confirm/', '<c:out value="${inputForm.command}"/>');"><span>登録</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->

		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p><a href="javascript:linkToUrl('<c:out value="${pageContext.request.contextPath}"/>/top/housing/detail/<c:out value="${inputForm.sysHousingCd}"/>/', '');"><span>戻る</span></a></p>
				</div>
			</div>
		</div>
		<!--/btnBlockC14 -->
	</div>
</div>
<br>

<c:if test="${inputForm.dtlFlg == '1'}">
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム詳細情報</h2>
	</div>
</div>
<!--/headingAreaInner -->
<!--headingAreaInner -->
<div class="headingAreaInner">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="10%"/>
			<col width="10%"/>
			<col width="60%"/>
			<col width="20%"/>
		</colgroup>
		<tr>
			<th class="head_tr">表示順</th>
			<th class="head_tr">画像</th>
			<th class="head_tr">名称</th>
			<th class="head_tr">価格</th>
		</tr>
		<c:forEach var="dtl" items="${dtlList}" varStatus="ItemStatus">
		<tr>
			<td width="300"><c:out value="${dtl.sortOrder}"/>&nbsp;</td>
			<td width="100">
				<p><a href="<c:out value="${dtl.pathName}"/>" target="_blank"><img src="<c:out value="${commonParameters.resourceRootUrl}"/>cmn/imgs/pdf_icon.gif" alt="" /></a></p>
			</td>
			<td width="1000"><c:out value="${dtl.imgName}"/>&nbsp;</td>
			<td width="400"><c:if test="${!empty dtl.reformPrice }"><fmt:formatNumber value="${dtl.reformPrice}" pattern="###,###" />円</c:if>&nbsp;</td>
		</tr>
		</c:forEach>
	</table>
</div>
<!--/flexBlockA01 -->

<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:initReformDlt();">
					<span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--btnBlockC14 -->
	</div>
</div>
<!--/flexBlockB06 -->
</c:if>


<c:if test="${inputForm.imgFlg == '1'}">
<div class="headingAreaInner">
	<div class="headingAreaB01 start">
		<h2>リフォーム画像情報</h2>
	</div>
</div>

<!--/headingAreaInner -->
<!--headingAreaInner -->
<div class="headingAreaInner">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="10%"/>
			<col width="35%"/>
			<col width="35%"/>
			<col width="20%"/>
		</colgroup>
		<tr>
			<th class="head_tr">表示順</th>
			<th class="head_tr">Before</th>
			<th class="head_tr">After</th>
			<th class="head_tr">閲覧権限</th>
		</tr>
		<c:forEach var="img" items="${imgList}" varStatus="ItemStatus">
		<tr>
			<td width="100"><c:out value="${img.sortOrder}"/>&nbsp;</td>
			<td width="300">
				<div style="width:40%;float:left" >
			    <p><a id="demo2" href="<c:out value="${img.beforeFileName}"/>">
                   <img src="<c:out value="${img.beforePathName}"/>" alt="" />
                </a></p>
                </div>
				<div  style="width:60%;float:left;" >
                <c:out value="${img.beforeComment}"/>
                </div>
			</td>
			<td width="300">
				<div style="width:40%;float:left" >
			    <p><a id="demo3" href="<c:out value="${img.afterFileName}"/>">
                   <img src="<c:out value="${img.afterPathName}"/>" alt="" />
                </a></p>
                </div>
				<div  style="width:60%;float:left;" >
                <c:out value="${img.afterComment}"/>
                </div>
			</td>
			<td width="200">
                <dm3lookup:lookupForEach lookupName="ImageInfoRoleId">
					<c:if test="${img.roleId == key}"><c:out value="${value}"/></c:if>
                </dm3lookup:lookupForEach>&nbsp;
			</td>
		</tr>
		</c:forEach>
	</table>
</div>
<!--/flexBlockA01 -->


<!--flexBlockB06 -->
<div class="flexBlockB06">
	<div class="flexBlockB06Inner clear">
		<!--btnBlockC14 -->
		<div class="btnBlockC14">
			<div class="btnBlockC14Inner">
				<div class="btnBlockC14Inner2">
					<p>	<a href="javascript:initReformImg();">
					<span>編&nbsp;&nbsp;集</span></a></p>
				</div>
			</div>
		</div>
		<!--btnBlockC14 -->
	</div>
</div>
<!--flexBlockB06 -->
</c:if>

</form>
</c:param>
</c:import>
