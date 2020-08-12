window.fbAsyncInit = function () {
    FB.init({
        appId: '279589539765379',
        cookie: true,
        xfbml: true,
        version: '7.0'
    });

    FB.AppEvents.logPageView();

};

(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {
        return;
    }
    js = d.createElement(s);
    js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function checkLoginState() {
    FB.login(function (response) {
        statusChangeCallback(response);
    }, {scope: 'email,public_profile'});
}

function statusChangeCallback(response) {
    if (response.status === 'connected') {
        FB.api('/me?fields=name,email,id', function (response) {
            document.getElementById("fbLoginEmail").setAttribute("value", response.email);
            document.getElementById("fbLoginName").setAttribute("value", response.name);
            document.getElementById("fbLoginID").setAttribute("value", response.id);
            document.getElementById("fbLoginForm").submit();
        });
    } 
}