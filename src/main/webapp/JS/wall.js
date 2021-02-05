$(function () {

    //便利贴点击查看与关闭
    console.log($('.ulAll li'));
    $('.ulAll li').click(function () {
        // console.log($(this).find('p').html());
        $('.noteId').html($(this).find('h2').html())
        $('.noteContent').html($(this).find('p').html());
        $('.noteMore').show();
    })
    $('.close').click(function () {
        $('.noteMore').hide();
    })

    //点赞
    $('.noteLike').click(function () {
        $(this).toggleClass('noteLikeAlready');
    })

    //书写便利贴
    $('.noteToWrite').click(function () {
        $('.writeNotes').show();
        // console.log($('.writeNotes button'));
    })
    $('.writeNotes button').click(function () {
        var noteUser = requestCheckUserLogin();
        var noteP = $('.writeNotes textarea').val();
        newNote(noteUser, noteP)
        $('.writeNotes').hide();
    })
    $('.close').click(function () {
        $('.writeNotes').hide();
    })

    //新建一张便利贴 
    function newNote(noteUser, noteP) {
        var li = $("<li></li>");
        // 新建note
        var str = "<a href='#'>" +
            " <h2>" + noteUser + ":</h2>" +
            " <p>" + noteP + "</p>" +
            "</a>";
        li.html(str);   //插入到<li>里
        $("#notesUl").prepend(li);    //插入到楼层里
        li.slideDown();     //为评论的添加缓冲效果
        $('.writeNotes textarea').val('');  //点击发表后，清空输入框里的内容
    }



    /*---------------------------右侧-----------------------------*/
    // 点击小图标后出现侧栏
    $(".clickShowSideBar").on({
        click: function () {
            //点击后水平移动
            $(".clickShowSideBar").css("right", 100);
            //点击后消失
            // $(".clickShowSideBar").fadeOut();
            //侧栏移动Part
            $(".sideBarBox").css("right", 0);
            $(".CRRightBox").css("width", 110);
            clickAnotherPosition();
        }
    });

    //右侧小图标的悬浮动画
    $(".clickShowSideBar").hover(
        function () {
            $(this).animate({
                marginRight: 55
            }, 0);
        },
        function () {
            $(this).animate({
                marginRight: 40
            }, 0);
        });

    //点击其他任意区域，侧栏消失

    function clickAnotherPosition() {
        $("body").on({
            click: function (e) {
                var target = $(e.target);
                //如果是这些部分及以下的子元素，可使用，其他都不可以
                if (!target.is(".sideBarBox")) {
                    if ($(".sideBarBox").css("right") == "0px") {
                        //点击后跟随水平移动
                        $(".clickShowSideBar").css("right", 0);
                        //点击后消失
                        // $(".clickShowSideBar").fadeIn();
                        //侧栏效果
                        $(".sideBarBox").css("right", -110);
                        $(".CRRightBox").css("width", 0);
                    }
                    //阻止事件冒泡
                    $(".sideBarBox").stop().on({
                        click: function (event) {
                            event.stopPropagation();
                        }
                    });
                }
            }
        });
    }

})

function requestCheckUserLogin() {
    $.ajax({
        type: "GET",
        url: "/UserLoginServlet",
        dataType: "json",
        contentType: "application/json",
        /**
         * @param {Object}result
         * @param {Object}result.state              接口状态
         *                                             ——— NOT_LOGGED 未登录
         *                                             ——— LOGGED 已经登录
         * @param {Object}result.user               用户信息包
         * @param {Number}result.user.id            用户id
         * @param {Number}result.user.nickname      用户昵称
         * @param {String}result.user.email         用户邮箱
         * @param {String}result.user.avatarPath    用户头像的Base64数据流
         * @param {Number}result.user.liked         用户获赞数
         * @param {Number}result.user.collected     用户被收藏数
         * @param {Number}result.user.birthday      生日
         * @param {Number}result.user.registerDate  注册时间
         * @param {String}result.user.userType      用户类型
         *                                             ——— SENIOR是高中生
         *                                             ——— COLLEGE是大学生
         *                                             ——— TEACHER是教师
         *                                             ——— OTHERS是其他用户
         *
         */
        success: function (result) {
            return result.user.nickname;
        },
        error: function () {
            console.log("初始化页面，检查用户是否已登录并获取信息失败")
        }
    });
}