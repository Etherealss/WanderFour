<template>
  <div class="askpost-show-slidebox">
    <div class="the-newest-posts">
      <other-related-box>
        <div class="box-top-text" slot="box-top-text">
          <img src="../../../assets/image/showPage/answerPosts_new.png"/>
          <p>最新问贴</p>
        </div>
        <img class="slidebox-leaf" slot="box-top-img" src="../../../assets/image/showPage/answerPosts_leafg.png"/>
        <!-- 其他问贴或文章的title模块 -->
        <li slot="li-items" v-for="(item, index) in list">
          <a href="">{{item}}</a>
        </li>
      </other-related-box>
    </div>
    <div class="waiting-answer">
      <other-related-box>
        <div class="box-top-text" slot="box-top-text">
          <img src="../../../assets/image/showPage/answerPosts_answer.png"/>
          <p>等你回答</p>
        </div>
        <img class="slidebox-leaf" slot="box-top-img" src="../../../assets/image/showPage/answerPosts_leafy.png"/>
        <!-- 其他问贴或文章的title模块 -->
        <li slot="li-items" v-for="(item, index) in list">
          <a href="">{{item}}</a>
        </li>
      </other-related-box>
    </div>
  </div>
</template>

<script>
  import otherRelatedBox from './show-otherRelatedBox.vue';

  export default {
    name: 'showSlideBox',
    data() {
      return {
        list: [1,2,3,4,5]
      }
    },
    components: {
      otherRelatedBox
    }
  }
</script>

<style scoped>
/* ——————————— 公共部分 —————————————— */
  .askpost-show-slidebox > div {
    width: 265px;
    height: 294px;
    border-radius: 10px;
    box-shadow: 1px 1px 15px rgba(193, 193, 193,0.8);
  }

  .box-top-text {
    border-radius: 10px 10px 0 0;
  }

/* —————————— 各自的部分 —————————— */
  .the-newest-posts {
    background: #daf2ee;
  }

  .the-newest-posts .box-top-text {
    background-color: #E8F3F1;
  }

  .the-newest-posts .box-top-text > img {
    margin-top: 5px;
    width: 91px;
    height: 41px;
  }

  .the-newest-posts .otherRelatedBox ul li:hover {
    background: #bce6df;
  }

/* -------------------------- */
  .waiting-answer {
    position: relative;
    margin-top: 40px;
    background: #fae5bf;
  }

  .waiting-answer .box-top-text {
    height: 50px;
    background-color: #f8f1e5;
  }

  .waiting-answer .box-top-text > img {
    position: absolute;
    top: -30px;
    left: -20px;
    width: 94px;
    height: 94px;
  }

  .waiting-answer .otherRelatedBox ul li:hover {
    background: #f9d89d;
  }
</style>
