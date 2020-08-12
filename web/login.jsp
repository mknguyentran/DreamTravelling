<%-- 
    Document   : login
    Created on : Jun 9, 2020, 12:09:02 PM
    Author     : Mk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dream Travelling</title>
        <link rel="stylesheet" type="text/css" href="/DreamTravelling/css/universal.css"/>
    </head>
    <body>
        <div id="fb-root"></div>
        <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v7.0&appId=279589539765379" nonce="zChNf1qL"></script>
        <script src="/DreamTravelling/js/loginWithFB.js"></script>
        <%@include file="/template/header.jsp" %>
        <div class="form loginForm">
            <font color="red">
            ${requestScope.ERROR.mainError}
            </font>
            <form action="login" method="POST">
                Email <input type="text" name="username"/>
                <br/>
                Password <input type="password" name="password"/>
                <input type="hidden" name="action" value="login"/>
                <br/>
                <div class="submitButton">
                    <button type="submit">Login</button>
                </div>
            </form>
            <form id="fbLoginForm" action="login" method="POST">
                <input type="hidden" name="loginWithFB" value=""/>
                <input type="hidden" name="action" value="login"/>
                <input id="fbLoginEmail" type="hidden" name="username"/>
                <input id="fbLoginName" type="hidden" name="name"/>
                <input id="fbLoginID" type="hidden" name="password"/>
                <div class="submitButton">
                    <button type="button" onclick="checkLoginState();">Login with Facebook</button>
                </div>
            </form>
        </div>
        <!--<div class="fb-login-button" data-size="medium" data-button-type="login_with" data-layout="rounded" data-auto-logout-link="false" data-use-continue-as="false" data-width="" scope="public_profile,email" onlogin="checkLoginState();"></div>-->
    </body>
</html>
