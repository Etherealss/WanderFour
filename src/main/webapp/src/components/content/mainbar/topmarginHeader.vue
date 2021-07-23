<template>
  <div>
    <div id="topmargin-header">
      <nav-bar>
        <!-- 1、首页图标 -->
        <nav-bar-item path="/homepage">
          <img slot="item-icon" src="../../../assets/image/homepage/navigationLogo.png" alt=""/>
        </nav-bar-item>
        <!-- 2、三个大页面 -->
        <div id="left-options">
          <nav-bar-item path="/learningWorld">
            <div slot="item-text">学习天地</div>
          </nav-bar-item>
          <nav-bar-item path="/professionalIntroduction">
            <div slot="item-text">专业介绍</div>
          </nav-bar-item>
          <nav-bar-item path="/universityLife">
            <div slot="item-text">大学生活</div>
          </nav-bar-item>
        </div>
        <!-- 3、搜索框：点击搜索或按下Enter，跳转到搜索页面 -->
        <search path="/searchPage"></search>

        <div id="right-options">
          <nav-bar-item id="wall-option" path="/wall">
            <div slot="item-text">目标墙</div>
          </nav-bar-item>
          <!-- 登录注册 -->
          <nav-bar-item id="sign-options" path="/sign">
            <div slot="item-text">
              <button>登录</button>
              <span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
              <button>注册</button>
            </div>
          </nav-bar-item>
        </div>
      </nav-bar>
    </div>
  </div>
</template>

<script>
  import NavBar from '../../common/navbar/NavBar.vue';
  import NavBarItem from './NavBarItem.vue';
  import Search from '../../common/search/Search.vue';

  export default {
    name: 'topmarginHeader',
    data() {
      return {

      }
    },
    components: {
      NavBar,
      NavBarItem,
      Search
    }
  }
</script>

<style scoped>
  @import "../../../assets/css/base.css";
  #topmargin-header {
    width: 100%;
    min-width: 1200px;
    height: 55px;
    margin: 0 auto 10px;
    box-shadow: 1px 1px 10px rgba(0, 0, 0, .15);
    background: #fff;
  }

  /* 三个大页面 */
  #left-options {
    display: flex;
    margin: 7px 0 0 20px;
    justify-content: center;
    align-items: center;
  }

  /* 目标墙+登录注册/个人中心 */
  #right-options {
    display: flex;
    position: absolute;
    right: 0;
    top: calc(50% - 15px + 4px);
    justify-content: center;
    align-content: center;
  }

  /* 目标墙 */
  #wall-option {
    padding: 10px 0 10px 20px;
    margin: -10px 80px 0 0;
    background: url(../../../assets/image/homepage/wallNavLogo.png) 0px 10px no-repeat;
    background-size: 25px;
    /* border: 1px solid #f00; */
  }

  /* 登录注册 */
  #sign-options button {
    border: none;
    outline: none;
    cursor: pointer;
    background: none;
    letter-spacing: 1px;
  }

  #sign-options button:hover {
    color: #81cbbf;
  }

  #sign-options span {
    color: #343434;
  }
</style>
