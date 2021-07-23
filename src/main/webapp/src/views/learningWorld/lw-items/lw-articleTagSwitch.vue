<template>
  <div class="article-tab-box">
    <ul id="LearningWorld_LE_optionBox">
      <li @click='change(index)' :class='currentIndex==index?"tabSelected":""' :key='item.id' v-for='(item,index) in list'>
        {{item.tag}}
      </li>
    </ul>
    <div :class='currentIndex==index?"current":""' :key='item.id' v-for='(item,index) in list'>
      <article-item v-for="(i, j) in item.abbData" :msg="i"/>
    </div>
  </div>
</template>

<script>
  import articleItem from './lw-articleItem.vue';

  export default {
    name: 'articleTagSwitch',
    data() {
      return {
        currentIndex: 0,
        list: [{
          id:1,
          tag:'HOT',
          // Abbreviated data 缩略数据
          abbData: [{
            articleId: 1,
            title: 'hot111',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 2,
            title: 'hot222',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 3,
            title: 'hot333',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 4,
            title: 'hot444',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 5,
            title: 'hot555',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 6,
            title: 'hot666',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          }]
        },
        {
          id:2,
          tag:'NEW',
          abbData: [{
            articleId: 1,
            title: 'new111',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 2,
            title: 'new222',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 3,
            title: 'new333',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 4,
            title: 'new444',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 5,
            title: 'new555',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          },{
            articleId: 6,
            title: 'new666',
            summary: 'dlfahglksjhglk',
            tags: ['学习经验', '数学', '高效学习']
          }]
        }]
      }
    },
    methods: {
      change: function(index){
        this.currentIndex=index;
      }
    },
    components: {
      articleItem
    }
  }
</script>

<style scoped>
  /* —————————————————————— 切换栏 —————————————————————————— */
  #LearningWorld_LE_optionBox {
    display: flex;
    margin: 0 -10px 18px -10px;
    padding: 0 0 5px 10px;
    list-style: none;
    /* border-bottom: 2px solid rgba(165, 165, 165, .3); */
    /* border: 1px solid #f00; */
  }

  #LearningWorld_LE_optionBox > li {
    padding: 0 5px;
    margin-right: 30px;
    line-height: 45px;
    font-size: 17px;
    text-shadow: .1px .1px .2px rgba(0, 0, 0,0.6);
    cursor: pointer;
    /* border: 1px solid #f00; */
  }

  #LearningWorld_LE_optionBox li:hover {
    color: #81cbbf;
  }

  .tabSelected {
    color: #81cbbf;
    background: url(../../../assets/image/learningWorld/content/LW_toggleBackground.png) no-repeat;
    background-size: 100% 48px;
    border-radius: 0 0 2px 2px;
  }

  /* ————————————————— 切换栏 ——————————————————— */
  .current {
    display: block !important;
  }

  .article-tab-box > div {
    display: none;
  }
</style>
