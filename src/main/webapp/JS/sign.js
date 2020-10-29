//登陆注册两个板块切换效果
function toggleForm() {
    var container = document.querySelector('.sign_container');
    container.classList.toggle('active');
}

//封装清除样式函数
function clearStyle(success, error, check, bug, a) {
    success.css("display", "none");
    error.css("display", "none");
    check.css("display", "none");
    bug.css("display", "none");
    a.removeClass();
}

$(function () {

    $('input').focus(function () {
        $(this).addClass('inputBlur');
    })
    $('input').blur(function () {
        $(this).removeClass('inputBlur');
    })

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
            //检验邮箱是否已被注册 (在邮箱格式正确后再发送给后端检查)
            if ($("#registerEmailSuccess").css('display') == 'block') {
                clearStyle($("#registerEmailSuccess"),$("#registerEmailSuccess"),$("#check2"),$("#check2"),$('#registerEmail'));
                checkEmailRepeat();
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
        if (len > 16 || len == 0) {
            $("#registerIdError").css("display", "block");
            $("#bug1").css("display", "block");
            $("#registerUserid").addClass('errorRed');
        } else {
            $("#registerIdSuccess").css("display", "block");
            $("#check1").css("display", "block");
            $("#registerUserid").addClass('successGreen');
        }
    })

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

    //检查成功提示是否已显示 即检查输入是否正确
    function checkInput(a) {
        if (a.css('display') == 'block')
            return 1;
    }

    //点击注册提交信息前再次检查输入框内容是否正确 若正确则发送到后端 减少服务器压力
    //点击注册按钮提交信息
    $("#registerButton").click(function () {
        if (checkInput($('#registerIdSuccess')) && checkInput($('#registerEmailSuccess')) && checkInput($('#registerPwSuccess')) && checkInput($('#registerPwAgainSuccess'))) {
            //获取注册表单的值
            var registerUseridVal = $('#registerUserid').val();
            var registerEmailVal = $('#registerEmail').val();
            var registerPwVal = md5($('#registerPassword').val());  //密码加密后才传给后端
            console.log(registerPwVal);
            //获取已被选中的单选框的value值
            var signIdentity = $('input[name="sign_identity"]:checked').val();
            var signSex = $('input[name="sign_sex"]:checked').val() == "男";
            //调用函数发送数据
            registerSumbit(registerUseridVal, registerEmailVal, registerPwVal, signIdentity, signSex);
        } else {
            alert('请输入正确的注册信息！');
        }
    })

    //点击登录提交信息前再次检查输入框内容是否正确 若正确则发送到后端
    //点击登录按钮提交信息
    $("#loginButton").click(function () {
        if (checkInput($('#loginEmailSuccess'))) {
            //获取登录表单的值
            var loginEmailVal = $('#sign_login_email').val();
            var loginPwVal = md5($('#sign_login_password').val());  //密码加密后才传给后端
            console.log(loginPwVal);
            //调用函数发送数据
            loginSumbit(loginEmailVal, loginPwVal);
        } else {
            alert('请输入正确的登录信息！');
        }
    })

})


/*--------------------------------------登录板块---------------------------------------*/
function loginSumbit(loginEmailVal, loginPwVal) {
    //创建登录ajax对象 向后端发送用户名和密码
    $.ajax({
        type: 'post',
        url: '/UserEnterServlet',
        data: {
            action: "login",
            //传递邮箱
            email: loginEmailVal,
            //传递密码
            pw: loginPwVal,
        },
        dataType: 'json',
        success: function (data) {
            //TODO 成功：跳转至首页
            //异常情况
            if (data.state.code == "SUCCESS") {
                window.location.href = "/index.html";
            } else if (data.state.code == "PW_ERROR") {
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
//检验邮箱是否已被注册发送的ajax
function checkEmailRepeat() {
    $("#registerEmail").blur(function () {
        $.ajax({
            type: 'GET',
            url: '/CheckUserExistServlet',
            data: {
                // method: "checkUserExist",
                //传递邮箱
                email: $('#registerEmail').val(),
            },
            dataType: 'json',
            success: function (data) {
                console.log(data);
                if (data.state.code == "IS_REGISTED") {
                    clearStyle($("#registerEmailSuccess"), $("#registerEmailError"), $("#check2"), $("#bug2"), $("#registerEmail"));
                    $("#registerEmailErrorRepeat").css("display", "block");
                    $("#bug2").css("display", "block");
                    $("#registerEmail").addClass('errorRed');
                } else if (data.state.code == "USER_UN_FOUND") {
                    clearStyle($("#registerEmailSuccess"), $("#registerEmailError"), $("#check2"), $("#bug2"), $("#registerEmail"));
                    $("#registerEmailErrorRepeat").css("display", "block");
                    $("#bug2").css("display", "block");
                    $("#registerEmail").addClass('errorRed');
                }
            },
            error: function () {
                console.log("检验邮箱是否已被注册发送error");
            }
        })
    })
}

//点击注册发送的ajax
function registerSumbit(registerUseridVal, registerEmailVal, registerPwVal, signIdentity, signSex) {
    //创建注册ajax对象 向后端发送
    $.ajax({
        type: 'post',
        url: '/UserEnterServlet',
        data: {
            action: "register",
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
        success: function (data) {
            if (data.state.code == "SUCCESS") {
                window.location.href = "/sign.html";
            } else {
                alert("注册失败！请重试");
            }
        },
        error: function () {
            alert("注册失败！请重试");
        }
    })
}

