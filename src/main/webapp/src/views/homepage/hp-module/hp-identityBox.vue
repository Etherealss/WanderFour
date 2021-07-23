<template>
  <div class="identityBox" id="">
    <!-- 标志图片 -->
    <div class="homePage_LWid_boxImg" >
      <slot name="idBox-icon"></slot>
    </div>
    <div class="idBox-text">
      <slot name="idBox-text"></slot>
    </div>
    <br />
    <slot name='idBox-message'></slot>
    <button class="toLWPath" @click="jumpLWClick">进 入</button>
    <a class="homePage_expend" @click="idSelected">
      <span class="homePage_plus">+</span>
      <span class="homePage_minus">-</span>
    </a>
  </div>
</template>

<script>
  import cnObj from '../../../assets/javascript/utils/className';

  export default {
    name: 'identityBox',
    props: {
      path: String
    },
    methods: {
      idSelected(e) {
        //e.target.parentNode.parentNode == '.identityBox'
        let idBox = e.target.parentNode.parentNode;
        cnObj.toggleClass(idBox, 'homePage_selected');
      },
      jumpLWClick() {
        this.$router.push(this.path);
      }
    },
    components: {
    }
  }
</script>

<style scoped>
  .identityBox {
    width: 30%;
    height: 85%;
    /* max-width: 218px;
    height: 232px; */
    position: relative;
    margin-left: 38px;
    border-radius: 4px;
    background-color: #95c9ae;
    overflow: hidden;
    box-shadow: 1px 1px 10px rgba(0,0,0,0.2);
  }

  /* 点击进入的按钮 */
  .toLWPath {
    margin-bottom: 10px;
    padding: 5px 30px;
    position: absolute;
    bottom: -50px;
    left: 35px;
    font-size: 12px;
    font-weight: bold;
    border: none;
    outline: none;
    background: none;
    color: #fff;
    border-radius: 4px;
    box-shadow: 1px 1px 10px rgba(0,0,0,.1);
    border: 1px dotted #fff;
    transition: all ease-in-out .5s;
  }

  .toLWPath:hover {
    background: #fff;
    color: #95c9ae;
    cursor: pointer;
  }

  /* 头像的渐变 */
  .homePage_LWid_boxImg {
    transition: all ease-in-out .8s;
  }

  /* 身份的文字 */
  .idBox-text {
    width: 100%;
    text-align: center;
  }

  /* "+" 和 "-" */
  .identityBox a.homePage_expend{
    width: 40px;
    height: 40px;
    position: absolute;
    right: 50%;
    bottom: 30px;
    transform: translateX(50%);
    display: block;
    border-radius: 50%;
    background: #fff;
    line-height: 40px;
    text-align: center;
    transition: all ease-in-out .5s;
    cursor: pointer;
  }

  .identityBox a.homePage_expend .homePage_plus{
    width: 40px;
    display: inline-block;
    opacity: 1;
    color: #787878;
    font-size: 24px;
  }

  .identityBox a.homePage_expend .homePage_minus{
    opacity: 0;
    color: #95c9ae;
    font-size: 24px;
  }

  /* —————————————— 有特殊属性 homePage_selected —————————————— */
  .identityBox.homePage_selected h3{
    width: 70%;
    top: 28px;
    left: 15%;
    z-index: 15;
    text-align: left;
    border-bottom: 1px solid rgba(255,255,255,0.8);
    transition: all ease-in-out .5s;
  }

  .identityBox.homePage_selected ul{
    opacity: 1;
    margin-top: 82px;
  }

  .identityBox.homePage_selected button{
    bottom: 20px;
    transition: all ease-in-out .5s;
  }

  /* 装加减号的盒子 */
  .identityBox.homePage_selected a.homePage_expend{
    width: 80px;
    height: 50px;
    position: absolute;
    right: -20px;
    bottom: -15px;
    display: block;
    background: #fff;
    transform: rotate(-45deg);
    transition: all ease-in-out .5s;
  }

  .identityBox.homePage_selected a.homePage_expend span{
    position: absolute;
    top: -5px;
    left: 40px;
    display: block;
    transform: rotate(45deg);
    font-size: 24px;
    transition: all ease-in-out .5s;
  }

  .identityBox.homePage_selected a.homePage_expend .homePage_minus{
    opacity: 1;
  }

  .identityBox.homePage_selected a.homePage_expend .homePage_plus{
    opacity: 0;
  }

  .identityBox.homePage_selected .homePage_LWid_boxImg{
    position: absolute;
    top: -65px;
    left: 50px;
    transform: scale(1.8);
    opacity: 0.5;
    transition: all ease-in-out 1s;
  }

  .identityBox.homePage_selected:hover .homePage_LWid_boxImg{
    animation: none;
  }
</style>
