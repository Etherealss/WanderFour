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


//跟随外框定义，顶着浏览器页面顶部走
//在框内随页面滑动而滑动
//outsideBox：外框限制的box，insideBox：随页面滑动的box
function scrollLimiteHeight(outsideBox,insideBox)
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
            insideBox.css({top: outsideBox.height()-insideBox.height()+20});  
            // insideBox.css({bottom: 0});
        }   
    });
}



