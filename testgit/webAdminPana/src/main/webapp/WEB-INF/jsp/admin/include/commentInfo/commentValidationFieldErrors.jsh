<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://dm3.transcosmos.co.jp/tags/lookup" prefix="dm3lookup" %>

<c:set var="dm3hasError" value="false" scope="request"/>
<c:forEach items="${errors}" var="thisError">
  <c:if test="${thisError.name == param.targetLabel}">
    <c:if test="${dm3hasError == false}">
      <c:set var="dm3hasError" value="true" scope="request"/>
<div class="errorTxt01">
    </c:if>

    <c:set var="message"><dm3lookup:lookup lookupName="errorTemplates" lookupKey="${thisError.type}"/></c:set>
    <c:if test="${message == ''}">
      <c:set var="message">Unknown error: field=[#fieldName], type=[#errorType], value=[#fieldValue]</c:set>
    </c:if>
    <c:if test="${fn:startsWith(message,'・')}">
      <c:set var="message" value="${fn:substring(message,1,fn:length(message))}"/>
    </c:if>

    <c:set var="label"><dm3lookup:lookup lookupName="errorLabels" lookupKey="${thisError.name}"/></c:set>
    <c:if test="${label == ''}">
      <c:set var="label"><c:out value="${thisError.name}"/></c:set>
    </c:if>

    <c:out value="${fn:replace(fn:replace(fn:replace(fn:replace(message, '[#fieldName]', label), '[#errorType]', thisError.type), '[#fieldValue]', thisError.value), '[#extraInfo]', thisError.extraInfo[0])}"/><br/>
  </c:if>
</c:forEach>
<c:if test="${dm3hasError == true}">
</div>
</c:if>