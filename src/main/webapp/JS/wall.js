$(function () {

    //便利贴点击查看与关闭
    console.log($('.ulAll li'));
    $('.ulAll li').click(function () {
        // console.log($(this).find('p').html());
        $('.noteContent').html($(this).find('p').html());
        $('.noteMore').show();
    })
    $('.close').click(function () {
        $('.noteMore').hide();
    })

})