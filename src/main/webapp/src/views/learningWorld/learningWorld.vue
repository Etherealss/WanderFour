<template>
  <div id="learing-world-page">
    <!-- 头部导航栏 -->
    <head-nav-bar>
      <div slot="nav-bar">
        <hs-nav-bar/>
      </div>
      <img slot="nav-img" src="../../assets/image/learningWorld/LW_HSTopBackground.png" alt="">
    </head-nav-bar>
    <!-- 问贴模块 -->
    <ask-post/>
    <!-- 文章模块 -->
    <article-box/>
    <!-- 底部 -->
    <bottom-page/>
  </div>
</template>

<script>
  import headNavBar from './lw-module/lw-headNavBar.vue';
  import hsNavBar from './lw-items/lw-hsNavBar.vue';
  import askPost from './lw-module/lw-askPost.vue';
  import articleBox from './lw-module/lw-articleBox.vue';
  import bottomPage from '../../components/content/mainbar/bottomFooter.vue';

  export default {
    name: 'learningWorld',
    data() {
      return {

      }
    },
    components: {
      headNavBar,
      hsNavBar,
      askPost,
      articleBox,
      bottomPage
    }
  }
</script>

<style>
/* 去除scoped限制，添加背景色 */
  body {
    background-color: #f5f5f5;
  }

  #learing-world-page {
    width: 100%;
    min-width: 1200px;

    /* border: 1px solid #f00; */
  }

  #aaa {
    width: 1200px;
    height: 1000px;
    border: 1px solid #f00;
  }
</style>
