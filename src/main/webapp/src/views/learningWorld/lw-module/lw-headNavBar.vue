<template>
  <div class="lw-head-navbar">
    <slot name="nav-bar"></slot>
    <slot name="nav-img"></slot>
  </div>
</template>

<script>
  import hsNavBar from '../lw-items/lw-hsNavBar.vue';

  export default {
    name: 'headNavBar',
    data() {
      return {

      }
    },
    components: {
      hsNavBar
    }
  }
</script>

<style scoped>
  .lw-head-navbar {
    display: flex;
    margin: 0 auto;
    width: 1200px;
    height: 250px;
    justify-content: center;
    /* border: 1px solid #f00; */
  }

  .lw-head-navbar img {
    margin: 35px 0 0 0;
    width: 400px;
    height: 200px;
    /* border: 1px solid #0f0; */
  }
</style>
