<template>
  <div class="outside-box">
    <div class="insert-box">
    <div class="carousel-box">
      <carousel-item></carousel-item>
      <carousel-item>
        <img slot='carousel-img' src="../../../assets/image/homepage/homePage_banner.png" alt=""/>
      </carousel-item>
      <carousel-item></carousel-item>
      <carousel-item></carousel-item>
      <carousel-item></carousel-item>
    </div>
    <div class="control-item">
      <button id="left">&lt;</button>
      <button id="right">&gt;</button>
    </div>
    </div>
    <ul class="point-items">
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
</template>

<script>
  import carouselItem from './carouselItem.vue';
  import carouselObj from '../../../assets/javascript/carousel.js';

  export default {
    name: 'carousel',
    data() {
      return {

      }
    },
    mounted() {
      this.carouselLoad();
    },
    methods: {
      carouselLoad() {
        carouselObj.carouselLoad();
      }
    },
    components: {
      carouselItem
    }
  }
</script>

<style scoped>
  .outside-box {
    position: relative;
    margin: 20px auto 120px;
    width: 1200px;
    height: 450px;
    /* border: 1px solid #0f0; */
  }

  /* —————————————————— 轮播图 —————————————————— */
  .insert-box {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .carousel-box {
    position: relative;
    width: 1020px;
    height: 400px;
  }

  /* ——————————————— 轮播图样式 ——————————————— */
  .carousel-box > div {
    position: absolute;
    width: 600px;
    height: 300px;
    border-radius: 5px;
    box-shadow: 1px 1px 10px rgba(0, 0, 0, .25);
    transition: all ease-in-out .6s;
    /* border: 1px solid #f00; */
  }

  .carousel-box > div > img {
    width: 600px;
    height: 300px;
    border-radius: 5px;
  }

  .carousel-left {
    top: 50px;
    left: 0;
    z-index: 1;
    opacity: 0.6;
  }

  .carousel-center {
    top: 50px;
    left: 200px;
    z-index: 10;
    transform:scale(1.2);
  }

  .carousel-right {
    top: 50px;
    left: 400px;
    z-index: 1;
    opacity: 0.6;
  }

  .carousel-others {
    top: 50px;
    left: 200px;
  }

  /* —————————————————— 按钮 —————————————————— */
  .control-item button {
    position: absolute;
    width: 50px;
    height: 40px;
    font-size: 30px;
    z-index: 100;
    top: calc(50% - 20px);
    cursor: pointer;
  }

  #left {
    left: 150px;
  }

  #right {
    right: 150px;
  }

  /* —————————————————— 悬浮按钮 —————————————————— */
  .point-items {
    position: absolute;
    display: flex;
    list-style: none;
    text-align: center;
    left: calc(50% - 70px);
    bottom: 0;
    /* border: 1px solid #f00; */
  }

  .point-items > li {
    margin: 5px;
    width: 20px;
    height: 8px;
    border-radius: 50px;
    background: #cccccc;
    box-shadow: .1px .1px 5px rgba(0, 0, 0, .1);
    transition: all ease-in-out 0.5s;
    cursor: pointer;
  }

  .active-point {
    width: 40px !important;
    background: #72c3b6 !important;
  }
</style>
