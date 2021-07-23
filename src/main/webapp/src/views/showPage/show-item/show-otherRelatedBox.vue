<template>
<div class="otherRelatedBox">
  <ul>
    <div>
      <p>最新问贴</p>
      <img class="answerPosts_leaf" src="../../../assets/image/showPage/answerPosts_leafg.png"/>
    </div>
    <li id="showNewNav1">
      <a href=""></a>
    </li>
    <li id="showNewNav2">
      <a href=""></a>
    </li>
    <li id="showNewNav3">
      <a href=""></a>
    </li>
    <li id="showNewNav4">
      <a href=""></a>
    </li>
    <li id="showNewNav5">
      <a href=""></a>
    </li>
  </ul>
</div>
</template>

<script>
  export default {
    name: 'otherRelatedBox',
    data() {
      return {

      }
    },
    components: {

    }
  }
</script>

<style scoped>
  .otherRelatedBox {
    width: 265px;
    height: 295px;
    border-radius: 10px;
    box-shadow: 1px 1px 15px rgba(193, 193, 193,0.8);
  }

  .otherRelatedBox ul{
    background: #daf2ee;
    border-radius: 10px;
  }

  .otherRelatedBox ul > div{
    background-color: #E8F3F1;
    background: url(../../../assets/image/showPage/answerPosts_new.png) 0 5px no-repeat;
    background-size: 90px 41px;
  }

  .otherRelatedBox ul li:hover{
    background: #bce6df;
    cursor: pointer;
  }
</style>
