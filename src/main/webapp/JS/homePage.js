$(function () {
    history.pushState(null, null, "index.html");
    window.addEventListener('popstate', function () {
        history.pushState(null, null, "index.html");
    });
})
//身份进入
$(".homePage_LWid").click(function () {
    $(this).toggleClass("homePage_selected");
});


//在距离顶部某一位置的伸缩与出现消失（得先有position定位）
function flexTop(button) {
    if ($(window).scrollTop() == 0)  //处于顶部时
    {
        button.addClass("homePage_hide");
        $(".topmargin_box").show();
    }

    $(window).scroll(function () {
        if ($(this).scrollTop() < 500) {
            button.addClass("homePage_hide");
            button.removeClass("homePage_appear");
            $(".topmargin_box").hide();
        }
        else    //滑动到“学习天地”这一部分
        {
            button.addClass("homePage_appear");
            button.removeClass("homePage_hide");
            $(".topmargin_box").show();
        }

        if ($(this).scrollTop() < 50) {
            $(".topmargin_box").show();
        }
    });
}

flexTop($(".homePage_returnTopBox"));
//侧面点击 隐藏 & 出现
$("#homePage_hideBtn").on("click", function () {
    $(".homePage_returnTopBox").toggleClass("homePage_appear");
});


//点击后缓慢回到顶部
slowToTop($("#homePage_returnTopBtn"));
slowToTarget($(".homePage_LearningWorld_btn"), $("#homePage_LearningWorld_target"));
slowToTarget($(".homePage_Professional_Introduction_btn"), $("#homePage_Professional_Introduction_target"));
slowToTarget($(".homePage_University_Life_btn"), $("#homePage_University_Life_target"));



//jQuery直接更改CSS样式，是行内样式
//在页面加载后就执行的函数： $(document).ready();

//——————————————————————— 轮播图 ——————————————————————————— 
$(".HP_item").eq(0).css("left", 0);
$(".HP_item").eq(1).css("left", 350);
$(".HP_item").eq(2).css("left", 700);

var HP_index = 1;  //表示第几张图片在中间

//去掉中间展示的那张的active
function clearActive() {
    for (var i = 0; i < $(".HP_item").length; i++) {
        $(".HP_item").eq(i).prop("class", 'HP_item');    //把图片里所有的类名全部更改为item
    }

    for (var i = 0; i < $(".HP_point").length; i++) {
        $(".HP_point").eq(i).prop("class", 'HP_point');
    }
}

//更改active的位置
function goIndex() {
    clearActive();
    $(".HP_item").eq(HP_index).prop("class", 'HP_item active');
    $(".HP_point").eq(HP_index).prop("class", 'HP_point active');

    $(".HP_item").eq(HP_index - 1).stop().animate({
        left: 0,
        opacity: 0.5
    }, 400);

    $(".HP_item").eq(HP_index).stop().animate({
        left: 350,
        opacity: 1
    }, 400);

    // 移动的这一张是2，切回0
    $(".HP_item").eq((HP_index == 2) ? 0 : (Number(HP_index) + 1)).stop().animate({
        left: 700,
        opacity: 0.5
    }, 400);

    //超过3张图片，剩下的全部都在正中间的这张的后面
    // var num = [HP_index-1,HP_index,HP_index+1];
    // $(".HP_item").eq(num).siblings().animate({
    //     left: 350,
    //     opacity: 0.5
    // },400);
}

//下一页，更改index的数值
function goNext() {
    if (HP_index < 2)
        HP_index++;
    else
        HP_index = 0;
    // console.log(index);
    goIndex();
}

//到上一页
function goPre() {
    if (HP_index > 0)
        HP_index--;
    else
        HP_index = 2;
    // console.log(index);
    goIndex();
}

// —————————————— 点击事件 ————————————————
$("#HP_Next").on({
    click: function () {
        goNext();
        HP_time = 0;
    }
});

$("#HP_Pre").on({
    click: function () {
        goPre();
        HP_time = 0;
    }
});

var HP_time = 0;   //定时器图片跳转参数
//—————————————— 下面小圆点的绑定 ———————————————————
$(".HP_point").on({
    mouseenter: function () {
        var pointIndex = $(this).attr("number");
        HP_index = pointIndex;
        goIndex();
        HP_time = 0;
        console.log(HP_index);
    }
});

// ——————————————————— 鼠标移入后出现按钮 ———————————————————————
$(".HP_carouselBox").on({
    mouseover: function () {
        $(".HP_carouselBtn").css({
            display: "block"
        });
    },
    mouseout: function () {
        $(".HP_carouselBtn").css({
            display: "none"
        });
    }
});

// 自动轮播（定时器）
setInterval(function () {
    HP_time++;
    if (HP_time == 20) {
        goNext();
        HP_time = 0;
    }
}, 150);



