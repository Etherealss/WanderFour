<template>
  <div class="LearningWorld-TA-slideBox">
    <ul>
      <li>
        <slot name="edit-part"></slot>
      </li>
      <li class="LearningWorld-toPosition">
        <slot name="jump-position"></slot>
      </li>
      <li class="LearningWorld-returnTopBtn" @click="JumptoLWTop">
        <div>回到顶部</div>
      </li>
    </ul>
  </div>
</template>

<script>
  import modelsObj from '../../../assets/javascript/utils/models.js';

  export default {
    name: 'slideBox',
    data() {
      return {

      }
    },
    methods: {
      JumptoLWTop() {
        modelsObj.slowToTop();
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ——————— 左边上下滑动Part ————————— */
  .LearningWorld-TA-slideBox{
    width: 272px;
    /* height: 385px; */
    margin: 0 0 0 6px;
    border-radius: 5px;
    background: #fff;
    /* border: 1px solid #00f; */
  }

  /* 装内容 */
  .LearningWorld-TA-slideBox > ul{
    margin-top: 20px;
    height: 365px;
    list-style-type: none;
    border-radius: 5px;
    box-shadow: 1px 1px 10px rgba(204, 204, 204,0.6);
    /* border: 1px solid #333; */
  }

  .LearningWorld-TA-slideBox > ul > li{
    margin: 0 5px;
  }

  .LearningWorld-TA-slideBox > ul > li:nth-child(2),
  .LearningWorld-TA-slideBox > ul > li:nth-child(3) {
    /* width: 262px; */
    margin-left: 0;
    width: 100%;
    height: 55px;
    line-height: 55px;
    text-align: center;
    /* border-radius: 4px; */
    border-top: 1px solid #e8e8e8;
    cursor: pointer;
    transition-duration: 0.3s;
    /* border: 1px solid #0f0; */
  }

  .LearningWorld-TA-slideBox > ul > li:nth-child(3) > div {
    height: inherit;
    background: url(../../../assets/image/learningWorld/content/backTop2.png) 174px 19px no-repeat;
    background-size: 16px 18px;
  }

  .LearningWorld-TA-slideBox > ul > li:nth-child(2):hover,
  .LearningWorld-TA-slideBox > ul > li:nth-child(3):hover {
    background: #efefef;
  }
</style>
