<template>
  <div class="postOutsideBox">
    <div class="postsTitle">
      <h3>{{msg.title}}</h3>
      <img src="../../../assets/image/showPage/answerPosts_tapes.png" alt=""/>
    </div>
    <!-- 到下面回答问题区域才出现的标题 -->
    <div class="postsTitle" id="postsHidden">
      <h3>{{msg.title}}</h3>
      <img src="../../../assets/image/showPage/answerPosts_tapes.png" alt=""/>
    </div>
    <div class="answerPosts_user">
      <div class="answerPostHead"></div>
      <p id="answerPostsAuthor">小华er</p>
      <!-- <span>简介blabla</span> -->
    </div>
    <div class="answerPosts_txt" id="answerPosts_txt">
      {{msg.content}}
      <!-- 2020北京第一届新高考小白鼠。目前6选3科目基本确定历史和地理。只在化学与政治间纠结了。
      原因：1.新高考赋分制，避强趋弱，我化学高一没好好学，现在追悔莫及，不知以后是否能赶上比我聪明还比我努力的人。
      2.我政治虽然比化学成绩好，但优势也不是很明显，现在我越来越觉得政治难，不适合我，且新高考选传统的文科除了选择面窄我不知道是否还有别的劣势。
      3.大学招生科目限制，选理化生科目选择面更广，我又没有明确的目标专业和大学。 -->
    </div>
    <img src="../../../assets/image/showPage/answerPosts_tapes.png" alt=""/>
  </div>
</template>

<script>
  export default {
    name: 'askpostContent',
    data() {
      return {
        msg: {
          title: '到下面回答问题区域才出现的标题',
          content: `2020北京第一届新高考小白鼠。目前6选3科目基本确定历史和地理。只在化学与政治间纠结了。
        原因：1.新高考赋分制，避强趋弱，我化学高一没好好学，现在追悔莫及，不知以后是否能赶上比我聪明还比我努力的人。
        2.我政治虽然比化学成绩好，但优势也不是很明显，现在我越来越觉得政治难，不适合我，且新高考选传统的文科除了选择面窄我不知道是否还有别的劣势。
        3.大学招生科目限制，选理化生科目选择面更广，我又没有明确的目标专业和大学。
        3.大学招生科目限制，选理化生科目选择面更广，我又没有明确的目标专业和大学。
        3.大学招生科目限制，选理化生科目选择面更广，我又没有明确的目标专业和大学。`
        }
      }
    },
    mounted() {
      window.onscroll = function() {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
.postOutsideBox {
  width: 875px;
  min-height: 283px;
  padding-bottom: 20px;
  margin-bottom: 50px;
  background: #fff;
  position: relative;
  border-radius: 10px;
  box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.15);
  /* border: 1px solid #00f; */
}

/* —————————————— 问帖标题 —————————————— */
.postsTitle {
  min-height: 30px;
  border-radius: 10px 10px 0 0;
  background: #ffffff;
  box-shadow: -1px 1px 5px rgba(0, 0, 0, 0.15);
  z-index: 10;
  /* border: 1px solid #f00; */
}

.postsTitle h3 {
  min-height: 28px;
  line-height: 30px;
  padding: 10px 36px;
  overflow-wrap: break-word;  /*要会换行*/
  font-size: 18px;
  color: #333333;
  border-bottom: 1px solid #f7f7f7;
  box-shadow: 1px 1px 10px rgba(193, 193, 193,0.1);
}
/* ———————— 胶带 ———————— */
.postsTitle > img {
  width: 32px;
  height: 60px;
  position: absolute;
  right: 31px;
  top: -32px;
}

/* ————————————— 当页面滑动到看不到问贴时，就出现这个 ————————————— */
#postsHidden {
  position: fixed;
  width: 875px;
  height: 51px;
  top: 0;
  border-radius: 0;
  background: #ffffff;
  /* background: #bce6df; */
  box-shadow: -1px 1px 10px rgba(0, 0, 0, 0.15);
  /* display: none; */
  /* border: 1px solid #f00; */
}

/* ——————— 固定位置的胶带 ——————— */
#postsHidden > img {
  top: -5px;
  right: -20px;
  transform: rotate(90deg);
  /* border: 1px solid #f00; */
}

/* ———————————————————— 用户信息Part ———————————————————————— */
.answerPosts_user {
  height: 70px;
  /* padding-top: 50px; */
  /* margin-top: 50px; */
  position: relative;
  /* border: 1px solid #00f; */
}

.answerPostHead {
  width: 50px;
  height: 50px;
  position: absolute;
  top: 12px;
  /* top: 62px; */
  left: 36px;
  border-radius: 50%;
  background-color: #ade1d9;
  /* background: url(../img/homePage_highSchoolStudent_head.png); */
  background-size: 100%;
}

.answerPosts_user p {
  position: absolute;
  /* top: 72px; */
  top: 26px;
  left: 102px;
  font-size: 16px;
  color: #333;
  font-weight: 800;

}

.answerPosts_user span {
  position: absolute;
  /* top: 95px; */
  top: 45px;
  left: 91px;
  font-size: 12px;
  color: #999;
}

/* ————————————— 对标题的描述 ——————————————— */
.answerPosts_txt {
  padding: 16px 36px 16px 36px;
  text-indent: 2rem;
  line-height: 25px;
  /* border: 1px solid #0f0; */
}

/* ————————————— 下面的胶带 ——————————————— */
.postOutsideBox > img {
  width: 32px;
  height: 60px;
  position: absolute;
  left: 46px;
  bottom: -28px;
}
</style>
