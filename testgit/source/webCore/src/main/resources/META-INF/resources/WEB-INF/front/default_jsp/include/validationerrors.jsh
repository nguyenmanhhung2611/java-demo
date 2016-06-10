<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<c:if test="${fn:length(errors)>0}">
	<div class="generalBlockA01 mb20 spMb15">
		<p>入力項目にエラーがあります。お手数ですが、再度ご確認ください。</p>
		<ul>
</c:if>
<c:forEach items="${errors}" var="thisError">
  <c:set var="message"><dm3lookup:lookup lookupName="errorCustom" lookupKey="${thisError.name}_${thisError.type}"/></c:set>
  <c:if test="${message == ''}">
    <c:set var="message"><dm3lookup:lookup lookupName="errorTemplates" lookupKey="${thisError.type}"/></c:set>
    <c:if test="${message == ''}">
      <c:set var="message">Unknown error: field=[#fieldName], type=[#errorType], value=[#fieldValue]</c:set>
    </c:if>
  </c:if>

  <c:set var="label"><dm3lookup:lookup lookupName="errorLabels" lookupKey="${thisError.name}"/></c:set>
  <c:if test="${label == ''}">
    <c:set var="label"><c:out value="${thisError.name}"/></c:set>
  </c:if>

    <c:if test="${thisError.getClass().name == 'jp.co.transcosmos.dm3.core.validation.LineValidationFailure'}" >
      <c:out value="${thisError.lineNo}" />行目
    </c:if>
    <li><c:out value="${fn:replace(fn:replace(fn:replace(fn:replace(message, '[#fieldName]', label), '[#errorType]', thisError.type), '[#fieldValue]', thisError.value), '[#extraInfo]', thisError.extraInfo[0])}"/>

</c:forEach>
<c:if test="${fn:length(errors)>0}"></ul></div></c:if>
<c:if test="${fn:length(errors)>0}"><br></c:if>