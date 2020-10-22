$(function () {

    //顶部导航栏二级导航显示隐藏
    $("#asPersonalCenter").hover(function () {
        $('#asTopNavMore').stop().fadeToggle();
    });

    //封装点击切换函数
    function asNavCurrent(a, b) {
        a.click(function () {
            a.siblings().removeClass();
            a.addClass(b);
        })
    }

    //Nav调用点击切换函数
    for (var i = 1; i <= 5; i++) {
        asNavCurrent($('#showNewNav' + i + ''), 'showNewNavCurrent');
        asNavCurrent($('#showHotNav' + i + ''), 'showNewNavCurrentHot');
    }

    //点赞收藏
    function LikeAndCol(a, b) {
        a.click(function () {
            a.hide();
            b.show();
        })
    }
    LikeAndCol($('.asmainLike'), $('.asamainLikeOver'));
    LikeAndCol($('.asamainLikeOver'), $('.asmainLike'));
    LikeAndCol($('.asmainCol'), $('.asmainColOver'));
    LikeAndCol($('.asmainColOver'), $('.asmainCol'));

    //调用获取文章相关内容ajax函数
    showArticle();
})


//获取文章相关内容ajax函数
function showArticle() {
    $.ajax({
        type: 'GET',
        url: '/WritingServlet?article=' + id + '',
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.state.code == "SUCCESS") {
                //检验测试
                console.log('success');
                //修改id
                $('#asUserId').html(res.article.authorId);
                //显示标题
                $('#asmainTitle').html(res.article.title);
                //显示标签
                if (res.article.label1 != undefined) {
                    $('#asmainTag1').html(res.article.label1);
                    $('#asmainTag1').show();
                    $('#asmainTags').show();
                }
                if (res.article.label2 != undefined) {
                    $('#asmainTag2').html(res.article.label2);
                    $('#asmainTag2').show();
                }
                if (res.article.label3 != undefined) {
                    $('#asmainTag3').html(res.article.label3);
                    $('#asmainTag3').show();
                }
                if (res.article.label4 != undefined) {
                    $('#asmainTag4').html(res.article.label4);
                    $('#asmainTag4').show();
                }
                if (res.article.label5 != undefined) {
                    $('#asmainTag5').html(res.article.label5);
                    $('#asmainTag5').show();
                }
                //显示文章内容
                $('#asmainArtical').html(res.article.content);
                //显示时间 时间毫秒数转换为年月日格式
                var date = new Date(res.article.createTime);
                $('#asCreatTime').html(date);
            } else {
                //检验测试
                console.log(fail);
            }

        }
    })
}