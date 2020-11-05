/**
 * @ignore  =====================================================================================
 * @fileoverview 初始化分页页面
 * @author  寒洲
 * @date 2020/10/29
 * @ignore  =====================================================================================
 */
/**
 * 访问后端获取数据列表
 * @param type article还是posts
 * @param partition 分区 学习天地-1 专业介绍-2 大学生活-3
 * @param order 获取最新的文章/问贴传：time
 *              获取最热（点赞最多）的文章/问贴传：like
 */
function getWritingList(type, partition, order) {
    $.ajax({
        type: 'GET',
        url: '/InitWritingController?type=' + type + '&partition=' + partition + '&order=' + order,
        dataType: 'json',
        contentType: "application/json",
        /**
         * @param {Object} res 数据包
         * @param {Array} res.writings 文章/问贴 列表
         * @param res.writings.userImg 用户头像
         * @param res.writings.userNickname 用户昵称
         * @param res.writings.writing 具体的文章数据
         * @param res.writings.writing.id
         * @param res.writings.writing.authorId
         * @param res.writings.writing.category
         * @param res.writings.writing.title
         * @param res.writings.writing.content
         * @param res.writings.writing.liked
         * @param res.writings.writing.collected
         * @param res.writings.writing.createTime
         * @param res.writings.writing.label1
         * @param res.writings.writing.label2
         * @param res.writings.writing.label3
         * @param res.writings.writing.label4
         * @param res.writings.writing.label5
         */
        success: function (res) {
            console.log(res);
            if (partition == 1){
                if (type=="article"){
                    //渲染学习天地的文章
                    setLearningArticle(res.writings);
                } else {
                    //渲染学习天地的问贴
                    setLearningPosts(res.writings);
                }
            } else if(partition == 2){
                //专业介绍
            } else if(partition == 3){
                //大学生活
            } else {
                console.log("partition参数错误");
            }
        }
    })
}

function setLearningArticle(list) {
    //渲染

}
function setLearningPosts(list) {
    //渲染
}