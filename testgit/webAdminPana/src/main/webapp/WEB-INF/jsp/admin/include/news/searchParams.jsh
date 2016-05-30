<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
邵ｺ鬘碑｡咲ｹｧ蟲ｨ笳狗ｹ晢ｽ｡郢晢ｽｳ郢晢ｿｽ郢晉ｿｫﾎｦ郢ｧ�ｽｹ隶匁ｺｯ�ｿｽ�ｽｽ邵ｺ�ｽｧ隰問�壺蔓邵ｺ�ｽｾ郢ｧ荳奇ｽ玖ｮ�諛�ｽｴ�ｽ｢隴夲ｽ｡闔会ｽｶ邵ｺ�ｽｮ hidden 陷�ｽｺ陷会ｿｽ
--%>
<input type="hidden" name="searchCommand" value="<c:out value="${searchForm.searchCommand}"/>">
<input type="hidden" name="keyNewsId" value="<c:out value="${searchForm.keyNewsId}"/>">
<input type="hidden" name="keyNewsTitle" value="<c:out value="${searchForm.keyNewsTitle}"/>">
<input type="hidden" name="keyNewsContent" value="<c:out value="${searchForm.keyNewsContent}"/>">
<input type="hidden" name="selectedPage" value="<c:out value="${searchForm.selectedPage}"/>">