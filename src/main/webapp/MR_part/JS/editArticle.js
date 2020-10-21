//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});

// —————————————— 二级分区Part获取属性 ————————————————————
// .editArticle_secondaryPartition ol > li
$(".editArticle_secondaryPartition ol > li").one({
    click: function(){
        $(this).attr("id","secondary");
        $(this).css({
            color: "#fff",
            background: "#AEE1D8"
        });
    }
});

// ——————————— 创建自定义新标签：点击按钮出现输入框，在输入框内可输入自定义标签 —————————————————————————
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
                }
            });

            // $("#editArticle_tagsList li").length;     //获取ul下li的数量   
               
        }
         
    }
    // mouseover: function(){
    //     $("#EA_addTagsBox").css({
    //         width: 165,
    //         transition: "all ease-in-out .5s"
    //     });
    // },
    // mouseleave: function(){
    //     $("#EA_addTagsBox").css({
    //         width: 36,
    //         transition: "all ease-in-out .5s"
    //     });
    // }
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
    var articleTitleVal = $("#articleTitleVal").val();  //文章标题
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

//Ajax传递参数
function submitVal(partition,category,authorId,articleTitleVal,articleContentVal,label)
{
   $.ajax({
       type:'POST',
       url:'http://192.168.137.138:8080/WritingServlet',
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


//点击提交信息
$("#editArticle_save").on({
    click: function(){
        // console.log(obtainVal().articleTitleVal);
        // console.log(obtainVal().articleContentVal);
        // console.log(obtainVal().paritition);
        // console.log(obtainVal().category);
        // console.log(obtainVal().authorId);
        // console.log(getLabelNum());
        
        // 传递参数给$.AJAX()
        submitVal(obtainVal().paritition,obtainVal().category,obtainVal().authorId,
        obtainVal().articleTitleVal,obtainVal().articleContentVal,getLabelNum());
    }
});


