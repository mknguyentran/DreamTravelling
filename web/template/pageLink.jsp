<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="pageLinkBar">
    <c:if test="${requestScope.PAGES_AMOUNT != null}">
        <c:if test="${requestScope.PAGES_AMOUNT > 0}">
            <c:if test="${param.page != 1 && param.page != null}" var="notFirstPageCheck">
                <c:url var="pageLink" value="searchTour">
                    <c:param name="searchDeparture" value="${param.searchDeparture}"/>
                    <c:param name="searchDestination" value="${param.searchDestination}"/>
                    <c:param name="searchFromDate" value="${param.searchFromDate}"/>
                    <c:param name="searchToDate" value="${param.searchToDate}"/>
                    <c:param name="priceRange" value="${param.priceRange}"/>
                    <c:param name="page" value="${param.page - 1}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink"><</a>
            </c:if>
            <c:if test="${!notFirstPageCheck}">
                <a class="pageLink"><</a>
            </c:if>
            <c:forEach begin="1" end="${requestScope.PAGES_AMOUNT}" varStatus="counter">
                <c:url var="pageLink" value="searchTour">
                    <c:param name="searchDeparture" value="${param.searchDeparture}"/>
                    <c:param name="searchDestination" value="${param.searchDestination}"/>
                    <c:param name="searchFromDate" value="${param.searchFromDate}"/>
                    <c:param name="searchToDate" value="${param.searchToDate}"/>
                    <c:param name="priceRange" value="${param.priceRange}"/>
                    <c:param name="page" value="${counter.count}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink" <c:if test="${param.page == counter.count || (param.page == null && counter.count == 1)}">id="currentPage"</c:if>>${counter.count}</a>
            </c:forEach>
            <c:if test="${param.page != requestScope.PAGES_AMOUNT && param.page != null}" var="notLastPageCheck">
                <c:url var="pageLink" value="searchTour">
                    <c:param name="searchDeparture" value="${param.searchDeparture}"/>
                    <c:param name="searchDestination" value="${param.searchDestination}"/>
                    <c:param name="searchFromDate" value="${param.searchFromDate}"/>
                    <c:param name="searchToDate" value="${param.searchToDate}"/>
                    <c:param name="priceRange" value="${param.priceRange}"/>
                    <c:param name="page" value="${param.page + 1}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink">></a>
            </c:if>
            <c:if test="${param.page == null && requestScope.PAGES_AMOUNT > 1}" var="isFirstPage">
                <c:url var="pageLink" value="searchTour">
                    <c:param name="searchDeparture" value="${param.searchDeparture}"/>
                    <c:param name="searchDestination" value="${param.searchDestination}"/>
                    <c:param name="searchFromDate" value="${param.searchFromDate}"/>
                    <c:param name="searchToDate" value="${param.searchToDate}"/>
                    <c:param name="priceRange" value="${param.priceRange}"/>
                    <c:param name="page" value="${2}"/>
                </c:url>
                <a href="${pageLink}" class="pageLink">></a>
            </c:if>
            <c:if test="${!isFirstPage && !notLastPageCheck}">
                <a class="pageLink">></a>
            </c:if>
        </c:if>
    </c:if>
</div>
