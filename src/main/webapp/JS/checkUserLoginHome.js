/**
 * @ignore  =====================================================================================
 * @fileoverview 检查用户登录
 * @author  寒洲
 * @date 2020/10/31
 * @ignore  =====================================================================================
 */
$(function () {
    //检查用户是否已登录，改变导航栏
    homeRequestCheckUserLogin();
})

function homeRequestCheckUserLogin() {
    $.ajax({
        type: "GET",
        url: "/UserLoginServlet",
        dataType: "json",
        success: function (result) {
            changeHomeEnter(result);
            changeNav(result);
        },
        error: function () {
            console.log("初始化页面，检查用户是否已登录并获取信息失败")
        }
    });
}

/**
 * 修改导航栏
 * @param result
 */
function changeNav(result) {
    if (result.state.code == "NOT_LOGGED") {
        //用户未登录的渲染
        console.log("用户未登录");
        $('.topmargin_personal_center').hide();
        $('.topmarginLogin').show();
    } else if (result.state.code == "LOGGED") {
        console.log("获取到的用户信息：id = " + result.user.id +
            "，nickname = " + result.user.nickname +
            ", userType = " + result.user.userType);
        //用户已经登录的渲染，参数看上面的doc注释
        $('.topmarginLogin').hide();
        $('.topmargin_personal_center').show();
        $('.topmargin_personal_center span').eq(1).html(result.user.nickname);
        if (result.user.userType == 'SENIOR') {
            $('.topmargin_personal_center span').eq(3).html("高中生");
        } else if (result.user.userType == 'COLLEGE') {
            $('.topmargin_personal_center span').eq(3).html("大学生");
        } else if (result.user.userType == 'TEACHER') {
            $('.topmargin_personal_center span').eq(3).html("教师");
        } else {
            $('.topmargin_personal_center span').eq(3).html("游客");
        }
        //渲染头像
    }
}

/**
 * 修改首页三个盒子的入口
 * @param result
 */
function changeHomeEnter(result) {
    if (result.state.code == "NOT_LOGGED") {
        //用户未登录的渲染
        console.log("用户未登录");
    } else if (result.state.code == "LOGGED") {
        //用户已经登录的渲染，参数看上面的doc注释
        if (result.user.userType == 'SENIOR') {
            checkUserIde($('#homePage_LWid_SENIOR'));
        } else if (result.user.userType == 'COLLEGE') {
            checkUserIde($('#homePage_LWid_COLLEGE'));
        } else if (result.user.userType == 'OTHERS') {
            checkUserIde($('#homePage_LWid_OTHERS'));
        }
    }
}

/**
 * 盒子隐藏
 * @param a
 */
function checkUserIde(a) {
    a.siblings().hide();
    $('.homePage_LWid_box').prepend("<h1>“开启属于你的学习之旅” →</h1>");
    $('.homePage_LWid_box h1').addClass('addCla');
}