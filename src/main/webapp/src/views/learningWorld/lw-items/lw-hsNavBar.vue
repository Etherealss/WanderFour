<template>
  <div class="learning-world-nav">
    <div class="learning-world-nav-plate">
      <ul>
        <li>高中</li>
        <li>
          <a href="#">主科</a>
        </li>
        <li>
          <a href="#">辅科</a>
        </li>
      </ul>
      <ul>
        <li>高考</li>
        <li>
          <a href="#">志愿填报</a>
        </li>
        <li>
          <a href="#">专业相关</a>
        </li>
        <li>
          <a href="#">心路历程</a>
        </li>
      </ul>
    </div>
    <div class="learning-world-nav-plate">
      <ul>
        <li>了解更多</li>
        <li>
          <a href="#">语言类</a>
        </li>
        <li>
          <a href="#">计算机相关</a>
        </li>
        <li>
          <a href="#">其他学科</a>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'hsNavBar',
    data() {
      return {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ——————————————————————— 导航板块 ———————————————————————— */
  .learning-world-nav{
    width: 720px;
    height: 210px;
    margin: 35px auto 0;
    /* border: 1px solid #00f; */
  }

  /* 各个板块信息 */
  .learning-world-nav-plate{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    /* border: 1px solid #0000ff; */
  }

  .learning-world-nav-plate > ul{
    height: 50px;
    line-height: 50px;
    margin: 0 30px 18.6px 4px;
    padding: 0 15px 0 50px;
    display: flex;
    flex-direction: row;
    list-style-type: none;
    background-color: #fff;
    border-radius: 24px;
    box-shadow: 1px 1px 8px rgba(204, 204, 204,0.25);
    background-repeat: no-repeat;
    background-position: 20px 10px;
    background-size: 27px;
  }

  .learning-world-nav-plate > ul:nth-child(1){
    background-image: url(../../../assets/image/learningWorld/LearningWorld_HS_highSchool.png);
  }

  .learning-world-nav-plate > ul:nth-child(2){
    background-image: url(../../../assets/image/learningWorld/LearningWorld_HS_gk.png);
  }

  .learning-world-nav-plate:nth-child(2) > ul{
    background-image: url(../../../assets/image/learningWorld/LearningWorld_HS_knowMore.png);
    background-size: 32px;
  }

  .learning-world-nav-plate > ul li{
    padding: 0 10px;
    font-size: 14px;
    /* border: 1px solid #f00; */
  }

  .learning-world-nav-plate > ul li a{
    text-decoration: none;
    color: #666;
    letter-spacing: 1.5px;
  }

  .learning-world-nav-plate > ul li:nth-child(1){
    padding-right: 16px;
    font-size: 14px;
    font-weight: bold;
    color: #333333;
    /* border: 1px solid #f00; */
  }

  .learning-world-nav-plate > ul li a:hover{
    color: #81cbbf;
  }
</style>
