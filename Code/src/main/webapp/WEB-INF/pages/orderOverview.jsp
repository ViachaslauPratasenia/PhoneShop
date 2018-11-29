<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<jsp:useBean id="order" scope="request" type="com.es.phoneshop.model.order.Order"/>
<jsp:useBean id="currency" scope="request" type="java.util.Currency"/>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Order Overview Page</title>
    <%@include file="../common/header.jsp" %>
</head>
<body>
<fmt:setBundle basename="i18n.messages" var="lang"/>
<h2 style="text-align: center;text-shadow:1px 1px 0 #444">Order Information:</h2>
<div class="w3-container w3-cell-row">
    <div class="w3-container w3-cell w3-mobile w3-centered">
        <h2 style="text-align: center;text-shadow:1px 1px 0 #444">Items:</h2>
        <table class="w3-table-all w3-card-4 w3-hoverable w3-centered w3-left">
            <tr class="w3-indigo">
                <th>Code</th>
                <th>Price</th>
                <th>Quantity</th>
            </tr>
            <c:forEach var="orderItem" items="${order.cartItems}">
                <tr>
                    <td>${orderItem.product.code}</td>
                    <td>${orderItem.product.price} ${orderItem.product.currency}</td>
                    <td>${orderItem.quantity}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="w3-container w3-cell w3-mobile w3-centered">
        <h2 style="text-align: center;text-shadow:1px 1px 0 #444">General:</h2>
        <table class="w3-table-all w3-card-4 w3-hoverable w3-centered w3-right">
            <tr class="w3-indigo">
                <th>Order Id</th>
                <th>Total Cost</th>
            </tr>
            <tr>
                <td>${order.orderId}</td>
                <th>${order.totalCost} ${currency}</th>
            </tr>
        </table>
        <h2 style="text-align: center;text-shadow:1px 1px 0 #444">Customer's Data:</h2>
        <table class="w3-table-all w3-card-4 w3-hoverable w3-centered w3-right">
            <tr class="w3-indigo">
                <th>Name</th>
                <th>Address</th>
                <th>Phone</th>
            </tr>
            <tr>
                <td>${order.name}</td>
                <td>${order.address}</td>
                <td>${order.phone}</td>
            </tr>
        </table>
    </div>
</div>
<%@include file="../common/footer.jsp" %>
</body>
</html>
