document.addEventListener('visibilitychange', account_status, false);
account_status();

function setCookie(name, value) {
    document.cookie = name + "=" + value
}

//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

    if (arr = document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

function account_status() {
    console.log("check account status");
    var name = getCookie("user_name");
    if (name !== null) {
        document.getElementById("account_action").innerHTML = "Welcome, " + name;
    } else {
        document.getElementById("account_action").innerHTML = "Please login";
    }
}

function GetRequest() {
    var url = location.search; //获取url中含"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

function isLogin() {
    if (getCookie("user_name") !== null)
        return true;
    else
        return false;
}

function myOrder() {
    if (!isLogin()) {
        alert("Please Log in!");
    } else {
        window.open("my_orders.html", '_self');
    }
}

function myCart() {
    if (!isLogin()) {
        alert("Please Log in!");
    } else {
        window.open("cart.html", '_self');
    }
}

function addPromotion() {
    if (!isLogin()) {
        alert("Please Log in!");
    } else {
        if (getCookie("user_role") !== "1") {
            alert("Only administrators have this permission");
        } else {
            alert("Add Promotion");
        }
    }
}

function addCommodity() {
    if (!isLogin()) {
        alert("Please Log in!");
    } else {
        if (getCookie("user_role") !== "1") {
            alert("Only administrators have this permission");
        } else {
            alert("Add Commodity");
        }
    }
}