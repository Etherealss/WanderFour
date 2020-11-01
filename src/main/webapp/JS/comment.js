$(function () {
    //点击“点击查看”展开剩余的楼中楼评论
    for (var i = 3; i < 10; i++) {
        $(".commentTocomment ul li").eq(i).hide();
    }
    //点击“点击收起”收起剩余的楼中楼评论
    $("i").click(function () {
        for (var i = 3; i < 10; i++) {
            $(".commentTocomment ul li").eq(i).toggle();
        }
        if ($(".commentTocomment ul li").eq(4).css('display') != 'none') {
            $("i").html('点击收起');
        } else {
            $("i").html('点击查看');
        }
    })

    //点击“回复”弹出评论框
    function gonaComment(a) {
        a.click(function () {
            // $(this).parent().parent().parent().append($('.clickComment'));
            console.log($(this).parent().parent().siblings().eq(1));
            $(this).parent().parent().siblings().eq(1).show();
        })
    }
    for (var i = 0; i < 13; i++) {
        gonaComment($('.toComment').eq(i));
    }

    // var date = new Date();
    // var now = date.getFullYear() + "." + date.getMonthr() + 1 + "." + date.getDate();
    // console.log(now);

    //在评论框输入内容点击“发表评论后”增加评论盒子
    $('button').click(function () {
        //获取评论输入框的值
        // console.log($(this).siblings().val());
        //隐藏评论输入框
        $(this).parent().hide();
        //显示楼中楼
        $(this).parent().siblings('div').eq(1).show();
        //向楼中楼ul中添加一个li
        $(this).parent().siblings('div').eq(1).children('ul').append($('.addCommentFirst').eq(0).clone());
        //修改li里的值
        // console.log($(this).parent().siblings('div').eq(1).children('ul').children('li').find('span').eq(1));
        $(this).parent().siblings('div').eq(1).children('ul').children('li').find('span').eq(1).html($(this).siblings().val());
        //修改评论时间
        var date = new Date();
        var day = date.getMonth() + 1;
        var now = date.getFullYear() + "." + day + "." + date.getDate();
        $(this).parent().siblings('div').eq(1).children('ul').children('li').find('span').eq(2).html(now);
        //修改楼中楼个数
        console.log($(this).parent().siblings('div').eq(1).find('strong').html() + 1);
        var allcom = parseInt($(this).parent().siblings('div').eq(1).find('strong').html());
        $(this).parent().siblings('div').eq(1).find('strong').html(allcom + 1);
    })

})
