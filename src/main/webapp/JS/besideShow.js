/**
 * @ignore  =====================================================================================
 * @fileoverview 侧栏信息显示
 * @author  寒洲
 * @date 2020/10/29
 * @ignore  =====================================================================================
 */
/**
 * 获取侧栏的 最新和最热 文章/问贴
 * @param partition
 * @param type
 */
function getWritingList(partition, type) {
    $.ajax({
        type: 'GET',
        url: '/WritingBesideServlet?type=' + type + '&partition=' + partition,
        dataType: 'json',
        /**
         * @param res 文章/问贴
         * @param res.new 最新文章/问贴
         * @param res.hot 最热文章/问贴（点赞数最高）
         * @param res.new.title 标题
         * @param res.new.id 文章/问贴id
         * @param res.new.title
         * @param res.hot.id
         */
        success: function (res) {
            console.log(res);
            if (type == "article"){
                setArticleList(res);
            }
        }
    })
}

function setArticleList(articles) {
    console.log(articles.new[0].title);
    for(var i=0; i<5; i++){
        $("#showHotNav"+i+" span a").text(articles.new[i].title);
        $("#showHotNav"+i+" span a").attr("href", "./articleShow.html?article=" + articles.new[i].id);
    }
}