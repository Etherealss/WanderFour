/**
 * @ignore  =====================================================================================
 * @fileoverview 点赞
 * @author  寒洲
 * @date 2020/10/31
 * @ignore  =====================================================================================
 */
/**
 * 点赞ajax对象
 * @param likeObj
 * @param targetId
 * @param targetType 文章点赞-article，帖子-posts，评论-comment
 */
function doLikeOperate(likeObj, targetId, targetType) {
    //修改样式
    // console.log($(this));
    doLikeChangeClass(likeObj);

    //点赞就true，取消点赞false
    doLikeAdjustNumerical(likeObj, likeObj.hasClass("asamainLikeOver"));
    //后端交互
    // sendLike(targetId, targetType, doLike);
}

/**
 * 修改点赞按钮样式
 * @param likeObj jQuery对象
 */
function doLikeChangeClass(likeObj) {
    if (likeObj.hasClass("asmainLike")) {
        //未点赞改成已点赞
        console.log("点赞");
        doLikeChangeClassToLiked(likeObj);
    } else if (likeObj.hasClass("asamainLikeOver")) {
        //已点赞改成未点赞
        console.log("取消点赞");
        doLikeChangeClassToUnLiked(likeObj);
    }
}

function doLikeChangeClassToLiked(likeObj) {
    likeObj.addClass("asamainLikeOver");
    likeObj.removeClass("asmainLike");
}

function doLikeChangeClassToUnLiked(likeObj) {
    likeObj.addClass("asmainLike");
    likeObj.removeClass("asamainLikeOver");
}

/**
 * 点赞调整数值
 */
function doLikeAdjustNumerical(likeObj, doLike) {
    //点赞+1，取消点赞-1
    var addNum = (doLike == true) ? 1 : -1;
    likeObj.html(Number(likeObj.html()) + addNum);
}

/**
 * 点赞ajax对象
 * @param targetId
 * @param targetType 文章点赞-article，帖子-posts，评论-comment
 * @param doLike 点赞就写true，取消点赞写false
 */
function sendLike(targetId, targetType, doLike) {
    console.log("点赞或取消点赞");
    //点赞为1，取消点赞为0
    var state = (doLike == true) ? 1 : 0;
    $.ajax({
        type: 'POST',
        url: '/LikeServlet',
        data: JSON.stringify({
            //目标作品id
            targetId: targetId,
            //目标作品类型，文章点赞-article，帖子-posts，评论-comment
            targetType: targetType,
            //点赞状态，1-点赞 0-未点赞/取消赞
            likeState: state
        }),
        dataType: 'json',
        contentType: "application/json",
        success: function (res) {
            console.log(res);
        }
    })
}