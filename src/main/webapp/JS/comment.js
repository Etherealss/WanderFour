//点击“点击查看”展开剩余的楼中楼评论
for (var i = 3; i < 10; i++) {
    $(".commentTocomment ul li").eq(i).hide();
}
//点击“点击收起”收起剩余的楼中楼评论
$("i").click(function () {
    for (var i = 3; i < 10; i++) {
        $(".commentTocomment ul li").eq(i).toggle();
    }
    // console.log($(".commentTocomment ul li").eq(4).css('display'));
    // console.log($("i"));
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

//在评论框输入内容点击“发表评论后”增加评论盒子
$('button').click(function () {
    console.log($(this).parent());
    $(this).parent().hide();
    $(this).parent().siblings('div').eq(1).show();
    console.log($(this).parent().siblings('div').eq(1).children('ul'));
    $(this).parent().siblings('div').eq(1).children('ul').append($('.addCommentFirst').clone());
})