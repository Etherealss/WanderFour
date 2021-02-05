$(function () {

    //获取当前时间
    function dateFormat() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + "." + month + "." + day;
    }

    //获取在input框里获得的文本（postsContent）
    function getPostsContent(a) {
        return a.val();
    }

    /* ----------------------------------动态添加一级评论---------------------------- */
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
        var str = "<div class='asCommentsContent'>" +
            "<div class='asCommentsContentOri'>" +
            "<img src='./img/articleShowLogo/userLogo.png' alt=''>" +
            "<span class='asCommentsContentID'>" + userId + ":</span>" +
            "<span class='asCommentConmain'>" + postsContent + "</span>" +
            "<div class='asCommentInteract'>" +
            "<span class='asCommmentTime'>" + postTime + "</span>" +
            "<span class='toComment'>回复</span>" +
            "<img src='./img/articleShowIcon/asUnLike.png' alt=''>" +
            "</div>" +
            "</div>" +
            " <div class='commentTocomment' style='display: none;'>" +
            "<ul class='asCommentsUlScond'>" +
            "</ul>" +
            "<div>共<strong>1条</strong>回复，<i style='color: #81CBBF; cursor: pointer;'>点击查看</i></div>" +
            " </div>" +
            "</div>";
        li.html(str);   //插入到<li>里
        $("#asmainCommentsUl").prepend(li);    //插入到楼层里
        li.slideDown();     //为评论的添加缓冲效果
        $("#toComInput").val("");  //点击发表后，清空输入框里的内容
        //为新增加的li里的“回复”添加点击弹出二级评论框事件
        li.find('.toComment').one({
            click: function () {
                clickToComSecond($(this));
                // console.log($(this));
                //更改中间内容部分的高度
                $(this).parent().parent().parent().height($(this).parent().parent().parent().height() + 10);
            }
        })

    }

    //点击后将输入的内容发表到楼层里
    $("#clickToCom").on({
        click: function () {
            var postsLikeNum = "点赞";
            if ($("#toComInput").val() == "") {
                alert("请输入内容！");
                return;
            }
            postsPublishFirst(userId, dateFormat(), getPostsContent($("#toComInput")), postsLikeNum);
            //更改中间内容部分的高度
            $(".articleShow_body").height($(".articleShow_main").height() + 50);

        }
    });


    /* ----------------------------------动态添加二级评论---------------------------- */
    /**
     * @param {*} userId 用户名（昵称）
     * @param {*} postTime 发表时间
     * @param {*} floorNum 楼层数
     * @param {*} postsContent 发表的评论内容
     * @param {*} postsLikeNum 点赞数
     */
    var userId = "小华er";  //TODO 后端传递的用户名

    function postsPublishSecond(userId, postTime, postsContent, postsLikeNum) {
        var li = $("<li></li>");
        //———————— 楼层发表样式 —————————
        var str = " <div>" +
            "<img src='./img/articleShowLogo/userLogo.png' alt=''>" +
            "<span class='asCommentsContentID'>" + userId + ":</span>" +
            "<span class='asCommentConmain'>" + postsContent + "</span>" +
            "<div class='asCommentInteract'>" +
            "<span class='asCommmentTime'>" + postTime + "</span>" +
            "<img src='./img/articleShowIcon/asUnLike.png' alt=''>" +
            "</div>" +
            "</div>";
        li.html(str);   //插入到<li>里
        return li;
        // $(".asCommentsUlScond").prepend(li);    //插入到楼层里
        // li.slideDown();     //为评论的添加缓冲效果
        //$(".ComInputSecond").val("");  //点击发表后，清空输入框里的内容

    }

    //添加二级评论框
    function clickToComSecond(a) {
        var toComSecond = $("<div class='clickComment clickCommentInit'></div>");
        //———————— 楼层发表样式 —————————
        var str = "<input type='text' class='ComInputSecond' placeholder='请输入评论...'>" +
            " <button class='clickComSecond'>发表评论</button>";
        toComSecond.html(str);
        a.parent().parent().parent().append(toComSecond);    //插入到楼层里
        toComSecond.slideDown();

        //点击后将输入的内容发表到楼层里
        $('.clickComSecond').on({
            click: function () {
                var postsLikeNum = "点赞";
                console.log($(this).parent().siblings().eq(1).find('.asCommentsUlScond'));
                console.log();
                $(this).parent().siblings().eq(1).find('.asCommentsUlScond').prepend(postsPublishSecond(userId, dateFormat(), getPostsContent($('.ComInputSecond')), postsLikeNum));
                console.log(postsPublishSecond(userId, dateFormat(), getPostsContent($('.ComInputSecond')), postsLikeNum));
                $(this).parent().siblings().eq(1).show();
                //更改中间内容部分的高度
                // $(".articleShow_body").height($(".articleShow_main").height() + 50);
                $(this).parent().parent().height($(this).parent().parent().height() + 50);
                // console.log($(this).parent());
                $(this).parent().remove();
                // $(".ComInputSecond").val("");  //点击发表后，清空输入框里的内容
            }
        });
    }

    //点击“回复”弹出二级评论框
    for (var i = 0; i < $('.toComment').length; i++) {
        $('.toComment').eq(i).one({
            click: function () {
                clickToComSecond($(this));
                // console.log($(this));
                //更改中间内容部分的高度
                $(this).parent().parent().parent().height($(this).parent().parent().parent().height() + 10);
            }
        });
    }

    //点击后将输入的内容发表到楼层里
    for (var i = 0; i < $('.clickComSecond').length; i++) {
        $('.clickComSecond').on({
            click: function () {
                var postsLikeNum = "点赞";
                console.log($(this).parent().siblings().eq(1).find('.asCommentsUlScond'));
                console.log();
                $(this).parent().siblings().eq(1).find('.asCommentsUlScond').prepend(postsPublishSecond(userId, dateFormat(), getPostsContent($('.ComInputSecond')), postsLikeNum));
                console.log(postsPublishSecond(userId, dateFormat(), getPostsContent($('.ComInputSecond')), postsLikeNum));
                $(this).parent().siblings().eq(1).show();
                //更改中间内容部分的高度
                // $(".articleShow_body").height($(".articleShow_main").height() + 50);
                $(this).parent().parent().height($(this).parent().parent().height() + 50);
                // console.log($(this).parent());
                $(this).parent().remove();
                // $(".ComInputSecond").val("");  //点击发表后，清空输入框里的内容
            }
        });
    }


    // for (var i = 0; i < $('.clickComSecond').length; i++){
    // }
    // $('.clickComSecond').on({
    //     click: function () {
    //         var postsLikeNum = "点赞";
    //         postsPublishSecond(userId, dateFormat(), getPostsContent($('.ComInputSecond')), postsLikeNum);
    //         //更改中间内容部分的高度
    //         $(".articleShow_body").height($(".articleShow_main").height() + 50);
    //         console.log($(this).parent);

    //     }
    // });

})
