//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});


//——————————————————————— 获取textarea的字数 —————————————————————————
$("#EA_textNum").text($("textarea").val().length);
$("textarea").on("keyup",function(){
    $("#EA_textNum").text($(this).val().length);
});

//—————————————————————— 切换分区，更改二级分区 ——————————————————————————
$(".editArticle_secondaryPartition").eq(1).hide();
$(".editArticle_secondaryPartition").eq(2).hide();

//通过切换radio的checked，来显示不同的二级分区
$("input[name='theBigPartition']").on("change",function(){ 
    if($(this).prop("checked"))
    {
        $(".editArticle_secondaryPartition").eq($(this).attr("num")).siblings().hide();
        $(".editArticle_secondaryPartition").eq($(this).attr("num")).show();

        //切换大分区，把二级分区的选取清空
        $(".editArticle_secondaryPartition").eq($(this).attr("num")).siblings().find("ol").find("li").removeAttr("id");
        $(".editArticle_secondaryPartition").eq($(this).attr("num")).siblings().find("ol").find("li").css({
            color: "#9a9a9a",
            background: "#fff"
        });
    }
});

// —————————————— 二级分区Part获取属性 ————————————————————
// $(".editArticle_secondaryPartition").find("ol").find("li").eq(0).attr("id","secondary");
// $(".editArticle_secondaryPartition").find("ol").find("li").eq(0).css({
//     color: "#fff",
//     background: "#AEE1D8"
// });

//只一次（类似单选按钮）
$(".editArticle_secondaryPartition ol > li").on("click",function()
{
    //获取id和换色
    $(this).attr("id","secondary");
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
});


// ——————————— 创建自定义新标签：点击按钮出现输入框，在输入框内可输入自定义标签 —————————————————————————
// 点击出现输入框
$("#EA_displayBtn").on({
    click: function(){
        $("#EA_addTagsBox").css({
            width: 165,
            transition: "all ease-in-out .5s" 
        });
        $(this).css({
            zIndex: -1
        });
    }
});

//动态创建
$("#EA_addBtn").on({
    click: function(){
        if($("#editTags").val() == "" || $("#editTags").val() == undefined)
        {
            alert("请在输入框内输入内容");
        }
        else{
            var li = $("<li></li>");
            li.html("<p id='label'>"+$("#editTags").val()+"</p> <a class='EA_delete'>×</a>");
            $("#editArticle_tagsList").append(li); //将动态创建的<li>添加到<ul>里
            $("#editTags").val(""); //点击添加后清空搜索框
            
            // 点击删除
            $(".EA_delete").on({
                click: function(){
                    $(this).parent().remove();
                    //限制自定义标签的数量，当删除后少于5个，再次出现
                    if($("#editArticle_tagsList li").length <= 4)
                    {
                        // $("#EA_addTagsBox").show();
                        $("#EA_addTagsBox").animate({
                            opacity: 1
                        },100);
                    }
                }   
            });

            // $("#editArticle_tagsList li").length;     //获取ul下li的数量   
            //限制自定义标签的数量
            if($("#editArticle_tagsList li").length > 4)
            {
                // $("#EA_addTagsBox").hide();
                $("#EA_addTagsBox").animate({
                    opacity: 0
                },100);
            }
        }   
    }
});

$(".editArticle_addCustomTags").on({
    mouseleave: function(){
        $("#EA_addTagsBox").css({
            width: 36,
            transition: "all ease-in-out .5s"
        });

        $("#EA_displayBtn").css({
            zIndex: 0
        });
    }
});

// ——————————————————————— AJAX相关Part ———————————————————————————————
//获取label的值（给label后添加i下标）
function getLabelNum()
{
    var label = new Array();
    for(var i = 0,j = 1;i < $("#editArticle_tagsList li").length;i++,j++)
    {
        $("#editArticle_tagsList li").eq(i).find("p").attr("id","label"+j);
        label[j] = $("#label"+j).html();
    }
    return label;
}

//获取需要的参数的值
function obtainVal()
{
    var articleTitleVal = txtLineBreak($("#articleTitleVal")).val();  //文章标题
    var articleContentVal = $("#articleContentVal").val(); //文章内容
    var paritition = $("input[name='theBigPartition']:checked").val();  //获取分区
    var category = $("#secondary").html(); //文章二级分类
    var authorId = "123456@qq.com";
     
        //通过返回一个对象的形式来使用
    var massage = {
        articleTitleVal: articleTitleVal,
        articleContentVal: articleContentVal,
        paritition: paritition,
        category: category,
        authorId: authorId
    } 

    return massage;
}

//将textarea中的换行“\n”替换成“<br/>”
function txtLineBreak(articleContent)
{
    var txt = articleContent.val();
    txt = txt.replace(/\n/g,"<br/>");   //替换
    articleContent.val(txt);

    return articleContent;
} 

//使textarea支持Tab键


//Ajax传递参数
function submitVal(partition,category,authorId,articleTitleVal,articleContentVal,label)
{
   $.ajax({
       type:'POST',
       url:'/WritingServlet',
       data:{
           method: "writingCRUD", //文章的操作，CRUD即增删改查，具体百度
           type: "article",
           action: "add", //增add删delete改post查get，通俗易懂
           partition: partition,
           category: category,
           authorId: authorId, 
           title: articleTitleVal,
           content: articleContentVal,
           label1: label[1], 
           label2: label[2],
           label3: label[3],
           label4: label[4],
           label5: label[5]
       },
       dataType: 'json',
       success: function(res){//成功的回调函数
           console.log(res);
       },
       error: function(){//XX失败
           console.log("ArticlePost ERROR");
       }
   });
}


function checkEmpty(){
    if($("#articleTitleVal").val() == "" || $("#articleTitleVal").val() == undefined)
    {
        alert("请输入文章标题");
    }
    else if($("#articleContentVal").val() == "" || $("#articleContentVal").val() == undefined)
    {
        alert("请输入文章内容");
    }
    else if($("#secondary").html() == "" || $("#secondary").html() == undefined)
    {
        alert("请选择文章二级分区");
    }
}

//点击提交信息
$("#editArticle_save").on({
    click: function(){
        // console.log(obtainVal().articleTitleVal);
        console.log(obtainVal().articleContentVal);
        // console.log(obtainVal().paritition);
        // console.log(obtainVal().category);
        // console.log(obtainVal().authorId);
        // console.log(getLabelNum());

        //判空
        checkEmpty();
        // 传递参数给$.AJAX()
        submitVal(obtainVal().paritition,obtainVal().category,obtainVal().authorId,
        obtainVal().articleTitleVal,obtainVal().articleContentVal,getLabelNum());
    }
});


