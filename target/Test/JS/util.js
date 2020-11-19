/**
 * @ignore  =====================================================================================
 * @fileoverview
 * @author  寒洲
 * @date 2020/10/30
 * @ignore  =====================================================================================
 */
function checkInputNotNull(maxLen, targetTxt) {
    var len = 0;
    for (var i = 0; i < targetTxt.length; i++) {
        //正则表达式判断中文
        if (/[\u4e00-\u9fa5]/.test(targetTxt[i])) {
            //中文
            len += 2;
        } else if (/a-zA-z/.test(targetTxt[i])) {
            //英文
            len++;
        } else {
            //输出了非中英文的特殊字符
            return false;
        }
    }
    //不大于限制的最大长度，且不为空，返回true
    return !(len > maxLen || len == 0);
}

function checkInputNotNull(maxLen, targetTxt) {
    var len = 0;
    for (var i = 0; i < targetTxt.length; i++) {
        //正则表达式判断中文
        if (/[\u4e00-\u9fa5]/.test(targetTxt[i])) {
            //中文
            len += 2;
        } else if (/a-zA-z/.test(targetTxt[i])) {
            //英文
            len++;
        } else {
            //输出了非中英文的特殊字符
            return false;
        }
    }
    //不大于限制的最大长度，且不为空，返回true
    return !(len > maxLen || len == 0);
}