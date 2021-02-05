/**
 * @ignore  =====================================================================================
 * @fileoverview 聊天室
 * @author  寒洲
 * @date 2020/11/19
 * @ignore  =====================================================================================
 */
/**
 * 聊天室中我【所有的】聊天的对象(Target User)的用户信息的Map集合
 */
var toUsers = {};

/**
 * 当前的聊天对象的id
 * 0 是初始化用，避免undefined
 */
var nowChatUserId = 2;

/** websocket聊天室对象 */
var ws = new WebSocket("ws://localhost:8080/chat");

$(function () {
    // 获取好友列表
    ajaxGetFriendList();

    // 显示当前用户（我自己）的用户信息
    showMyInfo();

    var params = getParams();

    // 渲染当前聊天对象的信息
    initToUserInfo(params.userid);

    // 绑定事件
    setEvent();
})

/**
 * 获取聊天对象的信息
 * @param userid
 * @returns {void|*}
 */
function getToUserInfo(userid) {
    if (toUsers[userid] != null) {
        return toUsers[userid];
    } else {
        var user = ajaxGetChatUserInfo(nowChatUserId);
        toUsers[user.id] = user;
        return user;
    }
}

/**
 * 渲染当前聊天对象的信息和聊天窗口
 */
function initToUserInfo(userid) {
    // 获取聊天对象的信息
    var user = getToUserInfo(userid);
    // 渲染
    showChatUser(user);

    $("#editReplyContent").val("");
}

function showMyInfo() {
    // 使用checkUserLogin.js中获取的全局user对象
    console.log(user.nickname);
    $("#myNiFckname").html(user.nickname);
}

/**
 * 绑定事件
 */
function setEvent() {
    ws.onopen = function (ev) {
        wsOnOpen(ev)
    };

    ws.onmessage = function (ev) {
        wsOnMessage(ev);
    };

    ws.onerror = function (ev) {
        wsOnError(ev)
    };
    ws.onclose = function (ev) {
        wsOnError(ev)
    };

    // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        closeWebSocket();
    }

    $("#postReplyBtn").click(function () {
        // doSendTxt();
    });

    // 绑定点击事件 双击好友名片切换聊天对象
    // nowChatUserId = doubleClickChangeChatUser();
}

/**
 * 获取聊天对象的用户信息
 */
function ajaxGetChatUserInfo(id) {
    var resUser;
    $.ajax({
        type: "GET",
        url: "/UserInfoServlet?userid=" + id,
        dataType: "json",
        contentType: "application/json",
        // 同步
        async: false,
        success: function (res) {
            if (res.state.code == "SUCCESS") {
                // 【聊天目标】的数据，不是当前用户的数据
                // toUsers[res.user.id] = res.user;
                // showChatName(toUsers.nickname);
                resUser = res.user;
            } else {
                console.log("res.state.code = " + res.state.code);
            }
        },
        error: function () {
            console.log("ajaxGetChatUserInfo error!!!!!");
        }
    });
    return resUser;
}

/**
 * 请求好友列表
 */
function ajaxGetFriendList() {
    $.ajax({
        type: "GET",
        url: "/socket/friend",
        dataType: "json",
        contentType: "application/json",
        /**
         * @param res
         * @param res.state.code
         * @param res.friends 好友信息包列表
         */
        success: function (res) {
            if (res.state.code == "SUCCESS") {
                toUsers = res.friends;
                initFriendsList(toUsers);
                nowChatUserId = res.friends[0].id;
            } else {
                console.log("res.state.code = " + res.state.code);
            }
        },
        error: function () {
            console.log("ajaxGetChatUserInfo error!!!!!");
        }
    });

}

/**
 * 初始化好友列表
 * @param friends
 */
function initFriendsList(friends) {
    console.log("初始化好友列表");
    for (var i = 0; i < friends.length; i++) {
        // 1、获取聊天记录
        var message = {
            message: "你好呀",
            createTime: new Date().getSeconds()
        }
        // 2、调用函数添加HTML
        createFriendList(friends[i], message);
    }
}

/**
 * 渲染聊天对象的信息
 * @param user
 */
function showChatUser(user) {
    $("#toUserNickname").html(user.nickname);
    // 可以补充渲染其他地方，现在这个函数只有一行代码
}

/**
 * 发送文本到服务器 服务器将转发给我的聊天对象
 */
function doSendTxt() {
    var inputTxt = $("#editReplyContent").val();
    console.log(inputTxt)
    // 清空输入框内容
    console.log($("#editReplyContent"));
    $("#editReplyContent").val("");

    var myMessageHtml =
        "<li class=\"userSelfPost\" ondblclick='doubleClickGetUserId();'>\n" +
        "    <img class=\"uersSelfImg\" src=\"" + user.avatarPath + "\"/>\n" +
        "    <span>" + inputTxt + "</span>\n" +
        "</li>"
    ;
    $("#charFrame").append(myMessageHtml);

    // 聊天信息传输
    // var transJson = getTxtForJson(inputTxt);
    // 发送json数据
    // ws.send(transJson);
}

function getTxtForJson(message) {
    // 传给服务器的数据包
    var res = JSON.stringify({
        "toId": nowChatUserId,
        "message": message
    });
    console.log(res);
    return res;
}

/**
 * 双击修改
 */
function doubleClickGetUserId(userid) {
    console.log("chat.js 双击" + userid);
    nowChatUserId = userid;
    // 初始化切换的聊天对象的信息
    initToUserInfo(userid);
}