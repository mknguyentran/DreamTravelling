<%-- 
    Document   : error
    Created on : Jun 11, 2020, 6:00:00 PM
    Author     : Mk
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
    </head>
    <body>
        <h1>Error Occured</h1>
        <p>Error Description: ${requestScope.ERROR}</p>
        <a href="searchTour" class="blueLink">< Back to home page</a>
    </body>
</html>
