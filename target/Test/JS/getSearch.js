/**
 * 获取url中的参数，返回参数包对象
 * @returns {{}} params
 */
getParams = function () {
    var params = {};
    //获取当前界面url中的参数
    var url = location.search;

    //如果存在“？”则说明存在参数
    if (url.indexOf("?") != -1) {
        //去除“？”，保留之后的所有字符
        var str = url.substr(1);
        //切割字符串，把每个参数及参数值分成数组
        var paramArr = str.split("&");
        for (var i = 0; i < paramArr.length; i++) {
            //前者为参数名称，后台为参数值
            params[paramArr[i].split("=")[0]] = paramArr[i].split("=")[1];
        }
    }
    return params;
}

/**
 * 检查输入，不允许为空
 * @param maxLen 限制的最大长度
 * @param targetTxt 检查的字符串
 * @returns {boolean} 是在规定长度内的中英文字符串且不为空返回true，否则为false
 */
checkInput = function (maxLen, targetTxt) {
    maxLen = maxLen * 2;
    var targetLength = targetTxt.length;
    if (targetLength == 0) {
        return false;
    }
    var txtLen = 0;
    for (var i = 0; i < targetLength; i++) {
        //正则表达式判断中文
        if (/[\u4e00-\u9fa5]/.test(targetTxt[i])) {
            //中文
            txtLen += 2;
        } else if (/[a-zA-Z0-9]/.test(targetTxt[i])) {
            //英文
            txtLen++;
        } else {
            //输入了非中英文的特殊字符
            return false;
        }
    }
    //不大于限制的最大长度，且不为空，返回true
    return txtLen <= maxLen;
}
/**
 * 前往问贴
 * @param id
 */
toPosts = function (id) {
    window.location.href = "answerPosts.html?posts=" + id;
}

/**
 * 前往文章
 * @param id
 */
toArticle = function (id) {
    window.location.href = "articleShow.html?article=" + id;
}

toLearningWord = function () {
    var loc = "";
    if (user != undefined) {
        if (user.userType == "COLLEGE") {
            loc += "LearningWorld_UM.html";
        }
    } else {
        loc += "LearningWorld_HS.html";
    }

    window.location.href = loc;
}

toProfessional = function () {
    window.location.href = "professionalIntroduction.html";
}

toUniversity = function () {
    window.location.href = "universityLife.html";
}

toIndex = function () {
    window.location.href = "./index.html";
}


/**
 * 格式化日期函数
 * @param dateMs
 * @returns {string}
 */
dateFormat = function (dateMs) {
    var date = new Date(dateMs);
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var mintue = date.getMinutes();
    var second = date.getSeconds();
    return year + "-" + month + "-" + day + " " + hour + ":" + mintue + ":" + second;
}

//转义  元素的innerHTML内容即为转义后的字符
function htmlEncode ( str ) {
    var ele = document.createElement('span');
    ele.appendChild( document.createTextNode( str ) );
    return ele.innerHTML;
}
//解析
function htmlDecode ( str ) {
    var ele = document.createElement('span');
    ele.innerHTML = str;
    return ele.textContent;
}

