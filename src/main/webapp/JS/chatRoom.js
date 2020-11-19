//点击网站图标，直接跳转到首页
$("#sideBarToHomePage").on({
    click: function(){
        window.open('./index.html');
        // window.location.href = './index.html';
    }
});

//点击个人中心，直接跳转到个人中心
$("#personalCenter").on({
    click: function(){
        window.open("./user.html");
        // window.location.href = './user.html';
    }
});

// 点击小图标后出现侧栏
$(".clickShowSideBar").on({
    click: function(){
        //点击后水平移动
        $(".clickShowSideBar").css("right",100);
        //点击后消失
        // $(".clickShowSideBar").fadeOut();
        //侧栏移动Part
        $(".sideBarBox").css("right",0);
        $(".CRRightBox").css("width",110);
        clickAnotherPosition();
    }
});

//点击其他任意区域，侧栏消失

function clickAnotherPosition()
{
    $("body").on({
        click:function(e)
        {
            var target = $(e.target);
            //如果是这些部分及以下的子元素，可使用，其他都不可以
            if(!target.is(".sideBarBox"))
            {
                if($(".sideBarBox").css("right") == "0px")
                {
                    //点击后跟随水平移动
                    $(".clickShowSideBar").css("right",0);
                    //点击后消失
                    // $(".clickShowSideBar").fadeIn();
                    //侧栏效果
                    $(".sideBarBox").css("right",-110);
                    $(".CRRightBox").css("width",0);
                }
                //阻止事件冒泡
                $(".sideBarBox").stop().on({
                    click: function(event)
                    {
                        event.stopPropagation();
                    }
                });
            }
        }
    });
}

// ——————————————————————— 创建标签样式 ———————————————————————————
// —————————————— 创建用户自己的发言 ————————————————
function createUserSelfPost(src,content)
{
    var li = $("<li class='userSelfPost'></li>");
    var liStr = "<img class='uersSelfImg' src='"+src+"'/>"+
    "<span>"+content+"</span>";
    li.html(liStr);
    $("#charFrame").append(li);
}
//src = ./img/homePage_highSchoolStudent_head.png

// —————————————— 创建对方用户的发言 ————————————————
function createUserOtherPost(src,content)
{
    var li = $("<li class='userOtherPost'></li>");
    var liStr = "<img class='uersOtherImg' src='"+src+"'/>"+
    "<span>"+content+"</span>";
    li.html(liStr);
    $("#charFrame").append(li);
}

//————————————————————— 在文本框内输入后，点击发送，发送到框内 ———————————————————————————
$("#postReplyBtn").on({
    click: function()
    {
        //创建显示框
        createUserSelfPost("./img/homePage_highSchoolStudent_head.png",$("#editReplyContent").val());
        //发表出去后清空文本框
        $("#editReplyContent").val("");
    }
});




