<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form action="searchTour" method="POST">
    <select name="searchDeparture">
        <option disabled="disabled" selected="selected">-----Departure-----</option>
        <c:if test="${requestScope.LOCATION_LIST != null}">
            <c:if test="${not empty requestScope.LOCATION_LIST}">
                <c:forEach items="${requestScope.LOCATION_LIST}" var="location">
                    <option value="${location.id}" <c:if test="${param.searchDeparture == location.id}">selected</c:if>>${location.name}</option>
                </c:forEach>
            </c:if>
        </c:if>
    </select>
    <select name="searchDestination">
        <option disabled="disabled" selected="selected">-----Destination-----</option>
        <c:if test="${requestScope.LOCATION_LIST != null}">
            <c:if test="${not empty requestScope.LOCATION_LIST}">
                <c:forEach items="${requestScope.LOCATION_LIST}" var="location">
                    <option value="${location.id}" <c:if test="${param.searchDestination == location.id}">selected</c:if>>${location.name}</option>
                </c:forEach>
            </c:if>
        </c:if>
    </select>
    From: <input type="date" name="searchFromDate" value="${param.searchFromDate}"/>
    To: <input type="date" name="searchToDate" value="${param.searchToDate}"/>
    <select name="priceRange">
        <option disabled="disabled" selected="selected">Price range</option>
        <option value="0-50" <c:if test="${param.priceRange == '0-50'}">selected</c:if>>Equal or lower than $50</option>
        <option value="50-100" <c:if test="${param.priceRange == '50-100'}">selected</c:if>>From $50 to $100</option>
        <option value="100-200" <c:if test="${param.priceRange == '100-200'}">selected</c:if>>From $100 to $200</option>
        <option value="200-300" <c:if test="${param.priceRange == '200-300'}">selected</c:if>>From $200 to $300</option>
        <option value="300-600" <c:if test="${param.priceRange == '300-600'}">selected</c:if>>From $300 to $600</option>
        <option value="600-1000" <c:if test="${param.priceRange == '600-1000'}">selected</c:if>>From $600 to $1000</option>
        <option value="1000-2000" <c:if test="${param.priceRange == '1000-2000'}">selected</c:if>>From $1000 to $2000</option>
        <option value="2000-0" <c:if test="${param.priceRange == '2000-0'}">selected</c:if>>$2000+</option>
    </select>
    <input type="submit" value=">"/>
</form>
<form action="searchTour" method="POST">
    <input type="submit" value="Reset"/>
</form>
