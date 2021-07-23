<template>
  <div>
    <slide-box>
      <div slot="edit-part" class="LearningWorld_TA_write">
        <span class="toAskBox_coil" id="toAskBox_coil1"></span>
        <span class="toAskBox_coil" id="toAskBox_coil2"></span>
        <ul>
          <li>
            <div class="toAsk-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_posting.png" alt=""/>
            </div>
            <p>我想发问贴</p>
          </li>
          <li>
            <div class="toAsk-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_myArticle.png" alt=""/>
            </div>
            <p>我的问贴</p>
          </li>
          <li>
            <div class="toAsk-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_drafts.png" alt=""/>
            </div>
            <p>草稿箱</p>
          </li>
        </ul>
      </div>
      <div slot="jump-position" class="LearningWorld-toWriting" @click="JumptoWriting()">文章</div>
    </slide-box>
  </div>
</template>

<script>
  import modelsObj from '../../../assets/javascript/utils/models.js';
  import slideBox from './lw-slideBox.vue';

  export default {
    name: 'askpostSlideBox',
    props: {
      path: String
    },
    data() {
      return {

      }
    },
    methods: {
      JumptoWriting() {
        let LWwriting = document.querySelector('.learning-world-article');
        modelsObj.slowToTarget(LWwriting);
      }
    },
    components: {
      slideBox
    }
  }
</script>

<style scoped>
  /* —————— 发问帖、问贴、草稿箱 ——————— */
  .LearningWorld_TA_write {
    position: relative;
    padding: 38px 0 0 0;
    list-style-type: none;
    background: url(../../../assets/image/learningWorld/content/LearingWorld_askPost_sideBackground.png) -2px -18px no-repeat;
    background-size: 272px auto;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_TA_write > ul > li{
    height: 70px;
    display: flex;
    position: relative;
    cursor: pointer;
    /* border: 1px solid #0f0; */
  }

  .LearningWorld_TA_write .toAsk-icon {
    width: 43px;
    height: 45px;
    margin: 8px 0 0 18px;
    background: #d0edee;
    border-radius: 10px;
    transition: all ease-in-out .3s;
  }

  .LearningWorld_TA_write .toAsk-icon img{
    width: 25px;
    height: 25px;
    margin: 10px;
  }

  .LearningWorld_TA_write > ul > li p {
    width: 240px;
    position: absolute;
    line-height: 50px;
    padding: 7px 0 0 68px;
    left: 10px;
    border-radius: 5px;
    text-decoration: none;
    color: #333;
    font-size: 15px;
    transition: all ease-in-out .3s;
    /* border: 1px solid #00f; */
  }

  .LearningWorld_TA_write > ul > li:hover p {
    color: #34b3a8;
  }

  .LearningWorld_TA_write > ul > li:hover .toAsk-icon {
    box-shadow: 1px 1px 5px rgba(0, 0, 0, .35);
  }

  /* —————— 侧栏两个小线圈 —————— */
  .toAskBox_coil{
    width: 20px;
    height: 48px;
    position: absolute;
    top: -24px;
    display: inline-block;
    background: #a5a5a5;
    border-radius: 22px;
    border: 1px solid rgba(165, 165, 165,0.9);
    z-index: 10;
    /* border: 1px solid #f00; */
  }

  #toAskBox_coil1{left: 81px;}
  #toAskBox_coil2{left: 177px;}

  /* 跳转的图标 */
  .LearningWorld-toWriting {
    height: inherit;
    background: url(../../../assets/image/learningWorld/content/backTop3.png) 156px 19px no-repeat;
    background-size: 15px 17px;
  }
</style>
