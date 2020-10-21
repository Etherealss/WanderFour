//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});

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

//点击更改层次（“HOT”和“适合你的”）
function switchover(switchPartBox,num,switchPart)
{
    switchPartBox.find("button").eq(num).on("click",function()
    {
        switchPart.css({zIndex: 10});
        switchPart.siblings().css({zIndex: 0});
        switchPartBox.find("button").eq(num).css({color: "#81cbbf"});
        switchPartBox.find("button").eq(num).siblings().css({color: "#000000"});

        autoPickUp($(".LearningWorld_packUp"),$(".LearningWorld_seeMore"),$(".LearningWorld_LE_article"),
        $(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent"));
    });
}

//HOT
switchover($(".LearningWorld_LE_optionBox"),0,$("#LearningWorld_HOT"));
//适合你的
switchover($(".LearningWorld_LE_optionBox"),1,$("#LearningWorld_suit"));


// —————————————————————————— 查看更多 ——————————————————————————————
// 默认时为收起状态
//热点
$("#LearningWorld_HOT .LearningWorld_LE_article").eq(3).hide();
$("#LearningWorld_HOT .LearningWorld_LE_article").eq(4).hide();
//适合部分
$("#LearningWorld_suit .LearningWorld_LE_article").eq(3).hide();
$("#LearningWorld_suit .LearningWorld_LE_article").eq(4).hide();

//问贴
$("#LearningWorld_toAsk .LearningWorld_TA_askPost").eq(3).hide();
$("#LearningWorld_toAsk .LearningWorld_TA_askPost").eq(4).hide();


//动态增加更改LearningWorld_LE_mainBox和LearningWorld_TA_mainBox的高度
function LearningWorld_changeHeight(outsideHeight,insideHeight)
{
    outsideHeight.height(insideHeight.height());
}

function aaa()
{
    console.log("$('.LearningWorld_TA_mainBox'):"+$(".LearningWorld_TA_mainBox").height());
console.log("$('.LearningWorld_TA_mainContent'):"+$(".LearningWorld_TA_mainContent").height());
}

//问贴Part
LearningWorld_changeHeight($(".LearningWorld_TA_mainBox"),$(".LearningWorld_TA_mainContent"));
//文章Part
LearningWorld_changeHeight($(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent"));

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

        //动态增加更改LearningWorld_LE_mainBox的高度
        LearningWorld_changeHeight(outsideHeight,insideHeight);
    });
}
$(function () {
    $("#test-box").click(function () {
        window.location.href = "/article.html";
    });
});

function toArticle() {
   window.location.href = "/articleShow.html?article=63";
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

        //动态增加更改LearningWorld_LE_mainBox的高度
        LearningWorld_changeHeight(outsideHeight,insideHeight);
        //解决点击收起后，侧栏会慢一拍的问题
        $(".LearningWorld_TA_slideBox").css({top: $(".LearningWorld_TA_mainBox").height()-$(".LearningWorld_TA_slideBox").height()+45});
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

    //动态增加更改LearningWorld_LE_mainBox的高度
    LearningWorld_changeHeight(outsideHeight,insideHeight);
 
}

// 问贴部分（只有一个）
moreContent($("#LearningWorld_seeMore0"),$("#LearningWorld_packUp0"),$("#LearningWorld_toAsk .LearningWorld_TA_askPost"),
$(".LearningWorld_TA_mainBox"),$(".LearningWorld_TA_mainContent"));
pickContent($("#LearningWorld_packUp0"),$("#LearningWorld_seeMore0"),$("#LearningWorld_toAsk .LearningWorld_TA_askPost"),
$(".LearningWorld_TA_mainBox"),$(".LearningWorld_TA_mainContent"));


//————————————— 要区分好哪一些按键是属于哪部分的，否则最终起作用的可能只有第一个 ——————————————————————————————
//HOT 部分
moreContent($("#LearningWorld_seeMore1"),$("#LearningWorld_packUp1"),$("#LearningWorld_HOT .LearningWorld_LE_article"),
$(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent:nth-child(1)"));
pickContent($("#LearningWorld_packUp1"),$("#LearningWorld_seeMore1"),$("#LearningWorld_HOT .LearningWorld_LE_article"),
$(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent:nth-child(1)"));

//适合你的 部分
moreContent($("#LearningWorld_seeMore2"),$("#LearningWorld_packUp2"),$("#LearningWorld_suit .LearningWorld_LE_article"),
$(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent:nth-child(2)"));
pickContent($("#LearningWorld_packUp2"),$("#LearningWorld_seeMore2"),$("#LearningWorld_suit .LearningWorld_LE_article"),
$(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_mainContent:nth-child(2)"));

//问贴左边滑动效果
scrollLimitation($(".LearningWorld_TA_mainBox"),$(".LearningWorld_TA_slideBox"));

//文章右边滑动效果
scrollLimitation($(".LearningWorld_LE_mainBox"),$(".LearningWorld_LE_slideBox"));

//点击滑动返回顶部
slowToTop($(".LearningWorld_returnTopBtn"));

//———————————————————————— 问贴悬浮后的效果 ——————————————————————————————

askDefault();
function askDefault()
{
    $(".LearningWorld_TA_askPost").eq(0).css({
        height: 265
    }).find(".LearningWorld_TA_comment").show();
    
    $(".LearningWorld_TA_askPost").eq(0).siblings().css({
        height: 167.7
    }).find(".LearningWorld_TA_comment").hide();
}

// $(".LearningWorld_TA_askPost").on({
//     mouseenter: function()
//     {
//         $(this).stop().animate({
//             height: 265
//         },1000);

//         $(this).siblings().stop().animate({
//             height: 167.7
//         },1000);

        
//     }
// });

//——————————————————————— 跳转到指定位置 ———————————————————————————
slowToTarget($(".LearningWorld_toAsk"),$(".LearningWorld_toAskBox > h2"));
slowToTarget($(".LearningWorld_toWriting"),$(".LearningWorld_learning_experience > h2"));

