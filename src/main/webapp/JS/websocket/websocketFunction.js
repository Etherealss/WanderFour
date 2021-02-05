/**
 * @ignore  =====================================================================================
 * @fileoverview websocket的回调函数及其相关子函数，放在这个js整理方便整理，增强可读性
 * @author  寒洲
 * @date 2020/11/19
 * @ignore  =====================================================================================
 */

function wsOnOpen(ev) {
    // 上线
    console.log("websocket:open!");
}

/**
 * @param evt
 * @param {string} evt.data ws数据对象包
 */
function wsOnMessage(evt) {
    // 获取服务器推送过来的数据，转为json
    /**
     * @type {object}
     * @param {boolean} evtData.isSystemInfo 是否为系统消息
     * @param {boolean} evtData.infoType 消息类型
     * @param {object}  evtData.data 具体数据
     */
    var evtData = JSON.parse(evt.data);
    console.log(evt);

    /**
     * @param {number} message.fromId 发送者id
     * @param {number} message.toId 接收者id
     * @param {string} message.message 信息字符串
     * @param {number} message.createTime 创建时间的毫秒数
     */
    var message = evtData.data;

    if (evtData.isSystemInfo) {
        // 是系统消息
        console.log("系统通知（" + dateFormat(message.createTime) + "）：message = " + message.message)
    } else {
        // 不是系统消息
        console.log(dateFormat(message.createTime) + "\n" + message.fromId + "说：" + message.message)

        // 消息气泡
        var getMessageHtml =
            "<li class=\"userOtherPost\">\n" +
            "    <img class=\"uersOtherImg\" src=\"" + toUsers.avatarPath + "\"/>\n" +
            "    <span>" + message.message + "</span>\n" +
            "</li>"
        ;
        // 插入HTML
        $("#charFrame").html(getMessageHtml);
    }


}

/**
 * websocket 回调函数 错误
 * @param ev
 */
function wsOnError(ev) {
    console.log("websocket error...");
    console.log(ev);
}

function closeWebSocket(ev) {
    console.log("websocket close...");
    console.log(ev);
}