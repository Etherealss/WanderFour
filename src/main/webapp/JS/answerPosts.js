//个人中心悬浮
$(".topmargin_personal_center").stop().hover(function () {
    $(this).find("ul").stop().fadeToggle();
});

$(function () {
    var params = getParams();
    getBesideWritingList(1,"posts")
    getPosts(params.posts);
})

//问贴渲染的ajax函数
function getPosts(postsId) {
    $.ajax({
        type: 'GET',
        url: '/WritingServlet?posts=' + postsId,
        dataType: 'json',
        contentType: "application/json",
        success: function (res) {
            console.log(res);
            if (res.state.code == "SUCCESS") {
                
                //转码
                unescape(res.writingBean);

                //检验测试
                //获取res对象数据中的文章对象，方便调用
                var posts = res.writingBean.writing;
                console.log(posts);
                //显示修改粉色标签
                // if (posts.partition == 1) {
                //     $('.partitionsTag').html('学习天地');
                // } else if (posts.partition == 2) {
                //     $('.partitionsTag').html('专业介绍');
                // } else if (posts.partition == 3) {
                //     $('.partitionsTag').html('大学生活');
                // }
                //显示昵称
                $('#answerPostsAuthor').val(posts.userNickname);
                //显示标题
                $('.postsTitle h3').text(posts.title);
                //显示文章内容
                $('.answerPosts_txt').html(posts.content);
                //显示标签
                // if (posts.label1 != undefined) {
                //     $('#asmainTag1').html(posts.label1);
                //     $('#asmainTag1').show();
                // }
                // if (posts.label2 != undefined) {
                //     $('#asmainTag2').html(posts.label2);
                //     $('#asmainTag2').show();
                // }
                // if (posts.label3 != undefined) {
                //     $('#asmainTag3').html(posts.label3);
                //     $('#asmainTag3').show();
                // }
                // if (posts.label4 != undefined) {
                //     $('#asmainTag4').html(posts.label4);
                //     $('#asmainTag4').show();
                // }
                // if (posts.label5 != undefined) {
                //     $('#asmainTag5').html(posts.label5);
                //     $('#asmainTag5').show();
                // }
                //显示时间 时间毫秒数转换为年月日格式
                // var date = dateFormat(posts.createTime);
                // $('#asCreatTime').html(date);
            } else {
                //检验测试
                console.log("fail");
            }

        }
    })
}

// 点赞后，更换👍的颜色
$(".APlist_like").on({
    click: function () {
        $(this).toggleClass("APlist_likeHover");
        // $(this).text("1");
    }
});

//全局变量————楼层数
var commentsFloorNum = 1;

// ————————————————————————— 滑动时被限制在浏览器顶部 —————————————————————————————
// 评论的“点击阅读全文”
readFullArticle($(".APlist_content"));
// 回复的“点击阅读全文”
readFullArticle($(".APReply_content"));

//给外框定义的高度为帖子的高度
$(".answerPosts_Content").height($(".answerPostsBox").height() + 50);

//旁边的“最新问贴”和“等你回答”部分（当滑动条滑动，被顶部顶住效果）
scrollLimiteHeight($(".answerPosts_Content"), $(".answerPosts_sideColumn"));

//页面滑动到一定位置时，显示固定到顶部的问贴问题
$(window).scroll(function()
{
    if($(window).scrollTop() > $(".answerPostsPart").height()+80)
    {
        $("#postsHidden").css({
            display: "block"
        });
    }
    else{
        $("#postsHidden").css({
            display: "none"
        });
    }
});

//————————————————————————— 动态添加楼层 —————————————————————————————
/**
 * @param {*} userId 用户名（昵称）
 * @param {*} postTime 发表时间
 * @param {*} floorNum 楼层数
 * @param {*} postsContent 发表的评论内容
 * @param {*} postsLikeNum 点赞数
 */

var userId = "小华er";

function postsPublish(userId, postTime, postsContent, postsLikeNum,src,floorNum) 
{
    var li = $("<li></li>");
    //———————— 楼层发表样式 —————————
    var str = "<div class='APlist_message'>" +
        "<div class='APlist_headIcon'>" +
        "<img src='"+src+"'/>" +
        "</div>" +
        "<a class='APlist_userName'>" + userId + "</a>" +
        "<span class='APlist_userIntro'>简介blabla</span>" +
        "<p class='APlist_postTime'>" + postTime + "</p>" +
        "<span class='APlist_position'>" + (commentsFloorNum != 1?floorNum:commentsFloorNum) + "楼</span>" +
        "</div>" +
        //"<p class='APlist_content' commentFloor='"+floorNum+"'>" + postsContent + "</p>" +
        "<p class='APlist_content' commentFloor='"+(commentsFloorNum != 1?floorNum:commentsFloorNum)+"'>" + postsContent + "</p>" +
        "<div class='APlist_likeAndReplyBox'>"+
            "<div class='APlist_likeAndReply'>" +
                "<div class='APlist_reply'>回复</div>" +
                "<span></span>" +
                "<div class='APlist_like'>" + postsLikeNum + "</div>" +
            "</div>"+
        "</div>"+
        "<ul class='APReplys_list'></ul>";
    li.html(str);   //插入到<li>里
    $(".answerPosts_list").prepend(li);    //插入到楼层里
    li.slideDown();     //为评论的添加缓冲效果
    $("#postsTextarea").val("");  //点击发表后，清空textarea里的内容

    clickLike($(".APlist_like"));    //点赞换色
}

// ———————————————————————— 点赞后，更换👍的颜色 ——————————————————————————————
function clickLike(AP_like)
{
    AP_like.on({
        click: function () {
            $(this).toggleClass("APlist_likeHover");
            if($(this).hasClass("APlist_likeHover"))    //点击前后显示“1”和“点赞”
            {
                $(this).text("1");
            }
            else
            {
                $(this).text("点赞");
            }
        }
    });
}

//获取在textarea框里获得的文本（postsContent）
function getPostsContent() {
    return $("#postsTextarea").val();
}

//———————————————————— 点击后将输入的内容发表到楼层里 ————————————————————
function clickPostPosts()
{
    $("#postPosts").on({
        click: function () 
        {
            if(getPostsContent() == undefined || getPostsContent() == null || getPostsContent() == "")
            {
                alert("请输入内容再发表评论");
                return false;
            }
            time = '1分钟前';
            //发表评论的ajax
            // publishComment(getPostsContent(),userid,parentId);
            //发表在楼层中
            postPostsUp(userId, time, getPostsContent());
            //点击评论的回复
            APlistAddContent("MR","1分钟前");
        }
    });
}

clickPostPosts();

//————— 输入的内容发表到楼层里 ———————————
function postPostsUp(userId,time,postContent,postsLikeNum,src,floorNum)
{
    var postsLikeNum = "点赞";
    postsPublish(userId, time,postContent,postsLikeNum,src,floorNum);
    
    commentsFloorNum++;
    
    //点击评论的回复
    // APlistAddContent("MR","1分钟前");

    // 评论的“点击阅读全文”
    readFullArticle($(".APlist_content"));

    //更改中间内容部分的高度
    $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);
}

//——————————————— 点击回到顶部 —————————————————
slowToTop($("#returnToTopBtn"));

//加载更多本为隐藏
$(".answerLayPageBox > p").hide();