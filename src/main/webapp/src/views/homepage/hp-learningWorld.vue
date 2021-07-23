<template>
  <div id="homepage-Learning-World">
    <lw-text/>
    <div id="outside-background">
      <div class="homePage_LW_identity">
        <div class="homePage_LWid_box">
          <!-- 高中生 -->
          <identity-box path="/learningWorld">
            <img class="idBox-icon" slot="idBox-icon" src="../../assets/image/homepage/homePage_highSchoolStudent_head.png" alt=""/>
            <h3 class="idBox-text" slot="idBox-text">高中生</h3>
            <ul class="idBox-message" slot="idBox-message">
              <li>高中/高考</li>
              <li>学习方法/经验</li>
              <li>资源分享</li>
            </ul>
          </identity-box>
          <!-- 大学生 -->
          <identity-box path="/learningWorld">
            <img class="idBox-icon" slot="idBox-icon" src="../../assets/image/homepage/homePage_universityStudent_head.png" alt=""/>
            <h3 class="idBox-text" slot="idBox-text">大学生</h3>
            <ul class="idBox-message" slot="idBox-message">
              <li>高中/高考</li>
              <li>学习方法/经验</li>
              <li>资源分享</li>
            </ul>
          </identity-box>
          <!-- 未知-游客 -->
          <identity-box path="/learningWorld">
            <img class="idBox-icon" slot="idBox-icon" src="../../assets/image/homepage/homePage_tourist_head.png" alt=""/>
            <h3 class="idBox-text" slot="idBox-text">游客</h3>
            <ul class="idBox-message" slot="idBox-message">
              <li>高中/高考</li>
              <li>学习方法/经验</li>
              <li>资源分享</li>
            </ul>
          </identity-box>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import identityBox from './hp-module/hp-identityBox.vue';
  import lwText from './hp-module/hp-lwText.vue';

  export default {
    name: 'learingWorld',
    data() {
      return {

      }
    },
    components: {
      lwText,
      identityBox,
    }
  }
</script>

<style scoped>
  #homepage-Learning-World {
    padding-bottom: 20px;
    /* border: 1px solid #0f0; */
  }

  /* ——————————— 身份该有的功能 ————————————————— */
  #outside-background {
    width: 100%;
    height: 322px;
    background: linear-gradient(to right, rgba(0, 0, 0, 0) 0%,rgba(0, 0, 0, 0) 50%,#ffd995 51%,#ffd995 100%);
  }

  .homePage_LW_identity {
    width: 100%;
    max-width: 1600px;
    min-width: 1200px;
    margin: 0 auto;
    height: 420px;
    position: relative;
    background: url(../../assets/image/homepage/homePage_LearningWorld_headLamp.png) no-repeat 55px 0;
    background-size: 1300px auto;
    /* border: 1px solid #f00; */
  }

  .homePage_LWid_box{
    width: 720px;
    height: 325px;
    position: absolute;
    top: 22px;
    left: 36%;
    display: flex;
    flex-direction: row;
    /* border: 1px solid #f00; */
  }

  /* ———————————————— 每个盒子内的内容 —————————————————— */
  .idBox-icon {
    width: 100px;
    height: 100px;
    margin: 20px auto;
    position: absolute;
    left: calc(50% - 50px);
    display: block;
    z-index: 5;
    border: 5px solid rgba(255,255,255,.3);
    border-radius: 50%;
  }

  /* ————————————— 身份文字 ————————————————— */
  .idBox-text {
    padding: 15px;
    position: absolute;
    top: 120px;
    /* left: calc(50% - 58px); */
    font-size: 24px;
    font-weight: normal;
    letter-spacing: 6px;
    color: #fff;
    z-index: 5;
    text-shadow: .1px .1px .1px rgba(0,0,0,.8);
    transition: all ease-in-out .5s;
  }

  /* ul列表：点击后才出现的部分 */
  .idBox-message {
    margin-top: 400px;
    margin: 400px 0 25px 50px;
    line-height: 25px;
    font-size: 14px;
    list-style-type: disc;
    color: #fff;
    position: absolute;
    z-index: 10;
    transition: all ease-in-out .5s;
  }

  .idBox-message li{
    line-height: 30px;
    letter-spacing: 2px;
    font-size: 14px;
  }
</style>
