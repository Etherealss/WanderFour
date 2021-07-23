<template>
  <div id="nav-bar">
    <slot></slot>
  </div>
</template>

<script>
  import NavBarItem from '../../content/mainbar/NavBarItem.vue';
  import Search from '../search/Search.vue';

  export default {
    name: 'NavBar',
    data() {
      return {

      }
    },
    components: {
      NavBarItem,
      Search
    }
  }
</script>

<style scoped>
  #nav-bar {  /*topmargin_box*/
    display: flex;
    position: relative;
    margin: 0 auto 10px;
    width: 1200px;
    height: 55px;
    background-color: #fff;
    /* border: 1px solid #f00; */
  }
</style>
