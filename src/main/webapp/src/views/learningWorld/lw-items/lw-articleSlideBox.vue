<template>
  <div>
    <slide-box>
      <div slot="edit-part">
        <ul class="LearningWorld_LE_write">
          <li>
            <div class="article-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_posting.png" alt="" />
            </div>
            <span>写文章</span>
          </li>
          <li>
            <div class="article-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_myArticle.png" alt="" />
            </div>
            <span>学习经验</span>
          </li>
          <li>
            <div class="article-icon">
              <img src="../../../assets/image/learningWorld/content/professionalIntroduction_drafts.png" alt="" />
            </div>
            <span>草稿箱</span>
          </li>
        </ul>
      </div>
      <div slot="jump-position" class="LearningWorld_toAsk" @click="JumptoAskPost()">问贴</div>
    </slide-box>
  </div>
</template>

<script>
  import modelsObj from '../../../assets/javascript/utils/models.js';
  import slideBox from './lw-slideBox.vue';

  export default {
    name: 'articleSlideBox',
    data() {
      return {

      }
    },
    methods: {
      JumptoAskPost() {
        let LWAskPost = document.querySelector('.askPost');
        modelsObj.slowToTarget(LWAskPost);
      }
    },
    components: {
      slideBox
    }
  }
</script>

<style scoped>
  /* ————————— 右侧栏第一板块 ——————————— */
  .LearningWorld_LE_write {
    display: flex;
    margin:  10px auto 10px;
    flex-direction: row;
    flex-wrap: wrap;
    list-style: none;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_LE_write li {
    width: 100px;
    height: 100px;
    margin: 18px 0 0 22px;
    cursor: pointer;
    /* border: 1px solid #0f0; */
  }

  .LearningWorld_LE_write li .article-icon {
    width: 46px;
    height: 46px;
    margin: 10px auto 10px;
    border-radius: 50%;
    background: #d0edee;
    transition: all ease-in-out .3s;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_LE_write li .article-icon img {
    width: 28px;
    height: 28px;
    margin: 9px;
  }

  .LearningWorld_LE_write li > span {
    display: inline-block;
    width: 100%;
    color: #333;
    text-align: center;
    transition: all ease-in-out .3s;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_LE_write li:hover span {
    color: #81cbbf;
  }

  .LearningWorld_LE_write li:hover .article-icon {
    box-shadow: -1px 1px 5px rgba(0, 0, 0, .35);
  }

  /* 跳转的图标 */
  .LearningWorld_toAsk {
    height: inherit;
    background: url(../../../assets/image/learningWorld/content/backTop1.png) 156px 19px no-repeat;
    background-size: 15px 17px;
  }
</style>
