$(function () {
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

    //调用获取url中参数函数 并传递给ajax对象中
    var params = getParams();
    console.log(params.partition);

    //调用初始化文章的ajax
    InitArticle(params.partition, params.order);

    //获取后端存储的文章ajax
    function InitArticle(partition, order) {
        $.ajax({
            type: 'GET',
            url: '/InitWritingController?partition=' + partition + '&order=' + order,
            dataType: 'json',
            contentType: "application/json",
            success: function (res) {
                console.log(res);
                //显示修改粉色标签
                if (res.articleList[i].partition == 1) {
                    $('.partitionsTag').html('学习天地');
                } else if (res.articleList[i].partition == 2) {
                    $('.partitionsTag').html('专业介绍');
                } else if (res.articleList[i].partition == 3) {
                    $('.partitionsTag').html('大学生活');
                }
                //显示时间 时间毫秒数转换为年月日格式
                var date = dateFormat(res.articleList[i].createTime);
                $('#asCreatTime').html(date);
                //修改id
                $('#asUserId').html(res.articleList[i].authorId);
                if (res.articleList[i].label1 != undefined) {
                    $('#asmainTag1').html(res.articleList[i].label1);
                    $('#asmainTag1').show();
                }
                if (res.articleList[i].label2 != undefined) {
                    $('#asmainTag2').html(res.articleList[i].label2);
                    $('#asmainTag2').show();
                }
                if (res.articleList[i].label3 != undefined) {
                    $('#asmainTag3').html(res.articleList[i].label3);
                    $('#asmainTag3').show();
                }
                if (res.articleList[i].label4 != undefined) {
                    $('#asmainTag4').html(res.articleList[i].label4);
                    $('#asmainTag4').show();
                }
                if (res.articleList[i].label5 != undefined) {
                    $('#asmainTag5').html(res.articleList[i].label5);
                    $('#asmainTag5').show();
                }
                //显示文章内容
                $('#asmainArtical').html(res.articleList[i].content);
                //显示标题
                $('#asmainTitle').html(res.articleList[i].title);
                //显示绿色标签
            }
        })
    }

})
