<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/functions" prefix="dm3functions" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/token" prefix="dm3token" %>
<%-- 
お知らせメンテナンス機能で使用する情報確認画面（詳細閲覧、削除確認）の出力 
--%>

<!--flexBlockA01 -->
<c:set var="information" value="${informationDetail.items['information']}" />
<c:set var="informationTarget" value="${informationDetail.items['informationTarget']}" />
<c:set var="memberInfo" value="${informationDetail.items['memberInfo']}" />
<div class="flexBlockA01">
	<table class="confirmBox">
		<tr>
			<th class="head_tr">お知らせ番号</th>
			<td width="200"><c:out value="${information.informationNo}"/>&nbsp;</td>
			<th class="head_tr" width="50">登録日時</th>
			<td width="100">
			    <div style="white-space: nowrap"><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${information.insDate}"/>&nbsp</div>
			</td>
		</tr>
		<tr>
			<th class="head_tr">種別</th>
			<td colspan="3"><dm3lookup:lookup lookupName="information_type" lookupKey="${information.informationType}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">公開対象区分</th>
			<td colspan="3">
				<dm3lookup:lookup lookupName="information_dspFlg" lookupKey="${information.dspFlg}"/>
				<%-- 個人指定した場合、個乳の入力属性を表示する。 コードがハードコーディングされているので、コード変更時は注意する事。--%>
				<c:if test="${information.dspFlg == '2'}">
								&nbsp;&nbsp;（<c:out value="${informationTarget.userId}"/>：<c:out value="${memberInfo.memberLname}"/>&nbsp;<c:out value="${memberInfo.memberFname}"/>）
				</c:if>
			</td>
		</tr>
		<tr>
			<th class="head_tr">タイトル</th>
			<td colspan="3"><c:out value="${information.title}"/>&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">お知らせ内容</th>
			<td colspan="3">${dm3functions:crToHtmlTag(information.informationMsg)}&nbsp;</td>
		</tr>
		<tr>
			<th class="head_tr">表示期間</th>
			<td colspan="3">
				<c:if test="${!empty information.startDate || !empty information.endDate}">
				<fmt:formatDate value="${information.startDate}" pattern="yyyy/MM/dd" />
				～
				<fmt:formatDate value="${information.endDate}" pattern="yyyy/MM/dd" /><br/>
				</c:if>
				<font color="red">※表示期間に指定がない場合、登録後すぐに表示開始し、また永久に教示されます。</font>&nbsp;
			</td>
		</tr>
		<tr>
			<th class="head_tr">リンク先URL</th>
			<td colspan="3"><a href="<c:out value="${information.url}"/>" target="_blank"><c:out value="${information.url}"/></a>&nbsp;</td>
		</tr>
	</table>
</div>
<!--/flexBlockA01 -->

<%-- お知らせ入力formパラメータ引き継ぎ --%>
<form method="post" name="inputForm" action="../list/">
	<input type="hidden" name="command" value="list">
	<input type="hidden" name="informationNo" value="<c:out value="${inputForm.informationNo}"/>">
	<c:import url="/WEB-INF/admin/default_jsp/include/information/searchParams.jsh" />
	<dm3token:oneTimeToken/>
</form>