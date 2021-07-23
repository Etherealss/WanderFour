<template>
  <div class="learning-world-article">
    <h2>文章</h2>
      <div class="learning-world-contentbox">
        <!-- 文章主要内容 Part -->
        <div class="learning-world-rightbox">
          <div class="tag-switch-mainbox">
            <article-tag-switch/>
          </div>
          <button @click="listAppearClick">查看更多</button>
        </div>
        <!-- 右边上下滑动 Part -->
        <div class="LearningWorld-slideBox">
          <div>
            <article-slide-box/>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
  import classObj from '../../../assets/javascript/utils/className.js';
  import articleItem from '../lw-items/lw-articleItem.vue';
  import articleTagSwitch from '../lw-items/lw-articleTagSwitch.vue';
  import articleSlideBox from '../lw-items/lw-articleSlideBox.vue';

  export default {
    name: 'articleBox',
    data() {
      return {

      }
    },
    methods: {
      listAppearClick(e) {
        let articleContentBox = e.target.previousElementSibling;
        if(!classObj.isHasClass(articleContentBox, 'tag-switch-list-appear')) {
          e.target.innerHTML = '点击收起';
        } else {
          e.target.innerHTML = '查看更多';
        }
        // 点击"查看更多"，出现与否
        classObj.toggleClass(articleContentBox, 'tag-switch-list-appear');
      }
    },
    components: {
      articleItem,
      articleTagSwitch,
      articleSlideBox
    }
  }
</script>

<style scoped>
  .learning-world-article {
    margin: 0 auto;
    padding: 0 30px;
    margin: 100px auto 30px;
    width: 1200px;
    /* height: 700px; */
    /* border: 1px solid #f00; */
  }

  .learning-world-article h2 {
    height: 25px;
    font-size: 25px;
    letter-spacing: 3px;
    margin-bottom: 25px;
    /* border: 1px solid #00f; */
  }

  /* ——————— 左边文章及其他内容Part ————————— */
  .learning-world-contentbox {
    /* height: 856px; */
    display: flex;
    padding: 20px 0 25px 0;
    border-top: 2px solid rgba(165, 165, 165, .3);
    border-bottom: 2px solid rgba(165, 165, 165, .3);
    /* border: 1px solid #0f0; */
  }

  /* ————— 切换栏内 ——————— */
  .tag-switch-mainbox {
    padding: 0 20px 0 15px;
    height: 800px;
    overflow: hidden;
    transition: all ease-in-out .3s;
    /* border: 1px solid #f00; */
  }

  /* ——————— 查看更多 ——————— */
  .learning-world-rightbox > button {
    display: inline-block;
    margin-left: calc(50% - 32px);
    border: none;
    outline: none;
    background: none;
    cursor: pointer;
    transition: all ease-in-out .3s;
  }

  .learning-world-rightbox > button:hover {
    color: #34b3a8;
  }

  /* 点击后显示 */
  .tag-switch-list-appear {
    height: 1180px;
  }

/* ——————————————— 滑动盒子 ————————————————— */
  .LearningWorld-slideBox {
    width: 280px;
    margin: -20px 0 0 0;
    /* border: 1px solid #f00; */
  }

  .LearningWorld-slideBox > div {
    position: sticky;
    top: 0;
    border: 1px solid rgba(0, 0, 0, 0);
  }
</style>
