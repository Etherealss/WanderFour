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

    //调用获取url中参数函数 并传递给ajax对象中
    var params = getParams();
    console.log(params.article);

    //调用获取文章相关内容ajax函数
    showArticle(params.article);

    //点击右下角小箭头返回顶部
    $('.articleShow_back').click(function () {
        $('html , body').animate({ scrollTop: 0 }, 'slow');
    })

    //点击“编辑”调用编辑文章的ajax
    $('.asEdit').click(function () {
        editArticle(params.article);
    })

    //点击“删除”调用删除文章的ajax
    $('.asDelete').click(function () {
        removeArticle(params.article);
    })
})


//获取url中含有的参数
function getParams() {
    var params = {};
    //获取当前界面url中的参数
    var url = location.search;
    //如果存在“？”则说明存在参数
    if (url.indexOf("?") != -1) {
        //去除“？”，保留之后的所有字符
        var str = url.substr(1);
        //切割字符串，把每个参数及参数值分成数组
        var paramArr = str.split("&");
        for (var i = 0; i < paramArr.length; i++) {
            //前者为参数名称，后台为参数值
            params[paramArr[i].split("=")[0]] = paramArr[i].split("=")[1];
        }
    }
    return params;
}

//格式化日期函数
function dateFormat(dateMs) {
    var date = new Date(dateMs);
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDate();
    var hour = date.getHours();
    var mintue = date.getMinutes();
    var second = date.getSeconds();
    return year + "-" + month + "-" + day + " " + hour + ":" + mintue + ":" + second;
}

//获取文章相关内容ajax函数
function showArticle(id) {
    $.ajax({
        type: 'GET',
        url: '/WritingServlet?article=' + id,
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.state.code == "SUCCESS") {
                //检验测试
                console.log('success');
                //显示修改粉色标签
                if (res.writing[i].category == 1) {
                    $('.partitionsTag').html('学习天地');
                } else if (res.writing[i].category == 2) {
                    $('.partitionsTag').html('专业介绍');
                } else if (res.writing[i].category == 3) {
                    $('.partitionsTag').html('大学生活');
                }
                //修改id
                $('#asUserId').html(res.writing.authorId);
                //显示标题
                $('#asmainTitle').html(res.writing.title);
                //显示标签
                if (res.writing.label1 != undefined) {
                    $('#asmainTag1').html(res.writing.label1);
                    $('#asmainTag1').show();
                }
                if (res.writing.label2 != undefined) {
                    $('#asmainTag2').html(res.writing.label2);
                    $('#asmainTag2').show();
                }
                if (res.writing.label3 != undefined) {
                    $('#asmainTag3').html(res.writing.label3);
                    $('#asmainTag3').show();
                }
                if (res.writing.label4 != undefined) {
                    $('#asmainTag4').html(res.writing.label4);
                    $('#asmainTag4').show();
                }
                if (res.writing.label5 != undefined) {
                    $('#asmainTag5').html(res.writing.label5);
                    $('#asmainTag5').show();
                }
                //显示文章内容
                $('#asmainArtical').html(res.writing.content);
                //显示时间 时间毫秒数转换为年月日格式
                var date = dateFormat(res.writing.createTime);
                $('#asCreatTime').html(date);
            } else {
                //检验测试
                console.log(fail);
            }

        }
    })
}

//编辑文章ajax对象
function editArticle(id) {
    $.ajax({
        type: 'PUT',
        url: '/WritingServlet',
        data: {
            type: "article",   //用于区分文章和帖子
            id: id,  //文章编号
            partition: "learning",   //分区 learning / major / college
        },
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.state.code == 'SUCCESS') {
                //TODO 跳转到编辑文章页面
                window.location.href = '/editArticle.html?article=' + id;
            } else if (res.state.code == 'NOT_AUTHOR') {
                alert("只有作者本人才可以删除文章哦！");
            } else if (res.state.code == 'ERROR') {
                alert("接口异常！没有获取到参数！");
            }
        }
    })
}

//删除文章ajax对象
function removeArticle(id) {
    $.ajax({
        type: 'Delete',
        url: '/WritingServlet',
        data: {
            // 文章编号
            article: id,
        },
        dataType: 'json',
        success: function (res) {
            console.log(res);
            if (res.state.code == 'SUCCESS') {
                //TODO 跳转到学习天地
                window.location.href = "/LearningWorld_HS.html";
            } else if (res.state.code == 'NOT_AUTHOR') {
                alert("只有作者本人才可以删除文章哦！");
            } else if (res.state.code == 'ERROR') {
                alert("接口异常！没有获取到参数！");
            }
        }
    })
}

//点赞ajax对象
function sendLike() {
    $.ajax({
        type: 'POST ',
        url: '/LikeServlet',
        data: {
            userid: 1,  //用户id
            targetId: "2",  //目标作品id
            targetType: "article",  //目标作品类型，文章点赞-article，帖子-posts，评论-comment
            likeState: "1"   //点赞状态，1-点赞 0-未点赞/取消赞
        },
        dataType: 'json',
        success: function (res) {
            console.log(res);
        }
    })
}
