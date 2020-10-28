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