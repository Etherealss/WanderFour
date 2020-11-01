/**
 * @ignore  =====================================================================================
 * @fileoverview
 * @author  寒洲
 * @date 2020/10/31
 * @ignore  =====================================================================================
 */
function doCollectOperate(collectObj, targetId, targetType) {
    //修改图标样式
    doCollectChangeClass(collectObj);
}

/**
 * 修改图标样式
 * @param collectObj jQuery对象
 */
function doCollectChangeClass(collectObj) {
    if (collectObj.hasClass("asmainCol")) {
        //未收藏改成已收藏
        console.log("收藏");
        collectObj.addClass("asmainColOver");
        collectObj.removeClass("asmainCol");
    } else if (collectObj.hasClass("asmainColOver")) {
        //已收藏改成未收藏
        console.log("取消收藏");
        collectObj.addClass("asmainCol");
        collectObj.removeClass("asmainColOver");
    }
}