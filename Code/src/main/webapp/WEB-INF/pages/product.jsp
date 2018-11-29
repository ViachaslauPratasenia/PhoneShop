<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" scope="request" type="com.es.phoneshop.model.product.Product"/>

<jsp:useBean id="messageCode" scope="session" type="java.lang.Integer"/>
<jsp:useBean id="quantity" scope="session" class="java.lang.String"/>

<jsp:useBean id="defaultCode" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="success" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="successHead" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="errorHead" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="bundleNames" scope="request" type="java.lang.String[]"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<script src="${pageContext.request.contextPath}/common/setCaretPosition.js"></script>
<script src="${pageContext.request.contextPath}/common/onLoad.js"></script>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Details</title>
    <%@include file="../common/header.jsp" %>
</head>
<body>
<fmt:setBundle basename="i18n.messages" var="lang"/>
<c:if test="${messageCode ne defaultCode}">
    <c:choose>
        <c:when test="${messageCode eq success}">
            <div class="w3-panel w3-green w3-display-container">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-green w3-large w3-display-topright">&times;</span>
                <h3><fmt:message key="${bundleNames[successHead]}" bundle="${lang}"/></h3>
                <p><fmt:message key="${bundleNames[success]}" bundle="${lang}"/></p>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${messageCode ne defaultCode}">
                <div class="w3-panel w3-red w3-display-container">
                    <span onclick="this.parentElement.style.display='none'"
                          class="w3-button w3-red w3-large w3-display-topright">&times;</span>
                    <h3><fmt:message key="${bundleNames[errorHead]}" bundle="${lang}"/></h3>
                    <p><fmt:message key="${bundleNames[messageCode]}" bundle="${lang}"/></p>
                </div>
            </c:if>
        </c:otherwise>
    </c:choose>
</c:if>
<form method="post">
    <table class="w3-table-all w3-hoverable w3-centered w3-border-pale-blue">
        <tr class="w3-indigo">
            <th>Id</th>
            <th>Code</th>
            <th>Description</th>
            <th>Price</th>
            <th>Currency</th>
            <th>Stock</th>
            <th>Quantity</th>
            <th>
                <input class="w3-button w3-teal w3-round-medium w3-light-green" type="submit" value="Add to Cart">
            </th>
        </tr>
        <tr>
            <td>${product.id}</td>
            <td>${product.code}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${product.currency}</td>
            <td>${product.stock}</td>
            <td>
                <label>
                    <input type="text" name="quantity" id="inputQuantity" autofocus
                           value="${empty quantity ? 1 : quantity}" style="text-align: right">
                </label>
            </td>
            <td></td>
        </tr>
    </table>
</form>
<%@include file="../common/footer.jsp" %>
</body>
</html>