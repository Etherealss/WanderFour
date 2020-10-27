//个人中心悬浮
$(".topmargin_personal_center").hover(function () {
    $(this).find("ul").stop().fadeToggle();
});

//——————————————————————— 获取textarea的字数 —————————————————————————
// $("#EA_textNum").text($("#editPostsContentVal").val().length);
$("#editPostsContentVal").on("keyup", function () {
    $("#EA_textNum").text($(this).val().length);
});


//—————————————————————— 切换分区，更改二级分区 ——————————————————————————
$(".editPosts_secondaryPartition").eq(1).hide();
$(".editPosts_secondaryPartition").eq(2).hide();

//通过切换radio的checked，来显示不同的二级分区
$("input[name='theBigPartition']").on("change", function () {
    if ($(this).prop("checked")) {
        $(".editPosts_secondaryPartition").eq($(this).attr("num")).siblings().hide();
        $(".editPosts_secondaryPartition").eq($(this).attr("num")).show();

        //切换大分区，把二级分区的选取清空
        $(".editPosts_secondaryPartition").eq($(this).attr("num")).siblings().find("ol").find("li").removeAttr("id");
        $(".editPosts_secondaryPartition").eq($(this).attr("num")).siblings().find("ol").find("li").css({
            color: "#9a9a9a",
            background: "#fff"
        });
    }
});

// —————————————— 二级分区Part获取属性 ————————————————————
//只一次（类似单选按钮）
$(".editPosts_secondaryPartition ol > li").on("click", function () {
    //获取id和换色
    $(this).attr("id", "secondary");
    $(this).css({
        color: "#fff",
        background: "#AEE1D8"
    });
    //同级兄弟类id和换色
    $(this).siblings().removeAttr("id");
    $(this).siblings().css({
        color: "#9a9a9a",
        background: "#fff"
    });
    //上一级的表兄弟类id和换色
    $(this).parent().siblings().find("li").removeAttr("id");
    $(this).parent().siblings().find("li").css({
        color: "#9a9a9a",
        background: "#fff"
    });
    //上上级的子类的子类id和换色
    $(this).parent().parent().siblings().find("li").removeAttr("id");
    $(this).parent().parent().siblings().find("li").css({
        color: "#9a9a9a",
        background: "#fff"
    });

    //二级分区里的小标题
    $(this).parent().find("span").css({color: "#000"});
    console.log($("#secondary").attr("backstage-data"));
});


// ——————————— 创建自定义新标签：点击按钮出现输入框，在输入框内可输入自定义标签 —————————————————————————
// 点击出现输入框
$("#EP_displayBtn").on({
    click: function () {
        $("#EP_addTagsBox").css({
            width: 165,
            transition: "all ease-in-out .5s"
        });
        $(this).css({
            zIndex: -1
        });
    }
});

//———— 点击除了.editPosts_customTagsBox之外的其他区域执行 ————————————
$("body").on({
    click: function (event) {
        if (!$(event.target).closest(".editPosts_customTagsBox").length) {
            $("#EP_addTagsBox").css({
                width: 36,
                transition: "all ease-in-out .5s"
            });

            $("#EP_displayBtn").css({
                zIndex: 0
            });

            $("#editTags").html("");
        }
    }
});


//—————————————— 限制可编辑div输入的字符长度 ———————————————
$("#editTags").on({
    keyup: function () {
        // var re = /^[0-9a-zA-Z]*$/g;
        // if(re.text($(this).html())) //判断输入是否为字母
        // {

        // }else 
        if ($(this).html().length > 10) {
            alert("长度超过了限制");
            $(this).html("");   //超过长度后清空，重新输入
        }

    },
    keydown: function (event) {//按下Enter键盘后禁止换行
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    }
});


//—————————————————————— 动态创建 ——————————————————————
//可编辑的div获取内容要用html()，input获取内容要用val()
$("#EP_addBtn").on({
    click: function () {
        if ($("#editTags").html() == "" || $("#editTags").html() == undefined) {
            alert("请在输入框内输入内容");
        } else {
            var li = $("<li></li>");
            li.html("<p id='label'>" + $("#editTags").html() + "</p> <a class='EA_delete'>×</a>");
            $("#editPosts_tagsList").append(li); //将动态创建的<li>添加到<ul>里
            $("#editTags").html(""); //点击添加后清空搜索框

            // 点击删除
            $(".EA_delete").on({
                click: function () {
                    $(this).parent().remove();
                    //限制自定义标签的数量，当删除后少于5个，再次出现
                    if ($("#editPosts_tagsList li").length <= 4) {
                        // $("#EA_addTagsBox").show();
                        $("#EP_addTagsBox").animate({
                            opacity: 1
                        }, 100);
                    }
                }
            });

            //限制自定义标签的数量
            if ($("#editPosts_tagsList li").length > 4) {
                $("#EP_addTagsBox").animate({
                    opacity: 0
                }, 100);
            }
        }
    }
});


