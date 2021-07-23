<template>
  <div>
    <div class="homePage_LW_titleBox">
      <h1 id="homePage_LearningWorld_target">学习天地</h1>
      <p>
          经验、资源、攻略这里都有，<br />
          在这里开始探索你的学习之路吧。
      </p>
  </div>
  </div>
</template>

<script>
  export default {
    name: 'lwText',
    data() {
      return {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
  .homePage_LW_titleBox{
    position: relative;
    width: 1200px;
    height: 450px;
    margin: 0 auto;
    background: url(../../../assets/image/homepage/homePage_LearningWorld_Communication.png) no-repeat 625px 40px;
    background-size: 516.9px 365.6px;
    /* border: 1px solid #f00; */
  }

  .homePage_LW_titleBox h1{
    position: absolute;
    top: 28px;
    left: 35px;
    color: #333;
    font-weight: normal;
    font-size: 40px;
    letter-spacing: 4px;
  }

  .homePage_LW_titleBox p{
    position: absolute;
    top: 124px;
    left: 35px;
    color: #333;
    line-height: 30px;
    font-size: 18px;
    letter-spacing: 5px;
  }

</style>
