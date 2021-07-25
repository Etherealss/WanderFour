<template>
<div class="otherRelatedBox">
  <ul>
    <!-- 标题以及一些图标 -->
    <div class="box-top">
      <slot name="box-top-text"></slot>
      <slot name="box-top-img"></slot>
    </div>
    <div class="li-items">
      <slot name="li-items"></slot>
    </div>
    <!-- <li v-for="(item, index) in list">
      <a href="">{{item}}</a>
    </li> -->
  </ul>
</div>
</template>

<script>
  export default {
    name: 'otherRelatedBox',
    data() {
      return {
      }
    },
    methods: {

    },
    components: {

    }
  }
</script>

<style scoped>
  .otherRelatedBox {
    width: 265px;
    height: 294px;
    border-radius: 10px;
    box-shadow: 1px 1px 15px rgba(193, 193, 193,0.8);
  }

/* ———————————————— 标题 —————————————————— */
  .otherRelatedBox ul > .box-top {
    box-shadow: -1px 1px 5px rgba(0, 0, 0, 0.15);
  }
/* ------------------------------------------- */
  .otherRelatedBox .box-top {
    width: 265px;
    height: 50px;
    padding: 0;
    position: relative;
    background-repeat: no-repeat;
    box-shadow: 1px 1px 5px rgba(193, 193, 193,0.25);
    border-radius: 10px 10px 0 0;
  }

  .otherRelatedBox .box-top p {
    position: absolute;
    top: 14px;
    left: 103px;
    font-size: 17px;
    font-weight: bold;
    color: #333333;
  }

/* —————————— 树叶的图片 —————————— */
  .otherRelatedBox .box-top .slidebox-leaf {
    width: 115px;
    height: 84px;
    position: absolute;
    left: 180px;
    top: -24px;
  }

/* —————————————————— 内容 ———————————————————— */
  .otherRelatedBox ul {
    list-style-type: none;
    border-radius: 10px;
  }

  .otherRelatedBox ul li a{
    width: 265px;
    height: 46px;
    line-height: 46px;
    padding: 0 20px;
    display: inline-block;
    color: #333333;
    font-size: 15px;
    text-decoration: none;
    overflow:hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
    /* border: 1px solid #f00; */
  }

  .otherRelatedBox ul li:hover {
    cursor: pointer;
  }

  .otherRelatedBox ul li:nth-last-child(1):hover {
    border-radius: 0 0 10px 10px;
  }
</style>