// ——————————————————————— AJAX相关Part ———————————————————————————————
//获取label的值（给label后添加i下标）
function getLabelNum() {
    var label = new Array();
    for (var i = 0, j = 1; i < $("#editPosts_tagsList li").length; i++, j++) {
        $("#editPosts_tagsList li").eq(i).find("p").attr("id", "label" + j);
        label[j] = $("#label" + j).html();
    }
    return label;
}

//获取需要的参数的值
function obtainVal() {
    var editPostsTitleVal = $("#editPostsTitleVal").val();  //文章标题
    var editPostsContentVal = txtLineBreak($("#editPostsContentVal")).val(); //文章内容（讲文本内容里的换行符替换掉）
    var paritition = $("input[name='theBigPartition']:checked").val();  //获取分区
    var category = $("#secondary").attr("backstage-data"); //文章二级分类
    // var authorId = "123456@qq.com";
    var authorId = "1";

    //通过返回一个对象的形式来使用
    var massage = {
        editPostsTitleVal: editPostsTitleVal,
        editPostsContentVal: editPostsContentVal,
        paritition: paritition,
        category: category,
        authorId: authorId
    }

    return massage;
}

// console.log($("input[name='theBigPartition']:checked").val());

//将textarea中的换行“\n”替换成“<br/>”
function txtLineBreak(editPostsContent) {
    var txt = editPostsContent.val();
    txt = txt.replace(/\n/g, "<br/>");   //替换
    editPostsContent.val(txt);

    return editPostsContent;
}

// ————————————————————————————————————————————————————————————————————————————————————————
//Ajax传递参数
function submitVal(partition, category, authorId, editPostsTitleVal, editPostsContentVal, label) {
    $.ajax({
        type: 'POST',
        url: '/WritingServlet',
        data: {
            type: "posts",
            partition: partition,
            category: category,
            authorId: authorId,
            title: editPostsTitleVal,
            content: editPostsContentVal,
            label1: label[1],
            label2: label[2],
            label3: label[3],
            label4: label[4],
            label5: label[5]
        },
        success: function (res) {//成功的回调函数

            console.log("res = " + res);
        },
        error: function () {//XX失败
            console.log("ArticlePost ERROR");
        }
    });
}


function checkEmpty() {
    if ($("#editPostsTitleVal").val() == "" || $("#editPostsTitleVal").val() == undefined) {
        alert("请输入文章标题");
    } else if ($("#editPostsContentVal").html() == "" || $("#editPostsContentVal").html() == undefined) {
        alert("请输入文章内容");
    } else if ($("#secondary").html() == "" || $("#secondary").html() == undefined) {
        alert("请选择文章二级分区");
    }
}

//点击提交信息
$("#editPosts_save").on({
    click: function () {
        // console.log(obtainVal().editPostsTitleVal);
        // console.log(obtainVal().editPostsContentVal);
        // console.log(obtainVal().paritition);
        // console.log(obtainVal().category);
        // console.log(obtainVal().authorId);
        // console.log(getLabelNum());

        // $("#textTxt").html(obtainVal().editPostsContentVal);
        //判空
        checkEmpty();
        // 传递参数给$.AJAX()
        submitVal(obtainVal().paritition, obtainVal().category, obtainVal().authorId,
            obtainVal().editPostsTitleVal, obtainVal().editPostsContentVal, getLabelNum());
    }
});


//——————————————————————————— 向后端请求分区编号对应的数据 ———————————————————————————————————
function getBackstageData(partitionNumber) {
    $.ajax({
        type: 'get',
        url: '/CategoryServlet?partition=' + partitionNumber,
        data: {},
        success: function (res) {
            var res = JSON.parse(res); //把传递数据的JSON格式转换为对象存储来用
            compareData(res.category);   //传递到页面进行配对比较
        },
        error: function () {
            console.log("数据传送失败");
        }
    })
}

// ——————————————— 传入获取大分区 —————————————————————————
function getPartitionNum() {
    var partitionNumber = $("input[name='theBigPartition']:checked").attr("num");
    getBackstageData(Number(partitionNumber + 1));
}

getPartitionNum();


// —————————————————— 传出匹配二级分区（双层循环判断） ——————————————————————
function compareData(categoryNum) {
    console.log(categoryNum)
    // $(".secondary").eq(e-1).text() 文章的二级分区
    for (i in categoryNum) {
        for (j in categoryNum) {
            if ($(".secondary").eq(i - 1).text() == categoryNum[j]) {
                // console.log(categoryNum[e]);
                $(".secondary").eq(i - 1).attr("backstage-data", j); //文章二级分类
            }
        }
    }
}

// —————————————————— 测试页面加载后是否把对应值赋值过去 ————————————————————
// var categoryNum = {
//     3: "语文",
//     9: "数学",
//     1: "英语",
//     5: "物理",
//     6: "化学",
//     4: "生物",
//     7: "历史",
//     2: "政治",
//     8: "地理"
// };

// compareData(categoryNum);
// for(var i = 0;i < $(".secondary").length;i++)
// {
//    //若输出顺序与上面categoryNum的键数值顺序一致即为正确
//     console.log($(".secondary").eq(i).attr("backstage-data"));
// }
