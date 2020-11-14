/* 时间戳转日期 */
function timestampToTime(timestamp) 
{
    var date = new Date(timestamp);
    year = date.getFullYear() + '.';
    month = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '.';
    day = date.getDate() < 10 ? '0'+date.getDate() : date.getDate();
    hour = date.getHours() + ':';
    minute = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
    second = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());

    return year + month + day;
}

//————————————————— 初始化的评论及回复，页面一加载就存在的 ————————————————————————————————
/**
 * @param {*} parentId 该回复在哪个评论下，评论id
 * @param {*} order 所获取到数据的排列方式（时间time，获赞数like）
 * @param {*} userid 当前浏览页码的用户id（即：使用者）
 * @param {*} currentPage 当前页码数
 */

// getCommentsAndReplys(1,3);
// function getCommentsAndReplys(parentId,userid,order,currentPage)
function getCommentsAndReplys()
{
    $.ajax({
        type:'get',
        url: 'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&type=article',
        //1评论3回复的测试
        // url:'http://192.168.137.150:8080/WritingCommentServlet?parentId=4&userid=3&order=time&currentPage=1&type=article',
        //热点的ip
        // url: 'http://192.168.43.236:8080/WritingCommentServlet?parentId=4&userid=3&order=time&currentPage=1&type=article',
        // url:'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&order=time&currentPage=1&type=article&targetId=1',
        //直接上的
        // url:'/WritingCommentServlet?type=posts&parentId='+parentId+'&order='+order+'&currentPage='+currentPage,
        dataType: 'json',
        contentType: "application/json",
        success:function(res){//成功的回调函数
            console.log(res);
            var commentsList = res.pageData.list;   //评论列表
            // var commentsList = res.commentData;
            // console.log(commentsList);
            //渲染评论列表
            for(var i = 0;i < commentsList.length;i++)
            {
                commentsRender(commentsList,i);
            }
            // clickUnfold($(".APReplys_list > li"));
            // console.log(res);
        },
        error:function(){
            console.log("获取评论出错，获取不到");
        }
    });
}
// getCommentsAndReplys();
// getCommentsAndReplys(4,3,time,1,article);
// getReplys(1,"time",3,1);

//——————————————————————— 点击查看更多按钮后才出现的数据 ———————————————————————————————
$(".upfoldBox > i").on({
    click: function()
    {
        // getUnderCommentssReplys();
        $(this).css({color: "#f00"});
        console.log("点击了评论下的回复");
    }
});


//————————————————————————— 评论下的回复，点击后才出现 ——————————————————————————————————
function getUnderCommentssReplys()
{
    $.ajax({
        type:'get',
        //热点的ip
        url:'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&order=time&currentPage=1&type=article&targetId=1',
        //直接上的
        // url:'/WritingCommentServlet?type=posts&parentId='+parentId+'&order='+order+'&currentPage='+currentPage,
        dataType: 'json',
        contentType: "application/json",
        success:function(res){//成功的回调函数
            console.log(res);
            var commentsList = res.pageData.list;   //评论下的回复列表
            //渲染评论下的回复列表
            for(var i = 0;i < commentsList.length;i++)
            {   //有依赖的回复即为回复的回复
                if(commentsList[i].parentComment.beRepliedComment !== undefined)
                {
                    replysReplysRender(commentsList[i].parentComment,4);
                    // console.log("第"+i+"条是回复的回复");
                }
                else{
                    replysCommentsRender(commentsList[i].parentComment,4);
                }
            }
            // clickUnfold($(".APReplys_list > li"));
        },
        error:function(){
            console.log("获取评论下的回复出错，获取不到");
        }
    });
}

//—————————————————————— 获取评论后渲染到页码上 ——————————————————————————
function commentsRender(commentsList,num)
{
    var userId = commentsList[num].parentComment.userNickname;
    var postTime = commentsList[num].parentComment.comment.createTime;
    var postsContent = commentsList[num].parentComment.comment.content;
    var like = commentsList[num].parentComment.comment.like;
    var src = commentsList[num].parentComment.userImg;
    var floorNum = num+1;   //当前评论所属的楼层数

    //判断是否为被回复的消息
    // var beRepliedComment = commentsList[num].parentComment.beRepliedComment;

    //渲染该层评论
    postPostsUp(userId, postTime, postsContent, like,src,floorNum);
    //渲染该层评论下的回复
    for(var i = 0;i < commentsList[num].replys.length;i++)
    {
        replysCommentsRender(commentsList[num].replys[i],floorNum);
    }
}

//点击“查看更多”出现的其他分页，每个分页都发送请求

//—————————————————————— 获取评论的回复后渲染到页码上 ——————————————————————————
function replysCommentsRender(replys,floorNum)
{
    var userNickname = replys.userNickname;
    var postTime = replys.comment.createTime;
    var replyContent = replys.comment.content;
    var src = replys.userImg;
    //评论的回复
    replyPublishUp(replyListPosition(floorNum),userNickname,timestampToTime(postTime),replyContent,src);
}

// replyPublishContent(replyListPosition($("#111")),"sfa","111","wjfh","");
//————————————————————— 获取回复所属评论的list位置 ———————————————————————————
function replyListPosition(floorNum)
{   //根据楼层数，让回复跟着所在的评论楼层
    var replyList = $(".APlist_content[commentFloor='"+floorNum+"']").parent().find(".APReplys_list");
    return replyList;
}

//—————————————————————— 获取回复的回复后渲染到页码上 ——————————————————————————
function replysReplysRender(replys,floorNum)
{
    var userNickname = replys.userNickname;
    var postTime = replys.comment.createTime;
    var replyContent = replys.comment.content;
    var src = replys.userImg;
    //被回复的对象的昵称
    var repliedTargetName = replys.beRepliedComment.id;
    replyAddPublishContent(replyListPosition(floorNum),userNickname,repliedTargetName,timestampToTime(postTime),replyContent,src);
}
// replyAddPublishContent(replyListPosition($("#111")),"userName","岚岚","123","replyContent","./img/homePage_highSchoolStudent_head.png");

//————————————————————————————— 获取被回复回复的值replyName ——————————————————————————————————
function getReplyName()
{

}


//获取当前页面的用户信息
//回复的回复
// APReplyAddContent(userName,postTime,src);

//评论的回复
//APlistAddContent(userName,postTime,src);