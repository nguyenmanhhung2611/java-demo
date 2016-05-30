<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- ----------------------------------------------------------------
 名称： リフォーム画像編集画面

 2015/04/04		fan		新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
<c:param name="pageTitle" value="リフォーム画像編集" />
<c:param name="contents">

<form action="uploadConfirm" enctype="multipart/form-data"  method="post" name="inputForm">
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>リフォーム画像登録</h2>
		</div>

		<c:import url="/WEB-INF/jsp/admin/include/reformImg/inputForm1.jsh" />

	</div>
	<!--/headingAreaInner -->
<!--flexBlockB03 -->
<div class="flexBlockB03">
    <!--btnBlockC02 -->
    <div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
                <p><a href="javascript:linkToUrl('../confirm/', 'insert');"><span>登録</span></a></p>
            </div>
        </div>
    </div>
    <!--/btnBlockC02 -->

	<!--btnBlockC02 -->
	<div class="btnBlockC14">
        <div class="btnBlockC14Inner">
            <div class="btnBlockC14Inner2">
				<p><a href="javascript:linkToUrlBack('../../input/', 'back');"><span>戻る</span></a></p>
			</div>
		</div>
	</div>
	<!--/btnBlockC02 -->
</div>
<!--/flexBlockB03 -->
<br>
<br>
	<!--headingAreaInner -->
	<div class="headingAreaInner">
		<div class="headingAreaB01 start">
			<h2>リフォーム画像編集</h2>
		</div>

		<c:import url="/WEB-INF/jsp/admin/include/reformImg/inputForm2.jsh" />
	</div>
	<!--/headingAreaInner -->
	    <!--flexBlockB03 -->
    <div class="flexBlockB03">
            <!--btnBlockC02 -->
            <c:if test="${ReformImgForm.divNo != null}">
            <div class="btnBlockC14">
                <div class="btnBlockC14Inner">
                    <div class="btnBlockC14Inner2">
                        <p><a href="javascript:linkToUrl('../confirm/', 'update');"><span>登録</span></a></p>
                    </div>
                </div>
            </div>
            </c:if>
            <c:if test="${ReformImgForm.divNo == null}">
            <div class="btnBlockC14">
                <div class="btnBlockC14Inner">
                    <div class="btnBlockC14Inner2">
                        <p><a disabled="disabled"><span>登録</span></a></p>
                    </div>
                </div>
            </div>
            </c:if>
            <!--/btnBlockC02 -->

            <!--btnBlockC02 -->
           	<div class="btnBlockC14">
                <div class="btnBlockC14Inner">
                    <div class="btnBlockC14Inner2">
                       <p><a href="javascript:linkToUrlBack('../../input/', 'back');"><span>戻る</span></a></p>
                    </div>
                </div>
            </div>
            <!--/btnBlockC02 -->
    </div>
    <!--/flexBlockB03 -->
</form>
</c:param>
</c:import>