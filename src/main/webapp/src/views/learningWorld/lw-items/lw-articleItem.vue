<template>
  <div class="learning-world-article">
    <h3>{{msg.title}}</h3>
    <p>{{msg.summary}}</p>
    <ul>
        <li v-for="item in msg.tags">{{item}}</li>
    </ul>
  </div>
</template>

<script>
  import classObj from '../../../assets/javascript/utils/className.js';

  export default {
    name: 'articleItem',
    props: {
      msg: {
        type: Object,
        default: '未初始化'
      }
    },
    data() {
      return {
        // articleMessage: {
        //   title: '怎么高效学好数学？',
        //   summary: `一、我的数学学习历程及遇到的困难
        //     1、我的数学学习历程
        //     我个人的数学学习历程比较曲折，在大一的时候挂过数学分析。我本人是学力学的，所以我们大一的数学课跟数学系的课程几乎是一致的。
        //     大二的时候还挂过高等代数。
        //     两次考研，第一次考研没考上，第二次考研成功了，我考上了理想的学校，理想的专业，选择了理想的导师，
        //     以专业第一名的成绩录取，包括`,
        //   tags: ['学习经验', '数学', '高效学习']
        // }
      }
    },
    mounted() {
      if(this.$el.children[2].children.length != 0) {
        this.addTagBackground();
      }
    },
    methods: {
      addTagBackground() {  // 根据标签的内容，给标签加背景色
        let tagList = this.$el.children[2];
        for(let i = 0; i < tagList.children.length; i++) {
          switch (tagList.children[i].innerHTML) {
            case '学习经验': {
              classObj.addClass(tagList.children[i], 'learning-experience-tag');
              break;
            }
            default: {
              classObj.addClass(tagList.children[i], 'ordinary-tag');
              break;
            }
          }
        }
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* —————————— 文章及其他 —————————— */
  .learning-world-article{
    width: 800px;
    height: 167.7px;
    margin: 0 0 16px 5px;
    background: #fff;
    border-radius: 5px;
    /* border: 1px solid #f00; */
    box-shadow: 1px 1px 12px rgba(204, 204, 204,0.3);
    cursor: pointer;
    transition-duration: 0.3s;
  }

  .learning-world-article:hover{
    box-shadow: 1px 1px 10px rgba(0, 0, 0,0.28);
  }

  .learning-world-article h3{
    padding: 15px 0 0 28px;
    font-size: 19px;
  }

  .learning-world-article p {
    position: relative;
    height: 60px;
    line-height: 20px;
    margin: 15px 30px 0 30px;
    font-size: 14px;
    overflow: hidden;
    /* border: 1px solid #f00; */
  }

  .learning-world-article p::after {
    content: '';
    width: 100px;
    height: 20px;
    position: absolute;
    bottom: 0;
    right: 0;
    background: linear-gradient(to right, rgba(255,255,255,0.2),rgba(255,255,255,0.8) ,rgba(255,255,255,1));
    /* border: 1px solid #f00; */
  }

  /* 小标签 */
  .learning-world-article > ul{
    display: flex;
    margin: 13px 0 0 30px;
    list-style-type: none;
  }

  .learning-world-article > ul > li{
    float: left;
    margin-right: 14px;
    padding: 5px 10px;
    font-size: 14px;
    /* background: #e4f4f1; */
    border-radius: 13px;
    color: #666666;
  }

  .learning-experience-tag {
    background: #fee8ea;
  }

  .ordinary-tag {
    background: #e4f4f1;;
  }
</style>
