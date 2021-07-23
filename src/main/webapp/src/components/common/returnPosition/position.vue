<template>
  <div class="slowToTop">
    <ul @click="slowTo">
      <li>学习天地</li>
      <li>专业介绍</li>
      <li>大学生活</li>
      <li @click="slowToTop">回到顶部</li>
    </ul>
  </div>
</template>

<script>
  import modelsObj from '../../../assets/javascript/utils/models';

  export default {
    name: 'App',
    mounted() { // 监控本页面的滚动事件
      window.addEventListener('scroll', this.scrollStype);
    },
    destroyed() { // 切换路由后切断 scroll 监控
      window.removeEventListener('scroll', this.scrollStype);//监听页面滚动事件
    },
    methods: {
      slowToTop() {  // 回到顶部
        modelsObj.slowToTop();
      },
      slowTo(e) {   // 类似于跳转锚点
        let target = modelsObj.judgmentBtn(e.target); // 获取要跳转的目的地
        modelsObj.slowToTarget(target);
      },
      scrollStype() { // 在哪一片区域就显示在哪一片区域
        let btnList = document.querySelector('.slowToTop').children[0];   // 按钮 ul
        // 侧边导航栏的伸缩
        modelsObj.sidebarStatus(btnList.parentNode);
        // 三个板块的位置
        let HLW_position = document.querySelector("#homepage-Learning-World");
        let HPI_position = document.querySelector("#homePage_Professional_Introduction");
        let HUL_position = document.querySelector("#homePage_University_Life");
        // 在指定区域内的样式
        modelsObj.scrollStype(btnList.children[0], HLW_position);
        modelsObj.scrollStype(btnList.children[1], HPI_position);
        modelsObj.scrollStype(btnList.children[2], HUL_position);
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  .slowToTop {
    position: fixed;
    /* right: 0px; */
    right: -100px;
    bottom: 50px;
    width: 80px;
    height: 140px;
    padding: 5px 0;
    border-radius: 4px 0 0 4px;
    background: #fff;
    box-shadow: 2px 2px 10px rgba(0,0,0,.25);
    transition: all ease-in-out .5s;
    z-index: 1000;
    /* border: 1px solid #f00; */
  }

/* 侧边栏隐藏出现 */
.homePage_appear {right: 0;}
.homePage_hide {right: -100px;}

  .slowToTop ul {
    list-style-type: none;
    background: #fff;
    color: #333;
  }

  .slowToTop ul li {
    padding: 8px 4px;
    text-align: center;
    font-size: 15px;
    letter-spacing: 1px;
    cursor: pointer;
    /* transition: all ease-in-out .3s; */
  }

  .slowToTop ul li:hover{color: #72c3b6;}

  .active {
    color: #fff;
    background: #72c3b6;
  }

  .active:hover {
    color: #fff !important;
  }
</style>
