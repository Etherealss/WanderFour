$(function () {

    //便利贴点击查看与关闭
    console.log($('.ulAll li'));
    $('.ulAll li').click(function () {
        $('.noteMore').show();
    })
    $('.close').click(function () {
        $('.noteMore').hide();
    })
})