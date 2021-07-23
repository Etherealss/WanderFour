<template>
  <div id="homepage">
    <!-- 顶部轮播图 -->
      <carousel/>
      <text-part/>
    <!-- 中部 -->
    <div class="homePage-content">
      <learning-world/>
      <professional-introduction path="/professionalIntroduction"/>
      <university-life path="/universityLife"/>
    </div>
    <!-- 侧边点击跳转到指定位置 -->
    <return-top/>
  </div>
</template>

<script>
  import carousel from '../../components/common/carousel/Carousel.vue';
  import textPart from './textPart.vue';
  import learningWorld from './hp-learningWorld.vue';
  import professionalIntroduction from './hp-professionalIntroduction.vue';
  import universityLife from './hp-universityLife.vue';
  import returnTop from '../../components/common/returnPosition/position.vue';

  export default {
    name: 'homepage',
    data() {
      return {

      }
    },
    components: {
      carousel,
      textPart,
      learningWorld,
      professionalIntroduction,
      universityLife,
      returnTop
    }
  }
</script>

<style scoped>
  #homepage {
    width: 100%;
    min-width: 1200px;
  }

  .homePage-content {
    width: 100%;
    height: auto;
    /* border: 1px solid #00f; */
  }
</style>
