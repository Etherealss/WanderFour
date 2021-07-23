<template>
  <div class="bottom-page-box">
    <div class="bottomOfPage">
      <div class="footIconBackground"></div>
      <p>
        让高中生提前了解专业，填报志愿时能有所选择。让大学生明确目标，迷茫不再，对自己有所规划。
      </p>
      <div class="bottomAbout-us">
        <p>联系我们</p>
        <ul>
          <li></li>
          <li></li>
          <li></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'bottomFooter',
    data() {
      return {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ———————————————————————————— 页面底部 —————————————————————————————— */
  .bottom-page-box{
    width: 100%;
    height: 175px;
    background: #81cbbf;
    z-index: 200;
    margin-top: 100px;
    /* position: absolute; */
    /* bottom: 50px; */
  }

  .bottomOfPage{
    width: 1200px;
    height: 175px;
    margin: 0 auto;
    position: relative;
    /* border: 1px solid #f00; */
    z-index: 200;
  }

  /* ———————— 页脚图标 —————————— */
  .footIconBackground {
    width: 104px;
    height: 55px;
    position: absolute;
    left: 20px;
    top: 23px;
    background: url(../../../assets/image/footer/pageFooterLogo.png) no-repeat;
    background-size: 104px 55px;
  }

  .bottomOfPage > p {
    position: absolute;
    left: 20px;
    bottom: 70px;
    color: #fff;
    font-size: 15px;
    /* border: 1px solid #0f0; */
  }

  /* —————————————— 联系我们 —————————————————— */
  .bottomAbout-us{
    width: 137px;
    height: 80px;
    position: absolute;
    right: 20px;
    bottom: 59px;
    /* border: 1px solid #f00; */
  }

  .bottomAbout-us > p{
    color: #fff;
    font-size: 15px;
    letter-spacing: 1px;
    margin-bottom: 20px;
  }

  /* ————— 图标 ————— */
  .bottomAbout-us > ul{
    display: flex;
    flex-direction: row;
    list-style-type: none;
    /* border: 1px solid #fff; */
  }

  .bottomAbout-us > ul li{
    width: 37px;
    height: 37px;
    border-radius: 100%;
    background-size: 37px 37px;
    box-shadow: 1px 1px 5px rgba(0,0,0,0.1);
    /* border: 1px solid #0f0; */
  }

  .bottomAbout-us > ul li:nth-child(1){
    margin-right: 12px;
    background-image: url(../../../assets/image/footer/footWeiBo.png);
  }

  .bottomAbout-us > ul li:nth-child(2){
    margin-right: 12px;
    background-image: url(../../../assets/image/footer/footWeiXin.png);
  }

  .bottomAbout-us > ul li:nth-child(3){
    background-image: url(../../../assets/image/footer/footEmail.png);
  }

</style>
