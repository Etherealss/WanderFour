<template>
  <div id="lw-high-school">
    <head-nav-bar>
      <div slot="nav-bar">
        <hs-nav-bar></hs-nav-bar>
      </div>
      <img slot="nav-img" src="../../assets/image/learningWorld/LW_HSTopBackground.png" alt="">
    </head-nav-bar>
  </div>
</template>

<script>
  import headNavBar from './lw-module/lw-headNavBar.vue';
  import hsNavBar from './lw-items/lw-hsNavBar.vue';

  export default {
    name: 'highSchool',
    data() {
      return {

      }
    },
    components: {
      headNavBar,
      hsNavBar
    }
  }
</script>

<style scoped>
  #lw-high-school {
    max-width: 1920px;
    width: 100%;
    min-width: 1200px;
    background-color: #f5f5f5;
    /* height: 2000px; */
    /* border: 1px solid #f00; */
  }
</style>
