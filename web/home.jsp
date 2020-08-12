<%-- 
    Document   : index
    Created on : Jun 6, 2020, 6:41:48 PM
    Author     : Mk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dream Travelling</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/homePage.css"/>
    </head>
    <body>
        <%@include file="/template/header.jsp" %>
        <div id="body">
            <%@include file="/template/searchBar.jsp" %>
            <c:if test="${requestScope.TOUR_LIST != null}">
                <%@include file="/template/pageLink.jsp"%>
                <br/>
                <c:if test="${not empty requestScope.TOUR_LIST}" var="isNotEmpty">
                    <c:forEach items="${requestScope.TOUR_LIST}" var="tour">
                        <c:url var="loadTourLink" value="loadTour">
                            <c:param name="id" value="${tour.id}"/>
                        </c:url>
                        <a href="${loadTourLink}">
                            <c:if test="${tour.image != null}" var="hasImage">
                                <div class="tourCard withImage" style="background-image: url(${tour.image})">
                                </c:if>
                                <c:if test="${!hasImage}">
                                    <div class="tourCard withoutImage">
                                    </c:if>
                                    <div class="tourInfo">
                                        <h2>${tour.name}</h2>
                                        <p>${tour.departure} - ${tour.destination}</p>
                                        <p>From: ${tour.simpleFromDate}</p>
                                        <p>To: ${tour.simpleToDate}</p>
                                        <h3>$${tour.price}</h3>
                                    </div>
                                </div>
                        </a>
                        <br/>
                    </c:forEach>
                </c:if>
                <c:if test="${!isNotEmpty}">
                    <p>No result found :(</p>
                </c:if>
            </c:if>
        </div>
    </body>
</html>
