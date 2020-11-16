$(function () {

    //获取当前时间
    function dateFormat() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + "." + month + "." + day;
    }

    //动态添加一级评论
    /**
     * @param {*} userId 用户名（昵称）
     * @param {*} postTime 发表时间
     * @param {*} floorNum 楼层数
     * @param {*} postsContent 发表的评论内容
     * @param {*} postsLikeNum 点赞数
     */

    var userId = "小华er";  //TODO 后端传递的用户名

    function postsPublishFirst(userId, postTime, postsContent, postsLikeNum) {
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
            "<img src='./img/articleShowIcon/asUnLike.png' alt=''>" +
            "</div>" +
            "</div>";

        li.html(str);   //插入到<li>里
        console.log(li.find('.toComment'));
        $("#asmainCommentsUl").prepend(li);    //插入到楼层里
        li.slideDown();     //为评论的添加缓冲效果
        $("#toComInput").val("");  //点击发表后，清空输入框里的内容
        li.find('.toComment').on({
            click: function () {
                clickToComSecond($(this));
                console.log($(this));
                //更改中间内容部分的高度
                $(this).parent().parent().parent().height($(this).parent().parent().parent().height() + 10);
            }
        })

    }

    //获取在input框里获得的文本（postsContent）
    function getPostsContent() {
        return $("#toComInput").val();
    }

    //点击后将输入的内容发表到楼层里
    $("#clickToCom").on({
        click: function () {
            var postsLikeNum = "点赞";
            if ($("#toComInput").val() == "") {
                alert("请输入内容！");
                return;
            }
            postsPublishFirst(userId, dateFormat(), getPostsContent(), postsLikeNum);
            //更改中间内容部分的高度
            $(".articleShow_body").height($(".articleShow_main").height() + 50);

        }

    });


    //动态添加二级评论
    /**
     * @param {*} userId 用户名（昵称）
     * @param {*} postTime 发表时间
     * @param {*} floorNum 楼层数
     * @param {*} postsContent 发表的评论内容
     * @param {*} postsLikeNum 点赞数
     */

    var userId = "小华er";  //TODO 后端传递的用户名
    function postsPublishSecond(userId, postTime, postsContent, postsLikeNum) {
        var li = $("<li class='addCommentFirst'></li>");
        //———————— 楼层发表样式 —————————
        var str = " <div>" +
            "<img src='./img/articleShowLogo/userLogo.png' alt='>" +
            "<span class='asCommentsContentID'>" + userId + ":</span>" +
            "<span class='asCommentConmain'>" + postsContent + "</span>" +
            "<div class='asCommentInteract'>" +
            "<span class='asCommmentTime'>" + postTime + "</span>" +
            "<img src='./img/articleShowIcon/asUnLike.png' alt=''>" +
            "</div>" +
            "</div>";
        li.html(str);   //插入到<li>里
        $(".asCommentsUlScond").prepend(li);    //插入到楼层里
        li.slideDown();     //为评论的添加缓冲效果
        $("#toComInput").val("");  //点击发表后，清空输入框里的内容

    }

    //点击后将输入的内容发表到楼层里
    // $("#clickToCom").on({
    //     click: function () {
    //         var postsLikeNum = "点赞";
    //         postsPublishFirst(userId, dateFormat(), getPostsContent(), postsLikeNum);
    //         //更改中间内容部分的高度
    //         $(".articleShow_body").height($(".articleShow_main").height() + 50);
    //     }
    // });

    //点击回复显示二级评论框
    function clickToComSecond(a) {
        var toComSecond = $("<div class='clickComment clickCommentInit'></div>");
        //———————— 楼层发表样式 —————————
        var str = "<input type='text' placeholder='请输入评论...'>" +
            " <button>发表评论</button>";
        toComSecond.html(str);
        a.parent().parent().parent().append(toComSecond);    //插入到楼层里
        toComSecond.slideDown();
    }

    for (var i = 0; i < $('.toComment').length; i++) {
        $('.toComment').eq(i).on({
            click: function () {
                clickToComSecond($(this));
                console.log($(this));
                //更改中间内容部分的高度
                $(this).parent().parent().parent().height($(this).parent().parent().parent().height() + 10);
            }
        });
    }

})
