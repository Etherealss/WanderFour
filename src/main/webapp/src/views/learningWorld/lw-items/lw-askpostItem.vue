<template>
  <div class="LearningWorld_TA_askPost" @click.stop="showClick">
  <!-- 跳转到askPostShow问帖显示页面 -->
    <router-link to="/user/askPostShow">
      <h3 @click="askPostShowClick">{{msg.title}}</h3>
    </router-link>
    <p>{{msg.summary}}</p>
    <ul class="LearningWorld_TA_comment">
      <li v-for="(item, index) in msg.comments">第 {{index+1}} 楼: {{item}}</li>
    </ul>
  </div>
</template>

<script>
  import classObj from '../../../assets/javascript/utils/className.js';
  import modelsObj from '../../../assets/javascript/utils/models.js';

  export default {
    name: 'askpostItem',
    props: {
      path: String
    },
    data() {
      return {
        msg: {
          title: '新高考。应该怎样选科，怎样选课又是最合理的？',
          summary: `2020北京第一届新高考小白鼠。目前6选3科目基本确定历史和地理。只在化学与政治间纠结了。
            1.新高考赋分制，避强趋弱，我化学高一没好好学，现在追悔莫及，不知以后是否能赶上比我聪明还比我努力的人。
            2.我政治虽然比化学成绩好，但优势也不是很明显，现在我越来越觉得政治难，不适合我，
            且新高考选传统的文科除了选择面窄我不知道是否还有别的劣势。
            3.大学招生科目限制，选理化生科目选择面更广，我又没有明确的目标专业和大学。`,
          comments: [
            '选一科自己喜欢的、学得下的',
            '我觉得化学太恐怖了，就没及格过。',
            '可以先去了解一下专业，再做出决定喔！'
          ]
        }
      }
    },
    mounted() {
      let get$el = this.$el;  // 当前组件的根元素
      window.onload = function() {
        classObj.addClass(get$el.parentNode.children[0], 'postAppear');
      }
    },
    methods: {
      showClick() { // 点击后显示评论
        classObj.addClass(this.$el, 'postAppear');  // 该模块显示评论
        let len = this.$el.parentNode.children.length;  // 同级模块的个数
        let num = modelsObj.takeIndex(this.$el, len);   // 查询出本模块在同级元素中的序位
        // 将其他模块收起
        for(let i = 0; i < len; i++) {
          if(i != num)
            classObj.removeClass(this.$el.parentNode.children[i], 'postAppear');
        }
      },
      askPostShowClick() {  // 跳转页面
        this.$router.push(this.path);
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  .LearningWorld_TA_askPost {
    margin: 0 0 15px 0;
    width: 800px;
    height: 155px;  /* 限制 */
    background: #fff;
    border-radius: 5px;
    box-shadow: 1px 1px 12px rgba(0, 0, 0, 0.15);
    cursor: pointer;
    transition: all ease-in-out .3s;
    overflow: hidden;
    /* border: 1px solid #0f0; */
  }

  /* .LearningWorld_TA_askPost:nth-child(1) {
    height: 250px;
  } */

  .LearningWorld_TA_askPost:hover {
    box-shadow: 1px 1px 10px rgba(0, 0, 0,0.35);
    cursor: pointer;
  }

  .LearningWorld_TA_askPost h3 {
    display: inline-block;
    margin: 20px 0 0 25px;
    color: #343434;
    font-size: 19px;
    letter-spacing: 2px;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_TA_askPost h3:hover {color: #81cbbf;}

  .LearningWorld_TA_askPost p {
    position: relative;
    height: 80px;
    margin: 18px 25px 0 35px;
    line-height: 20px;
    text-indent: 2rem;
    font-size: 14px;
    color: #343434;
    overflow: hidden;
    /* border: 1px solid #f00; */
  }

  .LearningWorld_TA_askPost p::after {
    content: '';
    width: 100px;
    height: 20px;
    position: absolute;
    bottom: 0;
    right: 0;
    background: linear-gradient(to right, rgba(255,255,255,0.2),rgba(255,255,255,0.8),rgba(255,255,255,1));
    /* border: 1px solid #f00; */
  }

  /* —————————— 悬浮伸缩 ———————————— */
  .LearningWorld_TA_askPost > ul {
    margin: 18px 35px;
    list-style-type: none;
    color: #666;
    border-radius: 5px;
    border: 1px solid #f5f5f5;
    background: #f5f5f5;
    box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.15),
                -1px -1px 1px rgba(0, 0, 0, 0.1);
    /* transition: all ease-in-out .5s; */
  }

  .LearningWorld_TA_askPost > ul li {
    margin: 6px 15px;
    font-size: 14px;
    /* border: 1px solid #00f; */
    /* 超出部分使用省略号 */
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .LearningWorld_TA_askPost > ul li:nth-child(1) {margin-top: 8px;}
  /* 点击后显示 */
  .postAppear {
    height: 250px !important;
  }
</style>
