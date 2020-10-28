//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});

// 专业板块悬浮显示
$(".PI_card").on({
    mouseenter: function(){
        $(this).find(".PI_hoverHiddenBox").stop().fadeIn(500);
    },
    mouseleave: function(){
        $(this).find(".PI_hoverHiddenBox").stop().fadeOut(500);
    }
});

//————————————————————————— 专业板块侧栏切换 ———————————————————————————
//第一个默认选中状态
$(".PI_sideBarBox > ul").find("li").eq(0).css({
    color: "#5ec1b8",
    background: "#f7f7f7"
});

//其他的点中的效果
function clickSideBarBtn()
{
    $(".PI_sideBarBox > ul").find("li").on({
        click: function()
        {
            $(this).css({
                color: "#5ec1b8",
                background: "#f7f7f7",
                boxShadow: "1px 1px 5px rgba(0,0,0,0.2)"
            }).siblings().css({
                color: "#333333",
                background: "#fff",
                boxShadow: "none"
            });
        }
    });
}

clickSideBarBtn();


// 学校板块悬浮显示
$(".SI_card").on({
    mouseenter: function(){
        // $(this).find(".SI_hoverHiddenBox").stop().fadeIn(500);
        $(this).find(".SI_hoverHiddenBox").stop().show(500);
    },
    mouseleave: function(){
        // $(this).find(".SI_hoverHiddenBox").stop().fadeOut(500);
        $(this).find(".SI_hoverHiddenBox").stop().hide(500);
    }
});

// —————————————————————— 问贴Part ——————————————————————————————
//在框内随页面滑动而滑动
//outsideBox：外框限制的box，insideBox：随页面滑动的box
function scrollLimitation(outsideBox,insideBox)
{ 
    $(window).scroll(function()
    {
        var scrollLen = $(window).scrollTop();    //滑动条滑动的位移
        var distance = scrollLen - outsideBox.offset().top;   //insideBox的位移

        if(scrollLen < outsideBox.offset().top){
            insideBox.css({top: 0});
        }
        else if(scrollLen >= outsideBox.offset().top && 
        scrollLen <= outsideBox.offset().top+outsideBox.height()-insideBox.height()+40){
            insideBox.css({top: distance});
        }
        else{   //scrollLen > outsideBox.offset().top+outsideBox.height()-insideBox.height()
            insideBox.css({top: outsideBox.height()-insideBox.height()+45});  
        }     
    });
}

// —————————————————————————— 查看更多 ——————————————————————————————
// 默认时为收起状态 ———— 问贴
$("#PI_toAsk .PI_TA_askPost").eq(3).hide();
$("#PI_toAsk .PI_TA_askPost").eq(4).hide();


//动态增加更改PI_TA_mainBox的高度
function PI_changeHeight(outsideHeight,insideHeight)
{
    outsideHeight.height(insideHeight.height());
}

//问贴Part
// PI_changeHeight($(".PI_TA_mainBox"),$(".PI_TA_mainContent"));
$(".PI_TA_mainBox").height($(".PI_TA_mainContent").height()+100);


//点击加载更多
function moreContent(clickBtn,anotherBtn,articlePart,outsideHeight,insideHeight)
{
    clickBtn.on("click",function()
    {
        for(var i = 3;i < 5;i++)
        {
            articlePart.eq(i).show();
        }
        
        $(this).css({display : "none"});    
        anotherBtn.css({display : "block"});

        //动态增加更改PI_LE_mainBox的高度
        PI_changeHeight(outsideHeight,insideHeight);
    });
}

// 点击收起更多
function pickContent(clickBtn,anotherBtn,articlePart,outsideHeight,insideHeight)
{
    clickBtn.on("click",function()
    {
        for(var i = 3;i < 5;i++)
        {
            articlePart.eq(i).hide();
        }
        $(this).css({display : "none"});    
        anotherBtn.css({display : "block"});

        //动态增加更改PI_LE_mainBox的高度
        PI_changeHeight(outsideHeight,insideHeight);
        //解决点击收起后，侧栏会慢一拍的问题
        $(".PI_TA_slideBox").css({top: $(".PI_TA_mainBox").height()-$(".PI_TA_slideBox").height()+45});
    });
}

//自动收起更多（如果在另外一个标签里点击了“查看更多”，切换标签时，自动收起）
function autoPickUp(clickBtn,anotherBtn,articlePart,outsideHeight,insideHeight)
{
    for(var i = 3;i < 5;i++)
    {
        $("#English"+i).hide();
        $("#Chinese"+i).hide();
        // articlePart.eq(i).css({display:"none"});
    }

    clickBtn.css({display : "none"});    
    anotherBtn.css({display : "block"});

    //动态增加更改PI_LE_mainBox的高度
    PI_changeHeight(outsideHeight,insideHeight);
 
}

// 问贴部分（只有一个）
moreContent($("#PI_seeMore0"),$("#PI_packUp0"),$("#PI_toAsk .PI_TA_askPost"),
$(".PI_TA_mainBox"),$(".PI_TA_mainContent"));
pickContent($("#PI_packUp0"),$("#PI_seeMore0"),$("#PI_toAsk .PI_TA_askPost"),
$(".PI_TA_mainBox"),$(".PI_TA_mainContent"));


//问贴左边滑动效果
scrollLimitation($(".PI_TA_mainBox"),$(".PI_TA_slideBox"));

//点击滑动返回指定位置
// slowToTop($(".LearningWorld_returnTopBtn"));
slowToTarget($(".schoolIntroduction_Btn"),$(".professionalIntroduction_Title"));
slowToTarget($(".professionalIntroduction_Btn"),$(".schoolIntroduction_Title"));

//———————————————————————— 问贴悬浮后的效果 ——————————————————————————————
function PI_askDefault()
{   
    $(".PI_TA_askPost").eq(0).css({
        height: 265
    }).find(".PI_TA_comment").show();
    
    $(".PI_TA_askPost").eq(0).siblings().css({
        height: 167.7
    }).find(".PI_TA_comment").hide();
}

//点击的显示，其他的收起
function PI_askArticle()
{
    $(".PI_TA_askPost").on({
        click: function()
        {
            $(this).stop().animate({
                height: 265
            }).find(".PI_TA_comment").show();

            $(this).siblings(".PI_TA_askPost").stop().animate({
                height: 167.7
            }).find(".PI_TA_comment").hide();
        }
    });
}

PI_askDefault();    //默认的时候，只第一个显示
PI_askArticle();    //点击高度增加

//——————————————————————— 跳转到指定位置 ———————————————————————————
slowToTarget($(".PI_toAsk"),$(".PI_toAskBox > h2"));
slowToTarget($(".PI_toWriting"),$(".PI_learning_experience > h2"));

//——————————————— 点击回到顶部 —————————————————
slowToTop($("#returnToTopBtn"));
