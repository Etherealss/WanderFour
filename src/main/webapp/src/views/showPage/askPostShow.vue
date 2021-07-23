<template>
  <div class="askPostShow">
    问帖
    <show-slide-box></show-slide-box>
  </div>
</template>

<script>
  import showSlideBox from './show-item/show-slideBox.vue';

  export default {
    name: 'askPostShow',
    data() {
      return {

      }
    },
    components: {
      showSlideBox
    }
  }
</script>

<style scoped>
  .askPostShow {
    height: 1000px;
    border: 1px solid #f00;
  }
</style>
