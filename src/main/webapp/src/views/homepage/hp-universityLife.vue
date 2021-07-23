<template>
  <div id="homePage_University_Life">
    <div class="HUL_txtBox">
      <h1 id="homePage_University_Life_target">大学生活</h1>
      <p>
        大学必知：自学、课外活动、证书、<br />
        综测、奖学金……
      </p>
      <button @click="jumpULClick">点击进入</button>
    </div>
    <img src="../../assets/image/homepage/homePage_University_Life_background.png" alt="" />
  </div>
</template>

<script>
  export default {
    name: 'universityLife',
    props: {
      path: String
    },
    methods: {
      jumpULClick() {
        this.$router.push(this.path);
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ————————————————————— 大学生活 ————————————————————————— */
  #homePage_University_Life{
    max-width: 1600px;
    width: 100%;
    height: 450px;
    margin: 0 auto;
    padding-top: 20px;
    position: relative;
    /* border: 1px solid #f00; */
  }

  .HUL_txtBox{
    width: 1200px;
    height: 400px;
    margin: 0 auto;
    padding-left: 40px;
    /* border: 1px solid #0f0; */
  }

  #homePage_University_Life h1 {
    margin-top: 28px;
    color: #333;
    font-size: 40px;
    font-weight: normal;
    letter-spacing: 4px;
  }

  #homePage_University_Life p {
    margin-top: 32px;
    line-height: 30px;
    font-size: 18px;
    letter-spacing: 4px;
  }

  .HUL_txtBox button {
    margin-top: 40px;
    background-color: #72c3b6;
    border: none;
    outline: none;
    z-index: 10;
    border-radius: 2px;
    padding: 10px 27px;
    font-size: 16px;
    color: #fff;
    text-shadow: .1px .1px .1px rgba(0,0,0,1);
    box-shadow: 1px 1px 8px rgba(0,0,0,0.15);
  }

  .HUL_txtBox button:hover {
    box-shadow: 1px 1px 10px rgba(0,0,0,0.3);
    cursor: pointer;
  }

  #homePage_University_Life img{
    width: 100%;
    position: absolute;
    top: 78px;
    z-index: -1;
  }
</style>
