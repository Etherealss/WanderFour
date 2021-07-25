<template>
  <div class="askPostShow">
    <!-- <show-slide-box></show-slide-box> -->
    <left-slide-box/>
    <!-- askpost 问帖部分 -->
    <div class="askpost-content">
      <askpost-content/>
    </div>
  </div>
</template>

<script>
  import leftSlideBox from './show-module/show-leftSlideBox.vue';
  import askpostContent from './show-item/show-askpostContent.vue';

  export default {
    name: 'askPostShow',
    data() {
      return {

      }
    },
    components: {
      leftSlideBox,
      askpostContent
    }
  }
</script>

<style scoped>
  .askPostShow {
    display: flex;
    margin: 0 auto;
    width: 1200px;
    height: 2000px;
    /* border: 1px solid #f00; */
  }

  /* ———————————— 问帖部分 ———————————————— */
  .askpost-content {
    padding: 30px 0 0 6px;
    width: 900px;
    height: 1500px;
    /* border: 1px solid #0f0; */
  }
</style>
