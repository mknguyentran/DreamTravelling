<%-- 
    Document   : createTour
    Created on : Jun 9, 2020, 2:38:01 PM
    Author     : Mk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dream Travelling - Create new Tour</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
    </head>
    <body>
        <%@include file="/template/header.jsp" %>
        <div class="form">
            <form action="CreateTourController" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="action" value="create"/>
                <font color="red">${requestScope.ERROR.idError}</font>
                <br/>
                ID <input type="text" name="id" value="${param.id}"/>
                <br/>
                <font color = "red">${requestScope.ERROR.nameError}</font>
                <br/>
                Name <input type = "text" name = "name" value = "${param.name}"/> 
                <br/>
                <font color="red">${requestScope.ERROR.locationError}</font>
                <br/>
                Departure
                <select name="departure">
                    <option hidden="hidden" selected="selected" value="">Choose a location</option>
                    <c:if test="${requestScope.LOCATION_LIST != null}" var="check">
                        <c:forEach items = "${requestScope.LOCATION_LIST}" var = "location">
                            <option value="${location.id}" <c:if test="${param.departure == location.id}">selected="selected"</c:if>>${location.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <br/>
                Destination
                <select name="destination">
                    <option hidden="hidden" selected="selected" value="">Choose a location</option>
                    <c:if test="${requestScope.LOCATION_LIST != null}">
                        <c:forEach items="${requestScope.LOCATION_LIST}" var="location">
                            <option value="${location.id}" <c:if test="${param.destination == location.id}">selected="selected"</c:if>>${location.name}</option>
                        </c:forEach>
                    </c:if>
                </select>
                <br/>
                <font color="red">${requestScope.ERROR.fromDateError}</font>
                <br/>
                From <input type="date" name="fromDate" value="${param.fromDate}"/>
                <br/>
                <font color="red">${requestScope.ERROR.toDateError}</font>
                <br/>
                To <input type="date" name="toDate" value="${param.toDate}"/>
                <br/>
                <font color="red">${requestScope.ERROR.priceError}</font>
                <br/>
                Price <input type="text" name="price" value="${param.price}"/>
                <br/>
                <font color="red">${requestScope.ERROR.quotaError}</font>
                <br/>
                Quota <input type="number" name="quota" value="${param.quota}"/>
                <br/>
                <font color="red">${requestScope.ERROR.imageError}</font>
                <br/>
                Image <input type="file" name="image" accept=".jpg,.jpeg,.png"/>
                <br/>
                <div class="submitButton">
                    <button type="submit">Create</button>
                </div>
            </form>
        </div>
    </body>
</html>
