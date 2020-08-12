<%-- 
    Document   : orderHistory
    Created on : Jun 20, 2020, 11:36:39 AM
    Author     : Mk
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order History</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css">
    </head>
    <body>
        <%@include file="/template/header.jsp" %>
        <c:if test="${requestScope.ORDER_LIST != null}" var="isNotNull">
            <c:if test="${not empty requestScope.ORDER_LIST}" var="isNotEmpty">
                <table border="0" cellpadding="5" style="width: 600px;">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Placed At</th>
                            <th>Total</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.ORDER_LIST}" var="order" varStatus="counter">
                            <tr>
                                <td>${pageScope.counter.count}</td>
                                <td>${pageScope.order.simpleCreatedAt}</td>
                                <td>$${pageScope.order.total}</td>
                                <c:if test="${pageScope.order.statusString == 'pending'}">
                                    <td style="color: goldenrod">${pageScope.order.statusString}</td>
                                </c:if>
                                <c:if test="${pageScope.order.statusString == 'completed'}">
                                    <td style="color: green">${pageScope.order.statusString}</td>
                                </c:if>
                                <c:if test="${pageScope.order.statusString == 'cancelled'}">
                                    <td style="color: indianred">${pageScope.order.statusString}</td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </c:if>
        </c:if>
    </body>
</html>
