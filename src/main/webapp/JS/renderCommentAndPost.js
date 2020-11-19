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

//—————————————————————————————— 初始化评论和回复区域 ——————————————————————————————
function initializeCommentsAndReplys(type,parentId,userid)
{
    $.ajax({
        type:'get',
        // url: 'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&type=article',
        url:'http://192.168.43.236:8080/WritingCommentServlet?type='+type+'&userid=3&parentId='+parentId,
        dataType: 'json',
        contentType: "application/json",
        success:function(res){//成功的回调函数
            console.log(res);
            var commentsList = res.pageData.list;   //评论列表
            var totalCount = res.pageData.totalCount;   //总条数
            var totalPage = res.pageData.totalPage; //总页码数
            //获取最新评论的楼层，若有新加的评论，即赋予楼层数
            commentsFloorNum += totalCount;
            //渲染评论列表
            for(var i = 0;i < commentsList.length;i++)
            {
                commentsRender(commentsList,i);
            }

            //判断是否显示加载更多和页面
            countsAndPages(totalCount,totalPage,type,parentId,userid);
        },
        error:function(){
            console.log("获取评论出错，获取不到");
        }
    });
}
 
//加载页面时初始化出来的
// initializeCommentsAndReplys('article',1,3);

//—————————————————— 判断是否显示评论列表的加载更多和页面 ——————————————————
function countsAndPages(totalCount,totalPage,type,parentId,userid)
{
    //获取总条数后，确定是否显示“加载更多”（大于3，显示“加载更多”）
    if(totalCount > 3)
    {
        $(".answerLayPageBox > p").show();
        //评论部分点击“加载更多”，出现更多评论
        $(".answerLayPageBox > p").on({
            click: function(){
                //先将原先的全部清空
                $(".answerPosts_list > li").remove();
                //加载出页码为1的评论
                getCommentsAndReplys(parentId,userid,"time",1,type);
                // getCommentsAndReplys('http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&order=time&currentPage=1&type=article');
                $(".answerLayPageBox > p").hide();  //点击完“加载更多”后，加载更多消失
               //总条数大于10，显示分页栏
                if(totalCount > 10)
                {
                    totalPage = Number(totalCount/10);
                    var isComment = true;   //判断是评论还是回复
                    //创建分页栏
                    laypageList($(".answerLayPageBox"),totalPage,parentId,userid,"time",type,isComment);
                    // laypageList($(".answerLayPageBox"),totalPage,$(".answerPosts_list"));   
                }
            }
        });
    }
}

//—————————————————————————————— 点击评论列表的“加载更多”出现 ——————————————————————————————
// getCommentsAndReplys('http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&order=time&currentPage=1&type=article');

function getCommentsAndReplys(parentId,userid,order,currentPage,type)
{
    $.ajax({
        type:'get',
        // url: 'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&type=article',
        //1评论3回复的测试192.168.43.236
        // url:'http://192.168.137.150:8080/WritingCommentServlet?parentId=4&userid=3&order=time&currentPage=1&type=article',
        //热点的ip
        // url: 'http://192.168.43.236:8080/WritingCommentServlet?parentId=4&userid=3&order=time&currentPage=1&type=article',
        // url:'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&order=time&currentPage=1&type=article&targetId=1',
        //直接上的
        url:'http://192.168.43.236:8080/WritingCommentServlet?parentId='+parentId+'&order='+order+'&currentPage='+currentPage+'&type='+type,
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
        },
        error:function(){
            console.log("获取评论出错，获取不到");
        }
    });
}
// getCommentsAndReplys('http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&order=time&currentPage=1&type=article');

//————————————————————————— 评论下的回复，点击“加载更多”后才出现 ——————————————————————————————————
function getUnderCommentssReplys(floorNum,parentId,order,currentPage,type,targetId)
{
    $.ajax({
        type:'get',
        //热点的ip
        // url:'http://192.168.43.236:8080/WritingCommentServlet?parentId=1&order=time&currentPage=1&type=article&targetId=1',
        //直接上的
        // url:'http://192.168.137.150:8080/WritingCommentServlet?parentId=1&order=time&currentPage=1&type=article&targetId=1',
        url:'http://192.168.43.236:8080/WritingCommentServlet?parentId='+parentId+'&order='+order+'&currentPage='+currentPage+'&type='+type+'&targetId='+targetId,
        dataType: 'json',
        contentType: "application/json",
        success:function(res){//成功的回调函数
            console.log(res);
            var replysList = res.pageData.list;   //评论下的回复列表

            //渲染评论下的回复列表
            for(var i = 0;i < replysList.length;i++)
            {   //有依赖的回复即为回复的回复
                if(replysList[i].beRepliedComment !== undefined)
                {
                    replysReplysRender(replysList[i],floorNum);
                }
                else{
                    replysCommentsRender(replysList[i].parentComment,floorNum);
                }
            }

            //评论底下所显示的回复列表当前页面最后一个消失
            replyListPosition(floorNum).find("li").eq(replysList.length-1).css({border: "none"});

            // 点击“点击收起”后，收起回复，回到最初的状态
            replyListPosition(floorNum).find("li").parent().find(".upfoldBox").find("i").eq(1).one({
                click: function(){
                    //点击收起后，将分页去除掉，让下一次点击在生成
                    $(this).parent().find(".laypagelist").remove();
                    //重新加载回初始化的状态（一条评论带三条回复）
                    clickUnfold(replyListPosition(floorNum).find("li"));
                }
            });
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

    //获取该层评论下的回复的条数，用其来判断是否有“点击查看”
    var replysCount = commentsList[num].replysCount;

    //渲染该层评论
    postPostsUp(userId, timestampToTime(postTime), postsContent, like,src,floorNum);
    //渲染该层评论下的回复
    for(var i = 0;i < commentsList[num].replys.length;i++)
    {
        replysCommentsRender(commentsList[num].replys[i],floorNum);
    }

    //当回复的条数 > 3，出现“点击查看”样式（该样式在回复所属的评论楼层中）
    if(replysCount > 3)
    {
        clickUnfoldShow(replyListPosition(floorNum).find("li"),replysCount,floorNum);
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
    var replyList = $(".APlist_content[commentfloor='"+floorNum+"']").parent().find(".APReplys_list");
    // var replyList = $(".APlist_content[commentFloor='"+floorNum+"']").parent().find(".APReplys_list");
    return replyList;
}

//—————————————————————— 获取回复的回复后渲染到页码上 ——————————————————————————
function replysReplysRender(replys,floorNum)
{
    var userNickname = replys.parentComment.userNickname;
    var postTime = replys.parentComment.comment.createTime;
    var replyContent = replys.parentComment.comment.content;
    var src = replys.parentComment.userImg;
    //被回复的对象的昵称
    var repliedTargetName = replys.beRepliedComment.userNickname;
    replyAddPublishContent(replyListPosition(floorNum),userNickname,repliedTargetName,timestampToTime(postTime),replyContent,src);
}

// replyAddPublishContent(replyListPosition($("#111")),"userName","岚岚","123","replyContent","./img/homePage_highSchoolStudent_head.png");

//————————————————————————————— 获取评论的回复列表的分页 ——————————————————————————————————



//获取当前页面的用户信息
//回复的回复
// APReplyAddContent(userName,postTime,src);

//评论的回复
//APlistAddContent(userName,postTime,src);
