<template>
  <div id="HP_textPart">
    <p>
      在未来，你想做什么，成为什么，该做什么。<br />
      在这里，你可以探索出答案。
    </p>
    <div class="HP_titleImage">
      <img src="../../assets/image/homepage/homePage_studyTitle.png" alt="" />
    </div>
  </div>
</template>

<script>
  export default {
    name: 'textPart',
    data() {
      return {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
/* —————————————————————— 首页文本区域 —————————————————————————— */
  #HP_textPart {
    width: 1200px;
    height: 300px;
    margin: 0 auto;
    position: relative;
    /* border: 1px solid #0f0; */
  }

  #HP_textPart > p {
    line-height: 40px;
    position: absolute;
    left: 35px;
    top: 48px;
    letter-spacing: 4px;
    font-size: 18px;
    color: #333;
  }

  .HP_titleImage {
    position: absolute;
    top: 25px;
    right: 0;
  }

  .HP_titleImage > img {
    width: 90%;
  }
</style>
