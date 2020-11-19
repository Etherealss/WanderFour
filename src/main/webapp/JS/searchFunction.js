// ——————————————————————————— 点击框内元素 ———————————————————————————————————
$(".topmargin_searchBox").on({
    click: function()
    {
        $(".topmargin_searchBox").css({
            borderColor: "#36b3a8"
        });
        clickSearchBoxHidden();
    }
});

//—————————————————— 点击其他任意区域，收起下拉栏 ————————————————————
function clickSearchBoxHidden()
{
    $("body").stop().on({
        click:function(e)
        {
            var target = $(e.target);
            //点击框内的可用，其余不能
            if(!target.is(".topmargin_searchBox"))
            {
                $(".topmargin_searchBox").css({
                    borderColor: "#e0e0e0",
                    borderRadius: "20px",
                    borderBottom: "2px solid #e0e0e0"
                });
                
                //下拉框内的内容全部清空
                $("#ComboBox").find("a").remove();
                //下拉框收起
                $("#ComboBox").hide();

                //阻止事件冒泡
                $(".topmargin_searchBox").stop().on({
                    click: function(event)
                    {
                        event.stopPropagation();
                    }
                });
            }
        }
    });
}
clickSearchBoxHidden();

// —————————————————————————————— 获取搜索详细内容的AJAX ——————————————————————————————————
/**
 * @param {*} str 传递过来的关键字
 * @param {*} idNum 文章对应的编号
 */
function getSearchDetails(str,idNum)
{
    $.ajax({
        type:'get',
        url:'http://192.168.43.236:8080/WritingSearchServlet?wd='+str+'&currentPage=1',
        data:{},
        dataType: 'json',
        contentType: 'application/json',
        success:function(res){
            console.log(res);
            var type = res.pageDate.list[idNum].writingType;    //获取对应类型
            var id = res.pageDate.list[idNum].writingId;     //获取文章编号
            
            
            //搜索成功后，通过匹配的数字，来获取第一个id，跳转过去
            skipToType(type,id);
        },
        error:function(){
            console.log("获取搜索详细信息失败");
        }
    })
}

// 跳转到对应的问贴或文字
function skipToType(type,id)
{
    if(type == 'posts')
    {//跳转到帖子
        window.location.href = 'http://192.168.43.236:8080/answerPosts.html?'+type+'=' + id;
        // window.open('http://192.168.43.236:8080/answerPosts.html?'+type+'=' + id); 
    }
    else if(type == 'article')
    {//跳转到文章
        window.location.href = 'http://192.168.43.236:8080/articleShow.html?'+type+'=' + id;
        // window.open('http://192.168.43.236:8080/articleShow.html?'+type+'=' + id); 
    }
}




// 初始时，下拉框的所有内容隐藏
$("#ComboBox").children().hide();

//每1s搜索一次，防止频繁发送jax请求
var appendListData = debounce(getInputTheTips,500);
// 搜索框输入时执行该事件
$("#topmarginSearch")[0].oninput = appendListData;

// —————————————————————————————— 获取搜索提示词的AJAX ——————————————————————————————————
function getSearchTipsData(str)
{
    $.ajax({
        type:'get',
        url:'http://192.168.43.236:8080/WritingSearchTipServlet?wd='+str+'&currentPage=1',
        data:{},
        dataType: 'json',
        contentType: 'application/json',
        success:function(res){
            console.log(res);
            //隔0.5s获取一次数据
            getListData(res.tips);
            
            //点击搜索出来的提示词，跳转对应的页面
            $("#ComboBox").find("a").on({
                click: function()
                {
                    var idNum = $(this).attr("tipsNum");
                    $(this).css({
                        background: "#e7e7e7",
                        color: "#36b3a8"
                    }).siblings().css({background: "#f7f7f7"});
                    getSearchDetails(str,idNum);
                    console.log(idNum);
                }
            });

            //按方向盘的下键，默认到第一个，且搜索框内显示第一个的文本
            onkeydown = function()
            {
                if(event.keyCode == 40)
                {
                    $("#ComboBox").find("a").eq(0).css({background: "#e7e7e7"});
                    $("#topmarginSearch").val($("#ComboBox").find("a").eq(0).text());
                }
            }
            //点击搜索，直接跳转到这篇文章
            $("#topmarginSearch+button").on({
                click: function()
                {
                    getSearchDetails(str,0);   
                }
            });

        },
        error:function(){
            console.log("获取搜索提示词失败");
        }
    })
}

// function keyboardEvents()
// {
    
// }
//———————————————————— 方向键盘事件：上下键盘切换 ——————————————————————————
// onkeydown = function()
// {
//     var keyboardNum = 0;
//     //键盘中的下键
//     if(event.keyCode == 40)
//     {
//         if(keyboardNum == 0)
//         {
//             $("#ComboBox").find("a").eq(0).css({
//                 background: "#e0e0e0"
//             });
//         }
//         else{
//             $("#ComboBox").find("a").eq(keyboardNum+1).css({
//                 background: "#e0e0e0"
//             });
//         }
//     }

//     //键盘上键

//     if(keyboardNum == 0)
//     {
//         keyboardNum
//     }
// }

//获取文本框的输入，并把该输入作为参数传递给后端
function getInputTheTips()
{
    //输入不为空，为空显示默认值
    console.log(typeof(this.value.length));
    if(this.value != null && this.value != undefined && this.value.length != 0)
    {
        //重新输入后，下拉框内的内容全部清空
        $("#ComboBox").find("a").remove();
        $("#ComboBox").children().show();
        // 将搜索框输入的语句传递给后端，再通过返回的关键词来匹配
        getSearchTipsData(encodeURIComponent(this.value));
    }
    else{
        //下拉框内的内容全部清空
        $("#ComboBox").find("a").remove();
        $("#ComboBox").hide();
        //外框样式
        $(".topmargin_searchBox").css({
            borderRadius: "20px",
            borderBottom: "2px solid #36b3a8"
        });
    }

    //获取条数不为空，下拉显示
    if($("#ComboBox").find("a").length != 0)
    {
        $("#ComboBox").slideDown(500);
    }
}


// ——————————————————————— 创建下拉框ComboBox内的搜索条件<a> ———————————————————————————
// 获取列表的数据
/**
 * @param {*} tips 搜索框提示词
 */
function getListData(tips)
{
    //搜索框样式
    $("#ComboBox").children().show();
    $("#ComboBox").css({
        border: "2px solid #36b3a8",
        borderTop: "none",
        paddingBottom: "10px"
    });

    //将获取的数据渲染出来
    for(var i = 0;i < tips.length;i++)
    {
        var searchTerm = $("<a tipsNum='"+i+"'>"+tips[i]+"</a>");
        $("#ComboBox").append(searchTerm);  //添加到末尾
        $("#ComboBox").slideDown(500);
    }

    //外框样式
    $(".topmargin_searchBox").css({
        borderColor: "#36b3a8",
        borderRadius: "20px 20px 0 0",
        borderBottom: "none"
    });
}





