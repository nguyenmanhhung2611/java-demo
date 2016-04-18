<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/custom.tld" prefix="customTag" %>
<%@ page session="false" %>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<p><customTag:convertText number="${number}" />
<p><customTag:outPutCurrentText>This is demo custom tag</customTag:outPutCurrentText>
</body>
</html>
