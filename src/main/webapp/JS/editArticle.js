//个人中心悬浮
$(".topmargin_personal_center").hover(function(){
    $(this).find("ul").stop().fadeToggle();
});


//———————————————————————————— 文本编辑问题 ——————————————————————————————————————
//——————————————————— 测试键盘事件的keyCode码 —————————————————————
function textareaEnter(textareaBox)
{
    textareaBox.on({
    keydown: function(event)
    {
        // console.log(event.keyCode);  //输出的值即为该按键的keyCode
        if(event.keyCode == 13){
            event.preventDefault();
            //重写Enter事件，按下Enter键盘后换行
            $(this).html($(this).html()+"<br/><br/>");
            // $(this).append("<br/><br/>");
            //将光标置于内容的最后位置
            setFocus($(this));
        }
    },
    // focus: function(){
    //     $(this).css({
    //         boxShadow: "1px 1px 5px rgba(0,0,0,0.25)",
    //         transition: "all ease-in-out .3s"
    //     });
    // },
    // blur: function(){
    //     $(this).css({
    //         boxShadow: "none",
    //         transition: "all ease-in-out .3s"
    //     });
    // }
});
}

//———————————————————— 设置光标始终在文本最后 ————————————————————
function setFocus(object){
    object = object[0]; // jquery 对象转dom对象  
    object.focus();
    var range = document.createRange();
    range.selectNodeContents(object);
    range.collapse(false);
    var sel = window.getSelection();

    sel.removeAllRanges();
    sel.addRange(range);
}; 

//————————————————————————————————— 插入图片 —————————————————————————————————————
//图片转Base64码再存储
function changeImgSrc(inputFile,index)
{
    inputFile.on({
        change: function()
        {
            //点击后在textareaBox里增添一个img标签
            $("#articleContentVal").append("<img id='articleUpImage"+index+"' src=''/><br/><br/>");
            
            var filePath = $(this).val();	//读取图片的本地存储路径
            
            var fr = new FileReader();	//创建FileReader()对象
            var imgObject = this.files[0];	//获取图片（FileReader为JavaScript的类，故要转换为DOM对象）

            fr.readAsDataURL(imgObject);	//将图片读取为DaraURL
            var obj = $("#articleUpImage"+index)[0];		//获取要显示图片的<img>标签

            //分割地址（常见图片格式：jpg、png）
            if(filePath.indexOf("jpg") != -1 || filePath.indexOf("JPG") != -1 || filePath.indexOf("PNG") != -1 || filePath.indexOf("png") != -1) 
            {
                // var arr = filePath.split('\\');
                // var fileName = arr[arr.length - 1];
            
                $("#articleUpImage"+index).css({width: 400});   //限制下图片显示的尺寸
                $("#articleUpImage"+index).show(); //图片显示
                index++;    //图片下标增加（为了可以上传多张图片）
                fr.onload = function(){
                    obj.src = this.result;	//<img>标签的src属性获取图片的转码
                    imgGetSrc(obj.src);	//直接在fr.onload里当作参数传送到函数里，函数写在和inputFile同一层次
                } 
            }
            else{
                return false;
            }
        }
    });

    //从fr.onload里传递过来的参数，放入图片中
    function imgGetSrc(img)
    {
        $("#articleUpImage"+index).attr("src",img);
    }
}

//换行以及光标永远置于编辑的最后位置
textareaEnter($("#articleContentVal"));
//点击上传插入不同数量的图片
changeImgSrc($("#imgFileBtn"),0); 

