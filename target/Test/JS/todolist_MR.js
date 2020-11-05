// 初始状态
$("#todolist_hidden_MR").hide();
$("#todolist_show_MR").show();
$("#todolist_show_MR").parent().animate({
    right: -350
});

//点击隐藏和点击出现
$("#todolist_show_MR").on({
    click: function () {
        $(this).parent().animate({
            right: 0
        }, 800);

        $("#todolist_hidden_MR").show();
        $(this).hide();
    }
});

$("#todolist_hidden_MR").on({
    click: function () {
        $(this).parent().animate({
            right: -350
        }, 800);

        $("#todolist_show_MR").show();
        $(this).hide();
    }
});

//————————————————— 获取数据 ———————————————————————
//按下回车，把完整数据存储到Local Storage
//存储格式：var todolist = [{title:"XXX", done:false}]
load(); //页面初始化，页面一加载，就把本地存储的数据渲染在页面上
$("#todolistInput_MR").on({
    keydown: function (event) {
        if (event.keyCode == 13) //按下回车
        {
            if ($(this).val() == "") //输入文本为空
            {
                alert("请输入文本");
            }
            else {
                var local_MR = getData();   //创建local_MR数组
                //往数组里更新存储数据，target：设定目标，done：完成状态
                local_MR.push({
                    target: $(this).val(),
                    done: false
                });

                saveData(local_MR); //把数组local存储给本地
                load();     //读取本地数据渲染到页面中
                $(this).val("");    //当按下回车，清除输入框内的内容
            }
        }
    }
});


//————————————— 读取本地存储数据 ——————————————————
//读取Local Storage存放的数据（将字符串转换为对象格式）
function getData() {
    var data = localStorage.getItem("todolist");
    if (data != null)    //确保有数据，才获取
    {
        return JSON.parse(data);    //将本地里字符串格式转换回对象（外边用的是对象格式）
    }
    else {   //本地存储为空，直接返回一个空数组
        return [];
    }
}

//————————————— 保存本地存储数据 ——————————————————
//将对象转换为字符串，即以JSON的格式存储进本地
function saveData(data) {
    localStorage.setItem("todolist", JSON.stringify(data));
}

//————————————— 渲染加载本地存储数据 ——————————————————
function load() {
    var data = getData();   //先读取本地存储数据
    //为了防止每次按下回车后，页面自动渲染与车渲染叠加渲染两次，故在加载时，先清空原有内容
    $("#todolist_MR,#donelist_MR").empty();

    //显示的统计个数
    var todoCount = 0;  //正在进行的个数
    var doneCount = 0;  //已经完成的个数

    //遍历这个数组
    $.each(data, function (i, n)   //i索引号，n为data数组里对应的数据
    {
        if (n.done == false) //未完成的
        {
            var olStr = "<li>" +
                "<input class='check_MR' type = 'checkbox'/>" +
                "<p>" + n.target + "</p>" +
                "<a class='delete_MR' href='Javascript:;' number='" + i + "'/></a>" +
                "</li>";
            $("#todolist_MR").prepend(olStr);
            todoCount++;
        }
        else {   //已完成的
            var ulStr = "<li>" +
                "<input class='check_MR' type = 'checkbox' checked='checked'/>" +
                "<p>" + n.target + "</p>" +
                "<a class='delete_MR' href='Javascript:;' number='" + i + "'/></a>" +
                "</li>";
            $("#donelist_MR").prepend(ulStr);
            doneCount++;
        }
    });

    //更改页面显示的个数
    $("#todocount_MR").text(todoCount);
    $("#donecount_MR").text(doneCount);

    if (todoCount + doneCount >= 8) {
        $("#todolistInput_MR").hide().slideUp(200);
    }
    else {
        $("#todolistInput_MR").show().slideDown(200);
    }
}

//————————————— 实现本地存储的删除操作 ——————————————————
function deleteData(list) {
    list.on("click", ".delete_MR", function () {
        var data = getData();   //先获取本地数据
        //获取索引号
        var index = $(this).attr("number");
        data.splice(index, 1);   //删除索引号所在本身的数据
        saveData(data); //将删除后的数据重新存储，迭代更新本地的存储数据
        load(); //将数据重新渲染到页面
    });
}

deleteData($("#todolist_MR"));  //正在进行的
deleteData($("#donelist_MR"));  //已经完成的

//————————————— 切换正在进行和已经完成的选项 ——————————————————
function switchList(list) {
    list.on("click", ".check_MR", function () {
        var data = getData();   //先获取本地存储的数据
        //修改数据（点击复选框，获取与它同级的兄弟<a>的自定义属性）
        var index = $(this).siblings("a").attr("number");
        //将当前复选框中是否选中这一状态与存储的data的done属性的false/true绑定
        data[index].done = $(this).prop("checked");
        saveData(data); //将删除后的数据重新存储，迭代更新本地的存储数据
        load(); //将数据重新渲染到页面
    });
}

switchList($("#todolist_MR"));  //正在进行的
switchList($("#donelist_MR"));  //已经完成的