$(function () {

    //个人中心悬浮
    $(".topmargin_personal_center").hover(function () {
        $(this).find("ul").stop().fadeToggle();
    });

    //封装点击切换板块函数
    function userCurrent(a, b, c) {
        a.click(function () {
            a.siblings().removeClass();
            b.siblings().hide();
            a.addClass(c);
            b.show();
        })
    }

    //大nav调用点击切换板块函数
    for (var i = 1; i <= 6; i++) {
        userCurrent($('#userNavLi' + i + ''), $('#differentDetails' + i + ''), 'userNavCurrent');
    }

    //“我的收藏”板块小nav调用点击切换板块函数
    for (var i = 1; i <= 2; i++) {
        userCurrent($('#userColBox' + i + ''), $('#userMyCollection' + i + ''), 'userColBoxCurrent');
    }

    //“我的创作”板块小nav调用点击切换板块函数
    for (var i = 1; i <= 2; i++) {
        userCurrent($('#userCreateBox' + i + ''), $('#userCreate' + i + ''), 'userColBoxCurrent');
    }

    function clickNone(a, b, i) {
        a.eq(i).click(function () {
            b.eq(i).hide();
        })
    }

    for (var i = 0; i < 5; i++) {
        clickNone($('.userCollectionStar'), $('.userColBoxContentMain'), i);
    }


    for (var i = 0; i < $('.userChangePW input').length; i++) {
        $('.userChangePW input').eq(i).on({
            focus: function () {
                $(this).addClass('inputBlur');
            }
        });
        $('.userChangePW input').eq(i).on({
            blur: function () {
                $(this).removeClass('inputBlur');
            }
        });
    }

    /*---------------------------------------修改密码--------------------------*/
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
                // clearStyle($("#registerEmailSuccess"),$("#registerEmailSuccess"),$("#check2"),$("#check2"),$('#registerEmail'));
                checkEmailRepeat();
            }
        });
    }
    //封装清除样式函数
    function clearStyle(success, error, check, bug, a) {
        success.css("display", "none");
        error.css("display", "none");
        check.css("display", "none");
        bug.css("display", "none");
        a.removeClass();
    }

    //判断用户输入的密码是否符合格式
    var regPw = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/;
    checkInputVal($('.userChangePW input').eq(1), $("#changePwSuccess"), $("#changePwError"), $("#check1"), $("#bug1"), regPw);

    //判断用户再次输入的密码是否和上次的一样
    $('.userChangePW input').eq(2).change(function () {
        $("#changePwAgainSuccess").css("display", "none");
        $("#changePwAgainError").css("display", "none");
        $("#check").css("display", "none");
        $("#bug").css("display", "none");
        $(this).removeClass();
        if ($(this).val() == $('.userChangePW input').eq(1).val()) {
            $("#changePwAgainSuccess").css("display", "block");
            $("#check2").css("display", "block");
            $(this).addClass('successGreen');
        } else {
            $("#changePwAgainError").css("display", "block");
            $("#bug2").css("display", "block");
            $(this).addClass('errorRed');
        }
    });

    //判断原密码输入是否正确
    $("#changeButton").click(function () {
        if (regPw.test($('.userChangePW input').eq(0).val())) {
            //获取登录表单的值
            var originalPW = md5($('.userChangePW input').eq(0).val());  //获取原密码并加密
            var changePW = md5($('.userChangePW input').eq(2).val());  //获取新密码并加密
            //调用函数发送数据
            changePwFun(originalPW, changePW);
        } else {
            alert('请输入正确的原始密码！');
        }
    });

})

//检验原密码是否正确
function changePwFun(originalPW, changePW) {
    $('.userChangePW input').eq(0).blur(function () {
        $.ajax({
            type: 'PUT',
            url: '/UserPasswordServlet',
            data: {
                //传递原密码
                originalPW: originalPW,
                //传递新密码
                changePW: changePW,
            },
            dataType: 'json',
            contentType: "application/json",
            success: function (res) {
                console.log(res);
                if (res.state.code == "SUCCESS") {
                    alert('密码修改成功！');
                } else if (res.state.code == "UN_LOGGED") {
                    alert('请在登录后修改密码！');
                } else if (res.state.code == "EXCEPTION") {
                    alert('接口异常！');
                }
            },
            error: function () {
                console.log("修改密码接口error");
            }
        })
    })
}