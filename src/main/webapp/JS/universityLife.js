//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});

// ————————————————————— 大学须知 “手风琴” 效果 ———————————————————————————————
$(".universityGuidelines").eq(0).find(".UG_content").css({width: 480});
$(".universityGuidelines").eq(0).siblings(".universityGuidelines").find(".UG_content").css({width: 0}).find("p").css({display: "none"});

$(".universityGuidelines").on("click",function(){
    $(this).find(".UG_content").animate({
        width: 480
    },600).find("p").animate({
        opacity: 1
    },600).css({display: "block"});

    $(this).find(".UG_content").parent().siblings(".universityGuidelines").find(".UG_content").animate({
        width: 0
    },600).find("p").animate({
        opacity: 0
    },600).css({display: "none"});
});

//——————————————————————— 轮播图 ——————————————————————————— 
$(".HP_item").eq(0).css("left",0);
$(".HP_item").eq(1).css("left",350);
$(".HP_item").eq(2).css("left",700);

var HP_index = 1;  //表示第几张图片在中间

//去掉中间展示的那张的active
function clearActive(){
    for(var i = 0;i < $(".HP_item").length;i++){
        $(".HP_item").eq(i).prop("class",'HP_item');    //把图片里所有的类名全部更改为item
    }
}

//更改active的位置
function goIndex()
{
    clearActive();
    $(".HP_item").eq(HP_index).prop("class",'HP_item active');

    $(".HP_item").eq(HP_index-1).animate({
        left: 0,
        opacity: 0.8
    },400);

    $(".HP_item").eq(HP_index).animate({
        left: 350,
        opacity: 1
    },400);

    // 移动的这一张是2，切回0
    $(".HP_item").eq((HP_index == 2)?0:(Number(HP_index)+1)).animate({
        left: 700,
        opacity: 0.8
    },400);

    //超过3张图片，剩下的全部都在正中间的这张的后面
    // var num = [HP_index-1,HP_index,HP_index+1];
    // $(".HP_item").eq(num).siblings().animate({
    //     left: 350,
    //     opacity: 0.5
    // },400);
}

//下一页，更改index的数值
function goNext()
{
    if(HP_index < 2)
        HP_index++;
    else
        HP_index = 0;
    // console.log(index);
    goIndex();
}

//到上一页
function goPre()
{
    if(HP_index > 0)
        HP_index--;
    else
        HP_index = 2;
    // console.log(index);
    goIndex();
}

// —————————————— 点击事件 ————————————————
$("#HP_Next").on({
    click: function(){
        goNext();
        HP_time = 0;
    }
});

$("#HP_Pre").on({
    click: function(){
        goPre();
        HP_time = 0;
    }
});

// ——————————————————— 鼠标移入后出现按钮 ———————————————————————
$(".HP_carouselBox").on({
    mouseover: function(){
        $(".HP_carouselBtn").css({
            display: "block"
        });
    },
    mouseout: function(){
        $(".HP_carouselBtn").css({
            display: "none"
        });
    }
});


var HP_time = 0;   //定时器图片跳转参数

// 自动轮播（定时器）
setInterval(function()
{
    HP_time++;
    if(HP_time == 20)
    {
        goNext();
        HP_time = 0;
    }   
},150);


