<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>List of Products</title>
    <%@ include file="../common/header.jsp" %>
</head>
<body>
<table class="w3-table-all w3-card-4 w3-hoverable w3-centered">
    <tr class="w3-indigo">
        <th>Id</th>
        <th>Code</th>
        <th>Description</th>
        <th>Price</th>
        <th>Stock</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.getId()}</td>
            <td><a href="<c:url value="/products/product/${product.id}"/>">${product.getCode()}</a></td>
            <td>${product.getDescription()}</td>
            <td>${product.getPrice()} ${product.getCurrency()}</td>
            <td>${product.getStock()}</td>
        </tr>
    </c:forEach>
</table>
<%@include file="../common/footer.jsp" %>
</body>
</html>