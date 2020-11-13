/* 时间戳转日期 */
function timestampToTime(timestamp) 
{
    //var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var date = new Date(timestamp);
    year = date.getFullYear() + '-';
    month = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    day = date.getDate() + ' ';
    hour = date.getHours() + ':';
    minute = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
    second = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
    return year + month + day + hour + minute + second;
}

//———————————————————————— 获取评论，查看更多评论 ————————————————————————
/** 
 * @param {*} parentId 想要获取的评论是在哪个文章/问贴下的，该文章/问贴的id
 * @param {*} order 按获赞数搜索：order=like，按最新发表的搜索：order=time
 * @param {*} userid 当前浏览页码的用户id（即：使用者）
 * @param {*} currentPage 当前评论页码数
 */

function getComments(parentId,order,userid,currentPage)
{
    $.ajax({
        type:'get',
        url:'/WritingCommentServlet?type=posts&parentId='+parentId+'&order='+order+'&userid='+userid+'&currentPage='+currentPage,
        data:{},
        contentType: "application/json",
        success:function(res){//成功的回调函数
            var pageList = res.pageData.list;
            commentsRender(pageList);
            console.log(res);
        },
        error:function(){//XX失败
            console.log("获取评论出错，获取不到");
        }
    })
}

//—————————————————————— 获取评论后渲染到页码上 ——————————————————————————
function commentsRender(pageList)
{
    var userId = pageList.parentComment.comment.userNickname;
    var postTime = pageList.parentComment.comment.createTime;
    var postsContent = pageList.parentComment.comment.content;
    var like = pageList.parentComment.comment.like;
    var src = pageList.parentComment.comment.userImg;
    postPostsUp(userId, postTime, postsContent, like,src);
}

//————————————————————————————————————— 查看评论下的更多回复 ————————————————————————————————————————
/**
 * @param {*} parentId 该回复在哪个评论下，评论id
 * @param {*} order 所获取到数据的排列方式（时间time，获赞数like）
 * @param {*} userid 当前浏览页码的用户id（即：使用者）
 * @param {*} currentPage 当前页码数
 */

// function getReplys(parentId,order,userid,currentPage)
function getReplys()
{
    $.ajax({
        type:'get',
        url:'localhost:8080/WritingCommentServlet?parentId=1&userid=3&order=time&currentPage=1&type=article',
        // url:'/WritingCommentServlet?type=posts&parentId='+parentId+'&order='+order+'&userid='+userid+'&currentPage='+currentPage,
        data:{},
        contentType: "application/json",
        success:function(res){//成功的回调函数
            var pageList = res.pageData.list;
            commentsRender(pageList);
            console.log(res);
        },
        error:function(){//XX失败
            console.log("获取评论出错，获取不到");
        }
    })
}

getReplys();
// getComments();

//—————————————————————— 获取评论的回复后渲染到页码上 ——————————————————————————
function replysCommentsRender(pageList)
{
    var userNickname = pageList.parentComment.comment.userNickname;
    var postTime = pageList.parentComment.comment.createTime;
    var replyContent = pageList.parentComment.comment.content;
    var src = pageList.parentComment.comment.userImg;
    var parentId = pageList.parentComment.comment.parentId;
    replyAddContent(replyListPosition(parentId),userNickname,timestampToTime(postTime),replyContent,src);
}

replyPublishContent(replyListPosition($("#111")),"sfa","111","wjfh","");
//——————————————— 获取回复所属评论的list位置 —————————————————————
function replyListPosition(parentId)
{
    if($(".APlist_userName").text() == parentId)
        console.log(111);
    var replyList = parentId.parent().siblings(".APReplys_list");
    return replyList;
}


//—————————————————————— 获取回复的回复后渲染到页码上 ——————————————————————————
function replysReplysRender(pageList)
{
    var userNickname = pageList.parentComment.comment.userNickname;
    var postTime = pageList.parentComment.comment.createTime;
    var replyContent = pageList.parentComment.comment.content;
    var src = pageList.parentComment.comment.userImg;
    var parentId = pageList.parentComment.comment.parentId;
    var targetId = pageList.parentComment.comment.targetId; //回复是回复那条回复的
    replyAddPublishContent(replyListPosition(parentId),userNickname,targetId,timestampToTime(postTime),replyContent,src);
}
// replyAddPublishContent(replyListPosition($("#111")),"userName","岚岚","123","replyContent","./img/homePage_highSchoolStudent_head.png");
