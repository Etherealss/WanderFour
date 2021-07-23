<template>
  <div class='nav-bar-item' @click="itemClick">
    <slot name='item-icon'></slot>
    <!-- 要使用插槽，在外层搞一个标签，用于定义一些其他样式或操作 -->
    <div :class="{active: isActive}">
      <slot name='item-text'></slot>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'NavBarItem',
    props: {  // 父子组件的动态传递
      path: String
    },
    data() {
      return {
        isActive: true
      }
    },
    methods: {
      itemClick() {
        //点击跳转页面
        this.$router.push(this.path);
      }
    },
    components: {
    }
  }
</script>

<style scoped>
  .nav-bar-item {
    /* flex: 1; */
    /* height: 49px; */
    letter-spacing: 1px;
    text-align: center;
  }

  .nav-bar-item > img {
    /* width: px; */
    height: 50px;
    margin: 2px 0 0 10px;
    vertical-align: middle;
    cursor: pointer;
  }

  .active {
    padding: 5px 11px;
    color: #343434;
    font-size: 1rem;
    /* border: 1px solid #000; */
  }

  .active:hover {
    color: #81cbbf;
    cursor: pointer;
  }
</style>
