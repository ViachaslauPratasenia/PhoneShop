<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<jsp:useBean id="wait" type="java.lang.Integer" scope="request"/>
<html>
<head>
    <title>Too many requests</title>
    <%@include file="../../common/header.jsp" %>
</head>
<body>
<h1>Error 429</h1>
<h2>You have requested this page more often than allowed</h2>
<p>You can wait ${wait} seconds and try again(reload the page)</p>
<%@include file="../../common/footer.jsp" %>
</body>
</html>
