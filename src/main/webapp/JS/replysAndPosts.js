//——————————————————————————————— 点击评论的回复，回复评论，增加楼层 ———————————————————————————————————
function APlistAddContent(userName,postTime,src)
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
                click: function()
                {
                    var replyContent = $(this).prev("textarea").val(); //回复的内容
                    //———————————————————— 将回复的内容填充进对应的位置 ————————————————————
                    var replyList = thisListReply.parent().parent().parent().find(".APReplys_list");    //要增加的楼层的位置
                    var emptyInput = $(this).prev("textarea");  //输入框

                    //内容判空
                    if(replyContent == undefined || replyContent == null || replyContent == "")
                    {
                        alert("请输入评论的回复");
                        //收起回复框
                        $(".replyInputBox").hide();
                        return false;
                    }

                    //----------创建一个新的楼层 ------------
                    replyPublish(replyList,userName,postTime,replyContent,emptyInput,src);
                    
                    // 回复的“点击阅读全文”
                    readFullArticle($(".APReply_content"));

                    //更改中间内容部分的高度，防止与底部相联
                    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);    
                }
            });
        }
    }); 
}

//——————————————————————————————————— 评论的回复-发布新楼层的函数 ———————————————————————————————————
function replyPublish(replyList,userName,postTime,replyContent,emptyInput,src)
{
    replyPublishUp(replyList,userName,postTime,replyContent,src);
    //发表后清空回复框内的内容
    emptyInput.val("");
    emptyInput.parent().hide();

    clickLike($(".APReply_like"));    //点赞换色

    //—————————————————— 发表评论的存储 ——————————————————
    //所发表的回复的对象评论（即：在该回复在哪个评论下）
    // var targetComment = replyList.prev(".APlist_message").find(".APlist_userName").text();
    // publishPost($("#postReplyContent"),targetComment,userid,parentId);
}

//—————————— 将输入的回复发表到楼层中 ————————————————————
function replyPublishUp(replyList,userName,postTime,replyContent,src)
{
    replyPublishContent(replyList,userName,postTime,replyContent,src);
    
    //点击回复的回复
    APReplyAddContent("MR","111","./img/homePage_highSchoolStudent_head.png");
    
    //点击回复的回复
    // APReplyAddContent("MR","1分钟前");

    // 评论的“点击阅读全文”
    readFullArticle($(".APlist_content"));

    //更改中间内容部分的高度
    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);
}

//———————————————————— 评论的回复 ——————————————————————
function replyPublishContent(replyList,userName,postTime,replyContent,src)
{
    var li = $("<li></li>");
    var str ="<div class='APReply_message'>"+
                "<div class='APReply_headIcon'>"+
                    "<img src='"+src+"'/>"+
                "</div>"+
                "<a class='APReply_userName'>"+userName+"</a>"+
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
}

APlistAddContent("MR","0000");

//————————————— 点击回复的回复，在输入框中获取“@某某”，给评论的回复做回复，增加楼层 ———————————————————
function APReplyAddContent(userName,postTime,src)
{
    $(".APReply_reply").on({
        click: function()
        {
            // console.log("这是回复框的回复");
            var thisListReply = $(this);
            //获取用户名的字符串
            var replyName =  $(this).parent().parent().find(".APReply_message").find(".APReply_userName").text();
            // var replyName = replyNameStr.substr(0,replyNameStr.length-1);
            
             //点击出现回复框，并把要回复的对象加入回复框中
             $(".replyInputBox").show().find("#postReplyContent").val("回复@"+replyName+"：");
            
            $("#postReply").one({
                click: function(){
                    var replyContent = splitContent($(this).prev("textarea").val());  //回复的内容，已截取掉@某某
                    var replyList = thisListReply.parent().parent().parent();    //要增加的楼层的位置
                    var emptyInput = $(this).prev("textarea");  //输入框

                    //输入内容判空
                    if(replyContent == null || replyContent == undefined || replyContent == "")
                    {
                        alert("请输入回复的回复");
                        //收起回复框
                        $(".replyInputBox").hide();
                        return false;
                    }
            
                    replyAddPublish(replyList,userName,replyName,postTime,replyContent,emptyInput,src);
                    
                    //点击回复的回复
                    APReplyAddContent("HHH","111","./img/homePage_highSchoolStudent_head.png");

                    // 回复的“点击阅读全文”
                    readFullArticle($(".APReply_content"));

                    //更改中间内容部分的高度，防止与底部相联
                    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50); 
                }
            });
        }
    });
}

