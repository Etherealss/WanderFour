<template>
  <div class="askPost">
    <h2>问贴</h2>
    <div class="LearningWorld-toAskBox">
      <!-- 左边上下滑动Part -->
      <div class="LearningWorld-slideBox">
        <div>
          <askpost-slide-box/>
        </div>
      </div>
      <!-- 问贴主要内容Part -->
      <div class="LearningWorld-TA-mainBox">
        <div class="askpost-content-box">
          <askpost-item></askpost-item>
          <askpost-item></askpost-item>
          <askpost-item></askpost-item>
          <askpost-item></askpost-item>
          <askpost-item></askpost-item>
        </div>
        <button @click="appearClick">查看更多</button>
      </div>
    </div>
  </div>
</template>

<script>
  import classObj from '../../../assets/javascript/utils/className.js';
  import askpostSlideBox from '../lw-items/lw-askpostSlideBox.vue';
  import askpostItem from '../lw-items/lw-askpostItem.vue';


  export default {
    name: 'askPost',
    data() {
      return {
      }
    },
    methods: {
      appearClick(e) {
        let askpostContentBox = e.target.previousElementSibling;
        // 收起后恢复第一个点出的状态
        if(classObj.isHasClass(askpostContentBox, 'appear')) {
          classObj.addClass(askpostContentBox.children[0], 'postAppear');
          // 将其他模块收起
          for(let i = 1; i < askpostContentBox.children.length; i++) {
            classObj.removeClass(askpostContentBox.children[i], 'postAppear');
          }
          e.target.innerHTML = '查看更多';
        } else {
          e.target.innerHTML = '点击收起';
        }

        // 点击"查看更多"，出现与否
        classObj.toggleClass(askpostContentBox, 'appear');
      }
    },
    components: {
      askpostSlideBox,
      askpostItem
    }
  }
</script>

<style scoped>
  .askPost {
    margin: 0 auto;
    padding: 0 30px;
    margin-bottom: 30px;
    width: 1200px;
    /* height: 780px; */
    /* border: 1px solid #0ff; */
  }

  .askPost > h2{
    height: 25px;
    font-size: 25px;
    letter-spacing: 3px;
    margin-bottom: 25px;
  }

/* ——————————————————— 中间盒子：滑动盒（左）+ 伸缩内容（右） ——————————————————————— */
  .LearningWorld-toAskBox {
    display: flex;
    padding: 20px 0 25px 0;
    flex-direction: row;
    flex-wrap: wrap;
    border-top: 2px solid rgba(165, 165, 165, .3);
    border-bottom: 2px solid rgba(165, 165, 165, .3);
    /* border: 1px solid #f00; */
  }

  /* ——————————————— 滑动盒子 ————————————————— */
  .LearningWorld-slideBox {
    width: 280px;
    margin: 0 10px;
    /* border: 1px solid #f00; */
  }

  .LearningWorld-slideBox > div {
    position: sticky;
    top: 0;
    border: 1px solid rgba(0, 0, 0, 0);
  }

  /* ——————————————— 伸缩内容 ————————————————— */
  .LearningWorld-TA-mainBox {
    width: 835px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    /* border: 1px solid #00f; */

  }

  /* —————————————————— 问贴 Content ———————————————————— */
  .askpost-content-box {
    height: 620px;
    padding: 20px 20px 0 20px;
    overflow: hidden;
    transition: all ease-in-out .5s;
    /* border: 1px solid #34b3a8; */
  }

  .appear {
    height: 965px;
  }

  /* ———————— 查看更多 ———————— */
  .LearningWorld-TA-mainBox button {
    margin: 15px auto 0;
    border: none;
    outline: none;
    background: none;
    cursor: pointer;
    transition: all ease-in-out .3s;
  }

  .LearningWorld-TA-mainBox button:hover {
    color: #34b3a8;
  }

  /* 点击后显示 */
  .postAppear {
    height: 250px !important;
  }
</style>
