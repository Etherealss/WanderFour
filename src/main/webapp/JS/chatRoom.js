//点击网站图标，直接跳转到首页
$("#sideBarToHomePage").on({
    click: function () {
        window.open('./index.html');
        // window.location.href = './index.html';
    }
});

//点击个人中心，直接跳转到个人中心
$("#personalCenter").on({
    click: function () {
        window.open("./user.html");
        // window.location.href = './user.html';
    }
});

// 点击小图标后出现侧栏
$(".clickShowSideBar").on({
    click: function () {
        //点击后水平移动
        $(".clickShowSideBar").css("right", 100);
        //点击后消失
        // $(".clickShowSideBar").fadeOut();
        //侧栏移动Part
        $(".sideBarBox").css("right", 0);
        $(".CRRightBox").css("width", 110);
        clickAnotherPosition();
    }
});

//右侧小图标的悬浮动画
$(".clickShowSideBar").hover(
    function () {
        $(this).animate({
            marginRight: 55
        }, 0);
    },
    function () {
        $(this).animate({
            marginRight: 40
        }, 0);
    });

//点击其他任意区域，侧栏消失

function clickAnotherPosition() {
    $("body").on({
        click: function (e) {
            var target = $(e.target);
            //如果是这些部分及以下的子元素，可使用，其他都不可以
            if (!target.is(".sideBarBox")) {
                if ($(".sideBarBox").css("right") == "0px") {
                    //点击后跟随水平移动
                    $(".clickShowSideBar").css("right", 0);
                    //点击后消失
                    // $(".clickShowSideBar").fadeIn();
                    //侧栏效果
                    $(".sideBarBox").css("right", -110);
                    $(".CRRightBox").css("width", 0);
                }
                //阻止事件冒泡
                $(".sideBarBox").stop().on({
                    click: function (event) {
                        event.stopPropagation();
                    }
                });
            }
        }
    });
}

// ——————————————————————— 创建标签样式 ———————————————————————————
// —————————————— 创建用户自己的发言 ————————————————
function createUserSelfPost(src, content) {
    var li = $("<li class='userSelfPost'></li>");
    var liStr = "<img class='uersSelfImg' src='" + src + "'/>" +
        "<span>" + content + "</span>";
    li.html(liStr);
    $("#charFrame").append(li);
}

//src = ./img/homePage_highSchoolStudent_head.png

// —————————————— 创建对方用户的发言 ————————————————
function createUserOtherPost(src, content) {
    var li = $("<li class='userOtherPost'></li>");
    var liStr = "<img class='uersOtherImg' src='../img/homePage_highSchoolStudent_head.png'/>" +
        "<span>" + content + "</span>";
    li.html(liStr);
    $("#charFrame").append(li);
}

//————————————————————— 在文本框内输入后，点击发送，发送到框内 ———————————————————————————
$("#postReplyBtn").on({
    click: function () {
        //创建显示框
        createUserSelfPost("./img/homePage_highSchoolStudent_head.png", $("#editReplyContent").val());
        //发表出去后清空文本框
        $("#editReplyContent").val("");
    }
});

// //————————————————————— 左侧用户列表点击后赋予用户id ——————————————————————————————
// function doubleClickChangeChatUser() {
//     $("#usersList").eq("li").on({
//         dblclick: function () {
//             console.log("双击");
//             //函数返回用户id
//             return $(this).attr("toUsersId");
//         }
//     });
// }

// ————————————————————————— 页面初始化产生左侧列表 —————————————————————————————
/**
 * toUsersId,src,toUsersName,theNewMessage,theNewTime
 * @param {object}user 聊天好友信息包
 * @param {number}user.id 聊天好友id
 * @param {string}user.nickname 聊天好友昵称
 * @param {string}user.avatarPath 聊天好友头像路径
 * @param {object}message 聊天记录信息包
 * @param {string}message.message 最后一条聊天记录的文本信息
 * @param {number}message.createTime 最后一条聊天记录的时间
 */
function createFriendList(user, message) {
    var li = $("<li ondblclick='doubleClickGetUserId(user.id)'></li>");
    var str = "<img src='../img/homePage_highSchoolStudent_head.png' />" +
        "      <a toUsersId='" + user.id + "'>" + user.nickname + "</a>" +
        "      <p>" + message.message + "</p>" +
        "      <span>" + "刚刚" + "</span>";
    li.html(str);
    $("#usersList").append(li);
}



