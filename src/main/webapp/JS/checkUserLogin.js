/**
 * @ignore  =====================================================================================
 * @fileoverview 检查用户登录
 * @author  寒洲
 * @date 2020/10/31
 * @ignore  =====================================================================================
 */
$(function () {
    //检查用户是否已登录，改变导航栏
    requestCheckUserLogin();
})

function requestCheckUserLogin() {
    $.ajax({
        type: "GET",
        url: "/UserLoginServlet",
        dataType: "json",
        /**
         * @param {Object}result
         * @param {Object}result.state              接口状态
         *                                             ——— NOT_LOGGED 未登录
         *                                             ——— LOGGED 已经登录
         * @param {Object}result.user               用户信息包
         * @param {Number}result.user.id            用户id
         * @param {Number}result.user.nickname      用户昵称
         * @param {String}result.user.email         用户邮箱
         * @param {String}result.user.avatarPath    用户头像的Base64数据流
         * @param {Number}result.user.liked         用户获赞数
         * @param {Number}result.user.collected     用户被收藏数
         * @param {Number}result.user.birthday      生日
         * @param {Number}result.user.registerDate  注册时间
         * @param {String}result.user.userType      用户类型
         *                                             ——— SENIOR是高中生
         *                                             ——— COLLEGE是大学生
         *                                             ——— TEACHER是教师
         *                                             ——— OTHERS是其他用户
         *
         */
        success: function (result) {
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