//登陆注册两个板块切换效果
function toggleForm() {
    var container = document.querySelector('.sign_container');
    container.classList.toggle('active');
}

//定义一个函数 当表单失去焦点时获取表单里的内容
/*function onblurMethod(a, b) {
    a.onblur = function () {
        b = this.value;
    }
}*/

$(function () {


    sign_login_email.onchange = function () {
        var email = this.value;
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (reg.test(email)) {
            $("#loAlertRed").hide();
        } else {
            $("#loAlertRed").show();
        }
    }
    registerEmail.onchange = function () {
        var email = this.value;
        var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        if (reg.test(email)) {
            $("#reAlertRed").hide();
        } else {
            $("#reAlertRed").show();
        }
    }


    //调用失去焦点函数
    //onblurMethod(loginEmail, getLoginEmail);
    //onblurMethod(loginPassword, getLoginPassword);

    $("#loginButton").click(function () {
        var loginEmailVal = $('#sign_login_email').val();
        var loginPwVal = $('#sign_login_password').val();
        loginSumbit(loginEmailVal, loginPwVal);
    })

    $("#registerButton").click(function () {
        //获取注册表单的值
        var registerUseridVal = $('#registerUserid').val();
        var registerEmailVal = $('#registerEmail').val();
        var registerPwVal = $('#registerPassword').val();
        var registerPwAgainVal = $('#registerPasswordAgain').val();
        //获取已被选中的单选框的value值
        var signIdentity = $('input[name="sign_identity"]:checked').val();
        var signSex = $('input[name="sign_sex"]:checked').val();
        registerSumbit(registerUseridVal, registerEmailVal, registerPwVal, registerPwAgainVal, signIdentity, signSex);
    })

})

/*--------------------------------------登录板块---------------------------------------*/
function loginSumbit(loginEmailVal, loginPwVal) {
    //创建登录ajax对象 向后端发送用户名和密码
    $.ajax({
        type: 'post',
        url: '/UserEnterServlet',
        data: {
            method: "login",
            //传递邮箱
            email: loginEmailVal,
            //传递密码
            pw: loginPwVal
        },
        dataType: 'json',
        success: function (res) {
            //TODO 预想效果：跳转至首页
            console.log(res);
        },
        error: function () {
            console.log("error!");
        }
    })
}

/*--------------------------------------注册板块---------------------------------------*/
function registerSumbit(registerUseridVal, registerEmailVal, registerPwVal, registerPwAgainVal, signIdentity, signSex) {
    //创建注册ajax对象 向后端发送
    $.ajax({
        type: 'post',
        url: '/UserEnterServlet',
        data: {
            method: "register",
            //传递用户名
            Userid: registerUseridVal,
            //传递邮箱
            email: registerEmailVal,
            //传递密码
            pw: registerPwVal,
            //传递再次输入的密码
            pwAgain: registerPwAgainVal,
            //传递身份
            identity: signIdentity,
            //传递性别
            sex: signSex,
        },
        dataType: 'json',
        success: function (res) {
            //预想效果：跳转至首页
            console.log(res);
        }
    })
}

