//身份进入
$(".homePage_LWid").click(function(){
    $(this).toggleClass("homePage_selected");
});


//在距离顶部某一位置的伸缩与出现消失（得先有position定位）
function flexTop(button)
{
    if($(window).scrollTop() == 0)  //处于顶部时
        button.addClass("homePage_hide");

    $(window).scroll(function()
    {
        if($(this).scrollTop() < 500)
        {
            button.addClass("homePage_hide");
            button.removeClass("homePage_appear");
        }
        else    //滑动到“学习天地”这一部分
        {
            button.addClass("homePage_appear");
            button.removeClass("homePage_hide");
        }
    });
}

flexTop($(".homePage_returnTopBox"));
//侧面点击 隐藏 & 出现
$("#homePage_hideBtn").on("click",function()
{       
    $(".homePage_returnTopBox").toggleClass("homePage_appear");
});


//点击后缓慢回到顶部
slowToTop($("#homePage_returnTopBtn"));
slowToTarget($(".homePage_LearningWorld_btn"),$("#homePage_LearningWorld_target"));
slowToTarget($(".homePage_Professional_Introduction_btn"),$("#homePage_Professional_Introduction_target"));
slowToTarget($(".homePage_University_Life_btn"),$("#homePage_University_Life_target"));



//jQuery直接更改CSS样式，是行内样式
//在页面加载后就执行的函数： $(document).ready();


    
