$(function () {

      //顶部导航栏二级导航显示隐藏
      $(".userPersonalCenter").hover(function () {
        $('#userTopNavMore').stop().fadeToggle();
    });

    //封装点击切换板块函数
    function userCurrent(a, b, c) {
        a.click(function () {
            a.siblings().removeClass();
            b.siblings().hide();
            a.addClass(c);
            b.show();
        })
    }

    //大nav调用点击切换板块函数
    for (var i = 1; i <= 4; i++) {
        userCurrent($('#userNavLi' + i + ''), $('#differentDetails' + i + ''), 'userNavCurrent');
    }

    //“我的收藏”板块小nav调用点击切换板块函数
    for (var i = 1; i <= 2; i++) {
        userCurrent($('#userColBox' + i + ''), $('#userMyCollection' + i + ''), 'userColBoxCurrent');
    }

    function clickNone(a, b, i) {
        a.eq(i).click(function () {
            b.eq(i).hide();
        })
    }

    for (var i = 0; i < 5; i++){
        clickNone($('.userCollectionStar'), $('.userColBoxContentMain'), i);
    }
 

})