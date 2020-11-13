$(function () {

    //动态添加一级评论
    /**
     * @param {*} userId 用户名（昵称）
     * @param {*} postTime 发表时间
     * @param {*} floorNum 楼层数
     * @param {*} postsContent 发表的评论内容
     * @param {*} postsLikeNum 点赞数
     */

    // var floorNum = 0;   //层楼数（全局变量）
    var userId = "小华er";

    // var imgSrc = "../img/homePage_highSchoolStudent_head.png";
    function postsPublish(userId, postTime, postsContent, postsLikeNum) {
        var li = $("<li></li>");
        //———————— 楼层发表样式 —————————
        var str = "<div class='asmainComments'>" +
            "<div class='asCommentsContent'>" +
            "<div class='asCommentsContentOri'>" +
            "<img src='./img/articleShowLogo/userLogo.png' alt=''>" +
            "<span class='asCommentsContentID'>" + userId + ":</span>" +
            "<span class='asCommentConmain'>" + postsContent + "</span>" +
            "<div class='asCommentInteract'>" +
            "<span class='asCommmentTime'>" + postTime + "</span>" +
            "<span class='toComment'>回复</span>" +
            "<img src='./img/articleShowIcon/asUnLike.png' alt='>" +
            "</div>" +
            "</div>";
        li.html(str);   //插入到<li>里
        $("#asmainCommentsUl").prepend(li);    //插入到楼层里
        li.slideDown();     //为评论的添加缓冲效果
        $("#toComInput").val("");  //点击发表后，清空textarea里的内容

    }

    //获取在textarea框里获得的文本（postsContent）
    function getPostsContent() {
        return $("#toComInput").val();
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
    $("#clickToCom").on({
        click: function () {
            var nowTime = new Date();
            var timeKeeping = nowTime.getMinutes();

            var postsLikeNum = "点赞";
            postsPublish(userId, getPostsTime(timeKeeping), getPostsContent(), postsLikeNum);
            //更改中间内容部分的高度
            // $(".answerPosts_Content").height($(".answerPostsBox").height() + 50);
        }
    });


})
