<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="errors" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="success" type="java.lang.Boolean" scope="session"/>
<jsp:useBean id="remove" type="java.lang.Boolean" scope="session"/>

<jsp:useBean id="successHead" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="cartUpdateSuccess" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="cartItemRemoveSuccess" type="java.lang.Integer" scope="request"/>
<jsp:useBean id="bundleNames" type="java.lang.String[]" scope="request"/>

<script src="${pageContext.request.contextPath}/common/setCaretPosition.js"></script>
<script src="${pageContext.request.contextPath}/common/onLoad.js"></script>

<html>
<head>
    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Your Cart Page</title>
    <%@include file="../common/header.jsp" %>
</head>
<body>

<fmt:setBundle basename="i18n.messages" var="lang"/>

<c:if test="${remove}">
    <div class="w3-panel w3-green w3-small w3-display-container">
        <span onclick="this.parentElement.style.display='none'"
              class="w3-button w3-hover-pink w3-green w3-small w3-display-topright">&times;</span>
        <h3>
            <fmt:message key="${bundleNames[successHead]}" bundle="${lang}"/>
        </h3>
        <p><fmt:message key="${bundleNames[cartItemRemoveSuccess]}" bundle="${lang}"/></p>
    </div>
</c:if>
<c:choose>
    <c:when test="${cart.cartItems.isEmpty()}">
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">Your Cart is Empty, try to add something and come back =)</b>
        </div>
    </c:when>
    <c:otherwise>
        <div style="text-align: center">
            <b style="text-shadow:1px 1px 0 #444">Products added to cart:</b>
        </div>
        <c:if test="${success}">
            <div class="w3-panel w3-green w3-small w3-display-container">
                <span onclick="this.parentElement.style.display='none'"
                      class="w3-button w3-hover-pink w3-green w3-small w3-display-topright">&times;</span>
                <h3>
                    <fmt:message key="${bundleNames[successHead]}" bundle="${lang}"/>
                </h3>
                <p><fmt:message key="${bundleNames[cartUpdateSuccess]}" bundle="${lang}"/></p>
            </div>
        </c:if>
        <form method="post" name="quantities">
            <table class="w3-table-all w3-hoverable w3-centered">
                <tr class="w3-indigo">
                    <th>Id</th>
                    <th>Code</th>
                    <th>Price</th>
                    <th>Currency</th>
                    <th>Stock</th>
                    <th>Quantity</th>
                    <th></th>
                </tr>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                    <tr>
                        <td>
                            <a href="<c:url value="/products/product/${cartItem.product.id}"/>">${cartItem.product.id}</a>
                        </td>
                        <td>${cartItem.product.code}</td>
                        <td>${cartItem.product.price}</td>
                        <td>${cartItem.product.currency}</td>
                        <td>${cartItem.product.stock}</td>
                        <td>
                            <input type="hidden" name="productId" value="${cartItem.product.id}">
                            <label>
                                <input type="text" name="newQuantity" id="inputQuantity" value="${cartItem.quantity}"
                                       autofocus style="text-align: right">
                            </label>
                            <c:if test="${not empty errors[status.index]}">
                                <div class="w3-panel w3-red w3-small w3-display-container">
                                    <span onclick="this.parentElement.style.display='none'"
                                          class="w3-button w3-hover-pink w3-red w3-small w3-display-topright">&times;</span>
                                    <p><fmt:message key="${bundleNames[errors[status.index]]}" bundle="${lang}"/></p>
                                </div>
                            </c:if>
                        </td>
                        <td>
                            <input type="submit" name="remove_${cartItem.product.id}"
                                   class="w3-button w3-round-medium w3-teal w3-light-green w3-hover-red" value="Delete">
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <p><input type="submit" class="w3-right w3-button w3-teal w3-round-medium w3-light-green" value="Update"></p>
        </form>
        <a class="w3-right w3-button w3-teal w3-round-medium w3-light-green w3-margin-right" href="${pageContext.request.contextPath}/checkout">Checkout</a>
    </c:otherwise>
</c:choose>
<%@include file="../common/footer.jsp" %>
</body>
</html>