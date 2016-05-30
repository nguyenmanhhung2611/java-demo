<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login"
	prefix="dm3login"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup"
	prefix="dm3lookup"%>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>

<%-- ----------------------------------------------------------------
 名称： 物件一覧画面

 2015/03/04		TRANS	新規作成
 2015/08/15		Duong.Nguyen	Add CSRF token to deleting link
 ---------------------------------------------------------------- --%>
<c:import url="/WEB-INF/jsp/admin/layout/layout.jsh">
	<c:param name="htmlTitle" value="${commonParameters.adminPageTitle}" />
	<c:param name="pageTitle" value="物件一覧画面" />
	<c:param name="contents">
		<script type="text/JavaScript">
			function CsvOut(url) {
				document.housingform.action = url;
				document.housingform.target = "_blank";
				document.housingform.submit();
				document.housingform.target = "_self";
			}
			function Search(url) {
				document.housingform.action = url;
				document.housingform.submit();
			}
			function newlist(url) {
				document.inputdate.action = url;
				document.inputdate.submit();
			}
			function linkToUpd(url, sysHousingCd) {
				document.inputdate.action = url;
				document.inputdate.sysHousingCd.value = sysHousingCd;
				document.inputdate.command.value = 'update';
				document.inputdate.submit();
			}
			function linkToDel(url, sysHousingCd) {
				if (confirm("削除を行います。よろしいですか？")) {
					document.inputdate.action = url;
					document.inputdate.sysHousingCd.value = sysHousingCd;
					document.inputdate.command.value = 'delete';
					document.inputdate.submit();
				}
			}
			function popupMember() {
				var url = '../../mypage/search/';
		    	window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
			}
			function KeyForm(page) {
				document.inputdate.action = '';
				document.inputdate.selectedPage.value = page;
				document.inputdate.command.value = 'list';
				document.inputdate.submit();
			}
		</script>

		<!--headingAreaInner -->
		<div class="headingAreaInner">
			<div class="headingAreaB01 start">
				<h2>物件の検索</h2>
			</div>
			<form action="" method="post" name="housingform">
				<input type="hidden" name="command" value="list" />
				<input type="hidden" name="searchCommand" value="<c:out value="${command}"/>">
				<input type="hidden" name="sysHousingCd" value="" />
				<!--flexBlockA01 -->
				<div class="flexBlockA01">
					<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
						<tr>
							<th class="head_tr" width="20%" >物件番号</th>
							<td width="80%"><input name="keyHousingCd" value="<c:out value="${searchForm.keyHousingCd}"/>" type="text" size="15" maxlength="10" class="input2 ime-disabled"></td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">物件名</th>
							<td width="80%"><input name="keyDisplayHousingName" value="<c:out value="${searchForm.keyDisplayHousingName}"/>" type="text" size="15" maxlength="25" class="input2">&nbsp;&nbsp;&nbsp;を含む</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">都道府県</th>
							<td width="80%">
							<select name="keyPrefCd">
								<option></option>
								<c:forEach items="${prefMst}" var="prefMst">
									<option value="<c:out value="${prefMst.prefCd}"/>" <c:if test="${prefMst.prefCd == searchForm.keyPrefCd}"> selected</c:if>><c:out value="${prefMst.prefName}"/></option>
								</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">登録日</th>
							<td width="80%">
								<input name="keyInsDateStart" value="<c:out value="${searchForm.keyInsDateStart}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled">
								～
								<input name="keyInsDateEnd" value="<c:out value="${searchForm.keyInsDateEnd}"/>" type="text" size="10" maxlength="10" class="input2 ime-disabled">
								(yyyy/mm/dd)
							</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">更新日時</th>
							<td width="80%">
								<dm3lookup:lookupForEach lookupName="housingListUpdDate">
									<label>
										<input type="radio" name="keyUpdDate" value="<c:out value="${key}"/>" <c:if test="${searchForm.keyUpdDate == key}">checked</c:if>><c:out value="${value}"/>
									</label>&nbsp;&nbsp;
								</dm3lookup:lookupForEach>
							</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">会員番号</th>
							<td width="80%">
								<div class="inputStyle inputStyle2">
									<input id="keyUserId" name="keyUserId" value="<c:out value="${searchForm.keyUserId}"/>" type="text" size="10" maxlength="20" class="input2 ime-disabled">
								</div>
					    		<!--btnBlockC11 -->
					    		<div class="btnBlockC11">
					        		<div class="btnBlockC11Inner">
					            		<div class="btnBlockC11Inner2">
											<p><a class="tblSmallBtn" href="javascript:popupMember();"><span>参照</span></a></p>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">公開区分</th>
							<td width="80%">
								<dm3lookup:lookupForEach lookupName="hiddenFlg">
									<label>
										<input type="radio" name="keyHiddenFlg" value="<c:out value="${key}"/>" <c:if test="${searchForm.keyHiddenFlg == key}">checked</c:if>><c:out value="${value}"/>
									</label>&nbsp;&nbsp;
								</dm3lookup:lookupForEach>
							</td>
						</tr>
						<tr>
							<th class="head_tr" width="20%">ステータス</th>
							<td width="80%"><select name="keyStatusCd">
								<option value=""></option>
								<dm3lookup:lookupForEach lookupName="statusCd">
									<option value="<c:out value="${key}"/>" <c:if test="${searchForm.keyStatusCd == key}">selected</c:if>><c:out value="${value}"/></option>&nbsp;
								</dm3lookup:lookupForEach>
							</select></td>
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
								<a href="javascript:newlist('../detail/');"><span>新規追加</span></a>
							</p>
						</div>
					</div>
				</div>
				<!--btnBlockC14 -->
				<div class="btnBlockC14">
					<div class="btnBlockC14Inner">
						<div class="btnBlockC14Inner2">
							<p>
								<a href="javascript:Search('../list/');"><span>検&nbsp;&nbsp;索</span></a>
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
                                		<a href="javascript:CsvOut('../csv/');"><span>CSV 出力</span></a>
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
                                		<a disabled="disabled"><span>CSV 出力</span></a>
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

		<div class="headingAreaInner">
			<p>
				<c:import
					url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" />
				<c:if test="${hitcont == 0}">
					<span class="errorMessage">検索結果が１件も取得できません、再度条件を見直し検索を行ってください。</span>
				</c:if>
			</p>
		</div>


		<div class="headingAreaInner">
			<!-- 初期画面表示判定 -->
			<c:if test="${hitcont != 0 && hitcont != null}">
			<div class="headingAreaB01 start">
				<h2>物件一覧</h2>
			</div>

			<!--flexBlockB07 -->
			<div class="flexBlockB07">
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
						<td width="23%">物件番号</td>
						<td width="22%">物件名</td>
						<td width="25%">物件住所</td>
						<td width="13%">ステータス</td>
						<td width="17%">&nbsp;</td>
					</tr>
					<!-- 検索結果画面表示判定 -->
					<c:forEach items="${searchForm.visibleRows}" var="housingInfoList">
						<c:set var="housingInfo" value="${housingInfoList.getHousingInfo().items['housingInfo']}" />
						<c:set var="buildingInfo" value="${housingInfoList.getBuilding().getBuildingInfo().items['buildingInfo']}" />
						<c:set var="housingStatusInfo" value="${housingInfoList.getHousingInfo().items['housingStatusInfo']}" />
						<c:set var="prefMst" value="${housingInfoList.getHousingInfo().items['prefMst']}" />
						<tr>
							<td width="23%"><a href="javascript:linkToUpd('../detail/<c:out value="${housingInfo.sysHousingCd}"/>/');"><c:out value="${housingInfo.housingCd}"/></a>&nbsp;
							</td>
							<td width="22%"><c:out value="${housingInfo.displayHousingName}" />&nbsp;</td>
							<td width="25%"><c:out value="${prefMst.prefName}" /><c:out value="${buildingInfo.addressName}" /><c:out value="${buildingInfo.addressOther1}" /><c:out value="${buildingInfo.addressOther2}" />&nbsp;</td>
							<td width="13%">
								<dm3lookup:lookupForEach lookupName="statusCd">
									<c:if test="${housingStatusInfo.statusCd == key}"><c:out value="${value}"/></c:if>
								</dm3lookup:lookupForEach>&nbsp;
							</td>
							<td width="17%">
								<div class="btnBlockC11">
									<div class="btnBlockC11Inner">
										<div class="btnBlockC11Inner2">
											<p>
												<a href="javascript:linkToUpd('../detail/<c:out value="${housingInfo.sysHousingCd}"/>/');" class="tblSmallBtn"><span>編集</span></a>
											</p>
										</div>
									</div>
								</div>
								<dm3login:hasRole roleName="admin">
								<div class="btnBlockC12">
									<div class="btnBlockC12Inner">
										<div class="btnBlockC12Inner2">
											<p>
												<a href="javascript:linkToDel('../delete/','<c:out value="${housingInfo.sysHousingCd}"/>');" class="tblSmallBtn"><span>削除</span></a>
											</p>
										</div>
									</div>
								</div>
								</dm3login:hasRole>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<!--/flexBlockB07 -->
			</c:if>

		</div>
		<%-- 入力formパラメータ引き継ぎ --%>
		<form action="" method="post" name="inputdate">
			<input type="hidden" name="command" value="">
			<input type="hidden" name="sysHousingCd" value="" />
			<c:import url="/WEB-INF/jsp/admin/include/housingList/searchParams.jsh" />
			<dm3token:oneTimeToken/>
		</form>

	</c:param>
</c:import>