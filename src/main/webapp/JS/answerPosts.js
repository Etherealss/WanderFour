//个人中心悬浮
$(".topmargin_personal_center").stop().hover(function () {
    $(this).find("ul").stop().fadeToggle();
});

$(function () {
    var params = getParams();
    getPosts(params.posts);
})

function getPosts(postsId) {
    $.ajax({
        type: 'GET',
        url: '/WritingServlet?posts=' + postsId,
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.state.code == "SUCCESS") {
                //转码
                unescape(res.article);

                //检验测试
                //获取res对象数据中的文章对象，方便调用
                var posts = res.writingBean.writing;

                //显示修改粉色标签
                if (posts.partition == 1) {
                    $('.partitionsTag').html('学习天地');
                } else if (posts.partition == 2) {
                    $('.partitionsTag').html('专业介绍');
                } else if (posts.partition == 3) {
                    $('.partitionsTag').html('大学生活');
                }
                //显示昵称
                $('#asUserId').html(res.article.userNickname);
                //显示标题
                $('#asmainTitle').html(posts.title);
                //显示标签
                if (posts.label1 != undefined) {
                    $('#asmainTag1').html(posts.label1);
                    $('#asmainTag1').show();
                }
                if (posts.label2 != undefined) {
                    $('#asmainTag2').html(posts.label2);
                    $('#asmainTag2').show();
                }
                if (posts.label3 != undefined) {
                    $('#asmainTag3').html(posts.label3);
                    $('#asmainTag3').show();
                }
                if (posts.label4 != undefined) {
                    $('#asmainTag4').html(posts.label4);
                    $('#asmainTag4').show();
                }
                if (posts.label5 != undefined) {
                    $('#asmainTag5').html(posts.label5);
                    $('#asmainTag5').show();
                }
                //显示文章内容
                $('#asmainArtical').html(posts.content);
                //显示时间 时间毫秒数转换为年月日格式
                var date = dateFormat(posts.createTime);
                $('#asCreatTime').html(date);
            } else {
                //检验测试
                console.log(fail);
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

// ————————————————————————— 滑动时被限制在浏览器顶部 —————————————————————————————
//给外框定义的高度为帖子的高度
$(".answerPosts_Content").height($(".answerPostsBox").height() + 50);

//旁边的“最新问贴”和“等你回答”部分（当滑动条滑动，被顶部顶住效果）
scrollLimiteHeight($(".answerPosts_Content"), $(".answerPosts_sideColumn"));

//页面滑动到一定位置时，显示问贴的问题
var timer = null;
$(window).scroll(function () {
    clearTimeout(timer);
    timer = setTimeout(function () {
        if ($(window).scrollTop() > 400) {
            $("#postsHidden").stop().fadeIn(100).show();
            scrollLimiteHeight($(".answerPosts_Content"), $("#postsHidden"));
        } else {
            $("#postsHidden").stop().fadeOut(100).hide();
        }
    }, 10);
});

//————————————————————————— 动态添加楼层 —————————————————————————————
/**
 * @param {*用户名（昵称）} userId
 * @param {*发表时间} postTime
 * @param {*楼层数} floorNum
 * @param {*发表的评论内容} postsContent
 * @param {*点赞数} postsLikeNum
 */

var floorNum = 0;   //层楼数（全局变量）
var userId = "小华er";

// var imgSrc = "../img/homePage_highSchoolStudent_head.png";
function postsPublish(userId, postTime, postsContent, postsLikeNum) {
    var li = $("<li></li>");
    //———————— 楼层发表样式 —————————
    var str = "<div class='APlist_message'>" +
        "<div class='APlist_headIcon'>" +
        "<img src=<img style='background:#f0f'/>" +
        "</div>" +
        "<a class='APlist_userName'>" + userId + "</a>" +
        "<span class='APlist_userIntro'>简介blabla</span>" +
        "<p class='APlist_postTime'>" + postTime + "</p>" +
        "<span class='APlist_position'>" + (++floorNum) + "楼</span>" +
        "</div>" +
        "<p class='APlist_content'>" + postsContent + "</p>" +
        "<div class='APlist_likeAndReply'>" +
        "<div class='APlist_like'>" + postsLikeNum + "</div>" +
        "<span></span>" +
        "<div class='APlist_reply'>回复</div>" +
        "</div>";
    li.html(str);   //插入到<li>里
    $(".answerPosts_list").prepend(li);    //插入到楼层里
    li.slideDown();     //为评论的添加缓冲效果
    $("#postsTextarea").val("");  //点击发表后，清空textarea里的内容

    // 点赞后，更换👍的颜色
    $(".APlist_like").on({
        click: function () {
            $(this).toggleClass("APlist_likeHover");
            // $(this).text("1");
        }
    });
}

//获取在textarea框里获得的文本（postsContent）
function getPostsContent() {
    return $("#postsTextarea").val();
}

//获取当前系统时间
function getPostsTime(timeKeeping) {
    console.log(timeKeeping);
    return postsDisplayTime(timeKeeping);
}

//显示时间
function postsDisplayTime(timeKeeping) {
    var curTime = new Date();
    // setTimeout("getPostsTime()", 60000); //每一分钟更新一次
    //一小时内显示多少分钟前：“XX分钟前”
    if (timeKeeping < 60) {
        return curTime.getMinutes() + "分钟前";
    }
    //24小时内显示多少小时前：“XX小时前”
    else if (timeKeeping < 24 * 60) {
        return curTime.getHours() + "小时前";
    }
    //24小时以上，当年内，显示月日
    else if (timeKeeping >= 24 * 60) {
        return (curTime.getMonth() + 1) + "." + curTime.getDate();
    }
    //过了一年后，显示加上年
    else {
        // curTime.getFullYear() + "." + (curTime.getMonth() + 1) + "." + curTime.getDate();
    }
}

//点击后将输入的内容发表到楼层里
$("#postPosts").on({
    click: function () {
        var nowTime = new Date();
        var timeKeeping = nowTime.getMinutes();

        var postsLikeNum = "点赞";
        postsPublish(userId, getPostsTime(timeKeeping), getPostsContent(), postsLikeNum);
        //更改中间内容部分的高度
        $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);
    }
});

//——————————————— 点击回到顶部 —————————————————
slowToTop($("#returnToTopBtn"));
