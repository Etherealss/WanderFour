<template>
  <div id="homePage_Professional_Introduction">
    <div class="homePage_PI_subjectBg"></div>
      <div class="homePage_PI_title">
        <h1 id="homePage_Professional_Introduction_target">专业介绍</h1>
        <p>
          希望你在踏入大学前，能够清楚自己想要的是什么。<br />
          希望你在大学时，能够清楚自己在做什么。
        </p>
        <button @click="jumpPIClick">点击进入</button>
      </div>
  </div>
</template>

<script>
  export default {
    name: 'professionalIntroduction',
    props: {
      path: String
    },
    methods: {
      jumpPIClick() {
        this.$router.push(this.path);
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ————————————————————— 专业介绍 ————————————————————————— */
  #homePage_Professional_Introduction{
    width: 1200px;
    height: 750px;
    margin: 0 auto;
    display: flex;
    /* border: 1px solid #00f; */
  }

  .homePage_PI_subjectBg{
    width: 520px;
    height: 600px;
    margin: 130px 0 0 0;
    background: url(../../assets/image/homepage/homePage_Professional_Introduction_subjects.png) no-repeat;
    background-size: 100%;
    /* border: 1px solid #f00; */
  }

  .homePage_PI_title{
    width: calc(100% - 520px);
    position: relative;
    right: 40px;
    background: url(../../assets/image/homepage/homePage_Professional_Introduction_people.png) no-repeat 440px 360px;
    background-size: 57px 246px;
    /* border: 1px solid #0f0; */
  }

  .homePage_PI_title h1{
    position: absolute;
    top: 28px;
    right: 0;
    color: #333;
    font-size: 40px;
    font-weight: normal;
    letter-spacing: 4px;
  }

  .homePage_PI_title p{
    position: absolute;
    top: 124px;
    right: -10px;
    line-height: 30px;
    color: #333;
    font-size: 18px;
    letter-spacing: 4px;
  }

  .homePage_PI_title button{
    position: absolute;
    top: 225px;
    /* right: 140px; */
    right: 0;
    padding: 10px 27px;
    border: none;
    outline: none;
    border-radius: 2px;
    font-size: 16px;
    color: #fff;
    background-color: #72c3b6;
    text-shadow: .1px .1px .1px rgba(0,0,0,1);
    box-shadow: 1px 1px 8px rgba(0,0,0,0.15);
    cursor: pointer;
  }

  .homePage_PI_title button:hover{
    box-shadow: 1px 1px 10px rgba(0,0,0,0.3);
    cursor: pointer;
  }
</style>
