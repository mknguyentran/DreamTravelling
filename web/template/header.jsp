<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="header">
    <a href="searchTour" class="textLink" id="title"><h1>Dream Travelling</h1></a>
    <c:if test="${sessionScope.ACCOUNT != null}" var="loggedIn">
        <h1 id="welcomeMessage">Hello, ${sessionScope.ACCOUNT.name}</h1><br/>
        <c:url value="logout" var="logoutLink">
            <c:param name="action" value="logout"/>
        </c:url>
        <a href="${pageScope.logoutLink}" class="textLink loginLink">Logout</a>
        <c:if test="${sessionScope.ACCOUNT.role == 'admin'}" var="isAdmin">
            <c:url value="createTour" var="createTourLink">
                <c:param name="action" value="prepare"/>
            </c:url>
            <a href="${pageScope.createTourLink}" class="textLink toolLink">Create new Tour</a>
        </c:if>
        <c:if test="${sessionScope.ACCOUNT.role == 'user'}" var="isUser">
            <c:if test="${sessionScope.CART != null}" var="isNotNull">
                <c:if test="${sessionScope.CART.productAmountInCart > 0}" var="hasItem">
                    <a href="loadCart" class="textLink toolLink">Cart(${sessionScope.CART.productAmountInCart})</a>
                </c:if>
            </c:if>
            <c:if test="${!isNotNull || !hasItem}">
                <a href="loadCart" class="textLink toolLink">Cart</a>
            </c:if>
            <a href="loadOrder" class="textLink toolLink">Order History</a>
        </c:if>
    </c:if>
    <c:if test="${!loggedIn}">
        <a href="/DreamTravelling/login.jsp" class="textLink loginLink">Login</a>
    </c:if>
</div>