//——————————————————————— 获取textarea的字数 —————————————————————————
$("#EA_textNum").text($("#articleContentVal").html().length);
$("#articleContentVal").on("keyup",function(){
    $("#EA_textNum").text($(this).html().length);
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
    console.log($("#secondary").attr("backstage-data"));
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

//———— 点击除了.editArticle_customTagsBox之外的其他区域执行 ————————————
$("body").on({
    click: function(event){
        if(!$(event.target).closest(".editArticle_customTagsBox").length)
        {
            $("#EA_addTagsBox").css({
            width: 36,
            transition: "all ease-in-out .5s"
        });

            $("#EA_displayBtn").css({
                zIndex: 0
            });

            $("#editTags").html("");
        }
    }
});


//—————————————— 限制可编辑div输入的字符长度 ———————————————
$("#editTags").on({
    keyup: function()
    {
        // var re = /^[0-9a-zA-Z]*$/g;
        // if(re.text($(this).html())) //判断输入是否为字母
        // {
            
        // }else 
        if($(this).html().length > 10)
        {
            alert("长度超过了限制");
            $(this).html("");   //超过长度后清空，重新输入
        }
        
    },
    keydown: function(event)
    {//按下Enter键盘后禁止换行
        if(event.keyCode == 13)
        {
            event.preventDefault();
        }
    }
});


//—————————————————————— 动态创建 ——————————————————————
//可编辑的div获取内容要用html()，input获取内容要用val()
$("#EA_addBtn").on({
    click: function()
    {
        if($("#editTags").html() == "" || $("#editTags").html() == undefined)
        {
            alert("请在输入框内输入内容");
        }
        else{
            var li = $("<li></li>");
            li.html("<p id='label'>"+$("#editTags").html()+"</p> <a class='EA_delete'>×</a>");
            $("#editArticle_tagsList").append(li); //将动态创建的<li>添加到<ul>里
            $("#editTags").html(""); //点击添加后清空搜索框
            
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
    var articleContentVal = $("#articleContentVal").html(); //文章内容
    var paritition = $("input[name='theBigPartition']:checked").val();  //获取分区
    var category = $("#secondary").attr("backstage-data"); //文章二级分类
    // var authorId = "123456@qq.com";
    var authorId = "2";
     
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

// console.log($("input[name='theBigPartition']:checked").val());

//将textarea中的换行“\n”替换成“<br/>”
// function txtLineBreak(articleContent)
// {
//     var txt = articleContent.val();
//     txt = txt.replace(/\n/g,"<br/>");   //替换
//     articleContent.val(txt);

//     return articleContent;
// } 

// ————————————————————————————————————————————————————————————————————————————————————————
//Ajax传递参数
function submitVal(partition,category,authorId,articleTitleVal,articleContentVal,label)
{
   $.ajax({
       type:'POST',
       url:'/WritingServlet',
       data:{
           type: "article",
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
    else if($("#articleContentVal").html() == "" || $("#articleContentVal").html() == undefined)
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
        // console.log(obtainVal().articleContentVal);
        // console.log(obtainVal().paritition);
        // console.log(obtainVal().category);
        // console.log(obtainVal().authorId);
        // console.log(getLabelNum());

        // $("#textTxt").html(obtainVal().articleContentVal);
        //判空
        checkEmpty();
        // 传递参数给$.AJAX()
        submitVal(obtainVal().paritition,obtainVal().category,obtainVal().authorId,
        obtainVal().articleTitleVal,obtainVal().articleContentVal,getLabelNum());
    }
});


//——————————————————————————— 向后端请求分区编号对应的数据 ———————————————————————————————————
function getBackstageData(partitionNumber)
{
    $.ajax({
        type:'get',
        url:'/CategoryServlet?partition='+partitionNumber,
        data:{},
        success:function(res)
        {
            console.log(res);
            var categoryNum = JSON.parse(res.category); //把传递数据的JSON格式转换为对象存储来用
            compareData(categoryNum);   //传递到页面进行配对比较
        },
        error:function()
        {
            console.log("数据传送失败");
        }
    })
}

// ——————————————— 传入获取大分区 —————————————————————————
function getPartitionNum()
{
    var partitionNumber = $("input[name='theBigPartition']:checked").attr("num");
    getBackstageData(Number(partitionNumber+1));
}

getPartitionNum();


// —————————————————— 传出匹配二级分区（双层循环判断） ——————————————————————
function compareData(categoryNum)
{
    // $(".secondary").eq(e-1).text() 文章的二级分区
    for(i in categoryNum)
    {
        for(j in categoryNum)
        {
            if($(".secondary").eq(i-1).text() == categoryNum[j])
            {
                // console.log(categoryNum[e]);
                $(".secondary").eq(i-1).attr("backstage-data",j); //文章二级分类
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

