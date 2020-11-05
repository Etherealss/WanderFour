/**
 * @ignore  =====================================================================================
 * @fileoverview 防注入
 * @author  寒洲
 * @date 2020/10/29
 * @ignore  =====================================================================================
 */
$(function () {
    //过滤URL非法SQL字符
    urlSafe();
})

urlSafe = function () {
    //过滤URL非法SQL字符
    var sUrl=location.search.toLowerCase();
    var sQuery=sUrl.substring(sUrl.indexOf("=")+1);
    var re=/%20|'|"|;|>|<|%/i;
    if(re.test(sQuery)) {
        console.log("过滤URL非法SQL字符");
        location.href=sUrl.replace(sQuery,"");
    }
}
//转义  元素的innerHTML内容即为转义后的字符
function htmlEncode (str) {
    var ele = document.createElement('span');
    ele.appendChild( document.createTextNode( str ) );
    return ele.innerHTML;
}
//解析
function htmlDecode (str) {
    var ele = document.createElement('span');
    ele.innerHTML = str;
    return ele.textContent;
}