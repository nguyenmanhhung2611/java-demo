<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/login" prefix="dm3login" %>
<%--
物件閲覧_新規登録画面で使用する入力フォームの出力
--%>
<p><c:import url="/WEB-INF/admin/default_jsp/include/validationerrors.jsh" /></p>

<!--物件基礎情報 -->
<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="18%"/>
			<col width="32%"/>
			<col width="18%"/>
			<col width="32%"/>
		</colgroup>
		<tr>
			<th class="head_tr">物件番号</th>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">登録日時</th>
			<td>&nbsp;</td>
			<th class="head_tr">更新日時</th>
			<td>&nbsp;</td>
		</tr>
	</table>
</div>

<!--ステータス編集 -->
<br>
<div class="headingAreaB01 start">
	<h2>ステータス編集</h2>
</div>

<div class="flexBlockA01">
	<table width="100%" border="1" cellspacing="0" cellpadding="0" class="tableA1">
		<colgroup>
			<col width="18%"/>
			<col width="15%"/>
			<col width="15%"/>
			<col width="15%"/>
			<col width="15%"/>
			<col width="22%"/>
		</colgroup>
		<tr>
			<th class="head_tr">物件名称<font color="red">※</font></th>
			<td colspan="5">
			   <input type="text" name="displayHousingName" value="<c:out value="${inputForm.displayHousingName}"/>" maxlength="25"  size="46" class="input2" >
			</td>
		</tr>
		<tr>
			<th class="head_tr">物件種別<font color="red">※</font></th>
			<td colspan="5">
			   <dm3lookup:lookupForEach lookupName="buildingInfo_housingKindCd">
				 <input type="radio" name="housingKindCd" value="<c:out value="${key}"/>"<c:if test="${inputForm.housingKindCd == key}">checked</c:if>>
				 <c:out value="${value}"/>&nbsp;
			   </dm3lookup:lookupForEach>
			</td>
		</tr>

		<tr>
			<th class="head_tr">公開区分<font color="red">※</font></th>
			<td colspan="5">
				<c:if test="${inputForm.readonlyFlg != 1}">
                   <dm3lookup:lookupForEach lookupName="hiddenFlg">
                       <input type="radio" name="hiddenFlg" value="<c:out value="${key}" />"
					<c:if test="${inputForm.hiddenFlg == key}"> checked</c:if> ><c:out value="${value}"/>
					&nbsp;&nbsp;
                   </dm3lookup:lookupForEach>
				</c:if>
				<c:if test="${inputForm.readonlyFlg == 1}">
				<input type="hidden" name="hiddenFlg" value="<c:out value="${inputForm.hiddenFlg}"/>">
                   <dm3lookup:lookupForEach lookupName="hiddenFlg">
                       <input type="radio" name="hiddenFlg" value="<c:out value="${key}" />"
					<c:if test="${inputForm.hiddenFlg == key}"> checked</c:if> disabled="disabled"><c:out value="${value}"/>
					&nbsp;&nbsp;
                   </dm3lookup:lookupForEach>
				</c:if>
			</td>
		</tr>

		<tr>
			<th class="head_tr">ステータス<font color="red">※</font></th>
			<td colspan="5">
				<select name="statusCd">
					<option></option>
				<dm3lookup:lookupForEach lookupName="statusCd">
					<option value="<c:out value="${key}"/>"<c:if test="${inputForm.statusCd==key}"> selected </c:if>><c:out value="${value}"/></option>
				</dm3lookup:lookupForEach>
				</select>
			</td>
		</tr>
		<tr>
			<th class="head_tr">会員番号</th>
			<td colspan="5">
				<div class="inputStyle inputStyle2">
					<input id="keyUserId" name="userId" value="<c:out value="${inputForm.userId}"/>" readonly = "readonly" type="text" size="13" maxlength="20" class="input2">
				</div>
	    		<!--btnBlockC11 -->
	    		<div class="btnBlockC11">
	        		<div class="btnBlockC11Inner">
	            		<div class="btnBlockC11Inner2">
							<p><a class= "tblSmallBtn" href="javascript:openWindow('<c:out value="${pageContext.request.contextPath}"/>/top/mypage/search/');"><span>参照</span></a></p>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th class="head_tr">氏名</th>
			<td id="memberNameTd">
				<c:out value="${inputForm.memberName}"/>
				&nbsp;
			</td>
			<th class="head_tr">電話番号</th>
			<td id="telTd">
				<c:out value="${inputForm.tel}"/>
				&nbsp;
			</td>
			<th class="head_tr">メールアドレス</th>
			<td id="emailTd">
				<c:out value="${inputForm.email}"/>
				&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">備考（管理者用）<br><p style="font-size: 85%">（200文字）</p></th>
			<td colspan="5">
				<textarea cols="50" rows="3" name="note" ><c:out value="${inputForm.note}"/></textarea>
			</td>
		</tr>
	</table>
</div>

<%-- ユーザ編集入力formパラメータ引き継ぎ --%>
<input type="hidden" name="command" value="<c:out value="${inputForm.command}"/>">
<input type="hidden" name="readonlyFlg" value="<c:out value="${inputForm.readonlyFlg}"/>">
<input type="hidden" name="memberName" value="<c:out value="${inputForm.memberName}"/>">
<input type="hidden" name="tel" value="<c:out value="${inputForm.tel}"/>">
<input type="hidden" name="email" value="<c:out value="${inputForm.email}"/>">

<script type ="text/JavaScript">
<!--
	function linkToUrl(url, cmd) {
		document.inputForm.action = url;
		document.inputForm.command.value = cmd;
		document.inputForm.submit();
	}

	function openWindow(url) {
		window.open(url,'newWindow','width=850px,height=720px,scrollbars=yes,location=no,directories=no,status=no');
	}
// -->
</script>

