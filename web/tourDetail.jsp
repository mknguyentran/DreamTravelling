<%-- 
    Document   : tourDetail
    Created on : Jun 13, 2020, 4:13:33 PM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dream Travelling - <c:if test="${requestScope.TOUR!= null}">${requestScope.TOUR.name}</c:if></title>
            <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
            <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/tourDetailPage.css"/>
        </head>
        <body>
        <%@include file="/template/header.jsp" %>
        <div id="tourDetail">
            <c:if test="${requestScope.ALERT != null}">
                <font color="red">${requestScope.ALERT}</font>
            </c:if>
            <c:if test="${requestScope.TOUR != null}" var="isNull">
                <img src="${requestScope.TOUR.image}"/>
                <h2>${requestScope.TOUR.name}</h2>
                <p>From: ${requestScope.TOUR.departure} - ${requestScope.TOUR.simpleFromDate}</p>
                <p>From: ${requestScope.TOUR.destination} - ${requestScope.TOUR.simpleToDate}</p>
                <h3>$${requestScope.TOUR.price}</h3>
                <c:if test="${requestScope.TOUR.statusString == 'available'}" var="isAvailable">
                    <c:if test="${sessionScope.ACCOUNT != null}" var="isLoggedIn">
                        <c:if test="${sessionScope.ACCOUNT.role == 'user'}" var="isUser">
                            <form action="manageCart" method="POST">
                                <input type="hidden" name="sentFrom" value="tourDetail"/>
                                <input type="hidden" name="id" value="${requestScope.TOUR.id}"/>
                                <input type="hidden" name="action" value="add"/>
                                <input type="submit" value="Add to Cart"/>
                            </form>
                        </c:if>
                    </c:if>
                    <c:if test="${!isLoggedIn || !isUser}">
                        <button type="button" disabled="disabled" id="disabledButton">Add to Cart</button>
                        <p>Please log in with a DreamTravelling account to buy this tour</p>
                    </c:if>
                </c:if>
                <c:if test="${!isAvailable}">
                    <h3 id="soldOut">Sold out</h3>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
