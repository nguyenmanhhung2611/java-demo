<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions"
	prefix="dm3functions"%>
<%-- ----------------------------------------------------------------
 名称： 問合ステータス編集確認画面

 2015/03/09		荊学ギョク	新規作成
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="問合ステータス編集確認" />
	<c:param name="contents">

		<!--headingAreaInner -->
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>問合ステータス編集確認</h2>
			</div>

			<c:import url="/WEB-INF/jsp/admin/include/inquiryStatus/confirm.jsh" />

		</div>
		<!--/headingAreaInner -->

		<!--flexBlockB06 -->
		<div class="flexBlockB06">
			<div class="flexBlockB06Inner clear">
				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:linkToUrl('../result/', '');"><span>登&nbsp;&nbsp;録</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockC14 -->

				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:back('../input/', 'back');"><span>戻&nbsp;&nbsp;る</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--/btnBlockC14 -->
			</div>
		</div>
		<!--/flexBlockB06 -->

	</c:param>
</c:import>