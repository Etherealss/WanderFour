// 点击后缓慢到达所在位置（clickBtn点击处，target跳转目标地）
function slowToTarget(clickBtn,target)
{
    clickBtn.on("click",function()
    {
        //页面滚动效果
        $("html,body").stop().animate({
            scrollTop: target.offset().top - 20
        },1000);
    });
}

//点击后缓慢回到顶部
function slowToTop(clickBtn)
{
    clickBtn.on("click",function(){
        $("html,body").stop().animate({
            scrollTop: 0
        },1000);
    });
}




