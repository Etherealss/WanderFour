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
function getBesideWritingList(partition, type) {
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
            if (type == "article") {
                setArticleList(res);
            } else {
                setPostsList(res);
            }
        }
    })
}

function setArticleList(articles) {
    for (var i = 1; i <= 5; i++) {
        $("#showNewNav" + i + " a").text(articles.new[i-1].title);
        $("#showNewNav" + i + " a").attr("href", "./articleShow.html?article=" + articles.new[i-1].id);
        // $("#showNewNav" + i + " a").attr("title", articles.new[i].title);
    }
    for (var j = 1; j <= 5; j++) {
        $("#showHotNav" + j + " a").text(articles.hot[j-1].title);
        $("#showHotNav" + j + " a").attr("href", "./articleShow.html?article=" + articles.hot[j-1].id);
        // $("#showHotNav" + j + " a").attr("title", articles.hot[j].title);
    }
}

function setPostsList(posts) {
    for (var i = 1; i <= 5; i++) {
        $("#showNewNav" + i + " a").text(posts.new[i-1].title);
        $("#showNewNav" + i + " a").attr("href", "./answerPosts.html?posts=" + posts.new[i-1].id);
        // $("#showNewNav" + i + " a").attr("title", posts.new[i].title);
    }
    for (var j = 1; j <= 5; j++) {
        $("#showHotNav" + j + " a").text(posts.hot[j-1].title);
        $("#showHotNav" + j + " a").attr("href", "./answerPosts.html?posts=" + posts.hot[j-1].id);
        // $("#showHotNav" + j + " a").attr("title", posts.hot[j].title);
    }
}