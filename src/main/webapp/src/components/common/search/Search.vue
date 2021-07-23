<template>
  <div>
    <form action="" class="topmargin_searchBox">
      <div class="topmargin_searchOutsideBox">
        <input type="search" name="" id="topmarginSearch" autocomplete="off" disableautocomplete="off" placeholder="搜索学习经验/资源分享" />
        <!-- click.prevent 阻止默认事件 -->
        <button @click.prevent="jumpSearchClick"></button>
      </div>
      <!-- 下拉框 -->
      <div id="ComboBox">
        <hr color="#CACACA"/>
      </div>
    </form>
  </div>
</template>

<script>
  export default {
    name: 'Search',
    props: {
      path: String
    },
    data() {
      return {
      }
    },
    methods: {
      jumpSearchClick() {
        this.$router.push(this.path);
      }
    },
    components: {

    }
  }
</script>

<style scoped>
  /* ———————————————————— 搜索Part —————————————————————— */
  .topmargin_searchBox{
    position: absolute;
    top: 8px;
    padding: 0 25px;
    margin: 0 60px;
    border-radius: 20px;
    border: 2px solid #e0e0e0;
    background: #f7f7f7;
    /* border-radius: 20px 20px 0 0; */
    /* border: 2px solid #36b3a8; */
    /* border-bottom: none; */
  }

  .topmargin_searchOutsideBox{
    width: 286px;
    height: 36px;
    font-size: 14px;
    box-sizing: border-box;
    /* border: 1px solid #f00; */
  }

  .topmargin_searchOutsideBox input{
    width: 275px;
    height: 36px;
    border: none;
    outline: none;
    background: #f7f7f7;
    font-size: 0.8rem;
    /* border: 1px solid #00f; */
  }

  .topmargin_searchOutsideBox input::placeholder{
    color: #c0c0c0;
    font-size: 0.8rem;
  }

  .topmargin_searchOutsideBox button {
    width: 30px;
    height: 30px;
    position: absolute;
    top: 3px;
    right: 10px;
    border: none;
    outline: none;
    background: url(../../../assets/image/homepage/topmargin_search.png) no-repeat;
    background-size: 26px;
    border-radius: 50%;
    transition: ease-in-out .3s;
    cursor: pointer;
  }

  .topmargin_searchOutsideBox button:hover {
    box-shadow: 1px 1px 10px rgba(0, 0, 0, 0.45);
  }

  /* —————————————— 搜索的下拉列表 ———————————————— */
  .topmargin_searchBox #ComboBox{
    display: none;
    position: absolute;
    width: 336px;
    top: 36px;
    left: -2px;
    box-sizing: content-box;
    border-radius: 0 0 20px 20px;
    border-top: none;
    background: #f7f7f7;
    /* border: 2px solid #36b3a8; */
    /* border-top: none; */
  }
  /* 下拉后出现的选项的样式 */
  .topmargin_searchBox #ComboBox a{
    height: 32px;
    line-height: 32px;
    padding: 0 25px;
    display: block;
    color: #666;
    font-size: 15px;
    text-decoration: none;
    cursor: pointer;
    /* border: 1px solid #f00; */
  }

  .topmargin_searchBox #ComboBox a:hover{
    color: #36b3a8;
    /* background: #e7e7e7; */
  }

  /* 下拉后出现的灰色线条 */
  .topmargin_searchBox #ComboBox hr{
    border: .5px solid #CACACA;
    margin: 0 15px 5px;
  }

  /* 搜索框颜色 */
  .searchBoxColor{
    border-color: #36b3a8;
    /* border-radius: 20px 20px 0 0; */
  }
</style>
