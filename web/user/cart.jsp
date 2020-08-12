<%-- 
    Document   : cart
    Created on : Jun 14, 2020, 12:03:30 AM
    Author     : Mk
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dream Travelling - Cart</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/cart.css"/>
    </head>
    <body>
        <%@include file="/template/header.jsp"%>
        <div id="content">
            <c:if test="${requestScope.ALERT != null}">
                <font color="red">${requestScope.ALERT}</font>
            </c:if>
            <c:if test="${requestScope.CART != null}" var="isNotNull">
                <c:if test="${not empty requestScope.CART}" var="hasItem">
                    <table cellpadding="5">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>From</th>
                                <th>To</th>
                                <th>Price</th>
                                <th>Amount</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.CART}" var="tour">
                                <c:if test="${tour.statusString == 'available'}" var="isAvailable">
                                    <tr>
                                        <td>${tour.name}</td>
                                        <td>${tour.simpleFromDate}</td>
                                        <td>${tour.simpleToDate}</td>
                                        <td>$${tour.price}</td>
                                        <td>
                                            <form action="manageCart" method="POST" class="minusButton" <c:if test="${pageScope.tour.amount == 1}">onclick="return confirm('Are you sure to remove this item from the cart?')"</c:if>>
                                                    <input type="hidden" name="sentFrom" value="cart"/>
                                                    <input type="hidden" name="id" value="${pageScope.tour.id}"/>
                                                <input type="hidden" name="action" value="decrease"/>
                                                <input class="button" type="submit" value="-"/>
                                            </form>
                                            <p class="amount">${tour.amount}</p>
                                            <form action="manageCart" method="POST" class="plusButton">
                                                <input type="hidden" name="sentFrom" value="cart"/>
                                                <input type="hidden" name="id" value="${pageScope.tour.id}"/>
                                                <input type="hidden" name="action" value="add"/>
                                                <input class="button" type="submit" value="+"/>
                                            </form>
                                        </td>
                                        <td>$${tour.total}</td>
                                        <td>
                                            <form action="manageCart" method="POST" onclick="return confirm('Are you sure to remove this item from the cart?')">
                                                <input type="hidden" name="sentFrom" value="cart"/>
                                                <input type="hidden" name="id" value="${pageScope.tour.id}"/>
                                                <input type="hidden" name="action" value="remove"/>
                                                <button type="submit" class="removeButton">Remove</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:if>
                                <c:if test="${!isAvailable}">
                                <p>Tour "${tour.name}" in your cart is no longer available</p>
                                <tr class="unavailable">
                                    <td><p>${tour.name}</p></td>
                                    <td><p>${tour.simpleFromDate}</p></td>
                                    <td><p>${tour.simpleToDate}</p></td>
                                    <td><p>$${tour.price}</p></td>
                                    <td><p>${tour.amount}</p></td>
                                    <td><p>$${tour.total}</p></td>
                                    <td>
                                        <form action="manageCart" method="POST">
                                            <input type="hidden" name="sentFrom" value="cart"/>
                                            <input type="hidden" name="id" value="${tour.id}"/>
                                            <input type="hidden" name="action" value="remove"/>
                                            <button type="submit" class="removeButton">Remove</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                    <c:if test="${requestScope.TOTAL > 0}">
                        <div id="discountCodeBox">
                            <c:if test="${requestScope.DC_ERROR != null}">
                                <font color="red">
                                ${requestScope.DC_ERROR}
                                </font>
                            </c:if>
                            <c:if test="${requestScope.DISCOUNT_CODE != null}" var="hasDiscountCode">
                                <div id="discountCode">
                                    <p>${requestScope.DISCOUNT_CODE}</p>
                                    <c:url value="applyDiscountCode" var="removeDCLink">
                                        <c:param name="action" value="remove"/>
                                    </c:url>
                                    <a href="${pageScope.removeDCLink}">x</a>
                                </div>
                            </c:if>
                            <c:if test="${!hasDiscountCode}">
                                <form action="applyDiscountCode" method="POST">
                                    <input id="discountCodeField" name="id" type="text" placeholder="Enter your discount code here"/>
                                    <input type="hidden" name="action" value="add"/>
                                    <input id="applyButton" type="submit" value="Apply"/>
                                </form>
                            </c:if>
                        </div>
                        <form action="placeOrder" method="POST">
                            <fieldset id="paymentMethodBox">
                                <legend>Payment Method</legend>
                                <input type="radio" name="paymentMethod" value="viaCounter" checked="checked" required="required"/>
                                <label for="viaCounter">Pay with cash via DreamTravelling counter</label>
                            </fieldset>
                            <c:if test="${pageScope.hasDiscountCode}">
                                <h2 id="total">Total: $${requestScope.DISCOUNTED_TOTAL} <span style="color: gray;text-decoration: line-through">${requestScope.TOTAL}</span></h2>
                                </c:if>
                                <c:if test="${!pageScope.hasDiscountCode}">
                                <h2 id="total">Total: $${requestScope.TOTAL}</h2>
                            </c:if>
                            <div class="submitButton">
                                <button type="submit" id="placeOrderButton">Place order</button>
                            </div>
                        </form>
                    </c:if>
                </c:if>
            </c:if>
            <c:if test="${!isNotNull || !hasItem}">
                <p>Your cart is empty =((</p>
            </c:if>
        </div>
    </body>
</html>