APReplyAddContent("MR","111","./img/homePage_highSchoolStudent_head.png");

//—————————————— 截取掉@某某 ——————————————————
function splitContent(replyContent)
{
    var indexColon =  replyContent.indexOf("：");  //获取第一个冒号所在的下标
    return replyContent.substr(indexColon+1,replyContent.length);
}

//——————————————————————— 发布新楼层的函数（回复的回复） ———————————————————————————————
function replyAddPublish(replyList,userName,replyName,postTime,replyContent,emptyInput,src)
{
    replyAddPublishContent(replyList,userName,replyName,postTime,replyContent,src);
    //发表后清空回复框内的内容
    emptyInput.val("");
    emptyInput.parent().hide();

    //新创建的也可以点击“回复”
    // APReplyAddContent("HHHH","233");
}

function replyAddPublishContent(replyList,userName,replyName,postTime,replyContent,src)
{
    var li = $("<li></li>");
    var str ="<div class='APReply_message'>"+
                "<div class='APReply_headIcon'>"+
                    "<img src='"+src+"'/>"+
                "</div>"+
                "<a class='APReply_userName'>"+userName+"</a>"+
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
}
 
//—————————————————————————————— 评论的AJAX ——————————————————————————————————————————
//——————————————————— 发表评论 ————————————————————————
function publishComment(PC_content,userid,parentId)
{
    $.ajax({
        type:'post',
        url:'/WritingCommentServlet',
        data: JSON.stringify({
            type: 'posts',
            userid: userid,  //当前浏览页面的用户
            parentId: parentId,    //问贴编号
            content: PC_content
        }),
        dataType:'json',
        success:function(res){
            console.log(res)
        },
        error:function(){
            console.log("发表评论存储失败");
        }
    })
}

// publishComment($("#postsTextarea").val(),userid,parentId);

//——————————————————— 发表回复 ————————————————————————
function publishPost(PC_content,targetComment,userid,parentId)
{
    $.ajax({
        type:'post',
        url:'/WritingCommentServlet',
        data: JSON.stringify({
            type: 'posts',
            targetId: targetComment, //该条回复的回复对象，传被回复对象的id
            userid: userid,  //当前浏览页面的用户
            parentId: parentId,    //问贴编号
            content: PC_content
        }),
        dataType:'json',
        success:function(res){
            console.log(res)
        },
        error:function(){
            console.log("发表评论存储失败");
        }
    })
}

//———————————————— 当评论的回复数量大于3，出现“点击查看” —————————————————————————————
function clickUnfoldShow(list,replysCount,floorNum)
{
    //只显示三个，剩下的隐藏掉
    for(var i = 3;i < list.length;i++)
    {
        list.eq(i).hide();  
    }
    //———————————————— 要插入的部分 —————————————————————
    var upfoldBox = $("<div class='upfoldBox'></div>");
    var upfoldStr = "<span>共<b>"+replysCount+"</b>条回复，</span>"+
            "<i>点击查看</i>"+"<i>点击收起</i>";
    upfoldBox.html(upfoldStr);  //隐藏三个，更换效果  
    list.parent().append(upfoldBox);    //将内容插入尾部
    // laypageList(list);  //分页Part
    list.eq(2).css({border: "none"});   //出现多条时出现

    //“点击收起”最开始为隐藏状态
    list.parent().find(".upfoldBox").find("i").eq(1).hide();
    
    //点击对应的“点击查看”后，显示更多$("i[commentssReplyFloor='"+floorNum+"']")
    list.parent().find(".upfoldBox").find("i").eq(0).on({
        click: function(){
            //隐藏“点击查看”，出现“点击收起”
            list.parent().find(".upfoldBox").find("i").eq(0).hide();
            list.parent().find(".upfoldBox").find("i").eq(1).show();
            //点击“点击查看”后，把缩略的出现，同时把原先的3个隐藏掉
            //把最底下三个隐藏起来
            for(var i = 0;i < 3;i++)
            {
                list.eq(list.length-1-i).hide();
                
            }
            //获取最新的回复列表长度的回复列表
            getUnderCommentssReplys(floorNum,1,'time',1,'article',1);
            //计算分页的页数
            var totalPage = Number(replysCount/10);
            //获取页面，创建分页栏
            laypageList($(this).parent(),totalPage,1,1,'time','article',false);            

            // console.log("回复的页码："+totalPage);
            //点击后，收起回复，回到最初的状态
            // list.parent().find(".upfoldBox").find("i").eq(1).on({
            //     click: function(){
            //         clickUnfold(list);
            //         // clickUnfold(getUnderCommentssReplys(1).getReplysList);
            //     }
            // });
            // console.log(getUnderCommentssReplys(floorNum));     
        }
    });
}

