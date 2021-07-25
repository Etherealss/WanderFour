<template>
  <div class="left-slidebox">
    <show-slide-box></show-slide-box>
  </div>
</template>

<script>
  import showSlideBox from '../show-item/show-slideBox.vue';

  export default {
    name: 'leftSlideBox',
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
  .askpost-show-slidebox {
    position: sticky;
    padding: 30px 30px 30px 10px;
    /* width: 310px; */
    top: 0;
    /* border: 1px solid #00f; */
  }
</style>
