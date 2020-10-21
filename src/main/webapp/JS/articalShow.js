/**
 * 获取url中的参数，返回参数包对象
 * @returns {{}} params
 */
getParams = function () {
    var params = {};
    //获取当前界面url中的参数
    var url = location.search;
    console.log(url);
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
$(function () {

    //顶部导航栏二级导航显示隐藏
    $("#asPersonalCenter").hover(function () {
        $('#asTopNavMore').fadeToggle();
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

    //点赞收藏效果
    $('.asmainLike').click(function () {
        $(this).css('background', 'url(./img/articleShowIcon/asLike.png) no-repeat left bottom / 19px 19px');
    })
    $('.asmainCol').click(function () {
        $(this).css('background', 'url(./img/articleShowIcon/asCollection.png) no-repeat left bottom / 25px 24px');
    })

    //获取url的参数
    var params = getParams();
    //输出参数看一下，看看有没有错误
    console.log(params);
    //调用获取文章相关内容ajax函数
    //传入参数
    //参数名为article，所以调用时就写params.article(访问对象属性)
    inquireArticle(params.article);
})

function inquireArticle(articleId) {
    //获取文章相关内容ajax函数
    $.ajax({
        type: 'GET',
        //在末端添加文章的id，以指定要访问的文章
        url: '/WritingServlet?article=' + articleId,
        dataType: 'json',
        /**
         * @param res
         * @param res.article
         * @param res.state
         */
        success: function (res) {
            console.log(res);
            if (res.state.code == "SUCCESS") {
                //检验测试
                console.log("success");
                //修改id
                $('#asUserId').text("文章编号："+res.article.id);
                //显示标题
                $('#asmainTitle').text(res.article.title);
                //显示标签
                if (res.article.label1 != undefined) {
                    $('#asmainTag1').text(res.article.label1);
                }
                if (res.article.label2 != undefined) {
                    $('#asmainTag2').text(res.article.label2);
                }
                if (res.article.label3 != undefined) {
                    $('#asmainTag3').text(res.article.label3);
                }
                if (res.article.label4 != undefined) {
                    $('#asmainTag4').text(res.article.label4);
                }
                if (res.article.label5 != undefined) {
                    $('#asmainTag5').text(res.article.label5);
                }
                //显示文章内容
                $('#asmainArtical').text(res.article.content);
                //显示时间 时间毫秒数转换为年月日格式
                var date = dateFormat(res.article.createTime);
                $('#asCreatTime').text(date);
            } else {
                //检验测试
                console.log(fail);
            }

        }
    })
}
/**
 * 日期毫秒数转为字符串
 * @param {Number} dateMs 日期毫秒数
 * @returns {string} 格式化日期yyyy-MM-dd HH:mm:ss
 */
dateFormat = function (dateMs) {
    /*
    实践证明，从后端获取到的日期对象时以毫秒数发送的
    但是此处直接使用接收到的数据调用日期对象的方法会报错
    应该用获得到的毫秒数创建js日期对象
     */
    var date = new Date(dateMs);
    var year = date.getFullYear();
    var month = date.getMonth();
    var day = date.getDate();
    var hour = date.getHours();
    var mintue = date.getMinutes()
    var second = date.getSeconds()
    return year + "-" + month + "-" + day + " " + hour + ":" + mintue + ":" + second;
}