//—————————————————————— 点击收起后，能够上拉，只剩下最开始三个 ——————————————————————————
function clickUnfold(list)
{
    // console.log("clickUnfold函数");
    // console.log(list.length);
    //隐藏“点击收起”，出现“点击查看”
    list.parent().find(".upfoldBox").find("i").eq(1).hide();
    list.parent().find(".upfoldBox").find("i").eq(0).show();
    //最后的页脚不显示
    // list.eq(list.length-4).css({border: "none"}).siblings().css({borderBottom: "1px solid #e7e7e7"});
    //点击“点击收起”后，收起只剩下三个
    for(var i = 0;i < list.length;i++)
    {
        //除了最后三个，其他直接去除
        // if(i < 10)
        if(i < list.length-3)
        {
            list.eq(i).remove();
        }
        else{
            //最后3个显示
            list.eq(i).show();
            //最后的页脚不显示
            if(i == list.length-1)
                list.eq(i).css({border: "none"});
        }
    }

    //若出现多页
    // laypageList($(".upfoldBox"),2,$(".answerPosts_list"));

    //更改中间内容部分的高度，防止与底部相联
    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);
}

//——————————— 点击 ———————————————
// clickUnfoldShow($(".APReplys_list > li"));

//———————————————————————— 超过十条有分页，分页Part ——————————————————————————————
//在统计条数和“点击查看/收起”部分出现
function laypageList(positionBox,pageNum)
{
    //创建分页列表
    var ul = $("<ul class='laypagelist'></ul>");
    for(var i = 0;i < pageNum;i++)
    {
        ul.append($("<li>"+(i+1)+"</li>"));
    }
    positionBox.append(ul);
    
    //——————————————————————— 点击页码发送请求取得评论信息 —————————————————————————————
    $(".laypagelist > li").on({
        click: function(){
            var pageNumber = Number($(this).index()+1); //获取所点击的页码数
            //判断是否为评论
            if(isComment)
            {
                //先将原先的全部清空
                $(".answerPosts_list > li").remove();
                //获得评论楼层的
                getCommentsAndReplys(parentId,userid,order,pageNumber,type);
                // getCommentsAndReplys('http://192.168.43.236:8080/WritingCommentServlet?parentId=1&userid=3&order=time&currentPage='+pageNumber+'&type=article');
                //缓慢回到顶部
                $("html,body").stop().animate({
                    scrollTop: 800
                },0);
            }else{
                //获取回复所在的评论楼层
                var replysFloor = $(this).parent().parent().parent();
                //先将原先显示的全部清空，但仍然保留最初的三个
                for(var i = 0;i < replysFloor.children("li").length-3;i++)
                {
                    replysFloor.children("li").eq(i).remove();
                }
                // replysFloor.children("li").remove();

                //获取回复所在的评论楼层的层数
                var replysFloorNum = replysFloor.parent().find(".APlist_content").attr("commentfloor");
                //获得回复楼层的
                getUnderCommentssReplys(replysFloorNum,parentId,order,pageNumber,type,1);
                //缓慢回到该层评论的第一层回复所在的位置
                $("html,body").stop().animate({
                    scrollTop: replysFloor.offset().top
                },500);
            }    
        }
    });
}

// laypageList($(".upfoldBox"),2,$(".answerPosts_list"));
//————————————————————————— 对问贴发表评论 —————————————————————————————————
//点击其他任意区域,"回复"框消失
function clickOtherAreaHidden()
{
    $("body").click(function(e)
    {
        if(!$(e.target).closest(".APlist_reply",".APReply_reply",".replyInputBox").length)
        {
            if($(".replyInputBox").is(":visible"))
            {
                $(".replyInputBox").find("#postReplyContent").val("");
                $(".replyInputBox").hide();
            }

            //阻止事件冒泡
            $(".replyInputBox").stop().on({
                click: function(event)
                {
                    event.stopPropagation();
                }
            });
        }

        //阻止事件冒泡
        $(".replyInputBox").on({
            click: function(event){
                event.stopPropagation();
            }
        });
    });
}

//有bug，会出现输入值后，依旧说为空的情况
clickOtherAreaHidden();
