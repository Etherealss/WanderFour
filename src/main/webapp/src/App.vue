<template>
  <div id="app">
    <!-- 顶部导航栏 -->
    <topmargin-header/>
    <router-view></router-view>
  </div>
</template>

<script>
  import topmarginHeader from './components/content/mainbar/topmarginHeader.vue';

  export default {
    name: 'app',
    components: {
      topmarginHeader
    }
  }
</script>

<style scoped>

</style>
