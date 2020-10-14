//登陆注册两个板块切换效果
function toggleForm() {
    var container = document.querySelector('.sign_container');
    container.classList.toggle('active');
}

$(function () {

    //封装清除样式函数
    function clearStyle(success, error, check, bug, a) {
        success.css("display", "none");
        error.css("display", "none");
        check.css("display", "none");
        bug.css("display", "none");
        a.removeClass();
    }

    //封装检测表单输入函数
    function checkInputVal(a, success, error, check, bug, reg) {
        a.change(function () {
            clearStyle(success, error, check, bug, a);
            if (reg.test($(this).val())) {
                success.css("display", "block");
                check.css("display", "block");
                a.addClass('successGreen');
            } else {
                error.css("display", "block");
                bug.css("display", "block");
                a.addClass('errorRed');
            }
        })
    }

    //登录板块 判断用户输入的邮箱是否符合格式
    var regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    checkInputVal($("#sign_login_email"), $("#loginEmailSuccess"), $("#loginEmailError"), $("#check5"), $("#bug5"), regEmail);

    //注册板块 判断用户输入的用户名是否符合格式
    $("#registerUserid").change(function () {
        clearStyle($("#registerIdSuccess"), $("#registerIdError"), $("#check1"), $("#bug1"), $("#registerUserid"));
        var len = 0;
        for (var i = 0; i < $(this).val().length; i++) {
            //正则表达式判断中文
            if (/[\u4e00-\u9fa5]/.test($(this).val()[i])) {
                len += 2;
            } else {
                len++;
            }
        }
        if (len > 16) {
            $("#registerIdError").css("display", "block");
            $("#bug1").css("display", "block");
            $("#registerUserid").addClass('errorRed');
        } else {
            $("#registerIdSuccess").css("display", "block");
            $("#check1").css("display", "block");
            $("#registerUserid").addClass('successGreen');
        }
    })
    // var regID = /[\u4e00-\u9fa5_a-zA-Z0-9_]{4,12}/;
    // checkInputVal($("#registerUserid"), $("#registerIdSuccess"), $("#registerIdError"), $("#check1"), $("#bug1"), regID);

    //注册板块 判断用户输入的邮箱是否符合格式
    // var regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    checkInputVal($("#registerEmail"), $("#registerEmailSuccess"), $("#registerEmailError"), $("#check2"), $("#bug2"), regEmail);

    //注册板块 判断用户输入的密码是否符合格式
    var regPw = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    checkInputVal($("#registerPassword"), $("#registerPwSuccess"), $("#registerPwError"), $("#check3"), $("#bug3"), regPw);

    //注册板块 判断用户再次输入的密码是否和上次的一样
    $("#registerPasswordAgain").change(function () {
        $("#registerPwAgainSuccess").css("display", "none");
        $("#registerPwAgainError").css("display", "none");
        $("#check4").css("display", "none");
        $("#bug4").css("display", "none");
        $("#registerPasswordAgain").removeClass();
        if ($(this).val() == $("#registerPassword").val()) {
            $("#registerPwAgainSuccess").css("display", "block");
            $("#check4").css("display", "block");
            $("#registerPasswordAgain").addClass('successGreen');
        } else {
            $("#registerPwAgainError").css("display", "block");
            $("#bug4").css("display", "block");
            $("#registerPasswordAgain").addClass('errorRed');
        }
    })

    //点击登录按钮提交信息
    $("#loginButton").click(function () {
        //获取登录表单的值
        var loginEmailVal = $('#sign_login_email').val();
        var loginPwVal = $('#sign_login_password').val();
        //调用函数发送数据
        loginSumbit(loginEmailVal, loginPwVal);
    })

    //点击注册按钮提交信息
    $("#registerButton").click(function () {
        //获取注册表单的值
        var registerUseridVal = $('#registerUserid').val();
        var registerEmailVal = $('#registerEmail').val();
        var registerPwVal = $('#registerPassword').val();
        var registerPwAgainVal = $('#registerPasswordAgain').val();
        //获取已被选中的单选框的value值
        var signIdentity = $('input[name="sign_identity"]:checked').val();
        var signSex = $('input[name="sign_sex"]:checked').val();
        //调用函数发送数据
        registerSumbit(registerUseridVal, registerEmailVal, registerPwVal, registerPwAgainVal, signIdentity, signSex);
    })

    //检验邮箱是否已被注册
    $("#registerEmail").blur(function () {
        $.ajax({
            type: 'post',
            url: 'http://192.168.137.138:8080/CheckUserExistServlet',
            data: {
                method: "checkUserExist",
                //传递邮箱
                email: $('#registerEmail').val(),
            },
            dataType: 'json',
            success: function (data) {
                console.log(data);
                if (data.state.code == "IS_REGISTED") {
                    $("#registerEmailErrorRepeat").css("display", "block");
                    $("#bug2").css("display", "block");
                    $("#registerEmail").addClass('errorRed');
                }
            },
            error: function () {
                console.log("11111");
            }
        })
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
            pw: loginPwVal,
        },
        dataType: 'json',
        success: function (data) {
            //TODO 成功：跳转至首页（后端实现）
            //异常情况
            if (data.state.code == "PW_ERROR") {
                clearStyle($("#loginPwSuccess"), $("#loginPwSuccess"), $("#check5"), $("#check5"), $("#sign_login_password"));
                $("#loginPwError").css("display", "block");
                $("#bug6").css("display", "block");
                $("#sign_login_password").addClass('errorRed');
            } else if (data.state.code == "USER_UN_FOUND") {
                clearStyle($("#loginEmailSuccess"), $("#loginEmailError"), $("#check5"), $("#bug5"), $("#sign_login_email"));
                clearStyle($("#loginEmailUnfound"), $("#loginEmaillogged"), $("#bug5"), $("#bug5"), $("#sign_login_email"));
                $("#loginEmailUnfound").css("display", "block");
                $("#bug5").css("display", "block");
                $("#sign_login_email").addClass('errorRed');
            } else if (data.state.code == "LOGGED") {
                clearStyle($("#loginEmailSuccess"), $("#loginEmailError"), $("#check5"), $("#bug5"), $("#sign_login_email"));
                clearStyle($("#loginEmailUnfound"), $("#loginEmaillogged"), $("#bug5"), $("#bug5"), $("#sign_login_email"));
                $("#loginEmaillogged").css("display", "block");
                $("#bug5").css("display", "block");
                $("#sign_login_email").addClass('errorRed');
            } else if (data.state.code == "EXCEPTION") {
                alert("请刷新页面！")
            }
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
            nickname: registerUseridVal,
            //传递邮箱
            email: registerEmailVal,
            //传递密码
            pw: registerPwVal,
            //传递身份
            userType: signIdentity,
            //传递性别
            sex: signSex,
        },
        dataType: 'json',
        success: function (res) {
            //TODO 跳转至登录，即重新跳转到本页面（后端实现）
            console.log(res);
        }
    })
}

