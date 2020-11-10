//——————————————————————————————— 点击评论的回复，回复评论，增加楼层 ———————————————————————————————————
function APlistAddContent(userName,postTime)
{
    $(".APlist_reply").on({
        click: function()
        {
            var thisListReply = $(this);
            // var replyInputBox = $("<div class='replyInputBox'></div>");
            //点击"回复"后,出现回答框
            $(".replyInputBox").show();

            //点击发表评论后,内容出现在所点击"回复"的评论的回复里
            $("#postReply").one({
                click: function(){
                    var replyContent = $(this).prev("textarea").val(); //回复的内容
                    //———————————————————— 将回复的内容填充进对应的位置 ————————————————————
                    var replyList = thisListReply.parent().parent().parent().find(".APReplys_list");    //要增加的楼层的位置
                    var emptyInput = $(this).prev("textarea");  //输入框

                    //----------创建一个新的楼层 ------------
                    replyPublish(replyList,userName,postTime,replyContent,emptyInput);

                    // 回复的“点击阅读全文”
                    readFullArticle($(".APReply_content"));

                    //更改中间内容部分的高度，防止与底部相联
                    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);    
                }
            });
        }
    });

    
}

//——————————————————————————————————— 发布新楼层的函数 ———————————————————————————————————
function replyPublish(replyList,userName,postTime,replyContent,emptyInput,flag)
{
    var li = $("<li></li>");
    var str ="<div class='APReply_message'>"+
                "<div class='APReply_headIcon'>"+
                    "<img src='./img/homePage_highSchoolStudent_head.png'/>"+
                "</div>"+
                "<a class='APReply_userName'>"+userName+"：</a>"+
                "<p class='APReply_postTime'>"+postTime+"</p>"+
            "</div>"+
            "<p class='APReply_content'>"+replyContent+"</p>"+
            "<div class='APReply_afterMessage'>"+
                "<div class='APReply_reply'>回复</div>"+
                "<span></span>"+
                "<div class='APReply_like'>点赞</div>"+
            "</div>";
    li.html(str);   //将str样式插入<li>里
    replyList.prepend(li);  //将内容插入对应的楼层里
    li.slideDown(); //缓冲效果
    //发表后清空回复框内的内容
    emptyInput.val("");
    emptyInput.parent().hide();

    clickLike($(".APReply_like"));    //点赞换色
}

//————————————————————————— 对问贴发表评论 —————————————————————————————————

APlistAddContent("MR","0000");

//点击其他任意区域,"回复"框消失
// $("document").on("click","not(.replyInputBox)",function()
// {
//     $(".replyInputBox").hide();
//     console.log(1111);

//     //阻止事件冒泡
//     $(".replyInputBox").on({
//         click: function(event){
//             event.stopPropagation();
//         }
//     });
// });

//————————————— 点击回复的回复，在输入框中获取“@某某”，给评论的回复做回复，增加楼层 ———————————————————
function APReplyAddContent(userName,postTime)
{
    $(".APReply_reply").on({
        click: function()
        {
            var thisListReply = $(this);
            var replyNameStr =  $(this).parent().parent().find(".APReply_message").find(".APReply_userName").text();
            var replyName = replyNameStr.substr(0,replyNameStr.length-1);
            //点击出现回复框，并把要回复的对象加入回复框中
            $(".replyInputBox").show().find("#postReplyContent").val("回复@"+replyName+"：");
            console.log(replyName);
            
            $("#postReply").one({
                click: function(){
                    var replyContent = splitContent($(this).prev("textarea").val());  //回复的内容，已截取掉@某某
                    var replyList = thisListReply.parent().parent().parent();    //要增加的楼层的位置
                    var emptyInput = $(this).prev("textarea");  //输入框

                    replyAddPublish(replyList,userName,replyName,postTime,replyContent,emptyInput);
                    
                    // 回复的“点击阅读全文”
                    readFullArticle($(".APReply_content"));

                    //更改中间内容部分的高度，防止与底部相联
                    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50); 
                }
            });
        }
    });
}

APReplyAddContent("MR","111");

//—————————————— 截取掉@某某 ——————————————————
function splitContent(replyContent)
{
    var indexColon =  replyContent.indexOf("：");  //获取第一个冒号所在的下标
    return replyContent.substr(indexColon+1,replyContent.length);
}

//——————————————————————————————————— 发布新楼层的函数 ———————————————————————————————————
function replyAddPublish(replyList,userName,replyName,postTime,replyContent,emptyInput)
{
    var li = $("<li></li>");
    var str ="<div class='APReply_message'>"+
                "<div class='APReply_headIcon'>"+
                    "<img src='./img/homePage_highSchoolStudent_head.png'/>"+
                "</div>"+
                "<a class='APReply_userName'>"+userName+"：</a>"+
                "<p class='APReply_postTime'>"+postTime+"</p>"+
            "</div>"+
            "<p class='APReply_content' style='text-indent:0;'>"+
                "<b>回复<span style='color:#4a9cd6'>@"+replyName+"</span>：</b>"+    //@某某
                replyContent+
            "</p>"+
            "<div class='APReply_afterMessage'>"+
                "<div class='APReply_reply'>回复</div>"+
                "<span></span>"+
                "<div class='APReply_like'>点赞</div>"+
            "</div>";
    li.html(str);   //将str样式插入<li>里
    replyList.prepend(li);  //将内容插入对应的楼层里
    li.slideDown(); //缓冲效果
    //发表后清空回复框内的内容
    emptyInput.val("");
    emptyInput.parent().hide();

    //新创建的也可以点击“回复”
    APReplyAddContent("HHHH","233");
}

//—————————————————————————————— 评论的AJAX ——————————————————————————————————————————
//——————————————————— 发表评论 ———————————————————
function publishComment(PC_content)
{
    $.ajax({
        type:'post',
        url:'/WritingCommentServlet',
        data:{
            type: 'posts',
            userid: 1,  //当前浏览页面的用户
            parentId: 3,    //问贴编号
            content: PC_content
        },
        dataType:'json',
        success:function(res){
            console.log(res)
        },
        error:function(){
            console.log("发表评论存储失败");
        }
    })
}

//——————————————————— 发表回复 ———————————————————
function publishPost(PC_content,targetComment){
    $.ajax({
        type:'post',
        url:'/WritingCommentServlet',
        data:{
            type: 'posts',
            targetId: targetComment, //该条回复的回复对象，传被回复对象的id
            userid: 1,  //当前浏览页面的用户
            parentId: 3,    //问贴编号
            content: PC_content
        },
        dataType:'json',
        success:function(res){
            console.log(res)
        },
        error:function(){
            console.log("发表评论存储失败");
        }
    })
}

//———————————— 获取评论 ————————————
function getComments()
{
    $.ajax({
        type:'get',
        url:'/WritingCommentServlet?type=xxx&parentId=xxx&order=xxx&userid=xxx&currentPage=xxx',
        data:{

        },
        contentType: "application/json",
        success:function(res){//成功的回调函数
            console.log(res);
        },
        error:function(){//XX失败
            console.log("获取评论出错，获取不到");
        }
    })
}
